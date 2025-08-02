package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.topic

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.TopicFollowPromptItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TopicFollowPromptItemMarshaller @Inject() (
  displayTypeMarshaller: TopicFollowPromptDisplayTypeMarshaller) {

  def apply(topicFollowPromptItem: TopicFollowPromptItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.TopicFollowPrompt(
      urt.TopicFollowPrompt(
        topicId = topicFollowPromptItem.id.toString,
        displayType = displayTypeMarshaller(topicFollowPromptItem.topicFollowPromptDisplayType),
        followIncentiveTitle = topicFollowPromptItem.followIncentiveTitle,
        followIncentiveTetwittert = topicFollowPromptItem.followIncentiveTetwittert
      )
    )
  }
}
