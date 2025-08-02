package com.twitter.visibility.configapi.configs

import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.EtwitterperimentConfigBuilder
import com.twitter.timelines.configapi.Param
import com.twitter.visibility.configapi.params.VisibilityEtwitterperiment
import com.twitter.visibility.models.SafetyLevel

object EtwitterperimentsHelper {

  def mkABEtwitterperimentConfig(etwitterperiment: VisibilityEtwitterperiment, param: Param[Boolean]): Config = {
    EtwitterperimentConfigBuilder(etwitterperiment)
      .addBucket(
        etwitterperiment.ControlBucket,
        param := true
      )
      .addBucket(
        etwitterperiment.TreatmentBucket,
        param := false
      )
      .build
  }

  def mkABEtwitterperimentConfig(etwitterperiment: VisibilityEtwitterperiment, safetyLevel: SafetyLevel): Config =
    mkABEtwitterperimentConfig(etwitterperiment, safetyLevel.enabledParam)
}
