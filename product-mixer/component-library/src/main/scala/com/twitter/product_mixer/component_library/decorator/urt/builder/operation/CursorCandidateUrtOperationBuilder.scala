package com.twitter.product_mixer.component_library.decorator.urt.builder.operation

import com.twitter.product_mixer.component_library.model.candidate.CursorCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.CursorDisplayTreatment
import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.CursorOperation
import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.CursorType
import com.twitter.product_mixer.core.pipeline.PipelineQuery

case class CursorCandidateUrtOperationBuilder[-Query <: PipelineQuery](
  cursorType: CursorType,
  displayTreatment: Option[CursorDisplayTreatment] = None,
  idToReplace: Option[Long] = None)
    extends CandidateUrtEntryBuilder[Query, CursorCandidate, CursorOperation] {

  override def apply(
    query: Query,
    cursorCandidate: CursorCandidate,
    candidateFeatures: FeatureMap
  ): CursorOperation = CursorOperation(
    id = cursorCandidate.id,
    sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
    value = cursorCandidate.value,
    cursorType = cursorType,
    displayTreatment = displayTreatment,
    idToReplace = idToReplace
  )
}
