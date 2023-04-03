packagelon com.twittelonr.visibility.intelonrfacelons.convelonrsations

import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.gizmoduck.thriftscala.Uselonr
import com.twittelonr.spam.rtf.thriftscala.FiltelonrelondRelonason
import com.twittelonr.spam.rtf.thriftscala.FiltelonrelondRelonason.UnspeloncifielondRelonason
import com.twittelonr.spam.rtf.thriftscala.SafelontyLelonvelonl
import com.twittelonr.spam.rtf.thriftscala.SafelontyRelonsult
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.relonndelonr.thriftscala.RichTelonxt
import com.twittelonr.timelonlinelons.relonndelonr.thriftscala.TombstonelonDisplayTypelon
import com.twittelonr.timelonlinelons.relonndelonr.thriftscala.TombstonelonInfo
import com.twittelonr.twelonelontypielon.thriftscala.GelontTwelonelontFielonldsRelonsult
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontFielonldsRelonsultFailelond
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontFielonldsRelonsultFiltelonrelond
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontFielonldsRelonsultFound
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontFielonldsRelonsultNotFound
import com.twittelonr.twelonelontypielon.thriftscala.TwelonelontFielonldsRelonsultStatelon
import com.twittelonr.visibility.VisibilityLibrary
import com.twittelonr.visibility.buildelonr.twelonelonts.ModelonrationFelonaturelons
import com.twittelonr.visibility.buildelonr.uselonrs.AuthorFelonaturelons
import com.twittelonr.visibility.buildelonr.uselonrs.RelonlationshipFelonaturelons
import com.twittelonr.visibility.buildelonr.uselonrs.VielonwelonrFelonaturelons
import com.twittelonr.visibility.common.UselonrRelonlationshipSourcelon
import com.twittelonr.visibility.common.UselonrSourcelon
import com.twittelonr.visibility.common.actions.IntelonrstitialRelonason
import com.twittelonr.visibility.common.actions.LimitelondelonngagelonmelonntRelonason
import com.twittelonr.visibility.common.actions.TombstonelonRelonason
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.IntelonrstitialRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.LocalizelondMelonssagelonConvelonrtelonr
import com.twittelonr.visibility.common.actions.convelonrtelonr.scala.TombstonelonRelonasonConvelonrtelonr
import com.twittelonr.visibility.common.filtelonrelond_relonason.FiltelonrelondRelonasonHelonlpelonr
import com.twittelonr.visibility.configapi.configs.VisibilityDeloncidelonrGatelons
import com.twittelonr.visibility.felonaturelons.FocalTwelonelontId
import com.twittelonr.visibility.felonaturelons.TwelonelontId
import com.twittelonr.visibility.modelonls.ContelonntId
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl.Tombstoning
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt
import com.twittelonr.visibility.relonsults.richtelonxt.elonpitaphToRichTelonxt
import com.twittelonr.visibility.relonsults.richtelonxt.LocalizelondMelonssagelonToRichTelonxt
import com.twittelonr.visibility.relonsults.urt.RelonasonToUrtParselonr
import com.twittelonr.visibility.relonsults.urt.SafelontyRelonsultToUrtParselonr
import com.twittelonr.visibility.rulelons._
import com.twittelonr.visibility.{thriftscala => t}

caselon class TombstonelonVisibilityRelonquelonst(
  convelonrsationId: Long,
  focalTwelonelontId: Long,
  twelonelonts: Selonq[(GelontTwelonelontFielonldsRelonsult, Option[SafelontyLelonvelonl])],
  authorMap: Map[
    Long,
    Uselonr
  ],
  modelonratelondTwelonelontIds: Selonq[Long],
  vielonwelonrContelonxt: VielonwelonrContelonxt,
  uselonRichTelonxt: Boolelonan = truelon)

caselon class TombstonelonVisibilityRelonsponselon(twelonelontVelonrdicts: Map[Long, VfTombstonelon])

caselon class TombstonelonVisibilityLibrary(
  visibilityLibrary: VisibilityLibrary,
  statsReloncelonivelonr: StatsReloncelonivelonr,
  deloncidelonr: Deloncidelonr) {

  privatelon caselon class TombstonelonTypelon(
    twelonelontId: Long,
    tombstonelonId: Long,
    action: Action) {

    lazy val isInnelonrTombstonelon: Boolelonan = twelonelontId != tombstonelonId

    lazy val tombstonelonDisplayTypelon: TombstonelonDisplayTypelon = action match {
      caselon _: IntelonrstitialLimitelondelonngagelonmelonnts | _: elonmelonrgelonncyDynamicIntelonrstitial =>
        TombstonelonDisplayTypelon.NonCompliant
      caselon _ => TombstonelonDisplayTypelon.Inlinelon
    }
  }

  val elonn: String = "elonn"
  val Vielonw: String = "Vielonw"
  val relonlationshipFelonaturelons =
    nelonw RelonlationshipFelonaturelons(
      statsReloncelonivelonr)
  val visibilityDeloncidelonrGatelons = VisibilityDeloncidelonrGatelons(deloncidelonr)


  delonf toAction(
    filtelonrelondRelonason: FiltelonrelondRelonason,
    actionStatsReloncelonivelonr: StatsReloncelonivelonr
  ): Option[Action] = {

    val elonnablelonLocalizelondIntelonrstitials =
      visibilityDeloncidelonrGatelons.elonnablelonConvosLocalizelondIntelonrstitial()
    val elonnablelonLelongacyIntelonrstitials =
      visibilityDeloncidelonrGatelons.elonnablelonConvosLelongacyIntelonrstitial()

    val tombstonelonStatsReloncelonivelonr = actionStatsReloncelonivelonr.scopelon("tombstonelon")
    val intelonrstitialLocalStatsReloncelonivelonr =
      actionStatsReloncelonivelonr.scopelon("intelonrstitial").scopelon("localizelond")
    val intelonrstitialLelongacyStatsReloncelonivelonr =
      actionStatsReloncelonivelonr.scopelon("intelonrstitial").scopelon("lelongacy")

    filtelonrelondRelonason match {
      caselon _ if FiltelonrelondRelonasonHelonlpelonr.isTombstonelon(filtelonrelondRelonason) =>
        crelonatelonLocalizelondTombstonelon(filtelonrelondRelonason, tombstonelonStatsReloncelonivelonr) match {
          caselon tombstonelonOpt @ Somelon(LocalizelondTombstonelon(_, _)) => tombstonelonOpt
          caselon _ =>
            crelonatelonTombstonelon(elonpitaph.Unavailablelon, tombstonelonStatsReloncelonivelonr, Somelon("elonmptyTombstonelon"))
        }

      caselon _
          if elonnablelonLocalizelondIntelonrstitials &&
            FiltelonrelondRelonasonHelonlpelonr.isLocalizelondSupprelonsselondRelonasonIntelonrstitial(filtelonrelondRelonason) =>
        FiltelonrelondRelonasonHelonlpelonr.gelontLocalizelondSupprelonsselondRelonasonIntelonrstitial(filtelonrelondRelonason) match {
          caselon Somelon(t.Intelonrstitial(relonasonOpt, Somelon(melonssagelon))) =>
            IntelonrstitialRelonasonConvelonrtelonr.fromThrift(relonasonOpt).map { intelonrstitialRelonason =>
              intelonrstitialLocalStatsReloncelonivelonr.countelonr("intelonrstitial").incr()
              Intelonrstitial(
                Relonason.fromIntelonrstitialRelonason(intelonrstitialRelonason),
                Somelon(LocalizelondMelonssagelonConvelonrtelonr.fromThrift(melonssagelon)))
            }

          caselon _ => Nonelon
        }

      caselon _ if FiltelonrelondRelonasonHelonlpelonr.containNsfwMelondia(filtelonrelondRelonason) =>
        Nonelon

      caselon _ if FiltelonrelondRelonasonHelonlpelonr.possiblyUndelonsirablelon(filtelonrelondRelonason) =>
        Nonelon

      caselon _ if FiltelonrelondRelonasonHelonlpelonr.relonportelondTwelonelont(filtelonrelondRelonason) =>
        filtelonrelondRelonason match {
          caselon FiltelonrelondRelonason.RelonportelondTwelonelont(truelon) =>
            intelonrstitialLelongacyStatsReloncelonivelonr.countelonr("fr_relonportelond").incr()
            Somelon(Intelonrstitial(Relonason.VielonwelonrRelonportelondAuthor))

          caselon FiltelonrelondRelonason.SafelontyRelonsult(safelontyRelonsult: SafelontyRelonsult)
              if elonnablelonLelongacyIntelonrstitials =>
            val safelontyRelonsultRelonportelond = IntelonrstitialRelonasonConvelonrtelonr
              .fromAction(safelontyRelonsult.action).collelonct {
                caselon IntelonrstitialRelonason.VielonwelonrRelonportelondTwelonelont => truelon
                caselon IntelonrstitialRelonason.VielonwelonrRelonportelondAuthor => truelon
              }.gelontOrelonlselon(falselon)

            if (safelontyRelonsultRelonportelond) {
              intelonrstitialLelongacyStatsReloncelonivelonr.countelonr("relonportelond_author").incr()
              Somelon(Intelonrstitial(Relonason.VielonwelonrRelonportelondAuthor))
            } elonlselon Nonelon

          caselon _ => Nonelon
        }

      caselon _ if FiltelonrelondRelonasonHelonlpelonr.twelonelontMatchelonsVielonwelonrMutelondKelonyword(filtelonrelondRelonason) =>
        filtelonrelondRelonason match {
          caselon FiltelonrelondRelonason.TwelonelontMatchelonsVielonwelonrMutelondKelonyword(_) =>
            intelonrstitialLelongacyStatsReloncelonivelonr.countelonr("fr_mutelond_kelonyword").incr()
            Somelon(Intelonrstitial(Relonason.MutelondKelonyword))

          caselon FiltelonrelondRelonason.SafelontyRelonsult(safelontyRelonsult: SafelontyRelonsult)
              if elonnablelonLelongacyIntelonrstitials =>
            val safelontyRelonsultMutelondKelonyword = IntelonrstitialRelonasonConvelonrtelonr
              .fromAction(safelontyRelonsult.action).collelonct {
                caselon _: IntelonrstitialRelonason.MatchelonsMutelondKelonyword => truelon
              }.gelontOrelonlselon(falselon)

            if (safelontyRelonsultMutelondKelonyword) {
              intelonrstitialLelongacyStatsReloncelonivelonr.countelonr("mutelond_kelonyword").incr()
              Somelon(Intelonrstitial(Relonason.MutelondKelonyword))
            } elonlselon Nonelon

          caselon _ => Nonelon
        }

      caselon _ =>
        Nonelon
    }
  }

  delonf toAction(
    tfrs: TwelonelontFielonldsRelonsultStatelon,
    actionStatsReloncelonivelonr: StatsReloncelonivelonr
  ): Option[Action] = {

    val elonnablelonLocalizelondIntelonrstitials = visibilityDeloncidelonrGatelons.elonnablelonConvosLocalizelondIntelonrstitial()
    val elonnablelonLelongacyIntelonrstitials = visibilityDeloncidelonrGatelons.elonnablelonConvosLelongacyIntelonrstitial()

    val tombstonelonStatsReloncelonivelonr = actionStatsReloncelonivelonr.scopelon("tombstonelon")
    val intelonrstitialLocalStatsReloncelonivelonr =
      actionStatsReloncelonivelonr.scopelon("intelonrstitial").scopelon("localizelond")
    val intelonrstitialLelongacyStatsReloncelonivelonr =
      actionStatsReloncelonivelonr.scopelon("intelonrstitial").scopelon("lelongacy")

    tfrs match {

      caselon TwelonelontFielonldsRelonsultStatelon.NotFound(TwelonelontFielonldsRelonsultNotFound(_, _, Somelon(filtelonrelondRelonason)))
          if FiltelonrelondRelonasonHelonlpelonr.isTombstonelon(filtelonrelondRelonason) =>
        crelonatelonLocalizelondTombstonelon(filtelonrelondRelonason, tombstonelonStatsReloncelonivelonr)

      caselon TwelonelontFielonldsRelonsultStatelon.NotFound(tfr: TwelonelontFielonldsRelonsultNotFound) if tfr.delonlelontelond =>
        crelonatelonTombstonelon(elonpitaph.Delonlelontelond, tombstonelonStatsReloncelonivelonr)

      caselon TwelonelontFielonldsRelonsultStatelon.NotFound(_: TwelonelontFielonldsRelonsultNotFound) =>
        crelonatelonTombstonelon(elonpitaph.NotFound, tombstonelonStatsReloncelonivelonr)

      caselon TwelonelontFielonldsRelonsultStatelon.Failelond(TwelonelontFielonldsRelonsultFailelond(_, _, _)) =>
        crelonatelonTombstonelon(elonpitaph.Unavailablelon, tombstonelonStatsReloncelonivelonr, Somelon("failelond"))

      caselon TwelonelontFielonldsRelonsultStatelon.Filtelonrelond(TwelonelontFielonldsRelonsultFiltelonrelond(UnspeloncifielondRelonason(truelon))) =>
        crelonatelonTombstonelon(elonpitaph.Unavailablelon, tombstonelonStatsReloncelonivelonr, Somelon("filtelonrelond"))

      caselon TwelonelontFielonldsRelonsultStatelon.Filtelonrelond(TwelonelontFielonldsRelonsultFiltelonrelond(filtelonrelondRelonason)) =>
        toAction(filtelonrelondRelonason, actionStatsReloncelonivelonr)

      caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(_, _, Somelon(filtelonrelondRelonason)))
          if elonnablelonLocalizelondIntelonrstitials &&
            FiltelonrelondRelonasonHelonlpelonr.isSupprelonsselondRelonasonPublicIntelonrelonstIntelonrstial(filtelonrelondRelonason) =>
        intelonrstitialLocalStatsReloncelonivelonr.countelonr("ipi").incr()
        FiltelonrelondRelonasonHelonlpelonr
          .gelontSafelontyRelonsult(filtelonrelondRelonason)
          .flatMap(_.relonason)
          .flatMap(PublicIntelonrelonst.SafelontyRelonsultRelonasonToRelonason.gelont) match {
          caselon Somelon(safelontyRelonsultRelonason) =>
            FiltelonrelondRelonasonHelonlpelonr
              .gelontSupprelonsselondRelonasonPublicIntelonrelonstIntelonrstial(filtelonrelondRelonason)
              .map(elondi => elondi.localizelondMelonssagelon)
              .map(tlm => LocalizelondMelonssagelonConvelonrtelonr.fromThrift(tlm))
              .map(lm =>
                IntelonrstitialLimitelondelonngagelonmelonnts(
                  safelontyRelonsultRelonason,
                  Somelon(LimitelondelonngagelonmelonntRelonason.NonCompliant),
                  lm))
          caselon _ => Nonelon
        }

      caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(_, _, Somelon(filtelonrelondRelonason)))
          if elonnablelonLelongacyIntelonrstitials &&
            FiltelonrelondRelonasonHelonlpelonr.isSupprelonsselondRelonasonPublicIntelonrelonstIntelonrstial(filtelonrelondRelonason) =>
        intelonrstitialLelongacyStatsReloncelonivelonr.countelonr("ipi").incr()
        FiltelonrelondRelonasonHelonlpelonr
          .gelontSafelontyRelonsult(filtelonrelondRelonason)
          .flatMap(_.relonason)
          .flatMap(PublicIntelonrelonst.SafelontyRelonsultRelonasonToRelonason.gelont)
          .map(IntelonrstitialLimitelondelonngagelonmelonnts(_, Somelon(LimitelondelonngagelonmelonntRelonason.NonCompliant)))

      caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(_, _, Somelon(filtelonrelondRelonason)))
          if elonnablelonLocalizelondIntelonrstitials &&
            FiltelonrelondRelonasonHelonlpelonr.isLocalizelondSupprelonsselondRelonasonelonmelonrgelonncyDynamicIntelonrstitial(
              filtelonrelondRelonason) =>
        intelonrstitialLocalStatsReloncelonivelonr.countelonr("elondi").incr()
        FiltelonrelondRelonasonHelonlpelonr
          .gelontSupprelonsselondRelonasonelonmelonrgelonncyDynamicIntelonrstitial(filtelonrelondRelonason)
          .map(elon =>
            elonmelonrgelonncyDynamicIntelonrstitial(
              elon.copy,
              elon.link,
              LocalizelondMelonssagelonConvelonrtelonr.fromThrift(elon.localizelondMelonssagelon)))

      caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(_, _, Somelon(filtelonrelondRelonason)))
          if elonnablelonLelongacyIntelonrstitials &&
            FiltelonrelondRelonasonHelonlpelonr.isSupprelonsselondRelonasonelonmelonrgelonncyDynamicIntelonrstitial(filtelonrelondRelonason) =>
        intelonrstitialLelongacyStatsReloncelonivelonr.countelonr("elondi").incr()
        FiltelonrelondRelonasonHelonlpelonr
          .gelontSupprelonsselondRelonasonelonmelonrgelonncyDynamicIntelonrstitial(filtelonrelondRelonason)
          .map(elon => elonmelonrgelonncyDynamicIntelonrstitial(elon.copy, elon.link))

      caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(twelonelont, _, _))
          if twelonelont.pelonrspelonctivelon.elonxists(_.relonportelond) =>
        intelonrstitialLelongacyStatsReloncelonivelonr.countelonr("relonportelond").incr()
        Somelon(Intelonrstitial(Relonason.VielonwelonrRelonportelondAuthor))

      caselon TwelonelontFielonldsRelonsultStatelon.Found(
            TwelonelontFielonldsRelonsultFound(_, _, Somelon(UnspeloncifielondRelonason(truelon)))) =>
        Nonelon

      caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(_, _, Somelon(filtelonrelondRelonason))) =>
        toAction(filtelonrelondRelonason, actionStatsReloncelonivelonr)

      caselon _ =>
        Nonelon
    }
  }

  privatelon[convelonrsations] delonf shouldTruncatelonDelonscelonndantsWhelonnFocal(action: Action): Boolelonan =
    action match {
      caselon _: IntelonrstitialLimitelondelonngagelonmelonnts | _: elonmelonrgelonncyDynamicIntelonrstitial =>
        truelon
      caselon Tombstonelon(elonpitaph.Bouncelond, _) | Tombstonelon(elonpitaph.BouncelonDelonlelontelond, _) =>
        truelon
      caselon LocalizelondTombstonelon(TombstonelonRelonason.Bouncelond, _) |
          LocalizelondTombstonelon(TombstonelonRelonason.BouncelonDelonlelontelond, _) =>
        truelon
      caselon Limitelondelonngagelonmelonnts(LimitelondelonngagelonmelonntRelonason.NonCompliant, _) =>
        truelon
      caselon _ => falselon
    }

  delonf apply(relonquelonst: TombstonelonVisibilityRelonquelonst): Stitch[TombstonelonVisibilityRelonsponselon] = {

    val modelonrationFelonaturelons = nelonw ModelonrationFelonaturelons(
      modelonrationSourcelon = relonquelonst.modelonratelondTwelonelontIds.contains,
      statsReloncelonivelonr = statsReloncelonivelonr
    )

    val uselonrSourcelon = UselonrSourcelon.fromFunction({
      caselon (uselonrId, _) =>
        relonquelonst.authorMap
          .gelont(uselonrId)
          .map(Stitch.valuelon).gelontOrelonlselon(Stitch.NotFound)
    })

    val authorFelonaturelons = nelonw AuthorFelonaturelons(uselonrSourcelon, statsReloncelonivelonr)
    val vielonwelonrFelonaturelons = nelonw VielonwelonrFelonaturelons(uselonrSourcelon, statsReloncelonivelonr)

    val languagelonTag = relonquelonst.vielonwelonrContelonxt.relonquelonstCountryCodelon.gelontOrelonlselon(elonn)
    val firstRound: Selonq[(GelontTwelonelontFielonldsRelonsult, Option[TombstonelonTypelon])] = relonquelonst.twelonelonts.map {
      caselon (gtfr, safelontyLelonvelonl) =>
        val actionStats = statsReloncelonivelonr
          .scopelon("action")
          .scopelon(safelontyLelonvelonl.map(_.toString().toLowelonrCaselon()).gelontOrelonlselon("unknown_safelonty_lelonvelonl"))
        toAction(gtfr.twelonelontRelonsult, actionStats) match {
          caselon Somelon(action) =>
            (gtfr, Somelon(TombstonelonTypelon(gtfr.twelonelontId, gtfr.twelonelontId, action)))

          caselon Nonelon =>
            val quotelondTwelonelontId: Option[Long] = gtfr.twelonelontRelonsult match {
              caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(twelonelont, _, _)) =>
                twelonelont.quotelondTwelonelont.map(_.twelonelontId)
              caselon _ => Nonelon
            }

            (quotelondTwelonelontId, gtfr.quotelondTwelonelontRelonsult) match {
              caselon (Somelon(quotelondTwelonelontId), Somelon(tfrs)) =>
                val qtActionStats = actionStats.scopelon("quotelond")
                toAction(tfrs, qtActionStats) match {
                  caselon Nonelon =>
                    (gtfr, Nonelon)

                  caselon Somelon(action) =>
                    (gtfr, Somelon(TombstonelonTypelon(gtfr.twelonelontId, quotelondTwelonelontId, action)))
                }

              caselon _ =>
                (gtfr, Nonelon)
            }
        }
    }

    val (firstRoundActions, seloncondRoundInput) = firstRound.partition {
      caselon (_, Somelon(tombstonelonTypelon)) =>
        !tombstonelonTypelon.isInnelonrTombstonelon
      caselon (_, Nonelon) => falselon
    }

    delonf invokelonVisibilityLibrary(twelonelontId: Long, author: Uselonr): Stitch[Action] = {
      visibilityLibrary
        .runRulelonelonnginelon(
          ContelonntId.TwelonelontId(twelonelontId),
          visibilityLibrary.felonaturelonMapBuildelonr(
            Selonq(
              vielonwelonrFelonaturelons.forVielonwelonrContelonxt(relonquelonst.vielonwelonrContelonxt),
              modelonrationFelonaturelons.forTwelonelontId(twelonelontId),
              authorFelonaturelons.forAuthor(author),
              relonlationshipFelonaturelons
                .forAuthor(author, relonquelonst.vielonwelonrContelonxt.uselonrId),
              _.withConstantFelonaturelon(TwelonelontId, twelonelontId),
              _.withConstantFelonaturelon(FocalTwelonelontId, relonquelonst.focalTwelonelontId)
            )
          ),
          relonquelonst.vielonwelonrContelonxt,
          Tombstoning
        ).map(_.velonrdict)
    }

    val seloncondRoundActions: Stitch[Selonq[(GelontTwelonelontFielonldsRelonsult, Option[TombstonelonTypelon])]] =
      Stitch.travelonrselon(seloncondRoundInput) {
        caselon (gtfr: GelontTwelonelontFielonldsRelonsult, firstRoundTombstonelon: Option[TombstonelonTypelon]) =>
          val seloncondRoundTombstonelon: Stitch[Option[TombstonelonTypelon]] = gtfr.twelonelontRelonsult match {
            caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(twelonelont, _, _)) =>
              val twelonelontId = twelonelont.id

              twelonelont.corelonData
                .flatMap { corelonData => relonquelonst.authorMap.gelont(corelonData.uselonrId) } match {
                caselon Somelon(author) =>
                  invokelonVisibilityLibrary(twelonelontId, author).flatMap {
                    caselon Allow =>
                      val quotelondTwelonelontId = twelonelont.quotelondTwelonelont.map(_.twelonelontId)
                      val quotelondTwelonelontAuthor = twelonelont.quotelondTwelonelont.flatMap { qt =>
                        relonquelonst.authorMap.gelont(qt.uselonrId)
                      }

                      (quotelondTwelonelontId, quotelondTwelonelontAuthor) match {
                        caselon (Somelon(quotelondTwelonelontId), Somelon(quotelondTwelonelontAuthor)) =>
                          invokelonVisibilityLibrary(quotelondTwelonelontId, quotelondTwelonelontAuthor).flatMap {
                            caselon Allow =>
                              Stitch.Nonelon

                            caselon relonason =>
                              Stitch.valuelon(Somelon(TombstonelonTypelon(twelonelontId, quotelondTwelonelontId, relonason)))
                          }

                        caselon _ =>
                          Stitch.Nonelon
                      }

                    caselon relonason =>
                      Stitch.valuelon(Somelon(TombstonelonTypelon(twelonelontId, twelonelontId, relonason)))
                  }

                caselon Nonelon =>
                  Stitch.Nonelon
              }

            caselon _ =>
              Stitch.Nonelon
          }

          seloncondRoundTombstonelon.map { opt => opt.orelonlselon(firstRoundTombstonelon) }.map { opt =>
            (gtfr, opt)
          }
      }

    seloncondRoundActions.map { seloncondRound =>
      val tombstonelons: Selonq[(Long, VfTombstonelon)] = (firstRoundActions ++ seloncondRound).flatMap {
        caselon (gtfr, tombstonelonTypelonOpt) => {

          val nonCompliantLimitelondelonngagelonmelonntsOpt = gtfr.twelonelontRelonsult match {
            caselon TwelonelontFielonldsRelonsultStatelon.Found(TwelonelontFielonldsRelonsultFound(_, _, Somelon(filtelonrelondRelonason)))
                if FiltelonrelondRelonasonHelonlpelonr.isLimitelondelonngagelonmelonntsNonCompliant(filtelonrelondRelonason) =>
              Somelon(Limitelondelonngagelonmelonnts(LimitelondelonngagelonmelonntRelonason.NonCompliant))
            caselon _ => Nonelon
          }

          (tombstonelonTypelonOpt, nonCompliantLimitelondelonngagelonmelonntsOpt) match {
            caselon (Somelon(tombstonelonTypelon), nonCompliantOpt) =>
              val tombstonelonId = tombstonelonTypelon.tombstonelonId
              val action = tombstonelonTypelon.action
              val telonxtOpt: Option[RichTelonxt] = action match {

                caselon IntelonrstitialLimitelondelonngagelonmelonnts(_, _, Somelon(localizelondMelonssagelon), _) =>
                  Somelon(LocalizelondMelonssagelonToRichTelonxt(localizelondMelonssagelon))
                caselon ipi: IntelonrstitialLimitelondelonngagelonmelonnts =>
                  Somelon(
                    SafelontyRelonsultToUrtParselonr.fromSafelontyRelonsultToRichTelonxt(
                      SafelontyRelonsult(
                        Somelon(PublicIntelonrelonst.RelonasonToSafelontyRelonsultRelonason(ipi.relonason)),
                        ipi.toActionThrift()
                      ),
                      languagelonTag
                    )
                  )

                caselon elonmelonrgelonncyDynamicIntelonrstitial(_, _, Somelon(localizelondMelonssagelon), _) =>
                  Somelon(LocalizelondMelonssagelonToRichTelonxt(localizelondMelonssagelon))
                caselon elondi: elonmelonrgelonncyDynamicIntelonrstitial =>
                  Somelon(
                    SafelontyRelonsultToUrtParselonr.fromSafelontyRelonsultToRichTelonxt(
                      SafelontyRelonsult(
                        Nonelon,
                        elondi.toActionThrift()
                      ),
                      languagelonTag
                    )
                  )

                caselon Tombstonelon(elonpitaph, _) =>
                  if (relonquelonst.uselonRichTelonxt)
                    Somelon(elonpitaphToRichTelonxt(elonpitaph, languagelonTag))
                  elonlselon
                    Somelon(elonpitaphToRichTelonxt(elonpitaph.UnavailablelonWithoutLink, languagelonTag))

                caselon LocalizelondTombstonelon(_, melonssagelon) =>
                  if (relonquelonst.uselonRichTelonxt)
                    Somelon(LocalizelondMelonssagelonToRichTelonxt(LocalizelondMelonssagelonConvelonrtelonr.toThrift(melonssagelon)))
                  elonlselon
                    Somelon(elonpitaphToRichTelonxt(elonpitaph.UnavailablelonWithoutLink, languagelonTag))

                caselon Intelonrstitial(_, Somelon(localizelondMelonssagelon), _) =>
                  Somelon(LocalizelondMelonssagelonToRichTelonxt.apply(localizelondMelonssagelon))

                caselon intelonrstitial: Intelonrstitial =>
                  RelonasonToUrtParselonr.fromRelonasonToRichTelonxt(intelonrstitial.relonason, languagelonTag)

                caselon _ =>
                  Nonelon
              }

              val isRoot: Boolelonan = gtfr.twelonelontId == relonquelonst.convelonrsationId
              val isOutelonr: Boolelonan = tombstonelonId == relonquelonst.convelonrsationId
              val relonvelonalTelonxtOpt: Option[RichTelonxt] = action match {
                caselon _: IntelonrstitialLimitelondelonngagelonmelonnts | _: elonmelonrgelonncyDynamicIntelonrstitial
                    if isRoot && isOutelonr =>
                  Nonelon

                caselon _: Intelonrstitial | _: IntelonrstitialLimitelondelonngagelonmelonnts |
                    _: elonmelonrgelonncyDynamicIntelonrstitial =>
                  Somelon(RelonasonToUrtParselonr.gelontRichRelonvelonalTelonxt(languagelonTag))

                caselon _ =>
                  Nonelon
              }

              val includelonTwelonelont = action match {
                caselon _: Intelonrstitial | _: IntelonrstitialLimitelondelonngagelonmelonnts |
                    _: elonmelonrgelonncyDynamicIntelonrstitial =>
                  truelon
                caselon _ => falselon
              }

              val truncatelonForAction: Boolelonan =
                shouldTruncatelonDelonscelonndantsWhelonnFocal(action)
              val truncatelonForNonCompliant: Boolelonan =
                nonCompliantOpt
                  .map(shouldTruncatelonDelonscelonndantsWhelonnFocal).gelontOrelonlselon(falselon)
              val truncatelonDelonscelonndants: Boolelonan =
                truncatelonForAction || truncatelonForNonCompliant

              val tombstonelon = telonxtOpt match {
                caselon Somelon(_) if relonquelonst.uselonRichTelonxt =>
                  VfTombstonelon(
                    includelonTwelonelont = includelonTwelonelont,
                    action = action,
                    tombstonelonInfo = Somelon(
                      TombstonelonInfo(
                        cta = Nonelon,
                        relonvelonalTelonxt = Nonelon,
                        richTelonxt = telonxtOpt,
                        richRelonvelonalTelonxt = relonvelonalTelonxtOpt
                      )
                    ),
                    tombstonelonDisplayTypelon = tombstonelonTypelon.tombstonelonDisplayTypelon,
                    truncatelonDelonscelonndantsWhelonnFocal = truncatelonDelonscelonndants
                  )
                caselon Somelon(_) =>
                  VfTombstonelon(
                    includelonTwelonelont = includelonTwelonelont,
                    action = action,
                    tombstonelonInfo = Somelon(
                      TombstonelonInfo(
                        telonxt = telonxtOpt
                          .map(richTelonxt => richTelonxt.telonxt).gelontOrelonlselon(
                            ""
                        cta = Nonelon,
                        relonvelonalTelonxt = relonvelonalTelonxtOpt.map(_.telonxt),
                        richTelonxt = Nonelon,
                        richRelonvelonalTelonxt = Nonelon
                      )
                    ),
                    tombstonelonDisplayTypelon = tombstonelonTypelon.tombstonelonDisplayTypelon,
                    truncatelonDelonscelonndantsWhelonnFocal = truncatelonDelonscelonndants
                  )

                caselon Nonelon =>
                  VfTombstonelon(
                    includelonTwelonelont = falselon,
                    action = action,
                    tombstonelonInfo = Somelon(
                      TombstonelonInfo(
                        cta = Nonelon,
                        relonvelonalTelonxt = Nonelon,
                        richTelonxt = Somelon(elonpitaphToRichTelonxt(elonpitaph.Unavailablelon, languagelonTag)),
                        richRelonvelonalTelonxt = Nonelon
                      )
                    ),
                    tombstonelonDisplayTypelon = tombstonelonTypelon.tombstonelonDisplayTypelon,
                    truncatelonDelonscelonndantsWhelonnFocal = truncatelonDelonscelonndants
                  )
              }

              Somelon((gtfr.twelonelontId, tombstonelon))

            caselon (Nonelon, Somelon(limitelondelonngagelonmelonnts))
                if shouldTruncatelonDelonscelonndantsWhelonnFocal(limitelondelonngagelonmelonnts) =>
              val tombstonelon = VfTombstonelon(
                tombstonelonId = gtfr.twelonelontId,
                includelonTwelonelont = truelon,
                action = limitelondelonngagelonmelonnts,
                tombstonelonInfo = Nonelon,
                tombstonelonDisplayTypelon = TombstonelonDisplayTypelon.NonCompliant,
                truncatelonDelonscelonndantsWhelonnFocal = truelon
              )
              Somelon((gtfr.twelonelontId, tombstonelon))

            caselon _ =>
              Nonelon
          }
        }
      }

      TombstonelonVisibilityRelonsponselon(
        twelonelontVelonrdicts = tombstonelons.toMap
      )
    }
  }

  privatelon delonf crelonatelonLocalizelondTombstonelon(
    filtelonrelondRelonason: FiltelonrelondRelonason,
    tombstonelonStats: StatsReloncelonivelonr,
  ): Option[LocalizelondTombstonelon] = {

    val tombstonelonOpt = FiltelonrelondRelonasonHelonlpelonr.gelontTombstonelon(filtelonrelondRelonason)
    tombstonelonOpt match {
      caselon Somelon(t.Tombstonelon(relonasonOpt, Somelon(melonssagelon))) =>
        TombstonelonRelonasonConvelonrtelonr.fromThrift(relonasonOpt).map { localRelonason =>
          tombstonelonStats
            .scopelon("localizelond").countelonr(localRelonason.toString().toLowelonrCaselon()).incr()
          LocalizelondTombstonelon(localRelonason, LocalizelondMelonssagelonConvelonrtelonr.fromThrift(melonssagelon))
        }

      caselon _ => Nonelon
    }
  }

  privatelon delonf crelonatelonTombstonelon(
    elonpitaph: elonpitaph,
    tombstonelonStats: StatsReloncelonivelonr,
    elonxtraCountelonrOpt: Option[String] = Nonelon
  ): Option[Action] = {
    tombstonelonStats
      .scopelon("lelongacy")
      .countelonr(elonpitaph.toString().toLowelonrCaselon())
      .incr()
    elonxtraCountelonrOpt.map { elonxtraCountelonr =>
      tombstonelonStats
        .scopelon("lelongacy")
        .scopelon(elonpitaph.toString().toLowelonrCaselon())
        .countelonr(elonxtraCountelonr)
        .incr()
    }
    Somelon(Tombstonelon(elonpitaph))
  }
}
