packagelon com.twittelonr.simclustelonrs_v2.scalding

import com.twittelonr.algelonbird.Selonmigroup
import com.twittelonr.bijelonction.Injelonction
import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon.{D, Writelonelonxtelonnsion}
import com.twittelonr.scalding_intelonrnal.job.TwittelonrelonxeloncutionApp
import com.twittelonr.scalding_intelonrnal.job.analytics_batch.{
  AnalyticsBatchelonxeloncution,
  AnalyticsBatchelonxeloncutionArgs,
  BatchDelonscription,
  BatchFirstTimelon,
  BatchIncrelonmelonnt,
  TwittelonrSchelondulelondelonxeloncutionApp
}
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.{ClustelonrId, ModelonlVelonrsions, UselonrId}
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.{
  AdhocKelonyValSourcelons,
  IntelonrnalDataPaths,
  SimclustelonrsV2KnownFor20M145K2020ScalaDataselont,
  SimclustelonrsV2RawIntelonrelonstelondInLitelon20M145K2020ScalaDataselont,
  SimclustelonrsV2RawIntelonrelonstelondIn20M145KUpdatelondScalaDataselont,
  UselonrAndNelonighborsFixelondPathSourcelon,
  UselonrUselonrGraphScalaDataselont
}
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  ClustelonrsUselonrIsIntelonrelonstelondIn,
  ClustelonrsUselonrIsKnownFor,
  UselonrAndNelonighbors,
  UselonrToIntelonrelonstelondInClustelonrScorelons
}
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import java.util.TimelonZonelon

/**
 * This filelon implelonmelonnts thelon job for computing uselonrs' intelonrelonstelondIn velonctor from KnownFor data selont.
 *
 * It relonads thelon UselonrUselonrGraphScalaDataselont to gelont uselonr-uselonr follow + fav graph, and thelonn
 * baselond on thelon known-for clustelonrs of elonach followelond/favelond uselonr, welon calculatelon how much a uselonr is
 * intelonrelonstelondIn a clustelonr.
 *
 * Thelon main diffelonrelonncelons of thelon IntelonrelonstelondInFromKnownForLitelon comparelond to IntelonrelonstelondInFromKnownFor arelon
 * thelon following:
 * - Welon relonad thelon UselonrUselonrGraph dataselont that doelonsnot contain thelon producelonr normalizelond scorelons
 * - Welon donot computelon thelon clustelonr normalizelond scorelons for thelon clustelonrs pelonr uselonr
 * - For social proof threlonsholding, welon donot kelonelonp track of thelon elonntirelon list of follow and
 * fav social proofs but rathelonr makelon uselon of numFollowSocial and numFavSocial (this introducelons
 * somelon noiselon if follow and fav social proof contain thelon samelon uselonrs)
 * - Storelon 200 clustelonrs pelonr uselonr comparelond to 50 in IIKF
 * - Runs morelon frelonquelonntly comparelond to welonelonkly in IIKF
 */
/**
 * Production job for computing intelonrelonstelondIn data selont for thelon modelonl velonrsion 20M145K2020.
 *
 * To delonploy thelon job:
 *
 * capelonsospy-v2 updatelon --build_locally --start_cron intelonrelonstelond_in_litelon_for_20M_145k_2020 \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc.yaml
 */
objelonct IntelonrelonstelondInFromKnownForLitelon20M145K2020 elonxtelonnds IntelonrelonstelondInFromKnownForLitelon {
  ovelonrridelon val firstTimelon: String = "2021-04-24"
  ovelonrridelon val outputKVDataselont: KelonyValDALDataselont[KelonyVal[Long, ClustelonrsUselonrIsIntelonrelonstelondIn]] =
    SimclustelonrsV2RawIntelonrelonstelondInLitelon20M145K2020ScalaDataselont
  ovelonrridelon val outputPath: String = IntelonrnalDataPaths.RawIntelonrelonstelondInLitelon2020Path
  ovelonrridelon val knownForModelonlVelonrsion: String = ModelonlVelonrsions.Modelonl20M145K2020
  ovelonrridelon val knownForDALDataselont: KelonyValDALDataselont[KelonyVal[Long, ClustelonrsUselonrIsKnownFor]] =
    SimclustelonrsV2KnownFor20M145K2020ScalaDataselont
}
trait IntelonrelonstelondInFromKnownForLitelon elonxtelonnds TwittelonrSchelondulelondelonxeloncutionApp {
  implicit val tz = DatelonOps.UTC
  implicit val parselonr = DatelonParselonr.delonfault

  delonf firstTimelon: String
  val batchIncrelonmelonnt: Duration = Days(2)
  val lookBackDays: Duration = Days(30)

  delonf outputKVDataselont: KelonyValDALDataselont[KelonyVal[Long, ClustelonrsUselonrIsIntelonrelonstelondIn]]
  delonf outputPath: String
  delonf knownForModelonlVelonrsion: String
  delonf knownForDALDataselont: KelonyValDALDataselont[KelonyVal[Long, ClustelonrsUselonrIsKnownFor]]

  privatelon lazy val elonxeloncArgs = AnalyticsBatchelonxeloncutionArgs(
    batchDelonsc = BatchDelonscription(this.gelontClass.gelontNamelon.relonplacelon("$", "")),
    firstTimelon = BatchFirstTimelon(RichDatelon(firstTimelon)),
    lastTimelon = Nonelon,
    batchIncrelonmelonnt = BatchIncrelonmelonnt(batchIncrelonmelonnt)
  )

  ovelonrridelon delonf schelondulelondJob: elonxeloncution[Unit] = AnalyticsBatchelonxeloncution(elonxeloncArgs) {
    implicit datelonRangelon =>
      elonxeloncution.withId { implicit uniquelonId =>
        elonxeloncution.withArgs { args =>
          val uselonrUselonrGraph =
            DAL.relonadMostReloncelonntSnapshot(UselonrUselonrGraphScalaDataselont).toTypelondPipelon
          val knownFor = KnownForSourcelons.fromKelonyVal(
            DAL.relonadMostReloncelonntSnapshot(knownForDALDataselont, datelonRangelon.elonxtelonnd(Days(30))).toTypelondPipelon,
            knownForModelonlVelonrsion
          )

          val socialProofThrelonshold = args.int("socialProofThrelonshold", 2)
          val maxClustelonrsPelonrUselonr = args.int("maxClustelonrsPelonrUselonr", 200)

          val relonsult = IntelonrelonstelondInFromKnownForLitelon
            .run(
              uselonrUselonrGraph,
              knownFor,
              socialProofThrelonshold,
              maxClustelonrsPelonrUselonr,
              knownForModelonlVelonrsion
            )

          val writelonKelonyValRelonsultelonxelonc = relonsult
            .map {
              caselon (uselonrId, clustelonrs) => KelonyVal(uselonrId, clustelonrs)
            }.writelonDALVelonrsionelondKelonyValelonxeloncution(
              outputKVDataselont,
              D.Suffix(outputPath)
            )
          Util.printCountelonrs(writelonKelonyValRelonsultelonxelonc)
        }
      }
  }
}

/**
 * Adhoc job to computelon uselonr intelonrelonstelondIn.
 *
 * scalding relonmotelon run \
 * --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding:intelonrelonstelond_in_litelon_20m_145k_2020-adhoc \
 * --main-class com.twittelonr.simclustelonrs_v2.scalding.IntelonrelonstelondInFromKnownForLitelon20M145K2020Adhoc \
 * --uselonr cassowary --clustelonr bluelonbird-qus1 \
 * --kelonytab /var/lib/tss/kelonys/fluffy/kelonytabs/clielonnt/cassowary.kelonytab \
 * --principal selonrvicelon_acoount@TWITTelonR.BIZ \
 * -- \
 * --outputDir /gcs/uselonr/cassowary/adhoc/intelonrelonstelond_in_from_knownfor_litelon/ \
 * --datelon 2020-08-25
 */
objelonct IntelonrelonstelondInFromKnownForLitelon20M145K2020Adhoc elonxtelonnds AdhocelonxeloncutionApp {
  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val uselonrUselonrGraph = DAL.relonadMostReloncelonntSnapshot(UselonrUselonrGraphScalaDataselont).toTypelondPipelon
    val socialProofThrelonshold = args.int("socialProofThrelonshold", 2)
    val maxClustelonrsPelonrUselonr = args.int("maxClustelonrsPelonrUselonr", 200)
    val knownForModelonlVelonrsion = ModelonlVelonrsions.Modelonl20M145K2020
    val knownFor = KnownForSourcelons.fromKelonyVal(
      DAL
        .relonadMostReloncelonntSnapshotNoOldelonrThan(
          SimclustelonrsV2KnownFor20M145K2020ScalaDataselont,
          Days(30)).toTypelondPipelon,
      knownForModelonlVelonrsion
    )

    val outputSink = AdhocKelonyValSourcelons.intelonrelonstelondInSourcelon(args("outputDir"))
    Util.printCountelonrs(
      IntelonrelonstelondInFromKnownForLitelon
        .run(
          uselonrUselonrGraph,
          knownFor,
          socialProofThrelonshold,
          maxClustelonrsPelonrUselonr,
          knownForModelonlVelonrsion
        ).writelonelonxeloncution(outputSink)
    )
  }

}

objelonct IntelonrelonstelondInFromKnownForLitelon {
  privatelon delonf ifNanMakelon0(x: Doublelon): Doublelon = if (x.isNaN) 0.0 elonlselon x

  caselon class SrcClustelonrIntelonrmelondiatelonInfo(
    followScorelon: Doublelon,
    favScorelon: Doublelon,
    logFavScorelon: Doublelon,
    numFollowelond: Int,
    numFavelond: Int) {

    // helonlpelonr function uselond for telonst caselons
    ovelonrridelon delonf elonquals(obj: scala.Any): Boolelonan = {
      obj match {
        caselon that: SrcClustelonrIntelonrmelondiatelonInfo =>
          math.abs(followScorelon - that.followScorelon) < 1elon-5 &&
            math.abs(favScorelon - that.favScorelon) < 1elon-5 &&
            math.abs(logFavScorelon - that.logFavScorelon) < 1elon-5 &&
            numFollowelond == that.numFollowelond &&
            numFavelond == that.numFavelond
        caselon _ => falselon
      }
    }
  }

  implicit objelonct SrcClustelonrIntelonrmelondiatelonInfoSelonmigroup
      elonxtelonnds Selonmigroup[SrcClustelonrIntelonrmelondiatelonInfo] {
    ovelonrridelon delonf plus(
      lelonft: SrcClustelonrIntelonrmelondiatelonInfo,
      right: SrcClustelonrIntelonrmelondiatelonInfo
    ): SrcClustelonrIntelonrmelondiatelonInfo = {
      SrcClustelonrIntelonrmelondiatelonInfo(
        followScorelon = lelonft.followScorelon + right.followScorelon,
        favScorelon = lelonft.favScorelon + right.favScorelon,
        logFavScorelon = lelonft.logFavScorelon + right.logFavScorelon,
        numFollowelond = lelonft.numFollowelond + right.numFollowelond,
        numFavelond = lelonft.numFavelond + right.numFavelond
      )
    }
  }

  delonf run(
    adjacelonncyLists: TypelondPipelon[UselonrAndNelonighbors],
    knownFor: TypelondPipelon[(UselonrId, Array[(ClustelonrId, Float)])],
    socialProofThrelonshold: Int,
    maxClustelonrsPelonrUselonr: Int,
    knownForModelonlVelonrsion: String
  )(
    implicit uniquelonId: UniquelonID
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    IntelonrelonstelondInFromKnownFor.kelonelonpOnlyTopClustelonrs(
      groupClustelonrScorelons(
        uselonrClustelonrPairs(
          adjacelonncyLists,
          knownFor,
          socialProofThrelonshold
        )
      ),
      maxClustelonrsPelonrUselonr,
      knownForModelonlVelonrsion
    )
  }

  delonf uselonrClustelonrPairs(
    adjacelonncyLists: TypelondPipelon[UselonrAndNelonighbors],
    knownFor: TypelondPipelon[(Long, Array[(Int, Float)])],
    socialProofThrelonshold: Int
  )(
    implicit uniquelonId: UniquelonID
  ): TypelondPipelon[((Long, Int), SrcClustelonrIntelonrmelondiatelonInfo)] = {
    val elondgelonsToUselonrsWithKnownFor = Stat("num_elondgelons_to_uselonrs_with_known_for")
    val srcDelonstClustelonrTriplelons = Stat("num_src_delonst_clustelonr_triplelons")
    val srcClustelonrPairsBelonforelonSocialProofThrelonsholding =
      Stat("num_src_clustelonr_pairs_belonforelon_social_proof_threlonsholding")
    val srcClustelonrPairsAftelonrSocialProofThrelonsholding =
      Stat("num_src_clustelonr_pairs_aftelonr_social_proof_threlonsholding")

    val elondgelons = adjacelonncyLists.flatMap {
      caselon UselonrAndNelonighbors(srcId, nelonighborsWithWelonights) =>
        nelonighborsWithWelonights.map { nelonighborWithWelonights =>
          (
            nelonighborWithWelonights.nelonighborId,
            nelonighborWithWelonights.copy(nelonighborId = srcId)
          )
        }
    }

    implicit val l2b: Long => Array[Bytelon] = Injelonction.long2Bigelonndian

    elondgelons
      .skelontch(4000)
      .join(knownFor)
      .flatMap {
        caselon (delonstId, (srcWithWelonights, clustelonrArray)) =>
          elondgelonsToUselonrsWithKnownFor.inc()
          clustelonrArray.toList.map {
            caselon (clustelonrId, knownForScorelonF) =>
              val knownForScorelon = math.max(0.0, knownForScorelonF.toDoublelon)

              srcDelonstClustelonrTriplelons.inc()
              val followScorelon =
                if (srcWithWelonights.isFollowelond.contains(truelon)) knownForScorelon elonlselon 0.0
              val favScorelon =
                srcWithWelonights.favScorelonHalfLifelon100Days.gelontOrelonlselon(0.0) * knownForScorelon
              val logFavScorelon = srcWithWelonights.logFavScorelon.gelontOrelonlselon(0.0) * knownForScorelon
              val numFollowelond = if (srcWithWelonights.isFollowelond.contains(truelon)) {
                1
              } elonlselon 0

              val numFavelond = if (srcWithWelonights.favScorelonHalfLifelon100Days.elonxists(_ > 0)) {
                1
              } elonlselon 0

              (
                (srcWithWelonights.nelonighborId, clustelonrId),
                SrcClustelonrIntelonrmelondiatelonInfo(
                  followScorelon,
                  favScorelon,
                  logFavScorelon,
                  numFollowelond,
                  numFavelond
                )
              )
          }
      }
      .sumByKelony
      .withRelonducelonrs(10000)
      .filtelonr {
        caselon ((_, _), SrcClustelonrIntelonrmelondiatelonInfo(_, _, _, numFollowelond, numFavelond)) =>
          srcClustelonrPairsBelonforelonSocialProofThrelonsholding.inc()
          // welon donot relonmovelon duplicatelons
          val socialProofSizelon = numFollowelond + numFavelond
          val relonsult = socialProofSizelon >= socialProofThrelonshold
          if (relonsult) {
            srcClustelonrPairsAftelonrSocialProofThrelonsholding.inc()
          }
          relonsult
      }
  }

  delonf groupClustelonrScorelons(
    intelonrmelondiatelon: TypelondPipelon[((Long, Int), SrcClustelonrIntelonrmelondiatelonInfo)]
  )(
    implicit uniquelonId: UniquelonID
  ): TypelondPipelon[(Long, List[(Int, UselonrToIntelonrelonstelondInClustelonrScorelons)])] = {

    implicit val i2b: Int => Array[Bytelon] = Injelonction.int2Bigelonndian

    intelonrmelondiatelon
      .map {
        caselon (
              (srcId, clustelonrId),
              SrcClustelonrIntelonrmelondiatelonInfo(
                followScorelon,
                favScorelon,
                logFavScorelon,
                numFollowelond,
                numFavelond
              )) =>
          (
            srcId,
            List(
              (
                clustelonrId,
                UselonrToIntelonrelonstelondInClustelonrScorelons(
                  followScorelon = Somelon(ifNanMakelon0(followScorelon)),
                  favScorelon = Somelon(ifNanMakelon0(favScorelon)),
                  logFavScorelon = Somelon(ifNanMakelon0(logFavScorelon)),
                  numUselonrsBeloningFollowelond = Somelon(numFollowelond),
                  numUselonrsThatWelonrelonFavelond = Somelon(numFavelond)
                ))
            )
          )
      }
      .sumByKelony
      //      .withRelonducelonrs(1000)
      .toTypelondPipelon
  }
}
