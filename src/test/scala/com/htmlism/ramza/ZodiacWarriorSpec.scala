package com.htmlism.ramza

import org.specs2.mutable.Specification
import com.htmlism.ramza.Jobs._

class ZodiacWarriorSpec extends Specification {
  "Zodiac Warrior" should {
    "start with 100 experience" in {
      ZodiacWarrior().experiencePoints must beEqualTo(100)
    }
  }

  "Dictionary for Squire" should {
    val (indexes, table) = ZodiacWarrior dictionaryFor Squire
    val size = indexes.size

    "have one job" in { size must beEqualTo(1) }
    "have a prerequisite table of one squared" in { table.length == size && table.forall(_.length == size) }
    "start at zero" in { table(0)(0) == 0 }
  }

  "Dictionary for Monk" should {
    val (indexes, table) = ZodiacWarrior dictionaryFor Monk
    val size = indexes.size
    val monkRequirements = table(indexes(Monk))

    "have three jobs" in { size must beEqualTo(3) }
    "have a prerequisite table of three squared" in { table.length == size && table.forall(_.length == size) }
    "require 3 for Knight" in { monkRequirements(indexes(Knight)) == 3 }
    "require 2 for Squire" in { monkRequirements(indexes(Squire)) == 2 }
  }

  "Available jobs" should {
    "have one when solving for Squire" in { (ZodiacWarrior toSolve Squire availableJobsVector).length == 1 }
    "have two when solving for Knight" in { (ZodiacWarrior toSolve Knight availableJobsVector).length == 2 }
  }

  "Experience gain" should {
    implicit val (indexes, table) = ZodiacWarrior dictionaryFor Knight
    val character = ZodiacWarrior toSolve Knight
    val characterWithExperience = character withExp(0, 2)

    "have the correct experience" in { characterWithExperience.experiencePoints == 110 }
    "have the correct job points" in { characterWithExperience.jobPoints(0) == 3 }
  }

  "Job points gain" should {
    implicit val (indexes, table) = ZodiacWarrior dictionaryFor Knight
    val character = ZodiacWarrior toSolve Knight
    val characterWithJobPoints = character withSharedJp(0, 4)

    "have the correct job points" in { characterWithJobPoints.jobPoints(0) == 1 }
  }
}
