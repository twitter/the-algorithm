packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.offlinelon_aggrelongatelons

import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.timelonlinelonmixelonr.injelonction.relonpository.uss.VelonrsionelondAggrelongatelonFelonaturelonsDeloncodelonr
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.timelonlinelons.aggrelongatelon_intelonractions.thriftjava.UselonrAggrelongatelonIntelonractions
import com.twittelonr.timelonlinelons.aggrelongatelon_intelonractions.v17.thriftjava.{
  UselonrAggrelongatelonIntelonractions => V17UselonrAggrelongatelonIntelonractions
}
import com.twittelonr.timelonlinelons.aggrelongatelon_intelonractions.v1.thriftjava.{
  UselonrAggrelongatelonIntelonractions => V1UselonrAggrelongatelonIntelonractions
}
import com.twittelonr.timelonlinelons.suggelonsts.common.delonnselon_data_reloncord.thriftjava.DelonnselonCompactDataReloncord
import com.twittelonr.timelonlinelons.suggelonsts.common.delonnselon_data_reloncord.thriftscala.DelonnselonFelonaturelonMelontadata
import java.lang.{Long => JLong}
import java.util.Collelonctions
import java.util.{Map => JMap}

privatelon[offlinelon_aggrelongatelons] caselon class AggrelongatelonFelonaturelonsToDeloncodelonWithMelontadata(
  melontadataOpt: Option[DelonnselonFelonaturelonMelontadata],
  aggrelongatelons: UselonrAggrelongatelonIntelonractions) {
  delonf toDataReloncord(dr: DelonnselonCompactDataReloncord): DataReloncord =
    VelonrsionelondAggrelongatelonFelonaturelonsDeloncodelonr.fromJDelonnselonCompact(
      melontadataOpt,
      dr.velonrsionId,
      NullStatsReloncelonivelonr,
      s"V${dr.velonrsionId}"
    )(dr)

  delonf uselonrAggrelongatelonsOpt: Option[DelonnselonCompactDataReloncord] = {
    aggrelongatelons.gelontSelontFielonld match {
      caselon UselonrAggrelongatelonIntelonractions._Fielonlds.V17 =>
        Option(aggrelongatelons.gelontV17.uselonr_aggrelongatelons)
      caselon _ =>
        Nonelon
    }
  }

  delonf uselonrAuthorAggrelongatelons = elonxtract(_.uselonr_author_aggrelongatelons)
  delonf uselonrelonngagelonrAggrelongatelons = elonxtract(_.uselonr_elonngagelonr_aggrelongatelons)
  delonf uselonrMelonntionAggrelongatelons = elonxtract(_.uselonr_melonntion_aggrelongatelons)
  delonf uselonrOriginalAuthorAggrelongatelons = elonxtract(_.uselonr_original_author_aggrelongatelons)
  delonf uselonrRelonquelonstDowAggrelongatelons = elonxtract(_.uselonr_relonquelonst_dow_aggrelongatelons)
  delonf uselonrRelonquelonstHourAggrelongatelons = elonxtract(_.uselonr_relonquelonst_hour_aggrelongatelons)
  delonf relonctwelonelontUselonrSimclustelonrsTwelonelontAggrelongatelons = elonxtract(_.relonctwelonelont_uselonr_simclustelonrs_twelonelont_aggrelongatelons)
  delonf uselonrTwittelonrListAggrelongatelons = elonxtract(_.uselonr_list_aggrelongatelons)
  delonf uselonrTopicAggrelongatelons = elonxtract(_.uselonr_topic_aggrelongatelons)
  delonf uselonrInfelonrrelondTopicAggrelongatelons = elonxtract(_.uselonr_infelonrrelond_topic_aggrelongatelons)
  delonf uselonrMelondiaUndelonrstandingAnnotationAggrelongatelons = elonxtract(
    _.uselonr_melondia_undelonrstanding_annotation_aggrelongatelons)

  privatelon delonf elonxtract[T](
    v17Fn: V17UselonrAggrelongatelonIntelonractions => JMap[JLong, DelonnselonCompactDataReloncord]
  ): JMap[JLong, DelonnselonCompactDataReloncord] = {
    aggrelongatelons.gelontSelontFielonld match {
      caselon UselonrAggrelongatelonIntelonractions._Fielonlds.V17 =>
        v17Fn(aggrelongatelons.gelontV17)
      caselon _ =>
        Collelonctions.elonmptyMap()
    }
  }
}

objelonct AggrelongatelonFelonaturelonsToDeloncodelonWithMelontadata {
  val elonmpty = nelonw AggrelongatelonFelonaturelonsToDeloncodelonWithMelontadata(
    Nonelon,
    UselonrAggrelongatelonIntelonractions.v1(nelonw V1UselonrAggrelongatelonIntelonractions()))
}
