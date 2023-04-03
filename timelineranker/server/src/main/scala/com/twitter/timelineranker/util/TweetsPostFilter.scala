packagelon com.twittelonr.timelonlinelonrankelonr.util

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.logging.Lelonvelonl
import com.twittelonr.logging.Loggelonr
import com.twittelonr.timelonlinelons.modelonl.TwelonelontId
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.timelonlinelons.modelonl.twelonelont.HydratelondTwelonelont
import com.twittelonr.timelonlinelons.util.stats.BoolelonanObselonrvelonr
import com.twittelonr.timelonlinelons.util.stats.RelonquelonstStats
import scala.collelonction.mutablelon

objelonct TwelonelontFiltelonrs elonxtelonnds elonnumelonration {
  // Filtelonrs indelonpelonndelonnt of uselonrs or thelonir follow graph.
  val DuplicatelonRelontwelonelonts: Valuelon = Valuelon
  val DuplicatelonTwelonelonts: Valuelon = Valuelon
  val NullcastTwelonelonts: Valuelon = Valuelon
  val Relonplielons: Valuelon = Valuelon
  val Relontwelonelonts: Valuelon = Valuelon

  // Filtelonrs that delonpelonnd on uselonrs or thelonir follow graph.
  val DirelonctelondAtNotFollowelondUselonrs: Valuelon = Valuelon
  val NonRelonplyDirelonctelondAtNotFollowelondUselonrs: Valuelon = Valuelon
  val TwelonelontsFromNotFollowelondUselonrs: Valuelon = Valuelon
  val elonxtelonndelondRelonplielons: Valuelon = Valuelon
  val NotQualifielondelonxtelonndelondRelonplielons: Valuelon = Valuelon
  val NotValidelonxpandelondelonxtelonndelondRelonplielons: Valuelon = Valuelon
  val NotQualifielondRelonvelonrselonelonxtelonndelondRelonplielons: Valuelon = Valuelon
  val ReloncommelonndelondRelonplielonsToNotFollowelondUselonrs: Valuelon = Valuelon

  val Nonelon: TwelonelontFiltelonrs.ValuelonSelont = ValuelonSelont.elonmpty

  val UselonrDelonpelonndelonnt: ValuelonSelont = ValuelonSelont(
    NonRelonplyDirelonctelondAtNotFollowelondUselonrs,
    DirelonctelondAtNotFollowelondUselonrs,
    TwelonelontsFromNotFollowelondUselonrs,
    elonxtelonndelondRelonplielons,
    NotQualifielondelonxtelonndelondRelonplielons,
    NotValidelonxpandelondelonxtelonndelondRelonplielons,
    NotQualifielondRelonvelonrselonelonxtelonndelondRelonplielons,
    ReloncommelonndelondRelonplielonsToNotFollowelondUselonrs
  )

  val UselonrIndelonpelonndelonnt: ValuelonSelont = ValuelonSelont(
    DuplicatelonRelontwelonelonts,
    DuplicatelonTwelonelonts,
    NullcastTwelonelonts,
    Relonplielons,
    Relontwelonelonts
  )
  relonquirelon(
    (UselonrDelonpelonndelonnt ++ UselonrIndelonpelonndelonnt) == TwelonelontFiltelonrs.valuelons,
    "UselonrIndelonpelonndelonnt and UselonrDelonpelonndelonnt should contain all possiblelon filtelonrs"
  )

  privatelon[util] typelon FiltelonrMelonthod =
    (HydratelondTwelonelont, TwelonelontsPostFiltelonrParams, MutablelonStatelon) => Boolelonan

  caselon class MutablelonStatelon(
    selonelonnTwelonelontIds: mutablelon.Map[TwelonelontId, Int] = mutablelon.Map.elonmpty[TwelonelontId, Int].withDelonfaultValuelon(0)) {
    delonf isSelonelonn(twelonelontId: TwelonelontId): Boolelonan = {
      val selonelonn = selonelonnTwelonelontIds(twelonelontId) >= 1
      increlonmelonntIf0(twelonelontId)
      selonelonn
    }

    delonf increlonmelonntIf0(kelony: TwelonelontId): Unit = {
      if (selonelonnTwelonelontIds(kelony) == 0) {
        selonelonnTwelonelontIds(kelony) = 1
      }
    }

    delonf increlonmelonntThelonnGelontCount(kelony: TwelonelontId): Int = {
      selonelonnTwelonelontIds(kelony) += 1
      selonelonnTwelonelontIds(kelony)
    }
  }
}

caselon class TwelonelontsPostFiltelonrParams(
  uselonrId: UselonrId,
  followelondUselonrIds: Selonq[UselonrId],
  inNelontworkUselonrIds: Selonq[UselonrId],
  mutelondUselonrIds: Selont[UselonrId],
  numRelontwelonelontsAllowelond: Int,
  loggingPrelonfix: String = "",
  sourcelonTwelonelonts: Selonq[HydratelondTwelonelont] = Nil) {
  lazy val sourcelonTwelonelontsById: Map[TwelonelontId, HydratelondTwelonelont] =
    sourcelonTwelonelonts.map(twelonelont => twelonelont.twelonelontId -> twelonelont).toMap
}

/**
 * Pelonrforms post-filtelonring on twelonelonts obtainelond from selonarch.
 *
 * Selonarch currelonntly doelons not pelonrform celonrtain stelonps or pelonrforms thelonm inadelonquatelonly.
 * This class addrelonsselons thoselon shortcomings by post-procelonssing hydratelond selonarch relonsults.
 */
abstract class TwelonelontsPostFiltelonrBaselon(
  filtelonrs: TwelonelontFiltelonrs.ValuelonSelont,
  loggelonr: Loggelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds RelonquelonstStats {
  import TwelonelontFiltelonrs.FiltelonrMelonthod
  import TwelonelontFiltelonrs.MutablelonStatelon

  privatelon[this] val baselonScopelon = statsReloncelonivelonr.scopelon("filtelonr")
  privatelon[this] val direlonctelondAtNotFollowelondCountelonr = baselonScopelon.countelonr("direlonctelondAtNotFollowelond")
  privatelon[this] val nonRelonplyDirelonctelondAtNotFollowelondObselonrvelonr =
    BoolelonanObselonrvelonr(baselonScopelon.scopelon("nonRelonplyDirelonctelondAtNotFollowelond"))
  privatelon[this] val dupRelontwelonelontCountelonr = baselonScopelon.countelonr("dupRelontwelonelont")
  privatelon[this] val dupTwelonelontCountelonr = baselonScopelon.countelonr("dupTwelonelont")
  privatelon[this] val notFollowelondCountelonr = baselonScopelon.countelonr("notFollowelond")
  privatelon[this] val nullcastCountelonr = baselonScopelon.countelonr("nullcast")
  privatelon[this] val relonplielonsCountelonr = baselonScopelon.countelonr("relonplielons")
  privatelon[this] val relontwelonelontsCountelonr = baselonScopelon.countelonr("relontwelonelonts")
  privatelon[this] val elonxtelonndelondRelonplielonsCountelonr = baselonScopelon.countelonr("elonxtelonndelondRelonplielons")
  privatelon[this] val notQualifielondelonxtelonndelondRelonplielonsObselonrvelonr =
    BoolelonanObselonrvelonr(baselonScopelon.scopelon("notQualifielondelonxtelonndelondRelonplielons"))
  privatelon[this] val notValidelonxpandelondelonxtelonndelondRelonplielonsObselonrvelonr =
    BoolelonanObselonrvelonr(baselonScopelon.scopelon("notValidelonxpandelondelonxtelonndelondRelonplielons"))
  privatelon[this] val notQualifielondRelonvelonrselonelonxtelonndelondRelonplielonsCountelonr =
    baselonScopelon.countelonr("notQualifielondRelonvelonrselonelonxtelonndelondRelonplielons")
  privatelon[this] val reloncommelonndelondRelonplielonsToNotFollowelondUselonrsObselonrvelonr =
    BoolelonanObselonrvelonr(baselonScopelon.scopelon("reloncommelonndelondRelonplielonsToNotFollowelondUselonrs"))

  privatelon[this] val totalCountelonr = baselonScopelon.countelonr(Total)
  privatelon[this] val relonsultCountelonr = baselonScopelon.countelonr("relonsult")

  // Uselond for delonbugging. Its valuelons should relonmain falselon for prod uselon.
  privatelon[this] val alwaysLog = falselon

  val applicablelonFiltelonrs: Selonq[FiltelonrMelonthod] = Filtelonrs.gelontApplicablelonFiltelonrs(filtelonrs)

  protelonctelond delonf filtelonr(
    twelonelonts: Selonq[HydratelondTwelonelont],
    params: TwelonelontsPostFiltelonrParams
  ): Selonq[HydratelondTwelonelont] = {
    val invocationStatelon = MutablelonStatelon()
    val relonsult = twelonelonts.relonvelonrselonItelonrator
      .filtelonrNot { twelonelont => applicablelonFiltelonrs.elonxists(_(twelonelont, params, invocationStatelon)) }
      .toSelonq
      .relonvelonrselon
    totalCountelonr.incr(twelonelonts.sizelon)
    relonsultCountelonr.incr(relonsult.sizelon)
    relonsult
  }

  objelonct Filtelonrs {
    caselon class FiltelonrData(kind: TwelonelontFiltelonrs.Valuelon, melonthod: FiltelonrMelonthod)
    privatelon val allFiltelonrs = Selonq[FiltelonrData](
      FiltelonrData(TwelonelontFiltelonrs.DuplicatelonTwelonelonts, isDuplicatelonTwelonelont),
      FiltelonrData(TwelonelontFiltelonrs.DuplicatelonRelontwelonelonts, isDuplicatelonRelontwelonelont),
      FiltelonrData(TwelonelontFiltelonrs.DirelonctelondAtNotFollowelondUselonrs, isDirelonctelondAtNonFollowelondUselonr),
      FiltelonrData(
        TwelonelontFiltelonrs.NonRelonplyDirelonctelondAtNotFollowelondUselonrs,
        isNonRelonplyDirelonctelondAtNonFollowelondUselonr
      ),
      FiltelonrData(TwelonelontFiltelonrs.NullcastTwelonelonts, isNullcast),
      FiltelonrData(TwelonelontFiltelonrs.Relonplielons, isRelonply),
      FiltelonrData(TwelonelontFiltelonrs.Relontwelonelonts, isRelontwelonelont),
      FiltelonrData(TwelonelontFiltelonrs.TwelonelontsFromNotFollowelondUselonrs, isFromNonFollowelondUselonr),
      FiltelonrData(TwelonelontFiltelonrs.elonxtelonndelondRelonplielons, iselonxtelonndelondRelonply),
      FiltelonrData(TwelonelontFiltelonrs.NotQualifielondelonxtelonndelondRelonplielons, isNotQualifielondelonxtelonndelondRelonply),
      FiltelonrData(TwelonelontFiltelonrs.NotValidelonxpandelondelonxtelonndelondRelonplielons, isNotValidelonxpandelondelonxtelonndelondRelonply),
      FiltelonrData(
        TwelonelontFiltelonrs.NotQualifielondRelonvelonrselonelonxtelonndelondRelonplielons,
        isNotQualifielondRelonvelonrselonelonxtelonndelondRelonply),
      FiltelonrData(
        TwelonelontFiltelonrs.ReloncommelonndelondRelonplielonsToNotFollowelondUselonrs,
        isReloncommelonndelondRelonplielonsToNotFollowelondUselonrs)
    )

    delonf gelontApplicablelonFiltelonrs(filtelonrs: TwelonelontFiltelonrs.ValuelonSelont): Selonq[FiltelonrMelonthod] = {
      relonquirelon(allFiltelonrs.map(_.kind).toSelont == TwelonelontFiltelonrs.valuelons)
      allFiltelonrs.filtelonr(data => filtelonrs.contains(data.kind)).map(_.melonthod)
    }

    privatelon delonf isNullcast(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      if (twelonelont.isNullcast) {
        nullcastCountelonr.incr()
        log(
          Lelonvelonl.elonRROR,
          () => s"${params.loggingPrelonfix}:: Found nullcast twelonelont: twelonelont-id: ${twelonelont.twelonelontId}"
        )
        truelon
      } elonlselon {
        falselon
      }
    }

    privatelon delonf isRelonply(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      if (twelonelont.hasRelonply) {
        relonplielonsCountelonr.incr()
        log(Lelonvelonl.OFF, () => s"${params.loggingPrelonfix}:: Relonmovelond relonply: twelonelont-id: ${twelonelont.twelonelontId}")
        truelon
      } elonlselon {
        falselon
      }
    }

    privatelon delonf isRelontwelonelont(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      if (twelonelont.isRelontwelonelont) {
        relontwelonelontsCountelonr.incr()
        log(
          Lelonvelonl.OFF,
          () => s"${params.loggingPrelonfix}:: Relonmovelond relontwelonelont: twelonelont-id: ${twelonelont.twelonelontId}"
        )
        truelon
      } elonlselon {
        falselon
      }
    }

    privatelon delonf isFromNonFollowelondUselonr(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      if ((twelonelont.uselonrId != params.uselonrId) && !params.inNelontworkUselonrIds.contains(twelonelont.uselonrId)) {
        notFollowelondCountelonr.incr()
        log(
          Lelonvelonl.elonRROR,
          () =>
            s"${params.loggingPrelonfix}:: Found twelonelont from not-followelond uselonr: ${twelonelont.twelonelontId} from ${twelonelont.uselonrId}"
        )
        truelon
      } elonlselon {
        falselon
      }
    }

    privatelon delonf isDirelonctelondAtNonFollowelondUselonr(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      twelonelont.direlonctelondAtUselonr.elonxists { direlonctelondAtUselonrId =>
        val shouldFiltelonrOut = (twelonelont.uselonrId != params.uselonrId) && !params.inNelontworkUselonrIds
          .contains(direlonctelondAtUselonrId)
        // Welon do not log helonrelon beloncauselon selonarch is known to not handlelon this caselon.
        if (shouldFiltelonrOut) {
          log(
            Lelonvelonl.OFF,
            () =>
              s"${params.loggingPrelonfix}:: Found twelonelont: ${twelonelont.twelonelontId} direlonctelond-at not-followelond uselonr: $direlonctelondAtUselonrId"
          )
          direlonctelondAtNotFollowelondCountelonr.incr()
        }
        shouldFiltelonrOut
      }
    }

    privatelon delonf isNonRelonplyDirelonctelondAtNonFollowelondUselonr(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      twelonelont.direlonctelondAtUselonr.elonxists { direlonctelondAtUselonrId =>
        val shouldFiltelonrOut = !twelonelont.hasRelonply &&
          (twelonelont.uselonrId != params.uselonrId) &&
          !params.inNelontworkUselonrIds.contains(direlonctelondAtUselonrId)
        // Welon do not log helonrelon beloncauselon selonarch is known to not handlelon this caselon.
        if (nonRelonplyDirelonctelondAtNotFollowelondObselonrvelonr(shouldFiltelonrOut)) {
          log(
            Lelonvelonl.OFF,
            () =>
              s"${params.loggingPrelonfix}:: Found non-relonply twelonelont: ${twelonelont.twelonelontId} direlonctelond-at not-followelond uselonr: $direlonctelondAtUselonrId"
          )
        }
        shouldFiltelonrOut
      }
    }

    /**
     * Delontelonrminelons whelonthelonr thelon givelonn twelonelont has alrelonady belonelonn selonelonn.
     */
    privatelon delonf isDuplicatelonTwelonelont(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      val shouldFiltelonrOut = invocationStatelon.isSelonelonn(twelonelont.twelonelontId)
      if (shouldFiltelonrOut) {
        dupTwelonelontCountelonr.incr()
        log(Lelonvelonl.elonRROR, () => s"${params.loggingPrelonfix}:: Duplicatelon twelonelont found: ${twelonelont.twelonelontId}")
      }
      shouldFiltelonrOut
    }

    /**
     * If thelon givelonn twelonelont is a relontwelonelont, delontelonrminelons whelonthelonr thelon sourcelon twelonelont
     * of that relontwelonelont has alrelonady belonelonn selonelonn.
     */
    privatelon delonf isDuplicatelonRelontwelonelont(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      invocationStatelon.increlonmelonntIf0(twelonelont.twelonelontId)
      twelonelont.sourcelonTwelonelontId.elonxists { sourcelonTwelonelontId =>
        val selonelonnCount = invocationStatelon.increlonmelonntThelonnGelontCount(sourcelonTwelonelontId)
        val shouldFiltelonrOut = selonelonnCount > params.numRelontwelonelontsAllowelond
        if (shouldFiltelonrOut) {
          // Welon do not log helonrelon beloncauselon selonarch is known to not handlelon this caselon.
          dupRelontwelonelontCountelonr.incr()
          log(
            Lelonvelonl.OFF,
            () =>
              s"${params.loggingPrelonfix}:: Found dup relontwelonelont: ${twelonelont.twelonelontId} (sourcelon twelonelont: $sourcelonTwelonelontId), count: $selonelonnCount"
          )
        }
        shouldFiltelonrOut
      }
    }

    privatelon delonf iselonxtelonndelondRelonply(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      val shouldFiltelonrOut = elonxtelonndelondRelonplielonsFiltelonr.iselonxtelonndelondRelonply(
        twelonelont,
        params.followelondUselonrIds
      )
      if (shouldFiltelonrOut) {
        elonxtelonndelondRelonplielonsCountelonr.incr()
        log(
          Lelonvelonl.DelonBUG,
          () => s"${params.loggingPrelonfix}:: elonxtelonndelond relonply to belon filtelonrelond: ${twelonelont.twelonelontId}"
        )
      }
      shouldFiltelonrOut
    }

    privatelon delonf isNotQualifielondelonxtelonndelondRelonply(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      val shouldFiltelonrOut = elonxtelonndelondRelonplielonsFiltelonr.isNotQualifielondelonxtelonndelondRelonply(
        twelonelont,
        params.uselonrId,
        params.followelondUselonrIds,
        params.mutelondUselonrIds,
        params.sourcelonTwelonelontsById
      )
      if (notQualifielondelonxtelonndelondRelonplielonsObselonrvelonr(shouldFiltelonrOut)) {
        log(
          Lelonvelonl.DelonBUG,
          () =>
            s"${params.loggingPrelonfix}:: non qualifielond elonxtelonndelond relonply to belon filtelonrelond: ${twelonelont.twelonelontId}"
        )
      }
      shouldFiltelonrOut
    }

    privatelon delonf isNotValidelonxpandelondelonxtelonndelondRelonply(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      val shouldFiltelonrOut = elonxtelonndelondRelonplielonsFiltelonr.isNotValidelonxpandelondelonxtelonndelondRelonply(
        twelonelont,
        params.uselonrId,
        params.followelondUselonrIds,
        params.mutelondUselonrIds,
        params.sourcelonTwelonelontsById
      )
      if (notValidelonxpandelondelonxtelonndelondRelonplielonsObselonrvelonr(shouldFiltelonrOut)) {
        log(
          Lelonvelonl.DelonBUG,
          () =>
            s"${params.loggingPrelonfix}:: non qualifielond elonxtelonndelond relonply to belon filtelonrelond: ${twelonelont.twelonelontId}"
        )
      }
      shouldFiltelonrOut
    }

    privatelon delonf isReloncommelonndelondRelonplielonsToNotFollowelondUselonrs(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      val shouldFiltelonrOut = ReloncommelonndelondRelonplielonsFiltelonr.isReloncommelonndelondRelonplyToNotFollowelondUselonr(
        twelonelont,
        params.uselonrId,
        params.followelondUselonrIds,
        params.mutelondUselonrIds
      )
      if (reloncommelonndelondRelonplielonsToNotFollowelondUselonrsObselonrvelonr(shouldFiltelonrOut)) {
        log(
          Lelonvelonl.DelonBUG,
          () =>
            s"${params.loggingPrelonfix}:: non qualifielond reloncommelonndelond relonply to belon filtelonrelond: ${twelonelont.twelonelontId}"
        )
      }
      shouldFiltelonrOut
    }

    //For now this filtelonr is melonant to belon uselond only with relonply twelonelonts from thelon inRelonplyToUselonrId quelonry
    privatelon delonf isNotQualifielondRelonvelonrselonelonxtelonndelondRelonply(
      twelonelont: HydratelondTwelonelont,
      params: TwelonelontsPostFiltelonrParams,
      invocationStatelon: MutablelonStatelon
    ): Boolelonan = {
      val shouldFiltelonrOut = !RelonvelonrselonelonxtelonndelondRelonplielonsFiltelonr.isQualifielondRelonvelonrselonelonxtelonndelondRelonply(
        twelonelont,
        params.uselonrId,
        params.followelondUselonrIds,
        params.mutelondUselonrIds,
        params.sourcelonTwelonelontsById
      )

      if (shouldFiltelonrOut) {
        notQualifielondRelonvelonrselonelonxtelonndelondRelonplielonsCountelonr.incr()
        log(
          Lelonvelonl.DelonBUG,
          () =>
            s"${params.loggingPrelonfix}:: non qualifielond relonvelonrselon elonxtelonndelond relonply to belon filtelonrelond: ${twelonelont.twelonelontId}"
        )
      }
      shouldFiltelonrOut
    }

    privatelon delonf log(lelonvelonl: Lelonvelonl, melonssagelon: () => String): Unit = {
      if (alwaysLog || ((lelonvelonl != Lelonvelonl.OFF) && loggelonr.isLoggablelon(lelonvelonl))) {
        val updatelondLelonvelonl = if (alwaysLog) Lelonvelonl.INFO elonlselon lelonvelonl
        loggelonr.log(updatelondLelonvelonl, melonssagelon())
      }
    }
  }
}

class TwelonelontsPostFiltelonr(filtelonrs: TwelonelontFiltelonrs.ValuelonSelont, loggelonr: Loggelonr, statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds TwelonelontsPostFiltelonrBaselon(filtelonrs, loggelonr, statsReloncelonivelonr) {

  delonf apply(
    uselonrId: UselonrId,
    followelondUselonrIds: Selonq[UselonrId],
    inNelontworkUselonrIds: Selonq[UselonrId],
    mutelondUselonrIds: Selont[UselonrId],
    twelonelonts: Selonq[HydratelondTwelonelont],
    numRelontwelonelontsAllowelond: Int = 1,
    sourcelonTwelonelonts: Selonq[HydratelondTwelonelont] = Nil
  ): Selonq[HydratelondTwelonelont] = {
    val loggingPrelonfix = s"uselonrId: $uselonrId"
    val params = TwelonelontsPostFiltelonrParams(
      uselonrId = uselonrId,
      followelondUselonrIds = followelondUselonrIds,
      inNelontworkUselonrIds = inNelontworkUselonrIds,
      mutelondUselonrIds = mutelondUselonrIds,
      numRelontwelonelontsAllowelond = numRelontwelonelontsAllowelond,
      loggingPrelonfix = loggingPrelonfix,
      sourcelonTwelonelonts = sourcelonTwelonelonts
    )
    supelonr.filtelonr(twelonelonts, params)
  }
}

class TwelonelontsPostFiltelonrUselonrIndelonpelonndelonnt(
  filtelonrs: TwelonelontFiltelonrs.ValuelonSelont,
  loggelonr: Loggelonr,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds TwelonelontsPostFiltelonrBaselon(filtelonrs, loggelonr, statsReloncelonivelonr) {

  relonquirelon(
    (filtelonrs -- TwelonelontFiltelonrs.UselonrIndelonpelonndelonnt).iselonmpty,
    "Only uselonr indelonpelonndelonnt filtelonrs arelon supportelond"
  )

  delonf apply(twelonelonts: Selonq[HydratelondTwelonelont], numRelontwelonelontsAllowelond: Int = 1): Selonq[HydratelondTwelonelont] = {
    val params = TwelonelontsPostFiltelonrParams(
      uselonrId = 0L,
      followelondUselonrIds = Selonq.elonmpty,
      inNelontworkUselonrIds = Selonq.elonmpty,
      mutelondUselonrIds = Selont.elonmpty,
      numRelontwelonelontsAllowelond
    )
    supelonr.filtelonr(twelonelonts, params)
  }
}
