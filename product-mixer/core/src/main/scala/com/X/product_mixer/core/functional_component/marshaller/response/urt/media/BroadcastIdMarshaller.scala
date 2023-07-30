package com.X.product_mixer.core.functional_component.marshaller.response.urt.media

import com.X.product_mixer.core.model.marshalling.response.urt.media.BroadcastId
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BroadcastIdMarshaller @Inject() () {

  def apply(broadcastId: BroadcastId): urt.BroadcastId = urt.BroadcastId(
    id = broadcastId.id
  )
}
