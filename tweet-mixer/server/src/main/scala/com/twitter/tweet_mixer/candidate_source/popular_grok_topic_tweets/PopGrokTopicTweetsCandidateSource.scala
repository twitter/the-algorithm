package com.twitter.tweet_mixer.candidate_source.popular_grok_topic_tweets

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.trends.trip.GrokTopicTweetRecommendationsClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopGrokTopicTweetsCandidateSource @Inject() (
  grokTopicTweetRecommendationsClientColumn: GrokTopicTweetRecommendationsClientColumn)
    extends CandidateSource[GrokTopicTweetsQuery, TweetCandidate] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("PopGrokTopicTweets")

  private val fetcher = grokTopicTweetRecommendationsClientColumn.fetcher

  override def apply(request: GrokTopicTweetsQuery): Stitch[Seq[TweetCandidate]] =
    OffloadFuturePools.offloadStitch {
      val key = GrokTopicTweetRecommendationsClientColumn.Key(
        userId = request.userId,
        language = request.language,
        placeId = request.placeId
      )
      fetcher.fetch(key, {}).map { response =>
        response.v.map(_.map(TweetCandidate(_)).take(request.maxNumCandidates))
          .getOrElse(Seq.empty)
      }
    }
}
