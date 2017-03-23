package com.htmlism.ramza

import guru.nidi.graphviz.attribute.{Color, Shape, Style}
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Factory._
import guru.nidi.graphviz.model.{Label, Link, MutableGraph, MutableNode}

object GenerateJobGraph {
  def main(args: Array[String]): Unit = {
    val output = args(0)

    val registry = scala.collection.mutable.Map[String, MutableNode]()

    // initialize nodes
    Job.jobs.foreach { j =>
      val name = j.name
      val node = mutNode(name)

      registry(name) = node
    }

    // initialize edges
    Job.jobs.foreach { j =>
      Job.dependencies(j.name).dependencies.foreach { case (prereq, lvl) =>
        val freshGet = registry(j.name)
        val freshGetPreReq = registry(prereq)

//        val edge = Link.between(freshGetPreReq, freshGet) // .attrs().add("label", "asdf", "a", "b")

        val mutatedNode = freshGetPreReq.addLink(freshGet)

        registry(prereq) = mutatedNode
      }
    }

    val graph = mutGraph()
    graph.setDirected()

    for (n <- registry.values)
      graph.add(n)

    val file = new java.io.File(output)
    Graphviz.fromGraph(graph).renderToFile(file)
  }
}
