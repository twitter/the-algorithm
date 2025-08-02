package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata

trait PinnableEntry {
  def isPinned: Option[Boolean] = None
}
