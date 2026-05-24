package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

// Track unread entries for the MarkUnread URT instruction.
trait MarkUnreadableEntry {
  def isMarkUnread: Option[Boolean] = None
}
