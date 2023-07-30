package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.tombstone

import com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.item.tombstone.TombstoneInfo
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TombstoneInfoMarshaller @Inject() (
  richTextMarshaller: RichTextMarshaller) {

  def apply(tombstoneInfo: TombstoneInfo): urt.TombstoneInfo = urt.TombstoneInfo(
    text = tombstoneInfo.text,
    richText = tombstoneInfo.richText.map(richTextMarshaller(_)),
    richRevealText = tombstoneInfo.richRevealText.map(richTextMarshaller(_))
  )
}
