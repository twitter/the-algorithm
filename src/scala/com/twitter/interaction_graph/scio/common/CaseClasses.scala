package com.twitter.interaction_graph.scio.common

import com.twitter.interaction_graph.thriftscala.FeatureName

/** Interaction Graph Raw Input type defines a common type for edge / vertex feature calculation
 * It has fields: (source Id, destination Id, Feature Name, age of this relationship (in days),
 * and value to be aggregated)
 */
case class InteractionGraphRawInput(
  src: Long,
  dst: Long,
  name: FeatureName,
  age: Int,
  featureValue: Double)

case class FeatureKey(
  src: Long,
  dest: Long,
  name: FeatureName)

case class Tweepcred(userId: Long, tweepcred: Short)
