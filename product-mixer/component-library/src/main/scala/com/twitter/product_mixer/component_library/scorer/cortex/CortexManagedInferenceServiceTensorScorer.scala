package com.tw ter.product_m xer.component_l brary.scorer.cortex

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.component_l brary.scorer.common.MLModel nferenceCl ent
 mport com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder.Model nferRequestBu lder
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.logg ng.Logg ng
 mport  nference.GrpcServ ce.Model nferRequest
 mport  nference.GrpcServ ce.Model nferResponse. nferOutputTensor
 mport scala.collect on.convert. mpl c Convers ons.`collect on AsScala erable`

pr vate[scorer] class CortexManaged nferenceServ ceTensorScorer[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any]
](
  overr de val  dent f er: Scorer dent f er,
  model nferRequestBu lder: Model nferRequestBu lder[
    Query,
    Cand date
  ],
  resultFeatureExtractors: Seq[FeatureW hExtractor[Query, Cand date, _]],
  cl ent: MLModel nferenceCl ent,
  statsRece ver: StatsRece ver)
    extends Scorer[Query, Cand date]
    w h Logg ng {

  requ re(resultFeatureExtractors.nonEmpty, "Result Extractors cannot be empty")

  pr vate val managedServ ceRequestFa lures = statsRece ver.counter("managedServ ceRequestFa lures")
  overr de val features: Set[Feature[_, _]] =
    resultFeatureExtractors.map(_.feature).toSet.as nstanceOf[Set[Feature[_, _]]]

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[FeatureMap]] = {
    val batch nferRequest: Model nferRequest = model nferRequestBu lder(query, cand dates)

    val managedServ ceResponse: St ch[Seq[ nferOutputTensor]] =
      cl ent.score(batch nferRequest).map(_.getOutputsL st.toSeq).onFa lure { e =>
        error(s"request to ML Managed Serv ce Fa led: $e")
        managedServ ceRequestFa lures. ncr()
      }

    managedServ ceResponse.map { responses =>
      extractResponse(query, cand dates.map(_.cand date), responses)
    }
  }

  def extractResponse(
    query: Query,
    cand dates: Seq[Cand date],
    tensorOutput: Seq[ nferOutputTensor]
  ): Seq[FeatureMap] = {
    val featureMapBu lders = cand dates.map { _ => FeatureMapBu lder.apply() }
    // Extract t  feature for each cand date from t  tensor outputs
    resultFeatureExtractors.foreach {
      case FeatureW hExtractor(feature, extractor) =>
        val extractedValues = extractor.apply(query, tensorOutput)
         f (cand dates.s ze != extractedValues.s ze) {
          throw P pel neFa lure(
             llegalStateFa lure,
            s"Managed Serv ce returned a d fferent number of $feature than t  number of cand dates." +
              s"Returned ${extractedValues.s ze} scores but t re  re ${cand dates.s ze} cand dates."
          )
        }
        // Go through t  extracted features l st one by one and update t  feature map result for each cand date.
        featureMapBu lders.z p(extractedValues).foreach {
          case (bu lder, value) =>
            bu lder.add(feature, So (value))
        }
    }

    featureMapBu lders.map(_.bu ld())
  }
}

case class FeatureW hExtractor[
  -Query <: P pel neQuery,
  -Cand date <: Un versalNoun[Any],
  ResultType
](
  feature: Feature[Cand date, Opt on[ResultType]],
  featureExtractor: ModelFeatureExtractor[Query, ResultType])

class UnexpectedFeatureTypeExcept on(feature: Feature[_, _])
    extends UnsupportedOperat onExcept on(s"Unsupported Feature type passed  n $feature")
