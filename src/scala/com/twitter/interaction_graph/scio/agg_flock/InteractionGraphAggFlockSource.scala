package com.tw ter. nteract on_graph.sc o.agg_flock

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter.flockdb.tools.datasets.flock.thr ftscala.FlockEdge
 mport com.tw ter.cde.sc o.dal_read.S ceUt l
 mport com.tw ter.wtf.dataflow.user_events.Val dUserFollowsScalaDataset
 mport org.joda.t  . nterval

case class  nteract onGraphAggFlockS ce(
  p pel neOpt ons:  nteract onGraphAggFlockOpt on
)(
   mpl c  sc: Sc oContext) {
  val dalEnv ron nt: Str ng = p pel neOpt ons
    .as(classOf[Serv ce dent f erOpt ons])
    .getEnv ron nt()

  def readFlockFollowsSnapshot(date nterval:  nterval): SCollect on[FlockEdge] =
    S ceUt l.readMostRecentSnapshotDALDataset(
      dataset = Val dUserFollowsScalaDataset,
      date nterval = date nterval,
      dalEnv ron nt = dalEnv ron nt)
}
