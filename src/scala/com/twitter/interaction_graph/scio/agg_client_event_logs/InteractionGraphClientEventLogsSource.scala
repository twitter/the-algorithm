package com.tw ter. nteract on_graph.sc o.agg_cl ent_event_logs

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.beam.job.Serv ce dent f erOpt ons
 mport com.tw ter.twadoop.user.gen.thr ftscala.Comb nedUser
 mport com.tw ter.users ce.snapshot.comb ned.Users ceScalaDataset
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.cde.sc o.dal_read.S ceUt l
 mport com.tw ter.wtf.scald ng.cl ent_event_process ng.thr ftscala.User nteract on
 mport com.tw ter.wtf.scald ng.jobs.cl ent_event_process ng.User nteract onScalaDataset
 mport org.joda.t  . nterval

case class  nteract onGraphCl entEventLogsS ce(
  p pel neOpt ons:  nteract onGraphCl entEventLogsOpt on
)(
   mpl c  sc: Sc oContext) {

  val dalEnv ron nt: Str ng = p pel neOpt ons
    .as(classOf[Serv ce dent f erOpt ons])
    .getEnv ron nt()

  def readUser nteract ons(date nterval:  nterval): SCollect on[User nteract on] = {

    S ceUt l.readDALDataset[User nteract on](
      dataset = User nteract onScalaDataset,
       nterval = date nterval,
      dalEnv ron nt = dalEnv ron nt)

  }

  def readComb nedUsers(): SCollect on[Comb nedUser] = {

    S ceUt l.readMostRecentSnapshotNoOlderThanDALDataset[Comb nedUser](
      dataset = Users ceScalaDataset,
      noOlderThan = Durat on.fromDays(5),
      dalEnv ron nt = dalEnv ron nt
    )
  }
}
