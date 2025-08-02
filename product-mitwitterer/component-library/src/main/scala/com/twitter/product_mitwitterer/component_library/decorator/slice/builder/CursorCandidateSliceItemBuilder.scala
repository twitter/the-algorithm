package com.twitter.product_mitwitterer.component_library.decorator.slice.builder

import com.twitter.product_mitwitterer.component_library.model.candidate.CursorCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.{
  NetwittertCursor => CursorCandidateNetwittertCursor
}
import com.twitter.product_mitwitterer.component_library.model.candidate.{
  PreviousCursor => CursorCandidatePreviousCursor
}
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.CursorItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.NetwittertCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.PreviousCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.functional_component.decorator.slice.builder.CandidateSliceItemBuilder

case class CursorCandidateSliceItemBuilder()
    etwittertends CandidateSliceItemBuilder[PipelineQuery, CursorCandidate, CursorItem] {

  override def apply(
    query: PipelineQuery,
    candidate: CursorCandidate,
    featureMap: FeatureMap
  ): CursorItem =
    candidate.cursorType match {
      case CursorCandidateNetwittertCursor => CursorItem(candidate.value, NetwittertCursor)
      case CursorCandidatePreviousCursor => CursorItem(candidate.value, PreviousCursor)
    }
}
