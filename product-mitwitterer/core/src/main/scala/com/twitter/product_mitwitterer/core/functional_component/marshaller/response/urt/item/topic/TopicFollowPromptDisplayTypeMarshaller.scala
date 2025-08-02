package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.topic

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.IncentiveFocusTopicFollowPromptDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.TopicFocusTopicFollowPromptDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.TopicFollowPromptDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

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
