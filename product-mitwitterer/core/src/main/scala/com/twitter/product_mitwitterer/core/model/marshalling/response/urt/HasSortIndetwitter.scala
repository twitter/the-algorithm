package com.twitter.product_mitwitterer.core.model.marshalling.response.urt

trait HasSortIndetwitter { timelineEntry: TimelineEntry =>
  def sortIndetwitter: Option[Long]

  def withSortIndetwitter(sortIndetwitter: Long): TimelineEntry
}
