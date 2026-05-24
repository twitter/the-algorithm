package com.twitter.home_mixer.product.scored_tweets.side_effect
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.side_effect.BaseCacheCandidateFeaturesSideEffect
import com.twitter.home_mixer.param.HomeMixerFlagName.DataRecordMetadataStoreConfigsYmlFlag
import com.twitter.home_mixer.param.HomeMixerInjectionNames._
import com.twitter.inject.annotations.Flag
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.simclusters_v2.thriftscala.TwhinTweetEmbedding
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus.Store
import com.twitter.strato.generated.client.videoRecommendations.twitterClip.TwitterClipEmbeddingMhClientColumn
import com.twitter.timelines.served_candidates_logging.{thriftscala => sc}
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pdr}
import com.twitter.twistly.thriftscala.VideoViewEngagementType
import com.twitter.twistly.thriftscala.WatchTimeMetadata
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CacheCandidateFeaturesSideEffect @Inject() (
  @Flag(DataRecordMetadataStoreConfigsYmlFlag) dataRecordMetadataStoreConfigsYml: String,
  @Named(MemcacheCandidateFeaturesStore) store: Store[
    sc.CandidateFeatureKey,
    pdr.PolyDataRecord
  ],
  @Named(TweetWatchTimeMetadataStore) tweetWatchTimeMetadataStore: ReadableStore[
    (Long, VideoViewEngagementType),
    WatchTimeMetadata
  ],
  twitterClipEmbeddingMhClientColumn: TwitterClipEmbeddingMhClientColumn,
  @Named(TwhinVideoEmbeddingsStore) twhinVideoStore: ReadableStore[Long, TwhinTweetEmbedding],
  statsReceiver: StatsReceiver)
    extends BaseCacheCandidateFeaturesSideEffect(
      dataRecordMetadataStoreConfigsYml,
      store,
      tweetWatchTimeMetadataStore,
      twitterClipEmbeddingMhClientColumn,
      twhinVideoStore,
      statsReceiver) {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("CacheCandidateFeatures")

  override val statScope: String = getClass.getSimpleName
}
