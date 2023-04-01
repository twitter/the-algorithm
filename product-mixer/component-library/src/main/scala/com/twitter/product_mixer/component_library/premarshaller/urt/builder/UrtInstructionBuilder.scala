package com.twitter.product_mixer.component_library.premarshaller.urt.builder

import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineInstruction
import com.twitter.product_mixer.core.pipeline.PipelineQuery

trait UrtInstructionBuilder[-Query <: PipelineQuery, +Instruction <: TimelineInstruction] {

  def includeInstruction: IncludeInstruction[Query] = AlwaysInclude

  def build(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Seq[Instruction]
}
