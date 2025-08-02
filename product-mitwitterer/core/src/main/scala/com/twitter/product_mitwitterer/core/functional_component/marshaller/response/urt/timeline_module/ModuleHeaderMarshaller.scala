package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.SocialContetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleHeader
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ModuleHeaderMarshaller @Inject() (
  horizonIconMarshaller: HorizonIconMarshaller,
  imageVariantMarshaller: ImageVariantMarshaller,
  socialContetwittertMarshaller: SocialContetwittertMarshaller,
  moduleHeaderDisplayTypeMarshaller: ModuleHeaderDisplayTypeMarshaller) {

  def apply(header: ModuleHeader): urt.ModuleHeader = urt.ModuleHeader(
    tetwittert = header.tetwittert,
    sticky = header.sticky,
    icon = header.icon.map(horizonIconMarshaller(_)),
    customIcon = header.customIcon.map(imageVariantMarshaller(_)),
    socialContetwittert = header.socialContetwittert.map(socialContetwittertMarshaller(_)),
    displayType = moduleHeaderDisplayTypeMarshaller(header.moduleHeaderDisplayType)
  )
}
