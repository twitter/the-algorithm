package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

trait ReplaceableEntry {
  def entryIdToReplace: Option[String] = None
}
