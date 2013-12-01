package com.htmlism.ramza

import scala.annotation.tailrec
import com.htmlism.ramza.Jobs.JobClass
import com.htmlism.ramza.ZodiacWarrior._

case class Party(characters: ZodiacWarrior*) {
  private def gainExperienceByJob(character: ZodiacWarrior, index: Int, job: JobClass) = {
    Party(
      characters.zipWithIndex.map {
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

  private def gainExperienceByCharacter(parties: List[Party], characterWithIndex: (ZodiacWarrior, Int))(implicit prerequisites: PrerequisiteTable) = {
    val (character, index) = characterWithIndex

    parties.flatMap { p =>
      character.availableJobs.toList.map { j =>
        p.gainExperienceByJob(character, index, j)
      }
    }
  }

  @tailrec
  private def gainExperienceRecursively(parties: List[Party], characters: Traversable[(ZodiacWarrior, Int)])(implicit prerequisites: PrerequisiteTable): List[Party] = characters.toList match {
    case head :: tail => {
      val partiesFromOneCharacter = gainExperienceByCharacter(parties, head)

      gainExperienceRecursively(partiesFromOneCharacter, tail)
    }
    case Nil => parties.map { p =>
      Party(p.characters.sortBy(_.toStableSortableString): _*)
    }
  }

  def gainExperience(implicit prerequisites: PrerequisiteTable) = gainExperienceRecursively(this :: Nil, characters.zipWithIndex)

  def anyDistanceFrom(job: JobClass) = characters.map(_.distanceFrom(job)).min

  def prettyPrint {
    characters.foreach(c => c.prettyPrint)
    println("--")
  }
}
