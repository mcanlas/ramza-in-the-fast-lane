package com.htmlism.ramza

import scala.annotation.tailrec
import com.htmlism.ramza.ZodiacWarrior._

case class Party(characters: ZodiacWarrior*) {
  private def gainExperienceByJob(character: ZodiacWarrior, index: Int, job: Int) = {
    Party(
      characters.zipWithIndex.map {
        case (ally, allyIndex) => {
          val jpToGain = 8 + character.jobLevelVector(job) * 2 + character.level / 4

          if (allyIndex == index) {
            ally withExp(job, jpToGain)
          } else {
            ally withSharedJp(job, jpToGain)
          }
        }
      }: _*
    )
  }

  private def gainExperienceByCharacter(parties: List[Party], characterWithIndex: (ZodiacWarrior, Int))(implicit context: SolverContext) = {
    val (character, index) = characterWithIndex

    parties.flatMap { p =>
      character.availableJobsVector.toList.map { j =>
        p.gainExperienceByJob(character, index, j)
      }
    }
  }

  @tailrec
  private def gainExperienceRecursively(parties: List[Party], characters: Traversable[(ZodiacWarrior, Int)])(implicit context: SolverContext): List[Party] = characters.toList match {
    case head :: tail => {
      val partiesFromOneCharacter = gainExperienceByCharacter(parties, head)

      gainExperienceRecursively(partiesFromOneCharacter, tail)
    }
    case Nil => parties.map { p =>
      Party(p.characters.sortBy(_.toStableSortableString): _*)
    }
  }

  def gainExperience(implicit context: SolverContext) = gainExperienceRecursively(this :: Nil, characters.zipWithIndex)

  def anyDistanceFrom(job: Int)(implicit context: SolverContext) = characters.map(_.distanceFrom(job)).min
}
