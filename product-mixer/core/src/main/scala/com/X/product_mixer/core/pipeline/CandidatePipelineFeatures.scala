package com.X.product_mixer.core.pipeline

import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails

private[core] object CandidatePipelineResults
    extends Feature[PipelineQuery, Seq[CandidateWithDetails]]
