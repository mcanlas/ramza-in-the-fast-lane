package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass
import scala.annotation.tailrec
import com.htmlism.ramza.ZodiacWarrior._

object ZodiacWarrior {
  type PrerequisiteTable = Vector[Vector[Int]]

  def dictionaryFor(jobClass: JobClass) = {
    val prerequisitesForThisJob = prerequisites(jobClass)

    val indexesByPrerequisites = prerequisites(jobClass).keys.zipWithIndex.toList.toMap
    val sortedJobs = indexesByPrerequisites.toList sortBy(_._2) map(_._1)

    val prerequisitesTable = ((sortedJobs :+ jobClass) map {
      j =>
        (sortedJobs map {
          prereq =>
            prerequisites(j).getOrElse(prereq, 0)
        }).toVector
    }).toVector

    val indexesByJob = indexesByPrerequisites + (jobClass -> prerequisitesForThisJob.size)

    (indexesByJob, prerequisitesTable)
  }

  def toSolve(jobClass: JobClass) = {
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

  def withJp(job: JobClass, newJobPoints: Int)(implicit indexesByJob: Map[JobClass, Int]) = copy(career = career.updated(indexesByJob(job), newJobPoints))

  def withSharedJp(jobIndex: Int, baseJpToGain: Int) = copy(career = career.updated(jobIndex, career(jobIndex) + baseJpToGain / 4))

  def level = if (experiencePoints > 99 * 100) 99 else experiencePoints / 100

  @tailrec
  final def jobLevelVector(jobIndex: Int, minimumsToCheck: List[Int] = jobPointMinima, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinima if career(jobIndex) >= jobPointMinimum => jobLevelVector(jobIndex, remainingMinima, level + 1)
    case _ => level
  }

  def availableJobsVector(implicit prerequisites: PrerequisiteTable) = prerequisites.indices.filter({ i =>
    val prereq = prerequisites(i)

    prereq.indices.forall({ jobIndex =>
      jobLevelVector(jobIndex) >= prereq(jobIndex)
    })
  })

  def jobPoints(jobIndex: Int) = career(jobIndex)

  def distanceFrom(job: Int)(implicit prerequisites: PrerequisiteTable) = {
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
