package com.twitter.visibility.configapi.params

private[visibility] object VisibilityEtwitterperiments {

  case object TestEtwitterperiment etwittertends VisibilityEtwitterperiment("vf_test_ddg_7727")

  object CommonBucketId etwittertends Enumeration {
    type CommonBucketId = Value
    val Control = Value("control")
    val Treatment = Value("treatment")
    val None = Value("none")
  }

  case object NotGraduatedUserLabelRuleEtwitterperiment
      etwittertends VisibilityEtwitterperiment("not_graduated_user_holdback_16332")
}
