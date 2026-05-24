package com.twitter.graph_feature_service.worker.util

import com.twitter.graph_feature_service.thriftscala.EdgeType
import com.twitter.graph_feature_service.thriftscala.EdgeType._

sealed trait GraphKey {

  def edgeType: EdgeType
}

sealed trait PartialValueGraph extends GraphKey

/**
 * Follow Graphs
 */
object FollowingPartialValueGraph extends PartialValueGraph {

  override def edgeType: EdgeType = Following
}

object FollowedByPartialValueGraph extends PartialValueGraph {

  override def edgeType: EdgeType = FollowedBy
}

/**
 * Mutual Follow Graphs
 */
object MutualFollowPartialValueGraph extends PartialValueGraph {

  override def edgeType: EdgeType = MutualFollow
}
