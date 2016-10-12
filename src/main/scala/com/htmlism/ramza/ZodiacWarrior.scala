package com.htmlism.ramza

object ZodiacWarrior {
  val defaultExperience = 100
  val maximumLevel = 99

  def apply(jobClass: JobClass): ZodiacWarrior = {
    val size = prerequisites(jobClass).size

    ZodiacWarrior(defaultExperience, career = IndexedSeq.fill(size)(0))
  }
}

case class ZodiacWarrior(experiencePoints: Int, career: IndexedSeq[Int]) {
  def withExp(jobIndex: Int, baseJpToGain: Int): ZodiacWarrior = {
    val currentJp = career(jobIndex)
    val augmentedJpToGain = baseJpToGain * 3 / 2

    ZodiacWarrior(experiencePoints + 10, career = career.updated(jobIndex, currentJp + augmentedJpToGain))
  }

  def withJp(job: JobClass, newJobPoints: Int)(implicit context: SolverContext): ZodiacWarrior =
    copy(career = career.updated(context.indexesByJob(job), newJobPoints))

  def withSharedJp(jobIndex: Int, baseJpToGain: Int): ZodiacWarrior = copy(career = career.updated(jobIndex, career(jobIndex) + baseJpToGain / 4))

  def level: Int =
    if (experiencePoints > ZodiacWarrior.maximumLevel * 100)
      ZodiacWarrior.maximumLevel
    else
      experiencePoints / 100

  def availableJobs(implicit context: SolverContext): TraversableOnce[Int] = {
    val SolverContext(_, _, prerequisites, _) = context

    prerequisites.indices.filter { i =>
      val prereq = prerequisites(i)

      val satisfiesJobMinima = prereq.indices.forall { jobIndex =>
        jobLevel(career(jobIndex)) >= prereq(jobIndex)
      }

      satisfiesJobMinima
    }
  }

  def jobPoints(jobIndex: Int): Int = career(jobIndex)

  def distanceFrom(job: Int)(implicit context: SolverContext): Int = {
    val SolverContext(_, _, prerequisites, _) = context

    prerequisites(job).indices.map { i =>
      val requiredPoints = jobPointMinima(prerequisites(job)(i) - 2)

      if (requiredPoints > career(i))
        requiredPoints - career(i)
      else
        0
    }.sum
  }
}
