package com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext

import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichText
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RichTextMarshaller @Inject() (
  richTextEntityMarshaller: RichTextEntityMarshaller,
  richTextAlignmentMarshaller: RichTextAlignmentMarshaller) {

  def apply(richText: RichText): urt.RichText = urt.RichText(
    text = richText.text,
    entities = richText.entities.map(richTextEntityMarshaller(_)),
    rtl = richText.rtl,
    alignment = richText.alignment.map(richTextAlignmentMarshaller(_))
  )
}
