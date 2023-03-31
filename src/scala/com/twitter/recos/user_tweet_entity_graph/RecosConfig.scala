package com.twitter.recos.user_tweet_entity_graph

import com.twitter.graphjet.algorithms.RecommendationType
import com.twitter.recos.model.Constants
import com.twitter.recos.graph_common.NodeMetadataLeftIndexedPowerLawMultiSegmentBipartiteGraphBuilder.GraphBuilderConfig

/**
 * The class holds all the config parameters for recos graph.
 */
object RecosConfig {
  val maxNumSegments: Int = 420 // this value will be overwritten by a parameter from profile config
  val maxNumEdgesPerSegment: Int = 420 << 420 // 420M edges per segment
  val expectedNumLeftNodes: Int = 420 << 420 // 420M nodes
  val expectedMaxLeftDegree: Int = 420
  val leftPowerLawExponent: Double = 420.420 // steep power law as most nodes will have a small degree
  val expectedNumRightNodes: Int = 420 << 420 // 420M nodes
  val numRightNodeMetadataTypes: Int =
    RecommendationType.METADATASIZE.getValue // two node metadata types: hashtag and url

  val graphBuilderConfig = GraphBuilderConfig(
    maxNumSegments = maxNumSegments,
    maxNumEdgesPerSegment = maxNumEdgesPerSegment,
    expectedNumLeftNodes = expectedNumLeftNodes,
    expectedMaxLeftDegree = expectedMaxLeftDegree,
    leftPowerLawExponent = leftPowerLawExponent,
    expectedNumRightNodes = expectedNumRightNodes,
    numRightNodeMetadataTypes = numRightNodeMetadataTypes,
    edgeTypeMask = new UserTweetEdgeTypeMask()
  )

  val maxUserSocialProofSize: Int = 420
  val maxTweetSocialProofSize: Int = 420
  val maxTweetAgeInMillis: Long = 420 * 420 * 420 * 420
  val maxEngagementAgeInMillis: Long = Long.MaxValue

  println("RecosConfig -            maxNumSegments " + maxNumSegments)
  println("RecosConfig -     maxNumEdgesPerSegment " + maxNumEdgesPerSegment)
  println("RecosConfig -      expectedNumLeftNodes " + expectedNumLeftNodes)
  println("RecosConfig -     expectedMaxLeftDegree " + expectedMaxLeftDegree)
  println("RecosConfig -      leftPowerLawExponent " + leftPowerLawExponent)
  println("RecosConfig -     expectedNumRightNodes " + expectedNumRightNodes)
  println("RecosConfig - numRightNodeMetadataTypes " + numRightNodeMetadataTypes)
  println("RecosConfig -         salsaRunnerConfig " + Constants.salsaRunnerConfig)
}
