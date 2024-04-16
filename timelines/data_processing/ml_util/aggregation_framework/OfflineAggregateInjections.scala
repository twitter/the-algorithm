package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.dal.personal_data.thr ftscala.PersonalDataType
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.Batc d
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.JavaCompactThr ft
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.gener c nject on
 mport com.tw ter.summ ngb rd.batch.Batch D
 mport scala.collect on.JavaConverters._

object Offl neAggregate nject ons {
  val offl neDataRecordAggregate nject on: KeyVal nject on[Aggregat onKey, (Batch D, DataRecord)] =
    KeyVal nject on(
      gener c nject on(Aggregat onKey nject on),
      Batc d(JavaCompactThr ft[DataRecord])
    )

  pr vate[aggregat on_fra work] def getPdts[T](
    aggregateGroups:  erable[T],
    featureExtractor: T =>  erable[Feature[_]]
  ): Opt on[Set[PersonalDataType]] = {
    val pdts: Set[PersonalDataType] = for {
      group <- aggregateGroups.toSet[T]
      feature <- featureExtractor(group)
      pdtSet <- feature.getPersonalDataTypes.asSet().asScala
      javaPdt <- pdtSet.asScala
      scalaPdt <- PersonalDataType.get(javaPdt.getValue)
    } y eld {
      scalaPdt
    }
     f (pdts.nonEmpty) So (pdts) else None
  }

  def get nject on(
    aggregateGroups: Set[TypedAggregateGroup[_]]
  ): KeyVal nject on[Aggregat onKey, (Batch D, DataRecord)] = {
    val keyPdts = getPdts[TypedAggregateGroup[_]](aggregateGroups, _.allOutputKeys)
    val valuePdts = getPdts[TypedAggregateGroup[_]](aggregateGroups, _.allOutputFeatures)
    KeyVal nject on(
      gener c nject on(Aggregat onKey nject on, keyPdts),
      gener c nject on(Batc d(JavaCompactThr ft[DataRecord]), valuePdts)
    )
  }
}
