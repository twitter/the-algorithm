package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .DataRecord rger
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.Author
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.T et
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.User
 mport com.tw ter.ml.featurestore.l b.onl ne.FeatureStoreCl ent
 mport com.tw ter.summ ngb rd.Producer
 mport com.tw ter.summ ngb rd.storm.Storm
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron.RealT  AggregatesJobConf g
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport java.lang.{Long => JLong}

 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on

pr vate[real_t  ] object StormAggregateS ceUt ls {
  type User d = Long
  type Author d = Long
  type T et d = Long

  /**
   * Attac s a [[FeatureStoreCl ent]] to t  underyl ng [[Producer]]. T  FeatureStoreCl ent
   * hydrates add  onal user features.
   *
   * @param underly ngProducer converts a stream of [[com.tw ter.cl entapp.thr ftscala.LogEvent]]
   *                           to a stream of [[DataRecord]].
   */
  def wrapByFeatureStoreCl ent(
    underly ngProducer: Producer[Storm, Event[DataRecord]],
    jobConf g: RealT  AggregatesJobConf g,
    scopedStatsRece ver: StatsRece ver
  ): Producer[Storm, Event[DataRecord]] = {
    lazy val keyDataRecordCounter = scopedStatsRece ver.counter("keyDataRecord")
    lazy val keyFeatureCounter = scopedStatsRece ver.counter("keyFeature")
    lazy val leftDataRecordCounter = scopedStatsRece ver.counter("leftDataRecord")
    lazy val r ghtDataRecordCounter = scopedStatsRece ver.counter("r ghtDataRecord")
    lazy val  rgeNumFeaturesCounter = scopedStatsRece ver.counter(" rgeNumFeatures")
    lazy val authorKeyDataRecordCounter = scopedStatsRece ver.counter("authorKeyDataRecord")
    lazy val authorKeyFeatureCounter = scopedStatsRece ver.counter("authorKeyFeature")
    lazy val authorLeftDataRecordCounter = scopedStatsRece ver.counter("authorLeftDataRecord")
    lazy val authorR ghtDataRecordCounter = scopedStatsRece ver.counter("authorR ghtDataRecord")
    lazy val author rgeNumFeaturesCounter = scopedStatsRece ver.counter("author rgeNumFeatures")
    lazy val t etKeyDataRecordCounter =
      scopedStatsRece ver.counter("t etKeyDataRecord")
    lazy val t etKeyFeatureCounter = scopedStatsRece ver.counter("t etKeyFeature")
    lazy val t etLeftDataRecordCounter =
      scopedStatsRece ver.counter("t etLeftDataRecord")
    lazy val t etR ghtDataRecordCounter =
      scopedStatsRece ver.counter("t etR ghtDataRecord")
    lazy val t et rgeNumFeaturesCounter =
      scopedStatsRece ver.counter("t et rgeNumFeatures")

    @trans ent lazy val featureStoreCl ent: FeatureStoreCl ent =
      FeatureStoreUt ls.mkFeatureStoreCl ent(
        serv ce dent f er = jobConf g.serv ce dent f er,
        statsRece ver = scopedStatsRece ver
      )

    lazy val jo nUserFeaturesDataRecordProducer =
       f (jobConf g.keyedByUserEnabled) {
        lazy val keyedByUserFeaturesStormServ ce: Storm#Serv ce[Set[User d], DataRecord] =
          Storm.serv ce(
            new UserFeaturesReadableStore(
              featureStoreCl ent = featureStoreCl ent,
              userEnt y = User,
              userFeaturesAdapter = UserFeaturesAdapter
            )
          )

        leftJo nDataRecordProducer(
          keyFeature = SharedFeatures.USER_ D,
          leftDataRecordProducer = underly ngProducer,
          r ghtStormServ ce = keyedByUserFeaturesStormServ ce,
          keyDataRecordCounter = keyDataRecordCounter,
          keyFeatureCounter = keyFeatureCounter,
          leftDataRecordCounter = leftDataRecordCounter,
          r ghtDataRecordCounter = r ghtDataRecordCounter,
           rgeNumFeaturesCounter =  rgeNumFeaturesCounter
        )
      } else {
        underly ngProducer
      }

    lazy val jo nAuthorFeaturesDataRecordProducer =
       f (jobConf g.keyedByAuthorEnabled) {
        lazy val keyedByAuthorFeaturesStormServ ce: Storm#Serv ce[Set[Author d], DataRecord] =
          Storm.serv ce(
            new UserFeaturesReadableStore(
              featureStoreCl ent = featureStoreCl ent,
              userEnt y = Author,
              userFeaturesAdapter = AuthorFeaturesAdapter
            )
          )

        leftJo nDataRecordProducer(
          keyFeature = T  l nesSharedFeatures.SOURCE_AUTHOR_ D,
          leftDataRecordProducer = jo nUserFeaturesDataRecordProducer,
          r ghtStormServ ce = keyedByAuthorFeaturesStormServ ce,
          keyDataRecordCounter = authorKeyDataRecordCounter,
          keyFeatureCounter = authorKeyFeatureCounter,
          leftDataRecordCounter = authorLeftDataRecordCounter,
          r ghtDataRecordCounter = authorR ghtDataRecordCounter,
           rgeNumFeaturesCounter = author rgeNumFeaturesCounter
        )
      } else {
        jo nUserFeaturesDataRecordProducer
      }

    lazy val jo nT etFeaturesDataRecordProducer = {
       f (jobConf g.keyedByT etEnabled) {
        lazy val keyedByT etFeaturesStormServ ce: Storm#Serv ce[Set[T et d], DataRecord] =
          Storm.serv ce(
            new T etFeaturesReadableStore(
              featureStoreCl ent = featureStoreCl ent,
              t etEnt y = T et,
              t etFeaturesAdapter = T etFeaturesAdapter
            )
          )

        leftJo nDataRecordProducer(
          keyFeature = T  l nesSharedFeatures.SOURCE_TWEET_ D,
          leftDataRecordProducer = jo nAuthorFeaturesDataRecordProducer,
          r ghtStormServ ce = keyedByT etFeaturesStormServ ce,
          keyDataRecordCounter = t etKeyDataRecordCounter,
          keyFeatureCounter = t etKeyFeatureCounter,
          leftDataRecordCounter = t etLeftDataRecordCounter,
          r ghtDataRecordCounter = t etR ghtDataRecordCounter,
           rgeNumFeaturesCounter = t et rgeNumFeaturesCounter
        )
      } else {
        jo nAuthorFeaturesDataRecordProducer
      }
    }

    jo nT etFeaturesDataRecordProducer
  }

  pr vate[t ] lazy val DataRecord rger = new DataRecord rger

  /**
   * Make jo n key from t  cl ent event data record and return both.
   * @param keyFeature Feature to extract jo n key value: USER_ D, SOURCE_TWEET_ D, etc.
   * @param record DataRecord conta n ng cl ent engage nt and bas c t et-s de features
   * @return T  return type  s a tuple of t  key and or g nal data record wh ch w ll be used
   *          n t  subsequent leftJo n operat on.
   */
  pr vate[t ] def mkKey(
    keyFeature: Feature[JLong],
    record: DataRecord,
    keyDataRecordCounter: Counter,
    keyFeatureCounter: Counter
  ): Set[Long] = {
    keyDataRecordCounter. ncr()
    val r chRecord = new R chDataRecord(record)
     f (r chRecord.hasFeature(keyFeature)) {
      keyFeatureCounter. ncr()
      val key: Long = r chRecord.getFeatureValue(keyFeature).toLong
      Set(key)
    } else {
      Set.empty[Long]
    }
  }

  /**
   * After t  leftJo n,  rge t  cl ent event data record and t  jo ned data record
   *  nto a s ngle data record used for furt r aggregat on.
   */
  pr vate[t ] def  rgeDataRecord(
    leftRecord: Event[DataRecord],
    r ghtRecordOpt: Opt on[DataRecord],
    leftDataRecordCounter: Counter,
    r ghtDataRecordCounter: Counter,
     rgeNumFeaturesCounter: Counter
  ): Event[DataRecord] = {
    leftDataRecordCounter. ncr()
    r ghtRecordOpt.foreach { r ghtRecord =>
      r ghtDataRecordCounter. ncr()
      DataRecord rger. rge(leftRecord.event, r ghtRecord)
       rgeNumFeaturesCounter. ncr(new R chDataRecord(leftRecord.event).numFeatures())
    }
    leftRecord
  }

  pr vate[t ] def leftJo nDataRecordProducer(
    keyFeature: Feature[JLong],
    leftDataRecordProducer: Producer[Storm, Event[DataRecord]],
    r ghtStormServ ce: Storm#Serv ce[Set[Long], DataRecord],
    keyDataRecordCounter: => Counter,
    keyFeatureCounter: => Counter,
    leftDataRecordCounter: => Counter,
    r ghtDataRecordCounter: => Counter,
     rgeNumFeaturesCounter: => Counter
  ): Producer[Storm, Event[DataRecord]] = {
    val keyedLeftDataRecordProducer: Producer[Storm, (Set[Long], Event[DataRecord])] =
      leftDataRecordProducer.map {
        case dataRecord: Ho Event[DataRecord] =>
          val key = mkKey(
            keyFeature = keyFeature,
            record = dataRecord.event,
            keyDataRecordCounter = keyDataRecordCounter,
            keyFeatureCounter = keyFeatureCounter
          )
          (key, dataRecord)
        case dataRecord: Prof leEvent[DataRecord] =>
          val key = Set.empty[Long]
          (key, dataRecord)
        case dataRecord: SearchEvent[DataRecord] =>
          val key = Set.empty[Long]
          (key, dataRecord)
        case dataRecord: UuaEvent[DataRecord] =>
          val key = Set.empty[Long]
          (key, dataRecord)
      }

    keyedLeftDataRecordProducer
      .leftJo n(r ghtStormServ ce)
      .map {
        case (_, (leftRecord, r ghtRecordOpt)) =>
           rgeDataRecord(
            leftRecord = leftRecord,
            r ghtRecordOpt = r ghtRecordOpt,
            leftDataRecordCounter = leftDataRecordCounter,
            r ghtDataRecordCounter = r ghtDataRecordCounter,
             rgeNumFeaturesCounter =  rgeNumFeaturesCounter
          )
      }
  }

  /**
   * F lter Un f ed User Act ons events to  nclude only act ons that has ho  t  l ne v s  pr or to land ng on t  page
   */
  def  sUuaBCEEventsFromHo (event: Un f edUserAct on): Boolean = {
    def breadcrumbV ewsConta n(v ew: Str ng): Boolean =
      event.event tadata.breadcrumbV ews.map(_.conta ns(v ew)).getOrElse(false)

    (event.act onType) match {
      case Act onType.Cl entT etV2 mpress on  f breadcrumbV ewsConta n("ho ") =>
        true
      case Act onType.Cl entT etV deoFullscreenV2 mpress on
           f (breadcrumbV ewsConta n("ho ") & breadcrumbV ewsConta n("v deo")) =>
        true
      case Act onType.Cl entProf leV2 mpress on  f breadcrumbV ewsConta n("ho ") =>
        true
      case _ => false
    }
  }
}
