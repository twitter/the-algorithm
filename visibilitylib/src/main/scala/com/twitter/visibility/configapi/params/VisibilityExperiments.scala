package com.tw ter.v s b l y.conf gap .params

pr vate[v s b l y] object V s b l yExper  nts {

  case object TestExper  nt extends V s b l yExper  nt("vf_test_ddg_7727")

  object CommonBucket d extends Enu rat on {
    type CommonBucket d = Value
    val Control = Value("control")
    val Treat nt = Value("treat nt")
    val None = Value("none")
  }

  case object NotGraduatedUserLabelRuleExper  nt
      extends V s b l yExper  nt("not_graduated_user_holdback_16332")
}
