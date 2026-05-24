package com.twitter.home_mixer.product.for_you.candidate_source

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.trends.trip.GrokTopicTweetsClientColumn
import javax.inject.Inject
import javax.inject.Singleton

case class TuneFeedRequest(
  grokTopic: Long,
  language: Option[String],
  placeId: Option[Long])

/*
 * Takes 3 grok topics the user is following at random, and fetches 1 post for each.
 */
@Singleton
class TuneFeedCandidateSource @Inject() (
  grokTopicTweetsStratoColumn: GrokTopicTweetsClientColumn)
    extends CandidateSource[
      TuneFeedRequest,
      TweetCandidate
    ] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("TuneFeed")

  private val fetcher = grokTopicTweetsStratoColumn.fetcher

  private val MaxModuleTweets = 20

  override def apply(tuneFeedRequest: TuneFeedRequest): Stitch[Seq[TweetCandidate]] = {
    val key = GrokTopicTweetsClientColumn.Key(
      language = tuneFeedRequest.language,
      placeId = tuneFeedRequest.placeId,
      topicId = tuneFeedRequest.grokTopic
    )

    fetcher.fetch(key, {}).map { response =>
      response.v
        .map(_.map { tweetId =>
          TweetCandidate(tweetId)
        })
        .getOrElse(Seq.empty).take(MaxModuleTweets)
    }
  }
}
