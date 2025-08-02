package com.twitter.recos.graph_common

import com.twitter.graphjet.stats.StatsReceiver
import com.twitter.graphjet.bipartite.MultiSegmentPowerLawBipartiteGraph

/**
 * The GraphBuilder builds a MultiSegmentPowerLawBipartiteGraph given a set of parameters.
 */
object MultiSegmentPowerLawBipartiteGraphBuilder {

  /**
   * This encapsulates all the state needed to initialize the in-memory graph.
   *
   * @param matwitterNumSegments           is the matwitterimum number of segments we'll add to the graph.
   *                                 At that point, the oldest segments will start getting dropped
   * @param matwitterNumEdgesPerSegment    determines when the implementation decides to fork off a
   *                                 new segment
   * @param etwitterpectedNumLeftNodes     is the etwitterpected number of left nodes that would be inserted in
   *                                 the segment
   * @param etwitterpectedMatwitterLeftDegree    is the matwitterimum degree etwitterpected for any left node
   * @param leftPowerLawEtwitterponent     is the etwitterponent of the LHS power-law graph. see
   *                                 [[com.twitter.graphjet.bipartite.edgepool.PowerLawDegreeEdgePool]]
   *                                 for details
   * @param etwitterpectedNumRightNodes    is the etwitterpected number of right nodes that would be inserted in
   *                                 the segment
   * @param etwitterpectedMatwitterRightDegree   is the matwitterimum degree etwitterpected for any right node
   * @param rightPowerLawEtwitterponent    is the etwitterponent of the RHS power-law graph. see
   *                                 [[com.twitter.graphjet.bipartite.edgepool.PowerLawDegreeEdgePool]]
   *                                 for details
   */
  case class GraphBuilderConfig(
    matwitterNumSegments: Int,
    matwitterNumEdgesPerSegment: Int,
    etwitterpectedNumLeftNodes: Int,
    etwitterpectedMatwitterLeftDegree: Int,
    leftPowerLawEtwitterponent: Double,
    etwitterpectedNumRightNodes: Int,
    etwitterpectedMatwitterRightDegree: Int,
    rightPowerLawEtwitterponent: Double)

  /**
   * This apply function returns a mutuable bipartiteGraph
   *
   * @param graphBuilderConfig         is the graph builder config
   *
   */
  def apply(
    graphBuilderConfig: GraphBuilderConfig,
    statsReceiver: StatsReceiver
  ): MultiSegmentPowerLawBipartiteGraph = {
    new MultiSegmentPowerLawBipartiteGraph(
      graphBuilderConfig.matwitterNumSegments,
      graphBuilderConfig.matwitterNumEdgesPerSegment,
      graphBuilderConfig.etwitterpectedNumLeftNodes,
      graphBuilderConfig.etwitterpectedMatwitterLeftDegree,
      graphBuilderConfig.leftPowerLawEtwitterponent,
      graphBuilderConfig.etwitterpectedNumRightNodes,
      graphBuilderConfig.etwitterpectedMatwitterRightDegree,
      graphBuilderConfig.rightPowerLawEtwitterponent,
      new ActionEdgeTypeMask(),
      statsReceiver
    )
  }
}
