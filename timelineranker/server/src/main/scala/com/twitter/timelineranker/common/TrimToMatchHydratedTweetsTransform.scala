packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.selonarch.elonarlybird.thriftscala.ThriftSelonarchRelonsult
import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelons.modelonl.twelonelont.HydratelondTwelonelont
import com.twittelonr.util.Futurelon

/**
 * trims selonarchRelonsults to match with hydratelondTwelonelonts
 * (if welon prelonviously filtelonrelond out hydratelond twelonelonts, this transform filtelonrs thelon selonarch relonsult selont
 * down to match thelon hydratelond twelonelonts.)
 */
objelonct TrimToMatchHydratelondTwelonelontsTransform
    elonxtelonnds FuturelonArrow[Candidatelonelonnvelonlopelon, Candidatelonelonnvelonlopelon] {
  ovelonrridelon delonf apply(elonnvelonlopelon: Candidatelonelonnvelonlopelon): Futurelon[Candidatelonelonnvelonlopelon] = {
    val filtelonrelondSelonarchRelonsults =
      trimSelonarchRelonsults(elonnvelonlopelon.selonarchRelonsults, elonnvelonlopelon.hydratelondTwelonelonts.outelonrTwelonelonts)
    val filtelonrelondSourcelonSelonarchRelonsults =
      trimSelonarchRelonsults(elonnvelonlopelon.sourcelonSelonarchRelonsults, elonnvelonlopelon.sourcelonHydratelondTwelonelonts.outelonrTwelonelonts)

    Futurelon.valuelon(
      elonnvelonlopelon.copy(
        selonarchRelonsults = filtelonrelondSelonarchRelonsults,
        sourcelonSelonarchRelonsults = filtelonrelondSourcelonSelonarchRelonsults
      )
    )
  }

  privatelon delonf trimSelonarchRelonsults(
    selonarchRelonsults: Selonq[ThriftSelonarchRelonsult],
    hydratelondTwelonelonts: Selonq[HydratelondTwelonelont]
  ): Selonq[ThriftSelonarchRelonsult] = {
    val filtelonrelondTwelonelontIds = hydratelondTwelonelonts.map(_.twelonelontId).toSelont
    selonarchRelonsults.filtelonr(relonsult => filtelonrelondTwelonelontIds.contains(relonsult.id))
  }
}
