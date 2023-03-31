package com.twitter.cr_mixer.model

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.cr_mixer.thriftscala.Product
import com.twitter.product_mixer.core.thriftscala.ClientContext
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.timelines.configapi.Params

sealed trait CandidateGeneratorQuery {
  val product: Product
  val maxNumResults: Int
  val impressedTweetList: Set[TweetId]
  val params: Params
  val requestUUID: Long
}

sealed trait HasUserId {
  val userId: UserId
}

case class CrCandidateGeneratorQuery(
  userId: UserId,
  product: Product,
  userState: UserState,
  maxNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long,
  languageCode: Option[String] = None)
    extends CandidateGeneratorQuery
    with HasUserId

case class UtegTweetCandidateGeneratorQuery(
  userId: UserId,
  product: Product,
  userState: UserState,
  maxNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long)
    extends CandidateGeneratorQuery
    with HasUserId

case class RelatedTweetCandidateGeneratorQuery(
  internalId: InternalId,
  clientContext: ClientContext, // To scribe LogIn/LogOut requests
  product: Product,
  maxNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long)
    extends CandidateGeneratorQuery

case class RelatedVideoTweetCandidateGeneratorQuery(
  internalId: InternalId,
  clientContext: ClientContext, // To scribe LogIn/LogOut requests
  product: Product,
  maxNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long)
    extends CandidateGeneratorQuery

case class FrsTweetCandidateGeneratorQuery(
  userId: UserId,
  product: Product,
  maxNumResults: Int,
  impressedUserList: Set[UserId],
  impressedTweetList: Set[TweetId],
  params: Params,
  languageCodeOpt: Option[String] = None,
  countryCodeOpt: Option[String] = None,
  requestUUID: Long)
    extends CandidateGeneratorQuery

case class AdsCandidateGeneratorQuery(
  userId: UserId,
  product: Product,
  userState: UserState,
  maxNumResults: Int,
  params: Params,
  requestUUID: Long)

case class TopicTweetCandidateGeneratorQuery(
  userId: UserId,
  topicIds: Set[TopicId],
  product: Product,
  maxNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long,
  isVideoOnly: Boolean)
    extends CandidateGeneratorQuery
