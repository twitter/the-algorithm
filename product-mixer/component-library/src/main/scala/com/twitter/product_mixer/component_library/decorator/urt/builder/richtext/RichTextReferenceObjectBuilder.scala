package com.twitter.product_mixer.component_library.decorator.urt.builder.richtext

import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.ReferenceObject
import com.twitter.twittertext.Extractor

trait RichTextReferenceObjectBuilder {
  def apply(entity: Extractor.Entity): Option[ReferenceObject]
}
