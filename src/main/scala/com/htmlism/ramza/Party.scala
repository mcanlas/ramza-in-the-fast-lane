package com.htmlism.ramza

import scala.annotation.tailrec
import com.htmlism.ramza.Jobs.JobClass

case class Party(characters: ZodiacWarrior*) {
  private def gainExperienceByJob(party: Party, character: ZodiacWarrior, index: Int, job: JobClass) = {
    Party(
      party.characters.zipWithIndex.map {
        case (ally, allyIndex) => {
          val currentJp = ally.jobPoints(job)

          val jpToGain = 8 + character.jobLevel(job) * 2 + character.level / 4
          val augmentedJpToGain = jpToGain * 3 / 2

          if (allyIndex == index) {
            ZodiacWarrior(ally.experiencePoints + 10, character.jobs + (job -> (currentJp + augmentedJpToGain)))
          } else {
            ally.copy(jobs = ally.jobs + (job -> (currentJp + jpToGain / 4)))
          }
        }
      }: _*
    )
  }

  private def gainExperienceByCharacter(parties: Parties, characterWithIndex: (ZodiacWarrior, Int)) = {
    val (character, index) = characterWithIndex

    parties.flatMap { p =>
      character.availableJobs.toList.map { j =>
        gainExperienceByJob(p, character, index, j)
      }
    }
  }

  @tailrec
  private def gainExperienceRecursively(parties: Parties, characters: Traversable[(ZodiacWarrior, Int)]): Parties = characters.toList match {
    case head :: tail => {
      val partiesFromOneCharacter = gainExperienceByCharacter(parties, head)

      gainExperienceRecursively(partiesFromOneCharacter, tail)
    }
    case Nil => parties
  }

  def gainExperience = gainExperienceRecursively(Set(this), characters.zipWithIndex)

  def prettyPrint {
    characters.foreach(c => c.prettyPrint)
    println("--")
  }
}
