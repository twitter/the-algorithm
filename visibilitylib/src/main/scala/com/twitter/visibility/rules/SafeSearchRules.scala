packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonDownrankSpamRelonplySelonctioningRulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonNotGraduatelondSelonarchDropRulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.elonnablelonNsfwTelonxtSelonctioningRulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams.NotGraduatelondUselonrLabelonlRulelonHoldbackelonxpelonrimelonntParam
import com.twittelonr.visibility.modelonls.TwelonelontSafelontyLabelonlTypelon
import com.twittelonr.visibility.modelonls.UselonrLabelonlValuelon
import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.LoggelondOutOrVielonwelonrNotFollowingAuthor
import com.twittelonr.visibility.rulelons.Condition.LoggelondOutOrVielonwelonrOptInFiltelonring
import com.twittelonr.visibility.rulelons.Condition.NonAuthorVielonwelonr
import com.twittelonr.visibility.rulelons.Condition.Not
import com.twittelonr.visibility.rulelons.Condition.TwelonelontComposelondBelonforelon
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrDoelonsFollowAuthor
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrOptInFiltelonringOnSelonarch
import com.twittelonr.visibility.rulelons.Relonason.Nsfw
import com.twittelonr.visibility.rulelons.Relonason.Unspeloncifielond
import com.twittelonr.visibility.rulelons.RulelonActionSourcelonBuildelonr.TwelonelontSafelontyLabelonlSourcelonBuildelonr

caselon objelonct SafelonSelonarchTwelonelontRulelons {

  objelonct SafelonSelonarchAbusivelonTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Unspeloncifielond),
        TwelonelontSafelontyLabelonlTypelon.Abusivelon
      )
      with DoelonsLogVelonrdict {
    ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
      TwelonelontSafelontyLabelonlSourcelonBuildelonr(TwelonelontSafelontyLabelonlTypelon.Abusivelon))
  }

  objelonct SafelonSelonarchNsfwHighPreloncisionTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
      )

  objelonct SafelonSelonarchGorelonAndViolelonncelonHighPreloncisionTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
      )

  objelonct SafelonSelonarchNsfwRelonportelondHelonuristicsTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
      )

  objelonct SafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
      )

  objelonct SafelonSelonarchNsfwCardImagelonTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        TwelonelontSafelontyLabelonlTypelon.NsfwCardImagelon
      )

  objelonct SafelonSelonarchNsfwHighReloncallTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        TwelonelontSafelontyLabelonlTypelon.NsfwHighReloncall
      )

  objelonct SafelonSelonarchNsfwVidelonoTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        TwelonelontSafelontyLabelonlTypelon.NsfwVidelono
      )

  objelonct SafelonSelonarchNsfwTelonxtTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        TwelonelontSafelontyLabelonlTypelon.NsfwTelonxt
      ) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonNsfwTelonxtSelonctioningRulelonParam)
  }

  objelonct SafelonSelonarchNsfwTelonxtAuthorLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Nsfw),
        UselonrLabelonlValuelon.DownrankSpamRelonply
      ) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonNsfwTelonxtSelonctioningRulelonParam)
  }

  objelonct SafelonSelonarchGorelonAndViolelonncelonTwelonelontLabelonlRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Drop(Unspeloncifielond),
        And(
          NonAuthorVielonwelonr,
          TwelonelontComposelondBelonforelon(TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelon.DelonpreloncatelondAt),
          LoggelondOutOrVielonwelonrOptInFiltelonring
        ),
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelon
      )

  objelonct SafelonSelonarchUntrustelondUrlTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Unspeloncifielond),
        TwelonelontSafelontyLabelonlTypelon.UntrustelondUrl
      )

  objelonct SafelonSelonarchDownrankSpamRelonplyTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Unspeloncifielond),
        TwelonelontSafelontyLabelonlTypelon.DownrankSpamRelonply
      ) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
      Selonq(elonnablelonDownrankSpamRelonplySelonctioningRulelonParam)
  }

  objelonct SafelonSelonarchDownrankSpamRelonplyAuthorLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.DownrankSpamRelonply
      ) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
      Selonq(elonnablelonDownrankSpamRelonplySelonctioningRulelonParam)
  }

  objelonct SafelonSelonarchAutomationNonFollowelonrTwelonelontLabelonlRulelon
      elonxtelonnds NonFollowelonrVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Unspeloncifielond),
        TwelonelontSafelontyLabelonlTypelon.Automation
      )

  objelonct SafelonSelonarchDuplicatelonMelonntionNonFollowelonrTwelonelontLabelonlRulelon
      elonxtelonnds NonFollowelonrVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Unspeloncifielond),
        TwelonelontSafelontyLabelonlTypelon.DuplicatelonMelonntion
      )

  objelonct SafelonSelonarchBystandelonrAbusivelonTwelonelontLabelonlRulelon
      elonxtelonnds NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
        Drop(Unspeloncifielond),
        TwelonelontSafelontyLabelonlTypelon.BystandelonrAbusivelon
      )
}

caselon objelonct UnsafelonSelonarchTwelonelontRulelons {

  objelonct UnsafelonSelonarchNsfwHighPreloncisionIntelonrstitialAllUselonrsTwelonelontLabelonlRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Intelonrstitial(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
      )

  objelonct UnsafelonSelonarchGorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Intelonrstitial(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
      )

  objelonct UnsafelonSelonarchGorelonAndViolelonncelonHighPreloncisionAllUselonrsTwelonelontLabelonlDropRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
      )

  objelonct UnsafelonSelonarchNsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Intelonrstitial(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
      )

  objelonct UnsafelonSelonarchNsfwRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlDropRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
      )

  objelonct UnsafelonSelonarchNsfwHighPreloncisionAllUselonrsTwelonelontLabelonlDropRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
      )

  objelonct UnsafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Intelonrstitial(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
      )

  objelonct UnsafelonSelonarchGorelonAndViolelonncelonRelonportelondHelonuristicsAllUselonrsTwelonelontLabelonlDropRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
      )

  objelonct UnsafelonSelonarchNsfwCardImagelonAllUselonrsTwelonelontLabelonlRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Intelonrstitial(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.NsfwCardImagelon
      )

  objelonct UnsafelonSelonarchNsfwCardImagelonAllUselonrsTwelonelontLabelonlDropRulelon
      elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
        Drop(Nsfw),
        Not(VielonwelonrOptInFiltelonringOnSelonarch),
        TwelonelontSafelontyLabelonlTypelon.NsfwCardImagelon
      )

}

caselon objelonct SafelonSelonarchUselonrRulelons {

  objelonct SafelonSelonarchAbusivelonUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.Abusivelon
      )

  objelonct SafelonSelonarchAbusivelonHighReloncallUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.AbusivelonHighReloncall,
        LoggelondOutOrVielonwelonrNotFollowingAuthor
      )

  objelonct SafelonSelonarchHighReloncallUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Nsfw),
        UselonrLabelonlValuelon.NsfwHighReloncall,
        LoggelondOutOrVielonwelonrNotFollowingAuthor
      )

  objelonct SafelonSelonarchCompromiselondUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.Compromiselond
      )

  objelonct SafelonSelonarchDuplicatelonContelonntUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.DuplicatelonContelonnt
      )

  objelonct SafelonSelonarchLowQualityUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.LowQuality
      )

  objelonct SafelonSelonarchNsfwHighPreloncisionUselonrLabelonlRulelon
      elonxtelonnds ConditionWithUselonrLabelonlRulelon(
        Drop(Nsfw),
        LoggelondOutOrVielonwelonrOptInFiltelonring,
        UselonrLabelonlValuelon.NsfwHighPreloncision
      )

  objelonct SafelonSelonarchNsfwAvatarImagelonUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Nsfw),
        UselonrLabelonlValuelon.NsfwAvatarImagelon
      )

  objelonct SafelonSelonarchNsfwBannelonrImagelonUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Nsfw),
        UselonrLabelonlValuelon.NsfwBannelonrImagelon
      )

  objelonct SafelonSelonarchNsfwNelonarPelonrfelonctAuthorRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Nsfw),
        UselonrLabelonlValuelon.NsfwNelonarPelonrfelonct
      )

  objelonct SafelonSelonarchRelonadOnlyUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.RelonadOnly
      )

  objelonct SafelonSelonarchSpamHighReloncallUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.SpamHighReloncall
      )

  objelonct SafelonSelonarchSelonarchBlacklistUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.SelonarchBlacklist
      )

  objelonct SafelonSelonarchNsfwTelonxtUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.NsfwTelonxt
      ) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
      Selonq(elonnablelonNsfwTelonxtSelonctioningRulelonParam)
  }

  objelonct SafelonSelonarchDoNotAmplifyNonFollowelonrsUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.DoNotAmplify,
        prelonrelonquisitelonCondition = Not(VielonwelonrDoelonsFollowAuthor)
      )

  objelonct SafelonSelonarchNotGraduatelondNonFollowelonrsUselonrLabelonlRulelon
      elonxtelonnds VielonwelonrOptInFiltelonringOnSelonarchUselonrLabelonlRulelon(
        Drop(Unspeloncifielond),
        UselonrLabelonlValuelon.NotGraduatelond,
        prelonrelonquisitelonCondition = Not(VielonwelonrDoelonsFollowAuthor)
      ) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
      Selonq(elonnablelonNotGraduatelondSelonarchDropRulelonParam)

    ovelonrridelon delonf holdbacks: Selonq[RulelonParam[Boolelonan]] =
      Selonq(NotGraduatelondUselonrLabelonlRulelonHoldbackelonxpelonrimelonntParam)

  }
}
