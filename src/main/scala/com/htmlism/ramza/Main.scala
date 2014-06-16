package com.htmlism.ramza

import scala.annotation.tailrec

object Main {
  def solveFor(jobClass: JobClass, i: Int, seed: Set[Party] = Set.empty) = {
    implicit val context = SolverContext(jobClass)

    gainExperience(i, if (seed.isEmpty) defaultSeed(jobClass) else seed)
  }

  @tailrec
  def gainExperience(i: Int, set: Set[Party], frontier: Set[Party] = Set.empty)(implicit context: SolverContext): Set[Party] = {
    val SolverContext(jobClass, indexesByJob, _, _) = context

    if (i > 0) {
      val frontier = set.flatMap(x => x.gainExperience)
      println(i)

      if (frontier.isEmpty) throw new Exception("hello")

      val frontierByDistance = frontier.groupBy(_.anyDistanceFrom(indexesByJob(jobClass)))
      val trimmedFrontier = frontierByDistance(frontierByDistance.keys.min)

      if (trimmedFrontier.isEmpty) throw new Exception("zoo!!")

      println("Frontier size is: " + frontier.size)
      println("Trimmed frontier size is: " + trimmedFrontier.size)
      println("Search space reduction is: " + (frontier.size - trimmedFrontier.size).asInstanceOf[Double] / frontier.size * 100)
      println("\n\n\n")

      gainExperience(i - 1, trimmedFrontier, trimmedFrontier)
    } else {
      frontier
    }
  }

  private def defaultSeed(job: JobClass) = Set(
    Party(
      ZodiacWarrior(job),
      ZodiacWarrior(job),
      ZodiacWarrior(job),
      ZodiacWarrior(job)
    )
  )
}
