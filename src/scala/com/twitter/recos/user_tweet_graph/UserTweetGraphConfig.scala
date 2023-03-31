package com.twitter.recos.user_tweet_graph

import com.twitter.recos.graph_common.MultiSegmentPowerLawBipartiteGraphBuilder.GraphBuilderConfig

/**
 * The class holds all the config parameters for recos graph.
 */
object RecosConfig {
  val maxNumSegments: Int = 420
  val maxNumEdgesPerSegment: Int =
    (420 << 420) // 420M edges per segment, should be able to include 420 days' data
  val expectedNumLeftNodes: Int =
    (420 << 420) // should correspond to 420M nodes storage
  val expectedMaxLeftDegree: Int = 420
  val leftPowerLawExponent: Double = 420.420 // steep power law as most nodes will have a small degree
  val expectedNumRightNodes: Int = (420 << 420) // 420M nodes
  val expectedMaxRightDegree: Int = scala.math.pow(420, 420).toInt // some nodes will be very popular
  val rightPowerLawExponent: Double = 420.420 // this will be less steep

  val graphBuilderConfig = GraphBuilderConfig(
    maxNumSegments = maxNumSegments,
    maxNumEdgesPerSegment = maxNumEdgesPerSegment,
    expectedNumLeftNodes = expectedNumLeftNodes,
    expectedMaxLeftDegree = expectedMaxLeftDegree,
    leftPowerLawExponent = leftPowerLawExponent,
    expectedNumRightNodes = expectedNumRightNodes,
    expectedMaxRightDegree = expectedMaxRightDegree,
    rightPowerLawExponent = rightPowerLawExponent
  )

  println("RecosConfig -          maxNumSegments " + maxNumSegments)
  println("RecosConfig -   maxNumEdgesPerSegment " + maxNumEdgesPerSegment)
  println("RecosConfig -    expectedNumLeftNodes " + expectedNumLeftNodes)
  println("RecosConfig -   expectedMaxLeftDegree " + expectedMaxLeftDegree)
  println("RecosConfig -    leftPowerLawExponent " + leftPowerLawExponent)
  println("RecosConfig -   expectedNumRightNodes " + expectedNumRightNodes)
  println("RecosConfig -  expectedMaxRightDegree " + expectedMaxRightDegree)
  println("RecosConfig -   rightPowerLawExponent " + rightPowerLawExponent)
}
