package com.htmlism.ramza

case class ZodiacWarrior(jobPoints: Map[JobId, Int] = Map(1 -> 0, 2 -> 0), experiencePoints: Int = 100) {
  def level = experiencePoints / 100

  def jobLevel(job: JobId, minimumsToCheck: List[Int] = jobPointMinimums, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinimums =>
      if (jobPoints(job) >= jobPointMinimum)
        jobLevel(job, remainingMinimums, level + 1)
      else
        level
    case Nil => level
  }

  def availableJobs = ???
}
