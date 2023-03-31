package com.twitter.recos.user_video_graph.util

import com.twitter.graphjet.bipartite.MultiSegmentIterator
import com.twitter.graphjet.bipartite.api.BipartiteGraph
import com.twitter.graphjet.bipartite.segment.BipartiteGraphSegment
import scala.collection.mutable.ListBuffer

object FetchRHSTweetsUtil {
  // get RHS tweets given LHS users
  def fetchRHSTweets(
    userIds: Seq[Long],
    bipartiteGraph: BipartiteGraph
  ): Seq[Long] = {
    userIds.distinct
      .flatMap { userId =>
        val tweetIdsIterator = bipartiteGraph
          .getLeftNodeEdges(userId).asInstanceOf[MultiSegmentIterator[BipartiteGraphSegment]]

        val tweetIds = new ListBuffer[Long]()
        if (tweetIdsIterator != null) {
          while (tweetIdsIterator.hasNext) {
            val rightNode = tweetIdsIterator.nextLong()
            tweetIds += rightNode
          }
        }
        tweetIds.distinct
      }
  }
}
