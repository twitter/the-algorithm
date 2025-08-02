package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.Preroll
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class PrerollMarshaller @Inject() (
  dynamicPrerollTypeMarshaller: DynamicPrerollTypeMarshaller,
  mediaInfoMarshaller: MediaInfoMarshaller) {

  def apply(preroll: Preroll): urt.Preroll =
    urt.Preroll(
      prerollId = preroll.prerollId,
      dynamicPrerollType = preroll.dynamicPrerollType.map(dynamicPrerollTypeMarshaller(_)),
      mediaInfo = preroll.mediaInfo.map(mediaInfoMarshaller(_))
    )
}
