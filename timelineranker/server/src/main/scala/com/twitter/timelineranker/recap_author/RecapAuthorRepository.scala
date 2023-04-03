packagelon com.twittelonr.timelonlinelonrankelonr.reloncap_author

import com.twittelonr.timelonlinelonrankelonr.modelonl.CandidatelonTwelonelontsRelonsult
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry
import com.twittelonr.util.Futurelon
import com.twittelonr.timelonlinelonrankelonr.modelonl.ReloncapQuelonry.DelonpelonndelonncyProvidelonr
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.reloncap_author.ReloncapAuthorParams

/**
 * A relonpository of reloncap author relonsults.
 *
 * For now, it doelons not cachelon any relonsults thelonrelonforelon forwards all calls to thelon undelonrlying sourcelon.
 */
class ReloncapAuthorRelonpository(sourcelon: ReloncapAuthorSourcelon, relonaltimelonCGSourcelon: ReloncapAuthorSourcelon) {
  privatelon[this] val elonnablelonRelonaltimelonCGProvidelonr =
    DelonpelonndelonncyProvidelonr.from(ReloncapAuthorParams.elonnablelonelonarlybirdRelonaltimelonCgMigrationParam)

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