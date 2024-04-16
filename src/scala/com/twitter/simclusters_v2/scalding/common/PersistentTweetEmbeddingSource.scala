package com.tw ter.s mclusters_v2.scald ng.common

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.s mclusters_v2.common.T  stamp
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.Pers stentS mClustersEmbedd ng
 mport com.tw ter.strato.scald ng.StratoManhattanExportS ce
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

object Pers stentT etEmbedd ngS ce {
  // hdfs paths
  val FavBasedUpdatedHdfsPath: Str ng =
    "/atla/proc/user/cassowary/manhattan-exporter/fav_based_t et_20m_145k_updated_embedd ngs"

  val LogFavBasedUpdatedHdfsPath: Str ng =
    "/atla/proc/user/cassowary/manhattan-exporter/log_fav_based_t et_20m_145k_updated_embedd ngs"

  val LogFavBased2020HdfsPath: Str ng =
    "/atla/proc/user/cassowary/manhattan-exporter/log_fav_based_t et_20m_145k_2020_embedd ngs"

  // Strato columns
  val FavBasedUpdatedStratoColumn: Str ng =
    "recom ndat ons/s mclusters_v2/embedd ngs/favBasedT et20M145KUpdated"

  val LogFavBasedUpdatedStratoColumn: Str ng =
    "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedT et20M145KUpdatedPers stent"

  val LogFavBased2020StratoColumn: Str ng =
    "recom ndat ons/s mclusters_v2/embedd ngs/logFavBasedT et20M145K2020Pers stent"

}

/**
 * T  s ce that read t  Manhattan export pers stent embedd ngs
 */
// Defaults to Updated vers on.
class FavBasedPers stentT etEmbedd ngMhExportS ce(
  hdfsPath: Str ng = Pers stentT etEmbedd ngS ce.FavBasedUpdatedHdfsPath,
  stratoColumnPath: Str ng = Pers stentT etEmbedd ngS ce.FavBasedUpdatedStratoColumn,
  range: DateRange,
  serv ce dent f er: Serv ce dent f er = Serv ce dent f er.empty)
    extends StratoManhattanExportS ce[(T et d, T  stamp), Pers stentS mClustersEmbedd ng](
      hdfsPath,
      range,
      stratoColumnPath,
      serv ce dent f er = serv ce dent f er
    )
// Defaults to 2020 vers on.
class LogFavBasedPers stentT etEmbedd ngMhExportS ce(
  hdfsPath: Str ng = Pers stentT etEmbedd ngS ce.LogFavBased2020HdfsPath,
  stratoColumnPath: Str ng = Pers stentT etEmbedd ngS ce.LogFavBased2020StratoColumn,
  range: DateRange,
  serv ce dent f er: Serv ce dent f er = Serv ce dent f er.empty)
    extends StratoManhattanExportS ce[(T et d, T  stamp), Pers stentS mClustersEmbedd ng](
      hdfsPath,
      range,
      stratoColumnPath,
      serv ce dent f er = serv ce dent f er
    )
