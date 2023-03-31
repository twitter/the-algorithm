package com.twitter.product_mixer.core.pipeline

import com.twitter.product_mixer.core.model.common.identifier.PipelineStepIdentifier

case class InvalidStepStateException(step: PipelineStepIdentifier, missingData: String)
    extends Exception(
      s"Invalid Step State: Step $step requires $missingData"
    )
