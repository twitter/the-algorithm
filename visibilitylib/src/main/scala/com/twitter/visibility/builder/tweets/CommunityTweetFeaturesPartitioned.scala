package com.twitter.visibility.builder.tweets

import com.twitter.servo.util.Gate
import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.builder.FeatureMapBuilder
import com.twitter.visibility.models.ViewerContetwittert

class CommunityTweetFeaturesPartitioned(
  a: CommunityTweetFeatures,
  b: CommunityTweetFeatures,
  bEnabled: Gate[Unit],
) etwittertends CommunityTweetFeatures {
  override def forTweet(
    tweet: Tweet,
    viewerContetwittert: ViewerContetwittert
  ): FeatureMapBuilder => FeatureMapBuilder =
    bEnabled.pick(
      b.forTweet(tweet, viewerContetwittert),
      a.forTweet(tweet, viewerContetwittert),
    )

  override def forTweetOnly(tweet: Tweet): FeatureMapBuilder => FeatureMapBuilder = bEnabled.pick(
    b.forTweetOnly(tweet),
    a.forTweetOnly(tweet),
  )
}
