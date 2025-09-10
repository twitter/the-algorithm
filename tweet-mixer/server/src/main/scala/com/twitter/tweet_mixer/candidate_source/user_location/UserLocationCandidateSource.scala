package com.twitter.tweet_mixer.candidate_source.user_location

import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyViewFetcherSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.timelines.local.FetchLocalPostsClientColumn
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationCandidateSource @Inject() (
  fetchLocalPostsClientColumn: FetchLocalPostsClientColumn)
    extends StratoKeyViewFetcherSource[
      FetchLocalPostsClientColumn.Key,
      FetchLocalPostsClientColumn.View,
      FetchLocalPostsClientColumn.Value,
      TweetMixerCandidate
    ] {

  override val fetcher: Fetcher[
    FetchLocalPostsClientColumn.Key,
    FetchLocalPostsClientColumn.View,
    FetchLocalPostsClientColumn.Value
  ] = fetchLocalPostsClientColumn.fetcher

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("UserLocation")
  private val DefaultSeedId = 0
  private val MaxSearchResults = 1000

  override protected def stratoResultTransformer(
    stratoKey: FetchLocalPostsClientColumn.Key,
    stratoResult: FetchLocalPostsClientColumn.Value
  ): Seq[TweetMixerCandidate] = {
    stratoResult.search
      .take(MaxSearchResults)
      .map { post =>
        TweetMixerCandidate(
          tweetId = post.id,
          score = post.score,
          seedId = post.cityId.getOrElse(DefaultSeedId)
        )
      }
  }
}
