package com.twitter.visibility.builder.tweets

import com.twitter.servo.util.Gate
import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.builder.FeatureMapBuilder
import com.twitter.visibility.models.ViewerContext

class CommunityTweetFeaturesPartitioned(
  a: CommunityTweetFeatures,
  b: CommunityTweetFeatures,
  bEnabled: Gate[Unit],
) extends CommunityTweetFeatures {
  override def forTweet(
    tweet: Tweet,
    viewerContext: ViewerContext
  ): FeatureMapBuilder => FeatureMapBuilder =
    bEnabled.pick(
      b.forTweet(tweet, viewerContext),
      a.forTweet(tweet, viewerContext),
    )

  override def forTweetOnly(tweet: Tweet): FeatureMapBuilder => FeatureMapBuilder = bEnabled.pick(
    b.forTweetOnly(tweet),
    a.forTweetOnly(tweet),
  )
}
