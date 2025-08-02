package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CallbackMarshaller @Inject() () {

  def apply(callback: Callback): urt.Callback = urt.Callback(
    endpoint = callback.endpoint
  )
}
