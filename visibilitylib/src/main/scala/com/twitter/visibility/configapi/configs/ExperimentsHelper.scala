package com.twitter.visibility.configapi.configs

import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.ExperimentConfigBuilder
import com.twitter.timelines.configapi.Param
import com.twitter.visibility.configapi.params.VisibilityExperiment
import com.twitter.visibility.models.SafetyLevel

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
