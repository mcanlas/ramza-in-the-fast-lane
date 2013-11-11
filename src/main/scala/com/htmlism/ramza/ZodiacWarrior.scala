package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass

case class ZodiacWarrior(jobPoints: Map[JobClass, Int] = Map.empty, experiencePoints: Int = 100) {
  def level = experiencePoints / 100

  import scala.annotation.tailrec

  @tailrec
  final def jobLevel(job: JobClass, minimumsToCheck: List[Int] = jobPointMinima, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinima if jobPoints.contains(job) && jobPoints(job) >= jobPointMinimum => jobLevel(job, remainingMinima, level + 1)
    case _ => level
  }

  def availableJobs = prerequisites.keys.filter({ jobName =>
    prerequisites(jobName).forall({
      case (prerequisiteName, prerequisiteLevel) => jobPoints.getOrElse(prerequisiteName, 0) >= prerequisiteLevel
    })
  })
}
