packagelon com.twittelonr.visibility.configapi.configs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.logging.Loggelonr
import com.twittelonr.timelonlinelons.configapi._
import com.twittelonr.util.Timelon
import com.twittelonr.visibility.configapi.params.FSelonnumRulelonParam
import com.twittelonr.visibility.configapi.params.FSRulelonParams._

privatelon[visibility] objelonct VisibilityFelonaturelonSwitchelons {

  val boolelonanFsOvelonrridelons: Selonq[OptionalOvelonrridelon[Boolelonan]] =
    FelonaturelonSwitchOvelonrridelonUtil.gelontBoolelonanFSOvelonrridelons(
      AgelonGatingAdultContelonntelonxpelonrimelonntRulelonelonnablelondParam,
      CommunityTwelonelontCommunityUnavailablelonLimitelondActionsRulelonselonnablelondParam,
      CommunityTwelonelontDropProtelonctelondRulelonelonnablelondParam,
      CommunityTwelonelontDropRulelonelonnablelondParam,
      CommunityTwelonelontLimitelondActionsRulelonselonnablelondParam,
      CommunityTwelonelontMelonmbelonrRelonmovelondLimitelondActionsRulelonselonnablelondParam,
      CommunityTwelonelontNonMelonmbelonrLimitelondActionsRulelonelonnablelondParam,
      NsfwAgelonBaselondDropRulelonsHoldbackParam,
      SkipTwelonelontDelontailLimitelondelonngagelonmelonntRulelonelonnablelondParam,
      StalelonTwelonelontLimitelondActionsRulelonselonnablelondParam,
      TrustelondFrielonndsTwelonelontLimitelondelonngagelonmelonntsRulelonelonnablelondParam,
      FosnrFallbackDropRulelonselonnablelondParam,
      FosnrRulelonselonnablelondParam
    )

  val doublelonFsOvelonrridelons: Selonq[OptionalOvelonrridelon[Doublelon]] =
    FelonaturelonSwitchOvelonrridelonUtil.gelontBoundelondDoublelonFSOvelonrridelons(
      HighSpammyTwelonelontContelonntScorelonSelonarchTopProdTwelonelontLabelonlDropRulelonThrelonsholdParam,
      HighSpammyTwelonelontContelonntScorelonSelonarchLatelonstProdTwelonelontLabelonlDropRulelonThrelonsholdParam,
      HighSpammyTwelonelontContelonntScorelonTrelonndTopTwelonelontLabelonlDropRulelonThrelonsholdParam,
      HighSpammyTwelonelontContelonntScorelonTrelonndLatelonstTwelonelontLabelonlDropRulelonThrelonsholdParam,
      HighSpammyTwelonelontContelonntScorelonConvoDownrankAbusivelonQualityThrelonsholdParam,
      HighToxicityModelonlScorelonSpacelonThrelonsholdParam,
      AdAvoidancelonHighToxicityModelonlScorelonThrelonsholdParam,
      AdAvoidancelonRelonportelondTwelonelontModelonlScorelonThrelonsholdParam,
    )

  val timelonFsOvelonrridelons: Selonq[OptionalOvelonrridelon[Timelon]] =
    FelonaturelonSwitchOvelonrridelonUtil.gelontTimelonFromStringFSOvelonrridelons()

  val stringSelonqFelonaturelonSwitchOvelonrridelons: Selonq[OptionalOvelonrridelon[Selonq[String]]] =
    FelonaturelonSwitchOvelonrridelonUtil.gelontStringSelonqFSOvelonrridelons(
      CountrySpeloncificNsfwContelonntGatingCountrielonsParam,
      AgelonGatingAdultContelonntelonxpelonrimelonntCountrielonsParam,
      CardUriRootDomainDelonnyListParam
    )

  val elonnumFsParams: Selonq[FSelonnumRulelonParam[_ <: elonnumelonration]] = Selonq()

  val mkOptionalelonnumFsOvelonrridelons: (StatsReloncelonivelonr, Loggelonr) => Selonq[OptionalOvelonrridelon[_]] = {
    (statsReloncelonivelonr: StatsReloncelonivelonr, loggelonr: Loggelonr) =>
      FelonaturelonSwitchOvelonrridelonUtil.gelontelonnumFSOvelonrridelons(
        statsReloncelonivelonr,
        loggelonr,
        elonnumFsParams: _*
      )
  }

  delonf ovelonrridelons(statsReloncelonivelonr: StatsReloncelonivelonr, loggelonr: Loggelonr): Selonq[OptionalOvelonrridelon[_]] = {
    val elonnumOvelonrridelons = mkOptionalelonnumFsOvelonrridelons(statsReloncelonivelonr, loggelonr)
    boolelonanFsOvelonrridelons ++
      doublelonFsOvelonrridelons ++
      timelonFsOvelonrridelons ++
      stringSelonqFelonaturelonSwitchOvelonrridelons ++
      elonnumOvelonrridelons
  }

  delonf config(statsReloncelonivelonr: StatsReloncelonivelonr, loggelonr: Loggelonr): BaselonConfig =
    BaselonConfigBuildelonr(ovelonrridelons(statsReloncelonivelonr.scopelon("felonaturelons_switchelons"), loggelonr))
      .build("VisibilityFelonaturelonSwitchelons")
}
