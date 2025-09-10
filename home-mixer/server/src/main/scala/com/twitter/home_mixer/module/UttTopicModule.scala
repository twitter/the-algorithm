package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.escherbird.util.uttclient.CacheConfigV2
import com.twitter.escherbird.util.uttclient.CachedUttClientV2
import com.twitter.escherbird.util.uttclient.UttClientCacheConfigsV2
import com.twitter.escherbird.utt.strato.{thriftscala => utt}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithModerateTimeout
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ConfigRepoLocalPath
import com.twitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal
import com.twitter.strato.client.Client
import com.twitter.topiclisting.TopicListing
import com.twitter.topiclisting.TopicListingBuilder
import com.twitter.topiclisting.clients.utt.UttClient
import com.twitter.topiclisting.utt.UttLocalization
import com.twitter.topiclisting.utt.UttLocalizationImpl
import com.twitter.tsp.stores.LocalizedUttRecommendableTopicsStore
import com.twitter.tsp.stores.TopicStore
import com.twitter.tsp.stores.UttTopicFilterStore
import com.twitter.util.JavaTimer
import javax.inject.Named
import javax.inject.Singleton

object UttTopicModule extends TwitterModule {

  private val StatsScope = "UttTopic"

  private val optOutStratoStorePath: String = "interests/optOutInterests"

  private val notInterestedInStorePath: String = "interests/notInterestedTopicsGetter"

  @Provides
  @Singleton
  def providesTopicListing(
    statsReceiver: StatsReceiver,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    @Flag(ConfigRepoLocalPath) localConfigRepoPath: String
  ): TopicListing = {
    val configSourceBasePath = if (isServiceLocal) Some(localConfigRepoPath) else None
    new TopicListingBuilder(statsReceiver, configSourceBasePath).build
  }

  @Provides
  @Singleton
  def providesUttLocalization(
    @Named(BatchedStratoClientWithModerateTimeout)
    stratoClient: Client,
    topicListing: TopicListing,
    statsReceiver: StatsReceiver
  ): UttLocalization = {
    // used the same capacity as in currently used client from TSP
    val defaultCacheConfigV2 = CacheConfigV2(capacity = 262143)
    val uttClientCacheConfigsV2 = UttClientCacheConfigsV2(
      getTaxonomyConfig = defaultCacheConfigV2,
      getUttTaxonomyConfig = defaultCacheConfigV2,
      getLeafIds = defaultCacheConfigV2,
      getLeafUttEntities = defaultCacheConfigV2
    )

    lazy val cachedUttClientV2 = new CachedUttClientV2(
      stratoClient = stratoClient,
      env = utt.Environment.Prod,
      cacheConfigs = uttClientCacheConfigsV2,
      statsReceiver = statsReceiver.scope("CachedUttClient")
    )
    val uttClient = new UttClient(cachedUttClientV2, statsReceiver)

    new UttLocalizationImpl(topicListing, uttClient, statsReceiver.scope(StatsScope))
  }

  @Provides
  @Singleton
  def providesUttTopicFilterStore(
    @Named(BatchedStratoClientWithModerateTimeout)
    stratoClient: Client,
    uttLocalization: UttLocalization,
    topicListing: TopicListing,
    statsReceiver: StatsReceiver
  ): UttTopicFilterStore = {
    val userOptOutTopicsStore =
      TopicStore.userOptOutTopicStore(stratoClient, optOutStratoStorePath)(
        statsReceiver.scope("interests_opt_out_store"))
    val explicitFollowingTopicsStore =
      TopicStore.explicitFollowingTopicStore(stratoClient)(
        statsReceiver.scope("explicit_following_interests_store"))
    val userNotInterestedInTopicsStore =
      TopicStore.notInterestedInTopicsStore(stratoClient, notInterestedInStorePath)(
        statsReceiver.scope("not_interested_in_store"))
    val localizedUttRecommendableTopicsStore = new LocalizedUttRecommendableTopicsStore(
      uttLocalization)
    val timer = new JavaTimer(isDaemon = true)

    new UttTopicFilterStore(
      topicListing = topicListing,
      userOptOutTopicsStore = userOptOutTopicsStore,
      explicitFollowingTopicsStore = explicitFollowingTopicsStore,
      notInterestedTopicsStore = userNotInterestedInTopicsStore,
      localizedUttRecommendableTopicsStore = localizedUttRecommendableTopicsStore,
      timer = timer,
      stats = statsReceiver.scope("UttTopicFilterStore")
    )
  }
}
