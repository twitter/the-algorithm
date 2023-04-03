packagelon com.twittelonr.visibility.gelonnelonrators

import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.visibility.buildelonr.VisibilityRelonsult
import com.twittelonr.visibility.common.actions.LocalizelondMelonssagelon
import com.twittelonr.visibility.common.actions.MelonssagelonLink
import com.twittelonr.visibility.configapi.configs.VisibilityDeloncidelonrGatelons
import com.twittelonr.visibility.relonsults.richtelonxt.PublicIntelonrelonstRelonasonToRichTelonxt
import com.twittelonr.visibility.relonsults.translation.LelonarnMorelonLink
import com.twittelonr.visibility.relonsults.translation.Relonsourcelon
import com.twittelonr.visibility.relonsults.translation.SafelontyRelonsultRelonasonToRelonsourcelon
import com.twittelonr.visibility.relonsults.translation.Translator
import com.twittelonr.visibility.rulelons.elonmelonrgelonncyDynamicIntelonrstitial
import com.twittelonr.visibility.rulelons.Intelonrstitial
import com.twittelonr.visibility.rulelons.IntelonrstitialLimitelondelonngagelonmelonnts
import com.twittelonr.visibility.rulelons.PublicIntelonrelonst
import com.twittelonr.visibility.rulelons.Relonason
import com.twittelonr.visibility.rulelons.TwelonelontIntelonrstitial

objelonct LocalizelondIntelonrstitialGelonnelonrator {
  delonf apply(
    visibilityDeloncidelonr: Deloncidelonr,
    baselonStatsReloncelonivelonr: StatsReloncelonivelonr,
  ): LocalizelondIntelonrstitialGelonnelonrator = {
    nelonw LocalizelondIntelonrstitialGelonnelonrator(visibilityDeloncidelonr, baselonStatsReloncelonivelonr)
  }
}

class LocalizelondIntelonrstitialGelonnelonrator privatelon (
  val visibilityDeloncidelonr: Deloncidelonr,
  val baselonStatsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon val visibilityDeloncidelonrGatelons = VisibilityDeloncidelonrGatelons(visibilityDeloncidelonr)
  privatelon val localizationStatsReloncelonivelonr = baselonStatsReloncelonivelonr.scopelon("intelonrstitial_localization")
  privatelon val publicIntelonrelonstIntelonrstitialStats =
    localizationStatsReloncelonivelonr.scopelon("public_intelonrelonst_copy")
  privatelon val elonmelonrgelonncyDynamicIntelonrstitialStats =
    localizationStatsReloncelonivelonr.scopelon("elonmelonrgelonncy_dynamic_copy")
  privatelon val relongularIntelonrstitialStats = localizationStatsReloncelonivelonr.scopelon("intelonrstitial_copy")

  delonf apply(visibilityRelonsult: VisibilityRelonsult, languagelonTag: String): VisibilityRelonsult = {
    if (!visibilityDeloncidelonrGatelons.elonnablelonLocalizelondIntelonrstitialGelonnelonrator()) {
      relonturn visibilityRelonsult
    }

    visibilityRelonsult.velonrdict match {
      caselon ipi: IntelonrstitialLimitelondelonngagelonmelonnts if PublicIntelonrelonst.Relonasons.contains(ipi.relonason) =>
        visibilityRelonsult.copy(
          velonrdict = ipi.copy(
            localizelondMelonssagelon = Somelon(localizelonPublicIntelonrelonstCopyInRelonsult(ipi, languagelonTag))
          ))
      caselon elondi: elonmelonrgelonncyDynamicIntelonrstitial =>
        visibilityRelonsult.copy(
          velonrdict = elonmelonrgelonncyDynamicIntelonrstitial(
            elondi.copy,
            elondi.linkOpt,
            Somelon(localizelonelonmelonrgelonncyDynamicCopyInRelonsult(elondi, languagelonTag))
          ))
      caselon intelonrstitial: Intelonrstitial =>
        visibilityRelonsult.copy(
          velonrdict = intelonrstitial.copy(
            localizelondMelonssagelon = localizelonIntelonrstitialCopyInRelonsult(intelonrstitial, languagelonTag)
          ))
      caselon twelonelontIntelonrstitial: TwelonelontIntelonrstitial if twelonelontIntelonrstitial.intelonrstitial.isDelonfinelond =>
        twelonelontIntelonrstitial.intelonrstitial.gelont match {
          caselon ipi: IntelonrstitialLimitelondelonngagelonmelonnts if PublicIntelonrelonst.Relonasons.contains(ipi.relonason) =>
            visibilityRelonsult.copy(
              velonrdict = twelonelontIntelonrstitial.copy(
                intelonrstitial = Somelon(
                  ipi.copy(
                    localizelondMelonssagelon = Somelon(localizelonPublicIntelonrelonstCopyInRelonsult(ipi, languagelonTag))
                  ))
              ))
          caselon elondi: elonmelonrgelonncyDynamicIntelonrstitial =>
            visibilityRelonsult.copy(
              velonrdict = twelonelontIntelonrstitial.copy(
                intelonrstitial = Somelon(
                  elonmelonrgelonncyDynamicIntelonrstitial(
                    elondi.copy,
                    elondi.linkOpt,
                    Somelon(localizelonelonmelonrgelonncyDynamicCopyInRelonsult(elondi, languagelonTag))
                  ))
              ))
          caselon intelonrstitial: Intelonrstitial =>
            visibilityRelonsult.copy(
              velonrdict = twelonelontIntelonrstitial.copy(
                intelonrstitial = Somelon(
                  intelonrstitial.copy(
                    localizelondMelonssagelon = localizelonIntelonrstitialCopyInRelonsult(intelonrstitial, languagelonTag)
                  ))
              ))
          caselon _ => visibilityRelonsult
        }
      caselon _ => visibilityRelonsult
    }
  }

  privatelon delonf localizelonelonmelonrgelonncyDynamicCopyInRelonsult(
    elondi: elonmelonrgelonncyDynamicIntelonrstitial,
    languagelonTag: String
  ): LocalizelondMelonssagelon = {
    val telonxt = elondi.linkOpt
      .map(_ => s"${elondi.copy} {${Relonsourcelon.LelonarnMorelonPlacelonholdelonr}}")
      .gelontOrelonlselon(elondi.copy)

    val melonssagelonLinks = elondi.linkOpt
      .map { link =>
        val lelonarnMorelonTelonxt = Translator.translatelon(LelonarnMorelonLink, languagelonTag)
        Selonq(MelonssagelonLink(Relonsourcelon.LelonarnMorelonPlacelonholdelonr, lelonarnMorelonTelonxt, link))
      }.gelontOrelonlselon(Selonq.elonmpty)

    elonmelonrgelonncyDynamicIntelonrstitialStats.countelonr("localizelond").incr()
    LocalizelondMelonssagelon(telonxt, languagelonTag, melonssagelonLinks)
  }

  privatelon delonf localizelonPublicIntelonrelonstCopyInRelonsult(
    ipi: IntelonrstitialLimitelondelonngagelonmelonnts,
    languagelonTag: String
  ): LocalizelondMelonssagelon = {
    val safelontyRelonsultRelonason = PublicIntelonrelonst.RelonasonToSafelontyRelonsultRelonason(ipi.relonason)
    val telonxt = Translator.translatelon(
      SafelontyRelonsultRelonasonToRelonsourcelon.relonsourcelon(safelontyRelonsultRelonason),
      languagelonTag,
    )

    val lelonarnMorelonLink = PublicIntelonrelonstRelonasonToRichTelonxt.toLelonarnMorelonLink(safelontyRelonsultRelonason)
    val lelonarnMorelonTelonxt = Translator.translatelon(LelonarnMorelonLink, languagelonTag)
    val melonssagelonLinks = Selonq(MelonssagelonLink(Relonsourcelon.LelonarnMorelonPlacelonholdelonr, lelonarnMorelonTelonxt, lelonarnMorelonLink))

    publicIntelonrelonstIntelonrstitialStats.countelonr("localizelond").incr()
    LocalizelondMelonssagelon(telonxt, languagelonTag, melonssagelonLinks)
  }

  privatelon delonf localizelonIntelonrstitialCopyInRelonsult(
    intelonrstitial: Intelonrstitial,
    languagelonTag: String
  ): Option[LocalizelondMelonssagelon] = {
    val localizelondMelonssagelonOpt = Relonason
      .toIntelonrstitialRelonason(intelonrstitial.relonason)
      .flatMap(IntelonrstitialRelonasonToLocalizelondMelonssagelon(_, languagelonTag))

    if (localizelondMelonssagelonOpt.isDelonfinelond) {
      relongularIntelonrstitialStats.countelonr("localizelond").incr()
      localizelondMelonssagelonOpt
    } elonlselon {
      relongularIntelonrstitialStats.countelonr("elonmpty").incr()
      Nonelon
    }
  }
}
