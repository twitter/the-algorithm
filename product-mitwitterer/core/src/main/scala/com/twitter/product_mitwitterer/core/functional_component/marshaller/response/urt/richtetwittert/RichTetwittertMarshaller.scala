package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RichTetwittertMarshaller @Inject() (
  richTetwittertEntityMarshaller: RichTetwittertEntityMarshaller,
  richTetwittertAlignmentMarshaller: RichTetwittertAlignmentMarshaller) {

  def apply(richTetwittert: RichTetwittert): urt.RichTetwittert = urt.RichTetwittert(
    tetwittert = richTetwittert.tetwittert,
    entities = richTetwittert.entities.map(richTetwittertEntityMarshaller(_)),
    rtl = richTetwittert.rtl,
    alignment = richTetwittert.alignment.map(richTetwittertAlignmentMarshaller(_))
  )
}
