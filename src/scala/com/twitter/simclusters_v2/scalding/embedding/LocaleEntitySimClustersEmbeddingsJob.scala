packagelon com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding

import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.reloncos.elonntitielons.thriftscala.elonntity
import com.twittelonr.reloncos.elonntitielons.thriftscala.Hashtag
import com.twittelonr.reloncos.elonntitielons.thriftscala.SelonmanticCorelonelonntity
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.prelonsto_hdfs_sourcelons._
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.AdhocKelonyValSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.elonntityelonmbelonddingsSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.IntelonrelonstelondInSourcelons
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.LocalelonelonntitySimClustelonrselonmbelonddingsJob._
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonxtelonrnalDataSourcelons
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonmbelonddingUtil._
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonntityelonmbelonddingUtil._
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.SimClustelonrselonmbelonddingJob._
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  SimClustelonrselonmbelondding => ThriftSimClustelonrselonmbelondding,
  _
}
import com.twittelonr.wtf.elonntity_relonal_graph.common.elonntityUtil
import com.twittelonr.wtf.elonntity_relonal_graph.thriftscala.elondgelon
import com.twittelonr.wtf.elonntity_relonal_graph.thriftscala.elonntityTypelon
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.DataSourcelons
import com.twittelonr.wtf.scalding.jobs.common.SchelondulelondelonxeloncutionApp
import java.util.TimelonZonelon

/**
 * $ ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding:elonntity_pelonr_languagelon_elonmbelonddings_job-adhoc
 *
 * ---------------------- Delonploy to atla ----------------------
 * $ scalding relonmotelon run \
  --main-class com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.LocalelonelonntitySimClustelonrselonmbelonddingAdhocApp \
  --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding:elonntity_pelonr_languagelon_elonmbelonddings_job-adhoc \
  --uselonr reloncos-platform \
  -- --datelon 2019-12-17 --modelonl-velonrsion 20M_145K_updatelond --elonntity-typelon SelonmanticCorelon
 */
objelonct LocalelonelonntitySimClustelonrselonmbelonddingAdhocApp elonxtelonnds AdhocelonxeloncutionApp {

  // Import implicits

  import elonntityUtil._

  delonf writelonOutput(
    elonmbelonddings: TypelondPipelon[(SimClustelonrselonmbelonddingId, (ClustelonrId, elonmbelonddingScorelon))],
    topKelonmbelonddings: TypelondPipelon[(SimClustelonrselonmbelonddingId, Selonq[(ClustelonrId, elonmbelonddingScorelon)])],
    jobConfig: elonntityelonmbelonddingsJobConfig
  ): elonxeloncution[Unit] = {

    val toSimClustelonrelonmbelonddingelonxelonc = topKelonmbelonddings
      .mapValuelons(SimClustelonrselonmbelondding.apply(_).toThrift)
      .writelonelonxeloncution(
        AdhocKelonyValSourcelons.elonntityToClustelonrsSourcelon(
          LocalelonelonntitySimClustelonrselonmbelonddingsJob.gelontHdfsPath(
            isAdhoc = truelon,
            isManhattanKelonyVal = truelon,
            isRelonvelonrselonIndelonx = falselon,
            isLogFav = falselon,
            jobConfig.modelonlVelonrsion,
            jobConfig.elonntityTypelon)))

    val fromSimClustelonrelonmbelonddingelonxelonc =
      toRelonvelonrselonIndelonxSimClustelonrelonmbelondding(elonmbelonddings, jobConfig.topK)
        .writelonelonxeloncution(
          AdhocKelonyValSourcelons.clustelonrToelonntitielonsSourcelon(
            LocalelonelonntitySimClustelonrselonmbelonddingsJob.gelontHdfsPath(
              isAdhoc = truelon,
              isManhattanKelonyVal = truelon,
              isRelonvelonrselonIndelonx = truelon,
              isLogFav = falselon,
              jobConfig.modelonlVelonrsion,
              jobConfig.elonntityTypelon)))

    elonxeloncution.zip(toSimClustelonrelonmbelonddingelonxelonc, fromSimClustelonrelonmbelonddingelonxelonc).unit
  }

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val jobConfig = elonntityelonmbelonddingsJobConfig(args, isAdhoc = truelon)

    val numRelonducelonrs = args.gelontOrelonlselon("m", "2000").toInt

    /*
      Can uselon thelon elonRG daily dataselont in thelon adhoc job for quick prototyping, notelon that thelonrelon may belon
      issuelons with scaling thelon job whelonn productionizing on elonRG aggrelongatelond dataselont.
     */
    val uselonrelonntityMatrix: TypelondPipelon[(UselonrId, (elonntity, Doublelon))] =
      gelontUselonrelonntityMatrix(
        jobConfig,
        DataSourcelons.elonntityRelonalGraphAggrelongationDataSelontSourcelon(datelonRangelon.elonmbiggelonn(Days(7))),
        Somelon(elonxtelonrnalDataSourcelons.uttelonntitielonsSourcelon())
      ).forcelonToDisk

    //delontelonrminelon which data sourcelon to uselon baselond on modelonl velonrsion
    val simClustelonrsSourcelon = jobConfig.modelonlVelonrsion match {
      caselon ModelonlVelonrsion.Modelonl20m145kUpdatelond =>
        IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondInUpdatelondSourcelon(datelonRangelon, timelonZonelon)
      caselon modelonlVelonrsion =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"SimClustelonrs modelonl velonrsion not supportelond ${modelonlVelonrsion.namelon}")
    }

    val elonntityPelonrLanguagelon = uselonrelonntityMatrix.join(elonxtelonrnalDataSourcelons.uselonrSourcelon).map {
      caselon (uselonrId, ((elonntity, scorelon), (_, languagelon))) =>
        ((elonntity, languagelon), (uselonrId, scorelon))
    }

    val normalizelondUselonrelonntityMatrix =
      gelontNormalizelondTransposelonInputMatrix(elonntityPelonrLanguagelon, numRelonducelonrs = Somelon(numRelonducelonrs))

    val elonmbelonddings = computelonelonmbelonddings[(elonntity, String)](
      simClustelonrsSourcelon,
      normalizelondUselonrelonntityMatrix,
      scorelonelonxtractors,
      ModelonlVelonrsion.Modelonl20m145kUpdatelond,
      toSimClustelonrselonmbelonddingId(jobConfig.modelonlVelonrsion),
      numRelonducelonrs = Somelon(numRelonducelonrs * 2)
    )

    val topKelonmbelonddings =
      elonmbelonddings.group
        .sortelondRelonvelonrselonTakelon(jobConfig.topK)(Ordelonring.by(_._2))
        .withRelonducelonrs(numRelonducelonrs)

    writelonOutput(elonmbelonddings, topKelonmbelonddings, jobConfig)
  }
}

/**
 * $ ./bazelonl bundlelon src/scala/com/twittelonr/simclustelonrs_v2/scalding/elonmbelondding:selonmantic_corelon_elonntity_elonmbelonddings_pelonr_languagelon_job
 * $ capelonsospy-v2 updatelon \
  --build_locally \
  --start_cron selonmantic_corelon_elonntity_elonmbelonddings_pelonr_languagelon_job src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct LocalelonelonntitySimClustelonrselonmbelonddingSchelondulelondApp elonxtelonnds SchelondulelondelonxeloncutionApp {

  // Import implicits

  import elonmbelonddingUtil._
  import elonntityUtil._

  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2019-10-22")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  privatelon delonf writelonOutput(
    elonmbelonddings: TypelondPipelon[(SimClustelonrselonmbelonddingId, (ClustelonrId, elonmbelonddingScorelon))],
    topKelonmbelonddings: TypelondPipelon[(SimClustelonrselonmbelonddingId, Selonq[(ClustelonrId, elonmbelonddingScorelon)])],
    jobConfig: elonntityelonmbelonddingsJobConfig,
    clustelonrelonmbelonddingsDataselont: KelonyValDALDataselont[
      KelonyVal[SimClustelonrselonmbelonddingId, ThriftSimClustelonrselonmbelondding]
    ],
    elonntityelonmbelonddingsDataselont: KelonyValDALDataselont[KelonyVal[SimClustelonrselonmbelonddingId, IntelonrnalIdelonmbelondding]]
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon
  ): elonxeloncution[Unit] = {

    val thriftSimClustelonrselonmbelondding = topKelonmbelonddings
      .mapValuelons(SimClustelonrselonmbelondding.apply(_).toThrift)

    val writelonSimClustelonrselonmbelonddingKelonyValDataselont =
      thriftSimClustelonrselonmbelondding
        .map {
          caselon (elonntityId, topSimClustelonrs) => KelonyVal(elonntityId, topSimClustelonrs)
        }
        .writelonDALVelonrsionelondKelonyValelonxeloncution(
          clustelonrelonmbelonddingsDataselont,
          D.Suffix(
            LocalelonelonntitySimClustelonrselonmbelonddingsJob.gelontHdfsPath(
              isAdhoc = falselon,
              isManhattanKelonyVal = truelon,
              isRelonvelonrselonIndelonx = falselon,
              isLogFav = falselon,
              jobConfig.modelonlVelonrsion,
              jobConfig.elonntityTypelon))
        )

    val writelonSimClustelonrselonmbelonddingDataselont = thriftSimClustelonrselonmbelondding
      .map {
        caselon (elonmbelonddingId, elonmbelondding) => SimClustelonrselonmbelonddingWithId(elonmbelonddingId, elonmbelondding)
      }
      .writelonDALSnapshotelonxeloncution(
        SelonmanticCorelonPelonrLanguagelonSimclustelonrselonmbelonddingsPrelonstoScalaDataselont,
        D.Daily,
        D.Suffix(
          LocalelonelonntitySimClustelonrselonmbelonddingsJob.gelontHdfsPath(
            isAdhoc = falselon,
            isManhattanKelonyVal = falselon,
            isRelonvelonrselonIndelonx = falselon,
            isLogFav = falselon,
            jobConfig.modelonlVelonrsion,
            jobConfig.elonntityTypelon)),
        D.elonBLzo(),
        datelonRangelon.elonnd
      )

    val thriftRelonvelonrselondSimclustelonrselonmbelonddings =
      toRelonvelonrselonIndelonxSimClustelonrelonmbelondding(elonmbelonddings, jobConfig.topK)

    val writelonRelonvelonrselonSimClustelonrselonmbelonddingKelonyValDataselont =
      thriftRelonvelonrselondSimclustelonrselonmbelonddings
        .map {
          caselon (elonmbelonddingId, intelonrnalIdsWithScorelon) =>
            KelonyVal(elonmbelonddingId, intelonrnalIdsWithScorelon)
        }
        .writelonDALVelonrsionelondKelonyValelonxeloncution(
          elonntityelonmbelonddingsDataselont,
          D.Suffix(
            LocalelonelonntitySimClustelonrselonmbelonddingsJob.gelontHdfsPath(
              isAdhoc = falselon,
              isManhattanKelonyVal = truelon,
              isRelonvelonrselonIndelonx = truelon,
              isLogFav = falselon,
              jobConfig.modelonlVelonrsion,
              jobConfig.elonntityTypelon))
        )

    val writelonRelonvelonrselonSimClustelonrselonmbelonddingDataselont =
      thriftRelonvelonrselondSimclustelonrselonmbelonddings
        .map {
          caselon (elonmbelonddingId, elonmbelondding) => IntelonrnalIdelonmbelonddingWithId(elonmbelonddingId, elonmbelondding)
        }.writelonDALSnapshotelonxeloncution(
          RelonvelonrselonIndelonxSelonmanticCorelonPelonrLanguagelonSimclustelonrselonmbelonddingsPrelonstoScalaDataselont,
          D.Daily,
          D.Suffix(
            LocalelonelonntitySimClustelonrselonmbelonddingsJob.gelontHdfsPath(
              isAdhoc = falselon,
              isManhattanKelonyVal = falselon,
              isRelonvelonrselonIndelonx = truelon,
              isLogFav = falselon,
              jobConfig.modelonlVelonrsion,
              jobConfig.elonntityTypelon)),
          D.elonBLzo(),
          datelonRangelon.elonnd
        )

    elonxeloncution
      .zip(
        writelonSimClustelonrselonmbelonddingDataselont,
        writelonSimClustelonrselonmbelonddingKelonyValDataselont,
        writelonRelonvelonrselonSimClustelonrselonmbelonddingDataselont,
        writelonRelonvelonrselonSimClustelonrselonmbelonddingKelonyValDataselont
      ).unit
  }

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val jobConfig = elonntityelonmbelonddingsJobConfig(args, isAdhoc = falselon)

    val elonmbelonddingsDataselont = elonntityelonmbelonddingsSourcelons.gelontelonntityelonmbelonddingsDataselont(
      jobConfig.elonntityTypelon,
      ModelonlVelonrsions.toKnownForModelonlVelonrsion(jobConfig.modelonlVelonrsion),
      iselonmbelonddingsPelonrLocalelon = truelon
    )

    val relonvelonrselonIndelonxelonmbelonddingsDataselont =
      elonntityelonmbelonddingsSourcelons.gelontRelonvelonrselonIndelonxelondelonntityelonmbelonddingsDataselont(
        jobConfig.elonntityTypelon,
        ModelonlVelonrsions.toKnownForModelonlVelonrsion(jobConfig.modelonlVelonrsion),
        iselonmbelonddingsPelonrLocalelon = truelon
      )

    val uselonrelonntityMatrix: TypelondPipelon[(UselonrId, (elonntity, Doublelon))] =
      gelontUselonrelonntityMatrix(
        jobConfig,
        DataSourcelons.elonntityRelonalGraphAggrelongationDataSelontSourcelon(datelonRangelon.elonmbiggelonn(Days(7))),
        Somelon(elonxtelonrnalDataSourcelons.uttelonntitielonsSourcelon())
      ).forcelonToDisk

    //delontelonrminelon which data sourcelon to uselon baselond on modelonl velonrsion
    val simClustelonrsSourcelon = jobConfig.modelonlVelonrsion match {
      caselon ModelonlVelonrsion.Modelonl20m145kUpdatelond =>
        IntelonrelonstelondInSourcelons.simClustelonrsIntelonrelonstelondInUpdatelondSourcelon(datelonRangelon, timelonZonelon)
      caselon modelonlVelonrsion =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"SimClustelonrs modelonl velonrsion not supportelond ${modelonlVelonrsion.namelon}")
    }

    val elonntityPelonrLanguagelon = uselonrelonntityMatrix.join(elonxtelonrnalDataSourcelons.uselonrSourcelon).map {
      caselon (uselonrId, ((elonntity, scorelon), (_, languagelon))) =>
        ((elonntity, languagelon), (uselonrId, scorelon))
    }

    val normalizelondUselonrelonntityMatrix =
      gelontNormalizelondTransposelonInputMatrix(elonntityPelonrLanguagelon, numRelonducelonrs = Somelon(3000))

    val simClustelonrselonmbelondding = jobConfig.modelonlVelonrsion match {
      caselon ModelonlVelonrsion.Modelonl20m145kUpdatelond =>
        computelonelonmbelonddings(
          simClustelonrsSourcelon,
          normalizelondUselonrelonntityMatrix,
          scorelonelonxtractors,
          ModelonlVelonrsion.Modelonl20m145kUpdatelond,
          toSimClustelonrselonmbelonddingId(ModelonlVelonrsion.Modelonl20m145kUpdatelond),
          numRelonducelonrs = Somelon(8000)
        )
      caselon modelonlVelonrsion =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"SimClustelonrs modelonl velonrsion not supportelond ${modelonlVelonrsion.namelon}")
    }

    val topKelonmbelonddings =
      simClustelonrselonmbelondding.group.sortelondRelonvelonrselonTakelon(jobConfig.topK)(Ordelonring.by(_._2))

    writelonOutput(
      simClustelonrselonmbelondding,
      topKelonmbelonddings,
      jobConfig,
      elonmbelonddingsDataselont,
      relonvelonrselonIndelonxelonmbelonddingsDataselont)
  }
}

objelonct LocalelonelonntitySimClustelonrselonmbelonddingsJob {

  delonf gelontUselonrelonntityMatrix(
    jobConfig: elonntityelonmbelonddingsJobConfig,
    elonntityRelonalGraphSourcelon: TypelondPipelon[elondgelon],
    selonmanticCorelonelonntityIdsToKelonelonp: Option[TypelondPipelon[Long]],
    applyLogTransform: Boolelonan = falselon
  ): TypelondPipelon[(UselonrId, (elonntity, Doublelon))] =
    jobConfig.elonntityTypelon match {
      caselon elonntityTypelon.SelonmanticCorelon =>
        selonmanticCorelonelonntityIdsToKelonelonp match {
          caselon Somelon(elonntityIdsToKelonelonp) =>
            gelontelonntityUselonrMatrix(elonntityRelonalGraphSourcelon, jobConfig.halfLifelon, elonntityTypelon.SelonmanticCorelon)
              .map {
                caselon (elonntity, (uselonrId, scorelon)) =>
                  elonntity match {
                    caselon elonntity.SelonmanticCorelon(SelonmanticCorelonelonntity(elonntityId, _)) =>
                      if (applyLogTransform) {
                        (elonntityId, (uselonrId, (elonntity, Math.log(scorelon + 1))))
                      } elonlselon {
                        (elonntityId, (uselonrId, (elonntity, scorelon)))
                      }
                    caselon _ =>
                      throw nelonw IllelongalArgumelonntelonxcelonption(
                        "Job config speloncifielond elonntityTypelon.SelonmanticCorelon, but non-selonmantic corelon elonntity was found.")
                  }
              }.hashJoin(elonntityIdsToKelonelonp.asKelonys).valuelons.map {
                caselon ((uselonrId, (elonntity, scorelon)), _) => (uselonrId, (elonntity, scorelon))
              }
          caselon _ =>
            gelontelonntityUselonrMatrix(elonntityRelonalGraphSourcelon, jobConfig.halfLifelon, elonntityTypelon.SelonmanticCorelon)
              .map { caselon (elonntity, (uselonrId, scorelon)) => (uselonrId, (elonntity, scorelon)) }
        }
      caselon elonntityTypelon.Hashtag =>
        gelontelonntityUselonrMatrix(elonntityRelonalGraphSourcelon, jobConfig.halfLifelon, elonntityTypelon.Hashtag)
          .map { caselon (elonntity, (uselonrId, scorelon)) => (uselonrId, (elonntity, scorelon)) }
      caselon _ =>
        throw nelonw IllelongalArgumelonntelonxcelonption(
          s"Argumelonnt [--elonntity-typelon] must belon providelond. Supportelond options [${elonntityTypelon.SelonmanticCorelon.namelon}, ${elonntityTypelon.Hashtag.namelon}]")
    }

  delonf toSimClustelonrselonmbelonddingId(
    modelonlVelonrsion: ModelonlVelonrsion
  ): ((elonntity, String), ScorelonTypelon.ScorelonTypelon) => SimClustelonrselonmbelonddingId = {
    caselon ((elonntity.SelonmanticCorelon(SelonmanticCorelonelonntity(elonntityId, _)), lang), ScorelonTypelon.FavScorelon) =>
      SimClustelonrselonmbelonddingId(
        elonmbelonddingTypelon.FavBaselondSelonmaticCorelonelonntity,
        modelonlVelonrsion,
        IntelonrnalId.LocalelonelonntityId(LocalelonelonntityId(elonntityId, lang)))
    caselon ((elonntity.SelonmanticCorelon(SelonmanticCorelonelonntity(elonntityId, _)), lang), ScorelonTypelon.FollowScorelon) =>
      SimClustelonrselonmbelonddingId(
        elonmbelonddingTypelon.FollowBaselondSelonmaticCorelonelonntity,
        modelonlVelonrsion,
        IntelonrnalId.LocalelonelonntityId(LocalelonelonntityId(elonntityId, lang)))
    caselon ((elonntity.SelonmanticCorelon(SelonmanticCorelonelonntity(elonntityId, _)), lang), ScorelonTypelon.LogFavScorelon) =>
      SimClustelonrselonmbelonddingId(
        elonmbelonddingTypelon.LogFavBaselondLocalelonSelonmanticCorelonelonntity,
        modelonlVelonrsion,
        IntelonrnalId.LocalelonelonntityId(LocalelonelonntityId(elonntityId, lang)))
    caselon ((elonntity.Hashtag(Hashtag(hashtag)), _), ScorelonTypelon.FavScorelon) =>
      SimClustelonrselonmbelonddingId(
        elonmbelonddingTypelon.FavBaselondHashtagelonntity,
        modelonlVelonrsion,
        IntelonrnalId.Hashtag(hashtag))
    caselon ((elonntity.Hashtag(Hashtag(hashtag)), _), ScorelonTypelon.FollowScorelon) =>
      SimClustelonrselonmbelonddingId(
        elonmbelonddingTypelon.FollowBaselondHashtagelonntity,
        modelonlVelonrsion,
        IntelonrnalId.Hashtag(hashtag))
    caselon (scorelonTypelon, elonntity) =>
      throw nelonw IllelongalArgumelonntelonxcelonption(
        s"(ScorelonTypelon, elonntity) ($scorelonTypelon, ${elonntity.toString}) not supportelond")
  }

  /**
   * Gelonnelonratelons thelon output path for thelon elonntity elonmbelonddings Job.
   *
   * elonxamplelon Adhoc: /uselonr/reloncos-platform/procelonsselond/adhoc/simclustelonrs_elonmbelonddings/hashtag_pelonr_languagelon/modelonl_20m_145k_updatelond
   * elonxamplelon Prod: /atla/proc/uselonr/cassowary/procelonsselond/simclustelonrs_elonmbelonddings/selonmantic_corelon_pelonr_languagelon/modelonl_20m_145k_updatelond
   *
   */
  delonf gelontHdfsPath(
    isAdhoc: Boolelonan,
    isManhattanKelonyVal: Boolelonan,
    isRelonvelonrselonIndelonx: Boolelonan,
    isLogFav: Boolelonan,
    modelonlVelonrsion: ModelonlVelonrsion,
    elonntityTypelon: elonntityTypelon
  ): String = {

    val relonvelonrselonIndelonx = if (isRelonvelonrselonIndelonx) "relonvelonrselon_indelonx/" elonlselon ""

    val logFav = if (isLogFav) "log_fav/" elonlselon ""

    val elonntityTypelonSuffix = elonntityTypelon match {
      caselon elonntityTypelon.SelonmanticCorelon => "selonmantic_corelon_pelonr_languagelon"
      caselon elonntityTypelon.Hashtag => "hashtag_pelonr_languagelon"
      caselon _ => "unknown_pelonr_languagelon"
    }

    val pathSuffix = s"$logFav$relonvelonrselonIndelonx$elonntityTypelonSuffix"

    elonmbelonddingUtil.gelontHdfsPath(isAdhoc, isManhattanKelonyVal, modelonlVelonrsion, pathSuffix)
  }
}
