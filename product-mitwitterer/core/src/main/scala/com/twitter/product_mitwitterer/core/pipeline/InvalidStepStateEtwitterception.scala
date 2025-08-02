package com.twitter.product_mitwitterer.core.pipeline

import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier

case class InvalidStepStateEtwitterception(step: PipelineStepIdentifier, missingData: String)
    etwittertends Etwitterception(
      s"Invalid Step State: Step $step requires $missingData"
    )
