package com.twitter.visibility.builder.tweets

import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.builder.FeatureMapBuilder
import com.twitter.visibility.features.CommunityTweetAuthorIsRemoved
import com.twitter.visibility.features.CommunityTweetCommunityNotFound
import com.twitter.visibility.features.CommunityTweetCommunityDeleted
import com.twitter.visibility.features.CommunityTweetCommunitySuspended
import com.twitter.visibility.features.CommunityTweetCommunityVisible
import com.twitter.visibility.features.CommunityTweetIsHidden
import com.twitter.visibility.features.TweetIsCommunityTweet
import com.twitter.visibility.features.ViewerIsCommunityAdmin
import com.twitter.visibility.features.ViewerIsCommunityMember
import com.twitter.visibility.features.ViewerIsCommunityModerator
import com.twitter.visibility.features.ViewerIsInternalCommunitiesAdmin
import com.twitter.visibility.models.CommunityTweet
import com.twitter.visibility.models.ViewerContext

trait CommunityTweetFeatures {

  def forTweet(
    tweet: Tweet,
    viewerContext: ViewerContext
  ): FeatureMapBuilder => FeatureMapBuilder

  def forTweetOnly(tweet: Tweet): FeatureMapBuilder => FeatureMapBuilder = {
    _.withConstantFeature(
      TweetIsCommunityTweet,
      CommunityTweet(tweet).isDefined
    )
  }

  protected def forNonCommunityTweet(): FeatureMapBuilder => FeatureMapBuilder = { builder =>
    builder
      .withConstantFeature(
        TweetIsCommunityTweet,
        false
      ).withConstantFeature(
        CommunityTweetCommunityNotFound,
        false
      ).withConstantFeature(
        CommunityTweetCommunitySuspended,
        false
      ).withConstantFeature(
        CommunityTweetCommunityDeleted,
        false
      ).withConstantFeature(
        CommunityTweetCommunityVisible,
        false
      ).withConstantFeature(
        ViewerIsInternalCommunitiesAdmin,
        false
      ).withConstantFeature(
        ViewerIsCommunityAdmin,
        false
      ).withConstantFeature(
        ViewerIsCommunityModerator,
        false
      ).withConstantFeature(
        ViewerIsCommunityMember,
        false
      ).withConstantFeature(
        CommunityTweetIsHidden,
        false
      ).withConstantFeature(
        CommunityTweetAuthorIsRemoved,
        false
      )
  }
}
