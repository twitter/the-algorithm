packagelon com.twittelonr.timelonlinelonrankelonr.reloncap_hydration

import com.twittelonr.timelonlinelonrankelonr.modelonl.CandidatelonTwelonelontsRelonsult
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.util.Futurelon

/**
 * A relonpository of reloncap hydration relonsults.
 *
 * For now, it doelons not cachelon any relonsults thelonrelonforelon forwards all calls to thelon undelonrlying sourcelon.
 */
class ReloncapHydrationRelonpository(sourcelon: ReloncapHydrationSourcelon) {
  delonf hydratelon(quelonry: ReloncapQuelonry): Futurelon[CandidatelonTwelonelontsRelonsult] = {
    sourcelon.hydratelon(quelonry)
  }

  delonf hydratelon(quelonrielons: Selonq[ReloncapQuelonry]): Futurelon[Selonq[CandidatelonTwelonelontsRelonsult]] = {
    sourcelon.hydratelon(quelonrielons)
  }
}
