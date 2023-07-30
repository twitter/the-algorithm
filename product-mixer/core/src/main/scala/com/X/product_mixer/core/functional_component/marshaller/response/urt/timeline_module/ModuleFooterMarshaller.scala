package com.X.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.X.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleFooter
import javax.inject.Inject
import javax.inject.Singleton
import com.X.timelines.render.{thriftscala => urt}

@Singleton
class ModuleFooterMarshaller @Inject() (urlMarshaller: UrlMarshaller) {

  def apply(footer: ModuleFooter): urt.ModuleFooter = urt.ModuleFooter(
    text = footer.text,
    landingUrl = footer.landingUrl.map(urlMarshaller(_))
  )
}
