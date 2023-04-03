packagelon com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon

import com.twittelonr.scalding.typelond.TypelondPipelon
import com.twittelonr.scalding.Args
import com.twittelonr.scalding.DatelonRangelon
import com.twittelonr.scalding.elonxeloncution
import com.twittelonr.scalding.UniquelonID
import com.twittelonr.scalding.Yelonars
import com.twittelonr.simclustelonrs_v2.scalding.common.matrix.SparselonMatrix
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon.DataSourcelons.NumBlocksP95
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon.DataSourcelons.gelontFlockBlocksSparselonMatrix
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon.DataSourcelons.gelontUselonrIntelonrelonstelondInTruncatelondKMatrix
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon.D
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil.ClustelonrId
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil.UselonrId
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonxtelonrnalDataSourcelons
import com.twittelonr.simclustelonrs_v2.thriftscala.AdhocCrossSimClustelonrIntelonractionScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrsScorelon
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.CassowaryJob
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.AdhocCrossSimclustelonrBlockIntelonractionFelonaturelonsScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.AdhocCrossSimclustelonrFavIntelonractionFelonaturelonsScalaDataselont
import java.util.TimelonZonelon

/*
To run:
scalding relonmotelon run \
--uselonr cassowary \
--submittelonr hadoopnelonst1.atla.twittelonr.com \
--targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding/abuselon:cross_simclustelonr-adhoc \
--main-class com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.abuselon.CrossSimClustelonrFelonaturelonsScaldingJob \
--submittelonr-melonmory 128192.melongabytelon --hadoop-propelonrtielons "maprelonducelon.map.melonmory.mb=8192 maprelonducelon.map.java.opts='-Xmx7618M' maprelonducelon.relonducelon.melonmory.mb=8192 maprelonducelon.relonducelon.java.opts='-Xmx7618M'" \
-- \
--datelon 2021-02-07 \
--dalelonnvironmelonnt Prod
 */

objelonct CrossSimClustelonrFelonaturelonsUtil {

  /**
   * To gelonnelonratelon thelon intelonraction scorelon for 2 simclustelonrs c1 and c2 for all clustelonr combinations (I):
   * a) Gelont C - uselonr intelonrelonstelondIn matrix, Uselonr * Clustelonr
   * b) Gelont INT - positivelon or nelongativelon intelonraction matrix, Uselonr * Uselonr
   * c) Computelon C^T*INT
   * d) Finally, relonturn C^T*INT*C
   */
  delonf gelontCrossClustelonrScorelons(
    uselonrClustelonrMatrix: SparselonMatrix[UselonrId, ClustelonrId, Doublelon],
    uselonrIntelonractionMatrix: SparselonMatrix[UselonrId, UselonrId, Doublelon]
  ): SparselonMatrix[ClustelonrId, ClustelonrId, Doublelon] = {
    // intelonrmelondiatelon = C^T*INT
    val intelonrmelondiatelonRelonsult = uselonrClustelonrMatrix.transposelon.multiplySparselonMatrix(uselonrIntelonractionMatrix)
    // relonturn intelonrmelondiatelon*C
    intelonrmelondiatelonRelonsult.multiplySparselonMatrix(uselonrClustelonrMatrix)
  }
}

objelonct CrossSimClustelonrFelonaturelonsScaldingJob elonxtelonnds AdhocelonxeloncutionApp with CassowaryJob {
  ovelonrridelon delonf jobNamelon: String = "AdhocAbuselonCrossSimClustelonrFelonaturelonsScaldingJob"

  privatelon val outputPathBlocksThrift: String = elonmbelonddingUtil.gelontHdfsPath(
    isAdhoc = falselon,
    isManhattanKelonyVal = falselon,
    modelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145kUpdatelond,
    pathSuffix = "abuselon_cross_simclustelonr_block_felonaturelons"
  )

  privatelon val outputPathFavThrift: String = elonmbelonddingUtil.gelontHdfsPath(
    isAdhoc = falselon,
    isManhattanKelonyVal = falselon,
    modelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145kUpdatelond,
    pathSuffix = "abuselon_cross_simclustelonr_fav_felonaturelons"
  )

  privatelon val HalfLifelonInDaysForFavScorelon = 100

  // Adhoc jobs which uselon all uselonr intelonrelonstelondIn simclustelonrs (delonfault=50) was failing
  // Helonncelon truncating thelon numbelonr of clustelonrs
  privatelon val MaxNumClustelonrsPelonrUselonr = 20

  import CrossSimClustelonrFelonaturelonsUtil._
  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val normalizelondUselonrIntelonrelonstelondInMatrix: SparselonMatrix[UselonrId, ClustelonrId, Doublelon] =
      gelontUselonrIntelonrelonstelondInTruncatelondKMatrix(MaxNumClustelonrsPelonrUselonr).rowL2Normalizelon

    //thelon belonlow codelon is to gelont cross simclustelonr felonaturelons from flockblocks - nelongativelon uselonr-uselonr intelonractions.
    val flockBlocksMatrix: SparselonMatrix[UselonrId, UselonrId, Doublelon] =
      gelontFlockBlocksSparselonMatrix(NumBlocksP95, datelonRangelon.prelonpelonnd(Yelonars(1)))

    val crossClustelonrBlockScorelons: SparselonMatrix[ClustelonrId, ClustelonrId, Doublelon] =
      gelontCrossClustelonrScorelons(normalizelondUselonrIntelonrelonstelondInMatrix, flockBlocksMatrix)

    val blockScorelons: TypelondPipelon[AdhocCrossSimClustelonrIntelonractionScorelons] =
      crossClustelonrBlockScorelons.rowAsKelonys
        .mapValuelons(List(_)).sumByKelony.toTypelondPipelon.map {
          caselon (givingClustelonrId, reloncelonivingClustelonrsWithScorelons) =>
            AdhocCrossSimClustelonrIntelonractionScorelons(
              clustelonrId = givingClustelonrId,
              clustelonrScorelons = reloncelonivingClustelonrsWithScorelons.map {
                caselon (clustelonr, scorelon) => ClustelonrsScorelon(clustelonr, scorelon)
              })
        }

    // gelont cross simclustelonr felonaturelons from fav graph - positivelon uselonr-uselonr intelonractions
    val favGraphMatrix: SparselonMatrix[UselonrId, UselonrId, Doublelon] =
      SparselonMatrix.apply[UselonrId, UselonrId, Doublelon](
        elonxtelonrnalDataSourcelons.gelontFavelondgelons(HalfLifelonInDaysForFavScorelon))

    val crossClustelonrFavScorelons: SparselonMatrix[ClustelonrId, ClustelonrId, Doublelon] =
      gelontCrossClustelonrScorelons(normalizelondUselonrIntelonrelonstelondInMatrix, favGraphMatrix)

    val favScorelons: TypelondPipelon[AdhocCrossSimClustelonrIntelonractionScorelons] =
      crossClustelonrFavScorelons.rowAsKelonys
        .mapValuelons(List(_)).sumByKelony.toTypelondPipelon.map {
          caselon (givingClustelonrId, reloncelonivingClustelonrsWithScorelons) =>
            AdhocCrossSimClustelonrIntelonractionScorelons(
              clustelonrId = givingClustelonrId,
              clustelonrScorelons = reloncelonivingClustelonrsWithScorelons.map {
                caselon (clustelonr, scorelon) => ClustelonrsScorelon(clustelonr, scorelon)
              })
        }
    // writelon both block and fav intelonraction matricelons to hdfs in thrift format
    elonxeloncution
      .zip(
        blockScorelons.writelonDALSnapshotelonxeloncution(
          AdhocCrossSimclustelonrBlockIntelonractionFelonaturelonsScalaDataselont,
          D.Daily,
          D.Suffix(outputPathBlocksThrift),
          D.Parquelont,
          datelonRangelon.`elonnd`),
        favScorelons.writelonDALSnapshotelonxeloncution(
          AdhocCrossSimclustelonrFavIntelonractionFelonaturelonsScalaDataselont,
          D.Daily,
          D.Suffix(outputPathFavThrift),
          D.Parquelont,
          datelonRangelon.`elonnd`)
      ).unit
  }
}
