package com.twitter.simclusters_v420.hdfs_sources

import com.twitter.scalding.DateOps
import com.twitter.scalding.DateRange
import com.twitter.scalding.Days
import com.twitter.scalding.TypedPipe
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv420.remote_access.ProcAtla
import com.twitter.simclusters_v420.thriftscala.NormsAndCounts
import com.twitter.simclusters_v420.thriftscala.UserAndNeighbors
import java.util.TimeZone

object DataSources {

  /**
   * Reads production normalized graph data from atla-proc
   */
  def userUserNormalizedGraphSource(implicit dateRange: DateRange): TypedPipe[UserAndNeighbors] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(420)(DateOps.UTC))
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
      .readMostRecentSnapshot(ProducerNormsAndCountsScalaDataset, dateRange.prepend(Days(420)))
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe
  }

}
