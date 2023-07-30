package com.X.visibility.configapi.configs

import com.X.timelines.configapi.Config
import com.X.timelines.configapi.ExperimentConfigBuilder
import com.X.timelines.configapi.Param
import com.X.visibility.configapi.params.VisibilityExperiment
import com.X.visibility.models.SafetyLevel

object ExperimentsHelper {

  def mkABExperimentConfig(experiment: VisibilityExperiment, param: Param[Boolean]): Config = {
    ExperimentConfigBuilder(experiment)
      .addBucket(
        experiment.ControlBucket,
        param := true
      )
      .addBucket(
        experiment.TreatmentBucket,
        param := false
      )
      .build
  }

  def mkABExperimentConfig(experiment: VisibilityExperiment, safetyLevel: SafetyLevel): Config =
    mkABExperimentConfig(experiment, safetyLevel.enabledParam)
}
