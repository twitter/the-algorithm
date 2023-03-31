package com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module

import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext

case class ModuleHeader(
  text: String,
  sticky: Option[Boolean],
  icon: Option[HorizonIcon],
  customIcon: Option[ImageVariant],
  socialContext: Option[SocialContext],
  moduleHeaderDisplayType: ModuleHeaderDisplayType)
