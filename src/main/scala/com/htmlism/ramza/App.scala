package com.htmlism.ramza

import scala.annotation.tailrec

object Main extends App {
  def defaultParty = Set(
    Party(
      ZodiacWarrior(),
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

      gainExperience(i - 1, set ++ frontier, frontier -- set)
    } else {
      frontier
    }
  }
}
