package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass
import scala.annotation.tailrec

case class Party(characters: ZodiacWarrior*) {
  private def gainExperienceByJob(parties: Parties, character: ZodiacWarrior, jobs: Traversable[JobClass]) = ???

  @tailrec
  private def gainExperienceByCharacter(parties: Parties, characters: Traversable[ZodiacWarrior]): Parties = characters match {
    case head :: tail => {
      val partiesFromOneCharacter = gainExperienceByJob(parties, head, ???)

      gainExperienceByCharacter(partiesFromOneCharacter, tail)
    }
    case Nil => parties
  }

  def gainExperience: Parties = gainExperienceByCharacter(this :: Nil, characters)
}
