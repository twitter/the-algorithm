package com.tw ter.ho _m xer.product.for_ .s de_effect

 mport com.tw ter.ho _m xer.model.Ho Features. sReadFromCac Feature
 mport com.tw ter.ho _m xer.model.Ho Features.Pred ct onRequest dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Served dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ServedRequest dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.StreamToKafkaFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object ServedCand dateKafkaS deEffect {

  def extractCand dates(
    query: P pel neQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    s ce dent f ers: Set[Cand dateP pel ne dent f er]
  ): Seq[ emCand dateW hDeta ls] = {
    val servedRequest dOpt =
      query.features.getOrElse(FeatureMap.empty).getOrElse(ServedRequest dFeature, None)

    selectedCand dates. erator
      .f lter(cand date => s ce dent f ers.conta ns(cand date.s ce))
      .flatMap {
        case  em:  emCand dateW hDeta ls => Seq( em)
        case module: ModuleCand dateW hDeta ls => module.cand dates
      }
      .f lter(cand date => cand date.features.getOrElse(StreamToKafkaFeature, false))
      .map { cand date =>
        val served d =
           f (cand date.features.getOrElse( sReadFromCac Feature, false) &&
            servedRequest dOpt.nonEmpty)
            servedRequest dOpt
          else
            cand date.features.getOrElse(Pred ct onRequest dFeature, None)

        cand date.copy(features = cand date.features + (Served dFeature, served d))
      }.toSeq
      // dedupl cate by (t et d, user d, served d)
      .groupBy { cand date =>
        (
          cand date.cand date dLong,
          query.getRequ redUser d,
          cand date.features.getOrElse(Served dFeature, None))
      }.values.map(_. ad).toSeq
  }
}
