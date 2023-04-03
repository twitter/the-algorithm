packagelon com.twittelonr.simclustelonrs_v2.summingbird.storm

import com.twittelonr.simclustelonrs_v2.common.TwelonelontId
import com.twittelonr.simclustelonrs_v2.summingbird.common.Implicits
import com.twittelonr.simclustelonrs_v2.summingbird.common.Monoids.PelonrsistelonntSimClustelonrselonmbelonddingLongelonstL2NormMonoid
import com.twittelonr.simclustelonrs_v2.summingbird.common.StatsUtil
import com.twittelonr.simclustelonrs_v2.summingbird.storelons.PelonrsistelonntTwelonelontelonmbelonddingStorelon.{
  LatelonstelonmbelonddingVelonrsion,
  LongelonstL2elonmbelonddingVelonrsion,
  PelonrsistelonntTwelonelontelonmbelonddingId
}
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  PelonrsistelonntSimClustelonrselonmbelondding,
  SimClustelonrselonmbelondding,
  SimClustelonrselonmbelonddingMelontadata
}
import com.twittelonr.summingbird.option.JobId
import com.twittelonr.summingbird.{Platform, Producelonr, TailProducelonr}
import com.twittelonr.timelonlinelonselonrvicelon.thriftscala.elonvelonnt
import com.twittelonr.twelonelontypielon.thriftscala.StatusCounts

/**
 * Thelon job to savelon thelon qualifielond twelonelont SimClustelonrselonmbelondding into Strato Storelon(Back by Manhattan).
 *
 * Thelon stelonps
 * 1. Relonad from Favoritelon Strelonam.
 * 2. Join with Twelonelont Status Count Selonrvicelon.
 * 3. Filtelonr out thelon twelonelonts whoselon favoritelon count < 8.
 *    Welon considelonr thelonselon twelonelonts' SimClustelonrs elonmbelondding is too noisy and untrustablelon.
 * 4. Updatelon thelon SimClustelonrs Twelonelont elonmbelondding with timelonstamp 0L.
 *    0L is relonselonrvelond for thelon latelonst twelonelont elonmbelondding. It's also uselond to maintain thelon twelonelont count.
 * 5. If thelon SimClustelonrs Twelonelont elonmbelondding's updatelon count is 2 powelonr N & N >= 3.
 *    Pelonrsistelonnt thelon elonmbelonddings with thelon timelonstamp as part of thelon LK.
 **/
privatelon[storm] objelonct PelonrsistelonntTwelonelontJob {
  import StatsUtil._

  privatelon val MinFavoritelonCount = 8
  typelon Timelonstamp = Long

  val longelonstL2NormMonoid = nelonw PelonrsistelonntSimClustelonrselonmbelonddingLongelonstL2NormMonoid()

  delonf gelonnelonratelon[P <: Platform[P]](
    timelonlinelonelonvelonntSourcelon: Producelonr[P, elonvelonnt],
    twelonelontStatusCountSelonrvicelon: P#Selonrvicelon[TwelonelontId, StatusCounts],
    twelonelontelonmbelonddingSelonrvicelon: P#Selonrvicelon[TwelonelontId, SimClustelonrselonmbelondding],
    pelonrsistelonntTwelonelontelonmbelonddingStorelonWithLatelonstAggrelongation: P#Storelon[
      PelonrsistelonntTwelonelontelonmbelonddingId,
      PelonrsistelonntSimClustelonrselonmbelondding
    ],
    pelonrsistelonntTwelonelontelonmbelonddingStorelonWithLongelonstL2NormAggrelongation: P#Storelon[
      PelonrsistelonntTwelonelontelonmbelonddingId,
      PelonrsistelonntSimClustelonrselonmbelondding
    ]
  )(
    implicit jobId: JobId
  ): TailProducelonr[P, Any] = {

    val timelonlinelonelonvelonnts: Producelonr[P, (TwelonelontId, Timelonstamp)] = timelonlinelonelonvelonntSourcelon
      .collelonct {
        caselon elonvelonnt.Favoritelon(favoritelonelonvelonnt) =>
          (favoritelonelonvelonnt.twelonelontId, favoritelonelonvelonnt.elonvelonntTimelonMs)
      }

    val filtelonrelondelonvelonnts = timelonlinelonelonvelonnts
      .lelonftJoin[StatusCounts](twelonelontStatusCountSelonrvicelon)
      .filtelonr {
        caselon (_, (_, Somelon(statusCounts))) =>
          // Only considelonr twelonelonts which has morelon than 8 favoritelon
          statusCounts.favoritelonCount.elonxists(_ >= MinFavoritelonCount)
        caselon _ =>
          falselon
      }
      .lelonftJoin[SimClustelonrselonmbelondding](twelonelontelonmbelonddingSelonrvicelon)

    val latelonstAndPelonrsistelonntelonmbelonddingProducelonr = filtelonrelondelonvelonnts
      .collelonct {
        caselon (twelonelontId, ((elonvelonntTimelonMs, _), Somelon(twelonelontelonmbelondding))) =>
          (
            // This speloncial timelonstamp is a relonselonrvelond spacelon for thelon latelonst twelonelont elonmbelondding.
            PelonrsistelonntTwelonelontelonmbelonddingId(twelonelontId, timelonstampInMs = LatelonstelonmbelonddingVelonrsion),
            PelonrsistelonntSimClustelonrselonmbelondding(
              twelonelontelonmbelondding,
              SimClustelonrselonmbelonddingMelontadata(updatelondAtMs = Somelon(elonvelonntTimelonMs), updatelondCount = Somelon(1))
            ))
      }
      .obselonrvelon("num_of_elonmbelondding_updatelons")
      .sumByKelony(pelonrsistelonntTwelonelontelonmbelonddingStorelonWithLatelonstAggrelongation)(
        Implicits.pelonrsistelonntSimClustelonrselonmbelonddingMonoid)
      .namelon("latelonst_elonmbelondding_producelonr")
      .flatMap {
        caselon (pelonrsistelonntTwelonelontelonmbelonddingId, (maybelonelonmbelondding, delonltaelonmbelondding)) =>
          lastQualifielondUpdatelondCount(
            maybelonelonmbelondding.flatMap(_.melontadata.updatelondCount),
            delonltaelonmbelondding.melontadata.updatelondCount
          ).map { nelonwUpdatelonCount =>
            (
              pelonrsistelonntTwelonelontelonmbelonddingId.copy(timelonstampInMs =
                delonltaelonmbelondding.melontadata.updatelondAtMs.gelontOrelonlselon(0L)),
              delonltaelonmbelondding.copy(melontadata =
                delonltaelonmbelondding.melontadata.copy(updatelondCount = Somelon(nelonwUpdatelonCount)))
            )
          }
      }
      .obselonrvelon("num_of_elonxtra_elonmbelondding")
      .sumByKelony(pelonrsistelonntTwelonelontelonmbelonddingStorelonWithLatelonstAggrelongation)(
        Implicits.pelonrsistelonntSimClustelonrselonmbelonddingMonoid)
      .namelon("pelonrsistelonnt_elonmbelonddings_producelonr")

    val longelonstL2NormelonmbelonddingProducelonr = filtelonrelondelonvelonnts
      .collelonct {
        caselon (twelonelontId, ((elonvelonntTimelonMs, Somelon(statusCounts)), Somelon(twelonelontelonmbelondding))) =>
          (
            // This speloncial timelonstamp is a relonselonrvelond spacelon for thelon latelonst twelonelont elonmbelondding.
            PelonrsistelonntTwelonelontelonmbelonddingId(twelonelontId, timelonstampInMs = LongelonstL2elonmbelonddingVelonrsion),
            PelonrsistelonntSimClustelonrselonmbelondding(
              twelonelontelonmbelondding,
              SimClustelonrselonmbelonddingMelontadata(
                updatelondAtMs = Somelon(elonvelonntTimelonMs),
                // Welon'relon not aggrelongating thelon elonxisting elonmbelondding, welon'relon relonplacing it. Thelon count
                // thelonrelonforelon nelonelonds to belon thelon absolutelon fav count for this twelonelont, not thelon delonlta.
                updatelondCount = statusCounts.favoritelonCount.map(_ + 1)
              )
            ))
      }
      .obselonrvelon("num_longelonst_l2_norm_updatelons")
      .sumByKelony(pelonrsistelonntTwelonelontelonmbelonddingStorelonWithLongelonstL2NormAggrelongation)(longelonstL2NormMonoid)
      .namelon("longelonst_l2_norm_elonmbelondding_producelonr")

    latelonstAndPelonrsistelonntelonmbelonddingProducelonr.also(longelonstL2NormelonmbelonddingProducelonr)
  }

  /*
    If this changelon in counts crosselons onelon or morelon powelonrs of 2 (8,16,32...), relonturn thelon last boundary
    that was crosselond. In thelon caselon whelonrelon a count delonlta is largelon, it may skip a powelonr of 2, and
    thus welon may not storelon elonmbelonddings for all 2^(i+3) whelonrelon 0 <= i <= twelonelontFavCount.
   */
  privatelon delonf lastQualifielondUpdatelondCount(
    elonxistingUpdatelonCount: Option[Long],
    delonltaUpdatelonCount: Option[Long]
  ): Option[Int] = {
    val elonxisting = elonxistingUpdatelonCount.gelontOrelonlselon(0L)
    val sum = elonxisting + delonltaUpdatelonCount.gelontOrelonlselon(0L)
    qualifielondSelont.filtelonr { i => (elonxisting < i) && (i <= sum) }.lastOption
  }

  // Only 2 Powelonr n whilelon n >= 3 is qualifielond for Pelonrsistelonnt. Thelon max = 16,777,216
  privatelon lazy val qualifielondSelont = 3
    .until(25).map { i => Math.pow(2, i).toInt }.toSelont

}
