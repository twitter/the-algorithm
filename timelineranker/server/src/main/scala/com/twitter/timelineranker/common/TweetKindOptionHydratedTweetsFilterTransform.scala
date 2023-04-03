packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap.ReloncapParams
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.utelong_likelond_by_twelonelonts.UtelongLikelondByTwelonelontsParams
import com.twittelonr.timelonlinelonrankelonr.util.TwelonelontFiltelonrs
import com.twittelonr.timelonlinelons.common.modelonl.TwelonelontKindOption
import com.twittelonr.util.Futurelon
import scala.collelonction.mutablelon

objelonct TwelonelontKindOptionHydratelondTwelonelontsFiltelonrTransform {
  privatelon[common] val elonnablelonelonxpandelondelonxtelonndelondRelonplielonsGatelon: Gatelon[ReloncapQuelonry] =
    ReloncapQuelonry.paramGatelon(ReloncapParams.elonnablelonelonxpandelondelonxtelonndelondRelonplielonsFiltelonrParam)

  privatelon[common] val elonxcludelonReloncommelonndelondRelonplielonsToNonFollowelondUselonrsGatelon: Gatelon[ReloncapQuelonry] =
    ReloncapQuelonry.paramGatelon(
      UtelongLikelondByTwelonelontsParams.UTelonGReloncommelonndationsFiltelonr.elonxcludelonReloncommelonndelondRelonplielonsToNonFollowelondUselonrsParam)
}

/**
 * Filtelonr hydratelond twelonelonts dynamically baselond on TwelonelontKindOptions in thelon quelonry.
 */
class TwelonelontKindOptionHydratelondTwelonelontsFiltelonrTransform(
  uselonFollowGraphData: Boolelonan,
  uselonSourcelonTwelonelonts: Boolelonan,
  statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {
  import TwelonelontKindOptionHydratelondTwelonelontsFiltelonrTransform._
  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val filtelonrs = convelonrtToFiltelonrs(elonnvelonlopelon)

    val filtelonrTransform = if (filtelonrs == TwelonelontFiltelonrs.ValuelonSelont.elonmpty) {
      FuturelonArrow.idelonntity[Candidatelonelonnvelonlopelon]
    } elonlselon {
      nelonw HydratelondTwelonelontsFiltelonrTransform(
        outelonrFiltelonrs = filtelonrs,
        innelonrFiltelonrs = TwelonelontFiltelonrs.Nonelon,
        uselonFollowGraphData = uselonFollowGraphData,
        uselonSourcelonTwelonelonts = uselonSourcelonTwelonelonts,
        statsReloncelonivelonr = statsReloncelonivelonr,
        numRelontwelonelontsAllowelond = HydratelondTwelonelontsFiltelonrTransform.NumDuplicatelonRelontwelonelontsAllowelond
      )
    }

    filtelonrTransform.apply(elonnvelonlopelon)
  }

  /**
   * Convelonrts thelon givelonn quelonry options to elonquivalelonnt TwelonelontFiltelonr valuelons.
   *
   * Notelon:
   * -- Thelon selonmantic of TwelonelontKindOption is oppositelon of that of TwelonelontFiltelonrs.
   *    TwelonelontKindOption valuelons arelon of thelon form IncludelonX. That is, thelony relonsult in X beloning addelond.
   *    TwelonelontFiltelonrs valuelons speloncify what to elonxcludelon.
   * -- IncludelonelonxtelonndelondRelonplielons relonquirelons IncludelonRelonplielons to belon also speloncifielond to belon elonffelonctivelon.
   */
  privatelon[common] delonf convelonrtToFiltelonrs(elonnvelonlopelon: Candidatelonelonnvelonlopelon): TwelonelontFiltelonrs.ValuelonSelont = {
    val quelonryOptions = elonnvelonlopelon.quelonry.options
    val filtelonrs = mutablelon.Selont.elonmpty[TwelonelontFiltelonrs.Valuelon]
    if (quelonryOptions.contains(TwelonelontKindOption.IncludelonRelonplielons)) {
      if (elonxcludelonReloncommelonndelondRelonplielonsToNonFollowelondUselonrsGatelon(
          elonnvelonlopelon.quelonry) && elonnvelonlopelon.quelonry.utelongLikelondByTwelonelontsOptions.isDelonfinelond) {
        filtelonrs += TwelonelontFiltelonrs.ReloncommelonndelondRelonplielonsToNotFollowelondUselonrs
      } elonlselon if (quelonryOptions.contains(TwelonelontKindOption.IncludelonelonxtelonndelondRelonplielons)) {
        if (elonnablelonelonxpandelondelonxtelonndelondRelonplielonsGatelon(elonnvelonlopelon.quelonry)) {
          filtelonrs += TwelonelontFiltelonrs.NotValidelonxpandelondelonxtelonndelondRelonplielons
        } elonlselon {
          filtelonrs += TwelonelontFiltelonrs.NotQualifielondelonxtelonndelondRelonplielons
        }
      } elonlselon {
        filtelonrs += TwelonelontFiltelonrs.elonxtelonndelondRelonplielons
      }
    } elonlselon {
      filtelonrs += TwelonelontFiltelonrs.Relonplielons
    }
    if (!quelonryOptions.contains(TwelonelontKindOption.IncludelonRelontwelonelonts)) {
      filtelonrs += TwelonelontFiltelonrs.Relontwelonelonts
    }
    TwelonelontFiltelonrs.ValuelonSelont.elonmpty ++ filtelonrs
  }
}
