package com.twitter.product_mixer.core.model.marshalling.response.urt.reaction

case class TimelineReaction(
  execution: TimelineReactionExecution,
  maxExecutionCount: Option[Short])
