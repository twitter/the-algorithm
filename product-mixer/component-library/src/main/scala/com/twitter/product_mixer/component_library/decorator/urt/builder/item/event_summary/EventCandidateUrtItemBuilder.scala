package com.twitter.product_mixer.component_library.decorator.urt.builder.item.event_summary

import com.twitter.product_mixer.component_library.decorator.urt.builder.item.event_summary.EventCandidateUrtItemBuilder.EventClientEventInfoElement
import com.twitter.product_mixer.component_library.model.candidate.trends_events.EventDisplayType
import com.twitter.product_mixer.component_library.model.candidate.trends_events.EventImage
import com.twitter.product_mixer.component_library.model.candidate.trends_events.EventTimeString
import com.twitter.product_mixer.component_library.model.candidate.trends_events.EventTitleFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.EventUrl
import com.twitter.product_mixer.component_library.model.candidate.trends_events.UnifiedEventCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.event.EventSummaryItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object EventCandidateUrtItemBuilder {
  val EventClientEventInfoElement = "event"
}

case class EventCandidateUrtItemBuilder[Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, UnifiedEventCandidate],
  feedbackActionInfoBuilder: Option[BaseFeedbackActionInfoBuilder[Query, UnifiedEventCandidate]] =
    None)
    extends CandidateUrtEntryBuilder[Query, UnifiedEventCandidate, TimelineItem] {

  override def apply(
    query: Query,
    candidate: UnifiedEventCandidate,
    candidateFeatures: FeatureMap
  ): TimelineItem = {
    EventSummaryItem(
      id = candidate.id,
      sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
      clientEventInfo = clientEventInfoBuilder(
        query = query,
        candidate = candidate,
        candidateFeatures = candidateFeatures,
        element = Some(EventClientEventInfoElement)
      ),
      feedbackActionInfo =
        feedbackActionInfoBuilder.flatMap(_.apply(query, candidate, candidateFeatures)),
      title = candidateFeatures.get(EventTitleFeature),
      displayType = candidateFeatures.get(EventDisplayType),
      url = candidateFeatures.get(EventUrl),
      image = candidateFeatures.getOrElse(EventImage, None),
      timeString = candidateFeatures.getOrElse(EventTimeString, None)
    )
  }
}
