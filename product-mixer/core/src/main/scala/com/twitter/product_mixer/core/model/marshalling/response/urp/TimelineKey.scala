package com.twitter.product_mixer.core.model.marshalling.response.urp

sealed trait TimelineKey

case class TopicsLandingTimeline(topicId: Option[String]) extends TimelineKey

case class NoteworthyAccountsTimeline(topicId: Option[String]) extends TimelineKey

case class TopicsPickerTimeline(topicId: Option[String]) extends TimelineKey

case class NotInterestedTopicsMeTimeline() extends TimelineKey

case class FollowedTopicsMeTimeline() extends TimelineKey

case class FollowedTopicsOtherTimeline(userId: Long) extends TimelineKey

case class NuxUserRecommendationsTimeline() extends TimelineKey

case class NuxForYouCategoryUserRecommendationsTimeline() extends TimelineKey

case class NuxPymkCategoryUserRecommendationsTimeline() extends TimelineKey

case class NuxGeoCategoryUserRecommendationsTimeline() extends TimelineKey

case class NuxSingleInterestCategoryUserRecommendationsTimeline(topicId: Option[String])
    extends TimelineKey

case class ShoppingHomeTimeline() extends TimelineKey

case class ForYouExploreMixerTimeline() extends TimelineKey

case class TrendingExploreMixerTimeline() extends TimelineKey
