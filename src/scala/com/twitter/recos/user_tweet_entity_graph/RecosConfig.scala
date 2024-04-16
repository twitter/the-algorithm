package com.tw ter.recos.user_t et_ent y_graph

 mport com.tw ter.graphjet.algor hms.Recom ndat onType
 mport com.tw ter.recos.model.Constants
 mport com.tw ter.recos.graph_common.Node tadataLeft ndexedPo rLawMult Seg ntB part eGraphBu lder.GraphBu lderConf g

/**
 * T  class holds all t  conf g para ters for recos graph.
 */
object RecosConf g {
  val maxNumSeg nts:  nt = 8 // t  value w ll be overwr ten by a para ter from prof le conf g
  val maxNumEdgesPerSeg nt:  nt = 1 << 27 // 134M edges per seg nt
  val expectedNumLeftNodes:  nt = 1 << 24 // 16M nodes
  val expectedMaxLeftDegree:  nt = 64
  val leftPo rLawExponent: Double = 16.0 // steep po r law as most nodes w ll have a small degree
  val expectedNumR ghtNodes:  nt = 1 << 24 // 16M nodes
  val numR ghtNode tadataTypes:  nt =
    Recom ndat onType.METADATAS ZE.getValue // two node  tadata types: hashtag and url

  val graphBu lderConf g = GraphBu lderConf g(
    maxNumSeg nts = maxNumSeg nts,
    maxNumEdgesPerSeg nt = maxNumEdgesPerSeg nt,
    expectedNumLeftNodes = expectedNumLeftNodes,
    expectedMaxLeftDegree = expectedMaxLeftDegree,
    leftPo rLawExponent = leftPo rLawExponent,
    expectedNumR ghtNodes = expectedNumR ghtNodes,
    numR ghtNode tadataTypes = numR ghtNode tadataTypes,
    edgeTypeMask = new UserT etEdgeTypeMask()
  )

  val maxUserSoc alProofS ze:  nt = 10
  val maxT etSoc alProofS ze:  nt = 10
  val maxT etAge nM ll s: Long = 24 * 60 * 60 * 1000
  val maxEngage ntAge nM ll s: Long = Long.MaxValue

  pr ntln("RecosConf g -            maxNumSeg nts " + maxNumSeg nts)
  pr ntln("RecosConf g -     maxNumEdgesPerSeg nt " + maxNumEdgesPerSeg nt)
  pr ntln("RecosConf g -      expectedNumLeftNodes " + expectedNumLeftNodes)
  pr ntln("RecosConf g -     expectedMaxLeftDegree " + expectedMaxLeftDegree)
  pr ntln("RecosConf g -      leftPo rLawExponent " + leftPo rLawExponent)
  pr ntln("RecosConf g -     expectedNumR ghtNodes " + expectedNumR ghtNodes)
  pr ntln("RecosConf g - numR ghtNode tadataTypes " + numR ghtNode tadataTypes)
  pr ntln("RecosConf g -         salsaRunnerConf g " + Constants.salsaRunnerConf g)
}
