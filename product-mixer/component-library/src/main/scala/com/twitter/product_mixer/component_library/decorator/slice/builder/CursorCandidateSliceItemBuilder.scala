package com.twitter.product_mixer.component_library.decorator.slice.builder

import com.twitter.product_mixer.component_library.model.candidate.CursorCandidate
import com.twitter.product_mixer.component_library.model.candidate.{
  NextCursor => CursorCandidateNextCursor
}
import com.twitter.product_mixer.component_library.model.candidate.{
  PreviousCursor => CursorCandidatePreviousCursor
}
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.response.slice.CursorItem
import com.twitter.product_mixer.core.model.marshalling.response.slice.NextCursor
import com.twitter.product_mixer.core.model.marshalling.response.slice.PreviousCursor
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.functional_component.decorator.slice.builder.CandidateSliceItemBuilder

case class CursorCandidateSliceItemBuilder()
    extends CandidateSliceItemBuilder[PipelineQuery, CursorCandidate, CursorItem] {

  override def apply(
    query: PipelineQuery,
    candidate: CursorCandidate,
    featureMap: FeatureMap
  ): CursorItem =
    candidate.cursorType match {
      case CursorCandidateNextCursor => CursorItem(candidate.value, NextCursor)
      case CursorCandidatePreviousCursor => CursorItem(candidate.value, PreviousCursor)
    }
}
