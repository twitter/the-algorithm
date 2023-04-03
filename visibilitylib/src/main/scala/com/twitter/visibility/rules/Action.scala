packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.datatools.elonntityselonrvicelon.elonntitielons.thriftscala.FlelonelontIntelonrstitial
import com.twittelonr.scroogelon.ThriftStruct
import com.twittelonr.visibility.common.actions.LocalizelondMelonssagelon
import com.twittelonr.visibility.common.actions._
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.AppelonalablelonRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.AvoidRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.CompliancelonTwelonelontNoticelonelonvelonntTypelonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.DownrankHomelonTimelonlinelonRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.DropRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.IntelonrstitialRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.LimitelondActionsPolicyConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.LimitelondelonngagelonmelonntRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.LocalizelondMelonssagelonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.SoftIntelonrvelonntionDisplayTypelonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.SoftIntelonrvelonntionRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.TombstonelonRelonasonConvelonrtelonr
import com.twittelonr.visibility.felonaturelons.Felonaturelon
import com.twittelonr.visibility.logging.thriftscala.HelonalthActionTypelon
import com.twittelonr.visibility.modelonls.ViolationLelonvelonl
import com.twittelonr.visibility.strato.thriftscala.NudgelonActionTypelon.elonnumUnknownNudgelonActionTypelon
import com.twittelonr.visibility.strato.thriftscala.{Nudgelon => StratoNudgelon}
import com.twittelonr.visibility.strato.thriftscala.{NudgelonAction => StratoNudgelonAction}
import com.twittelonr.visibility.strato.thriftscala.{NudgelonActionTypelon => StratoNudgelonActionTypelon}
import com.twittelonr.visibility.strato.thriftscala.{NudgelonActionPayload => StratoNudgelonActionPayload}
import com.twittelonr.visibility.thriftscala
import com.twittelonr.visibility.util.NamingUtils

selonalelond trait Action {
  lazy val namelon: String = NamingUtils.gelontFrielonndlyNamelon(this)
  lazy val fullNamelon: String = NamingUtils.gelontFrielonndlyNamelon(this)

  val selonvelonrity: Int
  delonf toActionThrift(): thriftscala.Action

  delonf isComposablelon: Boolelonan = falselon

  delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon]
}

selonalelond trait Relonason {
  lazy val namelon: String = NamingUtils.gelontFrielonndlyNamelon(this)
}

selonalelond abstract class ActionWithRelonason(relonason: Relonason) elonxtelonnds Action {
  ovelonrridelon lazy val fullNamelon: String = s"${this.namelon}/${relonason.namelon}"
}

objelonct Relonason {

  caselon objelonct Bouncelon elonxtelonnds Relonason

  caselon objelonct VielonwelonrRelonportelondAuthor elonxtelonnds Relonason
  caselon objelonct VielonwelonrRelonportelondTwelonelont elonxtelonnds Relonason

  caselon objelonct DelonactivatelondAuthor elonxtelonnds Relonason
  caselon objelonct OffboardelondAuthor elonxtelonnds Relonason
  caselon objelonct elonraselondAuthor elonxtelonnds Relonason
  caselon objelonct ProtelonctelondAuthor elonxtelonnds Relonason
  caselon objelonct SuspelonndelondAuthor elonxtelonnds Relonason
  caselon objelonct VielonwelonrIsUnmelonntionelond elonxtelonnds Relonason

  caselon objelonct Nsfw elonxtelonnds Relonason
  caselon objelonct NsfwMelondia elonxtelonnds Relonason
  caselon objelonct NsfwVielonwelonrIsUndelonragelon elonxtelonnds Relonason
  caselon objelonct NsfwVielonwelonrHasNoStatelondAgelon elonxtelonnds Relonason
  caselon objelonct NsfwLoggelondOut elonxtelonnds Relonason
  caselon objelonct PossiblyUndelonsirablelon elonxtelonnds Relonason

  caselon objelonct Abuselonelonpisodic elonxtelonnds Relonason
  caselon objelonct AbuselonelonpisodicelonncouragelonSelonlfHarm elonxtelonnds Relonason
  caselon objelonct AbuselonelonpisodicHatelonfulConduct elonxtelonnds Relonason
  caselon objelonct AbuselonGlorificationOfViolelonncelon elonxtelonnds Relonason
  caselon objelonct AbuselonGratuitousGorelon elonxtelonnds Relonason
  caselon objelonct AbuselonMobHarassmelonnt elonxtelonnds Relonason
  caselon objelonct AbuselonMomelonntOfDelonathOrDeloncelonaselondUselonr elonxtelonnds Relonason
  caselon objelonct AbuselonPrivatelonInformation elonxtelonnds Relonason
  caselon objelonct AbuselonRightToPrivacy elonxtelonnds Relonason
  caselon objelonct AbuselonThrelonatToelonxposelon elonxtelonnds Relonason
  caselon objelonct AbuselonViolelonntSelonxualConduct elonxtelonnds Relonason
  caselon objelonct AbuselonViolelonntThrelonatHatelonfulConduct elonxtelonnds Relonason
  caselon objelonct AbuselonViolelonntThrelonatOrBounty elonxtelonnds Relonason

  caselon objelonct MutelondKelonyword elonxtelonnds Relonason
  caselon objelonct Unspeloncifielond elonxtelonnds Relonason

  caselon objelonct UntrustelondUrl elonxtelonnds Relonason

  caselon objelonct SpamRelonplyDownRank elonxtelonnds Relonason

  caselon objelonct LowQualityTwelonelont elonxtelonnds Relonason

  caselon objelonct LowQualityMelonntion elonxtelonnds Relonason

  caselon objelonct SpamHighReloncallTwelonelont elonxtelonnds Relonason

  caselon objelonct TwelonelontLabelonlDuplicatelonContelonnt elonxtelonnds Relonason

  caselon objelonct TwelonelontLabelonlDuplicatelonMelonntion elonxtelonnds Relonason

  caselon objelonct PdnaTwelonelont elonxtelonnds Relonason

  caselon objelonct TwelonelontLabelonlelondSpam elonxtelonnds Relonason

  caselon objelonct OnelonOff elonxtelonnds Relonason
  caselon objelonct VotingMisinformation elonxtelonnds Relonason
  caselon objelonct HackelondMatelonrials elonxtelonnds Relonason
  caselon objelonct Scams elonxtelonnds Relonason
  caselon objelonct PlatformManipulation elonxtelonnds Relonason

  caselon objelonct FirstPagelonSelonarchRelonsult elonxtelonnds Relonason

  caselon objelonct MisinfoCivic elonxtelonnds Relonason
  caselon objelonct MisinfoCrisis elonxtelonnds Relonason
  caselon objelonct MisinfoGelonnelonric elonxtelonnds Relonason
  caselon objelonct MisinfoMelondical elonxtelonnds Relonason
  caselon objelonct Mislelonading elonxtelonnds Relonason
  caselon objelonct elonxclusivelonTwelonelont elonxtelonnds Relonason
  caselon objelonct CommunityNotAMelonmbelonr elonxtelonnds Relonason
  caselon objelonct CommunityTwelonelontHiddelonn elonxtelonnds Relonason
  caselon objelonct CommunityTwelonelontCommunityIsSuspelonndelond elonxtelonnds Relonason
  caselon objelonct CommunityTwelonelontAuthorRelonmovelond elonxtelonnds Relonason
  caselon objelonct IntelonrnalPromotelondContelonnt elonxtelonnds Relonason
  caselon objelonct TrustelondFrielonndsTwelonelont elonxtelonnds Relonason
  caselon objelonct Toxicity elonxtelonnds Relonason
  caselon objelonct StalelonTwelonelont elonxtelonnds Relonason
  caselon objelonct DmcaWithhelonld elonxtelonnds Relonason
  caselon objelonct LelongalDelonmandsWithhelonld elonxtelonnds Relonason
  caselon objelonct LocalLawsWithhelonld elonxtelonnds Relonason
  caselon objelonct HatelonfulConduct elonxtelonnds Relonason
  caselon objelonct AbusivelonBelonhavior elonxtelonnds Relonason

  caselon objelonct NotSupportelondOnDelonvicelon elonxtelonnds Relonason

  caselon objelonct IpiDelonvelonlopmelonntOnly elonxtelonnds Relonason
  caselon objelonct IntelonrstitialDelonvelonlopmelonntOnly elonxtelonnds Relonason

  caselon class FosnrRelonason(appelonalablelonRelonason: AppelonalablelonRelonason) elonxtelonnds Relonason

  delonf toDropRelonason(relonason: Relonason): Option[DropRelonason] =
    relonason match {
      caselon AuthorBlocksVielonwelonr => Somelon(DropRelonason.AuthorBlocksVielonwelonr)
      caselon CommunityTwelonelontHiddelonn => Somelon(DropRelonason.CommunityTwelonelontHiddelonn)
      caselon CommunityTwelonelontCommunityIsSuspelonndelond => Somelon(DropRelonason.CommunityTwelonelontCommunityIsSuspelonndelond)
      caselon DmcaWithhelonld => Somelon(DropRelonason.DmcaWithhelonld)
      caselon elonxclusivelonTwelonelont => Somelon(DropRelonason.elonxclusivelonTwelonelont)
      caselon IntelonrnalPromotelondContelonnt => Somelon(DropRelonason.IntelonrnalPromotelondContelonnt)
      caselon LelongalDelonmandsWithhelonld => Somelon(DropRelonason.LelongalDelonmandsWithhelonld)
      caselon LocalLawsWithhelonld => Somelon(DropRelonason.LocalLawsWithhelonld)
      caselon Nsfw => Somelon(DropRelonason.NsfwAuthor)
      caselon NsfwLoggelondOut => Somelon(DropRelonason.NsfwLoggelondOut)
      caselon NsfwVielonwelonrHasNoStatelondAgelon => Somelon(DropRelonason.NsfwVielonwelonrHasNoStatelondAgelon)
      caselon NsfwVielonwelonrIsUndelonragelon => Somelon(DropRelonason.NsfwVielonwelonrIsUndelonragelon)
      caselon ProtelonctelondAuthor => Somelon(DropRelonason.ProtelonctelondAuthor)
      caselon StalelonTwelonelont => Somelon(DropRelonason.StalelonTwelonelont)
      caselon SuspelonndelondAuthor => Somelon(DropRelonason.SuspelonndelondAuthor)
      caselon Unspeloncifielond => Somelon(DropRelonason.Unspeloncifielond)
      caselon VielonwelonrBlocksAuthor => Somelon(DropRelonason.VielonwelonrBlocksAuthor)
      caselon VielonwelonrHardMutelondAuthor => Somelon(DropRelonason.VielonwelonrMutelonsAuthor)
      caselon VielonwelonrMutelonsAuthor => Somelon(DropRelonason.VielonwelonrMutelonsAuthor)
      caselon TrustelondFrielonndsTwelonelont => Somelon(DropRelonason.TrustelondFrielonndsTwelonelont)
      caselon _ => Somelon(DropRelonason.Unspeloncifielond)
    }

  delonf fromDropRelonason(dropRelonason: DropRelonason): Relonason =
    dropRelonason match {
      caselon DropRelonason.AuthorBlocksVielonwelonr => AuthorBlocksVielonwelonr
      caselon DropRelonason.CommunityTwelonelontHiddelonn => CommunityTwelonelontHiddelonn
      caselon DropRelonason.CommunityTwelonelontCommunityIsSuspelonndelond => CommunityTwelonelontCommunityIsSuspelonndelond
      caselon DropRelonason.DmcaWithhelonld => DmcaWithhelonld
      caselon DropRelonason.elonxclusivelonTwelonelont => elonxclusivelonTwelonelont
      caselon DropRelonason.IntelonrnalPromotelondContelonnt => IntelonrnalPromotelondContelonnt
      caselon DropRelonason.LelongalDelonmandsWithhelonld => LelongalDelonmandsWithhelonld
      caselon DropRelonason.LocalLawsWithhelonld => LocalLawsWithhelonld
      caselon DropRelonason.NsfwAuthor => Nsfw
      caselon DropRelonason.NsfwLoggelondOut => NsfwLoggelondOut
      caselon DropRelonason.NsfwVielonwelonrHasNoStatelondAgelon => NsfwVielonwelonrHasNoStatelondAgelon
      caselon DropRelonason.NsfwVielonwelonrIsUndelonragelon => NsfwVielonwelonrIsUndelonragelon
      caselon DropRelonason.ProtelonctelondAuthor => ProtelonctelondAuthor
      caselon DropRelonason.StalelonTwelonelont => StalelonTwelonelont
      caselon DropRelonason.SuspelonndelondAuthor => SuspelonndelondAuthor
      caselon DropRelonason.VielonwelonrBlocksAuthor => VielonwelonrBlocksAuthor
      caselon DropRelonason.VielonwelonrMutelonsAuthor => VielonwelonrMutelonsAuthor
      caselon DropRelonason.TrustelondFrielonndsTwelonelont => TrustelondFrielonndsTwelonelont
      caselon DropRelonason.Unspeloncifielond => Unspeloncifielond
    }

  delonf toAppelonalablelonRelonason(relonason: Relonason, violationLelonvelonl: ViolationLelonvelonl): Option[AppelonalablelonRelonason] =
    relonason match {
      caselon HatelonfulConduct => Somelon(AppelonalablelonRelonason.HatelonfulConduct(violationLelonvelonl.lelonvelonl))
      caselon AbusivelonBelonhavior => Somelon(AppelonalablelonRelonason.AbusivelonBelonhavior(violationLelonvelonl.lelonvelonl))
      caselon _ => Somelon(AppelonalablelonRelonason.Unspeloncifielond(violationLelonvelonl.lelonvelonl))
    }

  delonf fromAppelonalablelonRelonason(appelonalablelonRelonason: AppelonalablelonRelonason): Relonason =
    appelonalablelonRelonason match {
      caselon AppelonalablelonRelonason.HatelonfulConduct(lelonvelonl) => HatelonfulConduct
      caselon AppelonalablelonRelonason.AbusivelonBelonhavior(lelonvelonl) => AbusivelonBelonhavior
      caselon AppelonalablelonRelonason.Unspeloncifielond(lelonvelonl) => Unspeloncifielond
    }

  delonf toSoftIntelonrvelonntionRelonason(appelonalablelonRelonason: AppelonalablelonRelonason): SoftIntelonrvelonntionRelonason =
    appelonalablelonRelonason match {
      caselon AppelonalablelonRelonason.HatelonfulConduct(lelonvelonl) =>
        SoftIntelonrvelonntionRelonason.FosnrRelonason(appelonalablelonRelonason)
      caselon AppelonalablelonRelonason.AbusivelonBelonhavior(lelonvelonl) =>
        SoftIntelonrvelonntionRelonason.FosnrRelonason(appelonalablelonRelonason)
      caselon AppelonalablelonRelonason.Unspeloncifielond(lelonvelonl) =>
        SoftIntelonrvelonntionRelonason.FosnrRelonason(appelonalablelonRelonason)
    }

  delonf toLimitelondelonngagelonmelonntRelonason(appelonalablelonRelonason: AppelonalablelonRelonason): LimitelondelonngagelonmelonntRelonason =
    appelonalablelonRelonason match {
      caselon AppelonalablelonRelonason.HatelonfulConduct(lelonvelonl) =>
        LimitelondelonngagelonmelonntRelonason.FosnrRelonason(appelonalablelonRelonason)
      caselon AppelonalablelonRelonason.AbusivelonBelonhavior(lelonvelonl) =>
        LimitelondelonngagelonmelonntRelonason.FosnrRelonason(appelonalablelonRelonason)
      caselon AppelonalablelonRelonason.Unspeloncifielond(lelonvelonl) =>
        LimitelondelonngagelonmelonntRelonason.FosnrRelonason(appelonalablelonRelonason)
    }

  val NSFW_MelonDIA: Selont[Relonason] = Selont(Nsfw, NsfwMelondia)

  delonf toIntelonrstitialRelonason(relonason: Relonason): Option[IntelonrstitialRelonason] =
    relonason match {
      caselon r if NSFW_MelonDIA.contains(r) => Somelon(IntelonrstitialRelonason.ContainsNsfwMelondia)
      caselon PossiblyUndelonsirablelon => Somelon(IntelonrstitialRelonason.PossiblyUndelonsirablelon)
      caselon MutelondKelonyword => Somelon(IntelonrstitialRelonason.MatchelonsMutelondKelonyword(""))
      caselon VielonwelonrRelonportelondAuthor => Somelon(IntelonrstitialRelonason.VielonwelonrRelonportelondAuthor)
      caselon VielonwelonrRelonportelondTwelonelont => Somelon(IntelonrstitialRelonason.VielonwelonrRelonportelondTwelonelont)
      caselon VielonwelonrBlocksAuthor => Somelon(IntelonrstitialRelonason.VielonwelonrBlocksAuthor)
      caselon VielonwelonrMutelonsAuthor => Somelon(IntelonrstitialRelonason.VielonwelonrMutelonsAuthor)
      caselon VielonwelonrHardMutelondAuthor => Somelon(IntelonrstitialRelonason.VielonwelonrMutelonsAuthor)
      caselon IntelonrstitialDelonvelonlopmelonntOnly => Somelon(IntelonrstitialRelonason.DelonvelonlopmelonntOnly)
      caselon DmcaWithhelonld => Somelon(IntelonrstitialRelonason.DmcaWithhelonld)
      caselon LelongalDelonmandsWithhelonld => Somelon(IntelonrstitialRelonason.LelongalDelonmandsWithhelonld)
      caselon LocalLawsWithhelonld => Somelon(IntelonrstitialRelonason.LocalLawsWithhelonld)
      caselon HatelonfulConduct => Somelon(IntelonrstitialRelonason.HatelonfulConduct)
      caselon AbusivelonBelonhavior => Somelon(IntelonrstitialRelonason.AbusivelonBelonhavior)
      caselon FosnrRelonason(appelonalablelonRelonason) => Somelon(IntelonrstitialRelonason.FosnrRelonason(appelonalablelonRelonason))
      caselon _ => Nonelon
    }

  delonf fromIntelonrstitialRelonason(intelonrstitialRelonason: IntelonrstitialRelonason): Relonason =
    intelonrstitialRelonason match {
      caselon IntelonrstitialRelonason.ContainsNsfwMelondia => Relonason.NsfwMelondia
      caselon IntelonrstitialRelonason.PossiblyUndelonsirablelon => Relonason.PossiblyUndelonsirablelon
      caselon IntelonrstitialRelonason.MatchelonsMutelondKelonyword(_) => Relonason.MutelondKelonyword
      caselon IntelonrstitialRelonason.VielonwelonrRelonportelondAuthor => Relonason.VielonwelonrRelonportelondAuthor
      caselon IntelonrstitialRelonason.VielonwelonrRelonportelondTwelonelont => Relonason.VielonwelonrRelonportelondTwelonelont
      caselon IntelonrstitialRelonason.VielonwelonrBlocksAuthor => Relonason.VielonwelonrBlocksAuthor
      caselon IntelonrstitialRelonason.VielonwelonrMutelonsAuthor => Relonason.VielonwelonrMutelonsAuthor
      caselon IntelonrstitialRelonason.DelonvelonlopmelonntOnly => Relonason.IntelonrstitialDelonvelonlopmelonntOnly
      caselon IntelonrstitialRelonason.DmcaWithhelonld => Relonason.DmcaWithhelonld
      caselon IntelonrstitialRelonason.LelongalDelonmandsWithhelonld => Relonason.LelongalDelonmandsWithhelonld
      caselon IntelonrstitialRelonason.LocalLawsWithhelonld => Relonason.LocalLawsWithhelonld
      caselon IntelonrstitialRelonason.HatelonfulConduct => Relonason.HatelonfulConduct
      caselon IntelonrstitialRelonason.AbusivelonBelonhavior => Relonason.AbusivelonBelonhavior
      caselon IntelonrstitialRelonason.FosnrRelonason(relonason) => Relonason.fromAppelonalablelonRelonason(relonason)
    }

}

selonalelond trait elonpitaph {
  lazy val namelon: String = NamingUtils.gelontFrielonndlyNamelon(this)
}

objelonct elonpitaph {

  caselon objelonct Unavailablelon elonxtelonnds elonpitaph

  caselon objelonct Blockelond elonxtelonnds elonpitaph
  caselon objelonct BlockelondBy elonxtelonnds elonpitaph
  caselon objelonct Relonportelond elonxtelonnds elonpitaph

  caselon objelonct BouncelonDelonlelontelond elonxtelonnds elonpitaph
  caselon objelonct Delonlelontelond elonxtelonnds elonpitaph
  caselon objelonct NotFound elonxtelonnds elonpitaph
  caselon objelonct PublicIntelonrelonst elonxtelonnds elonpitaph

  caselon objelonct Bouncelond elonxtelonnds elonpitaph
  caselon objelonct Protelonctelond elonxtelonnds elonpitaph
  caselon objelonct Suspelonndelond elonxtelonnds elonpitaph
  caselon objelonct Offboardelond elonxtelonnds elonpitaph
  caselon objelonct Delonactivatelond elonxtelonnds elonpitaph

  caselon objelonct MutelondKelonyword elonxtelonnds elonpitaph
  caselon objelonct Undelonragelon elonxtelonnds elonpitaph
  caselon objelonct NoStatelondAgelon elonxtelonnds elonpitaph
  caselon objelonct LoggelondOutAgelon elonxtelonnds elonpitaph
  caselon objelonct SupelonrFollowsContelonnt elonxtelonnds elonpitaph

  caselon objelonct Modelonratelond elonxtelonnds elonpitaph
  caselon objelonct ForelonmelonrgelonncyUselonOnly elonxtelonnds elonpitaph
  caselon objelonct UnavailablelonWithoutLink elonxtelonnds elonpitaph
  caselon objelonct CommunityTwelonelontHiddelonn elonxtelonnds elonpitaph
  caselon objelonct CommunityTwelonelontMelonmbelonrRelonmovelond elonxtelonnds elonpitaph
  caselon objelonct CommunityTwelonelontCommunityIsSuspelonndelond elonxtelonnds elonpitaph

  caselon objelonct UselonrSuspelonndelond elonxtelonnds elonpitaph

  caselon objelonct DelonvelonlopmelonntOnly elonxtelonnds elonpitaph

  caselon objelonct AdultMelondia elonxtelonnds elonpitaph
  caselon objelonct ViolelonntMelondia elonxtelonnds elonpitaph
  caselon objelonct OthelonrSelonnsitivelonMelondia elonxtelonnds elonpitaph

  caselon objelonct DmcaWithhelonldMelondia elonxtelonnds elonpitaph
  caselon objelonct LelongalDelonmandsWithhelonldMelondia elonxtelonnds elonpitaph
  caselon objelonct LocalLawsWithhelonldMelondia elonxtelonnds elonpitaph

  caselon objelonct ToxicRelonplyFiltelonrelond elonxtelonnds elonpitaph
}

selonalelond trait IsIntelonrstitial {
  delonf toIntelonrstitialThriftWrappelonr(): thriftscala.AnyIntelonrstitial
  delonf toIntelonrstitialThrift(): ThriftStruct
}

selonalelond trait IsAppelonalablelon {
  delonf toAppelonalablelonThrift(): thriftscala.Appelonalablelon
}

selonalelond trait IsLimitelondelonngagelonmelonnts {
  delonf policy: Option[LimitelondActionsPolicy]
  delonf gelontLimitelondelonngagelonmelonntRelonason: LimitelondelonngagelonmelonntRelonason
}

objelonct IsLimitelondelonngagelonmelonnts {
  delonf unapply(
    ilelon: IsLimitelondelonngagelonmelonnts
  ): Option[(Option[LimitelondActionsPolicy], LimitelondelonngagelonmelonntRelonason)] = {
    Somelon((ilelon.policy, ilelon.gelontLimitelondelonngagelonmelonntRelonason))
  }
}

selonalelond abstract class ActionWithelonpitaph(elonpitaph: elonpitaph) elonxtelonnds Action {
  ovelonrridelon lazy val fullNamelon: String = s"${this.namelon}/${elonpitaph.namelon}"
}

caselon class Appelonalablelon(
  relonason: Relonason,
  violationLelonvelonl: ViolationLelonvelonl,
  localizelondMelonssagelon: Option[LocalizelondMelonssagelon] = Nonelon)
    elonxtelonnds ActionWithRelonason(relonason)
    with IsAppelonalablelon {

  ovelonrridelon val selonvelonrity: Int = 17
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Appelonalablelon(toAppelonalablelonThrift())

  ovelonrridelon delonf toAppelonalablelonThrift(): thriftscala.Appelonalablelon =
    thriftscala.Appelonalablelon(
      Relonason.toAppelonalablelonRelonason(relonason, violationLelonvelonl).map(AppelonalablelonRelonasonConvelonrtelonr.toThrift),
      localizelondMelonssagelon.map(LocalizelondMelonssagelonConvelonrtelonr.toThrift)
    )

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.Appelonalablelon)
}

caselon class Drop(relonason: Relonason, applicablelonCountrielons: Option[Selonq[String]] = Nonelon)
    elonxtelonnds ActionWithRelonason(relonason) {

  ovelonrridelon val selonvelonrity: Int = 16
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Drop(
      thriftscala.Drop(
        Relonason.toDropRelonason(relonason).map(DropRelonasonConvelonrtelonr.toThrift),
        applicablelonCountrielons
      ))

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(HelonalthActionTypelon.Drop)
}

caselon class Intelonrstitial(
  relonason: Relonason,
  localizelondMelonssagelon: Option[LocalizelondMelonssagelon] = Nonelon,
  applicablelonCountrielons: Option[Selonq[String]] = Nonelon)
    elonxtelonnds ActionWithRelonason(relonason)
    with IsIntelonrstitial {

  ovelonrridelon val selonvelonrity: Int = 10
  ovelonrridelon delonf toIntelonrstitialThriftWrappelonr(): thriftscala.AnyIntelonrstitial =
    thriftscala.AnyIntelonrstitial.Intelonrstitial(
      toIntelonrstitialThrift()
    )

  ovelonrridelon delonf toIntelonrstitialThrift(): thriftscala.Intelonrstitial =
    thriftscala.Intelonrstitial(
      Relonason.toIntelonrstitialRelonason(relonason).map(IntelonrstitialRelonasonConvelonrtelonr.toThrift),
      localizelondMelonssagelon.map(LocalizelondMelonssagelonConvelonrtelonr.toThrift)
    )

  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Intelonrstitial(toIntelonrstitialThrift())

  delonf toMelondiaActionThrift(): thriftscala.MelondiaAction =
    thriftscala.MelondiaAction.Intelonrstitial(toIntelonrstitialThrift())

  ovelonrridelon delonf isComposablelon: Boolelonan = truelon

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.TwelonelontIntelonrstitial)
}

caselon class IntelonrstitialLimitelondelonngagelonmelonnts(
  relonason: Relonason,
  limitelondelonngagelonmelonntRelonason: Option[LimitelondelonngagelonmelonntRelonason],
  localizelondMelonssagelon: Option[LocalizelondMelonssagelon] = Nonelon,
  policy: Option[LimitelondActionsPolicy] = Nonelon)
    elonxtelonnds ActionWithRelonason(relonason)
    with IsIntelonrstitial
    with IsLimitelondelonngagelonmelonnts {

  ovelonrridelon val selonvelonrity: Int = 11
  ovelonrridelon delonf toIntelonrstitialThriftWrappelonr(): thriftscala.AnyIntelonrstitial =
    thriftscala.AnyIntelonrstitial.IntelonrstitialLimitelondelonngagelonmelonnts(
      toIntelonrstitialThrift()
    )

  ovelonrridelon delonf toIntelonrstitialThrift(): thriftscala.IntelonrstitialLimitelondelonngagelonmelonnts =
    thriftscala.IntelonrstitialLimitelondelonngagelonmelonnts(
      limitelondelonngagelonmelonntRelonason.map(LimitelondelonngagelonmelonntRelonasonConvelonrtelonr.toThrift),
      localizelondMelonssagelon.map(LocalizelondMelonssagelonConvelonrtelonr.toThrift)
    )

  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.IntelonrstitialLimitelondelonngagelonmelonnts(toIntelonrstitialThrift())

  ovelonrridelon delonf isComposablelon: Boolelonan = truelon

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.Limitelondelonngagelonmelonnts)

  delonf gelontLimitelondelonngagelonmelonntRelonason: LimitelondelonngagelonmelonntRelonason = limitelondelonngagelonmelonntRelonason.gelontOrelonlselon(
    LimitelondelonngagelonmelonntRelonason.NonCompliant
  )
}

caselon objelonct Allow elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = -1
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Allow(thriftscala.Allow())

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Nonelon
}

caselon objelonct Notelonvaluatelond elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = -1
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Notelonvaluatelond(thriftscala.Notelonvaluatelond())

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Nonelon
}

caselon class Tombstonelon(elonpitaph: elonpitaph, applicablelonCountryCodelons: Option[Selonq[String]] = Nonelon)
    elonxtelonnds ActionWithelonpitaph(elonpitaph) {

  ovelonrridelon val selonvelonrity: Int = 15
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Tombstonelon(thriftscala.Tombstonelon())

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(HelonalthActionTypelon.Tombstonelon)
}

caselon class LocalizelondTombstonelon(relonason: TombstonelonRelonason, melonssagelon: LocalizelondMelonssagelon) elonxtelonnds Action {
  ovelonrridelon lazy val fullNamelon: String = s"${this.namelon}/${NamingUtils.gelontFrielonndlyNamelon(relonason)}"

  ovelonrridelon val selonvelonrity: Int = 15
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Tombstonelon(
      thriftscala.Tombstonelon(
        relonason = TombstonelonRelonasonConvelonrtelonr.toThrift(Somelon(relonason)),
        melonssagelon = Somelon(LocalizelondMelonssagelonConvelonrtelonr.toThrift(melonssagelon))
      ))

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(HelonalthActionTypelon.Tombstonelon)
}

caselon class DownrankHomelonTimelonlinelon(relonason: Option[DownrankHomelonTimelonlinelonRelonason]) elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = 9
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.DownrankHomelonTimelonlinelon(toDownrankThrift())

  delonf toDownrankThrift(): thriftscala.DownrankHomelonTimelonlinelon =
    thriftscala.DownrankHomelonTimelonlinelon(
      relonason.map(DownrankHomelonTimelonlinelonRelonasonConvelonrtelonr.toThrift)
    )

  ovelonrridelon delonf isComposablelon: Boolelonan = truelon

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(HelonalthActionTypelon.Downrank)
}

caselon class Avoid(avoidRelonason: Option[AvoidRelonason] = Nonelon) elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = 1
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Avoid(toAvoidThrift())

  delonf toAvoidThrift(): thriftscala.Avoid =
    thriftscala.Avoid(
      avoidRelonason.map(AvoidRelonasonConvelonrtelonr.toThrift)
    )

  ovelonrridelon delonf isComposablelon: Boolelonan = truelon

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(HelonalthActionTypelon.Avoid)
}

caselon objelonct Downrank elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = 0
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Downrank(thriftscala.Downrank())

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(HelonalthActionTypelon.Downrank)
}

caselon objelonct ConvelonrsationSelonctionLowQuality elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = 4
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.ConvelonrsationSelonctionLowQuality(thriftscala.ConvelonrsationSelonctionLowQuality())

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.ConvelonrsationSelonctionLowQuality)
}

caselon objelonct ConvelonrsationSelonctionAbusivelonQuality elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = 5
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.ConvelonrsationSelonctionAbusivelonQuality(
      thriftscala.ConvelonrsationSelonctionAbusivelonQuality())

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.ConvelonrsationSelonctionAbusivelonQuality)

  delonf toConvelonrsationSelonctionAbusivelonQualityThrift(): thriftscala.ConvelonrsationSelonctionAbusivelonQuality =
    thriftscala.ConvelonrsationSelonctionAbusivelonQuality()
}

caselon class Limitelondelonngagelonmelonnts(
  relonason: LimitelondelonngagelonmelonntRelonason,
  policy: Option[LimitelondActionsPolicy] = Nonelon)
    elonxtelonnds Action
    with IsLimitelondelonngagelonmelonnts {

  ovelonrridelon val selonvelonrity: Int = 6
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.Limitelondelonngagelonmelonnts(toLimitelondelonngagelonmelonntsThrift())

  delonf toLimitelondelonngagelonmelonntsThrift(): thriftscala.Limitelondelonngagelonmelonnts =
    thriftscala.Limitelondelonngagelonmelonnts(
      Somelon(LimitelondelonngagelonmelonntRelonasonConvelonrtelonr.toThrift(relonason)),
      policy.map(LimitelondActionsPolicyConvelonrtelonr.toThrift),
      Somelon(relonason.toLimitelondActionsString)
    )

  ovelonrridelon delonf isComposablelon: Boolelonan = truelon

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.Limitelondelonngagelonmelonnts)

  delonf gelontLimitelondelonngagelonmelonntRelonason: LimitelondelonngagelonmelonntRelonason = relonason
}

caselon class elonmelonrgelonncyDynamicIntelonrstitial(
  copy: String,
  linkOpt: Option[String],
  localizelondMelonssagelon: Option[LocalizelondMelonssagelon] = Nonelon,
  policy: Option[LimitelondActionsPolicy] = Nonelon)
    elonxtelonnds Action
    with IsIntelonrstitial
    with IsLimitelondelonngagelonmelonnts {

  ovelonrridelon val selonvelonrity: Int = 11
  ovelonrridelon delonf toIntelonrstitialThriftWrappelonr(): thriftscala.AnyIntelonrstitial =
    thriftscala.AnyIntelonrstitial.elonmelonrgelonncyDynamicIntelonrstitial(
      toIntelonrstitialThrift()
    )

  ovelonrridelon delonf toIntelonrstitialThrift(): thriftscala.elonmelonrgelonncyDynamicIntelonrstitial =
    thriftscala.elonmelonrgelonncyDynamicIntelonrstitial(
      copy,
      linkOpt,
      localizelondMelonssagelon.map(LocalizelondMelonssagelonConvelonrtelonr.toThrift)
    )

  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.elonmelonrgelonncyDynamicIntelonrstitial(toIntelonrstitialThrift())

  ovelonrridelon delonf isComposablelon: Boolelonan = truelon

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.TwelonelontIntelonrstitial)

  delonf gelontLimitelondelonngagelonmelonntRelonason: LimitelondelonngagelonmelonntRelonason = LimitelondelonngagelonmelonntRelonason.NonCompliant
}

caselon class SoftIntelonrvelonntion(
  relonason: SoftIntelonrvelonntionRelonason,
  elonngagelonmelonntNudgelon: Boolelonan,
  supprelonssAutoplay: Boolelonan,
  warning: Option[String] = Nonelon,
  delontailsUrl: Option[String] = Nonelon,
  displayTypelon: Option[SoftIntelonrvelonntionDisplayTypelon] = Nonelon,
  flelonelontIntelonrstitial: Option[FlelonelontIntelonrstitial] = Nonelon)
    elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = 7
  delonf toSoftIntelonrvelonntionThrift(): thriftscala.SoftIntelonrvelonntion =
    thriftscala.SoftIntelonrvelonntion(
      Somelon(SoftIntelonrvelonntionRelonasonConvelonrtelonr.toThrift(relonason)),
      elonngagelonmelonntNudgelon = Somelon(elonngagelonmelonntNudgelon),
      supprelonssAutoplay = Somelon(supprelonssAutoplay),
      warning = warning,
      delontailsUrl = delontailsUrl,
      displayTypelon = SoftIntelonrvelonntionDisplayTypelonConvelonrtelonr.toThrift(displayTypelon)
    )

  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.SoftIntelonrvelonntion(toSoftIntelonrvelonntionThrift())

  ovelonrridelon delonf isComposablelon: Boolelonan = truelon

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.SoftIntelonrvelonntion)
}

caselon class TwelonelontIntelonrstitial(
  intelonrstitial: Option[IsIntelonrstitial],
  softIntelonrvelonntion: Option[SoftIntelonrvelonntion],
  limitelondelonngagelonmelonnts: Option[Limitelondelonngagelonmelonnts],
  downrank: Option[DownrankHomelonTimelonlinelon],
  avoid: Option[Avoid],
  melondiaIntelonrstitial: Option[Intelonrstitial] = Nonelon,
  twelonelontVisibilityNudgelon: Option[TwelonelontVisibilityNudgelon] = Nonelon,
  abusivelonQuality: Option[ConvelonrsationSelonctionAbusivelonQuality.typelon] = Nonelon,
  appelonalablelon: Option[Appelonalablelon] = Nonelon)
    elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = 12
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.TwelonelontIntelonrstitial(
      thriftscala.TwelonelontIntelonrstitial(
        intelonrstitial.map(_.toIntelonrstitialThriftWrappelonr()),
        softIntelonrvelonntion.map(_.toSoftIntelonrvelonntionThrift()),
        limitelondelonngagelonmelonnts.map(_.toLimitelondelonngagelonmelonntsThrift()),
        downrank.map(_.toDownrankThrift()),
        avoid.map(_.toAvoidThrift()),
        melondiaIntelonrstitial.map(_.toMelondiaActionThrift()),
        twelonelontVisibilityNudgelon.map(_.toTwelonelontVisbilityNudgelonThrift()),
        abusivelonQuality.map(_.toConvelonrsationSelonctionAbusivelonQualityThrift()),
        appelonalablelon.map(_.toAppelonalablelonThrift())
      )
    )

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Somelon(
    HelonalthActionTypelon.TwelonelontIntelonrstitial)
}

selonalelond trait LocalizelondNudgelonActionTypelon
objelonct LocalizelondNudgelonActionTypelon {
  caselon objelonct Relonply elonxtelonnds LocalizelondNudgelonActionTypelon
  caselon objelonct Relontwelonelont elonxtelonnds LocalizelondNudgelonActionTypelon
  caselon objelonct Likelon elonxtelonnds LocalizelondNudgelonActionTypelon
  caselon objelonct Sharelon elonxtelonnds LocalizelondNudgelonActionTypelon
  caselon objelonct Unspeloncifielond elonxtelonnds LocalizelondNudgelonActionTypelon

  delonf toThrift(
    localizelondNudgelonActionTypelon: LocalizelondNudgelonActionTypelon
  ): thriftscala.TwelonelontVisibilityNudgelonActionTypelon =
    localizelondNudgelonActionTypelon match {
      caselon Relonply => thriftscala.TwelonelontVisibilityNudgelonActionTypelon.Relonply
      caselon Relontwelonelont => thriftscala.TwelonelontVisibilityNudgelonActionTypelon.Relontwelonelont
      caselon Likelon => thriftscala.TwelonelontVisibilityNudgelonActionTypelon.Likelon
      caselon Sharelon => thriftscala.TwelonelontVisibilityNudgelonActionTypelon.Sharelon
      caselon Unspeloncifielond =>
        thriftscala.TwelonelontVisibilityNudgelonActionTypelon.elonnumUnknownTwelonelontVisibilityNudgelonActionTypelon(5)
    }

  delonf fromStratoThrift(stratoNudgelonActionTypelon: StratoNudgelonActionTypelon): LocalizelondNudgelonActionTypelon =
    stratoNudgelonActionTypelon match {
      caselon StratoNudgelonActionTypelon.Relonply => Relonply
      caselon StratoNudgelonActionTypelon.Relontwelonelont => Relontwelonelont
      caselon StratoNudgelonActionTypelon.Likelon => Likelon
      caselon StratoNudgelonActionTypelon.Sharelon => Sharelon
      caselon elonnumUnknownNudgelonActionTypelon(_) => Unspeloncifielond
    }
}

caselon class LocalizelondNudgelonActionPayload(
  helonading: Option[String],
  subhelonading: Option[String],
  iconNamelon: Option[String],
  ctaTitlelon: Option[String],
  ctaUrl: Option[String],
  postCtaTelonxt: Option[String]) {

  delonf toThrift(): thriftscala.TwelonelontVisibilityNudgelonActionPayload = {
    thriftscala.TwelonelontVisibilityNudgelonActionPayload(
      helonading = helonading,
      subhelonading = subhelonading,
      iconNamelon = iconNamelon,
      ctaTitlelon = ctaTitlelon,
      ctaUrl = ctaUrl,
      postCtaTelonxt = postCtaTelonxt
    )
  }
}

objelonct LocalizelondNudgelonActionPayload {
  delonf fromStratoThrift(
    stratoNudgelonActionPayload: StratoNudgelonActionPayload
  ): LocalizelondNudgelonActionPayload =
    LocalizelondNudgelonActionPayload(
      helonading = stratoNudgelonActionPayload.helonading,
      subhelonading = stratoNudgelonActionPayload.subhelonading,
      iconNamelon = stratoNudgelonActionPayload.iconNamelon,
      ctaTitlelon = stratoNudgelonActionPayload.ctaTitlelon,
      ctaUrl = stratoNudgelonActionPayload.ctaUrl,
      postCtaTelonxt = stratoNudgelonActionPayload.postCtaTelonxt
    )
}

caselon class LocalizelondNudgelonAction(
  nudgelonActionTypelon: LocalizelondNudgelonActionTypelon,
  nudgelonActionPayload: Option[LocalizelondNudgelonActionPayload]) {
  delonf toThrift(): thriftscala.TwelonelontVisibilityNudgelonAction = {
    thriftscala.TwelonelontVisibilityNudgelonAction(
      twelonelontVisibilitynudgelonActionTypelon = LocalizelondNudgelonActionTypelon.toThrift(nudgelonActionTypelon),
      twelonelontVisibilityNudgelonActionPayload = nudgelonActionPayload.map(_.toThrift)
    )
  }
}

objelonct LocalizelondNudgelonAction {
  delonf fromStratoThrift(stratoNudgelonAction: StratoNudgelonAction): LocalizelondNudgelonAction =
    LocalizelondNudgelonAction(
      nudgelonActionTypelon =
        LocalizelondNudgelonActionTypelon.fromStratoThrift(stratoNudgelonAction.nudgelonActionTypelon),
      nudgelonActionPayload =
        stratoNudgelonAction.nudgelonActionPayload.map(LocalizelondNudgelonActionPayload.fromStratoThrift)
    )
}

caselon class LocalizelondNudgelon(localizelondNudgelonActions: Selonq[LocalizelondNudgelonAction])

caselon objelonct LocalizelondNudgelon {
  delonf fromStratoThrift(stratoNudgelon: StratoNudgelon): LocalizelondNudgelon =
    LocalizelondNudgelon(localizelondNudgelonActions =
      stratoNudgelon.nudgelonActions.map(LocalizelondNudgelonAction.fromStratoThrift))
}

caselon class TwelonelontVisibilityNudgelon(
  relonason: TwelonelontVisibilityNudgelonRelonason,
  localizelondNudgelon: Option[LocalizelondNudgelon] = Nonelon)
    elonxtelonnds Action {

  ovelonrridelon val selonvelonrity: Int = 3
  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.TwelonelontVisibilityNudgelon(
      localizelondNudgelon match {
        caselon Somelon(nudgelon) =>
          thriftscala.TwelonelontVisibilityNudgelon(
            twelonelontVisibilityNudgelonActions = Somelon(nudgelon.localizelondNudgelonActions.map(_.toThrift()))
          )
        caselon _ => thriftscala.TwelonelontVisibilityNudgelon(twelonelontVisibilityNudgelonActions = Nonelon)
      }
    )

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] =
    Somelon(HelonalthActionTypelon.TwelonelontVisibilityNudgelon)

  delonf toTwelonelontVisbilityNudgelonThrift(): thriftscala.TwelonelontVisibilityNudgelon =
    thriftscala.TwelonelontVisibilityNudgelon(twelonelontVisibilityNudgelonActions =
      localizelondNudgelon.map(_.localizelondNudgelonActions.map(_.toThrift())))
}

trait BaselonCompliancelonTwelonelontNoticelon {
  val compliancelonTwelonelontNoticelonelonvelonntTypelon: CompliancelonTwelonelontNoticelonelonvelonntTypelon
  val delontails: Option[String]
  val elonxtelonndelondDelontailsUrl: Option[String]
}

caselon class CompliancelonTwelonelontNoticelonPrelonelonnrichmelonnt(
  relonason: Relonason,
  compliancelonTwelonelontNoticelonelonvelonntTypelon: CompliancelonTwelonelontNoticelonelonvelonntTypelon,
  delontails: Option[String] = Nonelon,
  elonxtelonndelondDelontailsUrl: Option[String] = Nonelon)
    elonxtelonnds Action
    with BaselonCompliancelonTwelonelontNoticelon {

  ovelonrridelon val selonvelonrity: Int = 2
  delonf toCompliancelonTwelonelontNoticelonThrift(): thriftscala.CompliancelonTwelonelontNoticelon =
    thriftscala.CompliancelonTwelonelontNoticelon(
      CompliancelonTwelonelontNoticelonelonvelonntTypelonConvelonrtelonr.toThrift(compliancelonTwelonelontNoticelonelonvelonntTypelon),
      CompliancelonTwelonelontNoticelonelonvelonntTypelonConvelonrtelonr.elonvelonntTypelonToLabelonlTitlelon(compliancelonTwelonelontNoticelonelonvelonntTypelon),
      delontails,
      elonxtelonndelondDelontailsUrl
    )

  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.CompliancelonTwelonelontNoticelon(
      toCompliancelonTwelonelontNoticelonThrift()
    )

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Nonelon

  delonf toCompliancelonTwelonelontNoticelon(): CompliancelonTwelonelontNoticelon = {
    CompliancelonTwelonelontNoticelon(
      compliancelonTwelonelontNoticelonelonvelonntTypelon = compliancelonTwelonelontNoticelonelonvelonntTypelon,
      labelonlTitlelon = CompliancelonTwelonelontNoticelonelonvelonntTypelonConvelonrtelonr.elonvelonntTypelonToLabelonlTitlelon(
        compliancelonTwelonelontNoticelonelonvelonntTypelon),
      delontails = delontails,
      elonxtelonndelondDelontailsUrl = elonxtelonndelondDelontailsUrl
    )
  }
}

caselon class CompliancelonTwelonelontNoticelon(
  compliancelonTwelonelontNoticelonelonvelonntTypelon: CompliancelonTwelonelontNoticelonelonvelonntTypelon,
  labelonlTitlelon: Option[String] = Nonelon,
  delontails: Option[String] = Nonelon,
  elonxtelonndelondDelontailsUrl: Option[String] = Nonelon)
    elonxtelonnds Action
    with BaselonCompliancelonTwelonelontNoticelon {

  ovelonrridelon val selonvelonrity: Int = 2
  delonf toCompliancelonTwelonelontNoticelonThrift(): thriftscala.CompliancelonTwelonelontNoticelon =
    thriftscala.CompliancelonTwelonelontNoticelon(
      CompliancelonTwelonelontNoticelonelonvelonntTypelonConvelonrtelonr.toThrift(compliancelonTwelonelontNoticelonelonvelonntTypelon),
      labelonlTitlelon,
      delontails,
      elonxtelonndelondDelontailsUrl
    )

  ovelonrridelon delonf toActionThrift(): thriftscala.Action =
    thriftscala.Action.CompliancelonTwelonelontNoticelon(
      toCompliancelonTwelonelontNoticelonThrift()
    )

  ovelonrridelon delonf toHelonalthActionTypelonThrift: Option[HelonalthActionTypelon] = Nonelon
}

objelonct Action {
  delonf toThrift[T <: Action](action: T): thriftscala.Action =
    action.toActionThrift()

  delonf gelontFirstIntelonrstitial(actions: Action*): Option[IsIntelonrstitial] =
    actions.collelonctFirst {
      caselon ilelon: IntelonrstitialLimitelondelonngagelonmelonnts => ilelon
      caselon elondi: elonmelonrgelonncyDynamicIntelonrstitial => elondi
      caselon i: Intelonrstitial => i
    }

  delonf gelontFirstSoftIntelonrvelonntion(actions: Action*): Option[SoftIntelonrvelonntion] =
    actions.collelonctFirst {
      caselon si: SoftIntelonrvelonntion => si
    }

  delonf gelontFirstLimitelondelonngagelonmelonnts(actions: Action*): Option[Limitelondelonngagelonmelonnts] =
    actions.collelonctFirst {
      caselon lelon: Limitelondelonngagelonmelonnts => lelon
    }

  delonf gelontAllLimitelondelonngagelonmelonnts(actions: Action*): Selonq[IsLimitelondelonngagelonmelonnts] =
    actions.collelonct {
      caselon ilelon: IsLimitelondelonngagelonmelonnts => ilelon
    }

  delonf gelontFirstDownrankHomelonTimelonlinelon(actions: Action*): Option[DownrankHomelonTimelonlinelon] =
    actions.collelonctFirst {
      caselon dr: DownrankHomelonTimelonlinelon => dr
    }

  delonf gelontFirstAvoid(actions: Action*): Option[Avoid] =
    actions.collelonctFirst {
      caselon a: Avoid => a
    }

  delonf gelontFirstMelondiaIntelonrstitial(actions: Action*): Option[Intelonrstitial] =
    actions.collelonctFirst {
      caselon i: Intelonrstitial if Relonason.NSFW_MelonDIA.contains(i.relonason) => i
    }

  delonf gelontFirstTwelonelontVisibilityNudgelon(actions: Action*): Option[TwelonelontVisibilityNudgelon] =
    actions.collelonctFirst {
      caselon n: TwelonelontVisibilityNudgelon => n
    }
}

selonalelond trait Statelon {
  lazy val namelon: String = NamingUtils.gelontFrielonndlyNamelon(this)
}

objelonct Statelon {
  caselon objelonct Pelonnding elonxtelonnds Statelon
  caselon objelonct Disablelond elonxtelonnds Statelon
  final caselon class MissingFelonaturelon(felonaturelons: Selont[Felonaturelon[_]]) elonxtelonnds Statelon
  final caselon class FelonaturelonFailelond(felonaturelons: Map[Felonaturelon[_], Throwablelon]) elonxtelonnds Statelon
  final caselon class RulelonFailelond(throwablelon: Throwablelon) elonxtelonnds Statelon
  caselon objelonct Skippelond elonxtelonnds Statelon
  caselon objelonct ShortCircuitelond elonxtelonnds Statelon
  caselon objelonct Helonldback elonxtelonnds Statelon
  caselon objelonct elonvaluatelond elonxtelonnds Statelon
}

caselon class RulelonRelonsult(action: Action, statelon: Statelon)
