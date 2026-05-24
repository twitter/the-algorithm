package com.twitter.frigate.pushservice.params

/**
 * This enum defines ML models for push
 */
object PushMLModel extends Enumeration {
  type PushMLModel = Value

  val WeightedOpenOrNtabClickProbability = Value
  val DauProbability = Value
  val OptoutProbability = Value
  val FilteringProbability = Value
  val BigFilteringSupervisedSendingModel = Value
  val BigFilteringSupervisedWithoutSendingModel = Value
  val BigFilteringRLSendingModel = Value
  val BigFilteringRLWithoutSendingModel = Value
  val HealthNsfwProbability = Value
}

object WeightedOpenOrNtabClickModel {
  type ModelNameType = String

  // MR models
  val Periodically_Refreshed_Prod_Model =
    "Periodically_Refreshed_Prod_Model" // used in DBv2 service, needed for gradually migrate via feature switch
}


object OptoutModel {
  type ModelNameType = String
  val D0_has_realtime_features = "D0_has_realtime_features"
  val D0_no_realtime_features = "D0_no_realtime_features"
}

object HealthNsfwModel {
  type ModelNameType = String
  val Q2_2022_Mr_Bqml_Health_Model_NsfwV0 = "Q2_2022_Mr_Bqml_Health_Model_NsfwV0"
}

object BigFilteringSupervisedModel {
  type ModelNameType = String
  val V0_0_BigFiltering_Supervised_Sending_Model = "Q3_2022_bigfiltering_supervised_send_model_v0"
  val V0_0_BigFiltering_Supervised_Without_Sending_Model =
    "Q3_2022_bigfiltering_supervised_not_send_model_v0"
}

object BigFilteringRLModel {
  type ModelNameType = String
  val V0_0_BigFiltering_Rl_Sending_Model = "Q3_2022_bigfiltering_rl_send_model_dqn_dau_15_open"
  val V0_0_BigFiltering_Rl_Without_Sending_Model =
    "Q3_2022_bigfiltering_rl_not_send_model_dqn_dau_15_open"
}

case class PushModelName(
  modelType: PushMLModel.Value,
  version: WeightedOpenOrNtabClickModel.ModelNameType) {
  override def toString: String = {
    modelType.toString + "_" + version
  }
}
