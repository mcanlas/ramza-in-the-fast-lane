package com.htmlism.ramza

import scala.annotation.tailrec

case class Party(characters: ZodiacWarrior*) {
  private def gainExperienceByJob = ???

  private def gainExperienceByCharacter(parties: Parties, characterWithIndex: (ZodiacWarrior, Int)) = {
    val (character, index) = characterWithIndex

    parties.flatMap { p => character.availableJobs.toList.map { j => p } }
  }

  @tailrec
  private def gainExperienceRecursively(parties: Parties, characters: Traversable[(ZodiacWarrior, Int)]): Parties = characters.toList match {
    case head :: tail => {
      val partiesFromOneCharacter = gainExperienceByCharacter(parties, head)

      gainExperienceRecursively(partiesFromOneCharacter, tail)
    }
    case Nil => parties
  }

  def gainExperience: Parties = gainExperienceRecursively(this :: Nil, characters.zipWithIndex)
}
