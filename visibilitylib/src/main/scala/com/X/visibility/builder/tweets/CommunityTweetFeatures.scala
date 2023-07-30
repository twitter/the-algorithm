package com.X.visibility.builder.tweets

import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.features.CommunityTweetAuthorIsRemoved
import com.X.visibility.features.CommunityTweetCommunityNotFound
import com.X.visibility.features.CommunityTweetCommunityDeleted
import com.X.visibility.features.CommunityTweetCommunitySuspended
import com.X.visibility.features.CommunityTweetCommunityVisible
import com.X.visibility.features.CommunityTweetIsHidden
import com.X.visibility.features.TweetIsCommunityTweet
import com.X.visibility.features.ViewerIsCommunityAdmin
import com.X.visibility.features.ViewerIsCommunityMember
import com.X.visibility.features.ViewerIsCommunityModerator
import com.X.visibility.features.ViewerIsInternalCommunitiesAdmin
import com.X.visibility.models.CommunityTweet
import com.X.visibility.models.ViewerContext

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
