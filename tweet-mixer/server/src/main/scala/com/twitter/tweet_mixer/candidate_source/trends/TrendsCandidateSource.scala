package com.twitter.tweet_mixer.candidate_source.trends

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.simclusters_v2.TopPostsPerCountryClientColumn
import com.twitter.tweet_mixer.candidate_source.trends.TrendsCandidateSource.cache
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.util.Random

object TrendsCandidateSource {
  private val BaseTTL = 5
  private val TTL = (BaseTTL + Random.nextInt(5)).minutes

  val cache: InProcessCache[String, Seq[TweetMixerCandidate]] =
    new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 500)
}

@Singleton
class TrendsCandidateSource @Inject() (
  @Named("StratoClientWithModerateTimeout") stratoClient: Client)
    extends CandidateSource[
      TopPostsPerCountryClientColumn.Key,
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("Trends")

  private val fetcher: Fetcher[
    TopPostsPerCountryClientColumn.Key,
    TopPostsPerCountryClientColumn.View,
    TopPostsPerCountryClientColumn.Value
  ] = new TopPostsPerCountryClientColumn(stratoClient).fetcher

  private val DefaultScore = 0

  override def apply(
    key: String
  ): Stitch[Seq[TweetMixerCandidate]] = OffloadFuturePools.offloadStitch {
    cache.get(key).map(Stitch.value(_)).getOrElse {
      fetcher.fetch(key).map { result =>
        val candidates = result.v.getOrElse(Seq.empty).map { post =>
          TweetMixerCandidate(
            tweetId = post.tweetId,
            score = DefaultScore,
            seedId = post.clusterId
          )
        }
        cache.set(key, candidates)
        candidates
      }
    }
  }
}
