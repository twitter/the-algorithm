packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.rulelons.Condition.VielonwelonrHasAdultMelondiaSelonttingLelonvelonl
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrHasViolelonntMelondiaSelonttingLelonvelonl
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrHasOthelonrSelonnsitivelonMelondiaSelonttingLelonvelonl
import com.twittelonr.visibility.rulelons.Condition.LoggelondInVielonwelonr
import com.twittelonr.visibility.rulelons.Condition.LoggelondOutVielonwelonr
import com.twittelonr.visibility.rulelons.Condition.TwelonelontHasNsfwUselonrAuthor
import com.twittelonr.visibility.rulelons.Condition.TwelonelontHasNsfwAdminAuthor
import com.twittelonr.visibility.rulelons.Condition.And
import com.twittelonr.visibility.rulelons.Condition.Or
import com.twittelonr.visibility.rulelons.Condition.Not
import com.twittelonr.visibility.rulelons.Condition.NonAuthorVielonwelonr
import com.twittelonr.visibility.rulelons.Condition.TwelonelontHasMelondia
import com.twittelonr.visibility.rulelons.Relonason.Nsfw
import com.twittelonr.visibility.modelonls.TwelonelontSafelontyLabelonlTypelon
import com.twittelonr.contelonnthelonalth.selonnsitivelonmelondiaselonttings.thriftscala.SelonnsitivelonMelondiaSelonttingsLelonvelonl


abstract class AdultMelondiaTwelonelontLabelonlDropRulelon(twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      And(LoggelondInVielonwelonr, VielonwelonrHasAdultMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Drop)),
      twelonelontSafelontyLabelonlTypelon
    )

abstract class ViolelonntMelondiaTwelonelontLabelonlDropRulelon(twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Drop(Nsfw),
      And(LoggelondInVielonwelonr, VielonwelonrHasViolelonntMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Drop)),
      twelonelontSafelontyLabelonlTypelon
    )

abstract class OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlDropRulelon(condition: Condition)
    elonxtelonnds RulelonWithConstantAction(
      Drop(Nsfw),
      And(
        condition,
        And(
          TwelonelontHasMelondia,
          LoggelondInVielonwelonr,
          VielonwelonrHasOthelonrSelonnsitivelonMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Drop)))
    )

abstract class AdultMelondiaTwelonelontLabelonlIntelonrstitialRulelon(twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Intelonrstitial(Nsfw),
      Or(
        LoggelondOutVielonwelonr,
        VielonwelonrHasAdultMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Warn),
        Not(VielonwelonrHasAdultMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Allow))
      ),
      twelonelontSafelontyLabelonlTypelon
    )

abstract class ViolelonntMelondiaTwelonelontLabelonlIntelonrstitialRulelon(twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Intelonrstitial(Nsfw),
      Or(
        LoggelondOutVielonwelonr,
        VielonwelonrHasViolelonntMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Warn),
        Not(VielonwelonrHasViolelonntMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Allow))
      ),
      twelonelontSafelontyLabelonlTypelon
    )

abstract class OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlIntelonrstitialRulelon(condition: Condition)
    elonxtelonnds RulelonWithConstantAction(
      Intelonrstitial(Nsfw),
      And(
        condition,
        TwelonelontHasMelondia,
        Or(
          LoggelondOutVielonwelonr,
          VielonwelonrHasOthelonrSelonnsitivelonMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Warn)
        )
      )
    )

abstract class AdultMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Tombstonelon(elonpitaph.AdultMelondia),
      And(
        LoggelondInVielonwelonr,
        NonAuthorVielonwelonr,
        VielonwelonrHasAdultMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Drop)),
      twelonelontSafelontyLabelonlTypelon
    )

abstract class ViolelonntMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      Tombstonelon(elonpitaph.ViolelonntMelondia),
      And(
        LoggelondInVielonwelonr,
        NonAuthorVielonwelonr,
        VielonwelonrHasViolelonntMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Drop)),
      twelonelontSafelontyLabelonlTypelon
    )

abstract class OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(condition: Condition)
    elonxtelonnds RulelonWithConstantAction(
      Tombstonelon(elonpitaph.OthelonrSelonnsitivelonMelondia),
      And(
        condition,
        And(
          TwelonelontHasMelondia,
          LoggelondInVielonwelonr,
          NonAuthorVielonwelonr,
          VielonwelonrHasOthelonrSelonnsitivelonMelondiaSelonttingLelonvelonl(SelonnsitivelonMelondiaSelonttingsLelonvelonl.Drop))
      )
    )

caselon objelonct SelonnsitivelonMelondiaTwelonelontDropRulelons {


  objelonct AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
      )

  objelonct AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwCardImagelon
      )

  objelonct AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
      )

  objelonct AdultMelondiaNsfwVidelonoTwelonelontLabelonlDropRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwVidelono
      )

  objelonct AdultMelondiaNsfwHighReloncallTwelonelontLabelonlDropRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwHighReloncall
      )

  objelonct AdultMelondiaNsfwTelonxtTwelonelontLabelonlDropRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwTelonxt
      )

  objelonct ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropRulelon
      elonxtelonnds ViolelonntMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
      )

  objelonct ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropRulelon
      elonxtelonnds ViolelonntMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
      )

  objelonct OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropRulelon
      elonxtelonnds OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontHasNsfwUselonrAuthor
      )

  objelonct OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropRulelon
      elonxtelonnds OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlDropRulelon(
        TwelonelontHasNsfwAdminAuthor
      )
}

caselon objelonct SelonnsitivelonMelondiaTwelonelontIntelonrstitialRulelons {

  objelonct AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlIntelonrstitialRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
      )
      with DoelonsLogVelonrdict

  objelonct AdultMelondiaNsfwCardImagelonTwelonelontLabelonlIntelonrstitialRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwCardImagelon
      )

  objelonct AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlIntelonrstitialRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
      )

  objelonct AdultMelondiaNsfwVidelonoTwelonelontLabelonlIntelonrstitialRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwVidelono
      )

  objelonct AdultMelondiaNsfwHighReloncallTwelonelontLabelonlIntelonrstitialRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwHighReloncall
      )

  objelonct AdultMelondiaNsfwTelonxtTwelonelontLabelonlIntelonrstitialRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwTelonxt
      )

  objelonct ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionIntelonrstitialRulelon
      elonxtelonnds ViolelonntMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
      )
      with DoelonsLogVelonrdict

  objelonct ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsIntelonrstitialRulelon
      elonxtelonnds ViolelonntMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
      )

  objelonct OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagIntelonrstitialRulelon
      elonxtelonnds OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontHasNsfwUselonrAuthor
      )

  objelonct OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagIntelonrstitialRulelon
      elonxtelonnds OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlIntelonrstitialRulelon(
        TwelonelontHasNsfwAdminAuthor
      )

}

caselon objelonct SelonnsitivelonMelondiaTwelonelontDropSelonttingLelonvelonlTombstonelonRulelons {


  objelonct AdultMelondiaNsfwHighPreloncisionTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwHighPreloncision
      )

  objelonct AdultMelondiaNsfwCardImagelonTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwCardImagelon
      )

  objelonct AdultMelondiaNsfwRelonportelondHelonuristicsTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics
      )

  objelonct AdultMelondiaNsfwVidelonoTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwVidelono
      )

  objelonct AdultMelondiaNsfwHighReloncallTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwHighReloncall
      )

  objelonct AdultMelondiaNsfwTelonxtTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds AdultMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontSafelontyLabelonlTypelon.NsfwTelonxt
      )

  objelonct ViolelonntMelondiaGorelonAndViolelonncelonHighPreloncisionDropSelonttingLelonvelonTombstonelonRulelon
      elonxtelonnds ViolelonntMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision
      )

  objelonct ViolelonntMelondiaGorelonAndViolelonncelonRelonportelondHelonuristicsDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds ViolelonntMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontSafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics
      )

  objelonct OthelonrSelonnsitivelonMelondiaNsfwUselonrTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontHasNsfwUselonrAuthor
      )

  objelonct OthelonrSelonnsitivelonMelondiaNsfwAdminTwelonelontFlagDropSelonttingLelonvelonlTombstonelonRulelon
      elonxtelonnds OthelonrSelonnsitivelonMelondiaTwelonelontLabelonlDropSelonttingLelonvelonlTombstonelonRulelon(
        TwelonelontHasNsfwAdminAuthor
      )
}
