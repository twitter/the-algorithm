package com.twitter.cr_mitwitterer.module.similarity_engine

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithScore
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.param.decider.CrMitwittererDecider
import com.twitter.cr_mitwitterer.param.decider.DeciderConstants
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine._
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.keyHasher
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.DeciderConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.GatingConfig
import com.twitter.cr_mitwitterer.similarity_engine.SimilarityEngine.SimilarityEngineConfig
import com.twitter.cr_mitwitterer.similarity_engine.StandardSimilarityEngine
import com.twitter.cr_mitwitterer.similarity_engine.TweetBasedQigSimilarityEngine
import com.twitter.cr_mitwitterer.thriftscala.SimilarityEngineType
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.qig_ranker.thriftscala.QigRanker
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object TweetBasedQigSimilarityEngineModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(ModuleNames.TweetBasedQigSimilarityEngine)
  def providesTweetBasedQigSimilarTweetsCandidateSource(
    qigRanker: QigRanker.MethodPerEndpoint,
    @Named(ModuleNames.UnifiedCache) crMitwittererUnifiedCacheClient: MemcachedClient,
    timeoutConfig: TimeoutConfig,
    statsReceiver: StatsReceiver,
    decider: CrMitwittererDecider
  ): StandardSimilarityEngine[
    TweetBasedQigSimilarityEngine.Query,
    TweetWithScore
  ] = {
    new StandardSimilarityEngine[
      TweetBasedQigSimilarityEngine.Query,
      TweetWithScore
    ](
      implementingStore = TweetBasedQigSimilarityEngine(qigRanker, statsReceiver),
      identifier = SimilarityEngineType.Qig,
      globalStats = statsReceiver,
      engineConfig = SimilarityEngineConfig(
        timeout = timeoutConfig.similarityEngineTimeout,
        gatingConfig = GatingConfig(
          deciderConfig =
            Some(DeciderConfig(decider, DeciderConstants.enableQigSimilarTweetsTrafficDeciderKey)),
          enableFeatureSwitch = None
        )
      ),
      memCacheConfig = Some(
        MemCacheConfig(
          cacheClient = crMitwittererUnifiedCacheClient,
          ttl = 10.minutes,
          keyToString = { k =>
            f"TweetBasedQIGRanker:${keyHasher.hashKey(k.sourceId.toString.getBytes)}%twitter"
          }
        )
      )
    )
  }
}
