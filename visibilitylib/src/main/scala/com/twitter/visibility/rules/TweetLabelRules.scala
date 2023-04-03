packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.common.ModelonlScorelonThrelonsholds
import com.twittelonr.visibility.common.actions.AvoidRelonason
import com.twittelonr.visibility.common.actions.AvoidRelonason.MightNotBelonSuitablelonForAds
import com.twittelonr.visibility.common.actions.LimitelondelonngagelonmelonntRelonason
import com.twittelonr.visibility.common.actions.TwelonelontVisibilityNudgelonRelonason
import com.twittelonr.visibility.configapi.configs.DeloncidelonrKelony
import com.twittelonr.visibility.configapi.params.FSRulelonParams.HighSpammyTwelonelontContelonntScorelonSelonarchLatelonstProdTwelonelontLabelonlDropRulelonThrelonsholdParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.HighSpammyTwelonelontContelonntScorelonSelonarchTopProdTwelonelontLabelonlDropRulelonThrelonsholdParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.HighSpammyTwelonelontContelonntScorelonTrelonndLatelonstTwelonelontLabelonlDropRulelonThrelonsholdParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.HighSpammyTwelonelontContelonntScorelonTrelonndTopTwelonelontLabelonlDropRulelonThrelonsholdParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.SkipTwelonelontDelontailLimitelondelonngagelonmelonntRulelonelonnablelondParam
import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams._
import com.twittelonr.visibility.modelonls.TwelonelontSafelontyLabelonlTypelon
import com.twittelonr.visibility.rulelons.Condition._
import com.twittelonr.visibility.rulelons.Condition.{Truelon => TruelonCondition}
import com.twittelonr.visibility.rulelons.Relonason._
import com.twittelonr.visibility.rulelons.RulelonActionSourcelonBuildelonr.TwelonelontSafelontyLabelonlSourcelonBuildelonr

objelonct AbusivelonTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.Abusivelon
    )
    with DoelonsLogVelonrdict

objelonct AbusivelonNonFollowelonrTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithTwelonelontLabelonlRulelon(
      Drop(Toxicity),
      TwelonelontSafelontyLabelonlTypelon.Abusivelon
    )

objelonct AbusivelonUqfNonFollowelonrTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithUqfTwelonelontLabelonlRulelon(
      Drop(Toxicity),
      TwelonelontSafelontyLabelonlTypelon.Abusivelon
    )

objelonct AbusivelonHighReloncallTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.AbusivelonHighReloncall
    )

objelonct AbusivelonHighReloncallNonFollowelonrTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithTwelonelontLabelonlRulelon(
      Intelonrstitial(PossiblyUndelonsirablelon),
      TwelonelontSafelontyLabelonlTypelon.AbusivelonHighReloncall
    )

objelonct AutomationTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.Automation
    )

objelonct BystandelonrAbusivelonTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.BystandelonrAbusivelon
    )

objelonct BystandelonrAbusivelonNonFollowelonrTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.BystandelonrAbusivelon
    )

abstract class DuplicatelonContelonntTwelonelontLabelonlRulelon(action: Action)
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      action,
      TwelonelontSafelontyLabelonlTypelon.DuplicatelonContelonnt
    )

objelonct DuplicatelonContelonntTwelonelontLabelonlDropRulelon
    elonxtelonnds DuplicatelonContelonntTwelonelontLabelonlRulelon(Drop(TwelonelontLabelonlDuplicatelonContelonnt))

objelonct DuplicatelonContelonntTwelonelontLabelonlTombstonelonRulelon
    elonxtelonnds DuplicatelonContelonntTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Unavailablelon))

objelonct DuplicatelonMelonntionTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.DuplicatelonMelonntion
    )

objelonct DuplicatelonMelonntionUqfTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithUqfTwelonelontLabelonlRulelon(
      Drop(TwelonelontLabelonlDuplicatelonMelonntion),
      TwelonelontSafelontyLabelonlTypelon.DuplicatelonMelonntion
    )

objelonct GorelonAndViolelonncelonTwelonelontLabelonlRulelon
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        TwelonelontComposelondBelonforelon(TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelon.DelonpreloncatelondAt)
      ),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelon
    )

objelonct LivelonLowQualityTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.LivelonLowQuality
    )

objelonct LowQualityMelonntionTwelonelontLabelonlRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(LowQualityMelonntion),
      And(
        TwelonelontHasLabelonlForPelonrspelonctivalUselonr(TwelonelontSafelontyLabelonlTypelon.LowQualityMelonntion),
        VielonwelonrHasUqfelonnablelond
      )
    )

abstract class NsfwCardImagelonTwelonelontLabelonlBaselonRulelon(
  ovelonrridelon val action: Action,
  val additionalCondition: Condition = TruelonCondition,
) elonxtelonnds RulelonWithConstantAction(
      action,
      And(
        additionalCondition,
        TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.NsfwCardImagelon)
      )
    )

objelonct NsfwCardImagelonTwelonelontLabelonlRulelon
    elonxtelonnds NsfwCardImagelonTwelonelontLabelonlBaselonRulelon(
      action = Drop(Nsfw),
      additionalCondition = NonAuthorVielonwelonr,
    )

objelonct NsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds NsfwCardImagelonTwelonelontLabelonlBaselonRulelon(
      action = Intelonrstitial(Nsfw)
    )

objelonct NsfwCardImagelonAvoidAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds NsfwCardImagelonTwelonelontLabelonlBaselonRulelon(
      action = Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct NsfwCardImagelonAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds NsfwCardImagelonTwelonelontLabelonlBaselonRulelon(
      action = Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct SelonarchAvoidTwelonelontNsfwAdminRulelon
    elonxtelonnds RulelonWithConstantAction(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontHasNsfwAdminAuthor
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct SelonarchAvoidTwelonelontNsfwUselonrRulelon
    elonxtelonnds RulelonWithConstantAction(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontHasNsfwUselonrAuthor
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct NsfwCardImagelonAllUselonrsTwelonelontLabelonlDropRulelon
    elonxtelonnds NsfwCardImagelonTwelonelontLabelonlBaselonRulelon(
      action = Drop(Nsfw),
    )

objelonct HighProactivelonTosScorelonTwelonelontLabelonlDropRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.HighProactivelonTosScorelon
    )

objelonct HighProactivelonTosScorelonTwelonelontLabelonlDropSelonarchRulelon
    elonxtelonnds NonAuthorAndNonFollowelonrWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.HighProactivelonTosScorelon
    )

objelonct NsfwHighPreloncisionTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
    )

objelonct NsfwHighPreloncisionAllUselonrsTwelonelontLabelonlDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
    )

objelonct NsfwHighPreloncisionInnelonrQuotelondTwelonelontLabelonlRulelon
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      And(IsQuotelondInnelonrTwelonelont, NonAuthorVielonwelonr),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonNsfwHpQuotelondTwelonelontDropRulelonParam)
}

objelonct NsfwHighPreloncisionTombstonelonInnelonrQuotelondTwelonelontLabelonlRulelon
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Tombstonelon(elonpitaph.Unavailablelon),
      And(IsQuotelondInnelonrTwelonelont, NonAuthorVielonwelonr),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonNsfwHpQuotelondTwelonelontTombstonelonRulelonParam)
}

objelonct GorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
    )

objelonct NsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
    )

objelonct GorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
    )

objelonct NsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Intelonrstitial(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
    )
    with DoelonsLogVelonrdict

objelonct GorelonAndViolelonncelonHighPreloncisionAvoidAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Intelonrstitial(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
    )
    with DoelonsLogVelonrdict {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision)
  )
}

objelonct NsfwRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct NsfwRelonportelondHelonuristicsAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Intelonrstitial(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
    )

objelonct GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Intelonrstitial(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
    )

objelonct GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct GorelonAndViolelonncelonRelonportelondHelonuristicsAvoidAdPlacelonmelonntAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonAvoidNsfwRulelonsParam)
}

objelonct GorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
    )

objelonct NsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
    )

objelonct GorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
    )

objelonct NsfwHighReloncallTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighReloncall
    )

objelonct NsfwHighReloncallAllUselonrsTwelonelontLabelonlDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighReloncall
    )

abstract class PdnaTwelonelontLabelonlRulelon(
  ovelonrridelon val action: Action,
  val additionalCondition: Condition)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      And(NonAuthorVielonwelonr, additionalCondition),
      TwelonelontSafelontyLabelonlTypelon.Pdna
    )

objelonct PdnaTwelonelontLabelonlRulelon elonxtelonnds PdnaTwelonelontLabelonlRulelon(Drop(PdnaTwelonelont), Condition.Truelon)

objelonct PdnaTwelonelontLabelonlTombstonelonRulelon
    elonxtelonnds PdnaTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Unavailablelon), Condition.Truelon)

objelonct PdnaQuotelondTwelonelontLabelonlTombstonelonRulelon
    elonxtelonnds PdnaTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Unavailablelon), Condition.IsQuotelondInnelonrTwelonelont) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonPdnaQuotelondTwelonelontTombstonelonRulelonParam)
}

objelonct PdnaAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.Pdna
    )

objelonct SelonarchBlacklistTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.SelonarchBlacklist
    )

objelonct SelonarchBlacklistHighReloncallTwelonelontLabelonlDropRulelon
    elonxtelonnds NonAuthorAndNonFollowelonrWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.SelonarchBlacklistHighReloncall
    )

abstract class SpamTwelonelontLabelonlRulelon(
  ovelonrridelon val action: Action,
  val additionalCondition: Condition)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      And(NonAuthorVielonwelonr, additionalCondition),
      TwelonelontSafelontyLabelonlTypelon.Spam
    )
    with DoelonsLogVelonrdict

objelonct SpamTwelonelontLabelonlRulelon elonxtelonnds SpamTwelonelontLabelonlRulelon(Drop(TwelonelontLabelonlelondSpam), Condition.Truelon)

objelonct SpamTwelonelontLabelonlTombstonelonRulelon
    elonxtelonnds SpamTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Unavailablelon), Condition.Truelon)

objelonct SpamQuotelondTwelonelontLabelonlTombstonelonRulelon
    elonxtelonnds SpamTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Unavailablelon), Condition.IsQuotelondInnelonrTwelonelont) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonSpamQuotelondTwelonelontTombstonelonRulelonParam)
}

objelonct SpamAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.Spam
    )

abstract class BouncelonTwelonelontLabelonlRulelon(ovelonrridelon val action: Action)
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      action,
      TwelonelontSafelontyLabelonlTypelon.Bouncelon
    )

objelonct BouncelonTwelonelontLabelonlRulelon elonxtelonnds BouncelonTwelonelontLabelonlRulelon(Drop(Bouncelon))

objelonct BouncelonTwelonelontLabelonlTombstonelonRulelon elonxtelonnds BouncelonTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Bouncelond))

abstract class BouncelonOutelonrTwelonelontLabelonlRulelon(ovelonrridelon val action: Action)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      And(Not(Condition.IsQuotelondInnelonrTwelonelont), NonAuthorVielonwelonr),
      TwelonelontSafelontyLabelonlTypelon.Bouncelon
    )

objelonct BouncelonOutelonrTwelonelontTombstonelonRulelon elonxtelonnds BouncelonOutelonrTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Bouncelond))

objelonct BouncelonQuotelondTwelonelontTombstonelonRulelon
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Tombstonelon(elonpitaph.Bouncelond),
      Condition.IsQuotelondInnelonrTwelonelont,
      TwelonelontSafelontyLabelonlTypelon.Bouncelon
    )

objelonct BouncelonAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Bouncelon),
      TwelonelontSafelontyLabelonlTypelon.Bouncelon
    )


abstract class SpamHighReloncallTwelonelontLabelonlRulelon(action: Action)
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      action,
      TwelonelontSafelontyLabelonlTypelon.SpamHighReloncall
    )

objelonct SpamHighReloncallTwelonelontLabelonlDropRulelon
    elonxtelonnds SpamHighReloncallTwelonelontLabelonlRulelon(Drop(SpamHighReloncallTwelonelont))

objelonct SpamHighReloncallTwelonelontLabelonlTombstonelonRulelon
    elonxtelonnds SpamHighReloncallTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Unavailablelon))

objelonct UntrustelondUrlAllVielonwelonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.UntrustelondUrl
    )

objelonct DownrankSpamRelonplyAllVielonwelonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.DownrankSpamRelonply
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonDownrankSpamRelonplySelonctioningRulelonParam)
}

objelonct UntrustelondUrlTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.UntrustelondUrl
    )

objelonct DownrankSpamRelonplyTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.DownrankSpamRelonply
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonDownrankSpamRelonplySelonctioningRulelonParam)
}

objelonct UntrustelondUrlUqfNonFollowelonrTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithUqfTwelonelontLabelonlRulelon(
      Drop(UntrustelondUrl),
      TwelonelontSafelontyLabelonlTypelon.UntrustelondUrl
    )

objelonct DownrankSpamRelonplyUqfNonFollowelonrTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithUqfTwelonelontLabelonlRulelon(
      Drop(SpamRelonplyDownRank),
      TwelonelontSafelontyLabelonlTypelon.DownrankSpamRelonply
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonDownrankSpamRelonplySelonctioningRulelonParam)
}

objelonct NsfaHighReloncallTwelonelontLabelonlRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.NsfaHighReloncall)
      )
    )

objelonct NsfaHighReloncallTwelonelontLabelonlIntelonrstitialRulelon
    elonxtelonnds RulelonWithConstantAction(
      Intelonrstitial(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.NsfaHighReloncall)
      )
    )

objelonct NsfwVidelonoTwelonelontLabelonlDropRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwVidelono
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonNsfwTelonxtSelonctioningRulelonParam)
}

objelonct NsfwTelonxtTwelonelontLabelonlDropRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwTelonxt
    )

objelonct NsfwVidelonoAllUselonrsTwelonelontLabelonlDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwVidelono
    )

objelonct NsfwTelonxtAllUselonrsTwelonelontLabelonlDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Nsfw),
      TwelonelontSafelontyLabelonlTypelon.NsfwTelonxt
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonNsfwTelonxtSelonctioningRulelonParam)
}

abstract class BaselonLowQualityTwelonelontLabelonlRulelon(action: Action)
    elonxtelonnds RulelonWithConstantAction(
      action,
      And(
        TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.LowQuality),
        TwelonelontComposelondBelonforelon(PublicIntelonrelonst.PolicyConfig.LowQualityProxyLabelonlStart),
        NonAuthorVielonwelonr
      )
    )
    with DoelonsLogVelonrdict {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.LowQuality))
}

objelonct LowQualityTwelonelontLabelonlDropRulelon elonxtelonnds BaselonLowQualityTwelonelontLabelonlRulelon(Drop(LowQualityTwelonelont))

objelonct LowQualityTwelonelontLabelonlTombstonelonRulelon
    elonxtelonnds BaselonLowQualityTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Unavailablelon))

abstract class SafelontyCrisisLelonvelonlDropRulelon(lelonvelonl: Int, condition: Condition = TruelonCondition)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      And(
        NonAuthorVielonwelonr,
        condition,
        TwelonelontHasSafelontyLabelonlWithScorelonelonqInt(TwelonelontSafelontyLabelonlTypelon.SafelontyCrisis, lelonvelonl)
      ),
      TwelonelontSafelontyLabelonlTypelon.SafelontyCrisis
    )

objelonct SafelontyCrisisAnyLelonvelonlDropRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.SafelontyCrisis
    )

objelonct SafelontyCrisisLelonvelonl2DropRulelon elonxtelonnds SafelontyCrisisLelonvelonlDropRulelon(2, Not(VielonwelonrDoelonsFollowAuthor))

objelonct SafelontyCrisisLelonvelonl3DropRulelon elonxtelonnds SafelontyCrisisLelonvelonlDropRulelon(3, Not(VielonwelonrDoelonsFollowAuthor))

objelonct SafelontyCrisisLelonvelonl4DropRulelon elonxtelonnds SafelontyCrisisLelonvelonlDropRulelon(4)

abstract class SafelontyCrisisLelonvelonlSelonctionRulelon(lelonvelonl: Int)
    elonxtelonnds ConditionWithNotInnelonrCirclelonOfFrielonndsRulelon(
      ConvelonrsationSelonctionAbusivelonQuality,
      And(
        TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.SafelontyCrisis),
        TwelonelontHasSafelontyLabelonlWithScorelonelonqInt(TwelonelontSafelontyLabelonlTypelon.SafelontyCrisis, lelonvelonl))
    ) {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.SafelontyCrisis))
}

objelonct SafelontyCrisisLelonvelonl3SelonctionRulelon
    elonxtelonnds SafelontyCrisisLelonvelonlSelonctionRulelon(3)
    with DoelonsLogVelonrdictDeloncidelonrelond {
  ovelonrridelon delonf velonrdictLogDeloncidelonrKelony = DeloncidelonrKelony.elonnablelonDownlelonvelonlRulelonVelonrdictLogging
}

objelonct SafelontyCrisisLelonvelonl4SelonctionRulelon
    elonxtelonnds SafelontyCrisisLelonvelonlSelonctionRulelon(4)
    with DoelonsLogVelonrdictDeloncidelonrelond {
  ovelonrridelon delonf velonrdictLogDeloncidelonrKelony = DeloncidelonrKelony.elonnablelonDownlelonvelonlRulelonVelonrdictLogging
}

objelonct DoNotAmplifyDropRulelon
    elonxtelonnds NonFollowelonrWithTwelonelontLabelonlRulelon(Drop(Unspeloncifielond), TwelonelontSafelontyLabelonlTypelon.DoNotAmplify)

objelonct DoNotAmplifyAllVielonwelonrsDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(Drop(Unspeloncifielond), TwelonelontSafelontyLabelonlTypelon.DoNotAmplify)

objelonct DoNotAmplifySelonctionRulelon
    elonxtelonnds ConditionWithNotInnelonrCirclelonOfFrielonndsRulelon(
      ConvelonrsationSelonctionAbusivelonQuality,
      TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.DoNotAmplify))

objelonct HighPSpammyScorelonAllVielonwelonrDropRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(Drop(Unspeloncifielond), TwelonelontSafelontyLabelonlTypelon.HighPSpammyTwelonelontScorelon)

objelonct HighPSpammyTwelonelontScorelonSelonarchTwelonelontLabelonlDropRulelon
    elonxtelonnds RulelonWithConstantAction(
      action = Drop(Unspeloncifielond),
      condition = And(
        LoggelondOutOrVielonwelonrNotFollowingAuthor,
        TwelonelontHasLabelonlWithScorelonAbovelonThrelonshold(
          TwelonelontSafelontyLabelonlTypelon.HighPSpammyTwelonelontScorelon,
          ModelonlScorelonThrelonsholds.HighPSpammyTwelonelontScorelonThrelonshold)
      )
    )
    with DoelonsLogVelonrdictDeloncidelonrelond {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonHighPSpammyTwelonelontScorelonSelonarchTwelonelontLabelonlDropRulelonParam)
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.HighPSpammyTwelonelontScorelon))
  ovelonrridelon delonf velonrdictLogDeloncidelonrKelony: DeloncidelonrKelony.Valuelon =
    DeloncidelonrKelony.elonnablelonSpammyTwelonelontRulelonVelonrdictLogging
}

objelonct AdsManagelonrDelonnyListAllUselonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.AdsManagelonrDelonnyList
    )

abstract class SmytelonSpamTwelonelontLabelonlRulelon(action: Action)
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      action,
      TwelonelontSafelontyLabelonlTypelon.SmytelonSpamTwelonelont
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonSmytelonSpamTwelonelontRulelonParam)
}

objelonct SmytelonSpamTwelonelontLabelonlDropRulelon elonxtelonnds SmytelonSpamTwelonelontLabelonlRulelon(Drop(TwelonelontLabelonlelondSpam))

objelonct SmytelonSpamTwelonelontLabelonlTombstonelonRulelon
    elonxtelonnds SmytelonSpamTwelonelontLabelonlRulelon(Tombstonelon(elonpitaph.Unavailablelon))

objelonct SmytelonSpamTwelonelontLabelonlDropSelonarchRulelon elonxtelonnds SmytelonSpamTwelonelontLabelonlRulelon(Drop(Unspeloncifielond))

objelonct HighSpammyTwelonelontContelonntScorelonSelonarchLatelonstTwelonelontLabelonlDropRulelon
    elonxtelonnds RulelonWithConstantAction(
      action = Drop(Unspeloncifielond),
      condition = And(
        Not(IsTwelonelontInTwelonelontLelonvelonlStcmHoldback),
        LoggelondOutOrVielonwelonrNotFollowingAuthor,
        TwelonelontHasLabelonlWithScorelonAbovelonThrelonsholdWithParam(
          TwelonelontSafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon,
          HighSpammyTwelonelontContelonntScorelonSelonarchLatelonstProdTwelonelontLabelonlDropRulelonThrelonsholdParam)
      )
    )
    with DoelonsLogVelonrdictDeloncidelonrelond {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon))
  ovelonrridelon delonf velonrdictLogDeloncidelonrKelony: DeloncidelonrKelony.Valuelon =
    DeloncidelonrKelony.elonnablelonSpammyTwelonelontRulelonVelonrdictLogging
}

objelonct HighSpammyTwelonelontContelonntScorelonSelonarchTopTwelonelontLabelonlDropRulelon
    elonxtelonnds RulelonWithConstantAction(
      action = Drop(Unspeloncifielond),
      condition = And(
        Not(IsTwelonelontInTwelonelontLelonvelonlStcmHoldback),
        LoggelondOutOrVielonwelonrNotFollowingAuthor,
        TwelonelontHasLabelonlWithScorelonAbovelonThrelonsholdWithParam(
          TwelonelontSafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon,
          HighSpammyTwelonelontContelonntScorelonSelonarchTopProdTwelonelontLabelonlDropRulelonThrelonsholdParam)
      )
    )
    with DoelonsLogVelonrdictDeloncidelonrelond {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon))
  ovelonrridelon delonf velonrdictLogDeloncidelonrKelony: DeloncidelonrKelony.Valuelon =
    DeloncidelonrKelony.elonnablelonSpammyTwelonelontRulelonVelonrdictLogging

}

objelonct HighSpammyTwelonelontContelonntScorelonTrelonndsTopTwelonelontLabelonlDropRulelon
    elonxtelonnds RulelonWithConstantAction(
      action = Drop(Unspeloncifielond),
      condition = And(
        Not(IsTwelonelontInTwelonelontLelonvelonlStcmHoldback),
        LoggelondOutOrVielonwelonrNotFollowingAuthor,
        IsTrelonndClickSourcelonSelonarchRelonsult,
        TwelonelontHasLabelonlWithScorelonAbovelonThrelonsholdWithParam(
          TwelonelontSafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon,
          HighSpammyTwelonelontContelonntScorelonTrelonndTopTwelonelontLabelonlDropRulelonThrelonsholdParam)
      )
    )
    with DoelonsLogVelonrdictDeloncidelonrelond {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon))
  ovelonrridelon delonf velonrdictLogDeloncidelonrKelony: DeloncidelonrKelony.Valuelon =
    DeloncidelonrKelony.elonnablelonSpammyTwelonelontRulelonVelonrdictLogging

}

objelonct HighSpammyTwelonelontContelonntScorelonTrelonndsLatelonstTwelonelontLabelonlDropRulelon
    elonxtelonnds RulelonWithConstantAction(
      action = Drop(Unspeloncifielond),
      condition = And(
        Not(IsTwelonelontInTwelonelontLelonvelonlStcmHoldback),
        LoggelondOutOrVielonwelonrNotFollowingAuthor,
        IsTrelonndClickSourcelonSelonarchRelonsult,
        TwelonelontHasLabelonlWithScorelonAbovelonThrelonsholdWithParam(
          TwelonelontSafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon,
          HighSpammyTwelonelontContelonntScorelonTrelonndLatelonstTwelonelontLabelonlDropRulelonThrelonsholdParam)
      )
    )
    with DoelonsLogVelonrdictDeloncidelonrelond {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon))
  ovelonrridelon delonf velonrdictLogDeloncidelonrKelony: DeloncidelonrKelony.Valuelon =
    DeloncidelonrKelony.elonnablelonSpammyTwelonelontRulelonVelonrdictLogging
}

objelonct GorelonAndViolelonncelonTopicHighReloncallTwelonelontLabelonlRulelon
    elonxtelonnds NonAuthorWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonTopicHighReloncall
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonGorelonAndViolelonncelonTopicHighReloncallTwelonelontLabelonlRulelon)
}

objelonct CopypastaSpamAllVielonwelonrsTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.CopypastaSpam
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonCopypastaSpamSelonarchDropRulelon)
}

objelonct CopypastaSpamAllVielonwelonrsSelonarchTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.CopypastaSpam
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonCopypastaSpamSelonarchDropRulelon)
}

objelonct CopypastaSpamNonFollowelonrSelonarchTwelonelontLabelonlRulelon
    elonxtelonnds NonFollowelonrWithTwelonelontLabelonlRulelon(
      Drop(Unspeloncifielond),
      TwelonelontSafelontyLabelonlTypelon.CopypastaSpam
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(elonnablelonCopypastaSpamSelonarchDropRulelon)
}

objelonct CopypastaSpamAbusivelonQualityTwelonelontLabelonlRulelon
    elonxtelonnds ConditionWithNotInnelonrCirclelonOfFrielonndsRulelon(
      ConvelonrsationSelonctionAbusivelonQuality,
      TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.CopypastaSpam)
    )
    with DoelonsLogVelonrdictDeloncidelonrelond {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonCopypastaSpamDownrankConvosAbusivelonQualityRulelon)
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.CopypastaSpam))
  ovelonrridelon delonf velonrdictLogDeloncidelonrKelony = DeloncidelonrKelony.elonnablelonDownlelonvelonlRulelonVelonrdictLogging
}

objelonct DynamicProductAdLimitelondelonngagelonmelonntTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Limitelondelonngagelonmelonnts(LimitelondelonngagelonmelonntRelonason.DynamicProductAd),
      TwelonelontSafelontyLabelonlTypelon.DynamicProductAd)

objelonct SkipTwelonelontDelontailLimitelondelonngagelonmelonntTwelonelontLabelonlRulelon
    elonxtelonnds AlwaysActRulelon(Limitelondelonngagelonmelonnts(LimitelondelonngagelonmelonntRelonason.SkipTwelonelontDelontail)) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    SkipTwelonelontDelontailLimitelondelonngagelonmelonntRulelonelonnablelondParam)
}

objelonct DynamicProductAdDropTwelonelontLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(Drop(Unspeloncifielond), TwelonelontSafelontyLabelonlTypelon.DynamicProductAd)

objelonct NsfwTelonxtTwelonelontLabelonlTopicsDropRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.Nsfw),
      And(
        NonAuthorVielonwelonr,
        Or(
          TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.elonxpelonrimelonntalSelonnsitivelonIllelongal2),
          TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.NsfwTelonxtHighPreloncision)
        )
      )
    )
    with DoelonsLogVelonrdict {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonNsfwTelonxtTopicsDropRulelonParam)
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.NsfwTelonxtHighPreloncision))
}


objelonct elonxpelonrimelonntalNudgelonLabelonlRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      TwelonelontVisibilityNudgelon(TwelonelontVisibilityNudgelonRelonason.elonxpelonrimelonntalNudgelonSafelontyLabelonlRelonason),
      TwelonelontSafelontyLabelonlTypelon.elonxpelonrimelonntalNudgelon) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonelonxpelonrimelonntalNudgelonelonnablelondParam)
}

objelonct NsfwTelonxtTwelonelontLabelonlAvoidRulelon
    elonxtelonnds RulelonWithConstantAction(
      Avoid(),
      Or(
        TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.elonxpelonrimelonntalSelonnsitivelonIllelongal2),
        TwelonelontHasLabelonl(TwelonelontSafelontyLabelonlTypelon.NsfwTelonxtHighPreloncision)
      )
    ) {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.NsfwTelonxtHighPreloncision))
}

objelonct DoNotAmplifyTwelonelontLabelonlAvoidRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(),
      TwelonelontSafelontyLabelonlTypelon.DoNotAmplify
    )

objelonct NsfaHighPreloncisionTwelonelontLabelonlAvoidRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(),
      TwelonelontSafelontyLabelonlTypelon.NsfaHighPreloncision
    ) {
  ovelonrridelon val fallbackActionBuildelonr: Option[ActionBuildelonr[_ <: Action]] = Somelon(
    nelonw ConstantActionBuildelonr(Avoid(Somelon(MightNotBelonSuitablelonForAds))))
}

objelonct NsfwHighPreloncisionTwelonelontLabelonlAvoidRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
    ) {
  ovelonrridelon val fallbackActionBuildelonr: Option[ActionBuildelonr[_ <: Action]] = Somelon(
    nelonw ConstantActionBuildelonr(Avoid(Somelon(MightNotBelonSuitablelonForAds))))
}

objelonct NsfwHighReloncallTwelonelontLabelonlAvoidRulelon
    elonxtelonnds TwelonelontHasLabelonlRulelon(
      Avoid(Somelon(AvoidRelonason.ContainsNsfwMelondia)),
      TwelonelontSafelontyLabelonlTypelon.NsfwHighReloncall
    ) {
  ovelonrridelon val fallbackActionBuildelonr: Option[ActionBuildelonr[_ <: Action]] = Somelon(
    nelonw ConstantActionBuildelonr(Avoid(Somelon(MightNotBelonSuitablelonForAds))))
}
