package com.twitter.home_mixer.util.tweetypie.content

import com.twitter.home_mixer.model.ContentFeatures
import com.twitter.tweetypie.{thriftscala => tp}

object FeatureExtractionHelper {

  def extractFeatures(
    tweet: tp.Tweet
  ): ContentFeatures = {
    val contentFeaturesFromTweet = ContentFeatures.Empty.copy(
      selfThreadMetadata = tweet.selfThreadMetadata
    )

    val contentFeaturesWithText = TweetTextFeaturesExtractor.addTextFeaturesFromTweet(
      contentFeaturesFromTweet,
      tweet
    )
    val contentFeaturesWithMedia = TweetMediaFeaturesExtractor.addMediaFeaturesFromTweet(
      contentFeaturesWithText,
      tweet
    )

    contentFeaturesWithMedia.copy(
      conversationControl = tweet.conversationControl,
      semanticCoreAnnotations = tweet.escherbirdEntityAnnotations.map(_.entityAnnotations)
    )
  }
}
