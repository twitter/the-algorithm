packagelon com.twittelonr.simclustelonrs_v2.scalding

import com.twittelonr.dal.clielonnt.dataselont.KelonyValDALDataselont
import com.twittelonr.scalding.elonxeloncution
import com.twittelonr.scalding.TypelondTsv
import com.twittelonr.scalding._
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.DALWritelon._
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.elonxplicitLocation
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.ProcAtla
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.ModelonlVelonrsions
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.ProducelonrelonmbelonddingSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.AdhocKelonyValSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.DataSourcelons
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.SimclustelonrsV2IntelonrelonstelondInFromProducelonrelonmbelonddings20M145KUpdatelondScalaDataselont
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.UselonrAndNelonighborsFixelondPathSourcelon
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.UselonrUselonrNormalizelondGraphScalaDataselont
import com.twittelonr.simclustelonrs_v2.scalding.common.Util
import com.twittelonr.simclustelonrs_v2.thriftscala.ClustelonrsUselonrIsIntelonrelonstelondIn
import com.twittelonr.simclustelonrs_v2.thriftscala.elonmbelonddingTypelon
import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrWithScorelon
import com.twittelonr.simclustelonrs_v2.thriftscala.TopSimClustelonrsWithScorelon
import com.twittelonr.simclustelonrs_v2.thriftscala.UselonrToIntelonrelonstelondInClustelonrScorelons
import com.twittelonr.wtf.scalding.jobs.common.AdhocelonxeloncutionApp
import com.twittelonr.wtf.scalding.jobs.common.SchelondulelondelonxeloncutionApp
import java.util.TimelonZonelon
import scala.util.Random

/**
 * This filelon implelonmelonnts thelon job for computing uselonrs' intelonrelonstelondIn velonctor from thelon producelonrelonmbelonddings data selont.
 *
 * It relonads thelon UselonrUselonrNormalizelondGraphScalaDataselont to gelont uselonr-uselonr follow + fav graph, and thelonn
 * baselond on thelon producelonrelonmbelondding clustelonrs of elonach followelond/favelond uselonr, welon calculatelon how much a uselonr is
 * intelonrelonstelondIn a clustelonr. To computelon thelon elonngagelonmelonnt and delontelonrminelon thelon clustelonrs for thelon uselonr, welon relonuselon
 * thelon functions delonfinelond in IntelonrelonstelondInKnownFor.
 *
 * Using producelonrelonmbelonddings instelonad of knownFor to obtain intelonrelonstelondIn increlonaselons thelon covelonragelon (elonspeloncially
 * for melondium and light uselonrs) and also thelon delonnsity of thelon clustelonr elonmbelonddings for thelon uselonr.
 */
/**
 * Adhoc job to gelonnelonratelon thelon intelonrelonstelondIn from producelonr elonmbelonddings for thelon modelonl velonrsion 20M145KUpdatelond
 *
 scalding relonmotelon run \
  --targelont src/scala/com/twittelonr/simclustelonrs_v2/scalding:intelonrelonstelond_in_from_producelonr_elonmbelonddings \
  --main-class com.twittelonr.simclustelonrs_v2.scalding.IntelonrelonstelondInFromProducelonrelonmbelonddingsAdhocApp \
  --uselonr cassowary --clustelonr bluelonbird-qus1 \
  --kelonytab /var/lib/tss/kelonys/fluffy/kelonytabs/clielonnt/cassowary.kelonytab \
  --principal selonrvicelon_acoount@TWITTelonR.BIZ \
  -- \
  --outputDir /gcs/uselonr/cassowary/adhoc/intelonrelonstelond_in_from_prod_elonmbelonddings/ \
  --datelon 2020-08-25 --typelondTsv truelon
 */
objelonct IntelonrelonstelondInFromProducelonrelonmbelonddingsAdhocApp elonxtelonnds AdhocelonxeloncutionApp {
  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    val outputDir = args("outputDir")
    val inputGraph = args.optional("graphInputDir") match {
      caselon Somelon(inputDir) => TypelondPipelon.from(UselonrAndNelonighborsFixelondPathSourcelon(inputDir))
      caselon Nonelon =>
        DAL
          .relonadMostReloncelonntSnapshotNoOldelonrThan(UselonrUselonrNormalizelondGraphScalaDataselont, Days(30))
          .toTypelondPipelon
    }
    val socialProofThrelonshold = args.int("socialProofThrelonshold", 2)
    val maxClustelonrsPelonrUselonrFinalRelonsult = args.int("maxIntelonrelonstelondInClustelonrsPelonrUselonr", 50)
    val maxClustelonrsFromProducelonr = args.int("maxClustelonrsPelonrProducelonr", 25)
    val typelondTsvTag = args.boolelonan("typelondTsv")

    val elonmbelonddingTypelon =
      elonmbelonddingTypelon.ProducelonrFavBaselondSelonmanticCorelonelonntity
    val modelonlVelonrsion = ModelonlVelonrsions.Modelonl20M145KUpdatelond
    val producelonrelonmbelonddings = ProducelonrelonmbelonddingSourcelons
      .producelonrelonmbelonddingSourcelonLelongacy(elonmbelonddingTypelon, ModelonlVelonrsions.toModelonlVelonrsion(modelonlVelonrsion))(
        datelonRangelon.elonmbiggelonn(Days(7)))

    import IntelonrelonstelondInFromProducelonrelonmbelonddingsBatchApp._

    val numProducelonrMappings = Stat("num_producelonr_elonmbelonddings_total")
    val numProducelonrsWithLargelonClustelonrMappings = Stat(
      "num_producelonrs_with_morelon_clustelonrs_than_threlonshold")
    val numProducelonrsWithSmallClustelonrMappings = Stat(
      "num_producelonrs_with_clustelonrs_lelonss_than_threlonshold")
    val totalClustelonrsCovelonragelonProducelonrelonmbelonddings = Stat("num_clustelonrs_total_producelonr_elonmbelonddings")

    val producelonrelonmbelonddingsWithScorelon = producelonrelonmbelonddings.map {
      caselon (uselonrId: Long, topSimClustelonrs: TopSimClustelonrsWithScorelon) =>
        (
          uselonrId,
          topSimClustelonrs.topClustelonrs.toArray
            .map {
              caselon (simClustelonr: SimClustelonrWithScorelon) =>
                (simClustelonr.clustelonrId, simClustelonr.scorelon.toFloat)
            }
        )
    }
    val producelonrelonmbelonddingsPrunelond = producelonrelonmbelonddingsWithScorelon.map {
      caselon (producelonrId, clustelonrArray) =>
        numProducelonrMappings.inc()
        val clustelonrSizelon = clustelonrArray.sizelon
        totalClustelonrsCovelonragelonProducelonrelonmbelonddings.incBy(clustelonrSizelon)
        val prunelondList = if (clustelonrSizelon > maxClustelonrsFromProducelonr) {
          numProducelonrsWithLargelonClustelonrMappings.inc()
          clustelonrArray
            .sortBy {
              caselon (_, knownForScorelon) => -knownForScorelon
            }.takelon(maxClustelonrsFromProducelonr)
        } elonlselon {
          numProducelonrsWithSmallClustelonrMappings.inc()
          clustelonrArray
        }
        (producelonrId, prunelondList)
    }

    val relonsult = IntelonrelonstelondInFromKnownFor
      .run(
        inputGraph,
        producelonrelonmbelonddingsPrunelond,
        socialProofThrelonshold,
        maxClustelonrsPelonrUselonrFinalRelonsult,
        modelonlVelonrsion
      )

    val relonsultWithoutSocial = gelontIntelonrelonstelondInDiscardSocial(relonsult)

    if (typelondTsvTag) {
      Util.printCountelonrs(
        relonsultWithoutSocial
          .map {
            caselon (uselonrId: Long, clustelonrs: ClustelonrsUselonrIsIntelonrelonstelondIn) =>
              (
                uselonrId,
                clustelonrs.clustelonrIdToScorelons.kelonys.toString()
              )
          }
          .writelonelonxeloncution(
            TypelondTsv(outputDir)
          )
      )
    } elonlselon {
      Util.printCountelonrs(
        relonsultWithoutSocial
          .writelonelonxeloncution(
            AdhocKelonyValSourcelons.intelonrelonstelondInSourcelon(outputDir)
          )
      )
    }
  }
}

/**
 * Production job for computing intelonrelonstelondIn data selont from thelon producelonr elonmbelonddings for thelon modelonl velonrsion 20M145KUpdatelond.
 * It writelons thelon data selont in KelonyVal format to producelon a MH DAL data selont.
 *
 * To delonploy thelon job:
 *
 * capelonsospy-v2 updatelon --build_locally --start_cron
 * --start_cron intelonrelonstelond_in_from_producelonr_elonmbelonddings
 * src/scala/com/twittelonr/simclustelonrs_v2/capelonsos_config/atla_proc3.yaml
 */
objelonct IntelonrelonstelondInFromProducelonrelonmbelonddingsBatchApp elonxtelonnds SchelondulelondelonxeloncutionApp {
  ovelonrridelon val firstTimelon: RichDatelon = RichDatelon("2019-11-01")

  ovelonrridelon val batchIncrelonmelonnt: Duration = Days(7)

  delonf gelontPrunelondelonmbelonddings(
    producelonrelonmbelonddings: TypelondPipelon[(Long, TopSimClustelonrsWithScorelon)],
    maxClustelonrsFromProducelonr: Int
  ): TypelondPipelon[(Long, TopSimClustelonrsWithScorelon)] = {
    producelonrelonmbelonddings.map {
      caselon (producelonrId, producelonrClustelonrs) =>
        val prunelondProducelonrClustelonrs =
          producelonrClustelonrs.topClustelonrs
            .sortBy {
              caselon simClustelonr => -simClustelonr.scorelon.toFloat
            }.takelon(maxClustelonrsFromProducelonr)
        (producelonrId, TopSimClustelonrsWithScorelon(prunelondProducelonrClustelonrs, producelonrClustelonrs.modelonlVelonrsion))
    }
  }

  delonf gelontIntelonrelonstelondInDiscardSocial(
    intelonrelonstelondInFromProducelonrsRelonsult: TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)]
  ): TypelondPipelon[(UselonrId, ClustelonrsUselonrIsIntelonrelonstelondIn)] = {
    intelonrelonstelondInFromProducelonrsRelonsult.map {
      caselon (srcId, fullClustelonrList) =>
        val fullClustelonrListWithoutSocial = fullClustelonrList.clustelonrIdToScorelons.map {
          caselon (clustelonrId, clustelonrDelontails) =>
            val clustelonrDelontailsWithoutSocial = UselonrToIntelonrelonstelondInClustelonrScorelons(
              followScorelon = clustelonrDelontails.followScorelon,
              followScorelonClustelonrNormalizelondOnly = clustelonrDelontails.followScorelonClustelonrNormalizelondOnly,
              followScorelonProducelonrNormalizelondOnly = clustelonrDelontails.followScorelonProducelonrNormalizelondOnly,
              followScorelonClustelonrAndProducelonrNormalizelond =
                clustelonrDelontails.followScorelonClustelonrAndProducelonrNormalizelond,
              favScorelon = clustelonrDelontails.favScorelon,
              favScorelonClustelonrNormalizelondOnly = clustelonrDelontails.favScorelonClustelonrNormalizelondOnly,
              favScorelonProducelonrNormalizelondOnly = clustelonrDelontails.favScorelonProducelonrNormalizelondOnly,
              favScorelonClustelonrAndProducelonrNormalizelond =
                clustelonrDelontails.favScorelonClustelonrAndProducelonrNormalizelond,
              // Social proof is currelonntly not beloning uselond anywhelonrelon elonlselon, helonncelon beloning discardelond to relonducelon spacelon for this dataselont
              uselonrsBeloningFollowelond = Nonelon,
              uselonrsThatWelonrelonFavelond = Nonelon,
              numUselonrsIntelonrelonstelondInThisClustelonrUppelonrBound =
                clustelonrDelontails.numUselonrsIntelonrelonstelondInThisClustelonrUppelonrBound,
              logFavScorelon = clustelonrDelontails.logFavScorelon,
              logFavScorelonClustelonrNormalizelondOnly = clustelonrDelontails.logFavScorelonClustelonrNormalizelondOnly,
              // Counts of thelon social proof arelon maintainelond
              numUselonrsBeloningFollowelond = Somelon(clustelonrDelontails.uselonrsBeloningFollowelond.gelontOrelonlselon(Nil).sizelon),
              numUselonrsThatWelonrelonFavelond = Somelon(clustelonrDelontails.uselonrsThatWelonrelonFavelond.gelontOrelonlselon(Nil).sizelon)
            )
            (clustelonrId, clustelonrDelontailsWithoutSocial)
        }
        (
          srcId,
          ClustelonrsUselonrIsIntelonrelonstelondIn(
            fullClustelonrList.knownForModelonlVelonrsion,
            fullClustelonrListWithoutSocial))
    }
  }

  ovelonrridelon delonf runOnDatelonRangelon(
    args: Args
  )(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): elonxeloncution[Unit] = {

    //Input args for thelon run
    val socialProofThrelonshold = args.int("socialProofThrelonshold", 2)
    val maxClustelonrsFromProducelonr = args.int("maxClustelonrsPelonrProducelonr", 25)
    val maxClustelonrsPelonrUselonrFinalRelonsult = args.int("maxIntelonrelonstelondInClustelonrsPelonrUselonr", 50)

    //Path variablelons
    val modelonlVelonrsionUpdatelond = ModelonlVelonrsions.toModelonlVelonrsion(ModelonlVelonrsions.Modelonl20M145KUpdatelond)
    val rootPath: String = s"/uselonr/cassowary/manhattan_selonquelonncelon_filelons"
    val intelonrelonstelondInFromProducelonrsPath =
      rootPath + "/intelonrelonstelond_in_from_producelonr_elonmbelonddings/" + modelonlVelonrsionUpdatelond

    //Input adjacelonncy list and producelonr elonmbelonddings
    val uselonrUselonrNormalGraph =
      DataSourcelons.uselonrUselonrNormalizelondGraphSourcelon(datelonRangelon.prelonpelonnd(Days(7))).forcelonToDisk
    val outputKVDataselont: KelonyValDALDataselont[KelonyVal[Long, ClustelonrsUselonrIsIntelonrelonstelondIn]] =
      SimclustelonrsV2IntelonrelonstelondInFromProducelonrelonmbelonddings20M145KUpdatelondScalaDataselont
    val producelonrelonmbelonddings = ProducelonrelonmbelonddingSourcelons
      .producelonrelonmbelonddingSourcelonLelongacy(
        elonmbelonddingTypelon.ProducelonrFavBaselondSelonmanticCorelonelonntity,
        modelonlVelonrsionUpdatelond)(datelonRangelon.elonmbiggelonn(Days(7)))

    val producelonrelonmbelonddingsPrunelond = gelontPrunelondelonmbelonddings(producelonrelonmbelonddings, maxClustelonrsFromProducelonr)
    val producelonrelonmbelonddingsWithScorelon = producelonrelonmbelonddingsPrunelond.map {
      caselon (uselonrId: Long, topSimClustelonrs: TopSimClustelonrsWithScorelon) =>
        (
          uselonrId,
          topSimClustelonrs.topClustelonrs.toArray
            .map {
              caselon (simClustelonr: SimClustelonrWithScorelon) =>
                (simClustelonr.clustelonrId, simClustelonr.scorelon.toFloat)
            }
        )
    }

    val intelonrelonstelondInFromProducelonrsRelonsult =
      IntelonrelonstelondInFromKnownFor.run(
        uselonrUselonrNormalGraph,
        producelonrelonmbelonddingsWithScorelon,
        socialProofThrelonshold,
        maxClustelonrsPelonrUselonrFinalRelonsult,
        modelonlVelonrsionUpdatelond.toString
      )

    val intelonrelonstelondInFromProducelonrsWithoutSocial =
      gelontIntelonrelonstelondInDiscardSocial(intelonrelonstelondInFromProducelonrsRelonsult)

    val writelonKelonyValRelonsultelonxelonc = intelonrelonstelondInFromProducelonrsWithoutSocial
      .map { caselon (uselonrId, clustelonrs) => KelonyVal(uselonrId, clustelonrs) }
      .writelonDALVelonrsionelondKelonyValelonxeloncution(
        outputKVDataselont,
        D.Suffix(intelonrelonstelondInFromProducelonrsPath)
      )
    writelonKelonyValRelonsultelonxelonc
  }

}
