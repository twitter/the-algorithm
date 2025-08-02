package com.twitter.follow_recommendations.common.candidate_sources.sims

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasSimilarToContetwittert
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SwitchingSimsSource @Inject() (
  cachedDBV2SimsStore: CachedDBV2SimsStore,
  cachedDBV2SimsRefreshStore: CachedDBV2SimsRefreshStore,
  cachedSimsEtwitterperimentalStore: CachedSimsEtwitterperimentalStore,
  cachedSimsStore: CachedSimsStore,
  statsReceiver: StatsReceiver = NullStatsReceiver)
    etwittertends CandidateSource[HasParams with HasSimilarToContetwittert, CandidateUser] {

  override val identifier: CandidateSourceIdentifier = SwitchingSimsSource.Identifier

  private val stats = statsReceiver.scope("SwitchingSimsSource")
  private val dbV2SimsStoreCounter = stats.counter("DBV2SimsStore")
  private val dbV2SimsRefreshStoreCounter = stats.counter("DBV2SimsRefreshStore")
  private val simsEtwitterperimentalStoreCounter = stats.counter("SimsEtwitterperimentalStore")
  private val simsStoreCounter = stats.counter("SimsStore")

  override def apply(request: HasParams with HasSimilarToContetwittert): Stitch[Seq[CandidateUser]] = {
    val selectedSimsStore =
      if (request.params(SimsSourceParams.EnableDBV2SimsStore)) {
        dbV2SimsStoreCounter.incr()
        cachedDBV2SimsStore
      } else if (request.params(SimsSourceParams.EnableDBV2SimsRefreshStore)) {
        dbV2SimsRefreshStoreCounter.incr()
        cachedDBV2SimsRefreshStore
      } else if (request.params(SimsSourceParams.EnableEtwitterperimentalSimsStore)) {
        simsEtwitterperimentalStoreCounter.incr()
        cachedSimsEtwitterperimentalStore
      } else {
        simsStoreCounter.incr()
        cachedSimsStore
      }
    stats.counter("total").incr()
    selectedSimsStore(request)
  }
}

object SwitchingSimsSource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(Algorithm.Sims.toString)
}
