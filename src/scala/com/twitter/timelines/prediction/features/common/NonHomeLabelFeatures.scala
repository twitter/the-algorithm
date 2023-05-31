package com.twitter.timelines.prediction.features.common

import com.twitter.dal.personal_data.thriftjava.PersonalDataType._
import com.twitter.ml.api.Feature
import com.twitter.ml.api.Feature.Binary
import java.lang.{Boolean => JBoolean}
import scala.collection.JavaConverters._

object ProfileLabelFeatures {
  private val prefix = "profile"

  val IS_CLICKED =
    new Binary(s"${prefix}.engagement.is_clicked", Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_DWELLED =
    new Binary(s"${prefix}.engagement.is_dwelled", Set(TweetsViewed, EngagementsPrivate).asJava)
  val IS_FAVORITED = new Binary(
    s"${prefix}.engagement.is_favorited",
    Set(PublicLikes, PrivateLikes, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED = new Binary(
    s"${prefix}.engagement.is_replied",
    Set(PublicReplies, PrivateReplies, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_RETWEETED = new Binary(
    s"${prefix}.engagement.is_retweeted",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)

  // Negative engagements
  val IS_DONT_LIKE =
    new Binary(s"${prefix}.engagement.is_dont_like", Set(EngagementsPrivate).asJava)
  val IS_BLOCK_CLICKED = new Binary(
    s"${prefix}.engagement.is_block_clicked",
    Set(Blocks, TweetsClicked, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_MUTE_CLICKED = new Binary(
    s"${prefix}.engagement.is_mute_clicked",
    Set(Mutes, TweetsClicked, EngagementsPrivate).asJava)
  val IS_REPORT_TWEET_CLICKED = new Binary(
    s"${prefix}.engagement.is_report_tweet_clicked",
    Set(TweetsClicked, EngagementsPrivate).asJava)

  val IS_NEGATIVE_FEEDBACK_UNION = new Binary(
    s"${prefix}.engagement.is_negative_feedback_union",
    Set(EngagementsPrivate, Blocks, Mutes, TweetsClicked, EngagementsPublic).asJava)

  val CoreEngagements: Set[Feature[JBoolean]] = Set(
    IS_CLICKED,
    IS_DWELLED,
    IS_FAVORITED,
    IS_REPLIED,
    IS_RETWEETED
  )

  val NegativeEngagements: Set[Feature[JBoolean]] = Set(
    IS_DONT_LIKE,
    IS_BLOCK_CLICKED,
    IS_MUTE_CLICKED,
    IS_REPORT_TWEET_CLICKED
  )

}

object SearchLabelFeatures {
  private val prefix = "search"

  val IS_CLICKED =
    new Binary(s"${prefix}.engagement.is_clicked", Set(TweetsClicked, EngagementsPrivate).asJava)
  val IS_DWELLED =
    new Binary(s"${prefix}.engagement.is_dwelled", Set(TweetsViewed, EngagementsPrivate).asJava)
  val IS_FAVORITED = new Binary(
    s"${prefix}.engagement.is_favorited",
    Set(PublicLikes, PrivateLikes, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_REPLIED = new Binary(
    s"${prefix}.engagement.is_replied",
    Set(PublicReplies, PrivateReplies, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_RETWEETED = new Binary(
    s"${prefix}.engagement.is_retweeted",
    Set(PublicRetweets, PrivateRetweets, EngagementsPrivate, EngagementsPublic).asJava)
  val IS_PROFILE_CLICKED_SEARCH_RESULT_USER = new Binary(
    s"${prefix}.engagement.is_profile_clicked_search_result_user",
    Set(ProfilesClicked, ProfilesViewed, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_SEARCH_RESULT_TWEET = new Binary(
    s"${prefix}.engagement.is_profile_clicked_search_result_tweet",
    Set(ProfilesClicked, ProfilesViewed, EngagementsPrivate).asJava)
  val IS_PROFILE_CLICKED_TYPEAHEAD_USER = new Binary(
    s"${prefix}.engagement.is_profile_clicked_typeahead_user",
    Set(ProfilesClicked, ProfilesViewed, EngagementsPrivate).asJava)

  val CoreEngagements: Set[Feature[JBoolean]] = Set(
    IS_CLICKED,
    IS_DWELLED,
    IS_FAVORITED,
    IS_REPLIED,
    IS_RETWEETED,
    IS_PROFILE_CLICKED_SEARCH_RESULT_USER,
    IS_PROFILE_CLICKED_SEARCH_RESULT_TWEET,
    IS_PROFILE_CLICKED_TYPEAHEAD_USER
  )
}
// Add Tweet Detail labels later
