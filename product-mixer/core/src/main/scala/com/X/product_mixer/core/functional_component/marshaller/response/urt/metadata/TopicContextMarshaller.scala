package com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.TopicContext
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicContextMarshaller @Inject() () {

  def apply(topicContext: TopicContext): urt.SocialContext = {
    urt.SocialContext.TopicContext(
      urt.TopicContext(
        topicId = topicContext.topicId,
        functionalityType = TopicContextFunctionalityTypeMarshaller(
          topicContext.functionalityType.getOrElse(BasicTopicContextFunctionalityType))
      )
    )
  }
}
