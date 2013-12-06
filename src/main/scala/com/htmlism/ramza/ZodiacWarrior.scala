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

case class ZodiacWarrior(experiencePoints: Int = 100, private val jobs: Map[JobClass, Int] = Map.empty, private val career: Vector[Int] = Vector()) {
  def withExp(jobIndex: Int, baseJpToGain: Int) = {
    val currentJp = career(jobIndex)
    val augmentedJpToGain = baseJpToGain * 3 / 2

    ZodiacWarrior(experiencePoints + 10, career = career.updated(jobIndex, currentJp + augmentedJpToGain))
  }

  def withExp(job: JobClass, baseJpToGain: Int) = {
    val currentJp = jobPoints(job)
    val augmentedJpToGain = baseJpToGain * 3 / 2

    ZodiacWarrior(experiencePoints + 10, jobs + (job -> (currentJp + augmentedJpToGain)))
  }

  def withJp(job: JobClass, newJobPoints: Int)(implicit indexesByJob: Map[JobClass, Int]) = copy(career = career.updated(indexesByJob(job), newJobPoints))

  def withSharedJp(jobIndex: Int, baseJpToGain: Int) = copy(career = career.updated(jobIndex, career(jobIndex) + baseJpToGain / 4))

  def withSharedJp(job: JobClass, baseJpToGain: Int) = copy(jobs = jobs + (job -> (jobPoints(job) + baseJpToGain / 4)))

  def level = if (experiencePoints > 99 * 100) 99 else experiencePoints / 100

  @tailrec
  final def jobLevel(job: JobClass, minimumsToCheck: List[Int] = jobPointMinima, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinima if jobPoints(job) >= jobPointMinimum => jobLevel(job, remainingMinima, level + 1)
    case _ => level
  }

  @tailrec
  final def jobLevelVector(jobIndex: Int, minimumsToCheck: List[Int] = jobPointMinima, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinima if career(jobIndex) >= jobPointMinimum => jobLevelVector(jobIndex, remainingMinima, level + 1)
    case _ => level
  }

  def availableJobs = prerequisites.keys.filter({ job =>
    prerequisites(job).forall({
      case (prerequisiteJob, prerequisiteLevel) => jobLevel(prerequisiteJob) >= prerequisiteLevel
    })
  })

  def availableJobsVector(implicit prerequisites: PrerequisiteTable) = prerequisites.indices.filter({ i =>
    val prereq = prerequisites(i)

    prereq.indices.forall({ jobIndex =>
      jobLevelVector(jobIndex) >= prereq(jobIndex)
    })
  })

  def jobPoints(job: JobClass) = jobs.getOrElse(job, 0)

  def jobPoints(jobIndex: Int) = career(jobIndex)

  def distanceFrom(job: JobClass) = {
    prerequisites(job).map {
      case (prerequisiteJob, jobLevel) => {
        val requiredPoints = jobPointMinima(jobLevel - 2)

        if (requiredPoints > jobPoints(prerequisiteJob))
          requiredPoints - jobPoints(prerequisiteJob)
        else
          0
      }
    }.sum
  }

  def prettyPrint {
    println("Experience: " + experiencePoints + jobs.map({ case (k, v) => "  " + k + ": " + v }).mkString(","))
  }

  def toStableSortableString = {
    val values = experiencePoints :: jobs.keys.toList.sorted.map { k => jobs(k) }

    values.mkString(",")
  }
}
