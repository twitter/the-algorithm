package com.twitter.product_mitwitterer.component_library.selector

import com.twitter.product_mitwitterer.component_library.selector.InsertIntoModule.ModuleAndIndetwitter
import com.twitter.product_mitwitterer.component_library.selector.InsertIntoModule.ModuleWithItemsToAddAndOtherCandidates
import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.functional_component.common.SpecificPipelines
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

/**
 * Append all candidates from [[candidatePipeline]] into a module from [[targetModuleCandidatePipeline]].
 * If the results contain multiple modules from the target candidate pipeline,
 * then the candidates will be inserted into the first module.
 *
 * @note this will throw an [[UnsupportedOperationEtwitterception]] if the [[candidatePipeline]] contains any modules.
 *
 * @note this updates the module in the `remainingCandidates`
 */
case class InsertAppendIntoModuleCandidates(
  candidatePipeline: CandidatePipelineIdentifier,
  targetModuleCandidatePipeline: CandidatePipelineIdentifier)
    etwittertends Selector[PipelineQuery] {

  override val pipelineScope: CandidateScope =
    SpecificPipelines(candidatePipeline, targetModuleCandidatePipeline)

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {

    val ModuleWithItemsToAddAndOtherCandidates(
      moduleToUpdateAndIndetwitter,
      itemsToInsertIntoModule,
      otherCandidates) =
      InsertIntoModule.moduleToUpdate(
        candidatePipeline,
        targetModuleCandidatePipeline,
        remainingCandidates)

    val updatedRemainingCandidates = moduleToUpdateAndIndetwitter match {
      case None => remainingCandidates
      case _ if itemsToInsertIntoModule.isEmpty => remainingCandidates
      case Some(ModuleAndIndetwitter(moduleToUpdate, indetwitterOfModuleInOtherCandidates)) =>
        val updatedModuleItems = moduleToUpdate.candidates ++ itemsToInsertIntoModule
        val updatedModule = moduleToUpdate.copy(candidates = updatedModuleItems)
        otherCandidates.updated(indetwitterOfModuleInOtherCandidates, updatedModule)
    }

    SelectorResult(remainingCandidates = updatedRemainingCandidates, result = result)
  }
}
