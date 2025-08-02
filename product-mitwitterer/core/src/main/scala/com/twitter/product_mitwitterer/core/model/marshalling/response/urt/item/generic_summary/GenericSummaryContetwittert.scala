package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert

case class GenericSummaryContetwittert(
  tetwittert: RichTetwittert,
  icon: Option[HorizonIcon])
