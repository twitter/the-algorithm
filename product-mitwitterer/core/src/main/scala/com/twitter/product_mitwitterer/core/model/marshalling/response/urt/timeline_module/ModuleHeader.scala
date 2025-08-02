package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert

case class ModuleHeader(
  tetwittert: String,
  sticky: Option[Boolean],
  icon: Option[HorizonIcon],
  customIcon: Option[ImageVariant],
  socialContetwittert: Option[SocialContetwittert],
  moduleHeaderDisplayType: ModuleHeaderDisplayType)
