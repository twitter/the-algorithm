package com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.X.timelines.render.{thriftscala => urt}
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.GeneralContext
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.TopicContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialContextMarshaller @Inject() (
  generalContextMarshaller: GeneralContextMarshaller,
  topicContextMarshaller: TopicContextMarshaller) {

  def apply(socialContext: SocialContext): urt.SocialContext =
    socialContext match {
      case generalContextBanner: GeneralContext =>
        generalContextMarshaller(generalContextBanner)
      case topicContextBanner: TopicContext =>
        topicContextMarshaller(topicContextBanner)
    }
}
