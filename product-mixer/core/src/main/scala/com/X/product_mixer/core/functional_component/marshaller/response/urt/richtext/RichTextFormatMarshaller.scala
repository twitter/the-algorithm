package com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext

import com.X.product_mixer.core.model.marshalling.response.urt.richtext.Plain
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichTextFormat
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.Strong
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RichTextFormatMarshaller @Inject() () {

  def apply(format: RichTextFormat): urt.RichTextFormat = format match {
    case Plain => urt.RichTextFormat.Plain
    case Strong => urt.RichTextFormat.Strong
  }
}
