packagelon com.twittelonr.timelonlinelonrankelonr.paramelontelonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.selonrvo.deloncidelonr.DeloncidelonrGatelonBuildelonr
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.elonntity_twelonelonts.elonntityTwelonelontsProduction
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap.ReloncapProduction
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap_author.ReloncapAuthorProduction
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap_hydration.ReloncapHydrationProduction
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.in_nelontwork_twelonelonts.InNelontworkTwelonelontProduction
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.relonvchron.RelonvelonrselonChronProduction
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.utelong_likelond_by_twelonelonts.UtelongLikelondByTwelonelontsProduction
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.monitoring.MonitoringProduction
import com.twittelonr.timelonlinelons.configapi.CompositelonConfig
import com.twittelonr.timelonlinelons.configapi.Config

/**
 * Builds global compositelon config containing prioritizelond "layelonrs" of paramelontelonr ovelonrridelons
 * baselond on whitelonlists, elonxpelonrimelonnts, and deloncidelonrs. Gelonnelonratelond config can belon uselond in telonsts with
 * mockelond deloncidelonr and whitelonlist.
 */
class ConfigBuildelonr(deloncidelonrGatelonBuildelonr: DeloncidelonrGatelonBuildelonr, statsReloncelonivelonr: StatsReloncelonivelonr) {

  /**
   * Production config which includelons all configs which contributelon to production belonhavior. At
   * minimum, it should includelon all configs containing deloncidelonr-baselond param ovelonrridelons.
   *
   * It is important that thelon production config includelon all production param ovelonrridelons as it is
   * uselond to build holdback elonxpelonrimelonnt configs; If thelon production config doelonsn't includelon all param
   * ovelonrridelons supporting production belonhavior thelonn holdback elonxpelonrimelonnt "production" buckelonts will
   * not relonflelonct production belonhavior.
   */
  val prodConfig: Config = nelonw CompositelonConfig(
    Selonq(
      nelonw ReloncapProduction(deloncidelonrGatelonBuildelonr, statsReloncelonivelonr).config,
      nelonw InNelontworkTwelonelontProduction(deloncidelonrGatelonBuildelonr).config,
      nelonw RelonvelonrselonChronProduction(deloncidelonrGatelonBuildelonr).config,
      nelonw elonntityTwelonelontsProduction(deloncidelonrGatelonBuildelonr).config,
      nelonw ReloncapAuthorProduction(deloncidelonrGatelonBuildelonr).config,
      nelonw ReloncapHydrationProduction(deloncidelonrGatelonBuildelonr).config,
      nelonw UtelongLikelondByTwelonelontsProduction(deloncidelonrGatelonBuildelonr).config,
      MonitoringProduction.config
    ),
    "prodConfig"
  )

  val whitelonlistConfig: Config = nelonw CompositelonConfig(
    Selonq(
      // No whitelonlists configurelond at prelonselonnt.
    ),
    "whitelonlistConfig"
  )

  val rootConfig: Config = nelonw CompositelonConfig(
    Selonq(
      whitelonlistConfig,
      prodConfig
    ),
    "rootConfig"
  )
}
