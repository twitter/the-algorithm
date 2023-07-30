package com.X.cr_mixer.source_signal

import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.GraphSourceInfo
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.param.RealGraphInParams
import com.X.cr_mixer.source_signal.SourceFetcher.FetcherQuery
import com.X.cr_mixer.thriftscala.SourceType
import com.X.finagle.stats.StatsReceiver
import com.X.simclusters_v2.common.UserId
import com.X.storehaus.ReadableStore
import com.X.util.Future
import com.X.wtf.candidate.thriftscala.CandidateSeq
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * This store fetch user recommendations from In-Network RealGraph (go/realgraph) for a given userId
 */
@Singleton
case class RealGraphInSourceGraphFetcher @Inject() (
  @Named(ModuleNames.RealGraphInStore) realGraphStoreMh: ReadableStore[UserId, CandidateSeq],
  override val timeoutConfig: TimeoutConfig,
  globalStats: StatsReceiver)
    extends SourceGraphFetcher {

  override protected val stats: StatsReceiver = globalStats.scope(identifier)
  override protected val graphSourceType: SourceType = SourceType.RealGraphIn

  override def isEnabled(query: FetcherQuery): Boolean = {
    query.params(RealGraphInParams.EnableSourceGraphParam)
  }

  override def fetchAndProcess(
    query: FetcherQuery,
  ): Future[Option[GraphSourceInfo]] = {
    val rawSignals = trackPerItemStats(query)(
      realGraphStoreMh.get(query.userId).map {
        _.map { candidateSeq =>
          candidateSeq.candidates
            .map { candidate =>
              // Bundle the userId with its score
              (candidate.userId, candidate.score)
            }
        }
      }
    )
    rawSignals.map {
      _.map { userWithScores =>
        convertGraphSourceInfo(userWithScores)
      }
    }
  }
}
