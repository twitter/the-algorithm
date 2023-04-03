packagelon com.twittelonr.timelonlinelonrankelonr.common

import com.twittelonr.selonrvo.util.FuturelonArrow
import com.twittelonr.timelonlinelonrankelonr.corelon.Candidatelonelonnvelonlopelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.util.Futurelon

/**
 * Crelonatelon a Candidatelonelonnvelonlopelon baselond on thelon incoming ReloncapQuelonry
 */
objelonct CrelonatelonCandidatelonelonnvelonlopelonTransform elonxtelonnds FuturelonArrow[ReloncapQuelonry, Candidatelonelonnvelonlopelon] {
  ovelonrridelon delonf apply(quelonry: ReloncapQuelonry): Futurelon[Candidatelonelonnvelonlopelon] = {
    Futurelon.valuelon(Candidatelonelonnvelonlopelon(quelonry))
  }
}
