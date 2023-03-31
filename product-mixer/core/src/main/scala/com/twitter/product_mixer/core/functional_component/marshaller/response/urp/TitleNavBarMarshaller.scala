package com.twitter.product_mixer.core.functional_component.marshaller.response.urp

import com.twitter.pages.render.{thriftscala => urp}
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ClientEventInfoMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urp.TitleNavBar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TitleNavBarMarshaller @Inject() (
  clientEventInfoMarshaller: ClientEventInfoMarshaller) {

  def apply(titleNavBar: TitleNavBar): urp.TitleNavBar =
    urp.TitleNavBar(
      title = titleNavBar.title,
      subtitle = titleNavBar.subtitle,
      clientEventInfo = titleNavBar.clientEventInfo.map(clientEventInfoMarshaller(_))
    )
}
