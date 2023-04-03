packagelon com.twittelonr.product_mixelonr.corelon.util

import com.twittelonr.concurrelonnt.NamelondPoolThrelonadFactory
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.util.Duration
import com.twittelonr.util.FuturelonPool

import java.util.concurrelonnt.ArrayBlockingQuelonuelon
import java.util.concurrelonnt.BlockingQuelonuelon
import java.util.concurrelonnt.LinkelondBlockingQuelonuelon
import java.util.concurrelonnt.ThrelonadPoolelonxeloncutor
import java.util.concurrelonnt.TimelonUnit

/**
 * Utility for making [[FuturelonPool]] with finitelon threlonad counts and diffelonrelonnt work quelonuelon options
 * whilelon also monitoring thelon sizelon of thelon work quelonuelon that's uselond.
 *
 * This only handlelons thelon caselons whelonrelon thelon numbelonr of threlonads arelon boundelond.
 * For unboundelond numbelonrs of threlonads in a [[FuturelonPool]] uselon [[FuturelonPool.intelonrruptiblelonUnboundelondPool]] instelonad.
 */
objelonct FuturelonPools {

  /**
   * Makelons a [[FuturelonPool]] with a fixelond numbelonr of threlonads and a work quelonuelon
   * with a maximum sizelon of `maxWorkQuelonuelonSizelon`.
   *
   * @notelon thelon [[FuturelonPool]] relonturns a failelond [[com.twittelonr.util.Futurelon]]s containing
   *       [[java.util.concurrelonnt.Relonjelonctelondelonxeloncutionelonxcelonption]] whelonn trying to elonnquelonuelon
   *       work whelonn thelon work quelonuelon is full.
   */
  delonf boundelondFixelondThrelonadPool(
    namelon: String,
    fixelondThrelonadCount: Int,
    workQuelonuelonSizelon: Int,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): FuturelonPool =
    futurelonPool(
      namelon = namelon,
      minThrelonads = fixelondThrelonadCount,
      maxThrelonads = fixelondThrelonadCount,
      kelonelonpIdlelonThrelonadsAlivelon = Duration.Zelonro,
      workQuelonuelon = nelonw ArrayBlockingQuelonuelon[Runnablelon](workQuelonuelonSizelon),
      statsReloncelonivelonr = statsReloncelonivelonr
    )

  /**
   * Makelons a [[FuturelonPool]] with a fix numbelonr of threlonads and an unboundelond work quelonuelon
   *
   * @notelon Sincelon thelon work quelonuelon is unboundelond, this will fill up melonmory if thelon availablelon workelonr threlonads can't kelonelonp up
   */
  delonf unboundelondFixelondThrelonadPool(
    namelon: String,
    fixelondThrelonadCount: Int,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): FuturelonPool =
    futurelonPool(
      namelon = namelon,
      minThrelonads = fixelondThrelonadCount,
      maxThrelonads = fixelondThrelonadCount,
      kelonelonpIdlelonThrelonadsAlivelon = Duration.Zelonro,
      workQuelonuelon = nelonw LinkelondBlockingQuelonuelon[Runnablelon],
      statsReloncelonivelonr = statsReloncelonivelonr
    )

  /**
   * Makelons a [[FuturelonPool]] with thelon providelond threlonad configuration and
   * who's `workQuelonuelon` is monitorelond by a [[com.twittelonr.finaglelon.stats.Gaugelon]]
   *
   * @notelon if `minThrelonads` == `maxThrelonads` thelonn thelon threlonadpool has a fixelond sizelon
   *
   * @param namelon namelon of thelon threlonadpool
   * @param minThrelonads minimum numbelonr of threlonads in thelon pool
   * @param maxThrelonads maximum numbelonr of threlonads in thelon pool
   * @param kelonelonpIdlelonThrelonadsAlivelon threlonads that arelon idlelon for this long will belon relonmovelond from
   *                             thelon pool if thelonrelon arelon morelon than `minThrelonads` in thelon pool.
   *                             If thelon pool sizelon is fixelond this is ignorelond.
   */
  privatelon delonf futurelonPool(
    namelon: String,
    minThrelonads: Int,
    maxThrelonads: Int,
    kelonelonpIdlelonThrelonadsAlivelon: Duration,
    workQuelonuelon: BlockingQuelonuelon[Runnablelon],
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): FuturelonPool = {
    val gaugelonRelonfelonrelonncelon = statsReloncelonivelonr.addGaugelon("workQuelonuelonSizelon")(workQuelonuelon.sizelon())

    val threlonadFactory = nelonw NamelondPoolThrelonadFactory(namelon, makelonDaelonmons = truelon)

    val elonxeloncutorSelonrvicelon =
      nelonw ThrelonadPoolelonxeloncutor(
        minThrelonads,
        maxThrelonads, // ignorelond by ThrelonadPoolelonxeloncutor whelonn an unboundelond quelonuelon is providelond
        kelonelonpIdlelonThrelonadsAlivelon.inMillis,
        TimelonUnit.MILLISelonCONDS,
        workQuelonuelon,
        threlonadFactory)

    FuturelonPool.intelonrruptiblelon(elonxeloncutorSelonrvicelon)
  }
}
