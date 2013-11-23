package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass
import scala.annotation.tailrec

object ZodiacWarrior {
  type PrerequisiteTable = Vector[Vector[Int]]

  def dictionaryFor(jobClass: JobClass) = {
    val prerequisitesForThisJob = prerequisites(jobClass)

    val indexesByJob = (prerequisites(jobClass).keys.zipWithIndex.toList :+ (jobClass, prerequisitesForThisJob.size)).toMap
    val sortedJobs = indexesByJob.toList sortBy(_._2) map(_._1)

    val prerequisitesTable = (sortedJobs map {
      j =>
        (sortedJobs map {
          prereq =>
            prerequisites(j).getOrElse(prereq, 0)
        }).toVector
    }).toVector

    (indexesByJob, prerequisitesTable)
  }

  def toSolve(jobClass: JobClass) = {
    val size = prerequisites(jobClass).size

    ZodiacWarrior(jp = Vector.fill(size)(0))
  }
}

case class ZodiacWarrior(experiencePoints: Int = 100, private val jobs: Map[JobClass, Int] = Map.empty, private val jp: Vector[Int] = Vector()) {
  def withExp(job: JobClass, baseJpToGain: Int) = {
    val currentJp = jobPoints(job)
    val augmentedJpToGain = baseJpToGain * 3 / 2

    ZodiacWarrior(experiencePoints + 10, jobs + (job -> (currentJp + augmentedJpToGain)))
  }

  def withSharedJp(job: JobClass, baseJpToGain: Int) = {
    val currentJp = jobPoints(job)

    copy(jobs = jobs + (job -> (currentJp + baseJpToGain / 4)))
  }

  def level = if (experiencePoints > 99 * 100) 99 else experiencePoints / 100

  @tailrec
  final def jobLevel(job: JobClass, minimumsToCheck: List[Int] = jobPointMinima, level: Int = 1): Int = minimumsToCheck match {
    case jobPointMinimum :: remainingMinima if jobPoints(job) >= jobPointMinimum => jobLevel(job, remainingMinima, level + 1)
    case _ => level
  }

  def availableJobs = prerequisites.keys.filter({ jobName =>
    import Jobs._

    val special = prerequisites(DarkKnight).contains(jobName)

    special && prerequisites(jobName).forall({
      case (prerequisiteName, prerequisiteLevel) => jobLevel(prerequisiteName) >= prerequisiteLevel
    })
  })

  def jobPoints(job: JobClass) = jobs.getOrElse(job, 0)

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
