package com.twitter.cr_mitwitterer.source_signal

import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.GraphSourceInfo
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.param.RealGraphInParams
import com.twitter.cr_mitwitterer.source_signal.SourceFetcher.FetcherQuery
import com.twitter.cr_mitwitterer.thriftscala.SourceType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.simclusters_v2.common.UserId
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import com.twitter.wtf.candidate.thriftscala.CandidateSeq
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

/**
 * This store fetch user recommendations from In-Network RealGraph (go/realgraph) for a given userId
 */
@Singleton
case class RealGraphInSourceGraphFetcher @Inject() (
  @Named(ModuleNames.RealGraphInStore) realGraphStoreMh: ReadableStore[UserId, CandidateSeq],
  override val timeoutConfig: TimeoutConfig,
  globalStats: StatsReceiver)
    etwittertends SourceGraphFetcher {

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
