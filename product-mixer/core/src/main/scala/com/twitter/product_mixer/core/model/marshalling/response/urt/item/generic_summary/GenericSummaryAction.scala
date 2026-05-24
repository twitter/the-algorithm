package com.twitter.product_mixer.core.model.marshalling.response.urt.item.generic_summary

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url

case class GenericSummaryAction(
  url: Url,
  clientEventInfo: Option[ClientEventInfo])
