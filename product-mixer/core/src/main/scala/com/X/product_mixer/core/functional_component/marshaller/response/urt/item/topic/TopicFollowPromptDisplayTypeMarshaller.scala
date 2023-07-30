package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.topic

import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.IncentiveFocusTopicFollowPromptDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicFocusTopicFollowPromptDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicFollowPromptDisplayType
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicFollowPromptDisplayTypeMarshaller @Inject() () {

  def apply(
    topicFollowPromptDisplayType: TopicFollowPromptDisplayType
  ): urt.TopicFollowPromptDisplayType =
    topicFollowPromptDisplayType match {
      case IncentiveFocusTopicFollowPromptDisplayType =>
        urt.TopicFollowPromptDisplayType.IncentiveFocus
      case TopicFocusTopicFollowPromptDisplayType => urt.TopicFollowPromptDisplayType.TopicFocus
    }
}
