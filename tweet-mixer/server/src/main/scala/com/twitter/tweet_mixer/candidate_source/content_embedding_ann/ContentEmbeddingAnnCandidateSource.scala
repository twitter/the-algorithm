package com.twitter.tweet_mixer.candidate_source.content_embedding_ann

import com.twitter.conversions.DurationOps.richDurationFromInt
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.servo.cache.ExpiringLruInProcessCache
import com.twitter.servo.cache.InProcessCache
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.searchai.candidatesources.RealtimeTopCandidatesBlendedOnTweetClientColumn
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import scala.util.Random

object ContentEmbeddingAnnCandidateSource {
  private val BaseTTL = 5
  private val TTL = (BaseTTL + Random.nextInt(5)).minutes

  val cache: InProcessCache[ContentEmbeddingAnnQuery, Seq[TweetMixerCandidate]] =
    new ExpiringLruInProcessCache(ttl = TTL, maximumSize = 500)
}

case class ContentEmbeddingAnnQuery(
  seedPostId: Seq[Long],
  maxCandidates: Int,
  minScore: Double,
  maxScore: Double,
  countryCode: String,
  languageCode: String,
  decayByCountry: Boolean,
  includeMediaSource: Boolean,
  includeTextSource: Boolean)

@Singleton
class ContentEmbeddingAnnCandidateSource @Inject() (
  @Named("StratoClientWithModerateTimeout") stratoClient: Client)
    extends CandidateSource[
      ContentEmbeddingAnnQuery,
      TweetMixerCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "ContentEmbeddingAnn")

  private val fetcher: Fetcher[
    RealtimeTopCandidatesBlendedOnTweetClientColumn.Key,
    RealtimeTopCandidatesBlendedOnTweetClientColumn.View,
    RealtimeTopCandidatesBlendedOnTweetClientColumn.Value
  ] = new RealtimeTopCandidatesBlendedOnTweetClientColumn(stratoClient).fetcher

  override def apply(
    key: ContentEmbeddingAnnQuery
  ): Stitch[Seq[TweetMixerCandidate]] = OffloadFuturePools.offloadStitch {
    Stitch
      .traverse(key.seedPostId) { id =>
        fetcher
          .fetch(
            id,
            RealtimeTopCandidatesBlendedOnTweetClientColumn.View(
              limit = key.maxCandidates,
              minScoreThreshold = key.minScore,
              maxSimilarity = key.maxScore,
              countryCode = key.countryCode,
              languageCode = key.languageCode,
              decayByCountry = key.decayByCountry,
              includeMediaSource = key.includeMediaSource,
              includeTextSource = key.includeTextSource
            )
          ).map { result =>
            val candidates = result.v.getOrElse(Seq.empty).map { post =>
              TweetMixerCandidate(
                tweetId = post.tweetId,
                score = post.score,
                seedId = id
              )
            }
            ContentEmbeddingAnnCandidateSource.cache.set(key, candidates)
            candidates
          }.liftToOption().map { v =>
            v.getOrElse(Seq.empty)
          }
      }.map(TweetMixerCandidate.interleave)
  }
}
