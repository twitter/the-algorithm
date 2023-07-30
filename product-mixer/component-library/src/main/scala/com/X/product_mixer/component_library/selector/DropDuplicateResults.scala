package com.X.product_mixer.component_library.selector

import com.X.product_mixer.component_library.selector.DropSelector.dropDuplicates
import com.X.product_mixer.core.functional_component.common.AllPipelines
import com.X.product_mixer.core.functional_component.common.CandidateScope
import com.X.product_mixer.core.functional_component.selector.Selector
import com.X.product_mixer.core.functional_component.selector.SelectorResult
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.pipeline.PipelineQuery

/**
 * Keep only the first instance of a candidate in the `result` as determined by comparing
 * the contained candidate ID and class type. Subsequent matching instances will be dropped. For
 * more details, see DropSelector#dropDuplicates
 *
 * @param duplicationKey how to generate the key used to identify duplicate candidates (by default use id and class name)
 * @param mergeStrategy how to merge two candidates with the same key (by default pick the first one)
 *
 * @note [[com.X.product_mixer.component_library.model.candidate.CursorCandidate]] are ignored.
 * @note [[com.X.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails]] are ignored.
 *
 * @example if `result`
 * `Seq(sourceA_Id1, sourceA_Id1, sourceA_Id2, sourceB_id1, sourceB_id2, sourceB_id3, sourceC_id4)`
 * then the output result will be `Seq(sourceA_Id1, sourceA_Id2, sourceB_id3, sourceC_id4)`
 */
case class DropDuplicateResults(
  duplicationKey: DeduplicationKey[_] = IdAndClassDuplicationKey,
  mergeStrategy: CandidateMergeStrategy = PickFirstCandidateMerger)
    extends Selector[PipelineQuery] {

  override val pipelineScope: CandidateScope = AllPipelines

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val dedupedResults = dropDuplicates(
      pipelineScope = pipelineScope,
      candidates = result,
      duplicationKey = duplicationKey,
      mergeStrategy = mergeStrategy)

    SelectorResult(remainingCandidates = remainingCandidates, result = dedupedResults)
  }
}
