package exercisethree

import com.typesafe.scalalogging.LazyLogging
import utilities.DoubleArgumentExerciseExecutor

/**
  * Identify the set of case officers (CASEOFFICER field) and output a unique list of these to a file.
  *
  * Improvements:
  *   - Assumes that column exists
  */
object ExerciseThree extends LazyLogging {

  /**
    * Point of entry for the program. Expects two parameters, the first containing a
    * resource name to execute against, the second containing a filename to write against.
    *
    * @param args Array of parameters, first should be resource to execute against, second should be file to write to
    */
  def main(args: Array[String]): Unit= {

    logger.info(s"Entered ExerciseThree.main: $args")

    DoubleArgumentExerciseExecutor.execute(
      args,
      d => d.select("CASEOFFICER").distinct().collect().mkString(", ")
    )

    logger.info(s"Exiting ExerciseThree.main: $args")

  }

}
