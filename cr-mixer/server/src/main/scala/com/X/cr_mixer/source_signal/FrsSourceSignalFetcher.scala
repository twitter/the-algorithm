package com.X.cr_mixer.source_signal

import com.X.cr_mixer.config.TimeoutConfig
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.model.SourceInfo
import com.X.cr_mixer.param.FrsParams
import com.X.cr_mixer.param.GlobalParams
import com.X.cr_mixer.source_signal.FrsStore.FrsQueryResult
import com.X.cr_mixer.source_signal.SourceFetcher.FetcherQuery
import com.X.cr_mixer.thriftscala.SourceType
import com.X.finagle.stats.StatsReceiver
import com.X.simclusters_v2.common.UserId
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.storehaus.ReadableStore
import com.X.util.Future
import javax.inject.Singleton
import javax.inject.Inject
import javax.inject.Named

@Singleton
case class FrsSourceSignalFetcher @Inject() (
  @Named(ModuleNames.FrsStore) frsStore: ReadableStore[FrsStore.Query, Seq[FrsQueryResult]],
  override val timeoutConfig: TimeoutConfig,
  globalStats: StatsReceiver)
    extends SourceSignalFetcher {

  override protected val stats: StatsReceiver = globalStats.scope(identifier)
  override type SignalConvertType = UserId

  override def isEnabled(query: FetcherQuery): Boolean = {
    query.params(FrsParams.EnableSourceParam)
  }

  override def fetchAndProcess(query: FetcherQuery): Future[Option[Seq[SourceInfo]]] = {
    // Fetch raw signals
    val rawSignals = frsStore
      .get(FrsStore.Query(query.userId, query.params(GlobalParams.UnifiedMaxSourceKeyNum)))
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
