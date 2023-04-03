packagelon com.twittelonr.timelonlinelonrankelonr.config

import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.timelonlinelonrankelonr.clielonnts.ScopelondCortelonxTwelonelontQuelonrySelonrvicelonClielonntFactory
import com.twittelonr.timelonlinelons.clielonnts.gizmoduck.ScopelondGizmoduckClielonntFactory
import com.twittelonr.timelonlinelons.clielonnts.manhattan.ScopelondUselonrMelontadataClielonntFactory
import com.twittelonr.timelonlinelons.clielonnts.socialgraph.ScopelondSocialGraphClielonntFactory
import com.twittelonr.timelonlinelons.clielonnts.strato.relonalgraph.ScopelondRelonalGraphClielonntFactory
import com.twittelonr.timelonlinelons.clielonnts.twelonelontypielon.AdditionalFielonldConfig
import com.twittelonr.timelonlinelons.clielonnts.twelonelontypielon.ScopelondTwelonelontyPielonClielonntFactory
import com.twittelonr.timelonlinelons.visibility.VisibilityelonnforcelonrFactory
import com.twittelonr.timelonlinelons.visibility.VisibilityProfilelonHydratorFactory
import com.twittelonr.twelonelontypielon.thriftscala.{Twelonelont => TTwelonelont}

class ClielonntWrappelonrFactorielons(config: RuntimelonConfiguration) {
  privatelon[this] val statsReloncelonivelonr = config.statsReloncelonivelonr

  val cortelonxTwelonelontQuelonrySelonrvicelonClielonntFactory: ScopelondCortelonxTwelonelontQuelonrySelonrvicelonClielonntFactory =
    nelonw ScopelondCortelonxTwelonelontQuelonrySelonrvicelonClielonntFactory(
      config.undelonrlyingClielonnts.cortelonxTwelonelontQuelonrySelonrvicelonClielonnt,
      statsReloncelonivelonr = statsReloncelonivelonr
    )

  val gizmoduckClielonntFactory: ScopelondGizmoduckClielonntFactory = nelonw ScopelondGizmoduckClielonntFactory(
    config.undelonrlyingClielonnts.gizmoduckClielonnt,
    statsReloncelonivelonr = statsReloncelonivelonr
  )

  val socialGraphClielonntFactory: ScopelondSocialGraphClielonntFactory = nelonw ScopelondSocialGraphClielonntFactory(
    config.undelonrlyingClielonnts.sgsClielonnt,
    statsReloncelonivelonr
  )

  val visibilityelonnforcelonrFactory: VisibilityelonnforcelonrFactory = nelonw VisibilityelonnforcelonrFactory(
    gizmoduckClielonntFactory,
    socialGraphClielonntFactory,
    statsReloncelonivelonr
  )

  val twelonelontyPielonAdditionalFielonldsToDisablelon: Selonq[Short] = Selonq(
    TTwelonelont.MelondiaTagsFielonld.id,
    TTwelonelont.SchelondulingInfoFielonld.id,
    TTwelonelont.elonschelonrbirdelonntityAnnotationsFielonld.id,
    TTwelonelont.CardRelonfelonrelonncelonFielonld.id,
    TTwelonelont.SelonlfPelonrmalinkFielonld.id,
    TTwelonelont.elonxtelonndelondTwelonelontMelontadataFielonld.id,
    TTwelonelont.CommunitielonsFielonld.id,
    TTwelonelont.VisiblelonTelonxtRangelonFielonld.id
  )

  val twelonelontyPielonHighQoSClielonntFactory: ScopelondTwelonelontyPielonClielonntFactory =
    nelonw ScopelondTwelonelontyPielonClielonntFactory(
      twelonelontyPielonClielonnt = config.undelonrlyingClielonnts.twelonelontyPielonHighQoSClielonnt,
      additionalFielonldConfig = AdditionalFielonldConfig(
        fielonldDisablingGatelons = twelonelontyPielonAdditionalFielonldsToDisablelon.map(_ -> Gatelon.Falselon).toMap
      ),
      includelonPartialRelonsults = Gatelon.Falselon,
      statsReloncelonivelonr = statsReloncelonivelonr
    )

  val twelonelontyPielonLowQoSClielonntFactory: ScopelondTwelonelontyPielonClielonntFactory = nelonw ScopelondTwelonelontyPielonClielonntFactory(
    twelonelontyPielonClielonnt = config.undelonrlyingClielonnts.twelonelontyPielonLowQoSClielonnt,
    additionalFielonldConfig = AdditionalFielonldConfig(
      fielonldDisablingGatelons = twelonelontyPielonAdditionalFielonldsToDisablelon.map(_ -> Gatelon.Falselon).toMap
    ),
    includelonPartialRelonsults = Gatelon.Falselon,
    statsReloncelonivelonr = statsReloncelonivelonr
  )

  val uselonrMelontadataClielonntFactory: ScopelondUselonrMelontadataClielonntFactory =
    nelonw ScopelondUselonrMelontadataClielonntFactory(
      config.undelonrlyingClielonnts.manhattanStarbuckClielonnt,
      TimelonlinelonRankelonrConstants.ManhattanStarbuckAppId,
      statsReloncelonivelonr
    )

  val visibilityProfilelonHydratorFactory: VisibilityProfilelonHydratorFactory =
    nelonw VisibilityProfilelonHydratorFactory(
      gizmoduckClielonntFactory,
      socialGraphClielonntFactory,
      statsReloncelonivelonr
    )

  val relonalGraphClielonntFactory =
    nelonw ScopelondRelonalGraphClielonntFactory(config.undelonrlyingClielonnts.stratoClielonnt, statsReloncelonivelonr)
}
