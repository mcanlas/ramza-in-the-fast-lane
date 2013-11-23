package com.htmlism.ramza

import com.htmlism.ramza.Jobs._
import scala.annotation.tailrec

object Main extends App {
  def solveFor(jobClass: JobClass, i: Int) = {
    val (indexesByJob, prerequisitesTable)  = ZodiacWarrior dictionaryFor jobClass

    val seed = Set(
      Party(
        ZodiacWarrior toSolve jobClass,
        ZodiacWarrior toSolve jobClass,
        ZodiacWarrior toSolve jobClass,
        ZodiacWarrior toSolve jobClass
      )
    )

    gainExperience(i, seed)
  }

  @tailrec
  def gainExperience(i: Int, set: Set[Party], frontier: Set[Party] = Set.empty): Set[Party] = {
    if (i > 0) {
      val frontier = set.flatMap(x => x.gainExperience)
      println(i)

      if (frontier.isEmpty) throw new Exception("hello")

      val frontierByDistance = frontier.groupBy(_.anyDistanceFrom(DarkKnight))
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
}
