package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass
import scala.annotation.tailrec

case class ZodiacWarrior(experiencePoints: Int = 100, jobs: Map[JobClass, Int] = Map.empty) {
  def level = experiencePoints / 100

  @tailrec
  final def jobLevel(job: JobClass, minimumsToCheck: List[Int] = jobPointMinima, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinima if jobPoints(job) >= jobPointMinimum => jobLevel(job, remainingMinima, level + 1)
    case _ => level
  }

  def availableJobs = prerequisites.keys.filter({ jobName =>
    import Jobs._

    val special = if (jobName == DarkKnight) true else prerequisites(DarkKnight).contains(jobName) && jobLevel(jobName) < prerequisites(DarkKnight)(jobName)

    special && prerequisites(jobName).forall({
      case (prerequisiteName, prerequisiteLevel) => jobLevel(prerequisiteName) >= prerequisiteLevel
    })
  })

  def jobPoints(job: JobClass) = jobs.getOrElse(job, 0)

  def prettyPrint {
    println("Experience: " + experiencePoints + jobs.map({ case (k, v) => "  " + k + ": " + v }).mkString(","))
  }
}
