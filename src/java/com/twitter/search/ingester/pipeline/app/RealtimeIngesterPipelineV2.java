package com.tw ter.search. ngester.p pel ne.app;
 mport java.ut l.L st;
 mport java.ut l.concurrent.CompletableFuture;
 mport java.ut l.concurrent.ExecutorServ ce;
 mport java.ut l.concurrent.SynchronousQueue;
 mport java.ut l.concurrent.ThreadPoolExecutor;
 mport java.ut l.concurrent.T  Un ;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search. ngester.model. ngesterT etEvent;
 mport com.tw ter.search. ngester.model.KafkaRawRecord;
 mport com.tw ter.search. ngester.p pel ne.tw ter.T etEventDeser al zerStage;
 mport com.tw ter.search. ngester.p pel ne.tw ter.kafka.KafkaConsu rStage;
 mport com.tw ter.search. ngester.p pel ne.tw ter.kafka.KafkaRawRecordConsu rStage;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neV2Creat onExcept on;
 mport com.tw ter.search. ngester.p pel ne.ut l.P pel neStageExcept on;

publ c class Realt   ngesterP pel neV2 {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Realt   ngesterP pel neV2.class);
  pr vate stat c f nal Str ng PROD_ENV =  "prod";
  pr vate stat c f nal Str ng STAG NG_ENV = "stag ng";
  pr vate stat c f nal Str ng STAG NG1_ENV = "stag ng1";
  pr vate stat c f nal Str ng REALT ME_CLUSTER = "realt  ";
  pr vate stat c f nal Str ng PROTECTED_CLUSTER = "protected";
  pr vate stat c f nal Str ng REALT ME_CG_CLUSTER = "realt  _cg";
  pr vate stat c f nal Str ng KAFKA_CL ENT_ D = "";
  pr vate stat c f nal Str ng KAFKA_TOP C_NAME = "";
  pr vate stat c f nal Str ng KAFKA_CONSUMER_GROUP_ D = "";
  pr vate stat c f nal Str ng KAFKA_CLUSTER_PATH = "";
  pr vate stat c f nal Str ng KAFKA_DEC DER_KEY = " ngester_t ets_consu _from_kafka";
  pr vate stat c f nal Str ng STATS_PREF X = "realt   ngesterp pel nev2";
  pr vate SearchCounter kafkaErrorCount = SearchCounter.create(STATS_PREF X
      + "_kafka_error_count");
  pr vate Boolean runn ng;
  pr vate Str ng env ron nt;
  pr vate Str ng cluster;
  pr vate ExecutorServ ce threadPool;
  pr vate KafkaConsu rStage<KafkaRawRecord> kafkaConsu r;
  pr vate T etEventDeser al zerStage t etEventDeser al zerStage;

  publ c Realt   ngesterP pel neV2(Str ng env ron nt, Str ng cluster,  nt threadsToSpawn) throws
      P pel neV2Creat onExcept on, P pel neStageExcept on {
     f (!env ron nt.equals(PROD_ENV) && !env ron nt.equals(STAG NG_ENV)
        && !env ron nt.equals(STAG NG1_ENV)) {
      throw new P pel neV2Creat onExcept on(" nval d value for env ron nt");
    }

     f (!cluster.equals(REALT ME_CLUSTER)
        && !cluster.equals(PROTECTED_CLUSTER) && !cluster.equals(REALT ME_CG_CLUSTER)) {
      throw new P pel neV2Creat onExcept on(" nval d value for cluster.");
    }

     nt numberOfThreads = Math.max(1, threadsToSpawn);
    t .env ron nt = env ron nt;
    t .cluster = cluster;
    t .threadPool = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L,
        T  Un .M LL SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPol cy());
     n Stages();
  }

  pr vate vo d  n Stages() throws P pel neStageExcept on {
    kafkaConsu r = new KafkaRawRecordConsu rStage(KAFKA_CL ENT_ D, KAFKA_TOP C_NAME,
        KAFKA_CONSUMER_GROUP_ D, KAFKA_CLUSTER_PATH, KAFKA_DEC DER_KEY);
    kafkaConsu r.setupStageV2();
    t etEventDeser al zerStage = new T etEventDeser al zerStage();
    t etEventDeser al zerStage.setupStageV2();
  }

  /***
   * Starts t  p pel ne by start ng t  poll ng from Kafka and pass ng t  events to t  f rst
   * stage of t  p pel ne.
   */
  publ c vo d run() {
    runn ng = true;
    wh le (runn ng) {
      pollFromKafkaAndSendToP pel ne();
    }
  }

  pr vate vo d pollFromKafkaAndSendToP pel ne() {
    try  {
      L st<KafkaRawRecord> records = kafkaConsu r.pollFromTop c();
      for (KafkaRawRecord record : records) {
        processKafkaRecord(record);
      }
    } catch (P pel neStageExcept on e) {
      kafkaErrorCount. ncre nt();
      LOG.error("Error poll ng from Kafka", e);
    }
  }

  pr vate vo d processKafkaRecord(KafkaRawRecord record) {
    CompletableFuture<KafkaRawRecord> stage1 = CompletableFuture.supplyAsync(() -> record,
        threadPool);

    CompletableFuture< ngesterT etEvent> stage2 = stage1.t nApplyAsync((KafkaRawRecord r) ->
      t etEventDeser al zerStage.runStageV2(r), threadPool);

  }

  /***
   * Stop t  p pel ne from process ng any furt r events.
   */
  publ c vo d shutdown() {
    runn ng = false;
    kafkaConsu r.cleanupStageV2();
    t etEventDeser al zerStage.cleanupStageV2();
  }
}
