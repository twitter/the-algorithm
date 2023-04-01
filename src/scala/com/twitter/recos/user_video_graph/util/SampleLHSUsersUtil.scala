package com.twitter.recos.user_video_graph.util

import com.twitter.graphjet.bipartite.MultiSegmentIterator
import com.twitter.graphjet.bipartite.api.BipartiteGraph
import com.twitter.graphjet.bipartite.segment.BipartiteGraphSegment
import java.util.Random
import scala.collection.mutable.ListBuffer

object SampleLHSUsersUtil {
  // sample userId nodes
  def sampleLHSUsers(
    maskedTweetId: Long,
    maxNumSamplesPerNeighbor: Int,
    bipartiteGraph: BipartiteGraph
  ): Seq[Long] = {
    val sampledUserIdsIterator = bipartiteGraph
      .getRandomRightNodeEdges(
        maskedTweetId,
        maxNumSamplesPerNeighbor,
        new Random(System.currentTimeMillis)).asInstanceOf[MultiSegmentIterator[
        BipartiteGraphSegment
      ]]

    val userIds = new ListBuffer[Long]()
    if (sampledUserIdsIterator != null) {
      while (sampledUserIdsIterator.hasNext) {
        val leftNode = sampledUserIdsIterator.nextLong()
        // If a user likes too many things, we risk including spammy behavior.
        if (bipartiteGraph.getLeftNodeDegree(leftNode) < 100)
          userIds += leftNode
      }
    }
    userIds
  }
}
