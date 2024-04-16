package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap . RecordOneToOneAdapter
 mport scala.ut l.Random

/**
 *  lper funct ons for FeatureStoreS ce operat ons  n FRS are ava lable  re.
 */
object Ut ls {

  pr vate val EarlyExp rat on = 0.2

  pr vate[common] def adaptAdd  onalFeaturesToDataRecord(
    record: DataRecord,
    adapterStats: StatsRece ver,
    featureAdapters: Seq[ RecordOneToOneAdapter[DataRecord]]
  ): DataRecord = {
    featureAdapters.foldR ght(record) { (adapter, record) =>
      adapterStats.counter(adapter.getClass.getS mpleNa ). ncr()
      adapter.adaptToDataRecord(record)
    }
  }

  // To avo d a cac  stampede. See https://en.w k ped a.org/w k /Cac _stampede
  pr vate[common] def random zedTTL(ttl: Long): Long = {
    (ttl - ttl * EarlyExp rat on * Random.nextDouble()).toLong
  }
}
