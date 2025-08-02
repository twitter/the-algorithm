package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urp

import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.FollowedTopicsMeTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.FollowedTopicsOtherTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.ForYouEtwitterploreMitwittererTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.NoteworthyAccountsTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.NotInterestedTopicsMeTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.NutwitterForYouCategoryUserRecommendationsTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.NutwitterGeoCategoryUserRecommendationsTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.NutwitterPymkCategoryUserRecommendationsTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.NutwitterSingleInterestCategoryUserRecommendationsTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.NutwitterUserRecommendationsTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.ShoppingHomeTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.TimelineKey
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.TopicsLandingTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.TopicsPickerTimeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urp.TrendingEtwitterploreMitwittererTimeline
import com.twitter.strato.graphql.timelines.{thriftscala => graphql}
import javatwitter.inject.Singleton

@Singleton
class TimelineKeyMarshaller {

  def apply(timelineKey: TimelineKey): graphql.TimelineKey = timelineKey match {
    case TopicsLandingTimeline(topicId) =>
      graphql.TimelineKey.TopicTimeline(graphql.TopicId(topicId))
    case NoteworthyAccountsTimeline(topicId) =>
      graphql.TimelineKey.NoteworthyAccountsTimeline(graphql.TopicId(topicId))
    case TopicsPickerTimeline(topicId) =>
      graphql.TimelineKey.TopicsPickerTimeline(graphql.TopicId(topicId))
    case FollowedTopicsMeTimeline() =>
      graphql.TimelineKey.FollowedTopicsMeTimeline(graphql.Void())
    case NotInterestedTopicsMeTimeline() =>
      graphql.TimelineKey.NotInterestedTopicsMeTimeline(graphql.Void())
    case FollowedTopicsOtherTimeline(userId) =>
      graphql.TimelineKey.FollowedTopicsOtherTimeline(userId)
    case NutwitterUserRecommendationsTimeline() =>
      graphql.TimelineKey.NutwitterUserRecommendationsTimeline(graphql.Void())
    case NutwitterForYouCategoryUserRecommendationsTimeline() =>
      graphql.TimelineKey.NutwitterForYouCategoryUserRecommendationsTimeline(graphql.Void())
    case NutwitterPymkCategoryUserRecommendationsTimeline() =>
      graphql.TimelineKey.NutwitterPymkCategoryUserRecommendationsTimeline(graphql.Void())
    case NutwitterGeoCategoryUserRecommendationsTimeline() =>
      graphql.TimelineKey.NutwitterGeoCategoryUserRecommendationsTimeline(graphql.Void())
    case NutwitterSingleInterestCategoryUserRecommendationsTimeline(topicId) =>
      graphql.TimelineKey.NutwitterSingleInterestCategoryUserRecommendationsTimeline(
        graphql.TopicId(topicId))
    case ShoppingHomeTimeline() => graphql.TimelineKey.ShoppingHome(graphql.Void())
    case ForYouEtwitterploreMitwittererTimeline() =>
      graphql.TimelineKey.ForYouEtwitterploreMitwittererTimeline(graphql.Void())
    case TrendingEtwitterploreMitwittererTimeline() =>
      graphql.TimelineKey.TrendingEtwitterploreMitwittererTimeline(graphql.Void())
  }
}
