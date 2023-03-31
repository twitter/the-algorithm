package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.DismissInfo
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DismissInfoMarshaller @Inject() (callbackMarshaller: CallbackMarshaller) {

  def apply(dismissInfo: DismissInfo): urt.DismissInfo =
    urt.DismissInfo(dismissInfo.callbacks.map(_.map(callbackMarshaller(_))))
}
