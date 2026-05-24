package com.twitter.frigate.pushservice.refresh_handler.cross

import com.twitter.frigate.common.util.MrNtabCopyObjects
import com.twitter.frigate.common.util.MrPushCopyObjects
import com.twitter.frigate.common.util._
import com.twitter.frigate.thriftscala.CommonRecommendationType
import com.twitter.frigate.thriftscala.CommonRecommendationType._

object CandidateToCopy {

  // Static map from a CommonRecommendationType to set of eligible push notification copies
  private[cross] val rectypeToPushCopy: Map[CommonRecommendationType, Set[
    MRPushCopy
  ]] =
    Map[CommonRecommendationType, Set[MRPushCopy]](
      F1FirstdegreeTweet -> Set(
        MrPushCopyObjects.FirstDegreeJustTweetedBoldTitle
      ),
      F1FirstdegreePhoto -> Set(
        MrPushCopyObjects.FirstDegreePhotoJustTweetedBoldTitle
      ),
      F1FirstdegreeVideo -> Set(
        MrPushCopyObjects.FirstDegreeVideoJustTweetedBoldTitle
      ),
      TweetRetweet -> Set(
        MrPushCopyObjects.TweetRetweetWithOneDisplaySocialContextsWithText,
        MrPushCopyObjects.TweetRetweetWithTwoDisplaySocialContextsWithText,
        MrPushCopyObjects.TweetRetweetWithOneDisplayAndKOtherSocialContextsWithText
      ),
      TweetRetweetPhoto -> Set(
        MrPushCopyObjects.TweetRetweetPhotoWithOneDisplaySocialContextWithText,
        MrPushCopyObjects.TweetRetweetPhotoWithTwoDisplaySocialContextsWithText,
        MrPushCopyObjects.TweetRetweetPhotoWithOneDisplayAndKOtherSocialContextsWithText
      ),
      TweetRetweetVideo -> Set(
        MrPushCopyObjects.TweetRetweetVideoWithOneDisplaySocialContextWithText,
        MrPushCopyObjects.TweetRetweetVideoWithTwoDisplaySocialContextsWithText,
        MrPushCopyObjects.TweetRetweetVideoWithOneDisplayAndKOtherSocialContextsWithText
      ),
      TweetFavorite -> Set(
        MrPushCopyObjects.TweetLikeOneSocialContextWithText,
        MrPushCopyObjects.TweetLikeTwoSocialContextWithText,
        MrPushCopyObjects.TweetLikeMultipleSocialContextWithText
      ),
      TweetFavoritePhoto -> Set(
        MrPushCopyObjects.TweetLikePhotoOneSocialContextWithText,
        MrPushCopyObjects.TweetLikePhotoTwoSocialContextWithText,
        MrPushCopyObjects.TweetLikePhotoMultipleSocialContextWithText
      ),
      TweetFavoriteVideo -> Set(
        MrPushCopyObjects.TweetLikeVideoOneSocialContextWithText,
        MrPushCopyObjects.TweetLikeVideoTwoSocialContextWithText,
        MrPushCopyObjects.TweetLikeVideoMultipleSocialContextWithText
      ),
      UnreadBadgeCount -> Set(MrPushCopyObjects.UnreadBadgeCount),
      InterestBasedTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      InterestBasedPhoto -> Set(MrPushCopyObjects.RecommendedForYouPhoto),
      InterestBasedVideo -> Set(MrPushCopyObjects.RecommendedForYouVideo),
      UserFollow -> Set(
        MrPushCopyObjects.UserFollowWithOneSocialContext,
        MrPushCopyObjects.UserFollowWithTwoSocialContext,
        MrPushCopyObjects.UserFollowOneDisplayAndKOtherSocialContext
      ),
      HermitUser -> Set(
        MrPushCopyObjects.HermitUserWithOneSocialContext,
        MrPushCopyObjects.HermitUserWithTwoSocialContext,
        MrPushCopyObjects.HermitUserWithOneDisplayAndKOtherSocialContexts
      ),
      TriangularLoopUser -> Set(
        MrPushCopyObjects.TriangularLoopUserWithOneSocialContext,
        MrPushCopyObjects.TriangularLoopUserWithTwoSocialContexts,
        MrPushCopyObjects.TriangularLoopUserOneDisplayAndKotherSocialContext
      ),
      ForwardAddressbookUserFollow -> Set(MrPushCopyObjects.ForwardAddressBookUserFollow),
      NewsArticleNewsLanding -> Set(MrPushCopyObjects.NewsArticleNewsLandingCopy),
      TopicProofTweet -> Set(MrPushCopyObjects.TopicProofTweet),
      UserInterestinTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      UserInterestinPhoto -> Set(MrPushCopyObjects.RecommendedForYouPhoto),
      UserInterestinVideo -> Set(MrPushCopyObjects.RecommendedForYouVideo),
      TwistlyTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      TwistlyPhoto -> Set(MrPushCopyObjects.RecommendedForYouPhoto),
      TwistlyVideo -> Set(MrPushCopyObjects.RecommendedForYouVideo),
      ElasticTimelineTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      ElasticTimelinePhoto -> Set(MrPushCopyObjects.RecommendedForYouPhoto),
      ElasticTimelineVideo -> Set(MrPushCopyObjects.RecommendedForYouVideo),
      ExploreVideoTweet -> Set(MrPushCopyObjects.ExploreVideoTweet),
      List -> Set(MrPushCopyObjects.ListRecommendation),
      InterestBasedUserFollow -> Set(MrPushCopyObjects.UserFollowInterestBasedCopy),
      PastEmailEngagementTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      PastEmailEngagementPhoto -> Set(MrPushCopyObjects.RecommendedForYouPhoto),
      PastEmailEngagementVideo -> Set(MrPushCopyObjects.RecommendedForYouVideo),
      ExplorePush -> Set(MrPushCopyObjects.ExplorePush),
      ConnectTabPush -> Set(MrPushCopyObjects.ConnectTabPush),
      ConnectTabWithUserPush -> Set(MrPushCopyObjects.ConnectTabWithUserPush),
      AddressBookUploadPush -> Set(MrPushCopyObjects.AddressBookPush),
      InterestPickerPush -> Set(MrPushCopyObjects.InterestPickerPush),
      CompleteOnboardingPush -> Set(MrPushCopyObjects.CompleteOnboardingPush),
      GeoPopTweet -> Set(MrPushCopyObjects.GeoPopPushCopy),
      TagSpaceTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      FrsTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      TwhinTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      MrModelingBasedTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      DetopicTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      TweetImpressions -> Set(MrPushCopyObjects.TopTweetImpressions),
      TrendTweet -> Set(MrPushCopyObjects.TrendTweet),
      ReverseAddressbookTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      ForwardAddressbookTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      SpaceInNetwork -> Set(MrPushCopyObjects.SpaceHost),
      SpaceOutOfNetwork -> Set(MrPushCopyObjects.SpaceHost),
      SubscribedSearch -> Set(MrPushCopyObjects.SubscribedSearchTweet),
      TripGeoTweet -> Set(MrPushCopyObjects.TripGeoTweetPushCopy),
      CrowdSearchTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      Digest -> Set(MrPushCopyObjects.Digest),
      TripHqTweet -> Set(MrPushCopyObjects.TripHqTweetPushCopy)
    )

  // Static map from a push copy to set of eligible ntab copies
  private[cross] val pushcopyToNtabcopy: Map[MRPushCopy, Set[MRNtabCopy]] =
    Map[MRPushCopy, Set[MRNtabCopy]](
      MrPushCopyObjects.FirstDegreeJustTweetedBoldTitle -> Set(
        MrNtabCopyObjects.FirstDegreeTweetRecent),
      MrPushCopyObjects.FirstDegreePhotoJustTweetedBoldTitle -> Set(
        MrNtabCopyObjects.FirstDegreeTweetRecent
      ),
      MrPushCopyObjects.FirstDegreeVideoJustTweetedBoldTitle -> Set(
        MrNtabCopyObjects.FirstDegreeTweetRecent
      ),
      MrPushCopyObjects.TweetRetweetWithOneDisplaySocialContextsWithText -> Set(
        MrNtabCopyObjects.TweetRetweetWithOneDisplaySocialContext
      ),
      MrPushCopyObjects.TweetRetweetWithTwoDisplaySocialContextsWithText -> Set(
        MrNtabCopyObjects.TweetRetweetWithTwoDisplaySocialContexts
      ),
      MrPushCopyObjects.TweetRetweetWithOneDisplayAndKOtherSocialContextsWithText -> Set(
        MrNtabCopyObjects.TweetRetweetWithOneDisplayAndKOtherSocialContexts
      ),
      MrPushCopyObjects.TweetRetweetPhotoWithOneDisplaySocialContextWithText -> Set(
        MrNtabCopyObjects.TweetRetweetPhotoWithOneDisplaySocialContext
      ),
      MrPushCopyObjects.TweetRetweetPhotoWithTwoDisplaySocialContextsWithText -> Set(
        MrNtabCopyObjects.TweetRetweetPhotoWithTwoDisplaySocialContexts
      ),
      MrPushCopyObjects.TweetRetweetPhotoWithOneDisplayAndKOtherSocialContextsWithText -> Set(
        MrNtabCopyObjects.TweetRetweetPhotoWithOneDisplayAndKOtherSocialContexts
      ),
      MrPushCopyObjects.TweetRetweetVideoWithOneDisplaySocialContextWithText -> Set(
        MrNtabCopyObjects.TweetRetweetVideoWithOneDisplaySocialContext
      ),
      MrPushCopyObjects.TweetRetweetVideoWithTwoDisplaySocialContextsWithText -> Set(
        MrNtabCopyObjects.TweetRetweetVideoWithTwoDisplaySocialContexts
      ),
      MrPushCopyObjects.TweetRetweetVideoWithOneDisplayAndKOtherSocialContextsWithText -> Set(
        MrNtabCopyObjects.TweetRetweetVideoWithOneDisplayAndKOtherSocialContexts
      ),
      MrPushCopyObjects.TweetLikeOneSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikeWithOneDisplaySocialContext
      ),
      MrPushCopyObjects.TweetLikeTwoSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikeWithTwoDisplaySocialContexts
      ),
      MrPushCopyObjects.TweetLikeMultipleSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikeWithOneDisplayAndKOtherSocialContexts
      ),
      MrPushCopyObjects.TweetLikePhotoOneSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikePhotoWithOneDisplaySocialContext
      ),
      MrPushCopyObjects.TweetLikePhotoTwoSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikePhotoWithTwoDisplaySocialContexts
      ),
      MrPushCopyObjects.TweetLikePhotoMultipleSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikePhotoWithOneDisplayAndKOtherSocialContexts
      ),
      MrPushCopyObjects.TweetLikeVideoOneSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikeVideoWithOneDisplaySocialContext
      ),
      MrPushCopyObjects.TweetLikeVideoTwoSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikeVideoWithTwoDisplaySocialContexts
      ),
      MrPushCopyObjects.TweetLikeVideoMultipleSocialContextWithText -> Set(
        MrNtabCopyObjects.TweetLikeVideoWithOneDisplayAndKOtherSocialContexts
      ),
      MrPushCopyObjects.UnreadBadgeCount -> Set.empty[MRNtabCopy],
      MrPushCopyObjects.RecommendedForYouTweet -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.RecommendedForYouPhoto -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.RecommendedForYouVideo -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.GeoPopPushCopy -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.UserFollowWithOneSocialContext -> Set(
        MrNtabCopyObjects.UserFollowWithOneDisplaySocialContext
      ),
      MrPushCopyObjects.UserFollowWithTwoSocialContext -> Set(
        MrNtabCopyObjects.UserFollowWithTwoDisplaySocialContexts
      ),
      MrPushCopyObjects.UserFollowOneDisplayAndKOtherSocialContext -> Set(
        MrNtabCopyObjects.UserFollowWithOneDisplayAndKOtherSocialContexts
      ),
      MrPushCopyObjects.HermitUserWithOneSocialContext -> Set(
        MrNtabCopyObjects.UserFollowWithOneDisplaySocialContext
      ),
      MrPushCopyObjects.HermitUserWithTwoSocialContext -> Set(
        MrNtabCopyObjects.UserFollowWithTwoDisplaySocialContexts
      ),
      MrPushCopyObjects.HermitUserWithOneDisplayAndKOtherSocialContexts -> Set(
        MrNtabCopyObjects.UserFollowWithOneDisplayAndKOtherSocialContexts
      ),
      MrPushCopyObjects.TriangularLoopUserWithOneSocialContext -> Set(
        MrNtabCopyObjects.TriangularLoopUserWithOneSocialContext
      ),
      MrPushCopyObjects.TriangularLoopUserWithTwoSocialContexts -> Set(
        MrNtabCopyObjects.TriangularLoopUserWithTwoSocialContexts
      ),
      MrPushCopyObjects.TriangularLoopUserOneDisplayAndKotherSocialContext -> Set(
        MrNtabCopyObjects.TriangularLoopUserOneDisplayAndKOtherSocialContext
      ),
      MrPushCopyObjects.NewsArticleNewsLandingCopy -> Set(
        MrNtabCopyObjects.NewsArticleNewsLandingCopy
      ),
      MrPushCopyObjects.UserFollowInterestBasedCopy -> Set(
        MrNtabCopyObjects.UserFollowInterestBasedCopy
      ),
      MrPushCopyObjects.ForwardAddressBookUserFollow -> Set(
        MrNtabCopyObjects.ForwardAddressBookUserFollow),
      MrPushCopyObjects.ConnectTabPush -> Set(
        MrNtabCopyObjects.ConnectTabPush
      ),
      MrPushCopyObjects.ExplorePush -> Set.empty[MRNtabCopy],
      MrPushCopyObjects.ConnectTabWithUserPush -> Set(
        MrNtabCopyObjects.UserFollowInterestBasedCopy),
      MrPushCopyObjects.AddressBookPush -> Set(MrNtabCopyObjects.AddressBook),
      MrPushCopyObjects.InterestPickerPush -> Set(MrNtabCopyObjects.InterestPicker),
      MrPushCopyObjects.CompleteOnboardingPush -> Set(MrNtabCopyObjects.CompleteOnboarding),
      MrPushCopyObjects.TopicProofTweet -> Set(MrNtabCopyObjects.TopicProofTweet),
      MrPushCopyObjects.TopTweetImpressions -> Set(MrNtabCopyObjects.TopTweetImpressions),
      MrPushCopyObjects.TrendTweet -> Set(MrNtabCopyObjects.TrendTweet),
      MrPushCopyObjects.SpaceHost -> Set(MrNtabCopyObjects.SpaceHost),
      MrPushCopyObjects.SubscribedSearchTweet -> Set(MrNtabCopyObjects.SubscribedSearchTweet),
      MrPushCopyObjects.TripGeoTweetPushCopy -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.Digest -> Set(MrNtabCopyObjects.Digest),
      MrPushCopyObjects.TripHqTweetPushCopy -> Set(MrNtabCopyObjects.HighQualityTweet),
      MrPushCopyObjects.ExploreVideoTweet -> Set(MrNtabCopyObjects.ExploreVideoTweet),
      MrPushCopyObjects.ListRecommendation -> Set(MrNtabCopyObjects.ListRecommendation),
      MrPushCopyObjects.MagicFanoutCreatorSubscription -> Set(
        MrNtabCopyObjects.MagicFanoutCreatorSubscription),
      MrPushCopyObjects.MagicFanoutNewCreator -> Set(MrNtabCopyObjects.MagicFanoutNewCreator)
    )

  /**
   *
   * @param crt - [[CommonRecommendationType]] used for a frigate push notification
   *
   * @return - Set of [[MRPushCopy]] objects representing push copies eligibile for a
   *         [[CommonRecommendationType]]
   */
  def getPushCopiesFromRectype(crt: CommonRecommendationType): Option[Set[MRPushCopy]] =
    rectypeToPushCopy.get(crt)

  /**
   *
   * @param pushcopy - [[MRPushCopy]] object representing a push notification copy
   * @return - Set of [[MRNtabCopy]] objects that can be paired with a given [[MRPushCopy]]
   */
  def getNtabcopiesFromPushcopy(pushcopy: MRPushCopy): Option[Set[MRNtabCopy]] =
    pushcopyToNtabcopy.get(pushcopy)
}
