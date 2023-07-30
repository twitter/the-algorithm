package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.topic

import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicFollowPromptItem
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicFollowPromptItemMarshaller @Inject() (
  displayTypeMarshaller: TopicFollowPromptDisplayTypeMarshaller) {

  def apply(topicFollowPromptItem: TopicFollowPromptItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.TopicFollowPrompt(
      urt.TopicFollowPrompt(
        topicId = topicFollowPromptItem.id.toString,
        displayType = displayTypeMarshaller(topicFollowPromptItem.topicFollowPromptDisplayType),
        followIncentiveTitle = topicFollowPromptItem.followIncentiveTitle,
        followIncentiveText = topicFollowPromptItem.followIncentiveText
      )
    )
  }
}
