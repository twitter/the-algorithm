package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.richtext

import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.richtext.ReferenceObject
import com.ExTwitter.ExTwittertext.Extractor

trait RichTextReferenceObjectBuilder {
  def apply(entity: Extractor.Entity): Option[ReferenceObject]
}
