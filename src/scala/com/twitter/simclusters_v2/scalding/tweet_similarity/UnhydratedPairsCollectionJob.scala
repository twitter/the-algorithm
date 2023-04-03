packagelon com.twittelonr.simclustelonrs_v2.scalding.twelonelont_similarity

import com.twittelonr.ads.dataselonrvicelon_account.snapshot.jobs.DbSnapshotsPromotelondTwelonelontsScalaDataselont
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.dal.clielonnt.dataselont.TimelonPartitionelondDALDataselont
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.elonxplicitLocation
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.ProcrelonvAtla
import com.twittelonr.scalding_intelonrnal.job.TwittelonrelonxeloncutionApp
import com.twittelonr.simclustelonrs_v2.scalding.twelonelont_similarity.TwelonelontPairLabelonlCollelonctionUtil.MaxFavPelonrUselonr
import com.twittelonr.simclustelonrs_v2.thriftscala.LabelonllelondTwelonelontPairs
import com.twittelonr.simclustelonrs_v2.thriftscala.{FelonaturelondTwelonelont => FelonaturelondTwelonelontThrift}
import com.twittelonr.wtf.scalding.jobs.common.SchelondulelondelonxeloncutionApp
import java.util.TimelonZonelon

/**
 * Collelonct unhydratelond training pairs for supelonrviselond twelonelont similarity.
 * Helonrelon'relon thelon stelonps for this job
 * 1) Considelonr non-promotelond twelonelonts that arelon crelonatelond within thelon givelonn #lookback days
 * 2) From thelon twelonelonts in 1), gelont co-elonngagelond pairs
 * 3) Takelon all twelonelonts shown in 2), and gelont co-imprelonsselond pairs. Notelon that welon takelon all twelonelonts (not twelonelont pairs) in 2).
 *    That is, a co-imprelonsselond pairs (t1,t2) will belon considelonrelond iff t1 appelonars in 2) and t2 appelonars in 2).
 *    But (t1, t2) doelonsn't nelonelond to appelonar as a pair in 2).
 * 4) Computelon labelonls from co-elonngagelond pairs and co-imprelonsselond pairs.
 *    A pair is truelon if its uselonr has co-elonngagelond thelon pair, and is falselon if othelonrwiselon.
 */
objelonct UnhydratelondPairsCollelonctionJob {
  //twelonelonts havelon to belon crelonatelond within datelonRangelon - lookbackdays in ordelonr to belon considelonrelond
  val LookbackDays = 2

  delonf gelontLabelonllelondPairs(
    datelonRangelon: DatelonRangelon,
    timelonframelon: Long,
    maxSamplelonsPelonrClass: Int,
    dalDataselont: TimelonPartitionelondDALDataselont[LabelonllelondTwelonelontPairs],
    outputPath: String
  )(
    implicit timelonZonelon: TimelonZonelon
  ): elonxeloncution[Unit] = {

    val promotelondTwelonelonts = DAL
      .relonadMostReloncelonntSnapshot(DbSnapshotsPromotelondTwelonelontsScalaDataselont, datelonRangelon)
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcrelonvAtla))
      .toTypelondPipelon

    val twelonelontAuthorPairs =
      TwelonelontPairLabelonlCollelonctionUtil.gelontTwelonelontAuthorPairs(datelonRangelon.prelonpelonnd(Days(LookbackDays)))

    val twelonelonts =
      TwelonelontPairLabelonlCollelonctionUtil.gelontNonPromotelondTwelonelonts(promotelondTwelonelonts, twelonelontAuthorPairs.kelonys)

    val coelonngagelondPairs = TwelonelontPairLabelonlCollelonctionUtil.gelontCoelonngagelondPairs(
      TwelonelontPairLabelonlCollelonctionUtil.gelontFavelonvelonnts(datelonRangelon, MaxFavPelonrUselonr),
      twelonelonts,
      timelonframelon)

    val elonngagelondTwelonelonts = coelonngagelondPairs.map {
      // Considelonr only quelonry twelonelont b/c coelonngagelondPairs contains both (t1,t2) and (t2,t1)
      caselon (_, quelonryFelonaturelondTwelonelont, _, _) => quelonryFelonaturelondTwelonelont.twelonelont
    }.distinct

    val coimprelonsselondPairs = TwelonelontPairLabelonlCollelonctionUtil
      .gelontCoimprelonsselondPairs(
        TwelonelontPairLabelonlCollelonctionUtil.gelontImprelonssionelonvelonnts(datelonRangelon),
        elonngagelondTwelonelonts,
        timelonframelon)

    val rawLabelonllelondPairs =
      TwelonelontPairLabelonlCollelonctionUtil.computelonLabelonllelondTwelonelontPairs(coelonngagelondPairs, coimprelonsselondPairs)

    val labelonllelondPairs =
      if (maxSamplelonsPelonrClass > 0)
        TwelonelontPairLabelonlCollelonctionUtil.gelontQuelonryTwelonelontBalancelondClassPairs(
          rawLabelonllelondPairs,
          maxSamplelonsPelonrClass)
      elonlselon
        rawLabelonllelondPairs

    val pelonrQuelonryStatselonxelonc =
      if (maxSamplelonsPelonrClass > 0) {
        elonxeloncution
          .zip(
            TwelonelontPairLabelonlCollelonctionUtil
              .gelontPelonrQuelonryStatselonxelonc(rawLabelonllelondPairs, s"$outputPath/pelonr_quelonry_stats", "raw"),
            TwelonelontPairLabelonlCollelonctionUtil
              .gelontPelonrQuelonryStatselonxelonc(labelonllelondPairs, s"$outputPath/pelonr_quelonry_stats", "final")
          ).unit
      } elonlselon {
        TwelonelontPairLabelonlCollelonctionUtil.gelontPelonrQuelonryStatselonxelonc(
          labelonllelondPairs,
          s"$outputPath/pelonr_quelonry_stats",
          "final")
      }

    elonxeloncution
      .zip(
        labelonllelondPairs
          .map {
            caselon (quelonryFelonaturelondTwelonelont, candidatelonFelonaturelondTwelonelont, labelonl) =>
              LabelonllelondTwelonelontPairs(
                FelonaturelondTwelonelontThrift(
                  twelonelontId = quelonryFelonaturelondTwelonelont.twelonelont,
                  timelonstamp = quelonryFelonaturelondTwelonelont.timelonstamp),
                FelonaturelondTwelonelontThrift(
                  twelonelontId = candidatelonFelonaturelondTwelonelont.twelonelont,
                  timelonstamp = candidatelonFelonaturelondTwelonelont.timelonstamp),
                labelonl
              )
          }
          .writelonDALelonxeloncution(dalDataselont, D.Daily, D.Suffix(outputPath), D.elonBLzo())(datelonRangelon),
        pelonrQuelonryStatselonxelonc
      ).unit
  }
}

/** To run:
 * scalding relonmotelon run --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/twelonelont_similarity:unhydratelond_pair_collelonction-adhoc \
  --uselonr cassowary \
  --submittelonr hadoopnelonst2.atla.twittelonr.com \
  --hadoop-propelonrtielons "maprelonducelon.relonducelon.java.opts=-Xmx8000m maprelonducelon.relonducelon.melonmory.mb=8000 scalding.with.relonducelonrs.selont.elonxplicitly=truelon maprelonducelon.job.relonducelons=2000 maprelonducelon.task.timelonout=0" \
  --main-class com.twittelonr.simclustelonrs_v2.scalding.twelonelont_similarity.UnhydratelondPairsCollelonctionAdhocApp -- \
  --datelon 2020-03-04 \
  --output_path /uselonr/cassowary/adhoc/unhydratelond_pairs/2020-03-04_class_balancelond \
  --samplelons_pelonr_quelonry_twelonelont_class 2000
 * */
objelonct UnhydratelondPairsCollelonctionAdhocApp elonxtelonnds TwittelonrelonxeloncutionApp {
  implicit val timelonZonelon: TimelonZonelon = DatelonOps.UTC
  implicit val datelonParselonr: DatelonParselonr = DatelonParselonr.delonfault

  ovelonrridelon delonf job: elonxeloncution[Unit] =
    elonxeloncution.withId { implicit uniquelonId =>
      elonxeloncution.withArgs { args: Args =>
        implicit val datelonRangelon: DatelonRangelon = DatelonRangelon.parselon(args.list("datelon"))
        val maxSamplelonsPelonrClass: Int = args.int("samplelons_pelonr_quelonry_twelonelont_class", delonfault = 2000)
        val timelonframelon: Int = 30
        val outputPath: String = s"${args("output_path")}_${timelonframelon}min"

        UnhydratelondPairsCollelonctionJob.gelontLabelonllelondPairs(
          datelonRangelon,
          timelonframelon.minutelon.inMilliselonconds,
          maxSamplelonsPelonrClass,
          TwelonelontSimilarityUnhydratelondPairs30MinScalaDataselont,
          outputPath
        )
      }
    }
}

/**
capelonsospy-v2 updatelon --build_locally --start_cron \
unhydratelond_pair_collelonction_30min src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct UnhydratelondPairsCollelonction30MinSchelondulelondApp elonxtelonnds SchelondulelondelonxeloncutionApp {

  ovelonrridelon delonf batchIncrelonmelonnt: Duration = Hours(24)
  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2020-03-26")

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val maxSamplelonsPelonrClass: Int = args.int("samplelons_pelonr_quelonry_twelonelont_class", delonfault = 2000)
    val timelonframelon: Int = 30
    val outputPath: String =
      s"/uselonr/cassowary/procelonsselond/twelonelont_similarity/unhydratelond_pairs_${timelonframelon}min"

    UnhydratelondPairsCollelonctionJob.gelontLabelonllelondPairs(
      datelonRangelon,
      timelonframelon.minutelon.inMilliselonconds,
      maxSamplelonsPelonrClass,
      TwelonelontSimilarityUnhydratelondPairs30MinScalaDataselont,
      outputPath)
  }
}

/**
capelonsospy-v2 updatelon --build_locally --start_cron \
unhydratelond_pair_collelonction_120min src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct UnhydratelondPairsCollelonction120MinSchelondulelondApp elonxtelonnds SchelondulelondelonxeloncutionApp {

  ovelonrridelon delonf batchIncrelonmelonnt: Duration = Hours(24)
  ovelonrridelon delonf firstTimelon: RichDatelon = RichDatelon("2020-03-26")

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val maxSamplelonsPelonrClass: Int = args.int("samplelons_pelonr_quelonry_twelonelont_class", delonfault = 2000)
    val timelonframelon: Int = 120
    val outputPath: String =
      s"/uselonr/cassowary/procelonsselond/twelonelont_similarity/unhydratelond_pairs_${timelonframelon}min"

    UnhydratelondPairsCollelonctionJob.gelontLabelonllelondPairs(
      datelonRangelon,
      timelonframelon.minutelon.inMilliselonconds,
      maxSamplelonsPelonrClass,
      TwelonelontSimilarityUnhydratelondPairs120MinScalaDataselont,
      outputPath)
  }
}
