package com.twitter.product_mixer.core.functional_component.marshaller.response.slice

import com.twitter.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mixer.core.model.common.identifier.TransportMarshallerIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.slice.Slice
import com.twitter.strato.graphql.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SliceTransportMarshaller @Inject() (sliceItemMarshaller: SliceItemMarshaller)
    extends TransportMarshaller[Slice, t.SliceResult] {

  override val identifier: TransportMarshallerIdentifier = TransportMarshallerIdentifier("Slice")

  override def apply(slice: Slice): t.SliceResult = {
    t.SliceResult.Slice(
      t.Slice(
        items = slice.items.map(sliceItemMarshaller(_)),
        sliceInfo = t.SliceInfo(
          previousCursor = slice.sliceInfo.previousCursor,
          nextCursor = slice.sliceInfo.nextCursor
        )
      ))
  }
}
