package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

case class CommerceDetails(
  dropId: Option[Long],
  shopV2Id: Option[Long],
  productKey: Option[Long],
  merchantId: Option[Long],
  productIndex: Option[Int])
