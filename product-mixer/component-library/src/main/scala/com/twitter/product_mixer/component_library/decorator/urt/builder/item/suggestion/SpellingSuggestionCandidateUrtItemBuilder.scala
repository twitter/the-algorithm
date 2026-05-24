package com.twitter.product_mixer.component_library.decorator.urt.builder.item.suggestion

import com.twitter.product_mixer.component_library.decorator.urt.builder.item.suggestion.SpellingSuggestionCandidateUrtItemBuilder.SpellingItemClientEventInfoElement
import com.twitter.product_mixer.component_library.model.candidate.suggestion.SpellingSuggestionCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.suggestion.SpellingItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object SpellingSuggestionCandidateUrtItemBuilder {
  val SpellingItemClientEventInfoElement: String = "spelling"
}

case class SpellingSuggestionCandidateUrtItemBuilder[Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, SpellingSuggestionCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, SpellingSuggestionCandidate]
  ] = None,
) extends CandidateUrtEntryBuilder[Query, SpellingSuggestionCandidate, SpellingItem] {

  override def apply(
    query: Query,
    candidate: SpellingSuggestionCandidate,
    candidateFeatures: FeatureMap
  ): SpellingItem = SpellingItem(
    id = candidate.id,
    sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      candidate,
      candidateFeatures,
      Some(SpellingItemClientEventInfoElement)),
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, candidate, candidateFeatures)),
    textResult = candidate.textResult,
    spellingActionType = candidate.spellingActionType,
    originalQuery = candidate.originalQuery
  )
}
