package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert

case class RichTetwittertEntity(
  fromIndetwitter: Int,
  toIndetwitter: Int,
  ref: Option[ReferenceObject],
  format: Option[RichTetwittertFormat])
