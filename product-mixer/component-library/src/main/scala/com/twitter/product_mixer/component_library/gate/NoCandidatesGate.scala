package com.ExTwitter.product_mixer.component_library.gate

import com.ExTwitter.product_mixer.core.functional_component.common.CandidateScope
import com.ExTwitter.product_mixer.core.functional_component.gate.QueryAndCandidateGate
import com.ExTwitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.ExTwitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

/**
 * A Gate that only continues if the previously returned candidates are empty. This is useful
 * for gating dependent candidate pipelines that are intedned to be used as a backfill when there
 * are no candidates available.
 */
case class NoCandidatesGate(scope: CandidateScope) extends QueryAndCandidateGate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier("NoCandidates")
  override def shouldContinue(
    query: PipelineQuery,
    candidates: Seq[CandidateWithDetails]
  ): Stitch[Boolean] = Stitch.value(scope.partition(candidates).candidatesInScope.isEmpty)
}
