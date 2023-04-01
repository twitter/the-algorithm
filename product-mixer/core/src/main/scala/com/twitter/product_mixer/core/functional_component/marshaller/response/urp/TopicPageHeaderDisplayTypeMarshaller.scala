package com.twitter.product_mixer.core.functional_component.marshaller.response.urp

import com.twitter.product_mixer.core.model.marshalling.response.urp.BasicTopicPageHeaderDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urp.PersonalizedTopicPageHeaderDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urp.TopicPageHeaderDisplayType
import com.twitter.pages.render.{thriftscala => urp}
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
