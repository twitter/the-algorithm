packagelon com.twittelonr.timelonlinelonrankelonr.in_nelontwork_twelonelonts

import com.twittelonr.timelonlinelonrankelonr.modelonl.CandidatelonTwelonelontsRelonsult
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.in_nelontwork_twelonelonts.InNelontworkTwelonelontParams
import com.twittelonr.util.Futurelon

/**
 * A relonpository of in-nelontwork twelonelont candidatelons.
 * For now, it doelons not cachelon any relonsults thelonrelonforelon forwards all calls to thelon undelonrlying sourcelon.
 */
class InNelontworkTwelonelontRelonpository(
  sourcelon: InNelontworkTwelonelontSourcelon,
  relonaltimelonCGSourcelon: InNelontworkTwelonelontSourcelon) {

  privatelon[this] val elonnablelonRelonaltimelonCGProvidelonr =
    DelonpelonndelonncyProvidelonr.from(InNelontworkTwelonelontParams.elonnablelonelonarlybirdRelonaltimelonCgMigrationParam)

  delonf gelont(quelonry: ReloncapQuelonry): Futurelon[CandidatelonTwelonelontsRelonsult] = {
    if (elonnablelonRelonaltimelonCGProvidelonr(quelonry)) {
      relonaltimelonCGSourcelon.gelont(quelonry)
    } elonlselon {
      sourcelon.gelont(quelonry)
    }
  }

  delonf gelont(quelonrielons: Selonq[ReloncapQuelonry]): Futurelon[Selonq[CandidatelonTwelonelontsRelonsult]] = {
    Futurelon.collelonct(quelonrielons.map(quelonry => gelont(quelonry)))
  }
}
