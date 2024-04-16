package com.tw ter.recos.user_user_graph

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.consu rs.F nagleKafkaConsu rBu lder
 mport com.tw ter.graphjet.b part e.Node tadataLeft ndexedPo rLawMult Seg ntB part eGraph
 mport com.tw ter.graphjet.b part e.seg nt.Node tadataLeft ndexedB part eGraphSeg nt
 mport com.tw ter.recos.hose.common.Un f edGraphWr er
 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport com.tw ter.recos.ut l.Act on

case class UserUserGraphWr er(
  shard d: Str ng,
  env: Str ng,
  hosena : Str ng,
  bufferS ze:  nt,
  kafkaConsu rBu lder: F nagleKafkaConsu rBu lder[Str ng, RecosHose ssage],
  cl ent d: Str ng,
  statsRece ver: StatsRece ver)
    extends Un f edGraphWr er[
      Node tadataLeft ndexedB part eGraphSeg nt,
      Node tadataLeft ndexedPo rLawMult Seg ntB part eGraph
    ] {

  // T  max throughput for each kafka consu r  s around 25MB/s
  // Use 3 processors for 75MB/s catch-up speed.
  val consu rNum:  nt = 3
  // Leave 2 Seg nts for l ve wr er
  val catchupWr erNum:  nt = RecosConf g.maxNumSeg nts - 2

   mport UserUserGraphWr er._

  pr vate def getEdgeType(act on: Byte): Byte = {
     f (act on == Act on.Follow. d) {
      UserEdgeTypeMask.FOLLOW
    } else  f (act on == Act on. nt on. d) {
      UserEdgeTypeMask.MENT ON
    } else  f (act on == Act on. d aTag. d) {
      UserEdgeTypeMask.MED ATAG
    } else {
      throw new  llegalArgu ntExcept on("getEdgeType:  llegal edge type argu nt " + act on)
    }
  }

  /**
   * Adds a RecosHose ssage to t  graph. used by l ve wr er to  nsert edges to t 
   * current seg nt
   */
  overr de def addEdgeToGraph(
    graph: Node tadataLeft ndexedPo rLawMult Seg ntB part eGraph,
    recosHose ssage: RecosHose ssage
  ): Un  = {
    graph.addEdge(
      recosHose ssage.left d,
      recosHose ssage.r ght d,
      getEdgeType(recosHose ssage.act on),
      recosHose ssage.edge tadata.getOrElse(0L),
      EMTPY_NODE_METADATA,
      EMTPY_NODE_METADATA
    )
  }

  /**
   * Adds a RecosHose ssage to t  g ven seg nt  n t  graph. Used by catch up wr ers to
   *  nsert edges to non-current (old) seg nts
   */
  overr de def addEdgeToSeg nt(
    seg nt: Node tadataLeft ndexedB part eGraphSeg nt,
    recosHose ssage: RecosHose ssage
  ): Un  = {
    seg nt.addEdge(
      recosHose ssage.left d,
      recosHose ssage.r ght d,
      getEdgeType(recosHose ssage.act on),
      recosHose ssage.edge tadata.getOrElse(0L),
      EMTPY_NODE_METADATA,
      EMTPY_NODE_METADATA
    )
  }
}

pr vate object UserUserGraphWr er {
  f nal val EMTPY_NODE_METADATA = new Array[Array[ nt]](1)
}
