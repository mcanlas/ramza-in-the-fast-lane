package com.htmlism

package object ramza {
  type JobId = Int

  val jobPointMinimums = List(200, 400, 700, 1100, 1600, 2200, 3000)

  object Jobs extends Enumeration {
    type JobClass = Value

    val Squire, Knight, Monk, Geomancer, Archer, Thief, Dragoon, Ninja, Samurai, Dancer, Chemist, Priest, Wizard, Mystic, TimeMage, Arithmetician, Orator, Summoner, Bard, OnionKnight, Mime, DarkKnight = Value
  }

  import Jobs._

  val emptyCareer = Map(
    Squire        -> 0,
    Chemist       -> 0,
    Knight        -> 0,
    Archer        -> 0,
    Priest        -> 0,
    Wizard        -> 0,
    Monk          -> 0,
    Thief         -> 0,
    Mystic        -> 0,
    TimeMage      -> 0,
    Geomancer     -> 0,
    Dragoon       -> 0,
    Orator        -> 0,
    Summoner      -> 0,
    Ninja         -> 0,
    Samurai       -> 0,
    Arithmetician -> 0,
    Dancer        -> 0,
    Bard          -> 0,
    Mime          -> 0,
    OnionKnight   -> 0,
    DarkKnight    -> 0
  )
}
