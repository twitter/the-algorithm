packagelon com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding

import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons._
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil._
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.SimClustelonrselonmbelonddingJob
import com.twittelonr.simclustelonrs_v2.thriftscala._
import com.twittelonr.wtf.scalding.jobs.common.{AdhocelonxeloncutionApp, SchelondulelondelonxeloncutionApp}
import java.util.TimelonZonelon

objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondInBatchAppUtil {
  import ProducelonrelonmbelonddingsFromIntelonrelonstelondIn._

  val uselonr = Systelonm.gelontelonnv("USelonR")

  val rootPath: String = s"/uselonr/$uselonr/manhattan_selonquelonncelon_filelons"

  // Helonlps spelonelond up thelon multiplication stelonp which can gelont velonry big
  val numRelonducelonrsForMatrixMultiplication: Int = 12000

  /**
   * Givelonn thelon producelonr x clustelonr matrix, kelony by producelonr / clustelonr individually, and writelon output
   * to individual DAL dataselonts
   */
  delonf writelonOutput(
    producelonrClustelonrelonmbelondding: TypelondPipelon[((ClustelonrId, UselonrId), Doublelon)],
    producelonrTopKelonmbelonddingsDataselont: KelonyValDALDataselont[KelonyVal[Long, TopSimClustelonrsWithScorelon]],
    clustelonrTopKProducelonrsDataselont: KelonyValDALDataselont[
      KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
    ],
    producelonrTopKelonmbelonddingsPath: String,
    clustelonrTopKProducelonrsPath: String,
    modelonlVelonrsion: ModelonlVelonrsion
  ): elonxeloncution[Unit] = {
    val kelonyelondByProducelonr =
      toSimClustelonrelonmbelondding(producelonrClustelonrelonmbelondding, topKClustelonrsToKelonelonp, modelonlVelonrsion)
        .map { caselon (uselonrId, clustelonrs) => KelonyVal(uselonrId, clustelonrs) }
        .writelonDALVelonrsionelondKelonyValelonxeloncution(
          producelonrTopKelonmbelonddingsDataselont,
          D.Suffix(producelonrTopKelonmbelonddingsPath)
        )

    val kelonyelondBySimClustelonr = fromSimClustelonrelonmbelondding(
      producelonrClustelonrelonmbelondding,
      topKUselonrsToKelonelonp,
      modelonlVelonrsion
    ).map {
        caselon (clustelonrId, topProducelonrs) => KelonyVal(clustelonrId, topProducelonrsToThrift(topProducelonrs))
      }
      .writelonDALVelonrsionelondKelonyValelonxeloncution(
        clustelonrTopKProducelonrsDataselont,
        D.Suffix(clustelonrTopKProducelonrsPath)
      )

    elonxeloncution.zip(kelonyelondByProducelonr, kelonyelondBySimClustelonr).unit
  }
}

/**
 * Baselon class for Fav baselond producelonr elonmbelonddings. Helonlps relonuselon thelon codelon for diffelonrelonnt modelonl velonrsions
 */
trait ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFavScorelonBaselon elonxtelonnds SchelondulelondelonxeloncutionApp {
  import ProducelonrelonmbelonddingsFromIntelonrelonstelondIn._
  import ProducelonrelonmbelonddingsFromIntelonrelonstelondInBatchAppUtil._

  delonf modelonlVelonrsion: ModelonlVelonrsion

  val producelonrTopKelonmbelonddingsByFavScorelonPathPrelonfix: String =
    "/producelonr_top_k_simclustelonr_elonmbelonddings_by_fav_scorelon_"

  val clustelonrTopKProducelonrsByFavScorelonPathPrelonfix: String =
    "/simclustelonr_elonmbelondding_top_k_producelonrs_by_fav_scorelon_"

  val minNumFavelonrs: Int = minNumFavelonrsForProducelonr

  delonf producelonrTopKSimclustelonrelonmbelonddingsByFavScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[Long, TopSimClustelonrsWithScorelon]
  ]

  delonf simclustelonrelonmbelonddingTopKProducelonrsByFavScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
  ]

  delonf gelontIntelonrelonstelondInFn: (DatelonRangelon, TimelonZonelon) => TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)]

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val producelonrTopKelonmbelonddingsByFavScorelonPathUpdatelond: String =
      rootPath + producelonrTopKelonmbelonddingsByFavScorelonPathPrelonfix + ModelonlVelonrsions
        .toKnownForModelonlVelonrsion(modelonlVelonrsion)

    val clustelonrTopKProducelonrsByFavScorelonPathUpdatelond: String =
      rootPath + clustelonrTopKProducelonrsByFavScorelonPathPrelonfix + ModelonlVelonrsions
        .toKnownForModelonlVelonrsion(modelonlVelonrsion)

    val producelonrClustelonrelonmbelonddingByFavScorelon = gelontProducelonrClustelonrelonmbelondding(
      gelontIntelonrelonstelondInFn(datelonRangelon.elonmbiggelonn(Days(5)), timelonZonelon),
      DataSourcelons.uselonrUselonrNormalizelondGraphSourcelon,
      DataSourcelons.uselonrNormsAndCounts,
      uselonrToProducelonrFavScorelon,
      uselonrToClustelonrFavScorelon, // Fav scorelon
      _.favelonrCount.elonxists(_ > minNumFavelonrs),
      numRelonducelonrsForMatrixMultiplication,
      modelonlVelonrsion,
      cosinelonSimilarityThrelonshold
    ).forcelonToDisk

    writelonOutput(
      producelonrClustelonrelonmbelonddingByFavScorelon,
      producelonrTopKSimclustelonrelonmbelonddingsByFavScorelonDataselont,
      simclustelonrelonmbelonddingTopKProducelonrsByFavScorelonDataselont,
      producelonrTopKelonmbelonddingsByFavScorelonPathUpdatelond,
      clustelonrTopKProducelonrsByFavScorelonPathUpdatelond,
      modelonlVelonrsion
    )
  }
}

/**
 * Baselon class for Follow baselond producelonr elonmbelonddings. Helonlps relonuselon thelon codelon for diffelonrelonnt modelonl velonrsions
 */
trait ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFollowScorelonBaselon elonxtelonnds SchelondulelondelonxeloncutionApp {
  import ProducelonrelonmbelonddingsFromIntelonrelonstelondIn._
  import ProducelonrelonmbelonddingsFromIntelonrelonstelondInBatchAppUtil._

  delonf modelonlVelonrsion: ModelonlVelonrsion

  val producelonrTopKelonmbelonddingsByFollowScorelonPathPrelonfix: String =
    "/producelonr_top_k_simclustelonr_elonmbelonddings_by_follow_scorelon_"

  val clustelonrTopKProducelonrsByFollowScorelonPathPrelonfix: String =
    "/simclustelonr_elonmbelondding_top_k_producelonrs_by_follow_scorelon_"

  delonf producelonrTopKSimclustelonrelonmbelonddingsByFollowScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[Long, TopSimClustelonrsWithScorelon]
  ]

  delonf simclustelonrelonmbelonddingTopKProducelonrsByFollowScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
  ]

  delonf gelontIntelonrelonstelondInFn: (DatelonRangelon, TimelonZonelon) => TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)]

  val minNumFollowelonrs: Int = minNumFollowelonrsForProducelonr

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val producelonrTopKelonmbelonddingsByFollowScorelonPath: String =
      rootPath + producelonrTopKelonmbelonddingsByFollowScorelonPathPrelonfix + ModelonlVelonrsions
        .toKnownForModelonlVelonrsion(modelonlVelonrsion)

    val clustelonrTopKProducelonrsByFollowScorelonPath: String =
      rootPath + clustelonrTopKProducelonrsByFollowScorelonPathPrelonfix + ModelonlVelonrsions
        .toKnownForModelonlVelonrsion(modelonlVelonrsion)

    val producelonrClustelonrelonmbelonddingByFollowScorelon = gelontProducelonrClustelonrelonmbelondding(
      gelontIntelonrelonstelondInFn(datelonRangelon.elonmbiggelonn(Days(5)), timelonZonelon),
      DataSourcelons.uselonrUselonrNormalizelondGraphSourcelon,
      DataSourcelons.uselonrNormsAndCounts,
      uselonrToProducelonrFollowScorelon,
      uselonrToClustelonrFollowScorelon, // Follow scorelon
      _.followelonrCount.elonxists(_ > minNumFollowelonrs),
      numRelonducelonrsForMatrixMultiplication,
      modelonlVelonrsion,
      cosinelonSimilarityThrelonshold
    ).forcelonToDisk

    writelonOutput(
      producelonrClustelonrelonmbelonddingByFollowScorelon,
      producelonrTopKSimclustelonrelonmbelonddingsByFollowScorelonDataselont,
      simclustelonrelonmbelonddingTopKProducelonrsByFollowScorelonDataselont,
      producelonrTopKelonmbelonddingsByFollowScorelonPath,
      clustelonrTopKProducelonrsByFollowScorelonPath,
      modelonlVelonrsion
    )
  }
}

/**
 capelonsospy-v2 updatelon --build_locally --start_cron \
 --start_cron producelonr_elonmbelonddings_from_intelonrelonstelond_in_by_fav_scorelon \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFavScorelonBatchApp
    elonxtelonnds ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFavScorelonBaselon {
  ovelonrridelon delonf modelonlVelonrsion: ModelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145kUpdatelond

  ovelonrridelon delonf gelontIntelonrelonstelondInFn: (
    DatelonRangelon,
    TimelonZonelon
  ) => TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] =
    IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondInUpdatelondSourcelon

  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2019-09-10")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrTopKSimclustelonrelonmbelonddingsByFavScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[Long, TopSimClustelonrsWithScorelon]
  ] =
    ProducelonrTopKSimclustelonrelonmbelonddingsByFavScorelonUpdatelondScalaDataselont

  ovelonrridelon delonf simclustelonrelonmbelonddingTopKProducelonrsByFavScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
  ] =
    SimclustelonrelonmbelonddingTopKProducelonrsByFavScorelonUpdatelondScalaDataselont
}

/**
capelonsospy-v2 updatelon --build_locally --start_cron \
 --start_cron producelonr_elonmbelonddings_from_intelonrelonstelond_in_by_fav_scorelon_2020 \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFavScorelon2020BatchApp
    elonxtelonnds ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFavScorelonBaselon {
  ovelonrridelon delonf modelonlVelonrsion: ModelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145k2020

  ovelonrridelon delonf gelontIntelonrelonstelondInFn: (
    DatelonRangelon,
    TimelonZonelon
  ) => TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] =
    IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondIn2020Sourcelon

  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2021-03-01")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrTopKSimclustelonrelonmbelonddingsByFavScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[Long, TopSimClustelonrsWithScorelon]
  ] =
    ProducelonrTopKSimclustelonrelonmbelonddingsByFavScorelon2020ScalaDataselont

  ovelonrridelon delonf simclustelonrelonmbelonddingTopKProducelonrsByFavScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
  ] =
    SimclustelonrelonmbelonddingTopKProducelonrsByFavScorelon2020ScalaDataselont
}

/**
capelonsospy-v2 updatelon --build_locally --start_cron \
 --start_cron producelonr_elonmbelonddings_from_intelonrelonstelond_in_by_fav_scorelon_delonc11 \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFavScorelonDelonc11BatchApp
    elonxtelonnds ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFavScorelonBaselon {
  ovelonrridelon delonf modelonlVelonrsion: ModelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145kDelonc11

  ovelonrridelon delonf gelontIntelonrelonstelondInFn: (
    DatelonRangelon,
    TimelonZonelon
  ) => TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] =
    IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondInDelonc11Sourcelon

  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2019-11-18")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrTopKSimclustelonrelonmbelonddingsByFavScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[Long, TopSimClustelonrsWithScorelon]
  ] =
    ProducelonrTopKSimclustelonrelonmbelonddingsByFavScorelonScalaDataselont

  ovelonrridelon delonf simclustelonrelonmbelonddingTopKProducelonrsByFavScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
  ] =
    SimclustelonrelonmbelonddingTopKProducelonrsByFavScorelonScalaDataselont
}

/**
capelonsospy-v2 updatelon --build_locally --start_cron \
 --start_cron producelonr_elonmbelonddings_from_intelonrelonstelond_in_by_follow_scorelon \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFollowScorelonBatchApp
    elonxtelonnds ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFollowScorelonBaselon {
  ovelonrridelon delonf modelonlVelonrsion: ModelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145kUpdatelond

  ovelonrridelon delonf gelontIntelonrelonstelondInFn: (
    DatelonRangelon,
    TimelonZonelon
  ) => TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] =
    IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondInUpdatelondSourcelon

  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2019-09-10")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrTopKSimclustelonrelonmbelonddingsByFollowScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[Long, TopSimClustelonrsWithScorelon]
  ] =
    ProducelonrTopKSimclustelonrelonmbelonddingsByFollowScorelonUpdatelondScalaDataselont

  ovelonrridelon delonf simclustelonrelonmbelonddingTopKProducelonrsByFollowScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
  ] =
    SimclustelonrelonmbelonddingTopKProducelonrsByFollowScorelonUpdatelondScalaDataselont
}

/**
capelonsospy-v2 updatelon --build_locally --start_cron \
 --start_cron producelonr_elonmbelonddings_from_intelonrelonstelond_in_by_follow_scorelon_2020 \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFollowScorelon2020BatchApp
    elonxtelonnds ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFollowScorelonBaselon {
  ovelonrridelon delonf modelonlVelonrsion: ModelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145k2020

  ovelonrridelon delonf gelontIntelonrelonstelondInFn: (
    DatelonRangelon,
    TimelonZonelon
  ) => TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] =
    IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondIn2020Sourcelon

  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2021-03-01")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrTopKSimclustelonrelonmbelonddingsByFollowScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[Long, TopSimClustelonrsWithScorelon]
  ] =
    ProducelonrTopKSimclustelonrelonmbelonddingsByFollowScorelon2020ScalaDataselont

  ovelonrridelon delonf simclustelonrelonmbelonddingTopKProducelonrsByFollowScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
  ] =
    SimclustelonrelonmbelonddingTopKProducelonrsByFollowScorelon2020ScalaDataselont
}

/**
capelonsospy-v2 updatelon --build_locally --start_cron \
 --start_cron producelonr_elonmbelonddings_from_intelonrelonstelond_in_by_follow_scorelon_delonc11 \
 src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFollowScorelonDelonc11BatchApp
    elonxtelonnds ProducelonrelonmbelonddingsFromIntelonrelonstelondInByFollowScorelonBaselon {
  ovelonrridelon delonf modelonlVelonrsion: ModelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145kDelonc11

  ovelonrridelon delonf gelontIntelonrelonstelondInFn: (
    DatelonRangelon,
    TimelonZonelon
  ) => TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] =
    IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondInDelonc11Sourcelon

  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2019-11-18")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf producelonrTopKSimclustelonrelonmbelonddingsByFollowScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[Long, TopSimClustelonrsWithScorelon]
  ] =
    ProducelonrTopKSimclustelonrelonmbelonddingsByFollowScorelonScalaDataselont

  ovelonrridelon delonf simclustelonrelonmbelonddingTopKProducelonrsByFollowScorelonDataselont: KelonyValDALDataselont[
    KelonyVal[PelonrsistelondFullClustelonrId, TopProducelonrsWithScorelon]
  ] =
    SimclustelonrelonmbelonddingTopKProducelonrsByFollowScorelonScalaDataselont
}

/**
 * Adhoc job to calculatelon producelonr's simclustelonr elonmbelonddings, which elonsselonntially assigns intelonrelonstelondIn
 * SimClustelonrs to elonach producelonr, relongardlelonss of whelonthelonr thelon producelonr has a knownFor assignmelonnt.
 *
$ ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding:producelonr_elonmbelonddings_from_intelonrelonstelond_in-adhoc

 $ scalding relonmotelon run \
 --main-class com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.ProducelonrelonmbelonddingsFromIntelonrelonstelondInAdhocApp \
 --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding:producelonr_elonmbelonddings_from_intelonrelonstelond_in-adhoc \
 --uselonr cassowary --clustelonr bluelonbird-qus1 \
 --kelonytab /var/lib/tss/kelonys/fluffy/kelonytabs/clielonnt/cassowary.kelonytab \
 --principal selonrvicelon_acoount@TWITTelonR.BIZ \
 -- --datelon 2020-08-25 --modelonl_velonrsion 20M_145K_updatelond \
 --outputDir /gcs/uselonr/cassowary/adhoc/producelonrelonmbelonddings/

 */
objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondInAdhocApp elonxtelonnds AdhocelonxeloncutionApp {

  import ProducelonrelonmbelonddingsFromIntelonrelonstelondIn._

  privatelon val numRelonducelonrsForMatrixMultiplication = 12000

  /**
   * Calculatelon thelon elonmbelondding and writelons thelon relonsults kelonyelond by producelonrs and clustelonrs selonparatelonly into
   * individual locations
   */
  privatelon delonf runAdhocByScorelon(
    intelonrelonstelondInClustelonrs: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    uselonrUselonrNormalGraph: TypelondPipelon[UselonrAndNelonighbors],
    uselonrNormsAndCounts: TypelondPipelon[NormsAndCounts],
    kelonyelondByProducelonrSinkPath: String,
    kelonyelondByClustelonrSinkPath: String,
    uselonrToProducelonrScoringFn: NelonighborWithWelonights => Doublelon,
    uselonrToClustelonrScoringFn: UselonrToIntelonrelonstelondInClustelonrScorelons => Doublelon,
    uselonrFiltelonr: NormsAndCounts => Boolelonan,
    modelonlVelonrsion: ModelonlVelonrsion
  )(
    implicit uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val producelonrClustelonrelonmbelondding = gelontProducelonrClustelonrelonmbelondding(
      intelonrelonstelondInClustelonrs,
      uselonrUselonrNormalGraph,
      uselonrNormsAndCounts,
      uselonrToProducelonrScoringFn,
      uselonrToClustelonrScoringFn,
      uselonrFiltelonr,
      numRelonducelonrsForMatrixMultiplication,
      modelonlVelonrsion,
      cosinelonSimilarityThrelonshold
    ).forcelonToDisk

    val kelonyByProducelonrelonxelonc =
      toSimClustelonrelonmbelondding(producelonrClustelonrelonmbelondding, topKClustelonrsToKelonelonp, modelonlVelonrsion)
        .writelonelonxeloncution(
          AdhocKelonyValSourcelons.topProducelonrToClustelonrelonmbelonddingsSourcelon(kelonyelondByProducelonrSinkPath))

    val kelonyByClustelonrelonxelonc =
      fromSimClustelonrelonmbelondding(producelonrClustelonrelonmbelondding, topKUselonrsToKelonelonp, modelonlVelonrsion)
        .map { caselon (clustelonrId, topProducelonrs) => (clustelonrId, topProducelonrsToThrift(topProducelonrs)) }
        .writelonelonxeloncution(
          AdhocKelonyValSourcelons.topClustelonrelonmbelonddingsToProducelonrSourcelon(kelonyelondByClustelonrSinkPath))

    elonxeloncution.zip(kelonyByProducelonrelonxelonc, kelonyByClustelonrelonxelonc).unit
  }

  // Calculatelon thelon elonmbelonddings using follow scorelons
  privatelon delonf runFollowScorelon(
    intelonrelonstelondInClustelonrs: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    uselonrUselonrNormalGraph: TypelondPipelon[UselonrAndNelonighbors],
    uselonrNormsAndCounts: TypelondPipelon[NormsAndCounts],
    modelonlVelonrsion: ModelonlVelonrsion,
    outputDir: String
  )(
    implicit uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val kelonyByClustelonrSinkPath = outputDir + "kelonyelondByClustelonr/byFollowScorelon_" + modelonlVelonrsion
    val kelonyByProducelonrSinkPath = outputDir + "kelonyelondByProducelonr/byFollowScorelon_" + modelonlVelonrsion

    runAdhocByScorelon(
      intelonrelonstelondInClustelonrs,
      uselonrUselonrNormalGraph,
      uselonrNormsAndCounts,
      kelonyelondByProducelonrSinkPath = kelonyByProducelonrSinkPath,
      kelonyelondByClustelonrSinkPath = kelonyByClustelonrSinkPath,
      uselonrToProducelonrScoringFn = uselonrToProducelonrFollowScorelon,
      uselonrToClustelonrScoringFn = uselonrToClustelonrFollowScorelon,
      _.followelonrCount.elonxists(_ > minNumFollowelonrsForProducelonr),
      modelonlVelonrsion
    )
  }

  // Calculatelon thelon elonmbelonddings using fav scorelons
  privatelon delonf runFavScorelon(
    intelonrelonstelondInClustelonrs: TypelondPipelon[(Long, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    uselonrUselonrNormalGraph: TypelondPipelon[UselonrAndNelonighbors],
    uselonrNormsAndCounts: TypelondPipelon[NormsAndCounts],
    modelonlVelonrsion: ModelonlVelonrsion,
    outputDir: String
  )(
    implicit uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val kelonyByClustelonrSinkPath = outputDir + "kelonyelondByClustelonr/byFavScorelon_" + modelonlVelonrsion
    val kelonyByProducelonrSinkPath = outputDir + "kelonyelondByProducelonr/byFavScorelon_" + modelonlVelonrsion

    runAdhocByScorelon(
      intelonrelonstelondInClustelonrs,
      uselonrUselonrNormalGraph,
      uselonrNormsAndCounts,
      kelonyelondByProducelonrSinkPath = kelonyByProducelonrSinkPath,
      kelonyelondByClustelonrSinkPath = kelonyByClustelonrSinkPath,
      uselonrToProducelonrScoringFn = uselonrToProducelonrFavScorelon,
      uselonrToClustelonrScoringFn = uselonrToClustelonrFavScorelon,
      _.favelonrCount.elonxists(_ > minNumFavelonrsForProducelonr),
      modelonlVelonrsion
    )
  }

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val outputDir = args("outputDir")

    val modelonlVelonrsion =
      ModelonlVelonrsions.toModelonlVelonrsion(args.relonquirelond("modelonl_velonrsion"))

    val intelonrelonstelondInClustelonrs = modelonlVelonrsion match {
      caselon ModelonlVelonrsion.Modelonl20m145k2020 =>
        IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondIn2020Sourcelon(datelonRangelon, timelonZonelon).forcelonToDisk
      caselon ModelonlVelonrsion.Modelonl20m145kUpdatelond =>
        IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondInUpdatelondSourcelon(datelonRangelon, timelonZonelon).forcelonToDisk
      caselon _ =>
        IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondInDelonc11Sourcelon(datelonRangelon, timelonZonelon).forcelonToDisk
    }

    elonxeloncution
      .zip(
        runFavScorelon(
          intelonrelonstelondInClustelonrs,
          DataSourcelons.uselonrUselonrNormalizelondGraphSourcelon,
          DataSourcelons.uselonrNormsAndCounts,
          modelonlVelonrsion,
          outputDir
        ),
        runFollowScorelon(
          intelonrelonstelondInClustelonrs,
          DataSourcelons.uselonrUselonrNormalizelondGraphSourcelon,
          DataSourcelons.uselonrNormsAndCounts,
          modelonlVelonrsion,
          outputDir
        )
      ).unit
  }
}

/**
 * Computelons thelon producelonr's intelonrelonstelondIn clustelonr elonmbelondding. i.elon. If a twelonelont author (producelonr) is not
 * associatelond with a KnownFor clustelonr, do a cross-product belontwelonelonn
 * [uselonr, intelonrelonstelondIn] and [uselonr, producelonr] to find thelon similarity matrix [intelonrelonstelondIn, producelonr].
 */
objelonct ProducelonrelonmbelonddingsFromIntelonrelonstelondIn {
  val minNumFollowelonrsForProducelonr: Int = 100
  val minNumFavelonrsForProducelonr: Int = 100
  val topKUselonrsToKelonelonp: Int = 300
  val topKClustelonrsToKelonelonp: Int = 60
  val cosinelonSimilarityThrelonshold: Doublelon = 0.01

  typelon ClustelonrId = Int

  delonf topProducelonrsToThrift(producelonrsWithScorelon: Selonq[(UselonrId, Doublelon)]): TopProducelonrsWithScorelon = {
    val thrift = producelonrsWithScorelon.map { producelonr =>
      TopProducelonrWithScorelon(producelonr._1, producelonr._2)
    }
    TopProducelonrsWithScorelon(thrift)
  }

  delonf uselonrToProducelonrFavScorelon(nelonighbor: NelonighborWithWelonights): Doublelon = {
    nelonighbor.favScorelonHalfLifelon100DaysNormalizelondByNelonighborFavelonrsL2.gelontOrelonlselon(0.0)
  }

  delonf uselonrToProducelonrFollowScorelon(nelonighbor: NelonighborWithWelonights): Doublelon = {
    nelonighbor.followScorelonNormalizelondByNelonighborFollowelonrsL2.gelontOrelonlselon(0.0)
  }

  delonf uselonrToClustelonrFavScorelon(clustelonrScorelon: UselonrToIntelonrelonstelondInClustelonrScorelons): Doublelon = {
    clustelonrScorelon.favScorelonClustelonrNormalizelondOnly.gelontOrelonlselon(0.0)
  }

  delonf uselonrToClustelonrFollowScorelon(clustelonrScorelon: UselonrToIntelonrelonstelondInClustelonrScorelons): Doublelon = {
    clustelonrScorelon.followScorelonClustelonrNormalizelondOnly.gelontOrelonlselon(0.0)
  }

  delonf gelontUselonrSimClustelonrsMatrix(
    simClustelonrsSourcelon: TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    elonxtractScorelon: UselonrToIntelonrelonstelondInClustelonrScorelons => Doublelon,
    modelonlVelonrsion: ModelonlVelonrsion
  ): TypelondPipelon[(UselonrId, Selonq[(Int, Doublelon)])] = {
    simClustelonrsSourcelon.collelonct {
      caselon (uselonrId, clustelonrs)
          if ModelonlVelonrsions.toModelonlVelonrsion(clustelonrs.knownForModelonlVelonrsion).elonquals(modelonlVelonrsion) =>
        uselonrId -> clustelonrs.clustelonrIdToScorelons
          .map {
            caselon (clustelonrId, clustelonrScorelons) =>
              (clustelonrId, elonxtractScorelon(clustelonrScorelons))
          }.toSelonq.filtelonr(_._2 > 0)
    }
  }

  /**
   * Givelonn a welonightelond uselonr-producelonr elonngagelonmelonnt history matrix, as welonll as a
   * welonightelond uselonr-intelonrelonstelondInClustelonr matrix, do thelon matrix multiplication to yielonld a welonightelond
   * producelonr-clustelonr elonmbelondding matrix
   */
  delonf gelontProducelonrClustelonrelonmbelondding(
    intelonrelonstelondInClustelonrs: TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)],
    uselonrProducelonrelonngagelonmelonntGraph: TypelondPipelon[UselonrAndNelonighbors],
    uselonrNormsAndCounts: TypelondPipelon[NormsAndCounts],
    uselonrToProducelonrScoringFn: NelonighborWithWelonights => Doublelon,
    uselonrToClustelonrScoringFn: UselonrToIntelonrelonstelondInClustelonrScorelons => Doublelon,
    uselonrFiltelonr: NormsAndCounts => Boolelonan, // function to deloncidelon whelonthelonr to computelon elonmbelonddings for thelon uselonr or not
    numRelonducelonrsForMatrixMultiplication: Int,
    modelonlVelonrsion: ModelonlVelonrsion,
    threlonshold: Doublelon
  )(
    implicit uid: UniquelonID
  ): TypelondPipelon[((ClustelonrId, UselonrId), Doublelon)] = {
    val uselonrSimClustelonrsMatrix = gelontUselonrSimClustelonrsMatrix(
      intelonrelonstelondInClustelonrs,
      uselonrToClustelonrScoringFn,
      modelonlVelonrsion
    )

    val uselonrUselonrNormalizelondGraph = gelontFiltelonrelondUselonrUselonrNormalizelondGraph(
      uselonrProducelonrelonngagelonmelonntGraph,
      uselonrNormsAndCounts,
      uselonrToProducelonrScoringFn,
      uselonrFiltelonr
    )

    SimClustelonrselonmbelonddingJob
      .lelongacyMultiplyMatricelons(
        uselonrUselonrNormalizelondGraph,
        uselonrSimClustelonrsMatrix,
        numRelonducelonrsForMatrixMultiplication
      )
      .filtelonr(_._2 >= threlonshold)
  }

  delonf gelontFiltelonrelondUselonrUselonrNormalizelondGraph(
    uselonrProducelonrelonngagelonmelonntGraph: TypelondPipelon[UselonrAndNelonighbors],
    uselonrNormsAndCounts: TypelondPipelon[NormsAndCounts],
    uselonrToProducelonrScoringFn: NelonighborWithWelonights => Doublelon,
    uselonrFiltelonr: NormsAndCounts => Boolelonan
  )(
    implicit uid: UniquelonID
  ): TypelondPipelon[(UselonrId, (UselonrId, Doublelon))] = {
    val numUselonrsCount = Stat("num_uselonrs_with_elonngagelonmelonnts")
    val uselonrUselonrFiltelonrelondelondgelonCount = Stat("num_filtelonrelond_uselonr_uselonr_elonngagelonmelonnts")
    val validUselonrsCount = Stat("num_valid_uselonrs")

    val validUselonrs = uselonrNormsAndCounts.collelonct {
      caselon uselonr if uselonrFiltelonr(uselonr) =>
        validUselonrsCount.inc()
        uselonr.uselonrId
    }

    uselonrProducelonrelonngagelonmelonntGraph
      .flatMap { uselonrAndNelonighbors =>
        numUselonrsCount.inc()
        uselonrAndNelonighbors.nelonighbors
          .map { nelonighbor =>
            uselonrUselonrFiltelonrelondelondgelonCount.inc()
            (nelonighbor.nelonighborId, (uselonrAndNelonighbors.uselonrId, uselonrToProducelonrScoringFn(nelonighbor)))
          }
          .filtelonr(_._2._2 > 0.0)
      }
      .join(validUselonrs.asKelonys)
      .map {
        caselon (nelonighborId, ((uselonrId, scorelon), _)) =>
          (uselonrId, (nelonighborId, scorelon))
      }
  }

  delonf fromSimClustelonrelonmbelondding[T, elon](
    relonsultMatrix: TypelondPipelon[((ClustelonrId, T), Doublelon)],
    topK: Int,
    modelonlVelonrsion: ModelonlVelonrsion
  ): TypelondPipelon[(PelonrsistelondFullClustelonrId, Selonq[(T, Doublelon)])] = {
    relonsultMatrix
      .map {
        caselon ((clustelonrId, inputId), scorelon) => (clustelonrId, (inputId, scorelon))
      }
      .group
      .sortelondRelonvelonrselonTakelon(topK)(Ordelonring.by(_._2))
      .map {
        caselon (clustelonrId, topelonntitielonsWithScorelon) =>
          PelonrsistelondFullClustelonrId(modelonlVelonrsion, clustelonrId) -> topelonntitielonsWithScorelon
      }
  }

  delonf toSimClustelonrelonmbelondding[T](
    relonsultMatrix: TypelondPipelon[((ClustelonrId, T), Doublelon)],
    topK: Int,
    modelonlVelonrsion: ModelonlVelonrsion
  )(
    implicit ordelonring: Ordelonring[T]
  ): TypelondPipelon[(T, TopSimClustelonrsWithScorelon)] = {
    relonsultMatrix
      .map {
        caselon ((clustelonrId, inputId), scorelon) => (inputId, (clustelonrId, scorelon))
      }
      .group
      //.withRelonducelonrs(3000) // uncommelonnt for producelonr-simclustelonrs job
      .sortelondRelonvelonrselonTakelon(topK)(Ordelonring.by(_._2))
      .map {
        caselon (inputId, topSimClustelonrsWithScorelon) =>
          val topSimClustelonrs = topSimClustelonrsWithScorelon.map {
            caselon (clustelonrId, scorelon) => SimClustelonrWithScorelon(clustelonrId, scorelon)
          }
          inputId -> TopSimClustelonrsWithScorelon(topSimClustelonrs, modelonlVelonrsion)
      }
  }
}
