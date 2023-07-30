package com.X.home_mixer.util.tweetypie.content

import com.X.home_mixer.model.ContentFeatures
import com.X.tweetypie.{thriftscala => tp}

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
