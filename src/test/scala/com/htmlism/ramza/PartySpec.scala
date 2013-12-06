package com.htmlism.ramza

import org.specs2.mutable.Specification
import com.htmlism.ramza.Jobs._

class PartySpec extends Specification {
  "An empty party" should {
    "yield one solution" in {
      implicit val (_, prerequisitesTable) = ZodiacWarrior dictionaryFor Squire

      Party().gainExperience.size === 1
    }
  }
}
