package com.X.product_mixer.core.functional_component.marshaller.response.urp

import com.X.product_mixer.core.model.marshalling.response.urp.BasicTopicPageHeaderDisplayType
import com.X.product_mixer.core.model.marshalling.response.urp.PersonalizedTopicPageHeaderDisplayType
import com.X.product_mixer.core.model.marshalling.response.urp.TopicPageHeaderDisplayType
import com.X.pages.render.{thriftscala => urp}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopicPageHeaderDisplayTypeMarshaller @Inject() () {

  def apply(
    topicPageHeaderDisplayType: TopicPageHeaderDisplayType
  ): urp.TopicPageHeaderDisplayType = topicPageHeaderDisplayType match {
    case BasicTopicPageHeaderDisplayType => urp.TopicPageHeaderDisplayType.Basic
    case PersonalizedTopicPageHeaderDisplayType => urp.TopicPageHeaderDisplayType.Personalized
  }
}
