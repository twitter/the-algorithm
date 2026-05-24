package com.twitter.tweet_mixer.candidate_source.evergreen_videos

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.evergreen_videos.twitter_clip_v0_long_video.AnnSearchClientColumn
import com.twitter.strato.generated.client.evergreen_videos.twitter_clip_v0_long_video.AnnSearchClientColumn.IdAndScore
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitterClipV0LongVideoCandidateSource @Inject() (
  stratoColumn: AnnSearchClientColumn)
    extends CandidateSource[EvergreenVideosSearchByTweetQuery, TweetMixerCandidate] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "TwitterClipV0LongVideo")

  override def apply(
    request: EvergreenVideosSearchByTweetQuery
  ): Stitch[Seq[TweetMixerCandidate]] = {
    OffloadFuturePools
      .offloadStitch {
        Stitch
          .traverse(request.tweetIds) { tweetId =>
            stratoColumn.fetcher
              .fetch(
                AnnSearchClientColumn.Key(
                  tweetId = tweetId,
                  size = Some(request.size)
                )).map { response =>
                response.v
                  .map { relatedTweets: Seq[IdAndScore] =>
                    relatedTweets.map(tweet =>
                      TweetMixerCandidate(
                        tweet.id,
                        tweet.score,
                        tweetId
                      ))
                  }.getOrElse(Seq.empty)
              }
          }
      }.map { resultLists =>
        // round robin flatten
        resultLists
          .flatMap(list => list.zipWithIndex)
          .sortBy(_._2)
          .map(_._1)
      }
  }
}
