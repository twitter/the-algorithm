package com.twitter.product_mitwitterer.component_library.premarshaller.slice.builder

import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.SliceItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait ShouldInclude[-Query <: PipelineQuery] {
  def apply(query: Query, items: Seq[SliceItem]): Boolean
}

object AlwaysInclude etwittertends ShouldInclude[PipelineQuery] {
  override def apply(query: PipelineQuery, entries: Seq[SliceItem]): Boolean = true
}

object IncludeOnNonEmpty etwittertends ShouldInclude[PipelineQuery] {
  override def apply(query: PipelineQuery, entries: Seq[SliceItem]): Boolean = entries.nonEmpty
}
