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
    val SolverContext(_, indexes, table, _) = ZodiacWarrior solveFor Squire
    val size = indexes.size

    "have one job" in { size must beEqualTo(1) }
    "have a prerequisite table of one squared" in { table.length === size && table.forall(_.length === size - 1) }
    "only job (self) has no prerequisites" in { table(0).isEmpty }
  }

  "Dictionary for Monk" should {
    val SolverContext(_, indexes, table, _) = ZodiacWarrior solveFor Monk
    val size = indexes.size
    val monkRequirements = table(indexes(Monk))

    "have three jobs" in { size must beEqualTo(3) }
    "have a prerequisite table of three squared" in { table.length === size && table.forall(_.length === size - 1) }
    "require 3 for Knight" in { monkRequirements(indexes(Knight)) === 3 }
    "require 2 for Squire" in { monkRequirements(indexes(Squire)) === 2 }
  }

  "Job levels" should {
    implicit val context = ZodiacWarrior solveFor Squire

    "be two for a level two squire solving for knight" in (ZodiacWarrior toSolve Knight withJp (Squire, 200)).jobLevelVector(context.indexesByJob(Squire)) === 2
  }

  "Available jobs" should {
    implicit val context = ZodiacWarrior solveFor Knight

    "have one when solving for Squire" in (ZodiacWarrior toSolve Knight availableJobsVector).length === 1
    "have two when solving for Knight" in (ZodiacWarrior toSolve Knight withJp (Squire, 200) availableJobsVector).length === 2
  }

  "Experience gain" should {
    implicit val SolverContext(_, indexes, table, _) = ZodiacWarrior solveFor Knight

    val character = ZodiacWarrior toSolve Knight
    val characterWithExperience = character withExp(0, 2)

    "have the correct experience" in { characterWithExperience.experiencePoints === 110 }
    "have the correct job points" in { characterWithExperience.jobPoints(0) === 3 }
  }

  "Job points gain" should {
    implicit val SolverContext(_, indexes, table, _) = ZodiacWarrior solveFor Knight

    val character = ZodiacWarrior toSolve Knight
    val characterWithJobPoints = character withSharedJp(0, 4)

    "have the correct job points" in { characterWithJobPoints.jobPoints(0) === 1 }
  }
}
