package com.twitter.product_mixer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mixer.core.model.marshalling.response.urp.PageHeader
import com.twitter.product_mixer.core.model.marshalling.response.urp.TopicPageHeader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageHeaderMarshaller @Inject() (
  topicPageHeaderMarshaller: TopicPageHeaderMarshaller) {

  def apply(pageHeader: PageHeader): urp.PageHeader = pageHeader match {
    case pageHeader: TopicPageHeader =>
      urp.PageHeader.TopicPageHeader(topicPageHeaderMarshaller(pageHeader))
  }
}
