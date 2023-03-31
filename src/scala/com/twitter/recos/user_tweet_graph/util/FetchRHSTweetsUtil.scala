package com.twitter.recos.user_tweet_graph.util

import com.twitter.graphjet.bipartite.MultiSegmentIterator
import com.twitter.graphjet.bipartite.api.BipartiteGraph
import com.twitter.graphjet.bipartite.segment.BipartiteGraphSegment
import scala.collection.mutable.ListBuffer
import com.twitter.recos.util.Action

object FetchRHSTweetsUtil {
  // get RHS tweets given LHS users
  def fetchRHSTweets(
    userIds: Seq[Long],
    bipartiteGraph: BipartiteGraph,
    allowedActions: Set[Action.Value]
  ): Seq[Long] = {
    val allowedActionStrings = allowedActions.map(_.toString)
    userIds.distinct
      .flatMap { userId =>
        val tweetIdsIterator = bipartiteGraph
          .getLeftNodeEdges(userId).asInstanceOf[MultiSegmentIterator[BipartiteGraphSegment]]

        val tweetIds = new ListBuffer[Long]()
        if (tweetIdsIterator != null) {
          while (tweetIdsIterator.hasNext) {
            val rightNode = tweetIdsIterator.nextLong()
            val edgeType = tweetIdsIterator.currentEdgeType()
            if (allowedActionStrings.contains(UserTweetEdgeTypeMask(edgeType).toString))
              tweetIds += rightNode
          }
        }
        tweetIds.distinct
      }
  }
}
