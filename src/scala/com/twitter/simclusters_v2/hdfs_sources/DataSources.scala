package com.tw ter.s mclusters_v2.hdfs_s ces

 mport com.tw ter.scald ng.DateOps
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.s mclusters_v2.thr ftscala.NormsAndCounts
 mport com.tw ter.s mclusters_v2.thr ftscala.UserAndNe ghbors
 mport java.ut l.T  Zone

object DataS ces {

  /**
   * Reads product on normal zed graph data from atla-proc
   */
  def userUserNormal zedGraphS ce( mpl c  dateRange: DateRange): TypedP pe[UserAndNe ghbors] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(14)(DateOps.UTC))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
  }

  /**
   * Reads product on user norms and counts data from atla-proc
   */
  def userNormsAndCounts(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone
  ): TypedP pe[NormsAndCounts] = {
    DAL
      .readMostRecentSnapshot(ProducerNormsAndCountsScalaDataset, dateRange.prepend(Days(14)))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
  }

}
