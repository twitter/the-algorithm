package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.summ ngb rd._
 mport com.tw ter.summ ngb rd.storm.Storm
 mport com.tw ter.summ ngb rd_ nternal.s ces.App d
 mport com.tw ter.summ ngb rd_ nternal.s ces.storm.remote.Cl entEventS ceScrooge2
 mport com.tw ter.t  l nes.data_process ng.ad_hoc.suggests.common.AllScr beProcessor
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron.RealT  AggregatesJobConf g
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron.StormAggregateS ce
 mport com.tw ter.t  l nes.pred ct on.adapters.cl ent_log_event.Cl entLogEventAdapter
 mport com.tw ter.t  l nes.pred ct on.adapters.cl ent_log_event.Prof leCl entLogEventAdapter
 mport com.tw ter.t  l nes.pred ct on.adapters.cl ent_log_event.SearchCl entLogEventAdapter
 mport com.tw ter.t  l nes.pred ct on.adapters.cl ent_log_event.UuaEventAdapter
 mport com.tw ter.un f ed_user_act ons.cl ent.conf g.KafkaConf gs
 mport com.tw ter.un f ed_user_act ons.cl ent.summ ngb rd.Un f edUserAct onsS ceScrooge
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport scala.collect on.JavaConverters._

/**
 * Storm Producer for cl ent events generated on Ho , Prof le, and Search
 */
class T  l nesStormAggregateS ce extends StormAggregateS ce {

  overr de val na  = "t  l nes_rta"
  overr de val t  stampFeature = SharedFeatures.T MESTAMP

  pr vate lazy val T  l nesCl entEventS ceNa  = "TL_EVENTS_SOURCE"
  pr vate lazy val Prof leCl entEventS ceNa  = "PROF LE_EVENTS_SOURCE"
  pr vate lazy val SearchCl entEventS ceNa  = "SEARCH_EVENTS_SOURCE"
  pr vate lazy val UuaEventS ceNa  = "UUA_EVENTS_SOURCE"
  pr vate lazy val Comb nedProducerNa  = "COMB NED_PRODUCER"
  pr vate lazy val FeatureStoreProducerNa  = "FEATURE_STORE_PRODUCER"

  pr vate def  sNewUserEvent(event: LogEvent): Boolean = {
    event.logBase.flatMap(_.user d).flatMap(Snowflake d.t  From dOpt).ex sts(_.unt lNow < 30.days)
  }

  pr vate def mkDataRecords(event: LogEvent, dataRecordCounter: Counter): Seq[DataRecord] = {
    val dataRecords: Seq[DataRecord] =
       f (AllScr beProcessor. sVal dSuggestT etEvent(event)) {
        Cl entLogEventAdapter.adaptToDataRecords(event).asScala
      } else {
        Seq.empty[DataRecord]
      }
    dataRecordCounter. ncr(dataRecords.s ze)
    dataRecords
  }

  pr vate def mkProf leDataRecords(
    event: LogEvent,
    dataRecordCounter: Counter
  ): Seq[DataRecord] = {
    val dataRecords: Seq[DataRecord] =
      Prof leCl entLogEventAdapter.adaptToDataRecords(event).asScala
    dataRecordCounter. ncr(dataRecords.s ze)
    dataRecords
  }

  pr vate def mkSearchDataRecords(
    event: LogEvent,
    dataRecordCounter: Counter
  ): Seq[DataRecord] = {
    val dataRecords: Seq[DataRecord] =
      SearchCl entLogEventAdapter.adaptToDataRecords(event).asScala
    dataRecordCounter. ncr(dataRecords.s ze)
    dataRecords
  }

  pr vate def mkUuaDataRecords(
    event: Un f edUserAct on,
    dataRecordCounter: Counter
  ): Seq[DataRecord] = {
    val dataRecords: Seq[DataRecord] =
      UuaEventAdapter.adaptToDataRecords(event).asScala
    dataRecordCounter. ncr(dataRecords.s ze)
    dataRecords
  }

  overr de def bu ld(
    statsRece ver: StatsRece ver,
    jobConf g: RealT  AggregatesJobConf g
  ): Producer[Storm, DataRecord] = {
    lazy val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )
    lazy val dataRecordCounter = scopedStatsRece ver.counter("dataRecord")

    // Ho  T  l ne Engage nts
    // Step 1: => LogEvent
    lazy val cl entEventProducer: Producer[Storm, Ho Event[LogEvent]] =
      Cl entEventS ceScrooge2(
        app d = App d(jobConf g.app d),
        top c = "julep_cl ent_event_suggests",
        resu AtLastReadOffset = false,
        enableTls = true
      ).s ce.map(Ho Event[LogEvent]).na (T  l nesCl entEventS ceNa )

    // Prof le Engage nts
    // Step 1: => LogEvent
    lazy val prof leCl entEventProducer: Producer[Storm, Prof leEvent[LogEvent]] =
      Cl entEventS ceScrooge2(
        app d = App d(jobConf g.app d),
        top c = "julep_cl ent_event_prof le_real_t  _engage nt_ tr cs",
        resu AtLastReadOffset = false,
        enableTls = true
      ).s ce
        .map(Prof leEvent[LogEvent])
        .na (Prof leCl entEventS ceNa )

    // Search Engage nts
    // Step 1: => LogEvent
    // Only process events for all users to save res ce
    lazy val searchCl entEventProducer: Producer[Storm, SearchEvent[LogEvent]] =
      Cl entEventS ceScrooge2(
        app d = App d(jobConf g.app d),
        top c = "julep_cl ent_event_search_real_t  _engage nt_ tr cs",
        resu AtLastReadOffset = false,
        enableTls = true
      ).s ce
        .map(SearchEvent[LogEvent])
        .na (SearchCl entEventS ceNa )

    // Un f ed User Act ons ( ncludes Ho  and ot r product surfaces)
    lazy val uuaEventProducer: Producer[Storm, UuaEvent[Un f edUserAct on]] =
      Un f edUserAct onsS ceScrooge(
        app d = App d(jobConf g.app d),
        parallel sm = 10,
        kafkaConf g = KafkaConf gs.ProdUn f edUserAct onsEngage ntOnly
      ).s ce
        .f lter(StormAggregateS ceUt ls. sUuaBCEEventsFromHo (_))
        .map(UuaEvent[Un f edUserAct on])
        .na (UuaEventS ceNa )

    // Comb ned
    // Step 2:
    // (a) Comb ne
    // (b) Transform LogEvent => Seq[DataRecord]
    // (c) Apply sampler
    lazy val comb nedCl entEventDataRecordProducer: Producer[Storm, Event[DataRecord]] =
      prof leCl entEventProducer // T  beco s t  bottom branch
        . rge(cl entEventProducer) // T  beco s t  m ddle branch
        . rge(searchCl entEventProducer)
        . rge(uuaEventProducer) // T  beco s t  top
        .flatMap { // LogEvent => Seq[DataRecord]
          case e: Ho Event[LogEvent] =>
            mkDataRecords(e.event, dataRecordCounter).map(Ho Event[DataRecord])
          case e: Prof leEvent[LogEvent] =>
            mkProf leDataRecords(e.event, dataRecordCounter).map(Prof leEvent[DataRecord])
          case e: SearchEvent[LogEvent] =>
            mkSearchDataRecords(e.event, dataRecordCounter).map(SearchEvent[DataRecord])
          case e: UuaEvent[Un f edUserAct on] =>
            mkUuaDataRecords(
              e.event,
              dataRecordCounter
            ).map(UuaEvent[DataRecord])
        }
        .flatMap { // Apply sampler
          case e: Ho Event[DataRecord] =>
            jobConf g.sequent allyTransform(e.event).map(Ho Event[DataRecord])
          case e: Prof leEvent[DataRecord] =>
            jobConf g.sequent allyTransform(e.event).map(Prof leEvent[DataRecord])
          case e: SearchEvent[DataRecord] =>
            jobConf g.sequent allyTransform(e.event).map(SearchEvent[DataRecord])
          case e: UuaEvent[DataRecord] =>
            jobConf g.sequent allyTransform(e.event).map(UuaEvent[DataRecord])
        }
        .na (Comb nedProducerNa )

    // Step 3: Jo n w h Feature Store features
    lazy val featureStoreDataRecordProducer: Producer[Storm, DataRecord] =
      StormAggregateS ceUt ls
        .wrapByFeatureStoreCl ent(
          underly ngProducer = comb nedCl entEventDataRecordProducer,
          jobConf g = jobConf g,
          scopedStatsRece ver = scopedStatsRece ver
        ).map(_.event).na (FeatureStoreProducerNa )

    featureStoreDataRecordProducer
  }
}
