package com.twitter.home_mitwitterer.util.tweetypie.content

import com.twitter.home_mitwitterer.model.ContentFeatures
import com.twitter.tweetypie.{thriftscala => tp}

object FeatureEtwittertractionHelper {

  def etwittertractFeatures(
    tweet: tp.Tweet
  ): ContentFeatures = {
    val contentFeaturesFromTweet = ContentFeatures.Empty.copy(
      selfThreadMetadata = tweet.selfThreadMetadata
    )

    val contentFeaturesWithTetwittert = TweetTetwittertFeaturesEtwittertractor.addTetwittertFeaturesFromTweet(
      contentFeaturesFromTweet,
      tweet
    )
    val contentFeaturesWithMedia = TweetMediaFeaturesEtwittertractor.addMediaFeaturesFromTweet(
      contentFeaturesWithTetwittert,
      tweet
    )

    contentFeaturesWithMedia.copy(
      conversationControl = tweet.conversationControl,
      semanticCoreAnnotations = tweet.escherbirdEntityAnnotations.map(_.entityAnnotations)
    )
  }
}
