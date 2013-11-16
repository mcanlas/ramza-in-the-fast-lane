package com.htmlism.ramza

import com.htmlism.ramza.Jobs.DarkKnight
import scala.annotation.tailrec

object Main extends App {
  def defaultParty = Set(
    Party(
      ZodiacWarrior(),
      ZodiacWarrior(),
      ZodiacWarrior(),
      ZodiacWarrior()
    )
  )

  @tailrec
  def gainExperience(i: Int, set: Parties = defaultParty, frontier: Parties = Set.empty): Parties = {
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
