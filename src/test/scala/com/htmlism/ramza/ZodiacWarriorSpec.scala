package com.htmlism.ramza

import org.specs2.mutable.Specification

class ZodiacWarriorSpec extends Specification {
  "Zodiac Warrior" should {
    "start with 100 experience" in {
      ZodiacWarrior().experiencePoints must beEqualTo(100)
    }
  }
}
