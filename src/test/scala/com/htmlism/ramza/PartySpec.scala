package com.htmlism.ramza

import org.specs2.mutable.Specification
import com.htmlism.ramza.Jobs._

class PartySpec extends Specification {
  "An empty party" should {
    "yield one solution" in {
      implicit val context = SolverContext(Squire)

      Party().gainExperience.size === 1
    }
  }

  "Solving for Onion Knight" should {
    implicit val context = SolverContext(OnionKnight)

    "yield two solutions after one iteration" in {
      val seed = Party(
        ZodiacWarrior(OnionKnight),
        ZodiacWarrior(OnionKnight)
      )

      seed.gainExperience.length === 2
    }.pendingUntilFixed
  }
}
