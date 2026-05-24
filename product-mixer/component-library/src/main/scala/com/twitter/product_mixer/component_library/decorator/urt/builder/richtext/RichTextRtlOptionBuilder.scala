package com.twitter.product_mixer.component_library.decorator.urt.builder.richtext

import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait RichTextRtlOptionBuilder[-Query <: PipelineQuery] {
  def apply(query: Query): Option[Boolean]
}

case class StaticRichTextRtlOptionBuilder[-Query <: PipelineQuery](rtlOption: Option[Boolean])
    extends RichTextRtlOptionBuilder[Query] {
  override def apply(query: Query): Option[Boolean] = rtlOption
}
