package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

trait PinnableEntry {
  def isPinned: Option[Boolean] = None
}
