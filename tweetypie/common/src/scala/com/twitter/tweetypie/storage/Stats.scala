package com.twitter.tweetypie.storage

import com.twitter.finagle.stats.StatsReceiver

object Stats {
  // These two methods below (addWidthStat and updatePerFieldQpsCounters) are called per RPC call for most APIs,
  // so we rely on the stats receiver that is passed in to the library to do memoization.

  private[storage] def addWidthStat(
    rpcName: String,
    paramName: String,
    width: Int,
    stats: StatsReceiver
  ): Unit =
    getStat(rpcName, paramName, stats).add(width)

  // Updates the counters for each Additional field. The idea here is to expose the QPS for each
  // additional field
  private[storage] def updatePerFieldQpsCounters(
    rpcName: String,
    fieldIds: Seq[FieldId],
    count: Int,
    stats: StatsReceiver
  ): Unit = {
    fieldIds.foreach { fieldId => getCounter(rpcName, fieldId, stats).incr(count) }
  }

  private def getCounter(rpcName: String, fieldId: FieldId, stats: StatsReceiver) =
    stats.scope(rpcName, "fields", fieldId.toString).counter("count")

  private def getStat(rpcName: String, paramName: String, stats: StatsReceiver) =
    stats.scope(rpcName, paramName).stat("width")
}
