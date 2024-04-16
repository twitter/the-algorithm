package com.tw ter.fr gate.pushserv ce.params

/**
 * T  enum def nes ML models for push
 */
object PushMLModel extends Enu rat on {
  type PushMLModel = Value

  val   ghtedOpenOrNtabCl ckProbab l y = Value
  val DauProbab l y = Value
  val OptoutProbab l y = Value
  val F lter ngProbab l y = Value
  val B gF lter ngSuperv sedSend ngModel = Value
  val B gF lter ngSuperv sedW houtSend ngModel = Value
  val B gF lter ngRLSend ngModel = Value
  val B gF lter ngRLW houtSend ngModel = Value
  val  althNsfwProbab l y = Value
}

object   ghtedOpenOrNtabCl ckModel {
  type ModelNa Type = Str ng

  // MR models
  val Per od cally_Refres d_Prod_Model =
    "Per od cally_Refres d_Prod_Model" // used  n DBv2 serv ce, needed for gradually m grate v a feature sw ch
}


object OptoutModel {
  type ModelNa Type = Str ng
  val D0_has_realt  _features = "D0_has_realt  _features"
  val D0_no_realt  _features = "D0_no_realt  _features"
}

object  althNsfwModel {
  type ModelNa Type = Str ng
  val Q2_2022_Mr_Bqml_ alth_Model_NsfwV0 = "Q2_2022_Mr_Bqml_ alth_Model_NsfwV0"
}

object B gF lter ngSuperv sedModel {
  type ModelNa Type = Str ng
  val V0_0_B gF lter ng_Superv sed_Send ng_Model = "Q3_2022_b gf lter ng_superv sed_send_model_v0"
  val V0_0_B gF lter ng_Superv sed_W hout_Send ng_Model =
    "Q3_2022_b gf lter ng_superv sed_not_send_model_v0"
}

object B gF lter ngRLModel {
  type ModelNa Type = Str ng
  val V0_0_B gF lter ng_Rl_Send ng_Model = "Q3_2022_b gf lter ng_rl_send_model_dqn_dau_15_open"
  val V0_0_B gF lter ng_Rl_W hout_Send ng_Model =
    "Q3_2022_b gf lter ng_rl_not_send_model_dqn_dau_15_open"
}

case class PushModelNa (
  modelType: PushMLModel.Value,
  vers on:   ghtedOpenOrNtabCl ckModel.ModelNa Type) {
  overr de def toStr ng: Str ng = {
    modelType.toStr ng + "_" + vers on
  }
}
