package com.htmlism.ramza

object SolverContext {
  def apply(jobClass: JobClass): SolverContext = {
    val prerequisitesForThisJob = prerequisites(jobClass)

    val indexesByPrerequisites = prerequisites(jobClass).keys.zipWithIndex.toMap
    val sortedJobs = indexesByPrerequisites.toSeq sortBy(_._2) map(_._1)

    val prerequisitesTable = ((sortedJobs :+ jobClass) map {
      j =>
        (sortedJobs map {
          prereq =>
            prerequisites(j).getOrElse(prereq, 0)
        }).toIndexedSeq
    }).toIndexedSeq

    val indexesByJob = indexesByPrerequisites + (jobClass -> prerequisitesForThisJob.size)

    SolverContext(jobClass, indexesByJob, prerequisitesTable, sortedJobs)
  }
}

case class SolverContext(solvingFor: JobClass, indexesByJob: Map[JobClass, Int], prerequisites: IndexedSeq[IndexedSeq[Int]], jobs: Seq[JobClass])
