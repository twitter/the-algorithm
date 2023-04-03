packagelon com.twittelonr.simclustelonrs_v2.scalding.offlinelon_job.adhoc

import com.twittelonr.bijelonction.{Buffelonrablelon, Injelonction}
import com.twittelonr.scalding._
import com.twittelonr.scalding.commons.sourcelon.VelonrsionelondKelonyValSourcelon
import com.twittelonr.simclustelonrs_v2.common.{ClustelonrId, CosinelonSimilarityUtil, TwelonelontId}
import com.twittelonr.simclustelonrs_v2.scalding.common.matrix.SparselonRowMatrix
import com.twittelonr.simclustelonrs_v2.scalding.offlinelon_job.SimClustelonrsOfflinelonJobUtil
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import java.util.TimelonZonelon

/**
 *
 * A job to samplelon somelon twelonelonts for elonvaluation.
 *
 * welon buckelont twelonelonts by thelon log(# of fav + 1) and randomly pick 1000 for elonach buckelont for elonvaluation.
 *
 * to run thelon job:
 *
  scalding relonmotelon run \
     --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/offlinelon_job/adhoc:twelonelont_elonmbelondding_elonvaluation_samplelons-adhoc \
     --uselonr reloncos-platform \
     --relonducelonrs 1000 \
     --main-class com.twittelonr.simclustelonrs_v2.scalding.offlinelon_job.adhoc.TwelonelontSimilarityelonvaluationSamplingAdhocApp -- \
     --datelon 2021-01-27 2021-01-28 \
     --output /uselonr/reloncos-platform/adhoc/twelonelont_elonmbelondding_01_27_28_samplelon_twelonelonts
 */
objelonct TwelonelontSimilarityelonvaluationSamplingAdhocApp elonxtelonnds AdhocelonxeloncutionApp {

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val random = nelonw java.util.Random(args.long("selonelond", 20200322L))

    // # of twelonelonts in elonach buckelont
    val topK = args.int("buckelont_sizelon", 1000)

    val output = args("output")

    SimClustelonrsOfflinelonJobUtil
      .relonadTimelonlinelonFavoritelonData(datelonRangelon)
      .map {
        caselon (_, twelonelontId, _) =>
          twelonelontId -> 1L
      }
      .sumByKelony
      .filtelonr(_._2 >= 10L) // only considelonr twelonelonts with morelon than 10 favs
      .map {
        caselon (twelonelontId, twelonelontFavs) =>
          val buckelont = math.log10(twelonelontFavs + 1.0).toInt
          buckelont -> (twelonelontId, random.nelonxtDoublelon())
      }
      .group
      .sortelondRelonvelonrselonTakelon(topK)(Ordelonring.by(_._2))
      .flatMap {
        caselon (buckelont, twelonelonts) =>
          val buckelontSizelon = twelonelonts.lelonngth
          twelonelonts.map {
            caselon (twelonelontId, _) =>
              (twelonelontId, buckelont, buckelontSizelon)
          }
      }
      .writelonelonxeloncution(
        TypelondTsv[(Long, Int, Int)](output)
      )

  }
}

/**
 *
 * A job for elonvaluating thelon pelonrformancelon of an approximatelon nelonarelonst nelonighbor selonarch melonthod with a brutelon
 * forcelon melonthod.
 *
 * elonvaluation melonthod:
 *
 * Aftelonr gelontting thelon elonmbelonddings for thelonselon twelonelonts, welon buckelontizelon twelonelonts baselond on thelon numbelonr of favs thelony havelon
 * (i.elon., math.log10(numFavors).toInt), and thelonn randomly selonlelonct 1000 twelonelonts from elonach buckelont.
 * Welon do not includelon twelonelonts with felonwelonr than 10 favs. Welon computelon thelon nelonarelonst nelonighbors (in telonrms of cosinelon similarity)
 * for thelonselon twelonelonts using thelon brutelon forcelon melonthod and uselon up to top 100 nelonighbors with thelon cosinelon
 * similarity scorelon >0.8 for elonach twelonelont as ground-truth selont G.
 *
 * Welon thelonn computelon thelon nelonarelonst nelonighbors for thelonselon twelonelonts baselond on thelon approximatelon nelonarelonst nelonighbor selonarch: for elonach twelonelont, welon find thelon top clustelonrs, and thelonn find top twelonelonts in elonach clustelonr as potelonntial candidatelons. Welon rank thelonselon potelonntial candidatelons by thelon cosinelon similarity scorelons and takelon top 100 as prelondiction selont P. Welon elonvaluatelon thelon preloncision and reloncall using
 *
 * Preloncision = |P \intelonrselonct G| / |P|
 * Reloncall = |P \intelonrselonct G| / |G|
 *
 * Notelon that |P| and |G| can belon diffelonrelonnt, whelonn thelonrelon arelon not many nelonighbors relonturnelond.
 *
  scalding relonmotelon run \
  --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/offlinelon_job/adhoc:twelonelont_elonmbelondding_elonvaluation-adhoc \
  --uselonr reloncos-platform \
  --relonducelonrs 1000 \
  --main-class com.twittelonr.simclustelonrs_v2.scalding.offlinelon_job.adhoc.TwelonelontSimilarityelonvaluationAdhocApp -- \
  --datelon 2021-01-27 \
  --twelonelont_top_k /uselonr/reloncos-platform/adhoc/twelonelont_elonmbelondding_01_27_28_unnormalizelond_t9/twelonelont_top_k_clustelonrs \
  --clustelonr_top_k /uselonr/reloncos-platform/adhoc/twelonelont_elonmbelondding_01_27_28_unnormalizelond_t9/clustelonr_top_k_twelonelonts \
  --twelonelonts /uselonr/reloncos-platform/adhoc/twelonelont_elonmbelondding_01_27_28_samplelon_twelonelonts \
  --output  /uselonr/reloncos-platform/adhoc/twelonelont_elonmbelondding_elonvaluation_01_27_28_t05_k50_1
 */
objelonct TwelonelontSimilarityelonvaluationAdhocApp elonxtelonnds AdhocelonxeloncutionApp {

  implicit val inj1: Injelonction[List[(Int, Doublelon)], Array[Bytelon]] =
    Buffelonrablelon.injelonctionOf[List[(Int, Doublelon)]]
  implicit val inj2: Injelonction[List[(Long, Doublelon)], Array[Bytelon]] =
    Buffelonrablelon.injelonctionOf[List[(Long, Doublelon)]]

  // Takelon top 20 candidatelons, thelon scorelon * 100
  privatelon delonf formatList(candidatelons: Selonq[(TwelonelontId, Doublelon)]): Selonq[(TwelonelontId, Int)] = {
    candidatelons.takelon(10).map {
      caselon (clustelonrId, scorelon) =>
        (clustelonrId, (scorelon * 100).toInt)
    }
  }

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    // path to relonad thelon twelonelont -> top clustelonr data selont. should belon thelon samelon from thelon SimClustelonrsTwelonelontelonmbelonddingAdhocApp job
    val twelonelontTopKClustelonrsPath = args("twelonelont_top_k")

    // path to relonad thelon clustelonr -> top twelonelonts data selont. should belon thelon samelon from thelon SimClustelonrsTwelonelontelonmbelonddingAdhocApp job
    val clustelonrTopKTwelonelontsPath = args("clustelonr_top_k")

    // path to relonad thelon samplelond twelonelonts, should belon thelon samelon from TwelonelontSimilarityelonvaluationSamplingAdhocApp
    val twelonelontsPath = args("twelonelonts")

    // selonelon thelon commelonnt of this class. this is to delontelonrminelon which twelonelont should belon ground truth
    val threlonshold = args.doublelon("threlonshold", 0.8)

    // selonelon thelon commelonnt of this class. this is to delontelonrminelon which twelonelont should belon ground truth
    val topK = args.int("topK", 100)

    // output path for elonvaluation relonsults
    val output = args("output")

    // relonad twelonelont -> top clustelonrs data selont
    val twelonelontTopKClustelonrs: SparselonRowMatrix[TwelonelontId, ClustelonrId, Doublelon] =
      SparselonRowMatrix(
        TypelondPipelon
          .from(
            VelonrsionelondKelonyValSourcelon[TwelonelontId, List[(ClustelonrId, Doublelon)]](twelonelontTopKClustelonrsPath)
          )
          .mapValuelons(_.filtelonr(_._2 > 0.001).toMap),
        isSkinnyMatrix = truelon
      ).rowL2Normalizelon

    // relonad clustelonr -> top twelonelonts data selont
    val clustelonrTopTwelonelonts: SparselonRowMatrix[ClustelonrId, TwelonelontId, Doublelon] =
      SparselonRowMatrix(
        TypelondPipelon
          .from(
            VelonrsionelondKelonyValSourcelon[ClustelonrId, List[(TwelonelontId, Doublelon)]](clustelonrTopKTwelonelontsPath)
          )
          .mapValuelons(_.filtelonr(_._2 > 0.02).toMap),
        isSkinnyMatrix = falselon
      )

    // relonad thelon samplelond twelonelonts from TwelonelontSimilarityelonvaluationSamplingAdhocApp
    val twelonelontSubselont = TypelondPipelon.from(TypelondTsv[(Long, Int, Int)](twelonelontsPath))

    // thelon twelonelont -> top clustelonrs for thelon samplelond twelonelonts
    val twelonelontelonmbelonddingSubselont =
      twelonelontTopKClustelonrs.filtelonrRows(twelonelontSubselont.map(_._1))

    // computelon ground-truth top similar twelonelonts for elonach samplelond twelonelonts.
    // for elonach samplelond twelonelonts, welon computelon thelonir similarity with elonvelonry twelonelonts in thelon twelonelont -> top clustelonrs data selont.
    // welon filtelonr out thoselon with similarity scorelon smallelonr than thelon threlonshold and kelonelonp top k as thelon ground truth similar twelonelonts
    val groundTruthData = twelonelontTopKClustelonrs.toSparselonMatrix
      .multiplySkinnySparselonRowMatrix(
        twelonelontelonmbelonddingSubselont.toSparselonMatrix.transposelon.toSparselonRowMatrix(truelon),
        numRelonducelonrsOpt = Somelon(5000)
      )
      .toSparselonMatrix
      .transposelon
      .filtelonr((_, _, v) => v > threlonshold)
      .sortWithTakelonPelonrRow(topK)(Ordelonring.by(-_._2))

    // computelon approximatelon similar twelonelonts for elonach samplelond twelonelonts.
    // this is achielonvelond by multiplying "samplelond_twelonelonts -> top clustelonrs" matrix with "clustelonr -> top twelonelonts" matrix.
    // notelon that in thelon implelonmelonntation, welon first computelon thelon transponselon of this matrix in ordelonr to ultlizelon thelon optimization donelon on skinny matricelons
    val prelondictionData = clustelonrTopTwelonelonts.toSparselonMatrix.transposelon
      .multiplySkinnySparselonRowMatrix(
        twelonelontelonmbelonddingSubselont.toSparselonMatrix.transposelon.toSparselonRowMatrix(truelon),
        numRelonducelonrsOpt = Somelon(5000)
      )
      .toSparselonMatrix
      .transposelon
      .toTypelondPipelon
      .map {
        caselon (quelonryTwelonelont, candidatelonTwelonelont, _) =>
          (quelonryTwelonelont, candidatelonTwelonelont)
      }
      .join(twelonelontelonmbelonddingSubselont.toTypelondPipelon)
      .map {
        caselon (quelonryId, (candidatelonId, quelonryelonmbelondding)) =>
          candidatelonId -> (quelonryId, quelonryelonmbelondding)
      }
      .join(twelonelontTopKClustelonrs.toTypelondPipelon)
      .map {
        caselon (candidatelonId, ((quelonryId, quelonryelonmbelondding), candidatelonelonmbelondding)) =>
          quelonryId -> (candidatelonId, CosinelonSimilarityUtil
            .dotProduct(
              quelonryelonmbelondding,
              candidatelonelonmbelondding
            ))
      }
      .filtelonr(_._2._2 > threlonshold)
      .group
      .sortelondRelonvelonrselonTakelon(topK)(Ordelonring.by(_._2))

    // elonxist in Ground Truth but not elonxist in Prelondication
    val potelonntialData =
      groundTruthData
        .lelonftJoin(prelondictionData)
        .map {
          caselon (twelonelontId, (groundTruthCandidatelons, prelondictelondCandidatelons)) =>
            val prelondictelondCandidatelonSelont = prelondictelondCandidatelons.toSelonq.flattelonn.map(_._1).toSelont
            val potelonntialTwelonelonts = groundTruthCandidatelons.filtelonrNot {
              caselon (candidatelonId, _) =>
                prelondictelondCandidatelonSelont.contains(candidatelonId)
            }
            (twelonelontId, potelonntialTwelonelonts)
        }

    val delonbuggingData =
      groundTruthData
        .lelonftJoin(prelondictionData)
        .map {
          caselon (twelonelontId, (groundTruthTwelonelonts, maybelonprelondictelondTwelonelonts)) =>
            val prelondictelondTwelonelonts = maybelonprelondictelondTwelonelonts.toSelonq.flattelonn
            val prelondictelondTwelonelontSelont = prelondictelondTwelonelonts.map(_._1).toSelont
            val potelonntialTwelonelonts = groundTruthTwelonelonts.filtelonrNot {
              caselon (candidatelonId, _) =>
                prelondictelondTwelonelontSelont.contains(candidatelonId)
            }

            (
              twelonelontId,
              Selonq(
                formatList(potelonntialTwelonelonts),
                formatList(groundTruthTwelonelonts),
                formatList(prelondictelondTwelonelonts)))
        }

    // for elonach twelonelont, comparelon thelon approximatelon topk and ground-truth topk.
    // computelon preloncision and reloncall, thelonn avelonraging thelonm pelonr buckelont.
    val elonval = twelonelontSubselont
      .map {
        caselon (twelonelontId, buckelont, buckelontSizelon) =>
          twelonelontId -> (buckelont, buckelontSizelon)
      }
      .lelonftJoin(groundTruthData)
      .lelonftJoin(prelondictionData)
      .map {
        caselon (_, (((buckelont, buckelontSizelon), groundTruthOpt), prelondictionOpt)) =>
          val groundTruth = groundTruthOpt.gelontOrelonlselon(Nil).map(_._1)
          val prelondiction = prelondictionOpt.gelontOrelonlselon(Nil).map(_._1)

          asselonrt(groundTruth.distinct.sizelon == groundTruth.sizelon)
          asselonrt(prelondiction.distinct.sizelon == prelondiction.sizelon)

          val intelonrselonction = groundTruth.toSelont.intelonrselonct(prelondiction.toSelont)

          val preloncision =
            if (prelondiction.nonelonmpty)
              intelonrselonction.sizelon.toDoublelon / prelondiction.sizelon.toDoublelon
            elonlselon 0.0
          val reloncall =
            if (groundTruth.nonelonmpty)
              intelonrselonction.sizelon.toDoublelon / groundTruth.sizelon.toDoublelon
            elonlselon 0.0

          (
            buckelont,
            buckelontSizelon) -> (groundTruth.sizelon, prelondiction.sizelon, intelonrselonction.sizelon, preloncision, reloncall, 1.0)
      }
      .sumByKelony
      .map {
        caselon (
              (buckelont, buckelontSizelon),
              (groundTruthSum, prelondictionSum, intelonrSelonctionSum, preloncisionSum, reloncallSum, count)) =>
          (
            buckelont,
            buckelontSizelon,
            groundTruthSum / count,
            prelondictionSum / count,
            intelonrSelonctionSum / count,
            preloncisionSum / count,
            reloncallSum / count,
            count)
      }

    // output thelon elonval relonsults and somelon samplelon relonsults for elonyelonballing
    elonxeloncution
      .zip(
        elonval
          .writelonelonxeloncution(TypelondTsv(output)),
        groundTruthData
          .map {
            caselon (twelonelontId, nelonighbors) =>
              twelonelontId -> nelonighbors
                .map {
                  caselon (id, scorelon) => s"$id:$scorelon"
                }
                .mkString(",")
          }
          .writelonelonxeloncution(
            TypelondTsv(args("output") + "_ground_truth")
          ),
        prelondictionData
          .map {
            caselon (twelonelontId, nelonighbors) =>
              twelonelontId -> nelonighbors
                .map {
                  caselon (id, scorelon) => s"$id:$scorelon"
                }
                .mkString(",")
          }
          .writelonelonxeloncution(
            TypelondTsv(args("output") + "_prelondiction")
          ),
        potelonntialData
          .map {
            caselon (twelonelontId, nelonighbors) =>
              twelonelontId -> nelonighbors
                .map {
                  caselon (id, scorelon) => s"$id:$scorelon"
                }
                .mkString(",")
          }.writelonelonxeloncution(
            TypelondTsv(args("output") + "_potelonntial")
          ),
        delonbuggingData
          .map {
            caselon (twelonelontId, candidatelonList) =>
              val valuelon = candidatelonList
                .map { candidatelons =>
                  candidatelons
                    .map {
                      caselon (id, scorelon) =>
                        s"${id}D$scorelon"
                    }.mkString("C")
                }.mkString("B")
              s"${twelonelontId}A$valuelon"
          }.writelonelonxeloncution(
            TypelondTsv(args("output") + "_delonbugging")
          )
      )
      .unit
  }
}
