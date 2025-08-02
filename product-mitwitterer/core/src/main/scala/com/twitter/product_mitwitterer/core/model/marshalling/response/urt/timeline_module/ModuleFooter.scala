package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url

case class ModuleFooter(
  tetwittert: String,
  landingUrl: Option[Url])
