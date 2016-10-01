package com.htmlism.ramza

import scala.annotation.tailrec

object Party {
  val empty = Party(Nil)
}

case class Party(characters: List[ZodiacWarrior]) {
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
      }
    )
  }

  private def gainExperienceByCharacter(parties: Seq[Party], characterWithIndex: (ZodiacWarrior, Int))(implicit context: SolverContext) = {
    val (character, index) = characterWithIndex

    parties.flatMap { p =>
      character.availableJobsVector.toList.map { j =>
        p.gainExperienceByJob(character, index, j)
      }
    }
  }

  @tailrec
  private def gainExperienceRecursively(parties: Seq[Party], characters: List[(ZodiacWarrior, Int)])(implicit context: SolverContext): Seq[Party] = characters match {
    case head :: tail => {
      val partiesAfterOneCharacter = gainExperienceByCharacter(parties, head)

      gainExperienceRecursively(partiesAfterOneCharacter, tail)
    }
    case Nil => parties.map { p =>
      Party(p.characters.sortBy(_.toStableSortableString))
    }
  }

  def gainExperience(implicit context: SolverContext): Seq[Party] = gainExperienceRecursively(this :: Nil, characters.zipWithIndex)

  def anyDistanceFrom(job: Int)(implicit context: SolverContext): Int = characters.map(_.distanceFrom(job)).min
}
