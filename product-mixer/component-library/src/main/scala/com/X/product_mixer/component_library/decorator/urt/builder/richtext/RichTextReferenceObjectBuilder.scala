package com.X.product_mixer.component_library.decorator.urt.builder.richtext

import com.X.product_mixer.core.model.marshalling.response.urt.richtext.ReferenceObject
import com.X.Xtext.Extractor

trait RichTextReferenceObjectBuilder {
  def apply(entity: Extractor.Entity): Option[ReferenceObject]
}
