package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass
import scala.annotation.tailrec

object ZodiacWarrior {
  def toSolve(jobClass: JobClass) = {
    val size = prerequisites(jobClass).size

    ZodiacWarrior(jp = Vector.fill(size)(0))
  }
}

case class ZodiacWarrior(private val experiencePoints: Int = 100, private val jobs: Map[JobClass, Int] = Map.empty, private val jp: Vector[Int] = Vector()) {
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
    experiencePoints.toString + jobs.keys.toList.sorted.map { k => k.toString + jobs(k) }.mkString
  }
}
