package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator

import com.ExTwitter.home_mixer.model.HomeFeatures._
import com.ExTwitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.ExTwitter.home_mixer.{thriftscala => hmt}
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.servo.cache.TtlCache
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.util.Return
import com.ExTwitter.util.Throw
import com.ExTwitter.util.Time

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fetch scored Tweets from cache and exclude the expired ones
 */
@Singleton
case class CachedScoredTweetsQueryFeatureHydrator @Inject() (
  scoredTweetsCache: TtlCache[Long, hmt.ScoredTweetsResponse])
    extends QueryFeatureHydrator[PipelineQuery] {

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
          val nonExpiredTweets = cachedScoredTweets.filter { tweet =>
            tweet.lastScoredTimestampMs.exists(Time.fromMilliseconds(_).untilNow < tweetScoreTtl)
          }
          FeatureMapBuilder().add(CachedScoredTweetsFeature, nonExpiredTweets).build()
        case Throw(exception) => throw exception
      }
    }
  }
}
