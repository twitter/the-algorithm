package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.transport.Transport
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.param.HomeMixerInjectionNames.ScoredTweetsCache
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.CachedScoredTweets
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.cache.TtlCache
import com.twitter.stitch.Stitch
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Time
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Fetch scored Tweets from cache and exclude the expired ones
 */
@Singleton
case class CachedScoredTweetsQueryFeatureHydrator @Inject() (
  @Named(ScoredTweetsCache)
  scoredTweetsCache: TtlCache[Long, hmt.ScoredTweetsResponse])
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("CachedScoredTweets")

  override val features: Set[Feature[_, _]] = Set(CachedScoredTweetsFeature)

  override def onlyIf(query: PipelineQuery): Boolean = {
    val serviceIdentifier = ServiceIdentifier.fromCertificate(Transport.peerCertificate)
    serviceIdentifier.role != "explore-mixer" && serviceIdentifier.role != "video-mixer"
  }

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
          FeatureMap(CachedScoredTweetsFeature, nonExpiredTweets)
        case Throw(exception) => throw exception
      }
    }
  }
}
