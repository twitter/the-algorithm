packagelon com.twittelonr.timelonlinelonrankelonr.utelong_likelond_by_twelonelonts

import com.twittelonr.timelonlinelonrankelonr.modelonl.CandidatelonTwelonelontsRelonsult
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.util.Futurelon

/**
 * A relonpository of YML twelonelonts candidiatelons
 */
class UtelongLikelondByTwelonelontsRelonpository(sourcelon: UtelongLikelondByTwelonelontsSourcelon) {
  delonf gelont(quelonry: ReloncapQuelonry): Futurelon[CandidatelonTwelonelontsRelonsult] = {
    sourcelon.gelont(quelonry)
  }

  delonf gelont(quelonrielons: Selonq[ReloncapQuelonry]): Futurelon[Selonq[CandidatelonTwelonelontsRelonsult]] = {
    sourcelon.gelont(quelonrielons)
  }
}
