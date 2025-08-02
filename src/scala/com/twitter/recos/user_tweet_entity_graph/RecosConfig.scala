package com.twitter.recos.user_tweet_entity_graph

import com.twitter.graphjet.algorithms.RecommendationType
import com.twitter.recos.model.Constants
import com.twitter.recos.graph_common.NodeMetadataLeftIndetwitteredPowerLawMultiSegmentBipartiteGraphBuilder.GraphBuilderConfig

/**
 * The class holds all the config parameters for recos graph.
 */
object RecosConfig {
  val matwitterNumSegments: Int = 8 // this value will be overwritten by a parameter from profile config
  val matwitterNumEdgesPerSegment: Int = 1 << 27 // 134M edges per segment
  val etwitterpectedNumLeftNodes: Int = 1 << 24 // 16M nodes
  val etwitterpectedMatwitterLeftDegree: Int = 64
  val leftPowerLawEtwitterponent: Double = 16.0 // steep power law as most nodes will have a small degree
  val etwitterpectedNumRightNodes: Int = 1 << 24 // 16M nodes
  val numRightNodeMetadataTypes: Int =
    RecommendationType.METADATASIZE.getValue // two node metadata types: hashtag and url

  val graphBuilderConfig = GraphBuilderConfig(
    matwitterNumSegments = matwitterNumSegments,
    matwitterNumEdgesPerSegment = matwitterNumEdgesPerSegment,
    etwitterpectedNumLeftNodes = etwitterpectedNumLeftNodes,
    etwitterpectedMatwitterLeftDegree = etwitterpectedMatwitterLeftDegree,
    leftPowerLawEtwitterponent = leftPowerLawEtwitterponent,
    etwitterpectedNumRightNodes = etwitterpectedNumRightNodes,
    numRightNodeMetadataTypes = numRightNodeMetadataTypes,
    edgeTypeMask = new UserTweetEdgeTypeMask()
  )

  val matwitterUserSocialProofSize: Int = 10
  val matwitterTweetSocialProofSize: Int = 10
  val matwitterTweetAgeInMillis: Long = 24 * 60 * 60 * 1000
  val matwitterEngagementAgeInMillis: Long = Long.MatwitterValue

  println("RecosConfig -            matwitterNumSegments " + matwitterNumSegments)
  println("RecosConfig -     matwitterNumEdgesPerSegment " + matwitterNumEdgesPerSegment)
  println("RecosConfig -      etwitterpectedNumLeftNodes " + etwitterpectedNumLeftNodes)
  println("RecosConfig -     etwitterpectedMatwitterLeftDegree " + etwitterpectedMatwitterLeftDegree)
  println("RecosConfig -      leftPowerLawEtwitterponent " + leftPowerLawEtwitterponent)
  println("RecosConfig -     etwitterpectedNumRightNodes " + etwitterpectedNumRightNodes)
  println("RecosConfig - numRightNodeMetadataTypes " + numRightNodeMetadataTypes)
  println("RecosConfig -         salsaRunnerConfig " + Constants.salsaRunnerConfig)
}
