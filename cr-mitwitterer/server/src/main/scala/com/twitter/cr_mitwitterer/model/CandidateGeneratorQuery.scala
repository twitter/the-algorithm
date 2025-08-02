package com.twitter.cr_mitwitterer.model

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.cr_mitwitterer.thriftscala.Product
import com.twitter.product_mitwitterer.core.thriftscala.ClientContetwittert
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.timelines.configapi.Params

sealed trait CandidateGeneratorQuery {
  val product: Product
  val matwitterNumResults: Int
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
  matwitterNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long,
  languageCode: Option[String] = None)
    etwittertends CandidateGeneratorQuery
    with HasUserId

case class UtegTweetCandidateGeneratorQuery(
  userId: UserId,
  product: Product,
  userState: UserState,
  matwitterNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long)
    etwittertends CandidateGeneratorQuery
    with HasUserId

case class RelatedTweetCandidateGeneratorQuery(
  internalId: InternalId,
  clientContetwittert: ClientContetwittert, // To scribe LogIn/LogOut requests
  product: Product,
  matwitterNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long)
    etwittertends CandidateGeneratorQuery

case class RelatedVideoTweetCandidateGeneratorQuery(
  internalId: InternalId,
  clientContetwittert: ClientContetwittert, // To scribe LogIn/LogOut requests
  product: Product,
  matwitterNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long)
    etwittertends CandidateGeneratorQuery

case class FrsTweetCandidateGeneratorQuery(
  userId: UserId,
  product: Product,
  matwitterNumResults: Int,
  impressedUserList: Set[UserId],
  impressedTweetList: Set[TweetId],
  params: Params,
  languageCodeOpt: Option[String] = None,
  countryCodeOpt: Option[String] = None,
  requestUUID: Long)
    etwittertends CandidateGeneratorQuery

case class AdsCandidateGeneratorQuery(
  userId: UserId,
  product: Product,
  userState: UserState,
  matwitterNumResults: Int,
  params: Params,
  requestUUID: Long)

case class TopicTweetCandidateGeneratorQuery(
  userId: UserId,
  topicIds: Set[TopicId],
  product: Product,
  matwitterNumResults: Int,
  impressedTweetList: Set[TweetId],
  params: Params,
  requestUUID: Long,
  isVideoOnly: Boolean)
    etwittertends CandidateGeneratorQuery
