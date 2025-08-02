package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertEntity
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RichTetwittertEntityMarshaller @Inject() (
  referenceObjectMarshaller: ReferenceObjectMarshaller,
  richTetwittertFormatMarshaller: RichTetwittertFormatMarshaller) {

  def apply(entity: RichTetwittertEntity): urt.RichTetwittertEntity = urt.RichTetwittertEntity(
    fromIndetwitter = entity.fromIndetwitter,
    toIndetwitter = entity.toIndetwitter,
    ref = entity.ref.map(referenceObjectMarshaller(_)),
    format = entity.format.map(richTetwittertFormatMarshaller(_))
  )
}
