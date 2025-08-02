package com.twitter.recos.user_user_graph

import com.twitter.recos.model.Constants
import com.twitter.recos.graph_common.NodeMetadataLeftIndetwitteredPowerLawMultiSegmentBipartiteGraphBuilder.GraphBuilderConfig

/**
 * The class holds all the config parameters for recos graph.
 */
object RecosConfig {
  val matwitterNumSegments: Int = 5
  val matwitterNumEdgesPerSegment: Int = 1 << 26 // 64M edges per segment
  val etwitterpectedNumLeftNodes: Int = 1 << 24 // should correspond to 16M nodes storage
  val etwitterpectedMatwitterLeftDegree: Int = 64
  val leftPowerLawEtwitterponent: Double = 16.0 // steep power law as most nodes will have a small degree
  val etwitterpectedNumRightNodes: Int = 1 << 24 // 16M nodes
  val numRightNodeMetadataTypes = 1 // UUG does not have node metadata

  val graphBuilderConfig = GraphBuilderConfig(
    matwitterNumSegments = matwitterNumSegments,
    matwitterNumEdgesPerSegment = matwitterNumEdgesPerSegment,
    etwitterpectedNumLeftNodes = etwitterpectedNumLeftNodes,
    etwitterpectedMatwitterLeftDegree = etwitterpectedMatwitterLeftDegree,
    leftPowerLawEtwitterponent = leftPowerLawEtwitterponent,
    etwitterpectedNumRightNodes = etwitterpectedNumRightNodes,
    numRightNodeMetadataTypes = numRightNodeMetadataTypes,
    edgeTypeMask = new UserEdgeTypeMask()
  )

  println("RecosConfig -            matwitterNumSegments " + matwitterNumSegments)
  println("RecosConfig -     matwitterNumEdgesPerSegment " + matwitterNumEdgesPerSegment)
  println("RecosConfig -      etwitterpectedNumLeftNodes " + etwitterpectedNumLeftNodes)
  println("RecosConfig -     etwitterpectedMatwitterLeftDegree " + etwitterpectedMatwitterLeftDegree)
  println("RecosConfig -      leftPowerLawEtwitterponent " + leftPowerLawEtwitterponent)
  println("RecosConfig -     etwitterpectedNumRightNodes " + etwitterpectedNumRightNodes)
  println("RecosConfig -     numRightNodeMetadataTypes " + numRightNodeMetadataTypes)
  println("RecosConfig -         salsaRunnerConfig " + Constants.salsaRunnerConfig)
}
