package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Twh nUserEngage ntFeatureRepos ory
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.twh n_embedd ngs.Twh nUserEngage ntEmbedd ngsAdapter
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .{thr ftscala => ml}
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object Twh nUserEngage ntFeature
    extends DataRecord nAFeature[P pel neQuery]
    w h FeatureW hDefaultOnFa lure[P pel neQuery, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class Twh nUserEngage ntQueryFeatureHydrator @ nject() (
  @Na d(Twh nUserEngage ntFeatureRepos ory)
  cl ent: KeyValueRepos ory[Seq[Long], Long, ml.FloatTensor],
  statsRece ver: StatsRece ver)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Twh nUserEngage nt")

  overr de val features: Set[Feature[_, _]] = Set(Twh nUserEngage ntFeature)

  pr vate val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )
  pr vate val keyFoundCounter = scopedStatsRece ver.counter("key/found")
  pr vate val keyLossCounter = scopedStatsRece ver.counter("key/loss")
  pr vate val keyFa lureCounter = scopedStatsRece ver.counter("key/fa lure")

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val user d = query.getRequ redUser d
    St ch.callFuture(cl ent(Seq(user d))).map { results =>
      val embedd ng: Opt on[ml.FloatTensor] = results(user d) match {
        case Return(value) =>
           f (value.ex sts(_.floats.nonEmpty)) keyFoundCounter. ncr()
          else keyLossCounter. ncr()
          value
        case Throw(_) =>
          keyFa lureCounter. ncr()
          None
        case _ =>
          None
      }

      val dataRecord =
        Twh nUserEngage ntEmbedd ngsAdapter.adaptToDataRecords(embedd ng).asScala. ad

      FeatureMapBu lder()
        .add(Twh nUserEngage ntFeature, dataRecord)
        .bu ld()
    }
  }

}
