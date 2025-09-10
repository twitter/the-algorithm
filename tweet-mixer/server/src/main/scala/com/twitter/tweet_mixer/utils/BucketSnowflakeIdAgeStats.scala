package com.twitter.tweet_mixer.utils

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.util.Time

/**
 * Record the counts by created Time in buckets for SnowflakeIds.
 * For example, tweetIds, userIds which are created after 2014.
 *
 * Warning: Don't use more than 100 buckets in real production.
 * It damages the production service performance.
 *
 * CQL example
 * zone([atla], groupby(metric, sum(_), default(0, rate(ts(SUM, content-recommender.prod.content-recommender, members(sd.content-recommender.prod.content-recommender), simClusters_tweet_candidate_source/hourly/'*'))))) / 60
 *
 * @param bucketSize Define the basic millisecond size of each bucket.
 * @param fn Define the function to convert a generic type to a SnowflakeId
 */
case class BucketSnowflakeIdAgeStats[K](
  bucketSize: Long,
  fn: K => Long
)(
  implicit statsReceiver: StatsReceiver) {
  import BucketSnowflakeIdAgeStats._

  private val memoizeStats = new MemoizingStatsReceiver(statsReceiver)
  private val bucketStats = memoizeStats.scope("bucket")
  private val totalNumCounter = memoizeStats.counter("total")

  def count[KS <: Iterable[K]](ks: KS): KS = {
    val now = Time.now

    ks.foreach { k =>
      val bucketId = SnowflakeId.timeFromIdOpt(fn(k)) match {
        case Some(time) if now > time =>
          (now - time).inMillis / bucketSize
        case None =>
          Invalid
      }
      bucketStats.counter(s"$bucketId").incr()
      totalNumCounter.incr()
    }
    ks
  }
}

object BucketSnowflakeIdAgeStats {

  val Invalid: Long = -1

  val MillisecondsPerMinute: Long = 1000 * 60
  val MillisecondsPerHour: Long = MillisecondsPerMinute * 60
  val MillisecondsPerDay: Long = MillisecondsPerHour * 24
  val MillisecondsPerYear: Long = MillisecondsPerDay * 365

}
