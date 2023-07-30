package com.X.product_mixer.core.functional_component.marshaller.response.urt.media

import com.X.product_mixer.core.model.marshalling.response.urt.media.MediaKey
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaKeyMarshaller @Inject() () {

  def apply(mediaKey: MediaKey): urt.MediaKey = urt.MediaKey(
    id = mediaKey.id,
    category = mediaKey.category
  )
}
