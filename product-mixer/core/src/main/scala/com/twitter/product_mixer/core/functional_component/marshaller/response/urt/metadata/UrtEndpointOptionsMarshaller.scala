package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.UrtEndpointOptions
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UrtEndpointOptionsMarshaller @Inject() () {

  def apply(urtEndpointOptions: UrtEndpointOptions): urt.UrtEndpointOptions =
    urt.UrtEndpointOptions(
      requestParams = urtEndpointOptions.requestParams,
      title = urtEndpointOptions.title,
      cacheId = urtEndpointOptions.cacheId,
      subtitle = urtEndpointOptions.subtitle
    )
}
