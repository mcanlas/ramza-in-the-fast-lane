package com.htmlism.ramza

case class SolverContext(solvingFor: JobClass, indexesByJob: Map[JobClass, Int], prerequisites: Vector[Vector[Int]], jobs: List[JobClass])
