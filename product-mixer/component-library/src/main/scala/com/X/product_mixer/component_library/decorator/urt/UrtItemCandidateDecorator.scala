package com.X.product_mixer.component_library.decorator.urt

import com.X.product_mixer.component_library.model.presentation.urt.UrtItemPresentation
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.common.identifier.DecoratorIdentifier
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineItem
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.X.product_mixer.core.functional_component.decorator.Decoration
import com.X.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.stitch.Stitch

/**
 * Decorator that will apply the provided [[CandidateUrtEntryBuilder]] to each candidate independently to make a [[TimelineItem]]
 */
case class UrtItemCandidateDecorator[
  Query <: PipelineQuery,
  BuilderInput <: UniversalNoun[Any],
  BuilderOutput <: TimelineItem
](
  builder: CandidateUrtEntryBuilder[Query, BuilderInput, BuilderOutput],
  override val identifier: DecoratorIdentifier = DecoratorIdentifier("UrtItemCandidate"))
    extends CandidateDecorator[Query, BuilderInput] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[BuilderInput]]
  ): Stitch[Seq[Decoration]] = {
    val candidatePresentations = candidates.map { candidate =>
      val itemPresentation = UrtItemPresentation(
        timelineItem = builder(query, candidate.candidate, candidate.features)
      )

      Decoration(candidate.candidate, itemPresentation)
    }

    Stitch.value(candidatePresentations)
  }
}
