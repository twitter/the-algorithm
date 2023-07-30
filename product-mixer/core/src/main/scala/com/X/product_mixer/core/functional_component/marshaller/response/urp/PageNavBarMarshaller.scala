package com.X.product_mixer.core.functional_component.marshaller.response.urp

import com.X.pages.render.{thriftscala => urp}
import com.X.product_mixer.core.model.marshalling.response.urp.PageNavBar
import com.X.product_mixer.core.model.marshalling.response.urp.TopicPageNavBar
import com.X.product_mixer.core.model.marshalling.response.urp.TitleNavBar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageNavBarMarshaller @Inject() (
  topicPageNavBarMarshaller: TopicPageNavBarMarshaller,
  titleNavBarMarshaller: TitleNavBarMarshaller) {

  def apply(pageNavBar: PageNavBar): urp.PageNavBar = pageNavBar match {
    case pageNavBar: TopicPageNavBar =>
      urp.PageNavBar.TopicPageNavBar(topicPageNavBarMarshaller(pageNavBar))
    case pageNavBar: TitleNavBar =>
      urp.PageNavBar.TitleNavBar(titleNavBarMarshaller(pageNavBar))
  }
}
