package com.X.product_mixer.component_library.decorator.slice.builder

import com.X.product_mixer.component_library.model.candidate.CursorCandidate
import com.X.product_mixer.component_library.model.candidate.{
  NextCursor => CursorCandidateNextCursor
}
import com.X.product_mixer.component_library.model.candidate.{
  PreviousCursor => CursorCandidatePreviousCursor
}
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.marshalling.response.slice.CursorItem
import com.X.product_mixer.core.model.marshalling.response.slice.NextCursor
import com.X.product_mixer.core.model.marshalling.response.slice.PreviousCursor
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.functional_component.decorator.slice.builder.CandidateSliceItemBuilder

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
