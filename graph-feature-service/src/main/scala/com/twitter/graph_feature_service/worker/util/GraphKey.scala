package com.twitter.graph_feature_service.worker.util

import com.twitter.graph_feature_service.thriftscala.EdgeType
import com.twitter.graph_feature_service.thriftscala.EdgeType._

sealed trait GraphKey {

  def edgeType: EdgeType
}

sealed trait PartialValueGraph etwittertends GraphKey

/**
 * Follow Graphs
 */
object FollowingPartialValueGraph etwittertends PartialValueGraph {

  override def edgeType: EdgeType = Following
}

object FollowedByPartialValueGraph etwittertends PartialValueGraph {

  override def edgeType: EdgeType = FollowedBy
}

/**
 * Mutual Follow Graphs
 */
object MutualFollowPartialValueGraph etwittertends PartialValueGraph {

  override def edgeType: EdgeType = MutualFollow
}
