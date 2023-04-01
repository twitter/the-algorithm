package com.twitter.visibility.configapi.params

private[visibility] object VisibilityExperiments {

  case object TestExperiment extends VisibilityExperiment("vf_test_ddg_7727")

  object CommonBucketId extends Enumeration {
    type CommonBucketId = Value
    val Control = Value("control")
    val Treatment = Value("treatment")
    val None = Value("none")
  }

  case object NotGraduatedUserLabelRuleExperiment
      extends VisibilityExperiment("not_graduated_user_holdback_16332")
}
