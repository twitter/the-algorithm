package com.ExTwitter.home_mixer.functional_component.selector

import com.ExTwitter.product_mixer.component_library.model.presentation.urt.UrtModulePresentation
import com.ExTwitter.product_mixer.core.functional_component.common.CandidateScope
import com.ExTwitter.product_mixer.core.functional_component.common.CandidateScope.PartitionedCandidates
import com.ExTwitter.product_mixer.core.functional_component.selector.Selector
import com.ExTwitter.product_mixer.core.functional_component.selector.SelectorResult
import com.ExTwitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.ExTwitter.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

/**
 * This selector updates the id of the conversation modules to be the head of the module's id.
 */
case class UpdateConversationModuleId(
  override val pipelineScope: CandidateScope)
    extends Selector[PipelineQuery] {

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val PartitionedCandidates(selectedCandidates, otherCandidates) =
      pipelineScope.partition(remainingCandidates)

    val updatedCandidates = selectedCandidates.map {
      case module @ ModuleCandidateWithDetails(candidates, presentationOpt, _) =>
        val updatedPresentation = presentationOpt.map {
          case urtModule @ UrtModulePresentation(timelineModule) =>
            urtModule.copy(timelineModule =
              timelineModule.copy(id = candidates.head.candidateIdLong))
        }
        module.copy(presentation = updatedPresentation)
      case candidate => candidate
    }

    SelectorResult(remainingCandidates = updatedCandidates ++ otherCandidates, result = result)
  }
}
