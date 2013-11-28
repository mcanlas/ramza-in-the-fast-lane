package com.htmlism.ramza

import org.specs2.mutable.Specification
import com.htmlism.ramza.Jobs._

class PartySpec extends Specification {
  "An empty party" should {
    "yield one solution" in {
      Party().gainExperience.size == 1
    }
  }

  "A party of one" should {
    "incorrectly yield two solutions" in {
      Party(ZodiacWarrior toSolve Squire).gainExperience.length == 2
    }
  }
}
