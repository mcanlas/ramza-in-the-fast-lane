package com.htmlism.ramza

import com.htmlism.ramza.Jobs.JobClass

case class SolverContext(solvingFor: JobClass, indexesByJob: Map[JobClass, Int], prerequisites: Vector[Vector[Int]], jobs: List[JobClass])
