packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.visibility.common.actions.LimitelondelonngagelonmelonntRelonason
import com.twittelonr.visibility.configapi.params.FSRulelonParams.AdAvoidancelonHighToxicityModelonlScorelonThrelonsholdParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.AdAvoidancelonRelonportelondTwelonelontModelonlScorelonThrelonsholdParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelonselonnablelondParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.CommunityTwelonelontDropProtelonctelondRulelonelonnablelondParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.CommunityTwelonelontDropRulelonelonnablelondParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.CommunityTwelonelontLimitelondActionsRulelonselonnablelondParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.CommunityTwelonelontMelonmbelonrRelonmovelondLimitelondActionsRulelonselonnablelondParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.CommunityTwelonelontNonMelonmbelonrLimitelondActionsRulelonelonnablelondParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.StalelonTwelonelontLimitelondActionsRulelonselonnablelondParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams.TrustelondFrielonndsTwelonelontLimitelondelonngagelonmelonntsRulelonelonnablelondParam
import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams
import com.twittelonr.visibility.configapi.params.RulelonParams._
import com.twittelonr.visibility.felonaturelons.{TwelonelontDelonlelontelonRelonason => FelonaturelonTwelonelontDelonlelontelonRelonason}
import com.twittelonr.visibility.modelonls.TwelonelontDelonlelontelonRelonason
import com.twittelonr.visibility.modelonls.TwelonelontSafelontyLabelonlTypelon
import com.twittelonr.visibility.rulelons.Condition.VielonwelonrIselonxclusivelonTwelonelontAuthor
import com.twittelonr.visibility.rulelons.Condition._
import com.twittelonr.visibility.rulelons.Relonason.CommunityTwelonelontAuthorRelonmovelond
import com.twittelonr.visibility.rulelons.Relonason.CommunityTwelonelontHiddelonn
import com.twittelonr.visibility.rulelons.Relonason.Nsfw
import com.twittelonr.visibility.rulelons.Relonason.StalelonTwelonelont
import com.twittelonr.visibility.rulelons.Relonason.Unspeloncifielond
import com.twittelonr.visibility.rulelons.RulelonActionSourcelonBuildelonr.TwelonelontSafelontyLabelonlSourcelonBuildelonr

abstract class TwelonelontHasLabelonlRulelon(action: Action, twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds RulelonWithConstantAction(action, TwelonelontHasLabelonl(twelonelontSafelontyLabelonlTypelon)) {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(twelonelontSafelontyLabelonlTypelon))
}

abstract class ConditionWithTwelonelontLabelonlRulelon(
  action: Action,
  condition: Condition,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds RulelonWithConstantAction(action, And(TwelonelontHasLabelonl(twelonelontSafelontyLabelonlTypelon), condition)) {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(twelonelontSafelontyLabelonlTypelon))
}

abstract class NonAuthorWithTwelonelontLabelonlRulelon(
  action: Action,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(action, NonAuthorVielonwelonr, twelonelontSafelontyLabelonlTypelon) {
  ovelonrridelon delonf actionSourcelonBuildelonr: Option[RulelonActionSourcelonBuildelonr] = Somelon(
    TwelonelontSafelontyLabelonlSourcelonBuildelonr(twelonelontSafelontyLabelonlTypelon))
}

abstract class NonFollowelonrWithTwelonelontLabelonlRulelon(
  action: Action,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      LoggelondOutOrVielonwelonrNotFollowingAuthor,
      twelonelontSafelontyLabelonlTypelon
    )

abstract class NonAuthorAndNonFollowelonrWithTwelonelontLabelonlRulelon(
  action: Action,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      And(NonAuthorVielonwelonr, LoggelondOutOrVielonwelonrNotFollowingAuthor),
      twelonelontSafelontyLabelonlTypelon
    )

abstract class NonFollowelonrWithUqfTwelonelontLabelonlRulelon(
  action: Action,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      Or(
        LoggelondOutVielonwelonr,
        And(
          NonAuthorVielonwelonr,
          Not(VielonwelonrDoelonsFollowAuthor),
          VielonwelonrHasUqfelonnablelond
        )
      ),
      twelonelontSafelontyLabelonlTypelon
    )

abstract class VielonwelonrWithUqfTwelonelontLabelonlRulelon(action: Action, labelonlValuelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(action, VielonwelonrHasUqfelonnablelond, labelonlValuelon)

caselon objelonct ConvelonrsationControlRulelons {

  abstract class ConvelonrsationControlBaselonRulelon(condition: Condition)
      elonxtelonnds RulelonWithConstantAction(
        Limitelondelonngagelonmelonnts(LimitelondelonngagelonmelonntRelonason.ConvelonrsationControl),
        condition) {
    ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(TwelonelontConvelonrsationControlelonnablelondParam)
  }

  objelonct LimitRelonplielonsCommunityConvelonrsationRulelon
      elonxtelonnds ConvelonrsationControlBaselonRulelon(
        And(
          TwelonelontIsCommunityConvelonrsation,
          Not(
            Or(
              LoggelondOutVielonwelonr,
              Relontwelonelont,
              VielonwelonrIsTwelonelontConvelonrsationRootAuthor,
              VielonwelonrIsInvitelondToTwelonelontConvelonrsation,
              ConvelonrsationRootAuthorDoelonsFollowVielonwelonr
            ))
        )
      )

  objelonct LimitRelonplielonsFollowelonrsConvelonrsationRulelon
      elonxtelonnds ConvelonrsationControlBaselonRulelon(
        And(
          TwelonelontIsFollowelonrsConvelonrsation,
          Not(
            Or(
              LoggelondOutVielonwelonr,
              Relontwelonelont,
              VielonwelonrIsTwelonelontConvelonrsationRootAuthor,
              VielonwelonrIsInvitelondToTwelonelontConvelonrsation,
              VielonwelonrDoelonsFollowConvelonrsationRootAuthor
            ))
        )
      )

  objelonct LimitRelonplielonsByInvitationConvelonrsationRulelon
      elonxtelonnds ConvelonrsationControlBaselonRulelon(
        And(
          TwelonelontIsByInvitationConvelonrsation,
          Not(
            Or(
              LoggelondOutVielonwelonr,
              Relontwelonelont,
              VielonwelonrIsTwelonelontConvelonrsationRootAuthor,
              VielonwelonrIsInvitelondToTwelonelontConvelonrsation
            ))
        )
      )

}

abstract class NonAuthorVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
  action: Action,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      And(NonAuthorVielonwelonr, LoggelondOutOrVielonwelonrOptInFiltelonring),
      twelonelontSafelontyLabelonlTypelon)

abstract class NonFollowelonrVielonwelonrOptInFiltelonringWithTwelonelontLabelonlRulelon(
  action: Action,
  twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon)
    elonxtelonnds ConditionWithTwelonelontLabelonlRulelon(
      action,
      And(LoggelondOutOrVielonwelonrNotFollowingAuthor, LoggelondOutOrVielonwelonrOptInFiltelonring),
      twelonelontSafelontyLabelonlTypelon
    )

objelonct TwelonelontNsfwUselonrDropRulelon elonxtelonnds RulelonWithConstantAction(Drop(Nsfw), TwelonelontHasNsfwUselonrAuthor)
objelonct TwelonelontNsfwAdminDropRulelon elonxtelonnds RulelonWithConstantAction(Drop(Nsfw), TwelonelontHasNsfwAdminAuthor)

objelonct NullcastelondTwelonelontRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(Nullcast, Not(Relontwelonelont), Not(IsQuotelondInnelonrTwelonelont), Not(TwelonelontIsCommunityTwelonelont)))

objelonct MutelondRelontwelonelontsRulelon
    elonxtelonnds RulelonWithConstantAction(Drop(Unspeloncifielond), And(Relontwelonelont, VielonwelonrMutelonsRelontwelonelontsFromAuthor))

abstract class FiltelonrCommunityTwelonelontsRulelon(ovelonrridelon val action: Action)
    elonxtelonnds RulelonWithConstantAction(action, TwelonelontIsCommunityTwelonelont) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(CommunityTwelonelontDropRulelonelonnablelondParam)
}

objelonct DropCommunityTwelonelontsRulelon elonxtelonnds FiltelonrCommunityTwelonelontsRulelon(Drop(CommunityTwelonelontHiddelonn))

objelonct TombstonelonCommunityTwelonelontsRulelon
    elonxtelonnds FiltelonrCommunityTwelonelontsRulelon(Tombstonelon(elonpitaph.Unavailablelon))

abstract class FiltelonrCommunityTwelonelontCommunityNotVisiblelonRulelon(ovelonrridelon val action: Action)
    elonxtelonnds RulelonWithConstantAction(
      action,
      And(
        NonAuthorVielonwelonr,
        TwelonelontIsCommunityTwelonelont,
        Not(CommunityTwelonelontCommunityVisiblelon),
      )) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] =
    Selonq(DropCommunityTwelonelontWithUndelonfinelondCommunityRulelonelonnablelondParam)
}

objelonct DropCommunityTwelonelontCommunityNotVisiblelonRulelon
    elonxtelonnds FiltelonrCommunityTwelonelontCommunityNotVisiblelonRulelon(Drop(CommunityTwelonelontHiddelonn))

objelonct TombstonelonCommunityTwelonelontCommunityNotVisiblelonRulelon
    elonxtelonnds FiltelonrCommunityTwelonelontCommunityNotVisiblelonRulelon(Tombstonelon(elonpitaph.Unavailablelon))

abstract class FiltelonrAllCommunityTwelonelontsRulelon(ovelonrridelon val action: Action)
    elonxtelonnds RulelonWithConstantAction(action, TwelonelontIsCommunityTwelonelont) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(CommunityTwelonelontselonnablelondParam)
}

objelonct DropAllCommunityTwelonelontsRulelon elonxtelonnds FiltelonrAllCommunityTwelonelontsRulelon(Drop(Unspeloncifielond))

objelonct TombstonelonAllCommunityTwelonelontsRulelon
    elonxtelonnds FiltelonrAllCommunityTwelonelontsRulelon(Tombstonelon(elonpitaph.Unavailablelon))

objelonct DropOutelonrCommunityTwelonelontsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(TwelonelontIsCommunityTwelonelont, Not(IsQuotelondInnelonrTwelonelont)))

objelonct DropAllHiddelonnCommunityTwelonelontsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(TwelonelontIsCommunityTwelonelont, CommunityTwelonelontIsHiddelonn))

abstract class FiltelonrHiddelonnCommunityTwelonelontsRulelon(ovelonrridelon val action: Action)
    elonxtelonnds RulelonWithConstantAction(
      action,
      And(
        NonAuthorVielonwelonr,
        TwelonelontIsCommunityTwelonelont,
        CommunityTwelonelontIsHiddelonn,
        Not(VielonwelonrIsCommunityModelonrator)
      ))

objelonct DropHiddelonnCommunityTwelonelontsRulelon
    elonxtelonnds FiltelonrHiddelonnCommunityTwelonelontsRulelon(Drop(CommunityTwelonelontHiddelonn))

objelonct TombstonelonHiddelonnCommunityTwelonelontsRulelon
    elonxtelonnds FiltelonrHiddelonnCommunityTwelonelontsRulelon(Tombstonelon(elonpitaph.CommunityTwelonelontHiddelonn))

objelonct DropAllAuthorRelonmovelondCommunityTwelonelontsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      And(TwelonelontIsCommunityTwelonelont, CommunityTwelonelontAuthorIsRelonmovelond))

abstract class FiltelonrAuthorRelonmovelondCommunityTwelonelontsRulelon(ovelonrridelon val action: Action)
    elonxtelonnds RulelonWithConstantAction(
      action,
      And(
        NonAuthorVielonwelonr,
        TwelonelontIsCommunityTwelonelont,
        CommunityTwelonelontAuthorIsRelonmovelond,
        Not(VielonwelonrIsCommunityModelonrator)
      ))

objelonct DropAuthorRelonmovelondCommunityTwelonelontsRulelon
    elonxtelonnds FiltelonrAuthorRelonmovelondCommunityTwelonelontsRulelon(Drop(CommunityTwelonelontAuthorRelonmovelond))

objelonct TombstonelonAuthorRelonmovelondCommunityTwelonelontsRulelon
    elonxtelonnds FiltelonrAuthorRelonmovelondCommunityTwelonelontsRulelon(Tombstonelon(elonpitaph.Unavailablelon))

abstract class FiltelonrProtelonctelondCommunityTwelonelontsRulelon(ovelonrridelon val action: Action)
    elonxtelonnds RulelonWithConstantAction(
      action,
      And(TwelonelontIsCommunityTwelonelont, ProtelonctelondAuthor, NonAuthorVielonwelonr)) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(CommunityTwelonelontDropProtelonctelondRulelonelonnablelondParam)
}

objelonct DropProtelonctelondCommunityTwelonelontsRulelon
    elonxtelonnds FiltelonrProtelonctelondCommunityTwelonelontsRulelon(Drop(CommunityTwelonelontHiddelonn))

objelonct TombstonelonProtelonctelondCommunityTwelonelontsRulelon
    elonxtelonnds FiltelonrProtelonctelondCommunityTwelonelontsRulelon(Tombstonelon(elonpitaph.Unavailablelon))

abstract class CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelon(
  relonason: LimitelondelonngagelonmelonntRelonason,
  condition: CommunityTwelonelontCommunityUnavailablelon,
) elonxtelonnds RulelonWithConstantAction(
      Limitelondelonngagelonmelonnts(relonason),
      And(
        Not(NonAuthorVielonwelonr),
        TwelonelontIsCommunityTwelonelont,
        condition,
      )
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelonselonnablelondParam)
}

objelonct CommunityTwelonelontCommunityNotFoundLimitelondActionsRulelon
    elonxtelonnds CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelon(
      LimitelondelonngagelonmelonntRelonason.CommunityTwelonelontCommunityNotFound,
      CommunityTwelonelontCommunityNotFound,
    )

objelonct CommunityTwelonelontCommunityDelonlelontelondLimitelondActionsRulelon
    elonxtelonnds CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelon(
      LimitelondelonngagelonmelonntRelonason.CommunityTwelonelontCommunityDelonlelontelond,
      CommunityTwelonelontCommunityDelonlelontelond,
    )

objelonct CommunityTwelonelontCommunitySuspelonndelondLimitelondActionsRulelon
    elonxtelonnds CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelon(
      LimitelondelonngagelonmelonntRelonason.CommunityTwelonelontCommunitySuspelonndelond,
      CommunityTwelonelontCommunitySuspelonndelond,
    )

abstract class CommunityTwelonelontModelonratelondLimitelondActionsRulelon(
  relonason: LimitelondelonngagelonmelonntRelonason,
  condition: CommunityTwelonelontIsModelonratelond,
  elonnablelondParam: RulelonParam[Boolelonan],
) elonxtelonnds RulelonWithConstantAction(
      Limitelondelonngagelonmelonnts(relonason),
      And(
        TwelonelontIsCommunityTwelonelont,
        condition,
        Or(
          Not(NonAuthorVielonwelonr),
          VielonwelonrIsCommunityModelonrator,
        )
      )) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelondParam)
}

objelonct CommunityTwelonelontMelonmbelonrRelonmovelondLimitelondActionsRulelon
    elonxtelonnds CommunityTwelonelontModelonratelondLimitelondActionsRulelon(
      LimitelondelonngagelonmelonntRelonason.CommunityTwelonelontMelonmbelonrRelonmovelond,
      CommunityTwelonelontAuthorIsRelonmovelond,
      CommunityTwelonelontMelonmbelonrRelonmovelondLimitelondActionsRulelonselonnablelondParam,
    )

objelonct CommunityTwelonelontHiddelonnLimitelondActionsRulelon
    elonxtelonnds CommunityTwelonelontModelonratelondLimitelondActionsRulelon(
      LimitelondelonngagelonmelonntRelonason.CommunityTwelonelontHiddelonn,
      CommunityTwelonelontIsHiddelonn,
      CommunityTwelonelontLimitelondActionsRulelonselonnablelondParam,
    )

abstract class CommunityTwelonelontLimitelondActionsRulelon(
  relonason: LimitelondelonngagelonmelonntRelonason,
  condition: Condition,
) elonxtelonnds RulelonWithConstantAction(
      Limitelondelonngagelonmelonnts(relonason),
      And(
        TwelonelontIsCommunityTwelonelont,
        condition
      )) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(CommunityTwelonelontLimitelondActionsRulelonselonnablelondParam)
}

objelonct CommunityTwelonelontMelonmbelonrLimitelondActionsRulelon
    elonxtelonnds CommunityTwelonelontLimitelondActionsRulelon(
      LimitelondelonngagelonmelonntRelonason.CommunityTwelonelontMelonmbelonr,
      VielonwelonrIsCommunityMelonmbelonr,
    )

objelonct CommunityTwelonelontNonMelonmbelonrLimitelondActionsRulelon
    elonxtelonnds CommunityTwelonelontLimitelondActionsRulelon(
      LimitelondelonngagelonmelonntRelonason.CommunityTwelonelontNonMelonmbelonr,
      Not(VielonwelonrIsCommunityMelonmbelonr),
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    CommunityTwelonelontNonMelonmbelonrLimitelondActionsRulelonelonnablelondParam)
}

objelonct RelonportelondTwelonelontIntelonrstitialRulelon
    elonxtelonnds RulelonWithConstantAction(
      Intelonrstitial(Relonason.VielonwelonrRelonportelondTwelonelont),
      And(
        NonAuthorVielonwelonr,
        Not(Relontwelonelont),
        VielonwelonrRelonportsTwelonelont
      )) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonRelonportelondTwelonelontIntelonrstitialRulelon)
}

objelonct RelonportelondTwelonelontIntelonrstitialSelonarchRulelon
    elonxtelonnds RulelonWithConstantAction(
      Intelonrstitial(Relonason.VielonwelonrRelonportelondTwelonelont),
      And(
        NonAuthorVielonwelonr,
        Not(Relontwelonelont),
        VielonwelonrRelonportsTwelonelont
      )) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonRelonportelondTwelonelontIntelonrstitialSelonarchRulelon)
}

abstract class FiltelonrelonxclusivelonTwelonelontContelonntRulelon(
  action: Action,
  additionalCondition: Condition = Condition.Truelon)
    elonxtelonnds RulelonWithConstantAction(
      action,
      And(
        additionalCondition,
        TwelonelontIselonxclusivelonContelonnt,
        Or(
          LoggelondOutVielonwelonr,
          Not(
            Or(
              VielonwelonrIselonxclusivelonTwelonelontAuthor,
              VielonwelonrSupelonrFollowselonxclusivelonTwelonelontAuthor,
              And(
                Not(NonAuthorVielonwelonr),
                Not(Relontwelonelont)
              )
            )
          ),
        ),
      )
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDropelonxclusivelonTwelonelontContelonntRulelon)
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonDropelonxclusivelonTwelonelontContelonntRulelonFailCloselond)
}

objelonct DropelonxclusivelonTwelonelontContelonntRulelon
    elonxtelonnds FiltelonrelonxclusivelonTwelonelontContelonntRulelon(Drop(Relonason.elonxclusivelonTwelonelont))

objelonct TombstonelonelonxclusivelonTwelonelontContelonntRulelon
    elonxtelonnds FiltelonrelonxclusivelonTwelonelontContelonntRulelon(Tombstonelon(elonpitaph.SupelonrFollowsContelonnt))

objelonct TombstonelonelonxclusivelonQuotelondTwelonelontContelonntRulelon
    elonxtelonnds FiltelonrelonxclusivelonTwelonelontContelonntRulelon(
      Tombstonelon(elonpitaph.SupelonrFollowsContelonnt),
      IsQuotelondInnelonrTwelonelont
    )

objelonct DropAllelonxclusivelonTwelonelontsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.elonxclusivelonTwelonelont),
      TwelonelontIselonxclusivelonContelonnt
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDropAllelonxclusivelonTwelonelontsRulelonParam)
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonDropAllelonxclusivelonTwelonelontsRulelonFailCloselondParam)
}

objelonct DropTwelonelontsWithGelonoRelonstrictelondMelondiaRulelon
    elonxtelonnds RulelonWithConstantAction(Drop(Unspeloncifielond), MelondiaRelonstrictelondInVielonwelonrCountry) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonDropTwelonelontsWithGelonoRelonstrictelondMelondiaRulelonParam)
}

objelonct TrustelondFrielonndsTwelonelontLimitelondelonngagelonmelonntsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Limitelondelonngagelonmelonnts(LimitelondelonngagelonmelonntRelonason.TrustelondFrielonndsTwelonelont),
      TwelonelontIsTrustelondFrielonndsContelonnt
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    TrustelondFrielonndsTwelonelontLimitelondelonngagelonmelonntsRulelonelonnablelondParam
  )
}

objelonct DropAllTrustelondFrielonndsTwelonelontsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Relonason.TrustelondFrielonndsTwelonelont),
      TwelonelontIsTrustelondFrielonndsContelonnt
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDropAllTrustelondFrielonndsTwelonelontsRulelonParam)
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(RulelonParams.Truelon)
}

objelonct DropAllCollabInvitationTwelonelontsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(Unspeloncifielond),
      TwelonelontIsCollabInvitationContelonnt
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDropAllCollabInvitationTwelonelontsRulelonParam)
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(RulelonParams.Truelon)
}

abstract class FiltelonrTrustelondFrielonndsTwelonelontContelonntRulelon(action: Action)
    elonxtelonnds OnlyWhelonnNotAuthorVielonwelonrRulelon(
      action,
      And(
        TwelonelontIsTrustelondFrielonndsContelonnt,
        Not(
          Or(
            VielonwelonrIsTrustelondFrielonndsTwelonelontAuthor,
            VielonwelonrIsTrustelondFrielonnd
          )
        )
      )
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDropTrustelondFrielonndsTwelonelontContelonntRulelonParam)
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(RulelonParams.Truelon)
}

objelonct DropTrustelondFrielonndsTwelonelontContelonntRulelon
    elonxtelonnds FiltelonrTrustelondFrielonndsTwelonelontContelonntRulelon(Drop(Relonason.TrustelondFrielonndsTwelonelont))

objelonct TombstonelonTrustelondFrielonndsTwelonelontContelonntRulelon
    elonxtelonnds FiltelonrTrustelondFrielonndsTwelonelontContelonntRulelon(Tombstonelon(elonpitaph.Unavailablelon))

objelonct TwelonelontNsfwUselonrAdminAvoidRulelon
    elonxtelonnds RulelonWithConstantAction(
      Avoid(),
      Or(
        TwelonelontHasNsfwUselonrAuthor,
        TwelonelontHasNsfwAdminAuthor,
        NsfwUselonrAuthor,
        NsfwAdminAuthor
      )
    )

objelonct AvoidHighToxicityModelonlScorelonRulelon
    elonxtelonnds RulelonWithConstantAction(
      Avoid(),
      TwelonelontHasLabelonlWithScorelonAbovelonThrelonsholdWithParam(
        TwelonelontSafelontyLabelonlTypelon.HighToxicityScorelon,
        AdAvoidancelonHighToxicityModelonlScorelonThrelonsholdParam)
    )

objelonct AvoidRelonportelondTwelonelontModelonlScorelonRulelon
    elonxtelonnds RulelonWithConstantAction(
      Avoid(),
      TwelonelontHasLabelonlWithScorelonAbovelonThrelonsholdWithParam(
        TwelonelontSafelontyLabelonlTypelon.HighPRelonportelondTwelonelontScorelon,
        AdAvoidancelonRelonportelondTwelonelontModelonlScorelonThrelonsholdParam)
    )

objelonct TombstonelonDelonlelontelondOutelonrTwelonelontRulelon
    elonxtelonnds RulelonWithConstantAction(
      Tombstonelon(elonpitaph.Delonlelontelond),
      And(
        elonquals(FelonaturelonTwelonelontDelonlelontelonRelonason, TwelonelontDelonlelontelonRelonason.Delonlelontelond),
        Not(IsQuotelondInnelonrTwelonelont)
      )
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDelonlelontelonStatelonTwelonelontRulelonsParam)
}

objelonct TombstonelonDelonlelontelondTwelonelontRulelon
    elonxtelonnds RulelonWithConstantAction(
      Tombstonelon(elonpitaph.Delonlelontelond),
      And(
        elonquals(FelonaturelonTwelonelontDelonlelontelonRelonason, TwelonelontDelonlelontelonRelonason.Delonlelontelond),
      )
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDelonlelontelonStatelonTwelonelontRulelonsParam)
}

objelonct TombstonelonDelonlelontelondQuotelondTwelonelontRulelon
    elonxtelonnds RulelonWithConstantAction(
      Tombstonelon(elonpitaph.Delonlelontelond),
      And(
        elonquals(FelonaturelonTwelonelontDelonlelontelonRelonason, TwelonelontDelonlelontelonRelonason.Delonlelontelond),
        IsQuotelondInnelonrTwelonelont
      )
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDelonlelontelonStatelonTwelonelontRulelonsParam)
}

objelonct TombstonelonBouncelonDelonlelontelondTwelonelontRulelon
    elonxtelonnds RulelonWithConstantAction(
      Tombstonelon(elonpitaph.BouncelonDelonlelontelond),
      elonquals(FelonaturelonTwelonelontDelonlelontelonRelonason, TwelonelontDelonlelontelonRelonason.BouncelonDelonlelontelond),
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDelonlelontelonStatelonTwelonelontRulelonsParam)
}

objelonct TombstonelonBouncelonDelonlelontelondOutelonrTwelonelontRulelon
    elonxtelonnds RulelonWithConstantAction(
      Tombstonelon(elonpitaph.BouncelonDelonlelontelond),
      And(
        elonquals(FelonaturelonTwelonelontDelonlelontelonRelonason, TwelonelontDelonlelontelonRelonason.BouncelonDelonlelontelond),
        Not(IsQuotelondInnelonrTwelonelont)
      )
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDelonlelontelonStatelonTwelonelontRulelonsParam)
}

objelonct TombstonelonBouncelonDelonlelontelondQuotelondTwelonelontRulelon
    elonxtelonnds RulelonWithConstantAction(
      Tombstonelon(elonpitaph.BouncelonDelonlelontelond),
      And(
        elonquals(FelonaturelonTwelonelontDelonlelontelonRelonason, TwelonelontDelonlelontelonRelonason.BouncelonDelonlelontelond),
        IsQuotelondInnelonrTwelonelont
      )
    ) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonDelonlelontelonStatelonTwelonelontRulelonsParam)
}


objelonct DropStalelonTwelonelontsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Drop(StalelonTwelonelont),
      And(TwelonelontIsStalelonTwelonelont, Not(IsQuotelondInnelonrTwelonelont), Not(Relontwelonelont), Not(IsSourcelonTwelonelont))) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(elonnablelonStalelonTwelonelontDropRulelonParam)
  ovelonrridelon delonf elonnablelonFailCloselond: Selonq[RulelonParam[Boolelonan]] = Selonq(
    elonnablelonStalelonTwelonelontDropRulelonFailCloselondParam)
}

objelonct StalelonTwelonelontLimitelondActionsRulelon
    elonxtelonnds RulelonWithConstantAction(
      Limitelondelonngagelonmelonnts(LimitelondelonngagelonmelonntRelonason.StalelonTwelonelont),
      TwelonelontIsStalelonTwelonelont) {
  ovelonrridelon delonf elonnablelond: Selonq[RulelonParam[Boolelonan]] = Selonq(StalelonTwelonelontLimitelondActionsRulelonselonnablelondParam)
}
