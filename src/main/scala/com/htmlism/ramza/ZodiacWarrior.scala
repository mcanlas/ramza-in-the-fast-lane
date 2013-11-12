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
    prerequisites(jobName).forall({
      case (prerequisiteName, prerequisiteLevel) => jobLevel(prerequisiteName) >= prerequisiteLevel
    })
  })

  def jobPoints(job: JobClass) = jobs.getOrElse(job, 0)

  def prettyPrint {
    println("Experience: " + experiencePoints)
    jobs.foreach {
      case (k, v) => println("  " + k + ": " + v)
    }
  }
}
