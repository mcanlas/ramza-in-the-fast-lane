package com.htmlism.ramza

object Party {
  val empty = Party(Nil)
}

case class Party(characters: Iterable[ZodiacWarrior]) {
  private def gainExperienceByJob(character: ZodiacWarrior, index: Int, job: Int) = {
    Party(
      characters.iterator.zipWithIndex.map {
        case (ally, allyIndex) => {
          val jpToGain = 8 + jobLevel(character.career(job)) * 2 + character.level / 4

          if (allyIndex == index)
            ally withExp(job, jpToGain)
          else
            ally withSharedJp(job, jpToGain)
        }
      }.toSeq
    )
  }

  private def gainExperienceByCharacter(parties: TraversableOnce[Party], characterWithIndex: (ZodiacWarrior, Int))(implicit context: SolverContext) = {
    val (character, index) = characterWithIndex

    parties.flatMap { p =>
      character.availableJobs.map { j =>
        p.gainExperienceByJob(character, index, j)
      }
    }
  }

  private def gainExperienceRecursively(
      parties: TraversableOnce[Party],
      charactersToProcess: TraversableOnce[(ZodiacWarrior, Int)])(implicit context: SolverContext): TraversableOnce[Party] =
    charactersToProcess.foldLeft(parties)((ps, c) => gainExperienceByCharacter(ps, c))

  def gainExperience(implicit context: SolverContext): TraversableOnce[Party] = gainExperienceRecursively(Seq(this), characters.zipWithIndex)

  def anyDistanceFrom(job: Int)(implicit context: SolverContext): Int = characters.map(_.distanceFrom(job)).min
}
