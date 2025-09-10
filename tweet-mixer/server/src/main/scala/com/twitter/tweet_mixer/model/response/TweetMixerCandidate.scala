package com.twitter.tweet_mixer.model.response

import com.twitter.tweet_mixer.utils.Utils

case class TweetMixerCandidate(
  tweetId: Long,
  score: Double,
  seedId: Long)

object TweetMixerCandidate {
  private val keyFn: TweetMixerCandidate => Long = candidate => candidate.tweetId
  def interleave(candidates: Seq[Seq[TweetMixerCandidate]]): Seq[TweetMixerCandidate] =
    Utils.interleave(candidates, keyFn)
}
