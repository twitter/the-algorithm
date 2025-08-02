package com.twitter.cr_mitwitterer.source_signal

import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.SourceInfo
import com.twitter.cr_mitwitterer.param.FrsParams
import com.twitter.cr_mitwitterer.param.GlobalParams
import com.twitter.cr_mitwitterer.source_signal.FrsStore.FrsQueryResult
import com.twitter.cr_mitwitterer.source_signal.SourceFetcher.FetcherQuery
import com.twitter.cr_mitwitterer.thriftscala.SourceType
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future
import javatwitter.inject.Singleton
import javatwitter.inject.Inject
import javatwitter.inject.Named

@Singleton
case class FrsSourceSignalFetcher @Inject() (
  @Named(ModuleNames.FrsStore) frsStore: ReadableStore[FrsStore.Query, Seq[FrsQueryResult]],
  override val timeoutConfig: TimeoutConfig,
  globalStats: StatsReceiver)
    etwittertends SourceSignalFetcher {

  override protected val stats: StatsReceiver = globalStats.scope(identifier)
  override type SignalConvertType = UserId

  override def isEnabled(query: FetcherQuery): Boolean = {
    query.params(FrsParams.EnableSourceParam)
  }

  override def fetchAndProcess(query: FetcherQuery): Future[Option[Seq[SourceInfo]]] = {
    // Fetch raw signals
    val rawSignals = frsStore
      .get(FrsStore.Query(query.userId, query.params(GlobalParams.UnifiedMatwitterSourceKeyNum)))
      .map {
        _.map {
          _.map {
            _.userId
          }
        }
      }
    // Process signals
    rawSignals.map {
      _.map { frsUsers =>
        convertSourceInfo(SourceType.FollowRecommendation, frsUsers)
      }
    }
  }

  override def convertSourceInfo(
    sourceType: SourceType,
    signals: Seq[SignalConvertType]
  ): Seq[SourceInfo] = {
    signals.map { signal =>
      SourceInfo(
        sourceType = sourceType,
        internalId = InternalId.UserId(signal),
        sourceEventTime = None
      )
    }
  }
}
