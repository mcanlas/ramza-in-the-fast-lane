package com.htmlism

package object ramza {
  val jobPointMinima = Seq(200, 400, 700, 1100, 1600, 2200, 3000)

  sealed trait JobClass

  object Squire extends JobClass
  object Knight extends JobClass
  object Monk extends JobClass
  object Geomancer extends JobClass
  object Archer extends JobClass
  object Thief extends JobClass
  object Dragoon extends JobClass
  object Ninja extends JobClass
  object Samurai extends JobClass
  object Dancer extends JobClass
  object Chemist extends JobClass
  object Priest extends JobClass
  object Wizard extends JobClass
  object Mystic extends JobClass
  object TimeMage extends JobClass
  object Arithmetician extends JobClass
  object Orator extends JobClass
  object Summoner extends JobClass
  object Bard extends JobClass
  object OnionKnight extends JobClass
  object Mime extends JobClass
  object DarkKnight extends JobClass

  val prerequisites: Map[JobClass, Map[JobClass, Int]] = Map(
    Squire        -> Map(),
    Chemist       -> Map(),
    Knight        -> Map(Squire -> 2),
    Archer        -> Map(Squire -> 2),
    Priest        -> Map(Chemist -> 2),
    Wizard        -> Map(Chemist -> 2),
    Monk          -> Map(Knight -> 3, Squire -> 2),
    Thief         -> Map(Squire -> 2, Archer -> 3),
    Mystic        -> Map(Priest -> 3, Chemist -> 2),
    TimeMage      -> Map(Chemist -> 2, Wizard -> 3),
    Geomancer     -> Map(Squire -> 2, Knight -> 3, Monk -> 4),
    Dragoon       -> Map(Thief -> 4, Squire -> 2, Archer -> 3),
    Orator        -> Map(Mystic -> 3, Chemist -> 2, Priest -> 3),
    Summoner      -> Map(TimeMage -> 3, Wizard -> 3, Chemist -> 2),
    Ninja         -> Map(Squire -> 2, Archer -> 4, Thief -> 5, Monk -> 4, Knight -> 3, Geomancer -> 2),
    Samurai       -> Map(Monk -> 5, Knight -> 4, Dragoon -> 2, Squire -> 2, Archer -> 3, Thief -> 4),
    Arithmetician -> Map(Mystic -> 3, Chemist -> 2, TimeMage -> 3, Wizard -> 5, Priest -> 5),
    Dancer        -> Map(Geomancer -> 5, Dragoon -> 5, Knight -> 3, Monk -> 4, Thief -> 4, Archer -> 3, Squire -> 2),
    Bard          -> Map(TimeMage -> 3, Wizard -> 3, Orator -> 5, Priest -> 3, Chemist -> 2, Summoner -> 5, Mystic -> 3),
    Mime          -> Map(Thief -> 4, TimeMage -> 3, Priest -> 3, Wizard -> 3, Orator -> 5, Archer -> 3, Geomancer -> 5, Squire -> 8, Summoner -> 5, Chemist -> 8, Monk -> 4, Mystic -> 3, Dragoon -> 5, Knight -> 3),
    OnionKnight   -> Map(Squire -> 6, Chemist -> 6),
    DarkKnight    -> Map(Wizard -> 8, Archer -> 4, Samurai -> 8, Thief -> 5, Geomancer -> 8, Squire -> 8, Ninja -> 8, Monk -> 5, Dragoon -> 8, Knight -> 8, Chemist -> 8)
  )

  def jobLevel(jobPoints: Int, minima: Seq[Int] = jobPointMinima): Int =
    jobPointMinima.iterator.takeWhile(min => jobPoints >= min).size + 1
}
