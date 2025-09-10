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
import com.twitter.strato.generated.client.trendsai.media.TopCountryVideosClientColumn
import com.twitter.tweet_mixer.candidate_source.trends.TrendsVideoCandidateSource.cache
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.util.Random

object TrendsVideoCandidateSource {
  private val BaseTTL = 5
  private val TTL = (BaseTTL + Random.nextInt(5)).minutes

  val cache: InProcessCache[String, Seq[TweetMixerCandidate]] =
    new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 500)
}

@Singleton
class TrendsVideoCandidateSource @Inject() (
  @Named("StratoClientWithModerateTimeout") stratoClient: Client)
    extends CandidateSource[
      TopCountryVideosClientColumn.Key,
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("TrendsVideo")

  private val fetcher: Fetcher[
    TopCountryVideosClientColumn.Key,
    TopCountryVideosClientColumn.View,
    TopCountryVideosClientColumn.Value
  ] = new TopCountryVideosClientColumn(stratoClient).fetcher

  override def apply(
    key: String
  ): Stitch[Seq[TweetMixerCandidate]] = OffloadFuturePools.offloadStitch {
    cache.get(key).map(Stitch.value(_)).getOrElse {
      fetcher.fetch(key).map { result =>
        val candidates = result.v.getOrElse(Seq.empty).map { post =>
          TweetMixerCandidate(
            tweetId = post.postId,
            score = post.score,
            seedId = post.trendId
          )
        }
        cache.set(key, candidates)
        candidates
      }
    }
  }
}
