package com.twitter.home_mitwitterer.product.following.model

import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.EtwitterternalStringRegistry
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

@Singleton
class HomeMitwittererEtwitterternalStrings @Inject() (
  @ProductScoped etwitterternalStringRegistryProvider: Provider[EtwitterternalStringRegistry]) {
  val seeNewTweetsString =
    etwitterternalStringRegistryProvider.get().createProdString("SeeNewTweets")
  val tweetedString =
    etwitterternalStringRegistryProvider.get().createProdString("Tweeted")
  val muteUserString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.muteUser")
  val blockUserString = etwitterternalStringRegistryProvider.get().createProdString("Feedback.blockUser")
  val unfollowUserString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.unfollowUser")
  val unfollowUserConfirmationString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.unfollowUserConfirmation")
  val reportTweetString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.reportTweet")
  val dontLikeString = etwitterternalStringRegistryProvider.get().createProdString("Feedback.dontLike")
  val dontLikeConfirmationString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.dontLikeConfirmation")
  val showFewerTweetsString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.showFewerTweets")
  val showFewerTweetsConfirmationString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.showFewerTweetsConfirmation")
  val showFewerRetweetsString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.showFewerRetweets")
  val showFewerRetweetsConfirmationString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.showFewerRetweetsConfirmation")
  val notRelevantString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.notRelevant")
  val notRelevantConfirmationString =
    etwitterternalStringRegistryProvider.get().createProdString("Feedback.notRelevantConfirmation")

  val socialContetwittertOneUserLikedString =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.oneUserLiked")
  val socialContetwittertTwoUsersLikedString =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.twoUsersLiked")
  val socialContetwittertMoreUsersLikedString =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.moreUsersLiked")
  val socialContetwittertLikedByTimelineTitle =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.likedByTimelineTitle")

  val socialContetwittertOneUserFollowsString =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.oneUserFollows")
  val socialContetwittertTwoUsersFollowString =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.twoUsersFollow")
  val socialContetwittertMoreUsersFollowString =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.moreUsersFollow")
  val socialContetwittertFollowedByTimelineTitle =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.followedByTimelineTitle")

  val socialContetwittertYouMightLikeString =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.youMightLike")

  val socialContetwittertEtwittertendedReply =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.etwittertendedReply")
  val socialContetwittertReceivedReply =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.receivedReply")

  val socialContetwittertPopularVideoString =
    etwitterternalStringRegistryProvider.get().createProdString("SocialContetwittert.popularVideo")

  val socialContetwittertPopularInYourAreaString =
    etwitterternalStringRegistryProvider.get().createProdString("PopgeoTweet.socialProof")

  val listToFollowModuleHeaderString =
    etwitterternalStringRegistryProvider.get().createProdString("ListToFollowModule.header")
  val listToFollowModuleFooterString =
    etwitterternalStringRegistryProvider.get().createProdString("ListToFollowModule.footer")
  val pinnedListsModuleHeaderString =
    etwitterternalStringRegistryProvider.get().createProdString("PinnedListModule.header")
  val pinnedListsModuleEmptyStateMessageString =
    etwitterternalStringRegistryProvider.get().createProdString("PinnedListModule.emptyStateMessage")

  val ownedSubscribedListsModuleHeaderString =
    etwitterternalStringRegistryProvider.get().createProdString("OwnedSubscribedListModule.header")
  val ownedSubscribedListsModuleEmptyStateMessageString =
    etwitterternalStringRegistryProvider
      .get().createProdString("OwnedSubscribedListModule.emptyStateMessage")
}
