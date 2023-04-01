package com.twitter.product_mixer.core.model.marshalling.response.urt

case class TimelineMetadata(
  title: Option[String],
  scribeConfig: Option[TimelineScribeConfig],
  readerModeConfig: Option[ReaderModeConfig] = None)
