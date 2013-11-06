package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass

case class ZodiacWarrior(jobPoints: Map[JobClass, Int] = emptyCareer, experiencePoints: Int = 100) {
  def level = experiencePoints / 100

  import scala.annotation.tailrec

  @tailrec
  final def jobLevel(job: JobClass, minimumsToCheck: List[Int] = jobPointMinimums, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinimums if jobPoints(job) >= jobPointMinimum => jobLevel(job, remainingMinimums, level + 1)
    case _ => level
  }

  def availableJobs = ???
}
