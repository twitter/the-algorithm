package com.twitter.product_mixer.core.model.marshalling.response.urt

import com.twitter.product_mixer.core.model.marshalling.HasMarshalling

case class Timeline(
  id: String,
  instructions: Seq[TimelineInstruction],
  // responseObjects::feedbackActions actions are populated implicitly, see UrtTransportMarshaller
  metadata: Option[TimelineMetadata] = None)
    extends HasMarshalling
