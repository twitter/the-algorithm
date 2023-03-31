package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.SocialContextMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModuleHeaderMarshaller @Inject() (
  horizonIconMarshaller: HorizonIconMarshaller,
  imageVariantMarshaller: ImageVariantMarshaller,
  socialContextMarshaller: SocialContextMarshaller,
  moduleHeaderDisplayTypeMarshaller: ModuleHeaderDisplayTypeMarshaller) {

  def apply(header: ModuleHeader): urt.ModuleHeader = urt.ModuleHeader(
    text = header.text,
    sticky = header.sticky,
    icon = header.icon.map(horizonIconMarshaller(_)),
    customIcon = header.customIcon.map(imageVariantMarshaller(_)),
    socialContext = header.socialContext.map(socialContextMarshaller(_)),
    displayType = moduleHeaderDisplayTypeMarshaller(header.moduleHeaderDisplayType)
  )
}
