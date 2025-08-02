package com.twitter.visibility.configapi.configs

import com.twitter.timelines.configapi.Config
import com.twitter.visibility.configapi.params.RuleParams._
import com.twitter.visibility.configapi.params.VisibilityEtwitterperiments._
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.SafetyLevel._

private[visibility] object VisibilityEtwitterperimentsConfig {
  import EtwitterperimentsHelper._

  val TestEtwitterperimentConfig: Config = mkABEtwitterperimentConfig(TestEtwitterperiment, TestHoldbackParam)

  val NotGraduatedUserLabelRuleHoldbackEtwitterperimentConfig: Config =
    mkABEtwitterperimentConfig(
      NotGraduatedUserLabelRuleEtwitterperiment,
      NotGraduatedUserLabelRuleHoldbackEtwitterperimentParam
    )

  def config(safetyLevel: SafetyLevel): Seq[Config] = {

    val etwitterperimentConfigs = safetyLevel match {

      case Test =>
        Seq(TestEtwitterperimentConfig)

      case _ => Seq(NotGraduatedUserLabelRuleHoldbackEtwitterperimentConfig)
    }

    etwitterperimentConfigs
  }

}
