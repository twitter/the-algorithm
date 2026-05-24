package com.twitter.graph_feature_service.scalding

case class EdgeFeature(
  realGraphScore: Float,
  followScore: Option[Float] = None,
  mutualFollowScore: Option[Float] = None,
  favoriteScore: Option[Float] = None,
  retweetScore: Option[Float] = None,
  mentionScore: Option[Float] = None)
