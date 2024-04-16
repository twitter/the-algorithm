package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.rules.Reason.Tox c y

object Tox c yReplyF lterRules {

  sealed abstract class Tox c yReplyF lterBaseRule(
    act on: Act on)
      extends RuleW hConstantAct on(
        act on = act on,
        cond  on = Cond  on.ToxrfF lteredFromAuthorV e r)

  object Tox c yReplyF lterRule
      extends Tox c yReplyF lterBaseRule(act on = Tombstone(Ep aph.Unava lable)) {

    overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
      RuleParams.EnableTox cReplyF lter ngConversat onRulesParam)
  }

  object Tox c yReplyF lterDropNot f cat onRule
      extends Tox c yReplyF lterBaseRule(act on = Drop(Tox c y)) {

    overr de def enabled: Seq[RuleParam[Boolean]] = Seq(
      RuleParams.EnableTox cReplyF lter ngNot f cat onsRulesParam)
  }
}
