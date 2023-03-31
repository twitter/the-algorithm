package com.twitter.product_mixer.core.model.marshalling.response.urt.item.generic_summary

import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText

case class GenericSummaryContext(
  text: RichText,
  icon: Option[HorizonIcon])
