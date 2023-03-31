package com.twitter.graph_feature_service.worker.util

import com.twitter.graph_feature_service.thriftscala.EdgeType

sealed trait GfsQuery {
  def edgeType: EdgeType
  def userId: Long
}

/**
 * Search for edges for any users to users in local partition.
 */
case class ToPartialQuery(edgeType: EdgeType, userId: Long) extends GfsQuery

