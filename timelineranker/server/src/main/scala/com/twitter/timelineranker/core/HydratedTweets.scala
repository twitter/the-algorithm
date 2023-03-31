package com.twitter.timelineranker.core

import com.twitter.timelines.model.tweet.HydratedTweet

case class HydratedTweets(
  outerTweets: Seq[HydratedTweet],
  innerTweets: Seq[HydratedTweet] = Seq.empty)
