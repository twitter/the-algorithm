package com.twitter.home_mixer.product.scored_tweets.util

import com.github.nscala_time.time.Imports.LocalDate
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.embedding.TweetTextV8EmbeddingFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.timelines.control_ai.control.{thriftscala => ci}

object ControlAiUtil {
  private def isDotProductClose(
    vector1: Option[Seq[Double]],
    vector2: Option[Seq[Double]],
    threshold: Double
  ): Boolean = {
    (vector1, vector2) match {
      case (Some(v1), Some(v2)) =>
        require(v1.length == v2.length)
        val dotProduct = v1.zip(v2).map { case (a, b) => a * b }.sum
        dotProduct > threshold
      case _ => false
    }
  }

  def conditionMatch(
    action: ci.Action,
    candidate: CandidateWithFeatures[TweetCandidate],
    topicMap: Map[String, Seq[Double]],
    threshold: Double
  ): Boolean = {
    val now = LocalDate.now()
    val currentTime = now.toDate.toInstant.getEpochSecond
    val currentDayOfWeek = now.getDayOfWeek
    val currentHourOfDay = now.toDateTimeAtCurrentTime.toDateTime.getHourOfDay

    val conditions: Seq[ci.Condition => Boolean] =
      Seq(
        _.postTopic.forall { topic =>
          isDotProductClose(
            topicMap.get(topic),
            candidate.features.getOrElse(TweetTextV8EmbeddingFeature, None),
            threshold)
        },
        _.postLanguage.forall(candidate.features.getOrElse(TweetLanguageFeature, None).contains),
        _.postHasVideo.forall(_ == candidate.features.getOrElse(HasVideoFeature, false)),
        _.postHasImage.forall(_ == candidate.features.getOrElse(HasImageFeature, false)),
        _.postIsReply.forall(
          _ == candidate.features.getOrElse(InReplyToTweetIdFeature, None).isDefined),
        _.postIsRetweet.forall(_ == candidate.features.getOrElse(IsRetweetFeature, false)),
        _.postMaximumAge.forall(maxAge =>
          SnowflakeId.timeFromIdOpt(candidate.candidate.id).exists(_.untilNow.inMinutes <= maxAge)),
        _.userFollowsAuthor.forall(_ == candidate.features.get(InNetworkFeature)),
        _.postLikesCountGreaterThan.forall(count =>
          candidate.features
            .getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ >= count))),
        _.postLikesCountLessThan.forall(count =>
          candidate.features
            .getOrElse(EarlybirdFeature, None).exists(_.favCountV2.exists(_ < count))),
        _.postRetweetsCountGreaterThan.forall(count =>
          candidate.features
            .getOrElse(EarlybirdFeature, None).exists(_.retweetCountV2.exists(_ >= count))),
        _.postRetweetsCountLessThan.forall(count =>
          candidate.features
            .getOrElse(EarlybirdFeature, None).exists(_.retweetCountV2.exists(_ < count))),
        _.postRepliesCountGreaterThan.forall(count =>
          candidate.features
            .getOrElse(EarlybirdFeature, None).exists(_.replyCountV2.exists(_ >= count))),
        _.postRepliesCountLessThan.forall(count =>
          candidate.features
            .getOrElse(EarlybirdFeature, None).exists(_.replyCountV2.exists(_ < count))),
        _.postQuotesCountGreaterThan.forall(count =>
          candidate.features
            .getOrElse(EarlybirdFeature, None).exists(_.quoteCount.exists(_ >= count))),
        _.postQuotesCountLessThan.forall(count =>
          candidate.features
            .getOrElse(EarlybirdFeature, None).exists(_.quoteCount.exists(_ < count))),
        _.postLikedByFollowings.forall(
          _ == candidate.features.getOrElse(SGSValidLikedByUserIdsFeature, Seq.empty).nonEmpty),
        _.authorId.forall(aid => candidate.features.getOrElse(AuthorIdFeature, None).contains(aid)),
        _.authorLanguage.forall(lang =>
          candidate.features.getOrElse(TweetLanguageFeature, None).contains(lang)),
        _.authorAccountAgeGreaterThan.forall(count =>
          candidate.features.getOrElse(AuthorAccountAge, None).exists(_.inDays / 365 >= count)),
        _.authorAccountAgeLessThan.forall(count =>
          candidate.features.getOrElse(AuthorAccountAge, None).exists(_.inDays / 365 < count)),
        _.authorFollowerCountGreaterThan.forall(count =>
          candidate.features.getOrElse(AuthorFollowersFeature, None).exists(_ >= count)),
        _.authorFollowerCountLessThan.forall(count =>
          candidate.features.getOrElse(AuthorFollowersFeature, None).exists(_ < count)),
        _.authorFollowedByFollowings.forall(
          _ == candidate.features.getOrElse(SGSValidFollowedByUserIdsFeature, Seq.empty).nonEmpty),
        _.queryValidStartTime.forall(currentTime >= _),
        _.queryValidEndTime.forall(currentTime < _),
        _.queryValidDayOfWeek.forall(_.toInt == currentDayOfWeek),
        _.queryValidHourOfDay.forall(_.toInt == currentHourOfDay)
      )

    conditions.forall(_(action.condition))
  }
}
