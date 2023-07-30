package com.X.visibility.builder.tweets

import com.X.servo.util.Gate
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.models.ViewerContext

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
