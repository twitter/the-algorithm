package com.ExTwitter.cr_mixer.source_signal

import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.GraphSourceInfo
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.param.FrsParams
import com.ExTwitter.cr_mixer.source_signal.FrsStore.FrsQueryResult
import com.ExTwitter.cr_mixer.source_signal.SourceFetcher.FetcherQuery
import com.ExTwitter.cr_mixer.thriftscala.SourceType
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/***
 * This store fetches user recommendations from FRS (go/frs) for a given userId
 */
@Singleton
case class FrsSourceGraphFetcher @Inject() (
  @Named(ModuleNames.FrsStore) frsStore: ReadableStore[FrsStore.Query, Seq[FrsQueryResult]],
  override val timeoutConfig: TimeoutConfig,
  globalStats: StatsReceiver)
    extends SourceGraphFetcher {

  override protected val stats: StatsReceiver = globalStats.scope(identifier)
  override protected val graphSourceType: SourceType = SourceType.FollowRecommendation

  override def isEnabled(query: FetcherQuery): Boolean = {
    query.params(FrsParams.EnableSourceGraphParam)
  }

  override def fetchAndProcess(
    query: FetcherQuery,
  ): Future[Option[GraphSourceInfo]] = {

    val rawSignals = trackPerItemStats(query)(
      frsStore
        .get(
          FrsStore
            .Query(query.userId, query.params(FrsParams.MaxConsumerSeedsNumParam))).map {
          _.map {
            _.map { v => (v.userId, v.score) }
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
