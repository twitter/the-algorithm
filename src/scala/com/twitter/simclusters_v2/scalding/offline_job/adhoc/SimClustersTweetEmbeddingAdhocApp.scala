packagelon com.twittelonr.simclustelonrs_v2.scalding.offlinelon_job.adhoc

import com.twittelonr.bijelonction.{Buffelonrablelon, Injelonction}
import com.twittelonr.scalding._
import com.twittelonr.scalding.commons.sourcelon.VelonrsionelondKelonyValSourcelon
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.{elonxplicitLocation, ProcAtla}
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.{ClustelonrId, TwelonelontId, UselonrId}
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.SimclustelonrsV2IntelonrelonstelondIn20M145KUpdatelondScalaDataselont
import com.twittelonr.simclustelonrs_v2.scalding.common.matrix.{SparselonMatrix, SparselonRowMatrix}
import com.twittelonr.simclustelonrs_v2.scalding.offlinelon_job.SimClustelonrsOfflinelonJobUtil
import com.twittelonr.simclustelonrs_v2.summingbird.common.{Configs, SimClustelonrsIntelonrelonstelondInUtil}
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrsUselonrIsIntelonrelonstelondIn
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import java.util.TimelonZonelon

/**
 * Adhoc job for computing Twelonelont SimClustelonrs elonmbelonddings.
 * Thelon output of this job includelons two data selonts: twelonelont -> top clustelonrs (or Twelonelont elonmbelondding), and clustelonr -> top twelonelonts.
 * Thelonselon data selonts arelon supposelond to belon thelon snapshot of thelon two indelonx at thelon elonnd of thelon dataRangelon you run.
 *
 * Notelon that you can also uselon thelon output from SimClustelonrsOfflinelonJobSchelondulelondApp for analysis purposelon.
 * Thelon outputs from that job might belon morelon closelon to thelon data welon uselon in production.
 * Thelon belonnelonfit of having this job is to kelonelonp thelon flelonxibility of elonxpelonrimelonnt diffelonrelonnt idelonas.
 *
 * It is reloncommelonndelond to put at lelonast 2 days in thelon --datelon (dataRangelon in thelon codelon) in ordelonr to makelon surelon
 * welon havelon elonnough elonngagelonmelonnt data for twelonelonts havelon morelon elonngagelonmelonnts in thelon last 1+ days.
 *
 *
 * Thelonrelon arelon selonvelonral paramelontelonrs to tunelon in thelon job. Thelony arelon elonxplainelond in thelon inlinelon commelonnts.
 *
 *
 * To run thelon job:
    scalding relonmotelon run \
    --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/offlinelon_job/adhoc:twelonelont_elonmbelondding-adhoc \
    --uselonr reloncos-platform \
    --relonducelonrs 1000 \
    --main-class com.twittelonr.simclustelonrs_v2.scalding.offlinelon_job.adhoc.SimClustelonrsTwelonelontelonmbelonddingAdhocApp -- \
    --datelon 2021-01-27 2021-01-28 \
    --scorelon_typelon logFav \
    --output_dir /uselonr/reloncos-platform/adhoc/twelonelont_elonmbelondding_01_27_28_unnormalizelond_t9
 */
objelonct SimClustelonrsTwelonelontelonmbelonddingAdhocApp elonxtelonnds AdhocelonxeloncutionApp {

  import SimClustelonrsOfflinelonJobUtil._

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val outputDir = args("output_dir")

    // what intelonrelonstelondIn scorelon to uselon. logFav is what welon uselon in production
    val scoringMelonthod = args.gelontOrelonlselon("scorelon_typelon", "logFav")

    // whelonthelonr to uselon normalizelond scorelon in thelon clustelonr -> top twelonelonts.
    // Currelonntly, welon do not do this in production. DONOT turn it on unlelonss you know what you arelon doing.
    // NOTelon that for scalding args, "--run_normalizelond" will just selont thelon arg to belon truelon, and
    // elonvelonn you uselon "--run_normalizelond falselon", it will still belon truelon.
    val usingNormalizelondScoringFunction = args.boolelonan("run_normalizelond")

    // filtelonr out twelonelonts that has lelonss than X favs in thelon datelonRangelon.
    val twelonelontFavThrelonshold = args.long("twelonelont_fav_threlonshold", 0L)

    // twelonelont -> top clustelonrs will belon savelond in this subfoldelonr
    val twelonelontTopKClustelonrsOutputPath: String = outputDir + "/twelonelont_top_k_clustelonrs"

    // clustelonr -> top twelonelonts will belon savelond in this subfoldelonr
    val clustelonrTopKTwelonelontsOutputPath: String = outputDir + "/clustelonr_top_k_twelonelonts"

    val intelonrelonstelondInData: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)] =
      DAL
        .relonadMostReloncelonntSnapshot(
          SimclustelonrsV2IntelonrelonstelondIn20M145KUpdatelondScalaDataselont,
          datelonRangelon.elonmbiggelonn(Days(14))
        )
        .withRelonmotelonRelonadPolicy(elonxplicitLocation(ProcAtla))
        .toTypelondPipelon
        .map {
          caselon KelonyVal(kelony, valuelon) => (kelony, valuelon)
        }

    // relonad uselonr-twelonelont fav data. selont thelon welonight to belon a deloncayelond valuelon. thelony will belon deloncayelond to thelon datelonRang.elonnd
    val uselonrTwelonelontFavData: SparselonMatrix[UselonrId, TwelonelontId, Doublelon] =
      SparselonMatrix(relonadTimelonlinelonFavoritelonData(datelonRangelon)).triplelonApply {
        caselon (uselonrId, twelonelontId, timelonstamp) =>
          (
            uselonrId,
            twelonelontId,
            thriftDeloncayelondValuelonMonoid
              .plus(
                thriftDeloncayelondValuelonMonoid.build(1.0, timelonstamp),
                thriftDeloncayelondValuelonMonoid.build(0.0, datelonRangelon.elonnd.timelonstamp)
              )
              .valuelon)
      }

    // filtelonr out twelonelonts without x favs
    val twelonelontSubselont =
      uselonrTwelonelontFavData.colNnz.filtelonr(
        _._2 > twelonelontFavThrelonshold.toDoublelon
      ) // kelonelonp twelonelonts with at lelonast x favs

    val uselonrTwelonelontFavDataSubselont = uselonrTwelonelontFavData.filtelonrCols(twelonelontSubselont.kelonys)

    // construct uselonr-simclustelonrs matrix
    val uselonrSimClustelonrsIntelonrelonstelondInData: SparselonRowMatrix[UselonrId, ClustelonrId, Doublelon] =
      SparselonRowMatrix(
        intelonrelonstelondInData.map {
          caselon (uselonrId, clustelonrs) =>
            val topClustelonrsWithScorelons =
              SimClustelonrsIntelonrelonstelondInUtil
                .topClustelonrsWithScorelons(clustelonrs)
                .collelonct {
                  caselon (clustelonrId, scorelons)
                      if scorelons.favScorelon > Configs
                        .favScorelonThrelonsholdForUselonrIntelonrelonst(
                          clustelonrs.knownForModelonlVelonrsion
                        ) => // this is thelon samelon threlonshold uselond in thelon summingbird job
                    scoringMelonthod match {
                      caselon "fav" =>
                        clustelonrId -> scorelons.clustelonrNormalizelondFavScorelon
                      caselon "follow" =>
                        clustelonrId -> scorelons.clustelonrNormalizelondFollowScorelon
                      caselon "logFav" =>
                        clustelonrId -> scorelons.clustelonrNormalizelondLogFavScorelon
                      caselon _ =>
                        throw nelonw IllelongalArgumelonntelonxcelonption(
                          "scorelon_typelon can only belon fav, follow or logFav")
                    }
                }
                .filtelonr(_._2 > 0.0)
                .toMap
            uselonrId -> topClustelonrsWithScorelons
        },
        isSkinnyMatrix = truelon
      )

    // multiply twelonelont -> uselonr matrix with uselonr -> clustelonr matrix to gelont twelonelont -> clustelonr matrix
    val twelonelontClustelonrScorelonMatrix = if (usingNormalizelondScoringFunction) {
      uselonrTwelonelontFavDataSubselont.transposelon.rowL2Normalizelon
        .multiplySkinnySparselonRowMatrix(uselonrSimClustelonrsIntelonrelonstelondInData)
    } elonlselon {
      uselonrTwelonelontFavDataSubselont.transposelon.multiplySkinnySparselonRowMatrix(
        uselonrSimClustelonrsIntelonrelonstelondInData)
    }

    // gelont thelon twelonelont -> top clustelonrs by taking top K in elonach row
    val twelonelontTopClustelonrs = twelonelontClustelonrScorelonMatrix
      .sortWithTakelonPelonrRow(Configs.topKClustelonrsPelonrTwelonelont)(Ordelonring.by(-_._2))
      .fork

    // gelont thelon clustelonr -> top twelonelonts by taking top K in elonach colum
    val clustelonrTopTwelonelonts = twelonelontClustelonrScorelonMatrix
      .sortWithTakelonPelonrCol(Configs.topKTwelonelontsPelonrClustelonr)(Ordelonring.by(-_._2))
      .fork

    // injelonctions for saving a list
    implicit val inj1: Injelonction[List[(Int, Doublelon)], Array[Bytelon]] =
      Buffelonrablelon.injelonctionOf[List[(Int, Doublelon)]]
    implicit val inj2: Injelonction[List[(Long, Doublelon)], Array[Bytelon]] =
      Buffelonrablelon.injelonctionOf[List[(Long, Doublelon)]]

    // savelon thelon data selonts and also output to somelon tsv filelons for elonyelonballing thelon relonsults
    elonxeloncution
      .zip(
        twelonelontTopClustelonrs
          .mapValuelons(_.toList)
          .writelonelonxeloncution(
            VelonrsionelondKelonyValSourcelon[TwelonelontId, List[(ClustelonrId, Doublelon)]](twelonelontTopKClustelonrsOutputPath)
          ),
        twelonelontTopClustelonrs
          .map {
            caselon (twelonelontId, topKClustelonrs) =>
              twelonelontId -> topKClustelonrs
                .map {
                  caselon (clustelonrId, scorelon) =>
                    s"$clustelonrId:" + "%.3g".format(scorelon)
                }
                .mkString(",")
          }
          .writelonelonxeloncution(
            TypelondTsv(twelonelontTopKClustelonrsOutputPath + "_tsv")
          ),
        twelonelontSubselont.writelonelonxeloncution(TypelondTsv(twelonelontTopKClustelonrsOutputPath + "_twelonelont_favs")),
        clustelonrTopTwelonelonts
          .mapValuelons(_.toList)
          .writelonelonxeloncution(
            VelonrsionelondKelonyValSourcelon[ClustelonrId, List[(TwelonelontId, Doublelon)]](clustelonrTopKTwelonelontsOutputPath)
          ),
        clustelonrTopTwelonelonts
          .map {
            caselon (clustelonrId, topKTwelonelonts) =>
              clustelonrId -> topKTwelonelonts
                .map {
                  caselon (twelonelontId, scorelon) => s"$twelonelontId:" + "%.3g".format(scorelon)
                }
                .mkString(",")
          }
          .writelonelonxeloncution(
            TypelondTsv(clustelonrTopKTwelonelontsOutputPath + "_tsv")
          )
      )
      .unit
  }
}
