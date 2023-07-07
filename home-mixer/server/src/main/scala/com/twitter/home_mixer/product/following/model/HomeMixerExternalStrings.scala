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
  val blockUserString = externalStringRegistryProvider.get().createProdString("Feedback.blockUser")
  val unfollowUserString =
    externalStringRegistryProvider.get().createProdString("Feedback.unfollowUser")
  val unfollowUserConfirmationString =
    externalStringRegistryProvider.get().createProdString("Feedback.unfollowUserConfirmation")
  val reportTweetString =
    externalStringRegistryProvider.get().createProdString("Feedback.reportTweet")
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

  val socialContextPopularVideoString =
    externalStringRegistryProvider.get().createProdString("SocialContext.popularVideo")

  val socialContextPopularInYourAreaString =
    externalStringRegistryProvider.get().createProdString("PopgeoTweet.socialProof")

  val listToFollowModuleHeaderString =
    externalStringRegistryProvider.get().createProdString("ListToFollowModule.header")
  val listToFollowModuleFooterString =
    externalStringRegistryProvider.get().createProdString("ListToFollowModule.footer")
  val pinnedListsModuleHeaderString =
    externalStringRegistryProvider.get().createProdString("PinnedListModule.header")
  val pinnedListsModuleEmptyStateMessageString =
    externalStringRegistryProvider.get().createProdString("PinnedListModule.emptyStateMessage")

  val ownedSubscribedListsModuleHeaderString =
    externalStringRegistryProvider.get().createProdString("OwnedSubscribedListModule.header")
  val ownedSubscribedListsModuleEmptyStateMessageString =
    externalStringRegistryProvider
      .get().createProdString("OwnedSubscribedListModule.emptyStateMessage")
}
