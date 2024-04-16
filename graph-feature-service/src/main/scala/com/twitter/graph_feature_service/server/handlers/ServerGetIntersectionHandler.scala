package com.tw ter.graph_feature_serv ce.server.handlers

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graph_feature_serv ce.server.handlers.ServerGet ntersect onHandler.Get ntersect onRequest
 mport com.tw ter.graph_feature_serv ce.server.stores.FeatureTypesEncoder
 mport com.tw ter.graph_feature_serv ce.server.stores.Get ntersect onStore.Get ntersect onQuery
 mport com.tw ter.graph_feature_serv ce.thr ftscala.PresetFeatureTypes
 mport com.tw ter.graph_feature_serv ce.thr ftscala._
 mport com.tw ter.graph_feature_serv ce.ut l.FeatureTypesCalculator
 mport com.tw ter.servo.request.RequestHandler
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l. mo ze
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
class ServerGet ntersect onHandler @ nject() (
  @Na d("ReadThroughGet ntersect onStore")
  readThroughStore: ReadableStore[Get ntersect onQuery, Cac d ntersect onResult],
  @Na d("BypassCac Get ntersect onStore")
  readOnlyStore: ReadableStore[Get ntersect onQuery, Cac d ntersect onResult]
)(
   mpl c  statsRece ver: StatsRece ver)
    extends RequestHandler[Get ntersect onRequest, Gfs ntersect onResponse] {

   mport ServerGet ntersect onHandler._

  // TODO: Track all t  stats based on PresetFeatureType and update t  dashboard
  pr vate val stats: StatsRece ver = statsRece ver.scope("srv").scope("get_ ntersect on")
  pr vate val numCand datesCount = stats.counter("total_num_cand dates")
  pr vate val numCand datesStat = stats.stat("num_cand dates")
  pr vate val numFeaturesStat = stats.stat("num_features")
  pr vate val userEmptyCount = stats.counter("user_empty_count")
  pr vate val cand dateEmptyRateStat = stats.stat("cand date_empty_rate")
  pr vate val cand dateNumEmptyStat = stats.stat("cand date_num_empty")
  pr vate val m ssedRateStat = stats.stat("m ss_rate")
  pr vate val numM ssedStat = stats.stat("num_m ssed")

  // Assu  t  order from HTL doesn't change. Only log t  HTL query now.
  pr vate val featureStatMap = FeatureTypesCalculator.presetFeatureTypes.map { feature =>
    val featureStr ng = s"${feature.leftEdgeType.na }_${feature.r ghtEdgeType.na }"
    feature -> Array(
      stats.counter(s"feature_type_${featureStr ng}_total"),
      stats.counter(s"feature_type_${featureStr ng}_count_zero"),
      stats.counter(s"feature_type_${featureStr ng}_left_zero"),
      stats.counter(s"feature_type_${featureStr ng}_r ght_zero")
    )
  }.toMap

  pr vate val s ceCand dateNumStats =  mo ze[PresetFeatureTypes, Stat] { presetFeature =>
    stats.stat(s"s ce_cand date_num_${presetFeature.na }")
  }

  overr de def apply(request: Get ntersect onRequest): Future[Gfs ntersect onResponse] = {
    val featureTypes = request.calculatedFeatureTypes
    val numCand dates = request.cand dateUser ds.length
    val numFeatures = featureTypes.length

    numCand datesCount. ncr(numCand dates)
    numCand datesStat.add(numCand dates)
    numFeaturesStat.add(numFeatures)
    s ceCand dateNumStats(request.presetFeatureTypes).add(numCand dates)

    // Note: do not change t  orders of features and cand dates.
    val cand date ds = request.cand dateUser ds

     f (featureTypes. sEmpty || cand date ds. sEmpty) {
      Future.value(DefaultGfs ntersect onResponse)
    } else {
      Future
        .collect {
          val get ntersect onStore =  f (request.cac able) readThroughStore else readOnlyStore
          get ntersect onStore.mult Get(Get ntersect onQuery.bu ldQuer es(request))
        }.map { responses =>
          val results = responses.collect {
            case (query, So (result)) =>
              query.cand date d -> Gfs ntersect onResult(
                query.cand date d,
                query.calculatedFeatureTypes.z p(result.values).map {
                  case (featureType, value) =>
                     ntersect onValue(
                      featureType,
                      So (value.count),
                       f (value. ntersect on ds. sEmpty) None else So (value. ntersect on ds),
                      So (value.leftNodeDegree),
                      So (value.r ghtNodeDegree)
                    )
                }
              )
          }

          // Keep t  response order sa  as  nput
          val processedResults = cand date ds.map { cand date d =>
            results.getOrElse(cand date d, Gfs ntersect onResult(cand date d, L st.empty))
          }

          val cand dateEmptyNum =
            processedResults.count(
              _. ntersect onValues.ex sts(value =>  sZero(value.r ghtNodeDegree)))

          val numM ssed = processedResults.count(_. ntersect onValues.s ze != numFeatures)

           f (processedResults.ex sts(
              _. ntersect onValues.forall(value =>  sZero(value.leftNodeDegree)))) {
            userEmptyCount. ncr()
          }

          cand dateNumEmptyStat.add(cand dateEmptyNum)
          cand dateEmptyRateStat.add(cand dateEmptyNum.toFloat / numCand dates)
          numM ssedStat.add(numM ssed)
          m ssedRateStat.add(numM ssed.toFloat / numCand dates)

          processedResults.foreach { result =>
            result. ntersect onValues.z p(featureTypes).foreach {
              case (value, featureType) =>
                featureStatMap.get(featureType).foreach { statsArray =>
                  statsArray(Total ndex). ncr()
                   f ( sZero(value.count)) {
                    statsArray(Count ndex). ncr()
                  }
                   f ( sZero(value.leftNodeDegree)) {
                    statsArray(Left ndex). ncr()
                  }
                   f ( sZero(value.r ghtNodeDegree)) {
                    statsArray(R ght ndex). ncr()
                  }
                }
            }
          }

          Gfs ntersect onResponse(processedResults)
        }
    }

  }

}

pr vate[graph_feature_serv ce] object ServerGet ntersect onHandler {

  case class Get ntersect onRequest(
    user d: Long,
    cand dateUser ds: Seq[Long],
    featureTypes: Seq[FeatureType],
    presetFeatureTypes: PresetFeatureTypes,
     ntersect on dL m : Opt on[ nt],
    cac able: Boolean) {

    lazy val calculatedFeatureTypes: Seq[FeatureType] =
      FeatureTypesCalculator.getFeatureTypes(presetFeatureTypes, featureTypes)

    lazy val calculatedFeatureTypesStr ng: Str ng =
      FeatureTypesEncoder(calculatedFeatureTypes)
  }

  object Get ntersect onRequest {

    def fromGfs ntersect onRequest(
      request: Gfs ntersect onRequest,
      cac able: Boolean
    ): Get ntersect onRequest = {
      Get ntersect onRequest(
        request.user d,
        request.cand dateUser ds,
        request.featureTypes,
        PresetFeatureTypes.Empty,
        request. ntersect on dL m ,
        cac able)
    }

    def fromGfsPreset ntersect onRequest(
      request: GfsPreset ntersect onRequest,
      cac able: Boolean
    ): Get ntersect onRequest = {
      Get ntersect onRequest(
        request.user d,
        request.cand dateUser ds,
        L st.empty,
        request.presetFeatureTypes,
        request. ntersect on dL m ,
        cac able)
    }
  }

  pr vate val DefaultGfs ntersect onResponse = Gfs ntersect onResponse()

  pr vate val Total ndex = 0
  pr vate val Count ndex = 1
  pr vate val Left ndex = 2
  pr vate val R ght ndex = 3

  def  sZero(opt: Opt on[ nt]): Boolean = {
    !opt.ex sts(_ != 0)
  }
}
