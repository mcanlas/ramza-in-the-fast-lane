package com.htmlism.ramza

import scala.annotation.tailrec

object Main {
  /**
    * @param i The number of iterations to run
    * @return
    */
  def solveFor(jobClass: JobClass, i: Int): Set[Party] = {
    implicit val context = SolverContext(jobClass)

    gainExperience(i, defaultSeed(jobClass))
  }

  /**
    * @param i The number of iterations to run
    * @return
    */
  def solveFor(jobClass: JobClass, i: Int, seed: Set[Party]): Set[Party] = {
    implicit val context = SolverContext(jobClass)

    gainExperience(i, seed)
  }

  /**
    * @param i The number of iterations to run
    * @return
    */
  @tailrec
  def gainExperience(i: Int, set: Set[Party], frontier: Set[Party] = Set.empty)(implicit context: SolverContext): Set[Party] = {
    val SolverContext(targetJobClass, indexesByJob, _, _) = context

    if (i > 0) {
      val frontier = set.flatMap(_.gainExperience)
      println(i)

      if (frontier.isEmpty)
        throw new Exception("hello")

      val frontierByDistance = frontier.groupBy(_.anyDistanceFrom(indexesByJob(targetJobClass)))
      val trimmedFrontier = frontierByDistance(frontierByDistance.keys.min)

      if (trimmedFrontier.isEmpty)
        throw new Exception("zoo!!")

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
    Party(Seq(
      ZodiacWarrior(job),
      ZodiacWarrior(job),
      ZodiacWarrior(job),
      ZodiacWarrior(job)
    ))
  )
}
