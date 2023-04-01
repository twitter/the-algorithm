package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.CommerceDetails
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommerceDetailsMarshaller @Inject() () {

  def apply(commerceDetails: CommerceDetails): urt.CommerceDetails = urt.CommerceDetails(
    dropId = commerceDetails.dropId,
    shopV2Id = commerceDetails.shopV2Id,
    productKey = commerceDetails.productKey,
    merchantId = commerceDetails.merchantId,
    productIndex = commerceDetails.productIndex,
  )
}
