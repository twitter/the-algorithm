package com.twitter.cr_mitwitterer.source_signal

import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.GraphSourceInfo
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.param.RealGraphOonParams
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
 * This store fetch user recommendations from RealGraphOON (go/realgraph) for a given userId
 */
@Singleton
case class RealGraphOonSourceGraphFetcher @Inject() (
  @Named(ModuleNames.RealGraphOonStore) realGraphOonStore: ReadableStore[UserId, CandidateSeq],
  override val timeoutConfig: TimeoutConfig,
  globalStats: StatsReceiver)
    etwittertends SourceGraphFetcher {

  override protected val stats: StatsReceiver = globalStats.scope(identifier)
  override protected val graphSourceType: SourceType = SourceType.RealGraphOon

  override def isEnabled(query: FetcherQuery): Boolean = {
    query.params(RealGraphOonParams.EnableSourceGraphParam)
  }

  override def fetchAndProcess(
    query: FetcherQuery,
  ): Future[Option[GraphSourceInfo]] = {
    val rawSignals = trackPerItemStats(query)(
      realGraphOonStore.get(query.userId).map {
        _.map { candidateSeq =>
          candidateSeq.candidates
            .map { candidate =>
              // Bundle the userId with its score
              (candidate.userId, candidate.score)
            }.take(query.params(RealGraphOonParams.MatwitterConsumerSeedsNumParam))
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
