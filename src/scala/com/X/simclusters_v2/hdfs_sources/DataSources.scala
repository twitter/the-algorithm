package com.X.simclusters_v2.hdfs_sources

import com.X.scalding.DateOps
import com.X.scalding.DateRange
import com.X.scalding.Days
import com.X.scalding.TypedPipe
import com.X.scalding_internal.dalv2.DAL
import com.X.scalding_internal.dalv2.remote_access.ExplicitLocation
import com.X.scalding_internal.dalv2.remote_access.ProcAtla
import com.X.simclusters_v2.thriftscala.NormsAndCounts
import com.X.simclusters_v2.thriftscala.UserAndNeighbors
import java.util.TimeZone

object DataSources {

  /**
   * Reads production normalized graph data from atla-proc
   */
  def userUserNormalizedGraphSource(implicit dateRange: DateRange): TypedPipe[UserAndNeighbors] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(14)(DateOps.UTC))
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe
  }

  /**
   * Reads production user norms and counts data from atla-proc
   */
  def userNormsAndCounts(
    implicit dateRange: DateRange,
    timeZone: TimeZone
  ): TypedPipe[NormsAndCounts] = {
    DAL
      .readMostRecentSnapshot(ProducerNormsAndCountsScalaDataset, dateRange.prepend(Days(14)))
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe
  }

}
