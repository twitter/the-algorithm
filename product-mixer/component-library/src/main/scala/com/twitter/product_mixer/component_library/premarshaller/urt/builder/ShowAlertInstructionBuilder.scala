package com.twitter.product_mixer.component_library.premarshaller.urt.builder

import com.twitter.product_mixer.core.model.marshalling.response.urt.ShowAlert
import com.twitter.product_mixer.core.model.marshalling.response.urt.ShowAlertInstruction
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class ShowAlertInstructionBuilder[Query <: PipelineQuery](
  override val includeInstruction: IncludeInstruction[Query] = AlwaysInclude)
    extends UrtInstructionBuilder[Query, ShowAlertInstruction] {

  override def build(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Seq[ShowAlertInstruction] = {
    if (includeInstruction(query, entries)) {
      // Currently only one Alert is supported per response
      entries.collectFirst {
        case alertEntry: ShowAlert => ShowAlertInstruction(alertEntry)
      }.toSeq
    } else Seq.empty
  }
}
