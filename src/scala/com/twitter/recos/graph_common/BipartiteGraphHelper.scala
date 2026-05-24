package com.twitter.recos.graph_common

import com.twitter.graphjet.algorithms.TweetIDMask
import com.twitter.graphjet.bipartite.api.BipartiteGraph
import scala.collection.mutable.ListBuffer

/*
 * The helper class encodes and decodes tweet ids with tweetypie's card information
 * when querying recos salsa library. Inside salsa library, all tweet ids are
 * encoded with card information for the purpose of inline filtering.
 */
class BipartiteGraphHelper(graph: BipartiteGraph) {
  private val tweetIDMask = new TweetIDMask

  def getLeftNodeEdges(leftNode: Long): Seq[(Long, Byte)] = {
    val iterator = graph.getLeftNodeEdges(leftNode)

    val edges: ListBuffer[(Long, Byte)] = ListBuffer()
    if (iterator != null) {
      while (iterator.hasNext) {
        val node = iterator.nextLong()
        val engagementType = iterator.currentEdgeType()
        edges += ((tweetIDMask.restore(node), engagementType))
      }
    }
    edges.reverse.distinct // Most recent edges first, no duplications
  }

  def getRightNodeEdges(rightNode: Long): Seq[Long] = {
    val iterator = graph.getRightNodeEdges(rightNode)
    val leftNodes: ListBuffer[Long] = ListBuffer()
    if (iterator != null) {
      while (iterator.hasNext) {
        leftNodes += iterator.nextLong()
      }
    }

    leftNodes.reverse.distinct // Most recent edges first, no duplications
  }
}
