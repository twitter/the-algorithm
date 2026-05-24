package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext

import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichTextEntity
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RichTextEntityMarshaller @Inject() (
  referenceObjectMarshaller: ReferenceObjectMarshaller,
  richTextFormatMarshaller: RichTextFormatMarshaller) {

  def apply(entity: RichTextEntity): urt.RichTextEntity = urt.RichTextEntity(
    fromIndex = entity.fromIndex,
    toIndex = entity.toIndex,
    ref = entity.ref.map(referenceObjectMarshaller(_)),
    format = entity.format.map(richTextFormatMarshaller(_))
  )
}
