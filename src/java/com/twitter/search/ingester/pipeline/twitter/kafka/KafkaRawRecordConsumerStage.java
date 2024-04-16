package com.tw ter.search. ngester.p pel ne.tw ter.kafka;

 mport org.apac .commons.p pel ne.val dat on.Consu dTypes;
 mport org.apac .commons.p pel ne.val dat on.ProducedTypes;
 mport org.apac .kafka.common.ser al zat on.Deser al zer;

 mport com.tw ter.f natra.kafka.serde. nternal.BaseDeser al zer;
 mport com.tw ter.search. ngester.model.KafkaRawRecord;
 mport com.tw ter.ut l.T  ;

/**
 * Kafka consu r stage that em s t  b nary payload wrapped  n {@code ByteArray}.
 */
@Consu dTypes(Str ng.class)
@ProducedTypes(KafkaRawRecord.class)
publ c class KafkaRawRecordConsu rStage extends KafkaConsu rStage<KafkaRawRecord> {
  publ c KafkaRawRecordConsu rStage() {
    super(getDeser al zer());
  }

  pr vate stat c Deser al zer<KafkaRawRecord> getDeser al zer() {
    return new BaseDeser al zer<KafkaRawRecord>() {
      @Overr de
      publ c KafkaRawRecord deser al ze(Str ng top c, byte[] data) {
        return new KafkaRawRecord(data, T  .now(). nM ll s());
      }
    };
  }

  publ c KafkaRawRecordConsu rStage(Str ng kafkaCl ent d, Str ng kafkaTop cNa ,
                                     Str ng kafkaConsu rGroup d, Str ng kafkaClusterPath,
                                     Str ng dec derKey) {
    super(kafkaCl ent d, kafkaTop cNa , kafkaConsu rGroup d, kafkaClusterPath, dec derKey,
        getDeser al zer());
  }
}
