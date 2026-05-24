package com.twitter.product_mixer.core.pipeline

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails

private[core] object CandidatePipelineResults
    extends Feature[PipelineQuery, Seq[CandidateWithDetails]]
