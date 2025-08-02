package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures._
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mitwitterer.{thriftscala => hmt}
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.TtlCache
import com.twitter.stitch.Stitch
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Time

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Fetch scored Tweets from cache and etwitterclude the etwitterpired ones
 */
@Singleton
case class CachedScoredTweetsQueryFeatureHydrator @Inject() (
  scoredTweetsCache: TtlCache[Long, hmt.ScoredTweetsResponse])
    etwittertends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("CachedScoredTweets")

  override val features: Set[Feature[_, _]] = Set(CachedScoredTweetsFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    val tweetScoreTtl = query.params(CachedScoredTweets.TTLParam)

    Stitch.callFuture(scoredTweetsCache.get(Seq(userId))).map { keyValueResult =>
      keyValueResult(userId) match {
        case Return(cachedCandidatesOpt) =>
          val cachedScoredTweets = cachedCandidatesOpt.map(_.scoredTweets).getOrElse(Seq.empty)
          val nonEtwitterpiredTweets = cachedScoredTweets.filter { tweet =>
            tweet.lastScoredTimestampMs.etwitterists(Time.fromMilliseconds(_).untilNow < tweetScoreTtl)
          }
          FeatureMapBuilder().add(CachedScoredTweetsFeature, nonEtwitterpiredTweets).build()
        case Throw(etwitterception) => throw etwitterception
      }
    }
  }
}
