name := "Planning_Permission"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "junit"                       %   "junit"           % "4.5",
  "org.scalatest"               %%  "scalatest"       % "3.0.1",
  "org.apache.spark"            %%  "spark-core"      % "2.3.0",
  "org.apache.spark"            %%  "spark-sql"       % "2.4.0",
  "com.typesafe.scala-logging"  %%  "scala-logging"   % "3.1.0",
  "ch.qos.logback"              %   "logback-classic" % "1.1.2"
)
