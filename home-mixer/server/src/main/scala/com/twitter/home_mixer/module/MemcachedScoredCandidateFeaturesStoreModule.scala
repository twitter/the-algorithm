package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.Store
import com.twitter.timelines.clients.memcache_common.StorehausMemcacheConfig
import com.twitter.timelines.ml.cont_train.common.client.scored_candidate_features_cache.ScoredCandidateFeaturesMemcacheBuilder
import com.twitter.timelines.served_candidates_logging.{thriftscala => scl}
import com.twitter.timelines.suggests.common.poly_data_record.{thriftjava => pdr}
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MemcacheCandidateFeaturesStore
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MemcacheVideoCandidateFeaturesStore
import com.twitter.finagle.memcached.compressing.scheme.Lz4
import javax.inject.Named
import javax.inject.Singleton

object MemcachedScoredCandidateFeaturesStoreModule extends TwitterModule {

  private val ScoredTweetsProdDestName =
    "/srv#/prod/local/cache/timelines_scored_candidate_features:twemcaches"
  private val ScoredTweetsStagingDestName =
    "/srv#/test/local/cache/twemcache_timelines_scored_candidate_features:twemcaches"

  private val ScoredVideoProdDestName =
    "/srv#/prod/local/cache/timelines_scored_video_candidate_features:twemcaches"
  private val ScoredVideoStagingDestName =
    "/srv#/test/local/cache/twemcache_timelines_scored_video_candidate_features:twemcaches"

  @Singleton
  @Provides
  @Named(MemcacheCandidateFeaturesStore)
  def providesMemcachedScoredCandidateFeaturesStore(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Store[scl.CandidateFeatureKey, pdr.PolyDataRecord] = {
    val destName = serviceIdentifier.environment.toLowerCase match {
      case "prod" => ScoredTweetsProdDestName
      case _ => ScoredTweetsStagingDestName
    }
    buildCacheClient(serviceIdentifier, statsReceiver, destName)
  }

  @Singleton
  @Provides
  @Named(MemcacheVideoCandidateFeaturesStore)
  def providesMemcachedScoredVideoCandidateFeaturesStore(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): Store[scl.CandidateFeatureKey, pdr.PolyDataRecord] = {
    val destName = serviceIdentifier.environment.toLowerCase match {
      case "prod" => ScoredVideoProdDestName
      case _ => ScoredVideoStagingDestName
    }
    buildCacheClient(serviceIdentifier, statsReceiver, destName)
  }

  private def buildCacheClient(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver,
    destName: String,
  ): Store[scl.CandidateFeatureKey, pdr.PolyDataRecord] = {

    new ScoredCandidateFeaturesMemcacheBuilder(
      config = StorehausMemcacheConfig(
        destName = destName,
        keyPrefix = "",
        requestTimeout = 200.milliseconds,
        numTries = 2,
        globalTimeout = 500.milliseconds,
        tcpConnectTimeout = 20.milliseconds,
        connectionAcquisitionTimeout = 150.milliseconds,
        numPendingRequests = 200,
        isReadOnly = false,
        serviceIdentifier = serviceIdentifier,
        numConnections = 1,
        compressionScheme = Lz4
      ),
      ttl = 5.minute,
      statsReceiver = statsReceiver
    ).build()
  }
}
