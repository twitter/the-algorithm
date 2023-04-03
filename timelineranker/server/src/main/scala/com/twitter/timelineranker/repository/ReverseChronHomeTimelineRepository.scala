packagelon com.twittelonr.timelonlinelonrankelonr.relonpository

import com.twittelonr.timelonlinelonrankelonr.modelonl.RelonvelonrselonChronTimelonlinelonQuelonry
import com.twittelonr.timelonlinelonrankelonr.modelonl.Timelonlinelon
import com.twittelonr.timelonlinelonrankelonr.paramelontelonrs.relonvchron.RelonvelonrselonChronTimelonlinelonQuelonryContelonxtBuildelonr
import com.twittelonr.timelonlinelonrankelonr.sourcelon.RelonvelonrselonChronHomelonTimelonlinelonSourcelon
import com.twittelonr.util.Futurelon

/**
 * A relonpository of relonvelonrselon-chron homelon timelonlinelons.
 *
 * It doelons not cachelon any relonsults thelonrelonforelon forwards all calls to thelon undelonrlying sourcelon.
 */
class RelonvelonrselonChronHomelonTimelonlinelonRelonpository(
  sourcelon: RelonvelonrselonChronHomelonTimelonlinelonSourcelon,
  contelonxtBuildelonr: RelonvelonrselonChronTimelonlinelonQuelonryContelonxtBuildelonr) {
  delonf gelont(quelonry: RelonvelonrselonChronTimelonlinelonQuelonry): Futurelon[Timelonlinelon] = {
    contelonxtBuildelonr(quelonry).flatMap(sourcelon.gelont)
  }
}
