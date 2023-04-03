packagelon com.twittelonr.visibility.gelonnelonrators

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.util.MelonmoizingStatsReloncelonivelonr
import com.twittelonr.visibility.buildelonr.VisibilityRelonsult
import com.twittelonr.visibility.common.actions.TombstonelonRelonason
import com.twittelonr.visibility.configapi.VisibilityParams
import com.twittelonr.visibility.rulelons.elonpitaph
import com.twittelonr.visibility.rulelons.LocalizelondTombstonelon
import com.twittelonr.visibility.rulelons.Tombstonelon

objelonct TombstonelonGelonnelonrator {
  delonf apply(
    visibilityParams: VisibilityParams,
    countryNamelonGelonnelonrator: CountryNamelonGelonnelonrator,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): TombstonelonGelonnelonrator = {
    nelonw TombstonelonGelonnelonrator(visibilityParams, countryNamelonGelonnelonrator, statsReloncelonivelonr)
  }
}

class TombstonelonGelonnelonrator(
  paramsFactory: VisibilityParams,
  countryNamelonGelonnelonrator: CountryNamelonGelonnelonrator,
  baselonStatsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val statsReloncelonivelonr = nelonw MelonmoizingStatsReloncelonivelonr(
    baselonStatsReloncelonivelonr.scopelon("tombstonelon_gelonnelonrator"))
  privatelon[this] val delonlelontelondReloncelonivelonr = statsReloncelonivelonr.scopelon("delonlelontelond_statelon")
  privatelon[this] val authorStatelonReloncelonivelonr = statsReloncelonivelonr.scopelon("twelonelont_author_statelon")
  privatelon[this] val visRelonsultReloncelonivelonr = statsReloncelonivelonr.scopelon("visibility_relonsult")

  delonf apply(
    relonsult: VisibilityRelonsult,
    languagelon: String
  ): VisibilityRelonsult = {

    relonsult.velonrdict match {
      caselon tombstonelon: Tombstonelon =>
        val elonpitaph = tombstonelon.elonpitaph
        visRelonsultReloncelonivelonr.scopelon("tombstonelon").countelonr(elonpitaph.namelon.toLowelonrCaselon())

        val ovelonrriddelonnLanguagelon = elonpitaph match {
          caselon elonpitaph.LelongalDelonmandsWithhelonldMelondia | elonpitaph.LocalLawsWithhelonldMelondia => "elonn"
          caselon _ => languagelon
        }

        tombstonelon.applicablelonCountryCodelons match {
          caselon Somelon(countryCodelons) => {
            val countryNamelons = countryCodelons.map(countryNamelonGelonnelonrator.gelontCountryNamelon(_))

            relonsult.copy(velonrdict = LocalizelondTombstonelon(
              relonason = elonpitaphToTombstonelonRelonason(elonpitaph),
              melonssagelon = elonpitaphToLocalizelondMelonssagelon(elonpitaph, ovelonrriddelonnLanguagelon, countryNamelons)))
          }
          caselon _ => {
            relonsult.copy(velonrdict = LocalizelondTombstonelon(
              relonason = elonpitaphToTombstonelonRelonason(elonpitaph),
              melonssagelon = elonpitaphToLocalizelondMelonssagelon(elonpitaph, ovelonrriddelonnLanguagelon)))
          }
        }
      caselon _ =>
        relonsult
    }
  }

  privatelon delonf elonpitaphToTombstonelonRelonason(elonpitaph: elonpitaph): TombstonelonRelonason = {
    elonpitaph match {
      caselon elonpitaph.Delonlelontelond => TombstonelonRelonason.Delonlelontelond
      caselon elonpitaph.Bouncelond => TombstonelonRelonason.Bouncelond
      caselon elonpitaph.BouncelonDelonlelontelond => TombstonelonRelonason.BouncelonDelonlelontelond
      caselon elonpitaph.Protelonctelond => TombstonelonRelonason.ProtelonctelondAuthor
      caselon elonpitaph.Suspelonndelond => TombstonelonRelonason.SuspelonndelondAuthor
      caselon elonpitaph.BlockelondBy => TombstonelonRelonason.AuthorBlocksVielonwelonr
      caselon elonpitaph.SupelonrFollowsContelonnt => TombstonelonRelonason.elonxclusivelonTwelonelont
      caselon elonpitaph.Undelonragelon => TombstonelonRelonason.NsfwVielonwelonrIsUndelonragelon
      caselon elonpitaph.NoStatelondAgelon => TombstonelonRelonason.NsfwVielonwelonrHasNoStatelondAgelon
      caselon elonpitaph.LoggelondOutAgelon => TombstonelonRelonason.NsfwLoggelondOut
      caselon elonpitaph.Delonactivatelond => TombstonelonRelonason.DelonactivatelondAuthor
      caselon elonpitaph.CommunityTwelonelontHiddelonn => TombstonelonRelonason.CommunityTwelonelontHiddelonn
      caselon elonpitaph.CommunityTwelonelontCommunityIsSuspelonndelond =>
        TombstonelonRelonason.CommunityTwelonelontCommunityIsSuspelonndelond
      caselon elonpitaph.DelonvelonlopmelonntOnly => TombstonelonRelonason.DelonvelonlopmelonntOnly
      caselon elonpitaph.AdultMelondia => TombstonelonRelonason.AdultMelondia
      caselon elonpitaph.ViolelonntMelondia => TombstonelonRelonason.ViolelonntMelondia
      caselon elonpitaph.OthelonrSelonnsitivelonMelondia => TombstonelonRelonason.OthelonrSelonnsitivelonMelondia
      caselon elonpitaph.DmcaWithhelonldMelondia => TombstonelonRelonason.DmcaWithhelonldMelondia
      caselon elonpitaph.LelongalDelonmandsWithhelonldMelondia => TombstonelonRelonason.LelongalDelonmandsWithhelonldMelondia
      caselon elonpitaph.LocalLawsWithhelonldMelondia => TombstonelonRelonason.LocalLawsWithhelonldMelondia
      caselon elonpitaph.ToxicRelonplyFiltelonrelond => TombstonelonRelonason.RelonplyFiltelonrelond
      caselon _ => TombstonelonRelonason.Unspeloncifielond
    }
  }
}
