package com.X.visibility.builder.tweets

import com.X.communities.moderation.thriftscala.CommunityTweetModerationState
import com.X.communities.moderation.thriftscala.CommunityUserModerationState
import com.X.communities.visibility.thriftscala.CommunityVisibilityFeatures
import com.X.communities.visibility.thriftscala.CommunityVisibilityFeaturesV1
import com.X.communities.visibility.thriftscala.CommunityVisibilityResult
import com.X.stitch.Stitch
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.builder.FeatureMapBuilder
import com.X.visibility.common.CommunitiesSource
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

class CommunityTweetFeaturesV2(communitiesSource: CommunitiesSource)
    extends CommunityTweetFeatures {
  private[this] def forCommunityTweet(
    communityTweet: CommunityTweet
  ): FeatureMapBuilder => FeatureMapBuilder = { builder: FeatureMapBuilder =>
    {
      val communityVisibilityFeaturesStitch =
        communitiesSource.getCommunityVisibilityFeatures(communityTweet.communityId)
      val communityTweetModerationStateStitch =
        communitiesSource.getTweetModerationState(communityTweet.tweet.id)
      val communityTweetAuthorModerationStateStitch =
        communitiesSource.getUserModerationState(
          communityTweet.authorId,
          communityTweet.communityId
        )

      def getFlagFromFeatures(f: CommunityVisibilityFeaturesV1 => Boolean): Stitch[Boolean] =
        communityVisibilityFeaturesStitch.map {
          case Some(CommunityVisibilityFeatures.V1(v1)) => f(v1)
          case _ => false
        }

      def getFlagFromCommunityVisibilityResult(
        f: CommunityVisibilityResult => Boolean
      ): Stitch[Boolean] = getFlagFromFeatures { v =>
        f(v.communityVisibilityResult)
      }

      builder
        .withConstantFeature(
          TweetIsCommunityTweet,
          true
        )
        .withFeature(
          CommunityTweetCommunityNotFound,
          getFlagFromCommunityVisibilityResult {
            case CommunityVisibilityResult.NotFound => true
            case _ => false
          }
        )
        .withFeature(
          CommunityTweetCommunitySuspended,
          getFlagFromCommunityVisibilityResult {
            case CommunityVisibilityResult.Suspended => true
            case _ => false
          }
        )
        .withFeature(
          CommunityTweetCommunityDeleted,
          getFlagFromCommunityVisibilityResult {
            case CommunityVisibilityResult.Deleted => true
            case _ => false
          }
        )
        .withFeature(
          CommunityTweetCommunityVisible,
          getFlagFromCommunityVisibilityResult {
            case CommunityVisibilityResult.Visible => true
            case _ => false
          }
        )
        .withFeature(
          ViewerIsInternalCommunitiesAdmin,
          getFlagFromFeatures { _.viewerIsInternalAdmin }
        )
        .withFeature(
          ViewerIsCommunityAdmin,
          getFlagFromFeatures { _.viewerIsCommunityAdmin }
        )
        .withFeature(
          ViewerIsCommunityModerator,
          getFlagFromFeatures { _.viewerIsCommunityModerator }
        )
        .withFeature(
          ViewerIsCommunityMember,
          getFlagFromFeatures { _.viewerIsCommunityMember }
        )
        .withFeature(
          CommunityTweetIsHidden,
          communityTweetModerationStateStitch.map {
            case Some(CommunityTweetModerationState.Hidden(_)) => true
            case _ => false
          }
        )
        .withFeature(
          CommunityTweetAuthorIsRemoved,
          communityTweetAuthorModerationStateStitch.map {
            case Some(CommunityUserModerationState.Removed(_)) => true
            case _ => false
          }
        )
    }
  }

  def forTweet(
    tweet: Tweet,
    viewerContext: ViewerContext
  ): FeatureMapBuilder => FeatureMapBuilder = {
    CommunityTweet(tweet) match {
      case None => forNonCommunityTweet()
      case Some(communityTweet) => forCommunityTweet(communityTweet)
    }
  }
}
