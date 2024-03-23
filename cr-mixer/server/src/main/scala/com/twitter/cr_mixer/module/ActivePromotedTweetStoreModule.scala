package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.bijection.thrift.CompactThriftCodec
import com.ExTwitter.ads.entities.db.thriftscala.LineItemObjective
import com.ExTwitter.bijection.Injection
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.thriftscala.LineItemInfo
import com.ExTwitter.finagle.memcached.{Client => MemcachedClient}
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.hermit.store.common.ObservedCachedReadableStore
import com.ExTwitter.hermit.store.common.ObservedMemcachedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.ml.api.DataType
import com.ExTwitter.ml.api.Feature
import com.ExTwitter.ml.api.GeneralTensor
import com.ExTwitter.ml.api.RichDataRecord
import com.ExTwitter.relevance_platform.common.injection.LZ4Injection
import com.ExTwitter.relevance_platform.common.injection.SeqObjectInjection
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.storehaus_internal.manhattan.ManhattanRO
import com.ExTwitter.storehaus_internal.manhattan.ManhattanROConfig
import com.ExTwitter.storehaus_internal.manhattan.Revenue
import com.ExTwitter.storehaus_internal.util.ApplicationID
import com.ExTwitter.storehaus_internal.util.DatasetName
import com.ExTwitter.storehaus_internal.util.HDFSPath
import com.ExTwitter.util.Future
import javax.inject.Named
import scala.collection.JavaConverters._

object ActivePromotedTweetStoreModule extends ExTwitterModule {

  case class ActivePromotedTweetStore(
    activePromotedTweetMHStore: ReadableStore[String, DataRecord],
    statsReceiver: StatsReceiver)
      extends ReadableStore[TweetId, Seq[LineItemInfo]] {
    override def get(tweetId: TweetId): Future[Option[Seq[LineItemInfo]]] = {
      activePromotedTweetMHStore.get(tweetId.toString).map {
        _.map { dataRecord =>
          val richDataRecord = new RichDataRecord(dataRecord)
          val lineItemIdsFeature: Feature[GeneralTensor] =
            new Feature.Tensor("active_promoted_tweets.line_item_ids", DataType.INT64)

          val lineItemObjectivesFeature: Feature[GeneralTensor] =
            new Feature.Tensor("active_promoted_tweets.line_item_objectives", DataType.INT64)

          val lineItemIdsTensor: GeneralTensor = richDataRecord.getFeatureValue(lineItemIdsFeature)
          val lineItemObjectivesTensor: GeneralTensor =
            richDataRecord.getFeatureValue(lineItemObjectivesFeature)

          val lineItemIds: Seq[Long] =
            if (lineItemIdsTensor.getSetField == GeneralTensor._Fields.INT64_TENSOR && lineItemIdsTensor.getInt64Tensor.isSetLongs) {
              lineItemIdsTensor.getInt64Tensor.getLongs.asScala.map(_.toLong)
            } else Seq.empty

          val lineItemObjectives: Seq[LineItemObjective] =
            if (lineItemObjectivesTensor.getSetField == GeneralTensor._Fields.INT64_TENSOR && lineItemObjectivesTensor.getInt64Tensor.isSetLongs) {
              lineItemObjectivesTensor.getInt64Tensor.getLongs.asScala.map(objective =>
                LineItemObjective(objective.toInt))
            } else Seq.empty

          val lineItemInfo =
            if (lineItemIds.size == lineItemObjectives.size) {
              lineItemIds.zipWithIndex.map {
                case (lineItemId, index) =>
                  LineItemInfo(
                    lineItemId = lineItemId,
                    lineItemObjective = lineItemObjectives(index)
                  )
              }
            } else Seq.empty

          lineItemInfo
        }
      }
    }
  }

  @Provides
  @Singleton
  def providesActivePromotedTweetStore(
    manhattanKVClientMtlsParams: ManhattanKVClientMtlsParams,
    @Named(ModuleNames.UnifiedCache) crMixerUnifiedCacheClient: MemcachedClient,
    crMixerStatsReceiver: StatsReceiver
  ): ReadableStore[TweetId, Seq[LineItemInfo]] = {

    val mhConfig = new ManhattanROConfig {
      val hdfsPath = HDFSPath("")
      val applicationID = ApplicationID("ads_bigquery_features")
      val datasetName = DatasetName("active_promoted_tweets")
      val cluster = Revenue

      override def statsReceiver: StatsReceiver =
        crMixerStatsReceiver.scope("active_promoted_tweets_mh")
    }
    val mhStore: ReadableStore[String, DataRecord] =
      ManhattanRO
        .getReadableStoreWithMtls[String, DataRecord](
          mhConfig,
          manhattanKVClientMtlsParams
        )(
          implicitly[Injection[String, Array[Byte]]],
          CompactThriftCodec[DataRecord]
        )

    val underlyingStore =
      ActivePromotedTweetStore(mhStore, crMixerStatsReceiver.scope("ActivePromotedTweetStore"))
    val memcachedStore = ObservedMemcachedReadableStore.fromCacheClient(
      backingStore = underlyingStore,
      cacheClient = crMixerUnifiedCacheClient,
      ttl = 60.minutes,
      asyncUpdate = false
    )(
      valueInjection = LZ4Injection.compose(SeqObjectInjection[LineItemInfo]()),
      statsReceiver = crMixerStatsReceiver.scope("memCachedActivePromotedTweetStore"),
      keyToString = { k: TweetId => s"apt/$k" }
    )

    ObservedCachedReadableStore.from(
      memcachedStore,
      ttl = 30.minutes,
      maxKeys = 250000, // size of promoted tweet is around 200,000
      windowSize = 10000L,
      cacheName = "active_promoted_tweet_cache",
      maxMultiGetSize = 20
    )(crMixerStatsReceiver.scope("inMemoryCachedActivePromotedTweetStore"))

  }

}
