package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.tombstone

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tombstone.TombstoneInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TombstoneInfoMarshaller @Inject() (
  richTetwittertMarshaller: RichTetwittertMarshaller) {

  def apply(tombstoneInfo: TombstoneInfo): urt.TombstoneInfo = urt.TombstoneInfo(
    tetwittert = tombstoneInfo.tetwittert,
    richTetwittert = tombstoneInfo.richTetwittert.map(richTetwittertMarshaller(_)),
    richRevealTetwittert = tombstoneInfo.richRevealTetwittert.map(richTetwittertMarshaller(_))
  )
}
