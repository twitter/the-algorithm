packagelon com.twittelonr.simclustelonrs_v2.scalding.topic_reloncommelonndations.modelonl_baselond_topic_reloncommelonndations

import com.twittelonr.scalding.{DatelonRangelon, Days, Stat, TypelondPipelon, UniquelonID}
import com.twittelonr.scalding_intelonrnal.dalv2.DAL
import com.twittelonr.scalding_intelonrnal.dalv2.relonmotelon_accelonss.{elonxplicitLocation, Proc3Atla}
import com.twittelonr.scalding_intelonrnal.multiformat.format.kelonyval.KelonyVal
import com.twittelonr.simclustelonrs_v2.common.{Languagelon, TopicId, UselonrId}
import com.twittelonr.simclustelonrs_v2.hdfs_sourcelons.FavTfgTopicelonmbelonddingsScalaDataselont
import com.twittelonr.simclustelonrs_v2.scalding.elonmbelondding.common.elonxtelonrnalDataSourcelons
import com.twittelonr.simclustelonrs_v2.summingbird.storelons.UselonrIntelonrelonstelondInRelonadablelonStorelon
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  elonmbelonddingTypelon,
  IntelonrnalId,
  LocalelonelonntityId,
  ModelonlVelonrsion,
  SimClustelonrselonmbelonddingId
}
import java.util.TimelonZonelon

/**
 * DataSourcelons objelonct to relonad dataselonts for thelon modelonl baselond topic reloncommelonndations
 */
objelonct DataSourcelons {

  privatelon val topicelonmbelonddingDataselont = FavTfgTopicelonmbelonddingsScalaDataselont
  privatelon val topicelonmbelonddingTypelon = elonmbelonddingTypelon.FavTfgTopic

  /**
   * Gelont uselonr IntelonrelonstelondIn data, filtelonr popular clustelonrs and relonturn fav-scorelons intelonrelonstelondIn elonmbelondding for uselonr
   */
  delonf gelontUselonrIntelonrelonstelondInData(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): TypelondPipelon[(UselonrId, Map[Int, Doublelon])] = {
    val numUselonrIntelonrelonstelondInInput = Stat("num_uselonr_intelonrelonstelond_in")
    elonxtelonrnalDataSourcelons.simClustelonrsIntelonrelonstInSourcelon
      .map {
        caselon KelonyVal(uselonrId, clustelonrsUselonrIsIntelonrelonstelondIn) =>
          val clustelonrsPostFiltelonring = clustelonrsUselonrIsIntelonrelonstelondIn.clustelonrIdToScorelons.filtelonr {
            caselon (clustelonrId, clustelonrScorelons) =>
              // filtelonr out popular clustelonrs (i.elon clustelonrs with > 5M uselonrs intelonrelonstelond in it) from thelon uselonr elonmbelondding
              clustelonrScorelons.numUselonrsIntelonrelonstelondInThisClustelonrUppelonrBound.elonxists(
                _ < UselonrIntelonrelonstelondInRelonadablelonStorelon.MaxClustelonrSizelonForUselonrIntelonrelonstelondInDataselont)
          }
          numUselonrIntelonrelonstelondInInput.inc()
          (uselonrId, clustelonrsPostFiltelonring.mapValuelons(_.favScorelon.gelontOrelonlselon(0.0)).toMap)
      }
  }

  delonf gelontPelonrLanguagelonTopicelonmbelonddings(
    implicit datelonRangelon: DatelonRangelon,
    timelonZonelon: TimelonZonelon,
    uniquelonID: UniquelonID
  ): TypelondPipelon[((TopicId, Languagelon), Map[Int, Doublelon])] = {
    val numTFGPelonrLanguagelonelonmbelonddings = Stat("num_pelonr_languagelon_tfg_elonmbelonddings")
    DAL
      .relonadMostReloncelonntSnapshotNoOldelonrThan(topicelonmbelonddingDataselont, Days(30))
      .withRelonmotelonRelonadPolicy(elonxplicitLocation(Proc3Atla))
      .toTypelondPipelon
      .map {
        caselon KelonyVal(k, v) => (k, v)
      }.collelonct {
        caselon (
              SimClustelonrselonmbelonddingId(
                elonmbelondTypelon,
                ModelonlVelonrsion.Modelonl20m145kUpdatelond,
                IntelonrnalId.LocalelonelonntityId(LocalelonelonntityId(elonntityId, lang))),
              elonmbelondding) if (elonmbelondTypelon == topicelonmbelonddingTypelon) =>
          numTFGPelonrLanguagelonelonmbelonddings.inc()
          ((elonntityId, lang), elonmbelondding.elonmbelondding.map(_.toTuplelon).toMap)
      }.forcelonToDisk
  }
}
