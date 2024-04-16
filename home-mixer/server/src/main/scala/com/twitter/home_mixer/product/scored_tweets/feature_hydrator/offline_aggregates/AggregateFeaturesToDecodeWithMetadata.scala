package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.t  l nem xer. nject on.repos ory.uss.Vers onedAggregateFeaturesDecoder
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.t  l nes.aggregate_ nteract ons.thr ftjava.UserAggregate nteract ons
 mport com.tw ter.t  l nes.aggregate_ nteract ons.v17.thr ftjava.{
  UserAggregate nteract ons => V17UserAggregate nteract ons
}
 mport com.tw ter.t  l nes.aggregate_ nteract ons.v1.thr ftjava.{
  UserAggregate nteract ons => V1UserAggregate nteract ons
}
 mport com.tw ter.t  l nes.suggests.common.dense_data_record.thr ftjava.DenseCompactDataRecord
 mport com.tw ter.t  l nes.suggests.common.dense_data_record.thr ftscala.DenseFeature tadata
 mport java.lang.{Long => JLong}
 mport java.ut l.Collect ons
 mport java.ut l.{Map => JMap}

pr vate[offl ne_aggregates] case class AggregateFeaturesToDecodeW h tadata(
   tadataOpt: Opt on[DenseFeature tadata],
  aggregates: UserAggregate nteract ons) {
  def toDataRecord(dr: DenseCompactDataRecord): DataRecord =
    Vers onedAggregateFeaturesDecoder.fromJDenseCompact(
       tadataOpt,
      dr.vers on d,
      NullStatsRece ver,
      s"V${dr.vers on d}"
    )(dr)

  def userAggregatesOpt: Opt on[DenseCompactDataRecord] = {
    aggregates.getSetF eld match {
      case UserAggregate nteract ons._F elds.V17 =>
        Opt on(aggregates.getV17.user_aggregates)
      case _ =>
        None
    }
  }

  def userAuthorAggregates = extract(_.user_author_aggregates)
  def userEngagerAggregates = extract(_.user_engager_aggregates)
  def user nt onAggregates = extract(_.user_ nt on_aggregates)
  def userOr g nalAuthorAggregates = extract(_.user_or g nal_author_aggregates)
  def userRequestDowAggregates = extract(_.user_request_dow_aggregates)
  def userRequestH Aggregates = extract(_.user_request_h _aggregates)
  def rect etUserS mclustersT etAggregates = extract(_.rect et_user_s mclusters_t et_aggregates)
  def userTw terL stAggregates = extract(_.user_l st_aggregates)
  def userTop cAggregates = extract(_.user_top c_aggregates)
  def user nferredTop cAggregates = extract(_.user_ nferred_top c_aggregates)
  def user d aUnderstand ngAnnotat onAggregates = extract(
    _.user_ d a_understand ng_annotat on_aggregates)

  pr vate def extract[T](
    v17Fn: V17UserAggregate nteract ons => JMap[JLong, DenseCompactDataRecord]
  ): JMap[JLong, DenseCompactDataRecord] = {
    aggregates.getSetF eld match {
      case UserAggregate nteract ons._F elds.V17 =>
        v17Fn(aggregates.getV17)
      case _ =>
        Collect ons.emptyMap()
    }
  }
}

object AggregateFeaturesToDecodeW h tadata {
  val empty = new AggregateFeaturesToDecodeW h tadata(
    None,
    UserAggregate nteract ons.v1(new V1UserAggregate nteract ons()))
}
