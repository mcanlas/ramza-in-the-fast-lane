package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass
import scala.annotation.tailrec

case class Party(characters: ZodiacWarrior*) {
  private def gainExperienceByJob = ???

  private def gainExperienceByCharacter(parties: Parties, character: ZodiacWarrior, jobs: Traversable[JobClass]) = parties.flatMap {
    p => jobs.toList.map {
      j => p
    }
  }

  @tailrec
  private def gainExperienceRecursively(parties: Parties, characters: Traversable[ZodiacWarrior]): Parties = characters.toList match {
    case head :: tail => {
      val partiesFromOneCharacter = gainExperienceByCharacter(parties, head, head.availableJobs)

      gainExperienceRecursively(partiesFromOneCharacter, tail)
    }
    case Nil => parties
  }

  def gainExperience: Parties = gainExperienceRecursively(this :: Nil, characters)
}
