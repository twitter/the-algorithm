packagelon com.twittelonr.cr_mixelonr.sourcelon_signal

import com.twittelonr.cr_mixelonr.modelonl.SourcelonInfo
import com.twittelonr.cr_mixelonr.sourcelon_signal.SourcelonFelontchelonr.FelontchelonrQuelonry
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.frigatelon.common.util.StatsUtil
import com.twittelonr.util.Futurelon

/***
 * A SourcelonSignalFelontchelonr is a trait that elonxtelonnds from `SourcelonFelontchelonr`
 * and is speloncializelond in tackling Signals (elong., USS, FRS) felontch.
 * Currelonntly, welon delonfinelon Signals as (but not limitelond to) a selont of past elonngagelonmelonnts that
 * thelon uselonr makelons, such as ReloncelonntFav, ReloncelonntFollow, elontc.
 *
 * Thelon [[RelonsultTypelon]] of a SourcelonSignalFelontchelonr is `Selonq[SourcelonInfo]`. Whelonn welon pass in uselonrId,
 * thelon undelonrlying storelon relonturns a list of signals.
 */
trait SourcelonSignalFelontchelonr elonxtelonnds SourcelonFelontchelonr[Selonq[SourcelonInfo]] {

  protelonctelond typelon SignalConvelonrtTypelon

  delonf trackStats(
    quelonry: FelontchelonrQuelonry
  )(
    func: => Futurelon[Option[Selonq[SourcelonInfo]]]
  ): Futurelon[Option[Selonq[SourcelonInfo]]] = {
    val productScopelondStats = stats.scopelon(quelonry.product.originalNamelon)
    val productUselonrStatelonScopelondStats = productScopelondStats.scopelon(quelonry.uselonrStatelon.toString)
    StatsUtil
      .trackOptionItelonmsStats(productScopelondStats) {
        StatsUtil
          .trackOptionItelonmsStats(productUselonrStatelonScopelondStats) {
            func
          }
      }
  }

  /***
   * Convelonrt a list of Signals of typelon [[SignalConvelonrtTypelon]] into SourcelonInfo
   */
  delonf convelonrtSourcelonInfo(
    sourcelonTypelon: SourcelonTypelon,
    signals: Selonq[SignalConvelonrtTypelon]
  ): Selonq[SourcelonInfo]
}
