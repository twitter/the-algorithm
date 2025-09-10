package com.twitter.tweet_mixer.candidate_source.evergreen_videos

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.evergreen_videos.semantic_video.AnnOnTweetClientColumn
import com.twitter.strato.generated.client.evergreen_videos.semantic_video.AnnOnTweetClientColumn.IdAndScore
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SemanticVideoCandidateSource @Inject() (
  stratoColumn: AnnOnTweetClientColumn)
    extends CandidateSource[EvergreenVideosSearchByTweetQuery, TweetMixerCandidate] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("SemanticVideo")

  private def isTextEligible(text: String): Boolean = {
    val words = text.split("\\s+")
    words.length >= 3 || text.length >= 10
  }

  override def apply(
    request: EvergreenVideosSearchByTweetQuery
  ): Stitch[Seq[TweetMixerCandidate]] = {
    request.textMap
      .map { textMap =>
        Stitch
          .traverse(request.tweetIds) { tweetId =>
            val text = textMap.getOrElse(tweetId, "")
            if (isTextEligible(text)) {
              stratoColumn.fetcher.fetch(tweetId).map { response =>
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
            } else {
              Stitch.value(Seq.empty)
            }
          }.map { resultLists =>
            // round robin flatten
            resultLists
              .flatMap(list => list.zipWithIndex)
              .sortBy(_._2)
              .map(_._1)
              .take(request.size)
          }
      }.getOrElse(Stitch.value(Seq.empty))
  }
}
