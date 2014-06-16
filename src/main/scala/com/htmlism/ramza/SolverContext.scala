package com.htmlism.ramza

object SolverContext {
  def apply(jobClass: JobClass): SolverContext = {
    val prerequisitesForThisJob = prerequisites(jobClass)

    val indexesByPrerequisites = prerequisites(jobClass).keys.zipWithIndex.toList.toMap
    val sortedJobs = indexesByPrerequisites.toList sortBy(_._2) map(_._1)

    val prerequisitesTable = ((sortedJobs :+ jobClass) map {
      j =>
        (sortedJobs map {
          prereq =>
            prerequisites(j).getOrElse(prereq, 0)
        }).toVector
    }).toVector

    val indexesByJob = indexesByPrerequisites + (jobClass -> prerequisitesForThisJob.size)

    SolverContext(jobClass, indexesByJob, prerequisitesTable, sortedJobs)
  }
}

case class SolverContext(solvingFor: JobClass, indexesByJob: Map[JobClass, Int], prerequisites: Vector[Vector[Int]], jobs: List[JobClass])
