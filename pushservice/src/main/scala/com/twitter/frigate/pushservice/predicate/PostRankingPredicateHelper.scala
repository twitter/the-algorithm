package com.tw ter.fr gate.pushserv ce.pred cate

 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.data_p pel ne.features_common.MrRequestContextForFeatureStore
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.T et
 mport com.tw ter.ml.featurestore.catalog.features.core.T et.Text
 mport com.tw ter.ml.featurestore.l b.T et d
 mport com.tw ter.ml.featurestore.l b.dynam c.Dynam cFeatureStoreCl ent
 mport com.tw ter.ml.featurestore.l b.onl ne.FeatureStoreRequest
 mport com.tw ter.ut l.Future

object PostRank ngPred cate lper {

  val t etTextFeature = "t et.core.t et.text"

  def getT etText(
    cand date: PushCand date w h T etCand date,
    dynam cCl ent: Dynam cFeatureStoreCl ent[MrRequestContextForFeatureStore]
  ): Future[Str ng] = {
     f (cand date.categor calFeatures.conta ns(t etTextFeature)) {
      Future.value(cand date.categor calFeatures.getOrElse(t etTextFeature, ""))
    } else {
      val cand dateT etEnt y = T et.w h d(T et d(cand date.t et d))
      val featureStoreRequests = Seq(
        FeatureStoreRequest(
          ent y ds = Seq(cand dateT etEnt y)
        ))
      val pred ct onRecords = dynam cCl ent(
        featureStoreRequests,
        requestContext = cand date.target.mrRequestContextForFeatureStore)

      pred ct onRecords.map { records =>
        val t etText = records. ad
          .getFeatureValue(cand dateT etEnt y, Text).getOrElse(
            ""
          )
        cand date.categor calFeatures(t etTextFeature) = t etText
        t etText
      }
    }
  }

  def getT etWordLength(t etText: Str ng): Double = {
    val t etTextW houtUrl: Str ng =
      t etText.replaceAll("https?://\\S+\\s?", "").replaceAll("[\\s]+", " ")
    t etTextW houtUrl.tr m().spl (" ").length.toDouble
  }

}
