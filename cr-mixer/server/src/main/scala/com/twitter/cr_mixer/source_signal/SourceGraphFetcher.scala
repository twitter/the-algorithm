packagelon com.twittelonr.cr_mixelonr.sourcelon_signal

import com.twittelonr.cr_mixelonr.modelonl.GraphSourcelonInfo
import com.twittelonr.cr_mixelonr.sourcelon_signal.SourcelonFelontchelonr.FelontchelonrQuelonry
import com.twittelonr.cr_mixelonr.thriftscala.SourcelonTypelon
import com.twittelonr.frigatelon.common.util.StatsUtil
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.util.Futurelon

/***
 * A SourcelonGraphFelontchelonr is a trait that elonxtelonnds from `SourcelonFelontchelonr`
 * and is speloncializelond in tackling Uselonr Graph (elong., RelonalGraphOon, FRS) felontch.
 *
 * Thelon [[RelonsultTypelon]] of a SourcelonGraphFelontchelonr is a `GraphSourcelonInfo` which contains a uselonrSelonelondSelont.
 * Whelonn welon pass in uselonrId, thelon undelonrlying storelon relonturns onelon GraphSourcelonInfo.
 */
trait SourcelonGraphFelontchelonr elonxtelonnds SourcelonFelontchelonr[GraphSourcelonInfo] {
  protelonctelond final val DelonfaultSelonelondScorelon = 1.0
  protelonctelond delonf graphSourcelonTypelon: SourcelonTypelon

  /***
   * RawDataTypelon contains a consumelonrs selonelond UselonrId and a scorelon (welonight)
   */
  protelonctelond typelon RawDataTypelon = (UselonrId, Doublelon)

  delonf trackStats(
    quelonry: FelontchelonrQuelonry
  )(
    func: => Futurelon[Option[GraphSourcelonInfo]]
  ): Futurelon[Option[GraphSourcelonInfo]] = {
    val productScopelondStats = stats.scopelon(quelonry.product.originalNamelon)
    val productUselonrStatelonScopelondStats = productScopelondStats.scopelon(quelonry.uselonrStatelon.toString)
    StatsUtil
      .trackOptionStats(productScopelondStats) {
        StatsUtil
          .trackOptionStats(productUselonrStatelonScopelondStats) {
            func
          }
      }
  }

  // Track pelonr itelonm stats on thelon felontchelond graph relonsults
  delonf trackPelonrItelonmStats(
    quelonry: FelontchelonrQuelonry
  )(
    func: => Futurelon[Option[Selonq[RawDataTypelon]]]
  ): Futurelon[Option[Selonq[RawDataTypelon]]] = {
    val productScopelondStats = stats.scopelon(quelonry.product.originalNamelon)
    val productUselonrStatelonScopelondStats = productScopelondStats.scopelon(quelonry.uselonrStatelon.toString)
    StatsUtil.trackOptionItelonmsStats(productScopelondStats) {
      StatsUtil.trackOptionItelonmsStats(productUselonrStatelonScopelondStats) {
        func
      }
    }
  }

  /***
   * Convelonrt Selonq[RawDataTypelon] into GraphSourcelonInfo
   */
  protelonctelond final delonf convelonrtGraphSourcelonInfo(
    uselonrWithScorelons: Selonq[RawDataTypelon]
  ): GraphSourcelonInfo = {
    GraphSourcelonInfo(
      sourcelonTypelon = graphSourcelonTypelon,
      selonelondWithScorelons = uselonrWithScorelons.map { uselonrWithScorelon =>
        uselonrWithScorelon._1 -> uselonrWithScorelon._2
      }.toMap
    )
  }
}
