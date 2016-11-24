package com.htmlism.ramza

import org.specs2.mutable.Specification

class PartySpec extends Specification {
  "An empty party" should {
    "yield one solution" in {
      implicit val context = SolverContext(Squire)

      Party.empty.gainExperience.size === 1
    }
  }

  "Solving for Onion Knight" should {
    implicit val context = SolverContext(OnionKnight)

    "yield two solutions after one iteration" in {
      val seed = Party(Seq(
        ZodiacWarrior(OnionKnight),
        ZodiacWarrior(OnionKnight)
      ))

      seed.gainExperience.size === 2
    }.pendingUntilFixed
  }
}
