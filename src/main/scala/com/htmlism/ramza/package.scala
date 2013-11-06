package com.htmlism

package object ramza {
  type JobId = Int

  val jobPointMinimums = List(200, 400, 700, 1100, 1600, 2200, 3000)

  object Jobs extends Enumeration {
    type JobClass = Value

    val Squire, Knight, Monk, Geomancer, Archer, Thief, Dragoon, Ninja, Samurai, Dancer, Chemist, Priest, Wizard, Mystic, TimeMage, Arithmetician, Orator, Summoner, Bard, OnionKnight, Mime, DarkKnight = Value
  }
}
