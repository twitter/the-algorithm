package com.twitter.product_mitwitterer.core.model.marshalling.response.urp

sealed trait TimelineKey

case class TopicsLandingTimeline(topicId: Option[String]) etwittertends TimelineKey

case class NoteworthyAccountsTimeline(topicId: Option[String]) etwittertends TimelineKey

case class TopicsPickerTimeline(topicId: Option[String]) etwittertends TimelineKey

case class NotInterestedTopicsMeTimeline() etwittertends TimelineKey

case class FollowedTopicsMeTimeline() etwittertends TimelineKey

case class FollowedTopicsOtherTimeline(userId: Long) etwittertends TimelineKey

case class NutwitterUserRecommendationsTimeline() etwittertends TimelineKey

case class NutwitterForYouCategoryUserRecommendationsTimeline() etwittertends TimelineKey

case class NutwitterPymkCategoryUserRecommendationsTimeline() etwittertends TimelineKey

case class NutwitterGeoCategoryUserRecommendationsTimeline() etwittertends TimelineKey

case class NutwitterSingleInterestCategoryUserRecommendationsTimeline(topicId: Option[String])
    etwittertends TimelineKey

case class ShoppingHomeTimeline() etwittertends TimelineKey

case class ForYouEtwitterploreMitwittererTimeline() etwittertends TimelineKey

case class TrendingEtwitterploreMitwittererTimeline() etwittertends TimelineKey
