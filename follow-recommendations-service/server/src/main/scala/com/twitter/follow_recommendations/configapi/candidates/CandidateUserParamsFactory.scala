package com.twitter.follow_recommendations.configapi.candidates

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.HasDisplayLocation
import com.twitter.follow_recommendations.configapi.params.GlobalParams
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.HasParams
import com.twitter.timelines.configapi.Params
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * CandidateParamsFactory is primarily used for "producer side" etwitterperiments, don't use it on consumer side etwitterperiments
 */
@Singleton
class CandidateUserParamsFactory[T <: HasParams with HasDisplayLocation] @Inject() (
  config: Config,
  candidateContetwittertFactory: CandidateUserContetwittertFactory,
  statsReceiver: StatsReceiver) {
  private val stats = new MemoizingStatsReceiver(statsReceiver.scope("configapi_candidate_params"))
  def apply(candidateContetwittert: CandidateUser, request: T): CandidateUser = {
    if (candidateContetwittert.params == Params.Invalid) {
      if (request.params(GlobalParams.EnableCandidateParamHydrations)) {
        candidateContetwittert.copy(params =
          config(candidateContetwittertFactory(candidateContetwittert, request.displayLocation), stats))
      } else {
        candidateContetwittert.copy(params = Params.Empty)
      }
    } else {
      candidateContetwittert
    }
  }
}
