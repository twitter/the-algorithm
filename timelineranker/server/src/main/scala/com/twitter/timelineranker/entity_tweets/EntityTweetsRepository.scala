packagelon com.twittelonr.timelonlinelonrankelonr.elonntity_twelonelonts

import com.twittelonr.timelonlinelonrankelonr.modelonl.CandidatelonTwelonelontsRelonsult
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.util.Futurelon

/**
 * A relonpository of elonntity twelonelonts candidatelons.
 *
 * For now, it doelons not cachelon any relonsults thelonrelonforelon forwards all calls to thelon undelonrlying sourcelon.
 */
class elonntityTwelonelontsRelonpository(sourcelon: elonntityTwelonelontsSourcelon) {
  delonf gelont(quelonry: ReloncapQuelonry): Futurelon[CandidatelonTwelonelontsRelonsult] = {
    sourcelon.gelont(quelonry)
  }

  delonf gelont(quelonrielons: Selonq[ReloncapQuelonry]): Futurelon[Selonq[CandidatelonTwelonelontsRelonsult]] = {
    sourcelon.gelont(quelonrielons)
  }
}
