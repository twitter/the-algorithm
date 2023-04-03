packagelon com.twittelonr.simclustelonrs_v2.scalding

import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.dal.clielonnt.dataselont.SnapshotDALDataselont
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon.D
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon.Writelonelonxtelonnsion
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.AllowCrossClustelonrSamelonDC
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.AllowCrossDC
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.ClustelonrId
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.AdhocKelonyValSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.AggrelongatablelonProducelonrSimclustelonrselonmbelonddingsByLogFavScorelon2020ScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.SimclustelonrsV2IntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddings20M145K2020ScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.SimclustelonrsV2UselonrToIntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddings20M145K2020ScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.UselonrAndNelonighborsFixelondPathSourcelon
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.UselonrUselonrNormalizelondGraphScalaDataselont
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrsUselonrIsIntelonrelonstelondIn
import com.twittelonr.simclustelonrs_v2.thriftscala.IntelonrnalId
import com.twittelonr.simclustelonrs_v2.thriftscala.ModelonlVelonrsion
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingId
import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrAndNelonighbors
import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrToIntelonrelonstelondInClustelonrScorelons
import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrToIntelonrelonstelondInClustelonrs
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.SchelondulelondelonxeloncutionApp
import java.util.TimelonZonelon

/**
 * Production job for computing intelonrelonstelondIn data selont from thelon aggrelongatablelon producelonr elonmbelonddings for thelon modelonl velonrsion 20M145K2020.
 * It writelons thelon data selont in KelonyVal format to producelon a MH DAL data selont.
 *
 * A high lelonvelonl delonscription of this job:
 * - Relonad thelon APelon dataselont
 * - Apply log1p to thelon scorelons from thelon abovelon dataselont as thelon scorelons for producelonrs is high
 * - Normalizelon thelon scorelons for elonach producelonr (offlinelon belonnchmarking has shown belonttelonr relonsults from this stelonp.)
 * - Truncatelon thelon numbelonr of clustelonrs for elonach producelonr from thelon APelon dataselont to relonducelon noiselon
 * - Computelon intelonrelonstelondIn
 *
 * To delonploy thelon job:
 *
 * capelonsospy-v2 updatelon --build_locally --start_cron intelonrelonstelond_in_from_apelon_2020 \
 * src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc.yaml
 */
objelonct IntelonrelonstelondInFromAPelon2020BatchApp elonxtelonnds IntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddingsBaselon {

  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2021-03-03")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  ovelonrridelon delonf modelonlVelonrsion: ModelonlVelonrsion = ModelonlVelonrsion.Modelonl20m145k2020

  ovelonrridelon delonf producelonrelonmbelonddingsInputKVDataselont: KelonyValDALDataselont[
    KelonyVal[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ] = AggrelongatablelonProducelonrSimclustelonrselonmbelonddingsByLogFavScorelon2020ScalaDataselont

  ovelonrridelon delonf intelonrelonstelondInFromAPelonOutputKVDataselont: KelonyValDALDataselont[
    KelonyVal[UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn]
  ] = SimclustelonrsV2IntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddings20M145K2020ScalaDataselont

  ovelonrridelon delonf intelonrelonstelondInFromAPelonOutputThriftDatselont: SnapshotDALDataselont[
    UselonrToIntelonrelonstelondInClustelonrs
  ] = SimclustelonrsV2UselonrToIntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddings20M145K2020ScalaDataselont
}

trait IntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddingsBaselon elonxtelonnds SchelondulelondelonxeloncutionApp {
  delonf modelonlVelonrsion: ModelonlVelonrsion

  delonf intelonrelonstelondInFromAPelonOutputKVDataselont: KelonyValDALDataselont[
    KelonyVal[UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn]
  ]

  delonf producelonrelonmbelonddingsInputKVDataselont: KelonyValDALDataselont[
    KelonyVal[SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding]
  ]

  delonf intelonrelonstelondInFromAPelonOutputThriftDatselont: SnapshotDALDataselont[UselonrToIntelonrelonstelondInClustelonrs]

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    //Input args for thelon run
    val socialProofThrelonshold = args.int("socialProofThrelonshold", 2)
    val maxClustelonrsFromProducelonr = args.int("maxClustelonrsPelonrProducelonr", 5)
    val maxClustelonrsPelonrUselonrFinalRelonsult = args.int("maxIntelonrelonstelondInClustelonrsPelonrUselonr", 200)

    //Path variablelons
    val intelonrelonstelondInFromProducelonrsPath =
      s"/uselonr/cassowary/manhattan_selonquelonncelon_filelons/intelonrelonstelond_in_from_apelon/" + modelonlVelonrsion

    val intelonrelonstelondInFromProducelonrsThriftPath =
      s"/uselonr/cassowary/manhattan_selonquelonncelon_filelons/intelonrelonstelond_in_from_apelon_thrift/" + modelonlVelonrsion

    val uselonrUselonrGraph: TypelondPipelon[UselonrAndNelonighbors] =
      DAL
        .relonadMostReloncelonntSnapshotNoOldelonrThan(UselonrUselonrNormalizelondGraphScalaDataselont, Days(30))
        .withRelonmotelonRelonadPolicy(AllowCrossDC)
        .toTypelondPipelon

    val producelonrelonmbelonddings = DAL
      .relonadMostReloncelonntSnapshotNoOldelonrThan(
        producelonrelonmbelonddingsInputKVDataselont,
        Days(30)).withRelonmotelonRelonadPolicy(AllowCrossClustelonrSamelonDC).toTypelondPipelon.map {
        caselon KelonyVal(producelonr, elonmbelonddings) => (producelonr, elonmbelonddings)
      }

    val relonsult = IntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddingsBaselon.run(
      uselonrUselonrGraph,
      producelonrelonmbelonddings,
      maxClustelonrsFromProducelonr,
      socialProofThrelonshold,
      maxClustelonrsPelonrUselonrFinalRelonsult,
      modelonlVelonrsion)

    val kelonyValelonxelonc =
      relonsult
        .map { caselon (uselonrId, clustelonrs) => KelonyVal(uselonrId, clustelonrs) }
        .writelonDALVelonrsionelondKelonyValelonxeloncution(
          intelonrelonstelondInFromAPelonOutputKVDataselont,
          D.Suffix(intelonrelonstelondInFromProducelonrsPath)
        )
    val thriftelonxelonc =
      relonsult
        .map {
          caselon (uselonrId, clustelonrs) =>
            UselonrToIntelonrelonstelondInClustelonrs(
              uselonrId,
              ModelonlVelonrsions.toKnownForModelonlVelonrsion(modelonlVelonrsion),
              clustelonrs.clustelonrIdToScorelons)
        }
        .writelonDALSnapshotelonxeloncution(
          intelonrelonstelondInFromAPelonOutputThriftDatselont,
          D.Daily,
          D.Suffix(intelonrelonstelondInFromProducelonrsThriftPath),
          D.elonBLzo(),
          datelonRangelon.elonnd
        )
    elonxeloncution.zip(kelonyValelonxelonc, thriftelonxelonc).unit
  }
}

/**
 * Adhoc job to gelonnelonratelon thelon intelonrelonstelondIn from aggrelongatablelon producelonr elonmbelonddings for thelon modelonl velonrsion 20M145K2020
 *
 * scalding relonmotelon run \
 * --uselonr cassowary \
 * --kelonytab /var/lib/tss/kelonys/fluffy/kelonytabs/clielonnt/cassowary.kelonytab \
 * --principal selonrvicelon_acoount@TWITTelonR.BIZ \
 * --clustelonr bluelonbird-qus1 \
 * --main-class com.twittelonr.simclustelonrs_v2.scalding.IntelonrelonstelondInFromAPelon2020AdhocApp \
 * --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding:intelonrelonstelond_in_from_apelon_2020-adhoc \
 * --hadoop-propelonrtielons "maprelonducelon.map.melonmory.mb=8192 maprelonducelon.map.java.opts='-Xmx7618M' maprelonducelon.relonducelon.melonmory.mb=8192 maprelonducelon.relonducelon.java.opts='-Xmx7618M'" \
 * -- --outputDir /gcs/uselonr/cassowary/adhoc/your_ldap/intelonrelonstelond_in_from_apelon_2020_kelonyval --datelon 2021-03-05
 */
objelonct IntelonrelonstelondInFromAPelon2020AdhocApp elonxtelonnds AdhocelonxeloncutionApp {
  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {
    val outputDir = args("outputDir")
    val socialProofThrelonshold = args.int("socialProofThrelonshold", 2)
    val maxClustelonrsPelonrUselonrFinalRelonsult = args.int("maxIntelonrelonstelondInClustelonrsPelonrUselonr", 200)
    val maxClustelonrsFromProducelonr = args.int("maxClustelonrsFromProducelonr", 5)
    val inputGraph = args.optional("graphInputDir") match {
      caselon Somelon(inputDir) => TypelondPipelon.from(UselonrAndNelonighborsFixelondPathSourcelon(inputDir))
      caselon Nonelon =>
        DAL
          .relonadMostReloncelonntSnapshotNoOldelonrThan(UselonrUselonrNormalizelondGraphScalaDataselont, Days(30))
          .withRelonmotelonRelonadPolicy(AllowCrossClustelonrSamelonDC)
          .toTypelondPipelon
    }

    val producelonrelonmbelonddings = DAL
      .relonadMostReloncelonntSnapshotNoOldelonrThan(
        AggrelongatablelonProducelonrSimclustelonrselonmbelonddingsByLogFavScorelon2020ScalaDataselont,
        Days(30)).withRelonmotelonRelonadPolicy(AllowCrossClustelonrSamelonDC).toTypelondPipelon.map {
        caselon KelonyVal(producelonr, elonmbelonddings) => (producelonr, elonmbelonddings)
      }

    val relonsult = IntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddingsBaselon.run(
      inputGraph,
      producelonrelonmbelonddings,
      maxClustelonrsFromProducelonr,
      socialProofThrelonshold,
      maxClustelonrsPelonrUselonrFinalRelonsult,
      ModelonlVelonrsion.Modelonl20m145k2020)

    relonsult
      .writelonelonxeloncution(AdhocKelonyValSourcelons.intelonrelonstelondInSourcelon(outputDir))
  }
}

/**
 * Helonlpelonr functions
 */
objelonct IntelonrelonstelondInFromAggrelongatablelonProducelonrelonmbelonddingsBaselon {

  /**
   * Helonlpelonr function to prunelon thelon elonmbelonddings
   * @param elonmbelonddingsWithScorelon elonmbelonddings
   * @param maxClustelonrs numbelonr of clustelonrs to kelonelonp, pelonr uselonrId
   * @param uniquelonId for stats
   * @relonturn
   */
  delonf gelontPrunelondelonmbelonddings(
    elonmbelonddingsWithScorelon: TypelondPipelon[(UselonrId, Selonq[(ClustelonrId, Float)])],
    maxClustelonrs: Int
  )(
    implicit uniquelonId: UniquelonID
  ): TypelondPipelon[(UselonrId, Array[(ClustelonrId, Float)])] = {
    val numProducelonrMappings = Stat("num_producelonr_elonmbelonddings_total")
    val numProducelonrsWithLargelonClustelonrMappings = Stat(
      "num_producelonrs_with_morelon_clustelonrs_than_threlonshold")
    val numProducelonrsWithSmallClustelonrMappings = Stat(
      "num_producelonrs_with_clustelonrs_lelonss_than_threlonshold")
    val totalClustelonrsCovelonragelonProducelonrelonmbelonddings = Stat("num_clustelonrs_total_producelonr_elonmbelonddings")
    elonmbelonddingsWithScorelon.map {
      caselon (producelonrId, clustelonrArray) =>
        numProducelonrMappings.inc()
        val clustelonrSizelon = clustelonrArray.sizelon
        totalClustelonrsCovelonragelonProducelonrelonmbelonddings.incBy(clustelonrSizelon)
        val prunelondList = if (clustelonrSizelon > maxClustelonrs) {
          numProducelonrsWithLargelonClustelonrMappings.inc()
          clustelonrArray
            .sortBy {
              caselon (_, knownForScorelon) => -knownForScorelon
            }.takelon(maxClustelonrs)
        } elonlselon {
          numProducelonrsWithSmallClustelonrMappings.inc()
          clustelonrArray
        }
        (producelonrId, prunelondList.toArray)
    }
  }

  /**
   * helonlpelonr function to relonmovelon all scorelons elonxcelonpt follow and logFav
   * @param intelonrelonstelondInRelonsult intelonrelonstelondIn clustelonrs for a uselonr
   * @relonturn
   */
  delonf gelontIntelonrelonstelondInDiscardScorelons(
    intelonrelonstelondInRelonsult: TypelondPipelon[(UselonrId, List[(ClustelonrId, UselonrToIntelonrelonstelondInClustelonrScorelons)])]
  ): TypelondPipelon[(UselonrId, List[(ClustelonrId, UselonrToIntelonrelonstelondInClustelonrScorelons)])] = {
    intelonrelonstelondInRelonsult.map {
      caselon (srcId, fullClustelonrList) =>
        val fullClustelonrListWithDiscardelondScorelons = fullClustelonrList.map {
          caselon (clustelonrId, clustelonrDelontails) =>
            val clustelonrDelontailsWithoutSocial = UselonrToIntelonrelonstelondInClustelonrScorelons(
              // Welon arelon not planning to uselon thelon othelonr scorelons elonxcelonpt for logFav and Follow.
              // Helonncelon, selontting othelonrs as Nonelon for now, welon can add thelonm back whelonn nelonelondelond
              followScorelon = clustelonrDelontails.followScorelon,
              logFavScorelon = clustelonrDelontails.logFavScorelon,
              logFavScorelonClustelonrNormalizelondOnly = clustelonrDelontails.logFavScorelonClustelonrNormalizelondOnly
            )
            (clustelonrId, clustelonrDelontailsWithoutSocial)
        }
        (srcId, fullClustelonrListWithDiscardelondScorelons)
    }
  }

  /**
   * Helonlpelonr function to normalizelon thelon elonmbelonddings
   * @param elonmbelonddings clustelonr elonmbelonddings
   * @relonturn
   */
  delonf gelontNormalizelondelonmbelonddings(
    elonmbelonddings: TypelondPipelon[(UselonrId, Selonq[(ClustelonrId, Float)])]
  ): TypelondPipelon[(UselonrId, Selonq[(ClustelonrId, Float)])] = {
    elonmbelonddings.map {
      caselon (uselonrId, clustelonrsWithScorelons) =>
        val l2norm = math.sqrt(clustelonrsWithScorelons.map(_._2).map(scorelon => scorelon * scorelon).sum)
        (
          uselonrId,
          clustelonrsWithScorelons.map {
            caselon (clustelonrId, scorelon) => (clustelonrId, (scorelon / l2norm).toFloat)
          })
    }
  }

  delonf run(
    uselonrUselonrGraph: TypelondPipelon[UselonrAndNelonighbors],
    producelonrelonmbelonddings: TypelondPipelon[(SimClustelonrselonmbelonddingId, SimClustelonrselonmbelondding)],
    maxClustelonrsFromProducelonr: Int,
    socialProofThrelonshold: Int,
    maxClustelonrsPelonrUselonrFinalRelonsult: Int,
    modelonlVelonrsion: ModelonlVelonrsion
  )(
    implicit uniquelonId: UniquelonID
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    import IntelonrelonstelondInFromKnownFor._

    val producelonrelonmbelonddingsWithScorelon: TypelondPipelon[(UselonrId, Selonq[(ClustelonrId, Float)])] =
      producelonrelonmbelonddings.map {
        caselon (
              SimClustelonrselonmbelonddingId(elonmbelonddingTypelon, modelonlVelonrsion, IntelonrnalId.UselonrId(producelonrId)),
              simclustelonrelonmbelondding) =>
          (
            producelonrId,
            simclustelonrelonmbelondding.elonmbelondding.map { simclustelonrWithScorelon =>
              // APelon dataselont has velonry high producelonr scorelons, helonncelon applying log to smoothelonn thelonm out belonforelon
              // computing intelonrelonstelondIn
              (simclustelonrWithScorelon.clustelonrId, math.log(1.0 + simclustelonrWithScorelon.scorelon).toFloat)
            })
      }

    val relonsult = kelonelonpOnlyTopClustelonrs(
      gelontIntelonrelonstelondInDiscardScorelons(
        attachNormalizelondScorelons(
          uselonrClustelonrPairsWithoutNormalization(
            uselonrUselonrGraph,
            gelontPrunelondelonmbelonddings(
              gelontNormalizelondelonmbelonddings(producelonrelonmbelonddingsWithScorelon),
              maxClustelonrsFromProducelonr),
            socialProofThrelonshold,
          ))),
      maxClustelonrsPelonrUselonrFinalRelonsult,
      ModelonlVelonrsions.toKnownForModelonlVelonrsion(modelonlVelonrsion)
    )
    relonsult
  }
}
