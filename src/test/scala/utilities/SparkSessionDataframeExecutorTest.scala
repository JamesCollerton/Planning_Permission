package utilities

import org.apache.spark.sql.functions._
import org.scalatest.FunSuite

class SparkSessionDataframeExecutorTest extends FunSuite {

  test("Given valid basic schema, when find schema, then correct schema returned") {
    val expectedSchema = "root\n |-- age: long (nullable = true)\n |-- city: string (nullable = true)\n |-- name: string (nullable = true)\n"
    val actualSchema = SparkSessionDataframeExecutor.buildSessionExecuteFunction(
                          "src/test/resources/data/utilities/valid-basic.json",
                          d => d.schema.treeString
                        )
    assert(actualSchema == expectedSchema)
  }

  test("Given valid dataset with ten records, when count records, then returns ten") {
    val count = SparkSessionDataframeExecutor.buildSessionExecuteFunction(
      "src/test/resources/data/utilities/valid-ten-records.json",
      d => d.count
    )
    assert(count == 10)
  }

  test("Given valid file with ten rows and three distinct case officers, when find case officers, returns correct three") {
    val caseOfficerList = SparkSessionDataframeExecutor.buildSessionExecuteFunction(
      "src/test/resources/data/utilities/valid-ten-rows-three-case-officers.json",
      d => d.select("CASEOFFICER").distinct().collect().mkString(", ")
    )
    assert(caseOfficerList == "[strawberry], [apple], [banana]")
  }

  test("Given valid file with ten rows, when find top 2 agents, then returns top 2 agents") {

    val caseOfficerList = SparkSessionDataframeExecutor.buildSessionExecuteFunction(
      "src/test/resources/data/utilities/valid-file-five-agents.json",
      d => d.groupBy("AGENT")
            .count()
            .withColumnRenamed("count", "n")
            .orderBy(desc("n"))
            .select("AGENT")
            .head(2)
            .mkString(", ")
    )
    assert(caseOfficerList == "[pineapple], [banana]")
  }

  test("Given valid file with five unique words, when count unique words, then returns correct result") {

    val caseTextCount = SparkSessionDataframeExecutor.buildSessionExecuteFunction(
      "src/test/resources/data/utilities/valid-file-five-unique-words.json",
      d => d.withColumn("CASETEXT", explode(split(col("CASETEXT"), " ")))
              .groupBy("CASETEXT")
              .count()
              .collect()
              .mkString(", ")
    )
    assert(caseTextCount == "[strawberry,1], [kiwi,1], [apple,1], [banana,1], [pineapple,1]")

  }

  test("Given valid input file with four days difference, when find consultation length, then return two days") {

    val averageConsultationLength = SparkSessionDataframeExecutor.buildSessionExecuteFunction(
      "src/test/resources/data/utilities/valid-file-four-days-difference.json",
      d => d.withColumn("PUBLICCONSULTATIONDIFFERENCE",
                datediff(to_date(col("PUBLICCONSULTATIONENDDATE"), "dd/MM/yyyy"), to_date(col("PUBLICCONSULTATIONSTARTDATE"), "dd/MM/yyyy"))
              )
              .agg(
                avg(col("PUBLICCONSULTATIONDIFFERENCE")).as("AVGPUBLICCONSULTATIONDIFFERENCE")
              )
              .collect()
              .mkString(", ")
    )
    assert(averageConsultationLength == "[4.0]")

  }

}
