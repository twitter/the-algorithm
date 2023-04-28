package com.twitter.unified_user_actions.adapter

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.stats.StatsReceiver

trait AbstractAdapter[INPUT, OUTK, OUTV] extends Serializable {

  /**
   * The basic input -> seq[output] adapter which concrete adapters should extend from
   * @param input a single INPUT
   * @return A list of (OUTK, OUTV) tuple. The OUTK is the output key mainly for publishing to Kafka (or Pubsub).
   *         If other processing, e.g. offline batch processing, doesn't require the output key then it can drop it
   *         like source.adaptOneToKeyedMany.map(_._2)
   */
  def adaptOneToKeyedMany(
    input: INPUT,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[(OUTK, OUTV)]
}
