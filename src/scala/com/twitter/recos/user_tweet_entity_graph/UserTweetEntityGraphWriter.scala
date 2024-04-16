package com.tw ter.recos.user_t et_ent y_graph

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.consu rs.F nagleKafkaConsu rBu lder
 mport com.tw ter.graphjet.algor hms.{Recom ndat onType, T et DMask}
 mport com.tw ter.graphjet.b part e.Node tadataLeft ndexedMult Seg ntB part eGraph
 mport com.tw ter.graphjet.b part e.seg nt.Node tadataLeft ndexedB part eGraphSeg nt
 mport com.tw ter.recos.hose.common.Un f edGraphWr er
 mport com.tw ter.recos. nternal.thr ftscala.RecosHose ssage
 mport com.tw ter.recos.serv ceap .T etyp e._

/**
 * T  class subm s a number of $numBootstrapWr ers graph wr er threads, BufferedEdgeWr er,
 * dur ng serv ce startup. One of t m  s l ve wr er thread, and t  ot r $(numBootstrapWr ers - 1)
 * are catchup wr er threads. All of t m consu  kafka events from an  nternal concurrent queue,
 * wh ch  s populated by kafka reader threads. At bootstrap t  , t  kafka reader threads look
 * back kafka offset from several h s ago and populate t   nternal concurrent queue.
 * Each graph wr er thread wr es to an  nd v dual graph seg nt separately.
 * T  $(numBootstrapWr ers - 1) catchup wr er threads w ll stop once all events
 * bet en current system t   at startup and t  t    n  mcac  are processed.
 * T  l ve wr er thread w ll cont nue to wr e all  ncom ng kafka events.
 *   l ves through t  ent re l fe cycle of recos graph serv ce.
 */
case class UserT etEnt yGraphWr er(
  shard d: Str ng,
  env: Str ng,
  hosena : Str ng,
  bufferS ze:  nt,
  kafkaConsu rBu lder: F nagleKafkaConsu rBu lder[Str ng, RecosHose ssage],
  cl ent d: Str ng,
  statsRece ver: StatsRece ver)
    extends Un f edGraphWr er[
      Node tadataLeft ndexedB part eGraphSeg nt,
      Node tadataLeft ndexedMult Seg ntB part eGraph
    ] {
  wr er =>
  // T  max throughput for each kafka consu r  s around 25MB/s
  // Use 4 processors for 100MB/s catch-up speed.
  val consu rNum:  nt = 4
  // Leave 1 Seg nts to L veWr er
  val catchupWr erNum:  nt = RecosConf g.maxNumSeg nts - 1

  pr vate f nal val EMTPY_LEFT_NODE_METADATA = new Array[Array[ nt]](1)

  /**
   * Adds a RecosHose ssage to t  graph. used by l ve wr er to  nsert edges to t 
   * current seg nt
   */
  overr de def addEdgeToGraph(
    graph: Node tadataLeft ndexedMult Seg ntB part eGraph,
    recosHose ssage: RecosHose ssage
  ): Un  = {
    graph.addEdge(
      recosHose ssage.left d,
      get taEdge(recosHose ssage.r ght d, recosHose ssage.card),
      UserT etEdgeTypeMask.act onTypeToEdgeType(recosHose ssage.act on),
      recosHose ssage.edge tadata.getOrElse(0L),
      EMTPY_LEFT_NODE_METADATA,
      extractEnt  es(recosHose ssage)
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
      get taEdge(recosHose ssage.r ght d, recosHose ssage.card),
      UserT etEdgeTypeMask.act onTypeToEdgeType(recosHose ssage.act on),
      recosHose ssage.edge tadata.getOrElse(0L),
      EMTPY_LEFT_NODE_METADATA,
      extractEnt  es(recosHose ssage)
    )
  }

  pr vate def get taEdge(r ght d: Long, cardOpt on: Opt on[Byte]): Long = {
    cardOpt on
      .map { card =>
         f ( sPhotoCard(card)) T et DMask.photo(r ght d)
        else  f ( sPlayerCard(card)) T et DMask.player(r ght d)
        else  f ( sSummaryCard(card)) T et DMask.summary(r ght d)
        else  f ( sPromot onCard(card)) T et DMask.promot on(r ght d)
        else r ght d
      }
      .getOrElse(r ght d)
  }

  pr vate def extractEnt  es( ssage: RecosHose ssage): Array[Array[ nt]] = {
    val ent  es: Array[Array[ nt]] =
      new Array[Array[ nt]](Recom ndat onType.METADATAS ZE.getValue)
     ssage.ent  es.foreach {
      _.foreach {
        case (ent yType,  ds) =>
          ent  es.update(ent yType,  ds.toArray)
      }
    }
    ent  es
  }

}
