package com.tw ter.recos.graph_common

 mport com.tw ter.graphjet.b part e.R ghtNode tadataLeft ndexedPo rLawMult Seg ntB part eGraph
 mport com.tw ter.graphjet.b part e.ap .EdgeTypeMask
 mport com.tw ter.graphjet.stats.StatsRece ver

/**
 * T  GraphBu lder bu lds a R ghtNode tadataLeft ndexedPo rLawMult Seg ntB part eGraphBu lder g ven a set of
 * para ters.
 */
object R ghtNode tadataLeft ndexedPo rLawMult Seg ntB part eGraphBu lder {

  /**
   * T  encapsulates all t  state needed to  n  al ze t   n- mory graph.
   *
    * @param maxNumSeg nts            s t  max mum number of seg nts  'll add to t  graph.
   *                                 At that po nt, t  oldest seg nts w ll start gett ng dropped
   * @param maxNumEdgesPerSeg nt    determ nes w n t   mple ntat on dec des to fork off a
   *                                 new seg nt
   * @param expectedNumLeftNodes      s t  expected number of left nodes that would be  nserted  n
   *                                 t  seg nt
   * @param expectedMaxLeftDegree     s t  max mum degree expected for any left node
   * @param leftPo rLawExponent      s t  exponent of t  LHS po r-law graph. see
   *                                 [[com.tw ter.graphjet.b part e.edgepool.Po rLawDegreeEdgePool]]
   *                                 for deta ls
   * @param expectedNumR ghtNodes     s t  expected number of r ght nodes that would be  nserted  n
   *                                 t  seg nt
   * @param numR ghtNode tadataTypes  s t  max number of node  tadata types assoc ated w h t 
   *                                  r ght nodes
   */
  case class GraphBu lderConf g(
    maxNumSeg nts:  nt,
    maxNumEdgesPerSeg nt:  nt,
    expectedNumLeftNodes:  nt,
    expectedMaxLeftDegree:  nt,
    leftPo rLawExponent: Double,
    expectedNumR ghtNodes:  nt,
    numR ghtNode tadataTypes:  nt,
    edgeTypeMask: EdgeTypeMask)

  /**
   * T  apply funct on returns a mutuable b part eGraph
   *
    * @param graphBu lderConf g  s t  graph bu lder conf g
   *
    */
  def apply(
    graphBu lderConf g: GraphBu lderConf g,
    statsRece verWrapper: StatsRece ver
  ): R ghtNode tadataLeft ndexedPo rLawMult Seg ntB part eGraph = {
    new R ghtNode tadataLeft ndexedPo rLawMult Seg ntB part eGraph(
      graphBu lderConf g.maxNumSeg nts,
      graphBu lderConf g.maxNumEdgesPerSeg nt,
      graphBu lderConf g.expectedNumLeftNodes,
      graphBu lderConf g.expectedMaxLeftDegree,
      graphBu lderConf g.leftPo rLawExponent,
      graphBu lderConf g.expectedNumR ghtNodes,
      graphBu lderConf g.numR ghtNode tadataTypes,
      graphBu lderConf g.edgeTypeMask,
      statsRece verWrapper
    )
  }
}
