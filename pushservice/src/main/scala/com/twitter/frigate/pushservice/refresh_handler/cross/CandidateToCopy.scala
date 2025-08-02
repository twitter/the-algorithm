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
        MrPushCopyObjects.TweetRetweetWithOneDisplaySocialContetwittertsWithTetwittert,
        MrPushCopyObjects.TweetRetweetWithTwoDisplaySocialContetwittertsWithTetwittert,
        MrPushCopyObjects.TweetRetweetWithOneDisplayAndKOtherSocialContetwittertsWithTetwittert
      ),
      TweetRetweetPhoto -> Set(
        MrPushCopyObjects.TweetRetweetPhotoWithOneDisplaySocialContetwittertWithTetwittert,
        MrPushCopyObjects.TweetRetweetPhotoWithTwoDisplaySocialContetwittertsWithTetwittert,
        MrPushCopyObjects.TweetRetweetPhotoWithOneDisplayAndKOtherSocialContetwittertsWithTetwittert
      ),
      TweetRetweetVideo -> Set(
        MrPushCopyObjects.TweetRetweetVideoWithOneDisplaySocialContetwittertWithTetwittert,
        MrPushCopyObjects.TweetRetweetVideoWithTwoDisplaySocialContetwittertsWithTetwittert,
        MrPushCopyObjects.TweetRetweetVideoWithOneDisplayAndKOtherSocialContetwittertsWithTetwittert
      ),
      TweetFavorite -> Set(
        MrPushCopyObjects.TweetLikeOneSocialContetwittertWithTetwittert,
        MrPushCopyObjects.TweetLikeTwoSocialContetwittertWithTetwittert,
        MrPushCopyObjects.TweetLikeMultipleSocialContetwittertWithTetwittert
      ),
      TweetFavoritePhoto -> Set(
        MrPushCopyObjects.TweetLikePhotoOneSocialContetwittertWithTetwittert,
        MrPushCopyObjects.TweetLikePhotoTwoSocialContetwittertWithTetwittert,
        MrPushCopyObjects.TweetLikePhotoMultipleSocialContetwittertWithTetwittert
      ),
      TweetFavoriteVideo -> Set(
        MrPushCopyObjects.TweetLikeVideoOneSocialContetwittertWithTetwittert,
        MrPushCopyObjects.TweetLikeVideoTwoSocialContetwittertWithTetwittert,
        MrPushCopyObjects.TweetLikeVideoMultipleSocialContetwittertWithTetwittert
      ),
      UnreadBadgeCount -> Set(MrPushCopyObjects.UnreadBadgeCount),
      InterestBasedTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      InterestBasedPhoto -> Set(MrPushCopyObjects.RecommendedForYouPhoto),
      InterestBasedVideo -> Set(MrPushCopyObjects.RecommendedForYouVideo),
      UserFollow -> Set(
        MrPushCopyObjects.UserFollowWithOneSocialContetwittert,
        MrPushCopyObjects.UserFollowWithTwoSocialContetwittert,
        MrPushCopyObjects.UserFollowOneDisplayAndKOtherSocialContetwittert
      ),
      HermitUser -> Set(
        MrPushCopyObjects.HermitUserWithOneSocialContetwittert,
        MrPushCopyObjects.HermitUserWithTwoSocialContetwittert,
        MrPushCopyObjects.HermitUserWithOneDisplayAndKOtherSocialContetwitterts
      ),
      TriangularLoopUser -> Set(
        MrPushCopyObjects.TriangularLoopUserWithOneSocialContetwittert,
        MrPushCopyObjects.TriangularLoopUserWithTwoSocialContetwitterts,
        MrPushCopyObjects.TriangularLoopUserOneDisplayAndKotherSocialContetwittert
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
      EtwitterploreVideoTweet -> Set(MrPushCopyObjects.EtwitterploreVideoTweet),
      List -> Set(MrPushCopyObjects.ListRecommendation),
      InterestBasedUserFollow -> Set(MrPushCopyObjects.UserFollowInterestBasedCopy),
      PastEmailEngagementTweet -> Set(MrPushCopyObjects.RecommendedForYouTweet),
      PastEmailEngagementPhoto -> Set(MrPushCopyObjects.RecommendedForYouPhoto),
      PastEmailEngagementVideo -> Set(MrPushCopyObjects.RecommendedForYouVideo),
      EtwitterplorePush -> Set(MrPushCopyObjects.EtwitterplorePush),
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
      MrPushCopyObjects.TweetRetweetWithOneDisplaySocialContetwittertsWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetWithOneDisplaySocialContetwittert
      ),
      MrPushCopyObjects.TweetRetweetWithTwoDisplaySocialContetwittertsWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetWithTwoDisplaySocialContetwitterts
      ),
      MrPushCopyObjects.TweetRetweetWithOneDisplayAndKOtherSocialContetwittertsWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetWithOneDisplayAndKOtherSocialContetwitterts
      ),
      MrPushCopyObjects.TweetRetweetPhotoWithOneDisplaySocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetPhotoWithOneDisplaySocialContetwittert
      ),
      MrPushCopyObjects.TweetRetweetPhotoWithTwoDisplaySocialContetwittertsWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetPhotoWithTwoDisplaySocialContetwitterts
      ),
      MrPushCopyObjects.TweetRetweetPhotoWithOneDisplayAndKOtherSocialContetwittertsWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetPhotoWithOneDisplayAndKOtherSocialContetwitterts
      ),
      MrPushCopyObjects.TweetRetweetVideoWithOneDisplaySocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetVideoWithOneDisplaySocialContetwittert
      ),
      MrPushCopyObjects.TweetRetweetVideoWithTwoDisplaySocialContetwittertsWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetVideoWithTwoDisplaySocialContetwitterts
      ),
      MrPushCopyObjects.TweetRetweetVideoWithOneDisplayAndKOtherSocialContetwittertsWithTetwittert -> Set(
        MrNtabCopyObjects.TweetRetweetVideoWithOneDisplayAndKOtherSocialContetwitterts
      ),
      MrPushCopyObjects.TweetLikeOneSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikeWithOneDisplaySocialContetwittert
      ),
      MrPushCopyObjects.TweetLikeTwoSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikeWithTwoDisplaySocialContetwitterts
      ),
      MrPushCopyObjects.TweetLikeMultipleSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikeWithOneDisplayAndKOtherSocialContetwitterts
      ),
      MrPushCopyObjects.TweetLikePhotoOneSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikePhotoWithOneDisplaySocialContetwittert
      ),
      MrPushCopyObjects.TweetLikePhotoTwoSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikePhotoWithTwoDisplaySocialContetwitterts
      ),
      MrPushCopyObjects.TweetLikePhotoMultipleSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikePhotoWithOneDisplayAndKOtherSocialContetwitterts
      ),
      MrPushCopyObjects.TweetLikeVideoOneSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikeVideoWithOneDisplaySocialContetwittert
      ),
      MrPushCopyObjects.TweetLikeVideoTwoSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikeVideoWithTwoDisplaySocialContetwitterts
      ),
      MrPushCopyObjects.TweetLikeVideoMultipleSocialContetwittertWithTetwittert -> Set(
        MrNtabCopyObjects.TweetLikeVideoWithOneDisplayAndKOtherSocialContetwitterts
      ),
      MrPushCopyObjects.UnreadBadgeCount -> Set.empty[MRNtabCopy],
      MrPushCopyObjects.RecommendedForYouTweet -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.RecommendedForYouPhoto -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.RecommendedForYouVideo -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.GeoPopPushCopy -> Set(MrNtabCopyObjects.RecommendedForYouCopy),
      MrPushCopyObjects.UserFollowWithOneSocialContetwittert -> Set(
        MrNtabCopyObjects.UserFollowWithOneDisplaySocialContetwittert
      ),
      MrPushCopyObjects.UserFollowWithTwoSocialContetwittert -> Set(
        MrNtabCopyObjects.UserFollowWithTwoDisplaySocialContetwitterts
      ),
      MrPushCopyObjects.UserFollowOneDisplayAndKOtherSocialContetwittert -> Set(
        MrNtabCopyObjects.UserFollowWithOneDisplayAndKOtherSocialContetwitterts
      ),
      MrPushCopyObjects.HermitUserWithOneSocialContetwittert -> Set(
        MrNtabCopyObjects.UserFollowWithOneDisplaySocialContetwittert
      ),
      MrPushCopyObjects.HermitUserWithTwoSocialContetwittert -> Set(
        MrNtabCopyObjects.UserFollowWithTwoDisplaySocialContetwitterts
      ),
      MrPushCopyObjects.HermitUserWithOneDisplayAndKOtherSocialContetwitterts -> Set(
        MrNtabCopyObjects.UserFollowWithOneDisplayAndKOtherSocialContetwitterts
      ),
      MrPushCopyObjects.TriangularLoopUserWithOneSocialContetwittert -> Set(
        MrNtabCopyObjects.TriangularLoopUserWithOneSocialContetwittert
      ),
      MrPushCopyObjects.TriangularLoopUserWithTwoSocialContetwitterts -> Set(
        MrNtabCopyObjects.TriangularLoopUserWithTwoSocialContetwitterts
      ),
      MrPushCopyObjects.TriangularLoopUserOneDisplayAndKotherSocialContetwittert -> Set(
        MrNtabCopyObjects.TriangularLoopUserOneDisplayAndKOtherSocialContetwittert
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
      MrPushCopyObjects.EtwitterplorePush -> Set.empty[MRNtabCopy],
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
      MrPushCopyObjects.EtwitterploreVideoTweet -> Set(MrNtabCopyObjects.EtwitterploreVideoTweet),
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
