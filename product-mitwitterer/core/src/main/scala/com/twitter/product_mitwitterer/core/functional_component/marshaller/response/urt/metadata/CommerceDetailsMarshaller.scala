package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.CommerceDetails
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CommerceDetailsMarshaller @Inject() () {

  def apply(commerceDetails: CommerceDetails): urt.CommerceDetails = urt.CommerceDetails(
    dropId = commerceDetails.dropId,
    shopV2Id = commerceDetails.shopV2Id,
    productKey = commerceDetails.productKey,
    merchantId = commerceDetails.merchantId,
    productIndetwitter = commerceDetails.productIndetwitter,
  )
}
