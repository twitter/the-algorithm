package com.twitter.home_mixer.product.following.model

import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.ExternalStringRegistry
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class HomeMixerExternalStrings @Inject() (
  @ProductScoped externalStringRegistryProvider: Provider[ExternalStringRegistry]) {
  val seeNewTweetsString =
    externalStringRegistryProvider.get().createProdString("SeeNewTweets")
  val tweetedString =
    externalStringRegistryProvider.get().createProdString("Tweeted")
  val muteUserString =
    externalStringRegistryProvider.get().createProdString("Feedback.muteUser")
  val muteUserConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.muteUserConfirmation")
  val blockUserString =
    externalStringRegistryProvider.get().createProdString("Feedback.blockUser")
  val blockUserConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.blockUserConfirmation")
  val unfollowUserString =
    externalStringRegistryProvider.get().createProdString("Feedback.unfollowUser")
  val unfollowUserConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.unfollowUserConfirmation")
  val reportTweetString =
    externalStringRegistryProvider.get().createProdString("Feedback.reportTweet")
  val genericString =
    externalStringRegistryProvider.get().createProdString("Feedback.generic")
  val genericConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.genericConfirmation")
  val relevantString =
    externalStringRegistryProvider.get().createProdString("Feedback.relevant")
  val relevantConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.relevantConfirmation")
  val dontLikeString = externalStringRegistryProvider.get().createProdString("Feedback.dontLike")
  val dontLikeConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.dontLikeConfirmation")
  val showFewerTweetsString =
    externalStringRegistryProvider.get().createProdString("Feedback.showFewerTweets")
  val showFewerTweetsConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.showFewerTweetsConfirmation")
  val showFewerRetweetsString =
    externalStringRegistryProvider.get().createProdString("Feedback.showFewerRetweets")
  val showFewerRetweetsConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.showFewerRetweetsConfirmation")
  val notRelevantString =
    externalStringRegistryProvider.get().createProdString("Feedback.notRelevant")
  val notRelevantConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.notRelevantConfirmation")
  val hatefulString =
    externalStringRegistryProvider.get().createProdString("Feedback.hateful")
  val hatefulConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.hatefulConfirmation")
  val boringString =
    externalStringRegistryProvider.get().createProdString("Feedback.boring")
  val boringConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.boringConfirmation")
  val confusingString =
    externalStringRegistryProvider.get().createProdString("Feedback.confusing")
  val confusingConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.confusingConfirmation")
  val clickbaitString =
    externalStringRegistryProvider.get().createProdString("Feedback.clickbait")
  val clickbaitConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.clickbaitConfirmation")
  val ragebaitString =
    externalStringRegistryProvider.get().createProdString("Feedback.ragebait")
  val ragebaitConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.ragebaitConfirmation")
  val regretString =
    externalStringRegistryProvider.get().createProdString("Feedback.regret")
  val regretConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.regretConfirmation")
  val neutralString =
    externalStringRegistryProvider.get().createProdString("Feedback.neutral")
  val neutralConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.neutralConfirmation")

  val seeMoreString =
    externalStringRegistryProvider.get().createProdString("PagedCarouselModule.showMoreText")
  val seeLessString =
    externalStringRegistryProvider.get().createProdString("PagedCarouselModule.showLessText")

  val socialContextOneUserLikedString =
    externalStringRegistryProvider.get().createProdString("SocialContext.oneUserLiked")
  val socialContextTwoUsersLikedString =
    externalStringRegistryProvider.get().createProdString("SocialContext.twoUsersLiked")
  val socialContextMoreUsersLikedString =
    externalStringRegistryProvider.get().createProdString("SocialContext.moreUsersLiked")
  val socialContextLikedByTimelineTitle =
    externalStringRegistryProvider.get().createProdString("SocialContext.likedByTimelineTitle")

  val socialContextOneUserFollowsString =
    externalStringRegistryProvider.get().createProdString("SocialContext.oneUserFollows")
  val socialContextTwoUsersFollowString =
    externalStringRegistryProvider.get().createProdString("SocialContext.twoUsersFollow")
  val socialContextMoreUsersFollowString =
    externalStringRegistryProvider.get().createProdString("SocialContext.moreUsersFollow")
  val socialContextFollowedByTimelineTitle =
    externalStringRegistryProvider.get().createProdString("SocialContext.followedByTimelineTitle")

  val socialContextYouMightLikeString =
    externalStringRegistryProvider.get().createProdString("SocialContext.youMightLike")

  val socialContextExtendedReply =
    externalStringRegistryProvider.get().createProdString("SocialContext.extendedReply")
  val socialContextReceivedReply =
    externalStringRegistryProvider.get().createProdString("SocialContext.receivedReply")

  val socialContextPopularInYourAreaString =
    externalStringRegistryProvider.get().createProdString("PopgeoTweet.socialProof")

  val ownedSubscribedListsModuleHeaderString =
    externalStringRegistryProvider.get().createProdString("OwnedSubscribedListModule.header")

  val CommunityToJoinHeaderString =
    externalStringRegistryProvider.get().createProdString("CommunityToJoinModule.header")
  val CommunityToJoinFooterString =
    externalStringRegistryProvider.get().createProdString("CommunityToJoinModule.footer")

  val RecommendedJobHeaderString =
    externalStringRegistryProvider.get().createProdString("RecommendedJobModule.header")
  val RecommendedJobFooterString =
    externalStringRegistryProvider.get().createProdString("RecommendedJobModule.footer")

  val RecommendedRecruitingOrganizationHeaderString =
    externalStringRegistryProvider
      .get().createProdString("RecommendedRecruitingOrganizationModule.header")
  val RecommendedRecruitingOrganizationFooterString =
    externalStringRegistryProvider
      .get().createProdString("RecommendedRecruitingOrganizationModule.footer")

  val BookmarksHeaderString =
    externalStringRegistryProvider.get().createProdString("RecentBookmarks.header")

  val PinnedTweetsHeaderString =
    externalStringRegistryProvider.get().createProdString("PinnedTweetsModule.header")
  val BroadcastedPinnedTweetSocialContextString =
    externalStringRegistryProvider.get().createProdString("BroadcastedPinnedTweet.context")
  val VideoCarouselHeaderString =
    externalStringRegistryProvider.get().createProdString("VideoCarouselModule.header")
  val VideoCarouselFooterString =
    externalStringRegistryProvider.get().createProdString("VideoCarouselModule.footer")

  val TrendingString =
    externalStringRegistryProvider.get().createProdString("Trending")
  val KeywordTrendsTweetCountDescriptionString =
    externalStringRegistryProvider.get().createProdString("KeywordTrends.tweetCountDescription")

  val NewsHeaderString =
    externalStringRegistryProvider.get().createProdString("News.header")
  val NewsFooterString =
    externalStringRegistryProvider.get().createProdString("News.footer")
}
