package com.htmlism.ramza

import scala.annotation.tailrec

object ZodiacWarrior {
  val defaultExperience = 100

  def apply(jobClass: JobClass): ZodiacWarrior = {
    val size = prerequisites(jobClass).size

    ZodiacWarrior(defaultExperience, career = IndexedSeq.fill(size)(0))
  }
}

case class ZodiacWarrior(experiencePoints: Int, private val career: IndexedSeq[Int] = IndexedSeq()) {
  def withExp(jobIndex: Int, baseJpToGain: Int): ZodiacWarrior = {
    val currentJp = career(jobIndex)
    val augmentedJpToGain = baseJpToGain * 3 / 2

    ZodiacWarrior(experiencePoints + 10, career = career.updated(jobIndex, currentJp + augmentedJpToGain))
  }

  def withJp(job: JobClass, newJobPoints: Int)(implicit context: SolverContext): ZodiacWarrior =
    copy(career = career.updated(context.indexesByJob(job), newJobPoints))

  def withSharedJp(jobIndex: Int, baseJpToGain: Int): ZodiacWarrior = copy(career = career.updated(jobIndex, career(jobIndex) + baseJpToGain / 4))

  def level: Int =
    if (experiencePoints > 99 * 100)
      99
    else
      experiencePoints / 100

  @tailrec
  final def jobLevel(jobIndex: Int, minimumsToCheck: Seq[Int] = jobPointMinima, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinima if career(jobIndex) >= jobPointMinimum => jobLevel(jobIndex, remainingMinima, level + 1)
    case _ => level
  }

  def availableJobs(implicit context: SolverContext): Seq[Int] = {
    val SolverContext(_, _, prerequisites, _) = context

    prerequisites.indices.filter { i =>
      val prereq = prerequisites(i)

      val satisfiesJobMinima = prereq.indices.forall { jobIndex =>
        jobLevel(jobIndex) >= prereq(jobIndex)
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

  def toStableSortableString: String = (experiencePoints +: career).mkString(",")
}
