package com.htmlism.ramza

import scala.collection.mutable

object Job {
  private implicit val registry = mutable.Map[String, Job]()

  def jobs = registry.values

  def dependencies(name: String) = registry(name)

  def tranisitiveDependencies(name: String): Map[String, Int] = {
    val job = registry(name)

    val newDeps = job.dependencies.keys.foldLeft(Map[String, Int]()) { (acc, e) => acc ++ tranisitiveDependencies(e) }

    job.dependencies ++ newDeps
  }

  def mergeDependencies(a: Map[Job, Int], b: Map[Job, Int]) = a ++ b

  val squire  = Job("Squire")
  val chemist = Job("Chemist")

  val knight    = Job("Knight").dependsOn(squire, 2)
  val monk      = Job("Monk").dependsOn(knight, 3)
  val geomancer = Job("Geomancer").dependsOn(monk, 4)

  val archer  = Job("Archer").dependsOn(squire, 2)
  val thief   = Job("Thief").dependsOn(archer, 3)
  val dragoon = Job("Dragoon").dependsOn(thief, 4)

  val priest = Job("Priest").dependsOn(chemist, 2)
  val mystic = Job("Mystic").dependsOn(priest, 3)
  val orator = Job("Orator").dependsOn(mystic, 3)

  val wizard = Job("Wizard").dependsOn(chemist, 2)
  val timeMage = Job("Time Mage").dependsOn(wizard, 3)
  val summoner = Job("Summoner").dependsOn(timeMage, 3)

  val ninja = Job("Ninja")
    .dependsOn(archer, 4)
    .dependsOn(thief, 5)
    .dependsOn(geomancer, 2)

  val samurai = Job("Samurai")
    .dependsOn(knight, 4)
    .dependsOn(monk, 5)
    .dependsOn(dragoon, 2)

  val arithmetician = Job("Arithmetician")
    .dependsOn(priest, 5)
    .dependsOn(wizard, 5)
    .dependsOn(mystic, 4)
    .dependsOn(timeMage, 4)

  val mime = Job("Mime")
    .dependsOn(squire, 8)
    .dependsOn(chemist, 8)
    .dependsOn(geomancer, 5)
    .dependsOn(dragoon, 5)
    .dependsOn(orator, 5)
    .dependsOn(summoner, 5)

  val dancer = Job("Dancer")
    .dependsOn(geomancer, 5)
    .dependsOn(dragoon, 5)

  val bard = Job("Bard")
    .dependsOn(orator, 5)
    .dependsOn(summoner, 5)

  val onionKnight = Job("Onion Knight")
    .dependsOn(squire, 6)
    .dependsOn(chemist, 6)

  val darkNight = Job("Dark Knight")
    .dependsOn(knight, 8)
    .dependsOn(wizard, 8)
    .dependsOn(geomancer, 8)
    .dependsOn(dragoon, 8)
    .dependsOn(samurai, 8)
    .dependsOn(ninja, 8)
}

case class Job(name: String, dependencies: Map[String, Int] = Map.empty)(implicit registry: mutable.Map[String, Job]) {
  registry(name) = this

  def dependsOn(that: Job, level: Int) = copy(dependencies = dependencies + (that.name -> level))
}
