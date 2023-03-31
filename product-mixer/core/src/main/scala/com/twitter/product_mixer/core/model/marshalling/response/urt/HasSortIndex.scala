package com.twitter.product_mixer.core.model.marshalling.response.urt

trait HasSortIndex { timelineEntry: TimelineEntry =>
  def sortIndex: Option[Long]

  def withSortIndex(sortIndex: Long): TimelineEntry
}
