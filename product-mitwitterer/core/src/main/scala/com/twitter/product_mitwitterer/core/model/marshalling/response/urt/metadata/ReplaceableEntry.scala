package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata

trait ReplaceableEntry {
  def entryIdToReplace: Option[String] = None
}
