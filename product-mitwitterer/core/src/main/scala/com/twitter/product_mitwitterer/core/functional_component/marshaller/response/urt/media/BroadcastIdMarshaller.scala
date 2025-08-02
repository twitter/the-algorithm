package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.media

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.media.BroadcastId
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class BroadcastIdMarshaller @Inject() () {

  def apply(broadcastId: BroadcastId): urt.BroadcastId = urt.BroadcastId(
    id = broadcastId.id
  )
}
