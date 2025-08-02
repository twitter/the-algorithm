package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.moment

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.moment.MomentAnnotationItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class MomentAnnotationItemMarshaller @Inject() (richTetwittertMarshaller: RichTetwittertMarshaller) {
  def apply(momentAnnotationItem: MomentAnnotationItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.MomentAnnotation(
      urt.MomentAnnotation(
        annotationId = momentAnnotationItem.id,
        tetwittert = momentAnnotationItem.tetwittert.map(richTetwittertMarshaller(_)),
        header = momentAnnotationItem.header.map(richTetwittertMarshaller(_)),
      )
    )
  }
}
