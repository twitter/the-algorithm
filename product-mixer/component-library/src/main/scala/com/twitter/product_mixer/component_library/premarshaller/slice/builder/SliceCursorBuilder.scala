package com.twitter.product_mixer.component_library.premarshaller.slice.builder

import com.twitter.product_mixer.core.model.marshalling.response.slice.CursorItem
import com.twitter.product_mixer.core.model.marshalling.response.slice.CursorType
import com.twitter.product_mixer.core.model.marshalling.response.slice.SliceItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait SliceCursorBuilder[-Query <: PipelineQuery] {

  val includeOperation: ShouldInclude[Query] = AlwaysInclude

  def cursorValue(query: Query, items: Seq[SliceItem]): String
  def cursorType: CursorType

  def build(query: Query, entries: Seq[SliceItem]): Option[CursorItem] = {
    if (includeOperation(query, entries)) {
      Some(
        CursorItem(
          cursorType = cursorType,
          value = cursorValue(query, entries)
        ))
    } else None
  }
}
