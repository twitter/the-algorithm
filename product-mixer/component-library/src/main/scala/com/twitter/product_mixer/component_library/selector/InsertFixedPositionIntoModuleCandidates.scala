package com.twitter.product_mixer.component_library.selector

import com.twitter.product_mixer.component_library.selector.InsertIntoModule.ModuleAndIndex
import com.twitter.product_mixer.component_library.selector.InsertIntoModule.ModuleWithItemsToAddAndOtherCandidates
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

/**
 * Insert all candidates from [[candidatePipeline]] at a 0-indexed fixed position into a module from
 * [[targetModuleCandidatePipeline]]. If the results contain multiple modules from the target candidate
 * pipeline, then the candidates will be inserted into the first module. If the target module's
 * items are a shorter length than the requested position, then the candidates will be appended
 * to the results.
 *
 * @note this will throw an [[UnsupportedOperationException]] if the [[candidatePipeline]] contains any modules.
 *
 * @note this updates the module in the `remainingCandidates`
 */
case class InsertFixedPositionIntoModuleCandidates(
  candidatePipeline: CandidatePipelineIdentifier,
  targetModuleCandidatePipeline: CandidatePipelineIdentifier,
  positionParam: Param[Int])
    extends Selector[PipelineQuery] {

  override val pipelineScope: CandidateScope =
    SpecificPipelines(candidatePipeline, targetModuleCandidatePipeline)

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {

    val position = query.params(positionParam)
    assert(position >= 0, "Position must be equal to or greater than zero")

    val ModuleWithItemsToAddAndOtherCandidates(
      moduleToUpdateAndIndex,
      itemsToInsertIntoModule,
      otherCandidates) =
      InsertIntoModule.moduleToUpdate(
        candidatePipeline,
        targetModuleCandidatePipeline,
        remainingCandidates)

    val updatedRemainingCandidates = moduleToUpdateAndIndex match {
      case None => remainingCandidates
      case _ if itemsToInsertIntoModule.isEmpty => remainingCandidates
      case Some(ModuleAndIndex(moduleToUpdate, indexOfModuleInOtherCandidates)) =>
        val updatedModuleItems =
          if (position < moduleToUpdate.candidates.length) {
            val (left, right) = moduleToUpdate.candidates.splitAt(position)
            left ++ itemsToInsertIntoModule ++ right
          } else {
            moduleToUpdate.candidates ++ itemsToInsertIntoModule
          }
        val updatedModule = moduleToUpdate.copy(candidates = updatedModuleItems)
        otherCandidates.updated(indexOfModuleInOtherCandidates, updatedModule)
    }

    SelectorResult(remainingCandidates = updatedRemainingCandidates, result = result)
  }
}
