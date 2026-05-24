package com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url

case class ModuleFooter(
  text: String,
  landingUrl: Option[Url])
