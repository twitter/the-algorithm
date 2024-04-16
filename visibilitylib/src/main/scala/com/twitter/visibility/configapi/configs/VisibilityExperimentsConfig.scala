package com.tw ter.v s b l y.conf gap .conf gs

 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.v s b l y.conf gap .params.RuleParams._
 mport com.tw ter.v s b l y.conf gap .params.V s b l yExper  nts._
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.SafetyLevel._

pr vate[v s b l y] object V s b l yExper  ntsConf g {
   mport Exper  nts lper._

  val TestExper  ntConf g: Conf g = mkABExper  ntConf g(TestExper  nt, TestHoldbackParam)

  val NotGraduatedUserLabelRuleHoldbackExper  ntConf g: Conf g =
    mkABExper  ntConf g(
      NotGraduatedUserLabelRuleExper  nt,
      NotGraduatedUserLabelRuleHoldbackExper  ntParam
    )

  def conf g(safetyLevel: SafetyLevel): Seq[Conf g] = {

    val exper  ntConf gs = safetyLevel match {

      case Test =>
        Seq(TestExper  ntConf g)

      case _ => Seq(NotGraduatedUserLabelRuleHoldbackExper  ntConf g)
    }

    exper  ntConf gs
  }

}
