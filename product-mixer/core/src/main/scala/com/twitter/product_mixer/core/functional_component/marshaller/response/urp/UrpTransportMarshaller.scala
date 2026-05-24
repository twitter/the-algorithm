package com.twitter.product_mixer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.TimelineScribeConfigMarshaller
import com.twitter.product_mixer.core.model.common.identifier.TransportMarshallerIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urp.Page
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrpTransportMarshaller @Inject() (
  pageBodyMarshaller: PageBodyMarshaller,
  timelineScribeConfigMarshaller: TimelineScribeConfigMarshaller,
  pageHeaderMarshaller: PageHeaderMarshaller,
  pageNavBarMarshaller: PageNavBarMarshaller)
    extends TransportMarshaller[Page, urp.Page] {

  override val identifier: TransportMarshallerIdentifier =
    TransportMarshallerIdentifier("UnifiedRichPage")

  override def apply(page: Page): urp.Page = urp.Page(
    id = page.id,
    pageBody = pageBodyMarshaller(page.pageBody),
    scribeConfig = page.scribeConfig.map(timelineScribeConfigMarshaller(_)),
    pageHeader = page.pageHeader.map(pageHeaderMarshaller(_)),
    pageNavBar = page.pageNavBar.map(pageNavBarMarshaller(_))
  )
}
