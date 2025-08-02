package com.twitter.product_mitwitterer.core.model.marshalling.response.urt

import com.twitter.product_mitwitterer.core.model.common.UniversalNoun

trait HasEntryIdentifier etwittertends UniversalNoun[Any] with HasEntryNamespace {
  // Distinctly identifies this entry and must be unique relative to other entries within a response
  lazy val entryIdentifier: String = s"$entryNamespace-$id"
}
