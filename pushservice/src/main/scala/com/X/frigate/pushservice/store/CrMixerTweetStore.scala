package com.X.frigate.pushservice.store

import com.X.cr_mixer.thriftscala.CrMixer
import com.X.cr_mixer.thriftscala.CrMixerTweetRequest
import com.X.cr_mixer.thriftscala.CrMixerTweetResponse
import com.X.cr_mixer.thriftscala.FrsTweetRequest
import com.X.cr_mixer.thriftscala.FrsTweetResponse
import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.Stat
import com.X.finagle.stats.StatsReceiver
import com.X.util.Future

/**
 * Store to get content recs from content recommender.
 */
case class CrMixerTweetStore(
  crMixer: CrMixer.MethodPerEndpoint
)(
  implicit statsReceiver: StatsReceiver = NullStatsReceiver) {

  private val requestsCounter = statsReceiver.counter("requests")
  private val successCounter = statsReceiver.counter("success")
  private val failuresCounter = statsReceiver.counter("failures")
  private val nonEmptyCounter = statsReceiver.counter("non_empty")
  private val emptyCounter = statsReceiver.counter("empty")
  private val failuresScope = statsReceiver.scope("failures")
  private val latencyStat = statsReceiver.stat("latency")

  private def updateStats[T](f: => Future[Option[T]]): Future[Option[T]] = {
    requestsCounter.incr()
    Stat
      .timeFuture(latencyStat)(f)
      .onSuccess { r =>
        if (r.isDefined) nonEmptyCounter.incr() else emptyCounter.incr()
        successCounter.incr()
      }
      .onFailure { e =>
        {
          failuresCounter.incr()
          failuresScope.counter(e.getClass.getName).incr()
        }
      }
  }

  def getTweetRecommendations(
    request: CrMixerTweetRequest
  ): Future[Option[CrMixerTweetResponse]] = {
    updateStats(crMixer.getTweetRecommendations(request).map { response =>
      Some(response)
    })
  }

  def getFRSTweetCandidates(request: FrsTweetRequest): Future[Option[FrsTweetResponse]] = {
    updateStats(crMixer.getFrsBasedTweetRecommendations(request).map { response =>
      Some(response)
    })
  }
}
