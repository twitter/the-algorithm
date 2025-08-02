package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert

import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

trait RichTetwittertRtlOptionBuilder[-Query <: PipelineQuery] {
  def apply(query: Query): Option[Boolean]
}

case class StaticRichTetwittertRtlOptionBuilder[-Query <: PipelineQuery](rtlOption: Option[Boolean])
    etwittertends RichTetwittertRtlOptionBuilder[Query] {
  override def apply(query: Query): Option[Boolean] = rtlOption
}
