package com.X.product_mixer.component_library.decorator.urt.builder.item.unified_trend_event

import com.X.product_mixer.component_library.decorator.urt.builder.item.event_summary.EventCandidateUrtItemBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.item.trend.TrendCandidateUrtItemBuilder
import com.X.product_mixer.component_library.model.candidate.trends_events.UnifiedEventCandidate
import com.X.product_mixer.component_library.model.candidate.trends_events.UnifiedTrendCandidate
import com.X.product_mixer.component_library.model.candidate.trends_events.UnifiedTrendEventCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.X.product_mixer.core.pipeline.PipelineQuery

case class UnifiedTrendEventCandidateUrtItemBuilder[Query <: PipelineQuery](
  eventCandidateUrtItemBuilder: EventCandidateUrtItemBuilder[Query],
  trendCandidateUrtItemBuilder: TrendCandidateUrtItemBuilder[Query])
    extends CandidateUrtEntryBuilder[Query, UnifiedTrendEventCandidate[Any], TimelineItem] {

  override def apply(
    query: Query,
    candidate: UnifiedTrendEventCandidate[Any],
    candidateFeatures: FeatureMap
  ): TimelineItem = {
    candidate match {
      case event: UnifiedEventCandidate =>
        eventCandidateUrtItemBuilder(
          query = query,
          candidate = event,
          candidateFeatures = candidateFeatures)
      case trend: UnifiedTrendCandidate =>
        trendCandidateUrtItemBuilder(
          query = query,
          candidate = trend,
          candidateFeatures = candidateFeatures)
    }
  }
}
