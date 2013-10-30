package com.htmlism.ramza

object Main extends App {
  val SQUIRE = 0
  val CHEMIST = 1

  val partyJp = List(
    List(0, 0),
    List(0, 0),
    List(0, 0),
    List(0, 0),
    List(0, 0)
  )

  for (i <- 1 to 30) {
    for (j <- 0 to 4) {

    }
  }

  println("hello")

  def allocateJp(partyJp: List, remainingIterations: Int) {
    if (remainingIterations == 0) {
      println(partyJp)
      println("done!")
    } else {
      allocateJp(partyJp, remainingIterations - 1)
    }
  }

  def allocateJpChar(partyJp: List, remainingCharacters: Int): List = {
    if (remainingCharacters == 0) {
      return partyJp
    } else {

    }
  }
}