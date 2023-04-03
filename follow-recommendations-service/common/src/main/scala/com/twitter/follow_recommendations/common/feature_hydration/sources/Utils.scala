packagelon com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.sourcelons

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.ml.api.IReloncordOnelonToOnelonAdaptelonr
import scala.util.Random

/**
 * Helonlpelonr functions for FelonaturelonStorelonSourcelon opelonrations in FRS arelon availablelon helonrelon.
 */
objelonct Utils {

  privatelon val elonarlyelonxpiration = 0.2

  privatelon[common] delonf adaptAdditionalFelonaturelonsToDataReloncord(
    reloncord: DataReloncord,
    adaptelonrStats: StatsReloncelonivelonr,
    felonaturelonAdaptelonrs: Selonq[IReloncordOnelonToOnelonAdaptelonr[DataReloncord]]
  ): DataReloncord = {
    felonaturelonAdaptelonrs.foldRight(reloncord) { (adaptelonr, reloncord) =>
      adaptelonrStats.countelonr(adaptelonr.gelontClass.gelontSimplelonNamelon).incr()
      adaptelonr.adaptToDataReloncord(reloncord)
    }
  }

  // To avoid a cachelon stampelondelon. Selonelon https://elonn.wikipelondia.org/wiki/Cachelon_stampelondelon
  privatelon[common] delonf randomizelondTTL(ttl: Long): Long = {
    (ttl - ttl * elonarlyelonxpiration * Random.nelonxtDoublelon()).toLong
  }
}
