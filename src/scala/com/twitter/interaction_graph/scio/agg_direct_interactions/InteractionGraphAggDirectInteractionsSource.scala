package com.tw ter. nteract on_graph.sc o.agg_d rect_ nteract ons

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter.cde.sc o.dal_read.S ceUt l
 mport com.tw ter.t  l neserv ce.thr ftscala.Contextual zedFavor eEvent
 mport com.tw ter.twadoop.user.gen.thr ftscala.Comb nedUser
 mport com.tw ter.t ets ce.common.thr ftscala.UnhydratedFlatT et
 mport com.tw ter.t etyp e.thr ftscala.T et d aTagEvent
 mport com.tw ter.users ce.snapshot.comb ned.Users ceScalaDataset
 mport com.tw ter.ut l.Durat on
 mport org.joda.t  . nterval
 mport twadoop_conf g.conf gurat on.log_categor es.group.t  l ne.T  l neServ ceFavor esScalaDataset
 mport twadoop_conf g.conf gurat on.log_categor es.group.t etyp e.T etyp e d aTagEventsScalaDataset
 mport t ets ce.common.UnhydratedFlatScalaDataset

case class  nteract onGraphAggD rect nteract onsS ce(
  p pel neOpt ons:  nteract onGraphAggD rect nteract onsOpt on
)(
   mpl c  sc: Sc oContext) {
  val dalEnv ron nt: Str ng = p pel neOpt ons
    .as(classOf[Serv ce dent f erOpt ons])
    .getEnv ron nt()

  def readFavor es(date nterval:  nterval): SCollect on[Contextual zedFavor eEvent] =
    S ceUt l.readDALDataset[Contextual zedFavor eEvent](
      dataset = T  l neServ ceFavor esScalaDataset,
       nterval = date nterval,
      dalEnv ron nt = dalEnv ron nt
    )

  def readPhotoTags(date nterval:  nterval): SCollect on[T et d aTagEvent] =
    S ceUt l.readDALDataset[T et d aTagEvent](
      dataset = T etyp e d aTagEventsScalaDataset,
       nterval = date nterval,
      dalEnv ron nt = dalEnv ron nt)

  def readT etS ce(date nterval:  nterval): SCollect on[UnhydratedFlatT et] =
    S ceUt l.readDALDataset[UnhydratedFlatT et](
      dataset = UnhydratedFlatScalaDataset,
       nterval = date nterval,
      dalEnv ron nt = dalEnv ron nt)

  def readComb nedUsers(): SCollect on[Comb nedUser] =
    S ceUt l.readMostRecentSnapshotNoOlderThanDALDataset[Comb nedUser](
      dataset = Users ceScalaDataset,
      noOlderThan = Durat on.fromDays(5),
      dalEnv ron nt = dalEnv ron nt
    )
}
