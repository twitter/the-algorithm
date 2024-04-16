package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.RuleParams
 mport com.tw ter.v s b l y.rules.DmConversat onRules._
 mport com.tw ter.v s b l y.rules.DmEventRules._
 mport com.tw ter.v s b l y.rules.Pol cyLevelRuleParams.ruleParams

object Sens  ve d aSett ngsD rect ssagesBaseRules {
  val pol cyRuleParams = Map[Rule, Pol cyLevelRuleParams](
    NsfwH ghPrec s on nterst  alAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aD rect ssagesRulesParam),
    GoreAndV olenceH ghPrec s onAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aD rect ssagesRulesParam),
    NsfwReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aD rect ssagesRulesParam),
    GoreAndV olenceReported ur st csAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aD rect ssagesRulesParam),
    NsfwCard mageAllUsersT etLabelRule -> ruleParams(
      RuleParams.EnableLegacySens  ve d aD rect ssagesRulesParam)
  )
}

case object D rect ssagesPol cy
    extends V s b l yPol cy(
      t etRules = T etDeta lPol cy.t etRules.d ff(L m edEngage ntBaseRules.t etRules),
      dmRules = Seq(
        Deact vatedAuthorRule,
        ErasedAuthorRule
      ),
      pol cyRuleParams = Sens  ve d aSett ngsD rect ssagesBaseRules.pol cyRuleParams
    )

case object D rect ssagesMutedUsersPol cy
    extends V s b l yPol cy(
      userRules = Seq(SuspendedAuthorRule)
    )

case object D rect ssagesSearchPol cy
    extends V s b l yPol cy(
      dmConversat onRules = Seq(
        DropDmConversat onW hUndef nedConversat on nfoRule,
        DropDmConversat onW hUndef nedConversat onT  l neRule,
        Drop naccess bleDmConversat onRule,
        DropEmptyDmConversat onRule,
        DropOneToOneDmConversat onW hUnava lablePart c pantsRule
      ),
      dmEventRules = Seq(
         naccess bleDmEventDropRule,
        H ddenAndDeletedDmEventDropRule,
         ssageCreateEventW hUnava lableSenderDropRule),
      userRules = Seq(ErasedAuthorRule, Deact vatedAuthorRule, SuspendedAuthorRule),
      t etRules =
        Seq(V e rBlocksAuthorRule, V e rMutesAuthorRule) ++ T etDeta lPol cy.t etRules.d ff(
          L m edEngage ntBaseRules.t etRules),
      pol cyRuleParams = Sens  ve d aSett ngsD rect ssagesBaseRules.pol cyRuleParams
    )

case object D rect ssagesP nnedPol cy
    extends V s b l yPol cy(
      dmConversat onRules = Seq(
        DropDmConversat onW hUndef nedConversat on nfoRule,
        DropDmConversat onW hUndef nedConversat onT  l neRule,
        Drop naccess bleDmConversat onRule,
        DropEmptyDmConversat onRule,
        DropOneToOneDmConversat onW hUnava lablePart c pantsRule
      ),
      dmEventRules = Seq(
         naccess bleDmEventDropRule,
        H ddenAndDeletedDmEventDropRule,
         ssageCreateEventW hUnava lableSenderDropRule),
      userRules = Seq(ErasedAuthorRule, Deact vatedAuthorRule, SuspendedAuthorRule),
      t etRules =
        Seq(V e rBlocksAuthorRule, V e rMutesAuthorRule) ++ T etDeta lPol cy.t etRules.d ff(
          L m edEngage ntBaseRules.t etRules),
      pol cyRuleParams = Sens  ve d aSett ngsD rect ssagesBaseRules.pol cyRuleParams
    )

case object D rect ssagesConversat onL stPol cy
    extends V s b l yPol cy(
      dmConversat onRules = Seq(
        DropDmConversat onW hUndef nedConversat on nfoRule,
        DropDmConversat onW hUndef nedConversat onT  l neRule,
        Drop naccess bleDmConversat onRule,
        DropEmptyDmConversat onRule,
        DropOneToOneDmConversat onW hUnava lablePart c pantsRule
      ),
      userRules = Seq(ErasedAuthorRule, Deact vatedAuthorRule, SuspendedAuthorRule),
      t etRules =
        Seq(V e rBlocksAuthorRule, V e rMutesAuthorRule) ++ T etDeta lPol cy.t etRules.d ff(
          L m edEngage ntBaseRules.t etRules),
      pol cyRuleParams = Sens  ve d aSett ngsD rect ssagesBaseRules.pol cyRuleParams
    )

case object D rect ssagesConversat onT  l nePol cy
    extends V s b l yPol cy(
      dmEventRules = Seq(
         naccess bleDmEventDropRule,
        H ddenAndDeletedDmEventDropRule,
         ssageCreateEventW hUnava lableSenderDropRule),
      userRules = Seq(ErasedAuthorRule, Deact vatedAuthorRule, SuspendedAuthorRule),
      t etRules =
        Seq(V e rBlocksAuthorRule, V e rMutesAuthorRule) ++ T etDeta lPol cy.t etRules.d ff(
          L m edEngage ntBaseRules.t etRules),
      pol cyRuleParams = Sens  ve d aSett ngsD rect ssagesBaseRules.pol cyRuleParams
    )

case object D rect ssages nboxPol cy
    extends V s b l yPol cy(
      dmConversat onRules = Seq(
        DropDmConversat onW hUndef nedConversat on nfoRule,
        DropDmConversat onW hUndef nedConversat onT  l neRule,
        Drop naccess bleDmConversat onRule,
        DropEmptyDmConversat onRule,
        DropOneToOneDmConversat onW hUnava lablePart c pantsRule
      ),
      dmEventRules = Seq(
         naccess bleDmEventDropRule,
        H ddenAndDeletedDmEventDropRule,
        DmEvent nOneToOneConversat onW hUnava lableUserDropRule,
         ssageCreateEventW hUnava lableSenderDropRule,
        NonPerspect valDmEventDropRule,
         lco  ssageCreateEventOnlyV s bleToRec p entDropRule,
        GroupEvent nOneToOneConversat onDropRule
      ),
      userRules = Seq(ErasedAuthorRule, Deact vatedAuthorRule, SuspendedAuthorRule),
      t etRules =
        Seq(V e rBlocksAuthorRule, V e rMutesAuthorRule) ++ T etDeta lPol cy.t etRules.d ff(
          L m edEngage ntBaseRules.t etRules),
      pol cyRuleParams = Sens  ve d aSett ngsD rect ssagesBaseRules.pol cyRuleParams
    )
