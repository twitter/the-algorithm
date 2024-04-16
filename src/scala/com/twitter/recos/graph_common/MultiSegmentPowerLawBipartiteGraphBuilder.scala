package com.tw ter.recos.graph_common

 mport com.tw ter.graphjet.stats.StatsRece ver
 mport com.tw ter.graphjet.b part e.Mult Seg ntPo rLawB part eGraph

/**
 * T  GraphBu lder bu lds a Mult Seg ntPo rLawB part eGraph g ven a set of para ters.
 */
object Mult Seg ntPo rLawB part eGraphBu lder {

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
   * @param expectedMaxR ghtDegree    s t  max mum degree expected for any r ght node
   * @param r ghtPo rLawExponent     s t  exponent of t  RHS po r-law graph. see
   *                                 [[com.tw ter.graphjet.b part e.edgepool.Po rLawDegreeEdgePool]]
   *                                 for deta ls
   */
  case class GraphBu lderConf g(
    maxNumSeg nts:  nt,
    maxNumEdgesPerSeg nt:  nt,
    expectedNumLeftNodes:  nt,
    expectedMaxLeftDegree:  nt,
    leftPo rLawExponent: Double,
    expectedNumR ghtNodes:  nt,
    expectedMaxR ghtDegree:  nt,
    r ghtPo rLawExponent: Double)

  /**
   * T  apply funct on returns a mutuable b part eGraph
   *
   * @param graphBu lderConf g          s t  graph bu lder conf g
   *
   */
  def apply(
    graphBu lderConf g: GraphBu lderConf g,
    statsRece ver: StatsRece ver
  ): Mult Seg ntPo rLawB part eGraph = {
    new Mult Seg ntPo rLawB part eGraph(
      graphBu lderConf g.maxNumSeg nts,
      graphBu lderConf g.maxNumEdgesPerSeg nt,
      graphBu lderConf g.expectedNumLeftNodes,
      graphBu lderConf g.expectedMaxLeftDegree,
      graphBu lderConf g.leftPo rLawExponent,
      graphBu lderConf g.expectedNumR ghtNodes,
      graphBu lderConf g.expectedMaxR ghtDegree,
      graphBu lderConf g.r ghtPo rLawExponent,
      new Act onEdgeTypeMask(),
      statsRece ver
    )
  }
}
