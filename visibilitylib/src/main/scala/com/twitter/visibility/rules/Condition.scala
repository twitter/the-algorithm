packagelon com.twittelonr.visibility.rulelons

import com.twittelonr.contelonnthelonalth.selonnsitivelonmelondiaselonttings.thriftscala.SelonnsitivelonMelondiaSelonttingsLelonvelonl
import com.twittelonr.contelonnthelonalth.toxicrelonplyfiltelonr.thriftscala.FiltelonrStatelon
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.gizmoduck.thriftscala.Labelonl
import com.twittelonr.gizmoduck.thriftscala.MutelonSurfacelon
import com.twittelonr.helonalth.platform_manipulation.stcm_twelonelont_holdback.StcmTwelonelontHoldback
import com.twittelonr.selonarch.common.constants.thriftscala.ThriftQuelonrySourcelon
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.takelondown.util.TakelondownRelonasons
import com.twittelonr.takelondown.util.{TakelondownRelonasons => TakelondownRelonasonsUtil}
import com.twittelonr.timelonlinelons.configapi.elonnumParam
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.tselonng.withholding.thriftscala.TakelondownRelonason
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon
import com.twittelonr.visibility.configapi.params.RulelonParam
import com.twittelonr.visibility.configapi.params.RulelonParams
import com.twittelonr.visibility.felonaturelons.AuthorIsSuspelonndelond
import com.twittelonr.visibility.felonaturelons.CardIsPoll
import com.twittelonr.visibility.felonaturelons.CardUriHost
import com.twittelonr.visibility.felonaturelons.SelonarchQuelonrySourcelon
import com.twittelonr.visibility.felonaturelons._
import com.twittelonr.visibility.felonaturelons.{AuthorBlocksOutelonrAuthor => AuthorBlocksOutelonrAuthorFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{AuthorBlocksVielonwelonr => AuthorBlocksVielonwelonrFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{
  CommunityTwelonelontAuthorIsRelonmovelond => CommunityTwelonelontAuthorIsRelonmovelondFelonaturelon
}
import com.twittelonr.visibility.felonaturelons.{
  CommunityTwelonelontCommunityNotFound => CommunityTwelonelontCommunityNotFoundFelonaturelon
}
import com.twittelonr.visibility.felonaturelons.{
  CommunityTwelonelontCommunityDelonlelontelond => CommunityTwelonelontCommunityDelonlelontelondFelonaturelon
}
import com.twittelonr.visibility.felonaturelons.{
  CommunityTwelonelontCommunitySuspelonndelond => CommunityTwelonelontCommunitySuspelonndelondFelonaturelon
}
import com.twittelonr.visibility.felonaturelons.{
  CommunityTwelonelontCommunityVisiblelon => CommunityTwelonelontCommunityVisiblelonFelonaturelon
}
import com.twittelonr.visibility.felonaturelons.{CommunityTwelonelontIsHiddelonn => CommunityTwelonelontIsHiddelonnFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{
  NotificationIsOnCommunityTwelonelont => NotificationIsOnCommunityTwelonelontFelonaturelon
}
import com.twittelonr.visibility.felonaturelons.{OutelonrAuthorFollowsAuthor => OutelonrAuthorFollowsAuthorFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{OutelonrAuthorIsInnelonrAuthor => OutelonrAuthorIsInnelonrAuthorFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{TwelonelontHasCard => TwelonelontHasCardFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{TwelonelontHasMelondia => TwelonelontHasMelondiaFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{TwelonelontIsCommunityTwelonelont => TwelonelontIsCommunityTwelonelontFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{TwelonelontIselonditTwelonelont => TwelonelontIselonditTwelonelontFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{TwelonelontIsStalelonTwelonelont => TwelonelontIsStalelonTwelonelontFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{VielonwelonrBlocksAuthor => VielonwelonrBlocksAuthorFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{VielonwelonrIsCommunityAdmin => VielonwelonrIsCommunityAdminFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{VielonwelonrIsCommunityMelonmbelonr => VielonwelonrIsCommunityMelonmbelonrFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{
  VielonwelonrIsCommunityModelonrator => VielonwelonrIsCommunityModelonratorFelonaturelon
}
import com.twittelonr.visibility.felonaturelons.{
  VielonwelonrIsIntelonrnalCommunitielonsAdmin => VielonwelonrIsIntelonrnalCommunitielonsAdminFelonaturelon
}
import com.twittelonr.visibility.felonaturelons.{VielonwelonrMutelonsAuthor => VielonwelonrMutelonsAuthorFelonaturelon}
import com.twittelonr.visibility.felonaturelons.{
  VielonwelonrMutelonsRelontwelonelontsFromAuthor => VielonwelonrMutelonsRelontwelonelontsFromAuthorFelonaturelon
}
import com.twittelonr.visibility.modelonls.ViolationLelonvelonl
import com.twittelonr.visibility.modelonls._
import com.twittelonr.visibility.rulelons.Relonsult.FoundCardUriRootDomain
import com.twittelonr.visibility.rulelons.Relonsult.FoundMelondiaLabelonl
import com.twittelonr.visibility.rulelons.Relonsult.FoundSpacelonLabelonl
import com.twittelonr.visibility.rulelons.Relonsult.FoundSpacelonLabelonlWithScorelonAbovelonThrelonshold
import com.twittelonr.visibility.rulelons.Relonsult.FoundTwelonelontLabelonl
import com.twittelonr.visibility.rulelons.Relonsult.FoundTwelonelontLabelonlForPelonrspelonctivalUselonr
import com.twittelonr.visibility.rulelons.Relonsult.FoundTwelonelontLabelonlWithLanguagelonIn
import com.twittelonr.visibility.rulelons.Relonsult.FoundTwelonelontLabelonlWithLanguagelonScorelonAbovelonThrelonshold
import com.twittelonr.visibility.rulelons.Relonsult.FoundTwelonelontLabelonlWithScorelonAbovelonThrelonshold
import com.twittelonr.visibility.rulelons.Relonsult.FoundTwelonelontViolationOfLelonvelonl
import com.twittelonr.visibility.rulelons.Relonsult.FoundTwelonelontViolationOfSomelonLelonvelonl
import com.twittelonr.visibility.rulelons.Relonsult.FoundUselonrLabelonl
import com.twittelonr.visibility.rulelons.Relonsult.FoundUselonrRolelon
import com.twittelonr.visibility.rulelons.Relonsult.HasQuelonrySourcelon
import com.twittelonr.visibility.rulelons.Relonsult.HasTwelonelontTimelonstampAftelonrCutoff
import com.twittelonr.visibility.rulelons.Relonsult.HasTwelonelontTimelonstampAftelonrOffselont
import com.twittelonr.visibility.rulelons.Relonsult.HasTwelonelontTimelonstampBelonforelonCutoff
import com.twittelonr.visibility.rulelons.Relonsult.ParamWasTruelon
import com.twittelonr.visibility.rulelons.Relonsult.Relonsult
import com.twittelonr.visibility.rulelons.Relonsult.Satisfielond
import com.twittelonr.visibility.rulelons.Relonsult.Unsatisfielond
import com.twittelonr.visibility.util.NamingUtils
import com.twittelonr.visibility.{felonaturelons => felonats}

selonalelond trait PrelonFiltelonrRelonsult
caselon objelonct Filtelonrelond elonxtelonnds PrelonFiltelonrRelonsult
caselon objelonct NelonelondsFullelonvaluation elonxtelonnds PrelonFiltelonrRelonsult
caselon objelonct NotFiltelonrelond elonxtelonnds PrelonFiltelonrRelonsult

selonalelond trait Condition {
  lazy val namelon: String = NamingUtils.gelontFrielonndlyNamelon(this)
  delonf felonaturelons: Selont[Felonaturelon[_]]
  delonf optionalFelonaturelons: Selont[Felonaturelon[_]]

  delonf prelonFiltelonr(
    elonvaluationContelonxt: elonvaluationContelonxt,
    felonaturelonMap: Map[Felonaturelon[_], _]
  ): PrelonFiltelonrRelonsult = {
    if (felonaturelons.forall(felonaturelonMap.contains)) {
      if (apply(elonvaluationContelonxt, felonaturelonMap).asBoolelonan) {
        NotFiltelonrelond
      } elonlselon {
        Filtelonrelond
      }
    } elonlselon {
      NelonelondsFullelonvaluation
    }
  }

  delonf apply(elonvaluationContelonxt: elonvaluationContelonxt, felonaturelonMap: Map[Felonaturelon[_], _]): Relonsult
}

trait PrelonFiltelonrOnOptionalFelonaturelons elonxtelonnds Condition {
  ovelonrridelon delonf prelonFiltelonr(
    elonvaluationContelonxt: elonvaluationContelonxt,
    felonaturelonMap: Map[Felonaturelon[_], _]
  ): PrelonFiltelonrRelonsult =
    if ((felonaturelons ++ optionalFelonaturelons).forall(felonaturelonMap.contains)) {
      if (apply(elonvaluationContelonxt, felonaturelonMap).asBoolelonan) {
        NotFiltelonrelond
      } elonlselon {
        Filtelonrelond
      }
    } elonlselon {
      NelonelondsFullelonvaluation
    }
}

trait HasSafelontyLabelonlTypelon {
  val labelonlTypelons: Selont[SafelontyLabelonlTypelon]
  delonf hasLabelonlTypelon(labelonlTypelon: SafelontyLabelonlTypelon): Boolelonan = labelonlTypelons.contains(labelonlTypelon)
}

selonalelond trait HasNelonstelondConditions elonxtelonnds HasSafelontyLabelonlTypelon {
  val conditions: Selonq[Condition]
  ovelonrridelon lazy val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = conditions
    .collelonct {
      caselon lt: HasSafelontyLabelonlTypelon => lt.labelonlTypelons
    }.flattelonn.toSelont
}

objelonct Relonsult {
  selonalelond trait ConditionRelonason
  caselon objelonct FoundInnelonrQuotelondTwelonelont elonxtelonnds ConditionRelonason
  caselon objelonct FoundTwelonelontViolationOfSomelonLelonvelonl elonxtelonnds ConditionRelonason
  caselon class FoundTwelonelontViolationOfLelonvelonl(lelonvelonl: ViolationLelonvelonl) elonxtelonnds ConditionRelonason
  caselon class FoundTwelonelontLabelonl(labelonl: TwelonelontSafelontyLabelonlTypelon) elonxtelonnds ConditionRelonason
  caselon class FoundSpacelonLabelonl(labelonl: SpacelonSafelontyLabelonlTypelon) elonxtelonnds ConditionRelonason
  caselon class FoundMelondiaLabelonl(labelonl: MelondiaSafelontyLabelonlTypelon) elonxtelonnds ConditionRelonason
  caselon class FoundTwelonelontLabelonlForPelonrspelonctivalUselonr(labelonl: TwelonelontSafelontyLabelonlTypelon) elonxtelonnds ConditionRelonason
  caselon class FoundTwelonelontLabelonlWithLanguagelonScorelonAbovelonThrelonshold(
    labelonl: TwelonelontSafelontyLabelonlTypelon,
    languagelonsToScorelonThrelonsholds: Map[String, Doublelon])
      elonxtelonnds ConditionRelonason
  caselon class FoundTwelonelontLabelonlWithScorelonAbovelonThrelonshold(labelonl: TwelonelontSafelontyLabelonlTypelon, threlonshold: Doublelon)
      elonxtelonnds ConditionRelonason
  caselon class FoundTwelonelontLabelonlWithLanguagelonIn(
    safelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon,
    languagelons: Selont[String])
      elonxtelonnds ConditionRelonason
  caselon class FoundTwelonelontSafelontyLabelonlWithPrelondicatelon(safelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon, namelon: String)
      elonxtelonnds ConditionRelonason
  caselon class FoundUselonrLabelonl(labelonl: UselonrLabelonlValuelon) elonxtelonnds ConditionRelonason
  caselon class FoundMutelondKelonyword(kelonyword: String) elonxtelonnds ConditionRelonason
  caselon objelonct HasTwelonelontTimelonstampAftelonrCutoff elonxtelonnds ConditionRelonason
  caselon objelonct HasTwelonelontTimelonstampAftelonrOffselont elonxtelonnds ConditionRelonason
  caselon objelonct HasTwelonelontTimelonstampBelonforelonCutoff elonxtelonnds ConditionRelonason
  caselon class IsTwelonelontRelonplyToParelonntTwelonelontBelonforelonDuration(duration: Duration) elonxtelonnds ConditionRelonason
  caselon class IsTwelonelontRelonplyToRootTwelonelontBelonforelonDuration(duration: Duration) elonxtelonnds ConditionRelonason
  caselon class HasQuelonrySourcelon(quelonrySourcelon: ThriftQuelonrySourcelon) elonxtelonnds ConditionRelonason
  caselon class FoundUselonrRolelon(rolelon: String) elonxtelonnds ConditionRelonason
  caselon class VielonwelonrInHrcj(jurisdiction: String) elonxtelonnds ConditionRelonason
  caselon class VielonwelonrOrRelonquelonstInCountry(country: String) elonxtelonnds ConditionRelonason
  caselon class VielonwelonrAgelonInYelonars(agelonInYelonars: Int) elonxtelonnds ConditionRelonason
  caselon objelonct NoVielonwelonrAgelon elonxtelonnds ConditionRelonason
  caselon class ParamWasTruelon(param: Param[Boolelonan]) elonxtelonnds ConditionRelonason
  caselon class FoundCardUriRootDomain(domain: String) elonxtelonnds ConditionRelonason
  caselon objelonct Unknown elonxtelonnds ConditionRelonason

  selonalelond trait Relonsult {
    delonf asBoolelonan: Boolelonan
  }

  val SatisfielondRelonsult: Relonsult = Satisfielond()

  caselon class Satisfielond(relonason: ConditionRelonason = Unknown) elonxtelonnds Relonsult {
    ovelonrridelon val asBoolelonan: Boolelonan = truelon
  }

  caselon class Unsatisfielond(condition: Condition) elonxtelonnds Relonsult {
    ovelonrridelon val asBoolelonan: Boolelonan = falselon
  }

  delonf fromMutelondKelonyword(mutelondKelonyword: MutelondKelonyword, unsatisfielond: Unsatisfielond): Relonsult = {
    mutelondKelonyword match {
      caselon MutelondKelonyword(Somelon(kelonyword)) => Satisfielond(FoundMutelondKelonyword(kelonyword))
      caselon _ => unsatisfielond
    }
  }

  caselon class FoundSpacelonLabelonlWithScorelonAbovelonThrelonshold(labelonl: SpacelonSafelontyLabelonlTypelon, threlonshold: Doublelon)
      elonxtelonnds ConditionRelonason
}

objelonct Condition {

  abstract class BoolelonanFelonaturelonCondition(felonaturelon: Felonaturelon[Boolelonan]) elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(felonaturelon)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      if (felonaturelonMap(felonaturelon).asInstancelonOf[Boolelonan]) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
  }

  caselon class ParamIsTruelon(param: Param[Boolelonan]) elonxtelonnds Condition with HasParams {
    ovelonrridelon lazy val namelon: String = s"ParamIsTruelon(${NamingUtils.gelontFrielonndlyNamelon(param)})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult = Satisfielond(ParamWasTruelon(param))

    ovelonrridelon val params: Selont[Param[_]] = Selont(param)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      if (elonvaluationContelonxt.params(param)) {
        SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
  }

  caselon objelonct Nelonvelonr elonxtelonnds Condition {
    ovelonrridelon lazy val namelon: String = s"""Nelonvelonr"""
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult = {
      NelonelondsFullelonvaluation
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      UnsatisfielondRelonsult
  }

  class BoolelonanCondition(valuelon: Boolelonan) elonxtelonnds Condition {
    ovelonrridelon lazy val namelon: String = s"""${if (valuelon) "Truelon" elonlselon "Falselon"}"""
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      valuelon match {
        caselon truelon => Relonsult.SatisfielondRelonsult
        caselon falselon => UnsatisfielondRelonsult
      }
  }

  caselon objelonct Truelon elonxtelonnds BoolelonanCondition(truelon)
  caselon objelonct Falselon elonxtelonnds BoolelonanCondition(falselon)

  abstract class ContelonntTakelonndownInVielonwelonrCountry(takelondownFelonaturelon: Felonaturelon[Selonq[TakelondownRelonason]])
      elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(takelondownFelonaturelon)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(RelonquelonstCountryCodelon)

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val relonquelonstCountryCodelon = felonaturelonMap.gelont(RelonquelonstCountryCodelon).asInstancelonOf[Option[String]]
      val takelondownRelonasons = felonaturelonMap(takelondownFelonaturelon).asInstancelonOf[Selonq[TakelondownRelonason]]
      if (TakelondownRelonasonsUtil.isTakelonnDownIn(relonquelonstCountryCodelon, takelondownRelonasons)) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct TwelonelontTakelonndownInVielonwelonrCountry
      elonxtelonnds ContelonntTakelonndownInVielonwelonrCountry(TwelonelontTakelondownRelonasons)

  caselon objelonct AuthorTakelonndownInVielonwelonrCountry
      elonxtelonnds ContelonntTakelonndownInVielonwelonrCountry(AuthorTakelondownRelonasons)

  caselon objelonct SuspelonndelondAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIsSuspelonndelond)

  caselon objelonct SuspelonndelondVielonwelonr elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsSuspelonndelond)

  caselon objelonct DelonactivatelondVielonwelonr elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsDelonactivatelond)

  caselon objelonct UnavailablelonAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIsUnavailablelon)

  caselon objelonct IsVelonrifielondCrawlelonrVielonwelonr elonxtelonnds BoolelonanFelonaturelonCondition(RelonquelonstIsVelonrifielondCrawlelonr)

  caselon objelonct LoggelondOutVielonwelonr elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrId)

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      if (felonaturelonMap.contains(VielonwelonrId)) UnsatisfielondRelonsult elonlselon Relonsult.SatisfielondRelonsult
  }

  caselon objelonct IsSelonlfQuotelon elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(AuthorId, OutelonrAuthorId)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val authorIds = felonaturelonMap(AuthorId).asInstancelonOf[Selont[Long]]
      val outelonrAuthorId = felonaturelonMap(OutelonrAuthorId).asInstancelonOf[Long]
      if (authorIds.contains(outelonrAuthorId)) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct VielonwelonrIsAuthor elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(AuthorId)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrId)

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      if (felonaturelonMap.contains(VielonwelonrId)) {
        val authorIds = felonaturelonMap(AuthorId).asInstancelonOf[Selont[Long]]
        val vielonwelonrId = felonaturelonMap(VielonwelonrId).asInstancelonOf[Long]
        if (authorIds.contains(vielonwelonrId)) {
          Relonsult.SatisfielondRelonsult
        } elonlselon {
          UnsatisfielondRelonsult
        }
      } elonlselon {
        UnsatisfielondRelonsult
      }
  }

  caselon objelonct NonAuthorVielonwelonr elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(AuthorId)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrId)

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      if (felonaturelonMap.contains(VielonwelonrId)) {
        val authorIds = felonaturelonMap(AuthorId).asInstancelonOf[Selont[Long]]
        val vielonwelonrId = felonaturelonMap(VielonwelonrId).asInstancelonOf[Long]
        if (authorIds.contains(vielonwelonrId)) {
          UnsatisfielondRelonsult
        } elonlselon {
          Relonsult.SatisfielondRelonsult
        }
      } elonlselon {
        Relonsult.SatisfielondRelonsult
      }
  }

  caselon objelonct VielonwelonrFollowsAuthorOfFosnrViolatingTwelonelont
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrFollowsAuthorOfViolatingTwelonelont)

  caselon objelonct VielonwelonrDoelonsNotFollowAuthorOfFosnrViolatingTwelonelont
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrDoelonsNotFollowAuthorOfViolatingTwelonelont)

  caselon objelonct VielonwelonrDoelonsFollowAuthor elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrFollowsAuthor)

  caselon objelonct AuthorDoelonsFollowVielonwelonr elonxtelonnds BoolelonanFelonaturelonCondition(AuthorFollowsVielonwelonr)

  caselon objelonct AuthorBlocksVielonwelonr elonxtelonnds BoolelonanFelonaturelonCondition(AuthorBlocksVielonwelonrFelonaturelon)

  caselon objelonct VielonwelonrBlocksAuthor elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrBlocksAuthorFelonaturelon)

  caselon objelonct VielonwelonrIsUnmelonntionelond elonxtelonnds BoolelonanFelonaturelonCondition(NotificationIsOnUnmelonntionelondVielonwelonr)

  caselon objelonct AuthorBlocksOutelonrAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(AuthorBlocksOutelonrAuthorFelonaturelon)

  caselon objelonct OutelonrAuthorFollowsAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(OutelonrAuthorFollowsAuthorFelonaturelon)

  caselon objelonct OutelonrAuthorIsInnelonrAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(OutelonrAuthorIsInnelonrAuthorFelonaturelon)

  caselon objelonct VielonwelonrMutelonsAuthor elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrMutelonsAuthorFelonaturelon)

  caselon objelonct VielonwelonrRelonportsAuthor elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrRelonportsAuthorAsSpam)
  caselon objelonct VielonwelonrRelonportsTwelonelont elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrRelonportelondTwelonelont)

  caselon objelonct IsQuotelondInnelonrTwelonelont elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsInnelonrQuotelondTwelonelont)

  caselon objelonct IsSourcelonTwelonelont elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsSourcelonTwelonelont)

  caselon objelonct VielonwelonrMutelonsRelontwelonelontsFromAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrMutelonsRelontwelonelontsFromAuthorFelonaturelon)

  caselon objelonct ConvelonrsationRootAuthorDoelonsFollowVielonwelonr
      elonxtelonnds BoolelonanFelonaturelonCondition(ConvelonrsationRootAuthorFollowsVielonwelonr)

  caselon objelonct VielonwelonrDoelonsFollowConvelonrsationRootAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrFollowsConvelonrsationRootAuthor)

  caselon objelonct TwelonelontIsCommunityTwelonelont elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsCommunityTwelonelontFelonaturelon)

  caselon objelonct NotificationIsOnCommunityTwelonelont
      elonxtelonnds BoolelonanFelonaturelonCondition(NotificationIsOnCommunityTwelonelontFelonaturelon)

  selonalelond trait CommunityTwelonelontCommunityUnavailablelon elonxtelonnds Condition

  caselon objelonct CommunityTwelonelontCommunityNotFound
      elonxtelonnds BoolelonanFelonaturelonCondition(CommunityTwelonelontCommunityNotFoundFelonaturelon)
      with CommunityTwelonelontCommunityUnavailablelon

  caselon objelonct CommunityTwelonelontCommunityDelonlelontelond
      elonxtelonnds BoolelonanFelonaturelonCondition(CommunityTwelonelontCommunityDelonlelontelondFelonaturelon)
      with CommunityTwelonelontCommunityUnavailablelon

  caselon objelonct CommunityTwelonelontCommunitySuspelonndelond
      elonxtelonnds BoolelonanFelonaturelonCondition(CommunityTwelonelontCommunitySuspelonndelondFelonaturelon)
      with CommunityTwelonelontCommunityUnavailablelon

  caselon objelonct CommunityTwelonelontCommunityVisiblelon
      elonxtelonnds BoolelonanFelonaturelonCondition(CommunityTwelonelontCommunityVisiblelonFelonaturelon)

  caselon objelonct VielonwelonrIsIntelonrnalCommunitielonsAdmin
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsIntelonrnalCommunitielonsAdminFelonaturelon)

  caselon objelonct VielonwelonrIsCommunityAdmin elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsCommunityAdminFelonaturelon)

  caselon objelonct VielonwelonrIsCommunityModelonrator
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsCommunityModelonratorFelonaturelon)

  caselon objelonct VielonwelonrIsCommunityMelonmbelonr
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsCommunityMelonmbelonrFelonaturelon)

  selonalelond trait CommunityTwelonelontIsModelonratelond elonxtelonnds Condition

  caselon objelonct CommunityTwelonelontIsHiddelonn
      elonxtelonnds BoolelonanFelonaturelonCondition(CommunityTwelonelontIsHiddelonnFelonaturelon)
      with CommunityTwelonelontIsModelonratelond

  caselon objelonct CommunityTwelonelontAuthorIsRelonmovelond
      elonxtelonnds BoolelonanFelonaturelonCondition(CommunityTwelonelontAuthorIsRelonmovelondFelonaturelon)
      with CommunityTwelonelontIsModelonratelond

  caselon objelonct DoelonsHavelonInnelonrCirclelonOfFrielonndsRelonlationship
      elonxtelonnds BoolelonanFelonaturelonCondition(HasInnelonrCirclelonOfFrielonndsRelonlationship)

  caselon objelonct TwelonelontIsCommunityConvelonrsation
      elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontHasCommunityConvelonrsationControl)

  caselon objelonct TwelonelontIsByInvitationConvelonrsation
      elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontHasByInvitationConvelonrsationControl)

  caselon objelonct TwelonelontIsFollowelonrsConvelonrsation
      elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontHasFollowelonrsConvelonrsationControl)

  caselon objelonct VielonwelonrIsTwelonelontConvelonrsationRootAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontConvelonrsationVielonwelonrIsRootAuthor)

  privatelon caselon objelonct VielonwelonrIsInvitelondToTwelonelontConvelonrsationByMelonntion
      elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontConvelonrsationVielonwelonrIsInvitelond)

  privatelon caselon objelonct VielonwelonrIsInvitelondToTwelonelontConvelonrsationByRelonplyMelonntion
      elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontConvelonrsationVielonwelonrIsInvitelondViaRelonplyMelonntion)

  objelonct VielonwelonrIsInvitelondToTwelonelontConvelonrsation
      elonxtelonnds Or(
        VielonwelonrIsInvitelondToTwelonelontConvelonrsationByMelonntion,
        VielonwelonrIsInvitelondToTwelonelontConvelonrsationByRelonplyMelonntion)

  objelonct TwelonelontIselonxclusivelonContelonnt elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIselonxclusivelonTwelonelont)
  objelonct VielonwelonrIselonxclusivelonTwelonelontAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIselonxclusivelonTwelonelontRootAuthor)
  objelonct VielonwelonrSupelonrFollowselonxclusivelonTwelonelontAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrSupelonrFollowselonxclusivelonTwelonelontRootAuthor)

  objelonct TwelonelontIsTrustelondFrielonndsContelonnt elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsTrustelondFrielonndTwelonelont)
  objelonct VielonwelonrIsTrustelondFrielonndsTwelonelontAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsTrustelondFrielonndTwelonelontAuthor)
  objelonct VielonwelonrIsTrustelondFrielonnd elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsTrustelondFrielonndOfTwelonelontAuthor)

  objelonct TwelonelontIsCollabInvitationContelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsCollabInvitationTwelonelont)

  caselon class TwelonelontHasLabelonlForPelonrspelonctivalUselonr(safelontyLabelonl: TwelonelontSafelontyLabelonlTypelon)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {
    ovelonrridelon lazy val namelon: String = s"TwelonelontHasLabelonlForPelonrspelonctivalUselonr(${safelontyLabelonl.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrId)
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonl)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(
      FoundTwelonelontLabelonlForPelonrspelonctivalUselonr(safelontyLabelonl)
    )

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      if (!felonaturelonMap.contains(VielonwelonrId)) {
        UnsatisfielondRelonsult
      } elonlselon {
        val vielonwelonrId = felonaturelonMap(VielonwelonrId).asInstancelonOf[Long]
        val labelonls = felonaturelonMap(TwelonelontSafelontyLabelonls).asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
        labelonls
          .collelonctFirst {
            caselon labelonl
                if labelonl.labelonlTypelon == safelontyLabelonl && labelonl.applicablelonUselonrs.contains(vielonwelonrId)
                  && elonxpelonrimelonntBaselon.shouldFiltelonrForSourcelon(elonvaluationContelonxt.params, labelonl.sourcelon) =>
              SatisfielondRelonsult
          }.gelontOrelonlselon(UnsatisfielondRelonsult)
      }
    }
  }

  caselon class TwelonelontHasLabelonl(
    safelontyLabelonl: TwelonelontSafelontyLabelonlTypelon,
    labelonlSourcelonelonxpelonrimelonntPrelondicatelon: Option[(Params, Option[LabelonlSourcelon]) => Boolelonan] = Nonelon)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {
    ovelonrridelon lazy val namelon: String = s"TwelonelontHasLabelonl(${safelontyLabelonl.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonl)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(FoundTwelonelontLabelonl(safelontyLabelonl))

    privatelon val labelonlSourcelonPrelondicatelon: (Params, Option[LabelonlSourcelon]) => Boolelonan =
      labelonlSourcelonelonxpelonrimelonntPrelondicatelon match {
        caselon Somelon(prelondicatelon) => prelondicatelon
        caselon _ => elonxpelonrimelonntBaselon.shouldFiltelonrForSourcelon
      }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(TwelonelontSafelontyLabelonls).asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.labelonlTypelon == safelontyLabelonl
                && labelonlSourcelonPrelondicatelon(elonvaluationContelonxt.params, labelonl.sourcelon) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class SpacelonHasLabelonl(
    safelontyLabelonlTypelon: SpacelonSafelontyLabelonlTypelon)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {
    ovelonrridelon lazy val namelon: String = s"SpacelonHasLabelonl(${safelontyLabelonlTypelon.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(SpacelonSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonlTypelon)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(FoundSpacelonLabelonl(safelontyLabelonlTypelon))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(SpacelonSafelontyLabelonls).asInstancelonOf[Selonq[SpacelonSafelontyLabelonl]]
      labelonls
        .collelonctFirst {
          caselon labelonl if labelonl.safelontyLabelonlTypelon == safelontyLabelonlTypelon =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class MelondiaHasLabelonl(
    safelontyLabelonlTypelon: MelondiaSafelontyLabelonlTypelon)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {
    ovelonrridelon lazy val namelon: String = s"MelondiaHasLabelonl(${safelontyLabelonlTypelon.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(MelondiaSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonlTypelon)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(FoundMelondiaLabelonl(safelontyLabelonlTypelon))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(MelondiaSafelontyLabelonls).asInstancelonOf[Selonq[MelondiaSafelontyLabelonl]]
      labelonls
        .collelonctFirst {
          caselon labelonl if labelonl.safelontyLabelonlTypelon == safelontyLabelonlTypelon =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class TwelonelontHasLabelonlWithLanguagelonScorelonAbovelonThrelonshold(
    safelontyLabelonl: TwelonelontSafelontyLabelonlTypelon,
    languagelonsToScorelonThrelonsholds: Map[String, Doublelon])
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {

    ovelonrridelon lazy val namelon: String =
      s"TwelonelontHasLabelonlWithLanguagelonScorelonAbovelonThrelonshold(${safelontyLabelonl.namelon}, ${languagelonsToScorelonThrelonsholds.toString})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonl)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond =
      Satisfielond(
        FoundTwelonelontLabelonlWithLanguagelonScorelonAbovelonThrelonshold(safelontyLabelonl, languagelonsToScorelonThrelonsholds))

    privatelon[this] delonf isAbovelonThrelonshold(labelonl: TwelonelontSafelontyLabelonl) = {
      val isAbovelonThrelonsholdOpt = for {
        modelonlMelontadata <- labelonl.modelonlMelontadata
        calibratelondLanguagelon <- modelonlMelontadata.calibratelondLanguagelon
        threlonshold <- languagelonsToScorelonThrelonsholds.gelont(calibratelondLanguagelon)
        scorelon <- labelonl.scorelon
      } yielonld scorelon >= threlonshold

      isAbovelonThrelonsholdOpt.gelontOrelonlselon(falselon)
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(TwelonelontSafelontyLabelonls).asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.labelonlTypelon == safelontyLabelonl
                && isAbovelonThrelonshold(labelonl) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class TwelonelontHasLabelonlWithScorelonAbovelonThrelonshold(
    safelontyLabelonl: TwelonelontSafelontyLabelonlTypelon,
    threlonshold: Doublelon)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {

    ovelonrridelon lazy val namelon: String =
      s"TwelonelontHasLabelonlWithScorelonAbovelonThrelonshold(${safelontyLabelonl.namelon}, $threlonshold)"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonl)

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult =
      Satisfielond(FoundTwelonelontLabelonlWithScorelonAbovelonThrelonshold(safelontyLabelonl, threlonshold))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(TwelonelontSafelontyLabelonls).asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.labelonlTypelon == safelontyLabelonl
                && labelonl.scorelon.elonxists(_ >= threlonshold) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class TwelonelontHasLabelonlWithScorelonAbovelonThrelonsholdWithParam(
    safelontyLabelonl: TwelonelontSafelontyLabelonlTypelon,
    threlonsholdParam: Param[Doublelon])
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon
      with HasParams {
    ovelonrridelon lazy val namelon: String =
      s"TwelonelontHasLabelonlWithScorelonAbovelonThrelonshold(${safelontyLabelonl.namelon}, ${NamingUtils.gelontFrielonndlyNamelon(threlonsholdParam)})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonl)
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)
    ovelonrridelon val params: Selont[Param[_]] = Selont(threlonsholdParam)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(TwelonelontSafelontyLabelonls).asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
      val threlonshold = elonvaluationContelonxt.params(threlonsholdParam)
      val SatisfielondRelonsult =
        Satisfielond(FoundTwelonelontLabelonlWithScorelonAbovelonThrelonshold(safelontyLabelonl, threlonshold))
      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.labelonlTypelon == safelontyLabelonl
                && labelonl.scorelon.elonxists(_ >= threlonshold) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class TwelonelontHasLabelonlWithLanguagelonIn(
    safelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon,
    languagelons: Selont[String])
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {

    ovelonrridelon lazy val namelon: String =
      s"TwelonelontHasLabelonlWithLanguagelonIn($safelontyLabelonlTypelon, $languagelons)"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonlTypelon)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond =
      Satisfielond(FoundTwelonelontLabelonlWithLanguagelonIn(safelontyLabelonlTypelon, languagelons))

    privatelon[this] delonf hasLanguagelonMatch(labelonl: TwelonelontSafelontyLabelonl): Boolelonan = {
      val isMatchingLanguagelonOpt = for {
        melontadata <- labelonl.modelonlMelontadata
        languagelon <- melontadata.calibratelondLanguagelon
      } yielonld languagelons.contains(languagelon)
      isMatchingLanguagelonOpt.gelontOrelonlselon(falselon)
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      felonaturelonMap(TwelonelontSafelontyLabelonls)
        .asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
        .collelonctFirst {
          caselon labelonl if labelonl.labelonlTypelon == safelontyLabelonlTypelon && hasLanguagelonMatch(labelonl) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class TwelonelontHasLabelonlWithLanguagelonsWithParam(
    safelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon,
    languagelonParam: Param[Selonq[String]])
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon
      with HasParams {
    ovelonrridelon lazy val namelon: String =
      s"TwelonelontHasLabelonlWithLanguagelonIn($safelontyLabelonlTypelon, ${NamingUtils.gelontFrielonndlyNamelon(languagelonParam)})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonlTypelon)
    ovelonrridelon val params: Selont[Param[_]] = Selont(languagelonParam)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)

    privatelon[this] delonf hasLanguagelonMatch(labelonl: TwelonelontSafelontyLabelonl, languagelons: Selont[String]): Boolelonan = {
      val isMatchingLanguagelonOpt = for {
        melontadata <- labelonl.modelonlMelontadata
        languagelon <- melontadata.calibratelondLanguagelon
      } yielonld languagelons.contains(languagelon)
      isMatchingLanguagelonOpt.gelontOrelonlselon(falselon)
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val languagelons = elonvaluationContelonxt.params(languagelonParam).toSelont
      val SatisfielondRelonsult: Satisfielond =
        Satisfielond(FoundTwelonelontLabelonlWithLanguagelonIn(safelontyLabelonlTypelon, languagelons))
      felonaturelonMap(TwelonelontSafelontyLabelonls)
        .asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
        .collelonctFirst {
          caselon labelonl if labelonl.labelonlTypelon == safelontyLabelonlTypelon && hasLanguagelonMatch(labelonl, languagelons) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  typelon TwelonelontSafelontyLabelonlPrelondicatelonFn = (TwelonelontSafelontyLabelonl) => Boolelonan
  abstract class NamelondTwelonelontSafelontyLabelonlPrelondicatelon(
    privatelon[rulelons] val fn: TwelonelontSafelontyLabelonlPrelondicatelonFn,
    privatelon[rulelons] val namelon: String)

  abstract class TwelonelontHasSafelontyLabelonlWithPrelondicatelon(
    privatelon[rulelons] val safelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon,
    privatelon[rulelons] val prelondicatelon: NamelondTwelonelontSafelontyLabelonlPrelondicatelon)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {

    ovelonrridelon lazy val namelon: String =
      s"TwelonelontHasSafelontyLabelonlWithPrelondicatelon(${prelondicatelon.namelon}($safelontyLabelonlTypelon))"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonlTypelon)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond =
      Satisfielond(Relonsult.FoundTwelonelontSafelontyLabelonlWithPrelondicatelon(safelontyLabelonlTypelon, prelondicatelon.namelon))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      felonaturelonMap(TwelonelontSafelontyLabelonls)
        .asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
        .collelonctFirst {
          caselon labelonl if labelonl.labelonlTypelon == safelontyLabelonlTypelon && prelondicatelon.fn(labelonl) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  objelonct TwelonelontHasSafelontyLabelonlWithPrelondicatelon {
    delonf unapply(
      condition: TwelonelontHasSafelontyLabelonlWithPrelondicatelon
    ): Option[(TwelonelontSafelontyLabelonlTypelon, NamelondTwelonelontSafelontyLabelonlPrelondicatelon)] =
      Somelon((condition.safelontyLabelonlTypelon, condition.prelondicatelon))
  }

  caselon class WithScorelonelonqInt(scorelon: Int)
      elonxtelonnds NamelondTwelonelontSafelontyLabelonlPrelondicatelon(
        fn = twelonelontSafelontyLabelonl => twelonelontSafelontyLabelonl.scorelon.elonxists(s => s.intValuelon() == scorelon),
        namelon = "WithScorelonelonqInt"
      )
  caselon class TwelonelontHasSafelontyLabelonlWithScorelonelonqInt(
    ovelonrridelon val safelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon,
    scorelon: Int)
      elonxtelonnds TwelonelontHasSafelontyLabelonlWithPrelondicatelon(
        safelontyLabelonlTypelon,
        prelondicatelon = WithScorelonelonqInt(scorelon)
      )

  caselon class TwelonelontRelonplyToParelonntTwelonelontBelonforelonDuration(duration: Duration) elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontParelonntId, TwelonelontTimelonstamp)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(
      Relonsult.IsTwelonelontRelonplyToParelonntTwelonelontBelonforelonDuration(duration))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      felonaturelonMap
        .gelont(TwelonelontParelonntId).collelonct {
          caselon twelonelontParelonntId: Long =>
            felonaturelonMap
              .gelont(TwelonelontTimelonstamp).collelonct {
                caselon twelonelontTimelonstamp: Timelon
                    if twelonelontTimelonstamp.diff(SnowflakelonId.timelonFromId(twelonelontParelonntId)) < duration =>
                  SatisfielondRelonsult
              }.gelontOrelonlselon(UnsatisfielondRelonsult)
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class TwelonelontRelonplyToRootTwelonelontBelonforelonDuration(duration: Duration) elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontConvelonrsationId, TwelonelontTimelonstamp)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(
      Relonsult.IsTwelonelontRelonplyToRootTwelonelontBelonforelonDuration(duration))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      felonaturelonMap
        .gelont(TwelonelontConvelonrsationId).collelonct {
          caselon twelonelontConvelonrsationId: Long =>
            felonaturelonMap
              .gelont(TwelonelontTimelonstamp).collelonct {
                caselon twelonelontTimelonstamp: Timelon
                    if twelonelontTimelonstamp.diff(
                      SnowflakelonId.timelonFromId(twelonelontConvelonrsationId)) < duration =>
                  SatisfielondRelonsult
              }.gelontOrelonlselon(UnsatisfielondRelonsult)
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class TwelonelontComposelondBelonforelon(cutoffTimelonstamp: Timelon) elonxtelonnds Condition {
    asselonrt(cutoffTimelonstamp.inMilliselonconds > SnowflakelonId.FirstSnowflakelonIdUnixTimelon)

    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontTimelonstamp)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(HasTwelonelontTimelonstampBelonforelonCutoff)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      felonaturelonMap(TwelonelontTimelonstamp) match {
        caselon timelonstamp: Timelon if timelonstamp > cutoffTimelonstamp => UnsatisfielondRelonsult
        caselon _ => SatisfielondRelonsult
      }
    }
  }

  caselon class TwelonelontComposelondAftelonr(cutoffTimelonstamp: Timelon) elonxtelonnds Condition {
    asselonrt(cutoffTimelonstamp.inMilliselonconds > SnowflakelonId.FirstSnowflakelonIdUnixTimelon)

    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontTimelonstamp)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(HasTwelonelontTimelonstampAftelonrCutoff)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      felonaturelonMap(TwelonelontTimelonstamp) match {
        caselon timelonstamp: Timelon if timelonstamp > cutoffTimelonstamp => SatisfielondRelonsult
        caselon _ => UnsatisfielondRelonsult
      }
    }
  }

  caselon class TwelonelontComposelondAftelonrOffselont(offselont: Duration) elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontTimelonstamp)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(HasTwelonelontTimelonstampAftelonrOffselont)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      felonaturelonMap(TwelonelontTimelonstamp) match {
        caselon timelonstamp: Timelon if timelonstamp > Timelon.now.minus(offselont) => SatisfielondRelonsult
        caselon _ => UnsatisfielondRelonsult
      }
    }
  }

  caselon class TwelonelontComposelondAftelonrWithParam(cutoffTimelonParam: Param[Timelon])
      elonxtelonnds Condition
      with HasParams {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontTimelonstamp)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val params: Selont[Param[_]] = Selont(cutoffTimelonParam)
    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(HasTwelonelontTimelonstampAftelonrCutoff)

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult = {
      val cutoffTimelonstamp = elonvaluationContelonxt.params(cutoffTimelonParam)
      if (cutoffTimelonstamp.inMilliselonconds < SnowflakelonId.FirstSnowflakelonIdUnixTimelon) {
        Filtelonrelond
      } elonlselon {
        supelonr.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap)
      }
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val cutoffTimelonstamp = elonvaluationContelonxt.params(cutoffTimelonParam)
      felonaturelonMap(TwelonelontTimelonstamp) match {
        caselon _: Timelon if cutoffTimelonstamp.inMilliselonconds < SnowflakelonId.FirstSnowflakelonIdUnixTimelon =>
          UnsatisfielondRelonsult
        caselon timelonstamp: Timelon if timelonstamp > cutoffTimelonstamp => SatisfielondRelonsult
        caselon _ => UnsatisfielondRelonsult
      }
    }
  }

  caselon class AuthorHasLabelonl(labelonlValuelon: UselonrLabelonlValuelon, shortCircuitablelon: Boolelonan = truelon)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {
    ovelonrridelon lazy val namelon: String = s"AuthorHasLabelonl(${labelonlValuelon.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(AuthorUselonrLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(labelonlValuelon)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(FoundUselonrLabelonl(labelonlValuelon))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(AuthorUselonrLabelonls).asInstancelonOf[Selonq[Labelonl]].map(UselonrLabelonl.fromThrift)
      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.labelonlValuelon == labelonlValuelon
                && elonxpelonrimelonntBaselon.shouldFiltelonrForSourcelon(elonvaluationContelonxt.params, labelonl.sourcelon) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  abstract class VielonwelonrHasRolelon(rolelon: String) elonxtelonnds Condition {
    ovelonrridelon lazy val namelon: String = s"VielonwelonrHasRolelon(${rolelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrRolelons)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(FoundUselonrRolelon(rolelon))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val rolelons = felonaturelonMap(VielonwelonrRolelons).asInstancelonOf[Selonq[String]]
      if (rolelons.contains(rolelon)) {
        SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct VielonwelonrIselonmployelonelon elonxtelonnds VielonwelonrHasRolelon(VielonwelonrRolelons.elonmployelonelonRolelon)

  caselon class VielonwelonrHasLabelonl(labelonlValuelon: UselonrLabelonlValuelon) elonxtelonnds Condition with HasSafelontyLabelonlTypelon {
    ovelonrridelon lazy val namelon: String = s"VielonwelonrHasLabelonl(${labelonlValuelon.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrUselonrLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(labelonlValuelon)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(FoundUselonrLabelonl(labelonlValuelon))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(VielonwelonrUselonrLabelonls).asInstancelonOf[Selonq[Labelonl]].map(UselonrLabelonl.fromThrift)
      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.labelonlValuelon == labelonlValuelon
                && elonxpelonrimelonntBaselon.shouldFiltelonrForSourcelon(elonvaluationContelonxt.params, labelonl.sourcelon) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon objelonct DelonactivatelondAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIsDelonactivatelond)
  caselon objelonct elonraselondAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIselonraselond)
  caselon objelonct OffboardelondAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIsOffboardelond)
  caselon objelonct ProtelonctelondAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIsProtelonctelond)
  caselon objelonct VelonrifielondAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIsVelonrifielond)
  caselon objelonct NsfwUselonrAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIsNsfwUselonr)
  caselon objelonct NsfwAdminAuthor elonxtelonnds BoolelonanFelonaturelonCondition(AuthorIsNsfwAdmin)
  caselon objelonct TwelonelontHasNsfwUselonrAuthor elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontHasNsfwUselonr)
  caselon objelonct TwelonelontHasNsfwAdminAuthor elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontHasNsfwAdmin)
  caselon objelonct TwelonelontHasMelondia elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontHasMelondiaFelonaturelon)
  caselon objelonct TwelonelontHasDmcaMelondia elonxtelonnds BoolelonanFelonaturelonCondition(HasDmcaMelondiaFelonaturelon)
  caselon objelonct TwelonelontHasCard elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontHasCardFelonaturelon)
  caselon objelonct IsPollCard elonxtelonnds BoolelonanFelonaturelonCondition(CardIsPoll)

  caselon objelonct ProtelonctelondVielonwelonr elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsProtelonctelond)
  caselon objelonct SoftVielonwelonr elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrIsSoftUselonr)

  caselon objelonct VielonwelonrHasUqfelonnablelond
      elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrHasUnivelonrsalQualityFiltelonrelonnablelond)

  abstract class VielonwelonrHasMatchingKelonywordFor(mutelonSurfacelon: MutelonSurfacelon) elonxtelonnds Condition {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont(felonaturelon)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    privatelon val felonaturelon: Felonaturelon[MutelondKelonyword] = mutelonSurfacelon match {
      caselon MutelonSurfacelon.HomelonTimelonlinelon => VielonwelonrMutelonsKelonywordInTwelonelontForHomelonTimelonlinelon
      caselon MutelonSurfacelon.Notifications => VielonwelonrMutelonsKelonywordInTwelonelontForNotifications
      caselon MutelonSurfacelon.TwelonelontRelonplielons => VielonwelonrMutelonsKelonywordInTwelonelontForTwelonelontRelonplielons

      caselon _ => throw nelonw NoSuchelonlelonmelonntelonxcelonption(mutelonSurfacelon.toString)
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val mutelondKelonyword = felonaturelonMap(felonaturelon)
        .asInstancelonOf[MutelondKelonyword]
      Relonsult.fromMutelondKelonyword(mutelondKelonyword, UnsatisfielondRelonsult)
    }
  }

  caselon objelonct VielonwelonrHasMatchingKelonywordForHomelonTimelonlinelon
      elonxtelonnds VielonwelonrHasMatchingKelonywordFor(MutelonSurfacelon.HomelonTimelonlinelon)

  caselon objelonct VielonwelonrHasMatchingKelonywordForNotifications
      elonxtelonnds VielonwelonrHasMatchingKelonywordFor(MutelonSurfacelon.Notifications)

  caselon objelonct VielonwelonrHasMatchingKelonywordForTwelonelontRelonplielons
      elonxtelonnds VielonwelonrHasMatchingKelonywordFor(MutelonSurfacelon.TwelonelontRelonplielons)

  caselon objelonct VielonwelonrHasMatchingKelonywordForAllSurfacelons elonxtelonnds Condition {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrMutelonsKelonywordInTwelonelontForAllSurfacelons)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val mutelondKelonyword = felonaturelonMap(VielonwelonrMutelonsKelonywordInTwelonelontForAllSurfacelons)
        .asInstancelonOf[MutelondKelonyword]
      Relonsult.fromMutelondKelonyword(mutelondKelonyword, UnsatisfielondRelonsult)
    }
  }

  abstract class VielonwelonrHasMatchingKelonywordInSpacelonTitlelonFor(mutelonSurfacelon: MutelonSurfacelon)
      elonxtelonnds Condition {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont(felonaturelon)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    privatelon val felonaturelon: Felonaturelon[MutelondKelonyword] = mutelonSurfacelon match {
      caselon MutelonSurfacelon.Notifications => VielonwelonrMutelonsKelonywordInSpacelonTitlelonForNotifications
      caselon _ => throw nelonw NoSuchelonlelonmelonntelonxcelonption(mutelonSurfacelon.toString)
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val mutelondKelonyword = felonaturelonMap(felonaturelon)
        .asInstancelonOf[MutelondKelonyword]
      Relonsult.fromMutelondKelonyword(mutelondKelonyword, UnsatisfielondRelonsult)
    }
  }

  caselon objelonct VielonwelonrHasMatchingKelonywordInSpacelonTitlelonForNotifications
      elonxtelonnds VielonwelonrHasMatchingKelonywordInSpacelonTitlelonFor(MutelonSurfacelon.Notifications)

  caselon objelonct VielonwelonrFiltelonrsNoConfirmelondelonmail
      elonxtelonnds BoolelonanFelonaturelonCondition(
        com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsNoConfirmelondelonmail
      )

  caselon objelonct VielonwelonrFiltelonrsNoConfirmelondPhonelon
      elonxtelonnds BoolelonanFelonaturelonCondition(
        com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsNoConfirmelondPhonelon
      )

  caselon objelonct VielonwelonrFiltelonrsDelonfaultProfilelonImagelon
      elonxtelonnds BoolelonanFelonaturelonCondition(
        com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsDelonfaultProfilelonImagelon
      )

  caselon objelonct VielonwelonrFiltelonrsNelonwUselonrs
      elonxtelonnds BoolelonanFelonaturelonCondition(
        com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsNelonwUselonrs
      )

  caselon objelonct VielonwelonrFiltelonrsNotFollowelondBy
      elonxtelonnds BoolelonanFelonaturelonCondition(
        com.twittelonr.visibility.felonaturelons.VielonwelonrFiltelonrsNotFollowelondBy
      )

  caselon objelonct AuthorHasConfirmelondelonmail
      elonxtelonnds BoolelonanFelonaturelonCondition(
        com.twittelonr.visibility.felonaturelons.AuthorHasConfirmelondelonmail
      )

  caselon objelonct AuthorHasVelonrifielondPhonelon
      elonxtelonnds BoolelonanFelonaturelonCondition(
        com.twittelonr.visibility.felonaturelons.AuthorHasVelonrifielondPhonelon
      )

  caselon objelonct AuthorHasDelonfaultProfilelonImagelon
      elonxtelonnds BoolelonanFelonaturelonCondition(
        com.twittelonr.visibility.felonaturelons.AuthorHasDelonfaultProfilelonImagelon
      )

  caselon objelonct AuthorIsNelonwAccount elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(AuthorAccountAgelon)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val agelon = felonaturelonMap(AuthorAccountAgelon).asInstancelonOf[Duration]

      if (agelon < 72.hours) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  abstract class VielonwelonrInJurisdiction elonxtelonnds Condition {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(RelonquelonstCountryCodelon, VielonwelonrCountryCodelon)

    protelonctelond val unsatisfielondRelonsult = Unsatisfielond(this)

    protelonctelond caselon class CountryFelonaturelons(
      relonquelonstCountryCodelon: Option[String],
      vielonwelonrCountryCodelon: Option[String])

    delonf gelontCountryFelonaturelons(felonaturelonMap: Map[Felonaturelon[_], _]): CountryFelonaturelons = {
      val relonquelonstCountryCodelonOpt = felonaturelonMap
        .gelont(RelonquelonstCountryCodelon)
        .map(_.asInstancelonOf[String])
      val vielonwelonrCountryCodelonOpt = felonaturelonMap
        .gelont(VielonwelonrCountryCodelon)
        .map(_.asInstancelonOf[String])

      CountryFelonaturelons(relonquelonstCountryCodelonOpt, vielonwelonrCountryCodelonOpt)
    }
  }

  caselon class VielonwelonrInHrcj(jurisdictions: Selont[String]) elonxtelonnds VielonwelonrInJurisdiction {

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult =
      felonaturelonMap
        .gelont(RelonquelonstCountryCodelon)
        .map(_.asInstancelonOf[String])
        .collelonctFirst {
          caselon rcc if jurisdictions.contains(rcc) => NelonelondsFullelonvaluation
        }
        .gelontOrelonlselon(Filtelonrelond)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val countryFelonaturelons = gelontCountryFelonaturelons(felonaturelonMap)

      countryFelonaturelons match {
        caselon CountryFelonaturelons(Somelon(rcc), Somelon(vcc))
            if jurisdictions.contains(rcc) && vcc.elonquals(rcc) =>
          Satisfielond(Relonsult.VielonwelonrInHrcj(rcc))
        caselon _ => unsatisfielondRelonsult
      }
    }
  }

  caselon class VielonwelonrOrRelonquelonstInJurisdiction(elonnablelondCountrielonsParam: Param[Selonq[String]])
      elonxtelonnds VielonwelonrInJurisdiction
      with HasParams
      with PrelonFiltelonrOnOptionalFelonaturelons {

    ovelonrridelon val params: Selont[Param[_]] = Selont(elonnablelondCountrielonsParam)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val countrielons: Selonq[String] =
        elonvaluationContelonxt.params(elonnablelondCountrielonsParam).map(c => c.toLowelonrCaselon)
      val countryFelonaturelons = gelontCountryFelonaturelons(felonaturelonMap)

      val countryCodelonOpt =
        countryFelonaturelons.vielonwelonrCountryCodelon.orelonlselon(countryFelonaturelons.relonquelonstCountryCodelon)

      countryCodelonOpt match {
        caselon Somelon(countryCodelon) if countrielons.contains(countryCodelon) =>
          Satisfielond(Relonsult.VielonwelonrOrRelonquelonstInCountry(countryCodelon))
        caselon _ => unsatisfielondRelonsult
      }
    }
  }

  caselon class VielonwelonrAgelonInYelonarsGtelon(agelonToComparelon: Int, ignorelonelonmptyAgelon: Boolelonan = falselon)
      elonxtelonnds Condition
      with PrelonFiltelonrOnOptionalFelonaturelons {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrAgelon)

    privatelon val unsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      felonaturelonMap
        .gelont(VielonwelonrAgelon)
        .map(_.asInstancelonOf[UselonrAgelon])
        .collelonctFirst {
          caselon UselonrAgelon(Somelon(agelon)) if agelon >= agelonToComparelon =>
            Satisfielond(Relonsult.VielonwelonrAgelonInYelonars(agelon))
          caselon UselonrAgelon(Nonelon) if ignorelonelonmptyAgelon =>
            Satisfielond(Relonsult.NoVielonwelonrAgelon)
        }
        .gelontOrelonlselon(unsatisfielondRelonsult)
  }

  caselon class UndelonragelonVielonwelonr(agelonToComparelon: Int) elonxtelonnds Condition with PrelonFiltelonrOnOptionalFelonaturelons {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrAgelon)

    privatelon val unsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      felonaturelonMap
        .gelont(VielonwelonrAgelon)
        .map(_.asInstancelonOf[UselonrAgelon])
        .collelonctFirst {
          caselon UselonrAgelon(Somelon(agelon)) if agelon < agelonToComparelon => Satisfielond(Relonsult.VielonwelonrAgelonInYelonars(agelon))
        }
        .gelontOrelonlselon(unsatisfielondRelonsult)
  }

  caselon objelonct VielonwelonrMissingAgelon elonxtelonnds Condition with PrelonFiltelonrOnOptionalFelonaturelons {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrAgelon)

    privatelon val unsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      felonaturelonMap
        .gelont(VielonwelonrAgelon)
        .map(_.asInstancelonOf[UselonrAgelon])
        .collelonctFirst {
          caselon UselonrAgelon(Nonelon) => Satisfielond(Relonsult.NoVielonwelonrAgelon)
        }
        .gelontOrelonlselon(unsatisfielondRelonsult)
  }

  caselon objelonct VielonwelonrOptInBlockingOnSelonarch elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrOptInBlocking)
  caselon objelonct VielonwelonrOptInFiltelonringOnSelonarch elonxtelonnds BoolelonanFelonaturelonCondition(VielonwelonrOptInFiltelonring)
  caselon objelonct SelonlfRelonply elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsSelonlfRelonply)
  caselon objelonct Nullcast elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsNullcast)
  caselon objelonct Modelonratelond elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsModelonratelond)
  caselon objelonct Relontwelonelont elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsRelontwelonelont)

  caselon objelonct IsFirstPagelonSelonarchRelonsult elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(SelonarchRelonsultsPagelonNumbelonr)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val selonarchRelonsultsPagelonNumbelonr = felonaturelonMap(SelonarchRelonsultsPagelonNumbelonr).asInstancelonOf[Int]
      if (selonarchRelonsultsPagelonNumbelonr == 1) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct HasSelonarchCandidatelonCountGrelonatelonrThan45 elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(SelonarchCandidatelonCount)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val selonarchCandidatelonCount = felonaturelonMap(SelonarchCandidatelonCount).asInstancelonOf[Int]
      if (selonarchCandidatelonCount > 45) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  abstract class HasSelonarchQuelonrySourcelon(quelonrySourcelonToMatch: ThriftQuelonrySourcelon) elonxtelonnds Condition {
    ovelonrridelon lazy val namelon: String = s"HasSelonarchQuelonrySourcelon(${quelonrySourcelonToMatch})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(SelonarchQuelonrySourcelon)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)
    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(HasQuelonrySourcelon(quelonrySourcelonToMatch))

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val quelonrySourcelon = felonaturelonMap(SelonarchQuelonrySourcelon).asInstancelonOf[ThriftQuelonrySourcelon]
      if (quelonrySourcelonToMatch.elonquals(quelonrySourcelon)) {
        SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct IsTrelonndClickSourcelonSelonarchRelonsult elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(SelonarchQuelonrySourcelon)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    privatelon delonf chelonckQuelonrySourcelon[T](
      felonaturelonMap: Map[Felonaturelon[_], _],
      nonTrelonndSourcelonRelonsult: T,
      trelonndSourcelonRelonsult: T
    ): T = {
      val selonarchRelonsultsPagelonNumbelonr = felonaturelonMap(SelonarchQuelonrySourcelon).asInstancelonOf[ThriftQuelonrySourcelon]
      if (selonarchRelonsultsPagelonNumbelonr == ThriftQuelonrySourcelon.TrelonndClick) {
        trelonndSourcelonRelonsult
      } elonlselon {
        nonTrelonndSourcelonRelonsult
      }
    }

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult =
      chelonckQuelonrySourcelon(felonaturelonMap, Filtelonrelond, NotFiltelonrelond)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      chelonckQuelonrySourcelon(felonaturelonMap, UnsatisfielondRelonsult, Relonsult.SatisfielondRelonsult)
  }
  caselon objelonct IsSelonarchHashtagClick elonxtelonnds HasSelonarchQuelonrySourcelon(ThriftQuelonrySourcelon.HashtagClick)
  caselon objelonct IsSelonarchTrelonndClick elonxtelonnds HasSelonarchQuelonrySourcelon(ThriftQuelonrySourcelon.TrelonndClick)

  caselon objelonct SelonarchQuelonryHasUselonr
      elonxtelonnds BoolelonanFelonaturelonCondition(com.twittelonr.visibility.felonaturelons.SelonarchQuelonryHasUselonr)

  caselon class elonquals[T](felonaturelon: Felonaturelon[T], valuelon: T) elonxtelonnds Condition {

    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont(felonaturelon)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val SatisfielondRelonsult: Relonsult = Satisfielond()
    privatelon val UnsatisfielondRelonsult: Relonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val felonaturelonValuelon = felonaturelonMap(felonaturelon).asInstancelonOf[T]
      if (felonaturelonValuelon.elonquals(valuelon)) SatisfielondRelonsult elonlselon UnsatisfielondRelonsult
    }
  }

  caselon class Felonaturelonelonquals[T](lelonft: Felonaturelon[T], right: Felonaturelon[T]) elonxtelonnds Condition {

    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(lelonft, right)

    privatelon val SatisfielondRelonsult: Relonsult = Satisfielond()
    privatelon val UnsatisfielondRelonsult: Relonsult = Unsatisfielond(this)

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult = {
      if (felonaturelonMap.contains(lelonft) && felonaturelonMap.contains(right)) {
        if (apply(elonvaluationContelonxt, felonaturelonMap).asBoolelonan) {
          NotFiltelonrelond
        } elonlselon {
          Filtelonrelond
        }
      } elonlselon {
        NelonelondsFullelonvaluation
      }
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      if (felonaturelonMap.contains(lelonft) && felonaturelonMap.contains(right)) {
        val lelonftValuelon = felonaturelonMap(lelonft).asInstancelonOf[T]
        val rightValuelon = felonaturelonMap(right).asInstancelonOf[T]
        if (lelonftValuelon.elonquals(rightValuelon)) SatisfielondRelonsult elonlselon UnsatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon class And(ovelonrridelon val conditions: Condition*)
      elonxtelonnds Condition
      with HasNelonstelondConditions
      with HasParams {
    ovelonrridelon lazy val namelon: String = s"(${conditions.map(_.namelon).mkString(" And ")})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = conditions.flatMap(_.felonaturelons).toSelont
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = conditions.flatMap(_.optionalFelonaturelons).toSelont
    ovelonrridelon val params: Selont[Param[_]] =
      conditions.collelonct { caselon p: HasParams => p.params }.flattelonn.toSelont

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult = {
      conditions.foldLelonft(NotFiltelonrelond: PrelonFiltelonrRelonsult) {
        caselon (NotFiltelonrelond, condition) => condition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap)
        caselon (Filtelonrelond, _) => Filtelonrelond
        caselon (NelonelondsFullelonvaluation, condition) => {
          condition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) match {
            caselon Filtelonrelond => Filtelonrelond
            caselon _ => NelonelondsFullelonvaluation
          }
        }
      }
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      conditions.foldLelonft(Relonsult.SatisfielondRelonsult) {
        caselon (relonsult @ Unsatisfielond(_), _) => relonsult
        caselon (Relonsult.SatisfielondRelonsult, condition) => condition.apply(elonvaluationContelonxt, felonaturelonMap)
        caselon (relonsult @ Satisfielond(_), condition) => {
          condition.apply(elonvaluationContelonxt, felonaturelonMap) match {
            caselon r @ Unsatisfielond(_) => r
            caselon _ => relonsult
          }
        }
      }
    }
  }

  caselon class Or(ovelonrridelon val conditions: Condition*)
      elonxtelonnds Condition
      with HasNelonstelondConditions
      with HasParams {
    ovelonrridelon lazy val namelon: String = s"(${conditions.map(_.namelon).mkString(" Or ")})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = conditions.flatMap(_.felonaturelons).toSelont
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = conditions.flatMap(_.optionalFelonaturelons).toSelont
    ovelonrridelon val params: Selont[Param[_]] =
      conditions.collelonct { caselon p: HasParams => p.params }.flattelonn.toSelont

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult = {
      conditions.foldLelonft(Filtelonrelond: PrelonFiltelonrRelonsult) {
        caselon (Filtelonrelond, c) => c.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap)
        caselon (NotFiltelonrelond, _) => NotFiltelonrelond
        caselon (NelonelondsFullelonvaluation, c) => {
          c.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) match {
            caselon NotFiltelonrelond => NotFiltelonrelond
            caselon _ => NelonelondsFullelonvaluation
          }
        }
      }
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val foundSatisfielondCondition =
        conditions.find(_.apply(elonvaluationContelonxt, felonaturelonMap).asBoolelonan)
      if (foundSatisfielondCondition.isDelonfinelond) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon class Not(condition: Condition) elonxtelonnds Condition with HasNelonstelondConditions with HasParams {
    ovelonrridelon lazy val namelon: String = s"Not(${condition.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = condition.felonaturelons
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = condition.optionalFelonaturelons
    ovelonrridelon val conditions: Selonq[Condition] = Selonq(condition)
    ovelonrridelon val params: Selont[Param[_]] =
      conditions.collelonct { caselon p: HasParams => p.params }.flattelonn.toSelont

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult =
      condition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) match {
        caselon Filtelonrelond => NotFiltelonrelond
        caselon NotFiltelonrelond => Filtelonrelond
        caselon _ => NelonelondsFullelonvaluation
      }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      if (condition(elonvaluationContelonxt, felonaturelonMap).asBoolelonan) {
        UnsatisfielondRelonsult
      } elonlselon {
        Relonsult.SatisfielondRelonsult
      }
  }

  val LoggelondOutOrVielonwelonrNotFollowingAuthor: And =
    And(NonAuthorVielonwelonr, Or(LoggelondOutVielonwelonr, Not(VielonwelonrDoelonsFollowAuthor)))

  val LoggelondOutOrVielonwelonrOptInFiltelonring: Or =
    Or(LoggelondOutVielonwelonr, VielonwelonrOptInFiltelonringOnSelonarch)

  val LoggelondInVielonwelonr: Not = Not(LoggelondOutVielonwelonr)

  val OutelonrAuthorNotFollowingAuthor: And =
    And(Not(OutelonrAuthorIsInnelonrAuthor), Not(OutelonrAuthorFollowsAuthor))

  val IsFocalTwelonelont: Felonaturelonelonquals[Long] = Felonaturelonelonquals(TwelonelontId, FocalTwelonelontId)

  val NonHydratingConditions: Selont[Class[_]] = Selont(
    LoggelondOutVielonwelonr,
    NonAuthorVielonwelonr,
    Truelon,
    TwelonelontComposelondAftelonr(Timelon.now),
    TwelonelontComposelondBelonforelon(Timelon.now)
  ).map(_.gelontClass)

  trait HasParams {
    val params: Selont[Param[_]]
  }

  delonf hasLabelonlCondition(condition: Condition, twelonelontSafelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon): Boolelonan =
    condition match {
      caselon lt: HasSafelontyLabelonlTypelon =>
        lt.hasLabelonlTypelon(twelonelontSafelontyLabelonlTypelon)
      caselon _ => falselon
    }

  delonf hasLabelonlCondition(condition: Condition, uselonrLabelonlValuelon: UselonrLabelonlValuelon): Boolelonan =
    condition match {
      caselon lt: HasSafelontyLabelonlTypelon =>
        lt.hasLabelonlTypelon(uselonrLabelonlValuelon)
      caselon _ => falselon
    }

  delonf hasLabelonlCondition(condition: Condition, spacelonSafelontyLabelonlTypelon: SpacelonSafelontyLabelonlTypelon): Boolelonan =
    condition match {
      caselon lt: HasSafelontyLabelonlTypelon =>
        lt.hasLabelonlTypelon(spacelonSafelontyLabelonlTypelon)
      caselon _ => falselon
    }

  delonf hasLabelonlCondition(condition: Condition, melondiaSafelontyLabelonlTypelon: MelondiaSafelontyLabelonlTypelon): Boolelonan =
    condition match {
      caselon lt: HasSafelontyLabelonlTypelon =>
        lt.hasLabelonlTypelon(melondiaSafelontyLabelonlTypelon)
      caselon _ => falselon
    }

  caselon class Chooselon[T](
    conditionMap: Map[T, Condition],
    delonfaultCondition: Condition,
    choicelonParam: Param[T])
      elonxtelonnds Condition
      with HasNelonstelondConditions
      with HasParams {
    ovelonrridelon lazy val namelon: String =
      s"(elonithelonr ${conditionMap.valuelons.map(_.namelon).mkString(", ")} or ${delonfaultCondition.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] =
      conditionMap.valuelons.flatMap(_.felonaturelons).toSelont ++ delonfaultCondition.felonaturelons
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] =
      conditionMap.valuelons.flatMap(_.optionalFelonaturelons).toSelont ++ delonfaultCondition.optionalFelonaturelons
    ovelonrridelon val conditions: Selonq[Condition] = conditionMap.valuelons.toSelonq :+ delonfaultCondition
    ovelonrridelon val params: Selont[Param[_]] =
      conditions.collelonct { caselon p: HasParams => p.params }.flattelonn.toSelont

    privatelon[this] delonf gelontCondition(elonvaluationContelonxt: elonvaluationContelonxt): Condition =
      conditionMap.gelontOrelonlselon(elonvaluationContelonxt.params(choicelonParam), delonfaultCondition)

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult =
      gelontCondition(elonvaluationContelonxt).prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      gelontCondition(elonvaluationContelonxt)(elonvaluationContelonxt, felonaturelonMap)
  }

  caselon class Ifelonlselon(
    branchingCondition: Condition,
    ifTruelonCondition: Condition,
    ifFalselonCondition: Condition)
      elonxtelonnds Condition
      with HasNelonstelondConditions
      with HasParams {
    ovelonrridelon lazy val namelon: String =
      s"(If ${branchingCondition.namelon} Thelonn ${ifTruelonCondition.namelon} elonlselon ${ifFalselonCondition.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] =
      branchingCondition.felonaturelons ++ ifTruelonCondition.felonaturelons ++ ifFalselonCondition.felonaturelons
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] =
      branchingCondition.optionalFelonaturelons ++ ifTruelonCondition.optionalFelonaturelons ++ ifFalselonCondition.optionalFelonaturelons
    ovelonrridelon val conditions: Selonq[Condition] =
      Selonq(branchingCondition, ifTruelonCondition, ifFalselonCondition)
    ovelonrridelon val params: Selont[Param[_]] =
      conditions.collelonct { caselon p: HasParams => p.params }.flattelonn.toSelont

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult =
      branchingCondition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) match {
        caselon Filtelonrelond =>
          ifFalselonCondition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap)
        caselon NotFiltelonrelond =>
          ifTruelonCondition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap)
        caselon _ =>
          NelonelondsFullelonvaluation
      }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult =
      if (branchingCondition(elonvaluationContelonxt, felonaturelonMap).asBoolelonan) {
        ifTruelonCondition(elonvaluationContelonxt, felonaturelonMap)
      } elonlselon {
        ifFalselonCondition(elonvaluationContelonxt, felonaturelonMap)
      }
  }

  caselon class GatelondAltelonrnatelon[T](
    delonfaultCondition: Condition,
    altelonrnatelonConditions: Map[T, Condition],
    buckelontIdelonntifielonrToUselonOnDisagrelonelonmelonntParam: Param[Option[T]])
      elonxtelonnds Condition
      with HasNelonstelondConditions
      with HasParams {

    ovelonrridelon lazy val namelon: String =
      s"(${delonfaultCondition.namelon} or somelontimelons ${altelonrnatelonConditions.valuelons.map(_.namelon).mkString(" or ")})"

    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] =
      delonfaultCondition.felonaturelons ++ altelonrnatelonConditions.valuelons.flatMap(_.felonaturelons)

    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] =
      delonfaultCondition.optionalFelonaturelons ++ altelonrnatelonConditions.valuelons.flatMap(_.optionalFelonaturelons)

    ovelonrridelon val conditions: Selonq[Condition] = Selonq(delonfaultCondition) ++ altelonrnatelonConditions.valuelons

    ovelonrridelon val params: Selont[Param[_]] =
      conditions.collelonct { caselon p: HasParams => p.params }.flattelonn.toSelont

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult =
      if (delonfaultCondition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) == Filtelonrelond &&
        altelonrnatelonConditions.valuelons.forall(_.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) == Filtelonrelond)) {
        Filtelonrelond
      } elonlselon if (delonfaultCondition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) == NotFiltelonrelond &&
        altelonrnatelonConditions.valuelons.forall(
          _.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) == NotFiltelonrelond)) {
        NotFiltelonrelond
      } elonlselon {
        NelonelondsFullelonvaluation
      }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val delonfaultConditionRelonsult: Relonsult = delonfaultCondition(elonvaluationContelonxt, felonaturelonMap)
      val altelonrnatelonConditionRelonsult: Map[T, Relonsult] =
        altelonrnatelonConditions.mapValuelons(_(elonvaluationContelonxt, felonaturelonMap))

      if (altelonrnatelonConditionRelonsult.valuelons.elonxists(_.asBoolelonan != delonfaultConditionRelonsult.asBoolelonan)) {
        elonvaluationContelonxt.params(buckelontIdelonntifielonrToUselonOnDisagrelonelonmelonntParam) match {
          caselon Somelon(buckelont) if altelonrnatelonConditionRelonsult.contains(buckelont) =>
            altelonrnatelonConditionRelonsult(buckelont)
          caselon _ =>
            delonfaultConditionRelonsult
        }
      } elonlselon {
        delonfaultConditionRelonsult
      }
    }
  }

  caselon class elonnumGatelondAltelonrnatelon[elon <: elonnumelonration](
    delonfaultCondition: Condition,
    altelonrnatelonConditions: Map[elon#Valuelon, Condition],
    buckelontIdelonntifielonrToUselonOnDisagrelonelonmelonntParam: elonnumParam[elon])
      elonxtelonnds Condition
      with HasNelonstelondConditions
      with HasParams {

    ovelonrridelon lazy val namelon: String =
      s"(${delonfaultCondition.namelon} or somelontimelons ${altelonrnatelonConditions.valuelons.map(_.namelon).mkString(" or ")})"

    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] =
      delonfaultCondition.felonaturelons ++ altelonrnatelonConditions.valuelons.flatMap(_.felonaturelons)

    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] =
      delonfaultCondition.optionalFelonaturelons ++ altelonrnatelonConditions.valuelons.flatMap(_.optionalFelonaturelons)

    ovelonrridelon val conditions: Selonq[Condition] = Selonq(delonfaultCondition) ++ altelonrnatelonConditions.valuelons

    ovelonrridelon val params: Selont[Param[_]] =
      conditions
        .collelonct {
          caselon p: HasParams => p.params
        }.flattelonn.toSelont + buckelontIdelonntifielonrToUselonOnDisagrelonelonmelonntParam

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult =
      if (delonfaultCondition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) == Filtelonrelond &&
        altelonrnatelonConditions.valuelons.forall(_.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) == Filtelonrelond)) {
        Filtelonrelond
      } elonlselon if (delonfaultCondition.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) == NotFiltelonrelond &&
        altelonrnatelonConditions.valuelons.forall(
          _.prelonFiltelonr(elonvaluationContelonxt, felonaturelonMap) == NotFiltelonrelond)) {
        NotFiltelonrelond
      } elonlselon {
        NelonelondsFullelonvaluation
      }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val delonfaultConditionRelonsult: Relonsult = delonfaultCondition(elonvaluationContelonxt, felonaturelonMap)
      val altelonrnatelonConditionRelonsult: Map[elon#Valuelon, Relonsult] =
        altelonrnatelonConditions.mapValuelons(_(elonvaluationContelonxt, felonaturelonMap))

      if (altelonrnatelonConditionRelonsult.valuelons.elonxists(_.asBoolelonan != delonfaultConditionRelonsult.asBoolelonan)) {
        elonvaluationContelonxt.params(buckelontIdelonntifielonrToUselonOnDisagrelonelonmelonntParam) match {
          caselon buckelont if altelonrnatelonConditionRelonsult.contains(buckelont) =>
            altelonrnatelonConditionRelonsult(buckelont)
          caselon _ =>
            delonfaultConditionRelonsult
        }
      } elonlselon {
        delonfaultConditionRelonsult
      }
    }
  }

  caselon objelonct IsTelonstTwelonelont elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontId)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      if (!felonaturelonMap.contains(TwelonelontId)) {
        UnsatisfielondRelonsult
      } elonlselon {
        Relonsult.SatisfielondRelonsult
      }
    }
  }

  caselon objelonct IsTwelonelontInTwelonelontLelonvelonlStcmHoldback elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontId)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val twelonelontId: Long = felonaturelonMap(TwelonelontId).asInstancelonOf[Long]
      if (StcmTwelonelontHoldback.isTwelonelontInHoldback(twelonelontId)) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct MelondiaRelonstrictelondInVielonwelonrCountry elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] =
      Selont(MelondiaGelonoRelonstrictionsAllowList, MelondiaGelonoRelonstrictionsDelonnyList)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(RelonquelonstCountryCodelon)

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val relonquelonstCountryCodelon = TakelondownRelonasons.normalizelonCountryCodelonOption(
        felonaturelonMap.gelont(RelonquelonstCountryCodelon).asInstancelonOf[Option[String]])
      val allowlistCountryCodelons =
        felonaturelonMap(MelondiaGelonoRelonstrictionsAllowList).asInstancelonOf[Selonq[String]]
      val delonnylistCountryCodelons =
        felonaturelonMap(MelondiaGelonoRelonstrictionsDelonnyList).asInstancelonOf[Selonq[String]]
      if ((allowlistCountryCodelons.nonelonmpty && !allowlistCountryCodelons.contains(relonquelonstCountryCodelon))
        || delonnylistCountryCodelons.contains(relonquelonstCountryCodelon)) {
        Relonsult.SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct OnelonToOnelonDmConvelonrsation
      elonxtelonnds BoolelonanFelonaturelonCondition(DmConvelonrsationIsOnelonToOnelonConvelonrsation)

  caselon objelonct DmConvelonrsationTimelonlinelonIselonmpty
      elonxtelonnds BoolelonanFelonaturelonCondition(DmConvelonrsationHaselonmptyTimelonlinelon)

  caselon objelonct DmConvelonrsationLastRelonadablelonelonvelonntIdIsValid
      elonxtelonnds BoolelonanFelonaturelonCondition(DmConvelonrsationHasValidLastRelonadablelonelonvelonntId)

  caselon objelonct VielonwelonrIsDmConvelonrsationParticipant
      elonxtelonnds BoolelonanFelonaturelonCondition(felonats.VielonwelonrIsDmConvelonrsationParticipant)

  caselon objelonct DmConvelonrsationInfoelonxists
      elonxtelonnds BoolelonanFelonaturelonCondition(felonats.DmConvelonrsationInfoelonxists)

  caselon objelonct DmConvelonrsationTimelonlinelonelonxists
      elonxtelonnds BoolelonanFelonaturelonCondition(felonats.DmConvelonrsationTimelonlinelonelonxists)

  caselon objelonct DmelonvelonntIsBelonforelonLastClelonarelondelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntOccurrelondBelonforelonLastClelonarelondelonvelonnt)

  caselon objelonct DmelonvelonntIsBelonforelonJoinConvelonrsationelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntOccurrelondBelonforelonJoinConvelonrsationelonvelonnt)

  caselon objelonct DmelonvelonntIsDelonlelontelond elonxtelonnds BoolelonanFelonaturelonCondition(felonats.DmelonvelonntIsDelonlelontelond)

  caselon objelonct DmelonvelonntIsHiddelonn elonxtelonnds BoolelonanFelonaturelonCondition(felonats.DmelonvelonntIsHiddelonn)

  caselon objelonct VielonwelonrIsDmelonvelonntInitiatingUselonr
      elonxtelonnds BoolelonanFelonaturelonCondition(felonats.VielonwelonrIsDmelonvelonntInitiatingUselonr)

  caselon objelonct DmelonvelonntInOnelonToOnelonConvelonrsationWithUnavailablelonUselonr
      elonxtelonnds BoolelonanFelonaturelonCondition(felonats.DmelonvelonntInOnelonToOnelonConvelonrsationWithUnavailablelonUselonr)

  caselon objelonct DmelonvelonntInOnelonToOnelonConvelonrsation
      elonxtelonnds BoolelonanFelonaturelonCondition(felonats.DmelonvelonntInOnelonToOnelonConvelonrsation)

  caselon objelonct MelonssagelonCrelonatelonDmelonvelonnt elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntIsMelonssagelonCrelonatelonelonvelonnt)

  caselon objelonct WelonlcomelonMelonssagelonCrelonatelonDmelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntIsWelonlcomelonMelonssagelonCrelonatelonelonvelonnt)

  caselon objelonct LastMelonssagelonRelonadUpdatelonDmelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntIsLastMelonssagelonRelonadUpdatelonelonvelonnt)

  caselon objelonct JoinConvelonrsationDmelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntIsJoinConvelonrsationelonvelonnt)

  caselon objelonct ConvelonrsationCrelonatelonDmelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntIsConvelonrsationCrelonatelonelonvelonnt)

  caselon objelonct TrustConvelonrsationDmelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntIsTrustConvelonrsationelonvelonnt)

  caselon objelonct CsFelonelondbackSubmittelondDmelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntIsCsFelonelondbackSubmittelond)

  caselon objelonct CsFelonelondbackDismisselondDmelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(DmelonvelonntIsCsFelonelondbackDismisselond)

  caselon objelonct PelonrspelonctivalJoinConvelonrsationDmelonvelonnt
      elonxtelonnds BoolelonanFelonaturelonCondition(felonats.DmelonvelonntIsPelonrspelonctivalJoinConvelonrsationelonvelonnt)


  caselon class SpacelonHasLabelonlWithScorelonAbovelonThrelonsholdWithParam(
    spacelonSafelontyLabelonlTypelon: SpacelonSafelontyLabelonlTypelon,
    threlonsholdParam: Param[Doublelon])
      elonxtelonnds Condition
      with HasParams {
    ovelonrridelon lazy val namelon: String =
      s"SpacelonHasLabelonlWithScorelonAbovelonThrelonshold(${spacelonSafelontyLabelonlTypelon.namelon}, ${NamingUtils.gelontFrielonndlyNamelon(threlonsholdParam)})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(SpacelonSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)
    ovelonrridelon val params: Selont[Param[_]] = Selont(threlonsholdParam)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(SpacelonSafelontyLabelonls).asInstancelonOf[Selonq[SpacelonSafelontyLabelonl]]
      val threlonshold = elonvaluationContelonxt.params(threlonsholdParam)
      val SatisfielondRelonsult =
        Satisfielond(FoundSpacelonLabelonlWithScorelonAbovelonThrelonshold(spacelonSafelontyLabelonlTypelon, threlonshold))
      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.safelontyLabelonlTypelon == spacelonSafelontyLabelonlTypelon
                && labelonl.safelontyLabelonl.scorelon.elonxists(_ >= threlonshold) =>
            SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class CardUriHasRootDomain(rootDomainParam: Param[Selonq[String]])
      elonxtelonnds Condition
      with HasParams {
    ovelonrridelon lazy val namelon: String =
      s"CardUriHasRootDomain(${NamingUtils.gelontFrielonndlyNamelon(rootDomainParam)})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(CardUriHost)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty
    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)
    ovelonrridelon val params: Selont[Param[_]] = Selont(rootDomainParam)

    privatelon[this] delonf isHostDomainOrSubdomain(domain: String, host: String): Boolelonan =
      host == domain || host.elonndsWith("." + domain)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val cardUriHost = felonaturelonMap(CardUriHost).asInstancelonOf[String]
      val rootDomains = elonvaluationContelonxt.params(rootDomainParam)

      if (rootDomains.elonxists(isHostDomainOrSubdomain(_, cardUriHost))) {
        Satisfielond(FoundCardUriRootDomain(cardUriHost))
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon class TwelonelontHasViolationOfLelonvelonl(lelonvelonl: ViolationLelonvelonl)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {

    ovelonrridelon lazy val namelon: String = s"twelonelontHasViolationOfLelonvelonl(${lelonvelonl})"

    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)
    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)

    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(FoundTwelonelontViolationOfLelonvelonl(lelonvelonl))

    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] =
      ViolationLelonvelonl.violationLelonvelonlToSafelontyLabelonls
        .gelontOrelonlselon(lelonvelonl, Selont.elonmpty)
        .map(_.asInstancelonOf[SafelontyLabelonlTypelon])

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(TwelonelontSafelontyLabelonls).asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
      if (labelonls.map(ViolationLelonvelonl.fromTwelonelontSafelontyLabelonl).contains(lelonvelonl)) {
        SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct TwelonelontHasViolationOfAnyLelonvelonl elonxtelonnds Condition with HasSafelontyLabelonlTypelon {

    ovelonrridelon lazy val namelon: String = s"twelonelontHasViolationOfAnyLelonvelonl"

    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(TwelonelontSafelontyLabelonls)

    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)

    privatelon val SatisfielondRelonsult: Satisfielond = Satisfielond(FoundTwelonelontViolationOfSomelonLelonvelonl)

    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] =
      ViolationLelonvelonl.violationLelonvelonlToSafelontyLabelonls.valuelons
        .relonducelonLelonft(_ ++ _)
        .map(_.asInstancelonOf[SafelontyLabelonlTypelon])

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(TwelonelontSafelontyLabelonls).asInstancelonOf[Selonq[TwelonelontSafelontyLabelonl]]
      if (labelonls
          .map(ViolationLelonvelonl.fromTwelonelontSafelontyLabelonlOpt).collelonct {
            caselon Somelon(lelonvelonl) => lelonvelonl
          }.nonelonmpty) {
        SatisfielondRelonsult
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  caselon objelonct TwelonelontIselonditTwelonelont elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIselonditTwelonelontFelonaturelon)
  caselon objelonct TwelonelontIsStalelonTwelonelont elonxtelonnds BoolelonanFelonaturelonCondition(TwelonelontIsStalelonTwelonelontFelonaturelon)


  caselon class VielonwelonrHasAdultMelondiaSelonttingLelonvelonl(selonttingLelonvelonlToComparelon: SelonnsitivelonMelondiaSelonttingsLelonvelonl)
      elonxtelonnds Condition {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrSelonnsitivelonMelondiaSelonttings)

    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      felonaturelonMap
        .gelont(VielonwelonrSelonnsitivelonMelondiaSelonttings)
        .map(_.asInstancelonOf[UselonrSelonnsitivelonMelondiaSelonttings])
        .collelonctFirst {
          caselon UselonrSelonnsitivelonMelondiaSelonttings(Somelon(selontting))
              if (selontting.vielonwAdultContelonnt == selonttingLelonvelonlToComparelon) =>
            Relonsult.SatisfielondRelonsult
          caselon UselonrSelonnsitivelonMelondiaSelonttings(Nonelon) => UnsatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class VielonwelonrHasViolelonntMelondiaSelonttingLelonvelonl(selonttingLelonvelonlToComparelon: SelonnsitivelonMelondiaSelonttingsLelonvelonl)
      elonxtelonnds Condition {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrSelonnsitivelonMelondiaSelonttings)

    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {

      felonaturelonMap
        .gelont(VielonwelonrSelonnsitivelonMelondiaSelonttings)
        .map(_.asInstancelonOf[UselonrSelonnsitivelonMelondiaSelonttings])
        .collelonctFirst {
          caselon UselonrSelonnsitivelonMelondiaSelonttings(Somelon(selontting))
              if (selontting.vielonwViolelonntContelonnt == selonttingLelonvelonlToComparelon) =>
            Relonsult.SatisfielondRelonsult
          caselon UselonrSelonnsitivelonMelondiaSelonttings(Nonelon) => UnsatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class VielonwelonrHasOthelonrSelonnsitivelonMelondiaSelonttingLelonvelonl(
    selonttingLelonvelonlToComparelon: SelonnsitivelonMelondiaSelonttingsLelonvelonl)
      elonxtelonnds Condition {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrSelonnsitivelonMelondiaSelonttings)

    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {

      felonaturelonMap
        .gelont(VielonwelonrSelonnsitivelonMelondiaSelonttings)
        .map(_.asInstancelonOf[UselonrSelonnsitivelonMelondiaSelonttings])
        .collelonctFirst {
          caselon UselonrSelonnsitivelonMelondiaSelonttings(Somelon(selontting))
              if (selontting.vielonwOthelonrContelonnt == selonttingLelonvelonlToComparelon) =>
            Relonsult.SatisfielondRelonsult
          caselon UselonrSelonnsitivelonMelondiaSelonttings(Nonelon) => UnsatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  privatelon[rulelons] val ToxrfTwelonelontFiltelonrelondForAuthor =
    elonquals(ToxicRelonplyFiltelonrStatelon, FiltelonrStatelon.FiltelonrelondFromAuthor)

  privatelon[rulelons] caselon objelonct ToxrfVielonwelonrIsConvelonrsationAuthor
      elonxtelonnds BoolelonanFelonaturelonCondition(ToxicRelonplyFiltelonrConvelonrsationAuthorIsVielonwelonr)

  val ToxrfFiltelonrelondFromAuthorVielonwelonr =
    And(LoggelondInVielonwelonr, ToxrfTwelonelontFiltelonrelondForAuthor, ToxrfVielonwelonrIsConvelonrsationAuthor)

  caselon objelonct SelonarchQuelonryMatchelonsScrelonelonnNamelon elonxtelonnds Condition {
    ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    ovelonrridelon delonf optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(RawQuelonry, AuthorScrelonelonnNamelon)

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      if (felonaturelonMap.contains(RawQuelonry) && felonaturelonMap.contains(AuthorScrelonelonnNamelon)) {
        val rawQuelonry = felonaturelonMap(RawQuelonry).asInstancelonOf[String]
        val authorScrelonelonnNamelon = felonaturelonMap(AuthorScrelonelonnNamelon).asInstancelonOf[String]
        if (rawQuelonry.elonqualsIgnorelonCaselon(authorScrelonelonnNamelon)) {
          Relonsult.SatisfielondRelonsult
        } elonlselon {
          UnsatisfielondRelonsult
        }
      } elonlselon {
        UnsatisfielondRelonsult
      }
    }
  }

  objelonct SelonarchQuelonryDoelonsNotMatchScrelonelonnNamelonConditionBuildelonr {
    delonf apply(condition: Condition, rulelonParam: RulelonParam[Boolelonan]): Chooselon[Boolelonan] = {
      Chooselon(
        conditionMap =
          Map(truelon -> And(condition, Not(SelonarchQuelonryMatchelonsScrelonelonnNamelon)), falselon -> condition),
        delonfaultCondition = condition,
        choicelonParam = rulelonParam
      )
    }
  }

  val SelonarchQuelonryDoelonsNotMatchScrelonelonnNamelonDelonfaultTruelonCondition: Chooselon[Boolelonan] =
    SelonarchQuelonryDoelonsNotMatchScrelonelonnNamelonConditionBuildelonr(Condition.Truelon, RulelonParams.Falselon)

  caselon objelonct OptionalNonAuthorVielonwelonr elonxtelonnds Condition {
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont()
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(AuthorId, VielonwelonrId)

    privatelon val UnsatisfielondRelonsult = Unsatisfielond(this)

    ovelonrridelon delonf prelonFiltelonr(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): PrelonFiltelonrRelonsult = {
      NelonelondsFullelonvaluation
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val authorIdsOpt = felonaturelonMap.gelont(AuthorId).asInstancelonOf[Option[Selont[Long]]]
      val vielonwelonrIdOpt = felonaturelonMap.gelont(VielonwelonrId).asInstancelonOf[Option[Long]]

      (for {
        authorIds <- authorIdsOpt
        vielonwelonrId <- vielonwelonrIdOpt
      } yielonld {
        if (authorIds.contains(vielonwelonrId)) UnsatisfielondRelonsult
        elonlselon Relonsult.SatisfielondRelonsult
      }) gelontOrelonlselon {
        Relonsult.SatisfielondRelonsult
      }
    }
  }

  caselon class VielonwelonrLocatelondInApplicablelonCountrielonsOfMelondiaWithholdingLabelonl(
    safelontyLabelonlTypelon: MelondiaSafelontyLabelonlTypelon)
      elonxtelonnds VielonwelonrInJurisdiction
      with HasSafelontyLabelonlTypelon {

    ovelonrridelon lazy val namelon: String =
      s"VielonwelonrLocatelondInApplicablelonCountrielonsOfMelondiaLabelonl(${safelontyLabelonlTypelon.namelon})"
    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(MelondiaSafelontyLabelonls)
    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont(VielonwelonrCountryCodelon, RelonquelonstCountryCodelon)
    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonlTypelon)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)

    privatelon[this] delonf isInApplicablelonCountrielons(
      countryCodelonOpt: Option[String],
      labelonl: SafelontyLabelonl
    ): Boolelonan = {
      val inApplicablelonCountry = (for {
        applicablelonCountrielons <- labelonl.applicablelonCountrielons
        countryCodelon <- countryCodelonOpt
      } yielonld {
        applicablelonCountrielons.contains(countryCodelon)
      }) gelontOrelonlselon (falselon)
      inApplicablelonCountry
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(MelondiaSafelontyLabelonls).asInstancelonOf[Selonq[MelondiaSafelontyLabelonl]]

      val countryFelonaturelons = gelontCountryFelonaturelons(felonaturelonMap)
      val countryCodelonOpt = countryFelonaturelons.relonquelonstCountryCodelon
        .orelonlselon(countryFelonaturelons.vielonwelonrCountryCodelon)

      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.safelontyLabelonlTypelon == safelontyLabelonlTypelon
                &&
                  isInApplicablelonCountrielons(countryCodelonOpt, labelonl.safelontyLabelonl) =>
            Relonsult.SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }
  }

  caselon class MelondiaHasLabelonlWithWorldwidelonWithholding(safelontyLabelonlTypelon: MelondiaSafelontyLabelonlTypelon)
      elonxtelonnds Condition
      with HasSafelontyLabelonlTypelon {

    ovelonrridelon lazy val namelon: String =
      s"MelondiaHasLabelonlWithWorldwidelonWithholding(${safelontyLabelonlTypelon.namelon})"

    ovelonrridelon val felonaturelons: Selont[Felonaturelon[_]] = Selont(MelondiaSafelontyLabelonls)

    ovelonrridelon val optionalFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

    ovelonrridelon val labelonlTypelons: Selont[SafelontyLabelonlTypelon] = Selont(safelontyLabelonlTypelon)

    privatelon val UnsatisfielondRelonsult: Unsatisfielond = Unsatisfielond(this)

    privatelon[this] delonf isWithhelonldWorldwidelon(labelonl: SafelontyLabelonl): Boolelonan = {
      labelonl.applicablelonCountrielons.map(_.contains("xx")).gelontOrelonlselon(falselon)
    }

    ovelonrridelon delonf apply(
      elonvaluationContelonxt: elonvaluationContelonxt,
      felonaturelonMap: Map[Felonaturelon[_], _]
    ): Relonsult = {
      val labelonls = felonaturelonMap(MelondiaSafelontyLabelonls).asInstancelonOf[Selonq[MelondiaSafelontyLabelonl]]

      labelonls
        .collelonctFirst {
          caselon labelonl
              if labelonl.safelontyLabelonlTypelon == safelontyLabelonlTypelon
                && isWithhelonldWorldwidelon(labelonl.safelontyLabelonl) =>
            Relonsult.SatisfielondRelonsult
        }.gelontOrelonlselon(UnsatisfielondRelonsult)
    }

  }
}
