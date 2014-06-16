package com.htmlism.ramza

import scala.annotation.tailrec

object ZodiacWarrior {
  def apply(jobClass: JobClass): ZodiacWarrior = {
    val size = prerequisites(jobClass).size

    ZodiacWarrior(career = Vector.fill(size)(0))
  }
}

case class ZodiacWarrior(experiencePoints: Int = 100, private val career: Vector[Int] = Vector()) {
  def withExp(jobIndex: Int, baseJpToGain: Int) = {
    val currentJp = career(jobIndex)
    val augmentedJpToGain = baseJpToGain * 3 / 2

    ZodiacWarrior(experiencePoints + 10, career = career.updated(jobIndex, currentJp + augmentedJpToGain))
  }

  def withJp(job: JobClass, newJobPoints: Int)(implicit context: SolverContext) = copy(career = career.updated(context.indexesByJob(job), newJobPoints))

  def withSharedJp(jobIndex: Int, baseJpToGain: Int) = copy(career = career.updated(jobIndex, career(jobIndex) + baseJpToGain / 4))

  def level = if (experiencePoints > 99 * 100) 99 else experiencePoints / 100

  @tailrec
  final def jobLevelVector(jobIndex: Int, minimumsToCheck: List[Int] = jobPointMinima, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinima if career(jobIndex) >= jobPointMinimum => jobLevelVector(jobIndex, remainingMinima, level + 1)
    case _ => level
  }

  def availableJobsVector(implicit context: SolverContext) = {
    val SolverContext(_, _, prerequisites, _) = context

    prerequisites.indices.filter({ i =>
      val prereq = prerequisites(i)

      prereq.indices.forall({ jobIndex =>
        jobLevelVector(jobIndex) >= prereq(jobIndex)
      })
    })
  }

  def jobPoints(jobIndex: Int) = career(jobIndex)

  def distanceFrom(job: Int)(implicit context: SolverContext) = {
    val SolverContext(_, _, prerequisites, _) = context

    prerequisites(job).indices.map { i =>
      val requiredPoints = jobPointMinima(prerequisites(job)(i) - 2)

      if (requiredPoints > career(i))
        requiredPoints - career(i)
      else
        0
    }.sum
  }

  def toStableSortableString = (experiencePoints :: career.toList).mkString(",")
}
