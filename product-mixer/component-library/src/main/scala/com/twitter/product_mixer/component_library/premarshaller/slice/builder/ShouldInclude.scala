package com.twitter.product_mixer.component_library.premarshaller.slice.builder

import com.twitter.product_mixer.core.model.marshalling.response.slice.SliceItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait ShouldInclude[-Query <: PipelineQuery] {
  def apply(query: Query, items: Seq[SliceItem]): Boolean
}

object AlwaysInclude extends ShouldInclude[PipelineQuery] {
  override def apply(query: PipelineQuery, entries: Seq[SliceItem]): Boolean = true
}

object IncludeOnNonEmpty extends ShouldInclude[PipelineQuery] {
  override def apply(query: PipelineQuery, entries: Seq[SliceItem]): Boolean = entries.nonEmpty
}
