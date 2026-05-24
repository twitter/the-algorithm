package com.twitter.recos.user_tweet_entity_graph

import com.twitter.graphjet.algorithms.RecommendationType
import com.twitter.recos.model.Constants
import com.twitter.recos.graph_common.NodeMetadataLeftIndexedPowerLawMultiSegmentBipartiteGraphBuilder.GraphBuilderConfig

/**
 * The class holds all the config parameters for recos graph.
 */
object RecosConfig {
  val maxNumSegments: Int = 8 // this value will be overwritten by a parameter from profile config
  val maxNumEdgesPerSegment: Int = 1 << 27 // 134M edges per segment
  val expectedNumLeftNodes: Int = 1 << 24 // 16M nodes
  val expectedMaxLeftDegree: Int = 64
  val leftPowerLawExponent: Double = 16.0 // steep power law as most nodes will have a small degree
  val expectedNumRightNodes: Int = 1 << 24 // 16M nodes
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

  val maxUserSocialProofSize: Int = 10
  val maxTweetSocialProofSize: Int = 10
  val maxTweetAgeInMillis: Long = 24 * 60 * 60 * 1000
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
