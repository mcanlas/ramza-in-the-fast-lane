package com.htmlism.ramza

import scala.annotation.tailrec
import com.htmlism.ramza.Jobs.JobClass

case class Party(characters: ZodiacWarrior*) {
  private def gainExperienceByJob(party: Party, character: ZodiacWarrior, index: Int, job: JobClass) = {
    Party(
      party.characters.zipWithIndex.map {
        case (ally, allyIndex) => {
          val jpToGain = 8 + character.jobLevel(job) * 2 + character.level / 4

          if (allyIndex == index) {
            ally withExp(job, jpToGain)
          } else {
            ally withSharedJp(job, jpToGain)
          }
        }
      }: _*
    )
  }

  private def gainExperienceByCharacter(parties: List[Party], characterWithIndex: (ZodiacWarrior, Int)) = {
    val (character, index) = characterWithIndex

    parties.flatMap { p =>
      character.availableJobs.toList.map { j =>
        gainExperienceByJob(p, character, index, j)
      }
    }
  }

  @tailrec
  private def gainExperienceRecursively(parties: List[Party], characters: Traversable[(ZodiacWarrior, Int)]): List[Party] = characters.toList match {
    case head :: tail => {
      val partiesFromOneCharacter = gainExperienceByCharacter(parties, head)

      gainExperienceRecursively(partiesFromOneCharacter, tail)
    }
    case Nil => parties.map { p =>
      Party(p.characters.sortBy(_.hashCode): _*)
    }
  }

  def gainExperience = gainExperienceRecursively(this :: Nil, characters.zipWithIndex)

  def anyDistanceFrom(job: JobClass) = characters.map(_.distanceFrom(job)).min

  def prettyPrint {
    characters.foreach(c => c.prettyPrint)
    println("--")
  }
}
