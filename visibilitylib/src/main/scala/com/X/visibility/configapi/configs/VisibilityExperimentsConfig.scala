package com.X.visibility.configapi.configs

import com.X.timelines.configapi.Config
import com.X.visibility.configapi.params.RuleParams._
import com.X.visibility.configapi.params.VisibilityExperiments._
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.SafetyLevel._

private[visibility] object VisibilityExperimentsConfig {
  import ExperimentsHelper._

  val TestExperimentConfig: Config = mkABExperimentConfig(TestExperiment, TestHoldbackParam)

  val NotGraduatedUserLabelRuleHoldbackExperimentConfig: Config =
    mkABExperimentConfig(
      NotGraduatedUserLabelRuleExperiment,
      NotGraduatedUserLabelRuleHoldbackExperimentParam
    )

  def config(safetyLevel: SafetyLevel): Seq[Config] = {

    val experimentConfigs = safetyLevel match {

      case Test =>
        Seq(TestExperimentConfig)

      case _ => Seq(NotGraduatedUserLabelRuleHoldbackExperimentConfig)
    }

    experimentConfigs
  }

}
