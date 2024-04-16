package com.tw ter.recos.user_t et_graph

 mport com.tw ter.recos.graph_common.Mult Seg ntPo rLawB part eGraphBu lder.GraphBu lderConf g

/**
 * T  class holds all t  conf g para ters for recos graph.
 */
object RecosConf g {
  val maxNumSeg nts:  nt = 8
  val maxNumEdgesPerSeg nt:  nt =
    (1 << 28) // 268M edges per seg nt, should be able to  nclude 2 days' data
  val expectedNumLeftNodes:  nt =
    (1 << 26) // should correspond to 67M nodes storage
  val expectedMaxLeftDegree:  nt = 64
  val leftPo rLawExponent: Double = 16.0 // steep po r law as most nodes w ll have a small degree
  val expectedNumR ghtNodes:  nt = (1 << 26) // 67M nodes
  val expectedMaxR ghtDegree:  nt = scala.math.pow(1024, 2).to nt // so  nodes w ll be very popular
  val r ghtPo rLawExponent: Double = 4.0 // t  w ll be less steep

  val graphBu lderConf g = GraphBu lderConf g(
    maxNumSeg nts = maxNumSeg nts,
    maxNumEdgesPerSeg nt = maxNumEdgesPerSeg nt,
    expectedNumLeftNodes = expectedNumLeftNodes,
    expectedMaxLeftDegree = expectedMaxLeftDegree,
    leftPo rLawExponent = leftPo rLawExponent,
    expectedNumR ghtNodes = expectedNumR ghtNodes,
    expectedMaxR ghtDegree = expectedMaxR ghtDegree,
    r ghtPo rLawExponent = r ghtPo rLawExponent
  )

  pr ntln("RecosConf g -          maxNumSeg nts " + maxNumSeg nts)
  pr ntln("RecosConf g -   maxNumEdgesPerSeg nt " + maxNumEdgesPerSeg nt)
  pr ntln("RecosConf g -    expectedNumLeftNodes " + expectedNumLeftNodes)
  pr ntln("RecosConf g -   expectedMaxLeftDegree " + expectedMaxLeftDegree)
  pr ntln("RecosConf g -    leftPo rLawExponent " + leftPo rLawExponent)
  pr ntln("RecosConf g -   expectedNumR ghtNodes " + expectedNumR ghtNodes)
  pr ntln("RecosConf g -  expectedMaxR ghtDegree " + expectedMaxR ghtDegree)
  pr ntln("RecosConf g -   r ghtPo rLawExponent " + r ghtPo rLawExponent)
}
