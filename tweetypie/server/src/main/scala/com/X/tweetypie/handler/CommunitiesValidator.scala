package com.X.tweetypie.handler

import com.X.featureswitches.v2.FeatureSwitchResults
import com.X.servo.util.Gate
import com.X.tweetypie.Future
import com.X.tweetypie.core.TweetCreateFailure
import com.X.tweetypie.thriftscala.Communities
import com.X.tweetypie.thriftscala.TweetCreateState.CommunityProtectedUserCannotTweet
import com.X.tweetypie.util.CommunityUtil

object CommunitiesValidator {
  case class Request(
    matchedResults: Option[FeatureSwitchResults],
    isProtected: Boolean,
    community: Option[Communities])

  type Type = Request => Future[Unit]

  val CommunityProtectedCanCreateTweet = "communities_protected_community_tweet_creation_enabled"

  val communityProtectedCanCreateTweetGate: Gate[Request] = Gate { request: Request =>
    request.matchedResults
      .flatMap(_.getBoolean(CommunityProtectedCanCreateTweet, shouldLogImpression = true))
      .contains(false)
  }

  def apply(): Type =
    (request: Request) => {
      // Order is important: the feature-switch gate is checked only when the
      // request is both protected & community so that the FS experiment measurements
      // are based only on data from requests that are subject to rejection by this validator.
      if (request.isProtected &&
        CommunityUtil.hasCommunity(request.community) &&
        communityProtectedCanCreateTweetGate(request)) {
        Future.exception(TweetCreateFailure.State(CommunityProtectedUserCannotTweet))
      } else {
        Future.Unit
      }
    }
}
