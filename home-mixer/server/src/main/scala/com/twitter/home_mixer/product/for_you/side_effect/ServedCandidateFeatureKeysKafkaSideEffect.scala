package com.tw ter.ho _m xer.product.for_ .s de_effect

 mport com.tw ter.ho _m xer.model.Ho Features.Cand dateS ce dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sReadFromCac Feature
 mport com.tw ter.ho _m xer.model.Ho Features.Pred ct onRequest dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Served dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ServedRequest dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.EnableServedCand dateKafkaPubl sh ngParam
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.s de_effect.KafkaPubl sh ngS deEffect
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.ml.cont_tra n.common.doma n.non_scald ng.ServedCand dateFeatureKeysAdapter
 mport com.tw ter.t  l nes.ml.cont_tra n.common.doma n.non_scald ng.ServedCand dateFeatureKeysF elds
 mport com.tw ter.t  l nes.ml.kafka.serde.Cand dateFeatureKeySerde
 mport com.tw ter.t  l nes.ml.kafka.serde.TBaseSerde
 mport com.tw ter.t  l nes.served_cand dates_logg ng.{thr ftscala => sc}
 mport com.tw ter.t  l nes.suggests.common.poly_data_record.{thr ftjava => pldr}
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => tls}
 mport org.apac .kafka.cl ents.producer.ProducerRecord
 mport org.apac .kafka.common.ser al zat on.Ser al zer
 mport scala.collect on.JavaConverters._

/**
 * P pel ne s de-effect that publ s s cand date keys to a Kafka top c.
 */
class ServedCand dateFeatureKeysKafkaS deEffect(
  top c: Str ng,
  s ce dent f ers: Set[ dent f er.Cand dateP pel ne dent f er])
    extends KafkaPubl sh ngS deEffect[
      sc.Cand dateFeatureKey,
      pldr.PolyDataRecord,
      P pel neQuery,
      T  l ne
    ]
    w h P pel neResultS deEffect.Cond  onally[P pel neQuery, T  l ne] {

   mport ServedCand dateKafkaS deEffect._

  overr de val  dent f er: S deEffect dent f er = S deEffect dent f er("ServedCand dateFeatureKeys")

  overr de def only f(
    query: P pel neQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: T  l ne
  ): Boolean = query.params.getBoolean(EnableServedCand dateKafkaPubl sh ngParam)

  overr de val bootstrapServer: Str ng = "/s/kafka/t  l ne:kafka-tls"

  overr de val keySerde: Ser al zer[sc.Cand dateFeatureKey] =
    Cand dateFeatureKeySerde().ser al zer()

  overr de val valueSerde: Ser al zer[pldr.PolyDataRecord] =
    TBaseSerde.Thr ft[pldr.PolyDataRecord]().ser al zer

  overr de val cl ent d: Str ng = "ho _m xer_served_cand date_feature_keys_producer"

  overr de def bu ldRecords(
    query: P pel neQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: T  l ne
  ): Seq[ProducerRecord[sc.Cand dateFeatureKey, pldr.PolyDataRecord]] = {
    val servedRequest dOpt =
      query.features.getOrElse(FeatureMap.empty).getOrElse(ServedRequest dFeature, None)

    extractCand dates(query, selectedCand dates, s ce dent f ers).map { cand date =>
      val  sReadFromCac  = cand date.features.getOrElse( sReadFromCac Feature, false)
      val served d = cand date.features.get(Served dFeature).get

      val key = sc.Cand dateFeatureKey(
        t et d = cand date.cand date dLong,
        v e r d = query.getRequ redUser d,
        served d = served d)

      val record =
        ServedCand dateFeatureKeysAdapter
          .adaptToDataRecords(
            ServedCand dateFeatureKeysF elds(
              cand dateT etS ce d = cand date.features
                .getOrElse(Cand dateS ce dFeature, None).map(_.value.toLong).getOrElse(2L),
              pred ct onRequest d =
                cand date.features.getOrElse(Pred ct onRequest dFeature, None).get,
              servedRequest dOpt =  f ( sReadFromCac ) servedRequest dOpt else None,
              served d = served d,
               nject onModuleNa  = cand date.getClass.getS mpleNa ,
              v e rFollowsOr g nalAuthor =
                So (cand date.features.getOrElse( nNetworkFeature, true)),
              suggestType = cand date.features
                .getOrElse(SuggestTypeFeature, None).getOrElse(tls.SuggestType.RankedOrgan cT et),
              f nalPos  on ndex = So (cand date.s cePos  on),
               sReadFromCac  =  sReadFromCac 
            )).asScala. ad

      new ProducerRecord(top c, key, pldr.PolyDataRecord.dataRecord(record))
    }
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(98.5)
  )
}
