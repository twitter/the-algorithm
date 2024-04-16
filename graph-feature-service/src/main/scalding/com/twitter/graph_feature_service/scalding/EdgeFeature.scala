package com.tw ter.graph_feature_serv ce.scald ng

case class EdgeFeature(
  realGraphScore: Float,
  followScore: Opt on[Float] = None,
  mutualFollowScore: Opt on[Float] = None,
  favor eScore: Opt on[Float] = None,
  ret etScore: Opt on[Float] = None,
   nt onScore: Opt on[Float] = None)
