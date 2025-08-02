package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.ReferenceObject
import com.twitter.twittertetwittert.Etwittertractor

trait RichTetwittertReferenceObjectBuilder {
  def apply(entity: Etwittertractor.Entity): Option[ReferenceObject]
}
