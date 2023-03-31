package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.moment

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.moment.MomentAnnotationItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MomentAnnotationItemMarshaller @Inject() (richTextMarshaller: RichTextMarshaller) {
  def apply(momentAnnotationItem: MomentAnnotationItem): urt.TimelineItemContent = {
    urt.TimelineItemContent.MomentAnnotation(
      urt.MomentAnnotation(
        annotationId = momentAnnotationItem.id,
        text = momentAnnotationItem.text.map(richTextMarshaller(_)),
        header = momentAnnotationItem.header.map(richTextMarshaller(_)),
      )
    )
  }
}
