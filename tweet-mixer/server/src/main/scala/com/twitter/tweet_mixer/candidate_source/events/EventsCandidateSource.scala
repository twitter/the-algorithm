package com.twitter.tweet_mixer.candidate_source.events

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.recommendations.simclusters_v2.TopPostsPerEventClientColumn
import com.twitter.tweet_mixer.candidate_source.events.EventsCandidateSource.cache
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.util.Random

object EventsCandidateSource {
  private val BaseTTL = 4
  private val TTL = (BaseTTL + Random.nextInt(3)).minutes

  val cache: InProcessCache[EventsRequest, Seq[TweetMixerCandidate]] =
    new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 100)
}

case class EventsRequest(
  key: TopPostsPerEventClientColumn.Key,
  irrelevanceDownrank: Double)

@Singleton
class EventsCandidateSource @Inject() (
  @Named("StratoClientWithModerateTimeout") stratoClient: Client)
    extends CandidateSource[
      EventsRequest,
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("Events")

  private val fetcher: Fetcher[
    TopPostsPerEventClientColumn.Key,
    Unit,
    TopPostsPerEventClientColumn.Value
  ] = new TopPostsPerEventClientColumn(stratoClient).fetcher

  override def apply(
    request: EventsRequest
  ): Stitch[Seq[TweetMixerCandidate]] = OffloadFuturePools.offloadStitch {
    cache.get(request).map(Stitch.value(_)).getOrElse {
      fetcher.fetch(request.key).map { result =>
        val posts = result.v.getOrElse(Seq.empty)

        val candidates = posts.map { post =>
          TweetMixerCandidate(
            tweetId = post.tweetId,
            score = if (post.isRelevant) post.score else post.score * request.irrelevanceDownrank,
            seedId = 0L
          )
        }
        cache.set(request, candidates)
        candidates
      }
    }
  }
}
