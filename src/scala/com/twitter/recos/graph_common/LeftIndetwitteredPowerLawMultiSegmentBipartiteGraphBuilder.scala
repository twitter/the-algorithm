package com.twitter.recos.graph_common

import com.twitter.graphjet.bipartite.LeftIndetwitteredPowerLawMultiSegmentBipartiteGraph
import com.twitter.graphjet.bipartite.api.EdgeTypeMask
import com.twitter.graphjet.stats.StatsReceiver

/**
 * The GraphBuilder builds a LeftIndetwitteredPowerLawMultiSegmentBipartiteGraph given a set of
 * parameters.
 */
object LeftIndetwitteredPowerLawMultiSegmentBipartiteGraphBuilder {

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
   */
  case class GraphBuilderConfig(
    matwitterNumSegments: Int,
    matwitterNumEdgesPerSegment: Int,
    etwitterpectedNumLeftNodes: Int,
    etwitterpectedMatwitterLeftDegree: Int,
    leftPowerLawEtwitterponent: Double,
    etwitterpectedNumRightNodes: Int,
    edgeTypeMask: EdgeTypeMask)

  /**
   * This apply function returns a mutuable bipartiteGraph
   *
   * @param graphBuilderConfig is the graph builder config
   *
   */
  def apply(
    graphBuilderConfig: GraphBuilderConfig,
    statsReceiverWrapper: StatsReceiver
  ): LeftIndetwitteredPowerLawMultiSegmentBipartiteGraph = {
    new LeftIndetwitteredPowerLawMultiSegmentBipartiteGraph(
      graphBuilderConfig.matwitterNumSegments,
      graphBuilderConfig.matwitterNumEdgesPerSegment,
      graphBuilderConfig.etwitterpectedNumLeftNodes,
      graphBuilderConfig.etwitterpectedMatwitterLeftDegree,
      graphBuilderConfig.leftPowerLawEtwitterponent,
      graphBuilderConfig.etwitterpectedNumRightNodes,
      graphBuilderConfig.edgeTypeMask,
      statsReceiverWrapper
    )
  }
}
