package com.htmlism.ramza

import org.specs2.mutable.Specification
import com.htmlism.ramza.Jobs._

class ZodiacWarriorSpec extends Specification {
  "Dictionary for Squire" should {
    val SolverContext(_, indexes, table, _) = SolverContext(Squire)
    val size = indexes.size

    "have one job" in { size must beEqualTo(1) }
    "have a prerequisite table of one squared" in { table.length === size && table.forall(_.length === size - 1) }
    "only job (self) has no prerequisites" in { table(0).isEmpty }
  }

  "Dictionary for Monk" should {
    val SolverContext(_, indexes, table, _) = SolverContext(Monk)
    val size = indexes.size
    val monkRequirements = table(indexes(Monk))

    "have three jobs" in { size must beEqualTo(3) }
    "have a prerequisite table of three squared" in { table.length === size && table.forall(_.length === size - 1) }
    "require 3 for Knight" in { monkRequirements(indexes(Knight)) === 3 }
    "require 2 for Squire" in { monkRequirements(indexes(Squire)) === 2 }
  }

  "Job levels" should {
    implicit val context = SolverContext(Squire)

    "be two for a level two squire solving for knight" in (ZodiacWarrior(Knight) withJp (Squire, 200)).jobLevel(context.indexesByJob(Squire)) === 2
  }

  "Available jobs" should {
    implicit val context = SolverContext(Knight)

    "have one when solving for Squire" in (ZodiacWarrior(Knight) availableJobs).size === 1
    "have two when solving for Knight" in (ZodiacWarrior(Knight) withJp (Squire, 200) availableJobs).size === 2
  }

  "Experience gain" should {
    implicit val SolverContext(_, indexes, table, _) = SolverContext(Knight)

    val character = ZodiacWarrior(Knight)
    val characterWithExperience = character withExp(0, 2)

    "have the correct experience" in { characterWithExperience.experiencePoints === 110 }
    "have the correct job points" in { characterWithExperience.jobPoints(0) === 3 }
  }

  "Job points gain" should {
    implicit val SolverContext(_, indexes, table, _) = SolverContext(Knight)

    val character = ZodiacWarrior(Knight)
    val characterWithJobPoints = character withSharedJp(0, 4)

    "have the correct job points" in { characterWithJobPoints.jobPoints(0) === 1 }
  }
}
