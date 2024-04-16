package com.tw ter.ho _m xer.product.for_ .s de_effect

 mport com.tw ter.ho _m xer.model.Ho Features. sReadFromCac Feature
 mport com.tw ter.ho _m xer.model.Ho Features.Pred ct onRequest dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Served dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ServedRequest dFeature
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.EnableServedCand dateKafkaPubl sh ngParam
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.product_m xer.component_l brary.s de_effect.KafkaPubl sh ngS deEffect
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.ml.cont_tra n.common.doma n.non_scald ng.DataRecordLogg ngRelatedFeatures.tlmServedKeysFeatureContext
 mport com.tw ter.t  l nes.ml.kafka.serde.ServedCand dateKeySerde
 mport com.tw ter.t  l nes.ml.kafka.serde.TBaseSerde
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.t  l nes.served_cand dates_logg ng.{thr ftscala => sc}
 mport com.tw ter.t  l nes.suggests.common.poly_data_record.{thr ftjava => pldr}
 mport com.tw ter.ut l.T  
 mport org.apac .kafka.cl ents.producer.ProducerRecord
 mport org.apac .kafka.common.ser al zat on.Ser al zer

/**
 * P pel ne s de-effect that publ s s cand date keys to a Kafka top c.
 */
class ServedCand dateKeysKafkaS deEffect(
  top c: Str ng,
  s ce dent f ers: Set[Cand dateP pel ne dent f er])
    extends KafkaPubl sh ngS deEffect[
      sc.ServedCand dateKey,
      pldr.PolyDataRecord,
      P pel neQuery,
      T  l ne
    ]
    w h P pel neResultS deEffect.Cond  onally[P pel neQuery, T  l ne] {

   mport ServedCand dateKafkaS deEffect._

  overr de val  dent f er: S deEffect dent f er = S deEffect dent f er("ServedCand dateKeys")

  overr de def only f(
    query: P pel neQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: T  l ne
  ): Boolean = query.params.getBoolean(EnableServedCand dateKafkaPubl sh ngParam)

  overr de val bootstrapServer: Str ng = "/s/kafka/t  l ne:kafka-tls"

  overr de val keySerde: Ser al zer[sc.ServedCand dateKey] = ServedCand dateKeySerde.ser al zer()

  overr de val valueSerde: Ser al zer[pldr.PolyDataRecord] =
    TBaseSerde.Thr ft[pldr.PolyDataRecord]().ser al zer

  overr de val cl ent d: Str ng = "ho _m xer_served_cand date_keys_producer"

  overr de def bu ldRecords(
    query: P pel neQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: T  l ne
  ): Seq[ProducerRecord[sc.ServedCand dateKey, pldr.PolyDataRecord]] = {
    val servedT  stamp = T  .now. nM ll seconds
    val servedRequest dOpt =
      query.features.getOrElse(FeatureMap.empty).getOrElse(ServedRequest dFeature, None)

    extractCand dates(query, selectedCand dates, s ce dent f ers).collect {
      // Only publ sh non-cac d t ets to t  ServedCand dateKey top c
      case cand date  f !cand date.features.getOrElse( sReadFromCac Feature, false) =>
        val key = sc.ServedCand dateKey(
          t et d = cand date.cand date dLong,
          v e r d = query.getRequ redUser d,
          served d = -1L
        )

        val record = SR chDataRecord(new DataRecord, tlmServedKeysFeatureContext)
        record.setFeatureValueFromOpt on(
          T  l nesSharedFeatures.PRED CT ON_REQUEST_ D,
          cand date.features.getOrElse(Pred ct onRequest dFeature, None)
        )
        record
          .setFeatureValueFromOpt on(T  l nesSharedFeatures.SERVED_REQUEST_ D, servedRequest dOpt)
        record.setFeatureValueFromOpt on(
          T  l nesSharedFeatures.SERVED_ D,
          cand date.features.getOrElse(Served dFeature, None)
        )
        record.setFeatureValueFromOpt on(
          T  l nesSharedFeatures. NJECT ON_TYPE,
          record.getFeatureValueOpt(T  l nesSharedFeatures. NJECT ON_TYPE))
        record.setFeatureValue(
          T  l nesSharedFeatures.SERVED_T MESTAMP,
          servedT  stamp
        )
        record.record.dropUnknownFeatures()

        new ProducerRecord(top c, key, pldr.PolyDataRecord.dataRecord(record.getRecord))
    }
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(98.5)
  )
}
