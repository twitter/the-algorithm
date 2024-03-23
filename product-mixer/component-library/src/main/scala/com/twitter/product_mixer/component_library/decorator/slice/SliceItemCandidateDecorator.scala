package com.ExTwitter.product_mixer.component_library.decorator.slice

import com.ExTwitter.product_mixer.component_library.model.candidate.CursorCandidate
import com.ExTwitter.product_mixer.component_library.model.presentation.slice.SliceItemPresentation
import com.ExTwitter.product_mixer.core.model.common.UniversalNoun
import com.ExTwitter.product_mixer.core.model.common.identifier.DecoratorIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.response.slice.CursorItem
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.ExTwitter.product_mixer.core.functional_component.decorator.Decoration
import com.ExTwitter.product_mixer.core.functional_component.decorator.slice.builder.CandidateSliceItemBuilder
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.stitch.Stitch

/**
 * Adds a [[Decoration]] for all `candidates` that are [[CursorCandidate]]s
 *
 * @note Only [[CursorCandidate]]s get decorated in [[SliceItemCandidateDecorator]]
 *       because the [[com.ExTwitter.product_mixer.component_library.premarshaller.slice.SliceDomainMarshaller]]
 *       handles the undecorated non-[[CursorCandidate]] `candidates` directly.
 */
case class SliceItemCandidateDecorator[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
  cursorBuilder: CandidateSliceItemBuilder[Query, CursorCandidate, CursorItem],
  override val identifier: DecoratorIdentifier = DecoratorIdentifier("SliceItemCandidate"))
    extends CandidateDecorator[Query, Candidate] {

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[Seq[Decoration]] = {
    val cursorPresentations = candidates.collect {
      case CandidateWithFeatures(candidate: CursorCandidate, features) =>
        val cursorItem = cursorBuilder(query, candidate, features)
        val presentation = SliceItemPresentation(sliceItem = cursorItem)

        Decoration(candidate, presentation)
    }

    Stitch.value(cursorPresentations)
  }
}
