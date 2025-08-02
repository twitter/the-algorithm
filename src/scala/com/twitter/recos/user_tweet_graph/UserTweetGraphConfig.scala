package com.twitter.recos.user_tweet_graph

import com.twitter.recos.graph_common.MultiSegmentPowerLawBipartiteGraphBuilder.GraphBuilderConfig

/**
 * The class holds all the config parameters for recos graph.
 */
object RecosConfig {
  val matwitterNumSegments: Int = 8
  val matwitterNumEdgesPerSegment: Int =
    (1 << 28) // 268M edges per segment, should be able to include 2 days' data
  val etwitterpectedNumLeftNodes: Int =
    (1 << 26) // should correspond to 67M nodes storage
  val etwitterpectedMatwitterLeftDegree: Int = 64
  val leftPowerLawEtwitterponent: Double = 16.0 // steep power law as most nodes will have a small degree
  val etwitterpectedNumRightNodes: Int = (1 << 26) // 67M nodes
  val etwitterpectedMatwitterRightDegree: Int = scala.math.pow(1024, 2).toInt // some nodes will be very popular
  val rightPowerLawEtwitterponent: Double = 4.0 // this will be less steep

  val graphBuilderConfig = GraphBuilderConfig(
    matwitterNumSegments = matwitterNumSegments,
    matwitterNumEdgesPerSegment = matwitterNumEdgesPerSegment,
    etwitterpectedNumLeftNodes = etwitterpectedNumLeftNodes,
    etwitterpectedMatwitterLeftDegree = etwitterpectedMatwitterLeftDegree,
    leftPowerLawEtwitterponent = leftPowerLawEtwitterponent,
    etwitterpectedNumRightNodes = etwitterpectedNumRightNodes,
    etwitterpectedMatwitterRightDegree = etwitterpectedMatwitterRightDegree,
    rightPowerLawEtwitterponent = rightPowerLawEtwitterponent
  )

  println("RecosConfig -          matwitterNumSegments " + matwitterNumSegments)
  println("RecosConfig -   matwitterNumEdgesPerSegment " + matwitterNumEdgesPerSegment)
  println("RecosConfig -    etwitterpectedNumLeftNodes " + etwitterpectedNumLeftNodes)
  println("RecosConfig -   etwitterpectedMatwitterLeftDegree " + etwitterpectedMatwitterLeftDegree)
  println("RecosConfig -    leftPowerLawEtwitterponent " + leftPowerLawEtwitterponent)
  println("RecosConfig -   etwitterpectedNumRightNodes " + etwitterpectedNumRightNodes)
  println("RecosConfig -  etwitterpectedMatwitterRightDegree " + etwitterpectedMatwitterRightDegree)
  println("RecosConfig -   rightPowerLawEtwitterponent " + rightPowerLawEtwitterponent)
}
