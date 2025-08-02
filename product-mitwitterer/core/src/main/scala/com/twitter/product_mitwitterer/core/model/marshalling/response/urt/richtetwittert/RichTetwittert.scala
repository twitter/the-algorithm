package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert

case class RichTetwittert(
  tetwittert: String,
  entities: List[RichTetwittertEntity],
  rtl: Option[Boolean],
  alignment: Option[RichTetwittertAlignment])
