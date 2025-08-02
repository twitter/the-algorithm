package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait IncludeInstruction[-Query <: PipelineQuery] { self =>
  def apply(query: Query, entries: Seq[TimelineEntry]): Boolean

  def inverse(): IncludeInstruction[Query] = new IncludeInstruction[Query] {
    def apply(query: Query, entries: Seq[TimelineEntry]): Boolean = !self.apply(query, entries)
  }
}

object AlwaysInclude etwittertends IncludeInstruction[PipelineQuery] {
  override def apply(query: PipelineQuery, entries: Seq[TimelineEntry]): Boolean = true
}

object IncludeOnFirstPage etwittertends IncludeInstruction[PipelineQuery with HasPipelineCursor[_]] {
  override def apply(
    query: PipelineQuery with HasPipelineCursor[_],
    entries: Seq[TimelineEntry]
  ): Boolean = query.isFirstPage
}

object IncludeAfterFirstPage etwittertends IncludeInstruction[PipelineQuery with HasPipelineCursor[_]] {
  override def apply(
    query: PipelineQuery with HasPipelineCursor[_],
    entries: Seq[TimelineEntry]
  ): Boolean = !query.isFirstPage
}
