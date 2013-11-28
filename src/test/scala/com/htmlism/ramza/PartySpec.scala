package com.htmlism.ramza

import org.specs2.mutable.Specification

class PartySpec extends Specification {
  "An empty party" should {
    "yield one solution" in {
      Party().gainExperience.size == 1
    }
  }
}
