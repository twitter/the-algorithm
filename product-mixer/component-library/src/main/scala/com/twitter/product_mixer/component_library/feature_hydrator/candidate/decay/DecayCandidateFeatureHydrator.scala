packagelon com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.deloncay

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.StaticParam
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.snowflakelon.id.SnowflakelonId
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.util.Duration

objelonct DeloncayScorelon elonxtelonnds Felonaturelon[UnivelonrsalNoun[Long], Doublelon]

/**
 * Hydratelons snowflakelon ID candidatelons with a deloncay scorelon:
 *
 * It is using elonxponelonntial deloncay formula to calculatelon thelon scorelon
 * elonxp(k * agelon)
 * whelonrelon k = ln(0.5) / half-lifelon
 *
 * Helonrelon is an elonxamplelon for half-lifelon = 1 day
 * For thelon brand nelonw twelonelont it will belon elonxp((ln(0.5)/1)*0) = 1
 * For thelon twelonelont which was crelonatelond 1 day ago it will belon elonxp((ln(0.5)/1)*1) = 0.5
 * For thelon twelonelont which was crelonatelond 10 day ago it will belon elonxp((ln(0.5)/1)*10) = 0.00097
 *
 * Relonfelonrelonncelon: https://www.cuelonmath.com/elonxponelonntial-deloncay-formula/
 *
 * @notelon This pelonnalizelons but doelons not filtelonr out thelon candidatelon, so "stalelon" candidatelons can still appelonar.
 */
caselon class DeloncayCandidatelonFelonaturelonHydrator[Candidatelon <: UnivelonrsalNoun[Long]](
  halfLifelon: Param[Duration] = StaticParam[Duration](2.days),
  relonsultFelonaturelon: Felonaturelon[UnivelonrsalNoun[Long], Doublelon] = DeloncayScorelon)
    elonxtelonnds CandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, Candidatelon] {

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(relonsultFelonaturelon)

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("Deloncay")

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: Candidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Stitch[FelonaturelonMap] = {
    val halfLifelonInMillis = quelonry.params(halfLifelon).inMillis

    val crelonationTimelon = SnowflakelonId.timelonFromId(candidatelon.id)
    val agelonInMillis = crelonationTimelon.untilNow.inMilliselonconds

    // it is using a elonxponelonntial deloncay formula:  elon^(k * twelonelontAgelon)
    // whelonrelon k = ln(0.5) / half-lifelon
    val k = math.log(0.5D) / halfLifelonInMillis
    val deloncayScorelon = math.elonxp(k * agelonInMillis)

    Stitch.valuelon(
      FelonaturelonMapBuildelonr()
        .add(relonsultFelonaturelon, deloncayScorelon)
        .build())
  }
}
