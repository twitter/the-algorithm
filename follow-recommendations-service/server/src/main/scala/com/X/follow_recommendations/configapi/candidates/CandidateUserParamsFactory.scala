package com.X.follow_recommendations.configapi.candidates

import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasDisplayLocation
import com.X.follow_recommendations.configapi.params.GlobalParams
import com.X.servo.util.MemoizingStatsReceiver
import com.X.timelines.configapi.Config
import com.X.timelines.configapi.HasParams
import com.X.timelines.configapi.Params
import javax.inject.Inject
import javax.inject.Singleton

/**
 * CandidateParamsFactory is primarily used for "producer side" experiments, don't use it on consumer side experiments
 */
@Singleton
class CandidateUserParamsFactory[T <: HasParams with HasDisplayLocation] @Inject() (
  config: Config,
  candidateContextFactory: CandidateUserContextFactory,
  statsReceiver: StatsReceiver) {
  private val stats = new MemoizingStatsReceiver(statsReceiver.scope("configapi_candidate_params"))
  def apply(candidateContext: CandidateUser, request: T): CandidateUser = {
    if (candidateContext.params == Params.Invalid) {
      if (request.params(GlobalParams.EnableCandidateParamHydrations)) {
        candidateContext.copy(params =
          config(candidateContextFactory(candidateContext, request.displayLocation), stats))
      } else {
        candidateContext.copy(params = Params.Empty)
      }
    } else {
      candidateContext
    }
  }
}
