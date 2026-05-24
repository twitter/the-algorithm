package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallbackMarshaller @Inject() () {

  def apply(callback: Callback): urt.Callback = urt.Callback(
    endpoint = callback.endpoint
  )
}
