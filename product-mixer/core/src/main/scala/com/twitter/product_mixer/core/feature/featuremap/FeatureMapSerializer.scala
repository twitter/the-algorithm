package com.tw ter.product_m xer.core.feature.featuremap

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.fasterxml.jackson.datab nd.JsonSer al zer
 mport com.fasterxml.jackson.datab nd.Ser al zerProv der
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1Response
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1ResponseFeature
 mport com.tw ter.ut l.Return

/**
 * Render ng feature maps  s dangerous because   don't control all t  data that's stored  n t m.
 * T  can result fa led requests, as   m ght try to render a recurs ve structure, very large
 * structure, etc. Create a s mple map us ng toStr ng, t  mostly works and  s better than fa l ng
 * t  request.
 *
 * @note changes to ser al zat on log c can have ser ous performance  mpl cat ons g ven how hot t 
 *       ser al zat on path  s. Cons der benchmark ng changes w h [[com.tw ter.product_m xer.core.benchmark.Cand dateP pel neResultSer al zat onBenchmark]]
 */
pr vate[featuremap] class FeatureMapSer al zer() extends JsonSer al zer[FeatureMap] {
  overr de def ser al ze(
    featureMap: FeatureMap,
    gen: JsonGenerator,
    ser al zers: Ser al zerProv der
  ): Un  = {
    gen.wr eStartObject()

    featureMap.underly ngMap.foreach {
      case (FeatureStoreV1ResponseFeature, Return(value)) =>
        //   know that value has to be [[FeatureStoreV1Response]] but  s type has been erased,
        // prevent ng us from pattern-match ng.
        val featureStoreResponse = value.as nstanceOf[FeatureStoreV1Response]

        val features erator = featureStoreResponse.r chDataRecord.allFeatures erator()
        wh le (features erator.moveNext()) {
          gen.wr eStr ngF eld(
            features erator.getFeature.getFeatureNa ,
            s"${features erator.getFeatureType.na }(${truncateStr ng(
              features erator.getFeatureValue.toStr ng)})")
        }

        featureStoreResponse.fa ledFeatures.foreach {
          case (fa ledFeature, fa lureReasons) =>
            gen.wr eStr ngF eld(
              fa ledFeature.toStr ng,
              s"Fa led(${truncateStr ng(fa lureReasons.toStr ng)})")
        }
      case (na , Return(value)) =>
        gen.wr eStr ngF eld(na .toStr ng, truncateStr ng(value.toStr ng))
      case (na , error) =>
        // Note:   don't match on Throw(error) because   want to keep   for t  toStr ng
        gen.wr eStr ngF eld(na .toStr ng, truncateStr ng(error.toStr ng))

    }

    gen.wr eEndObject()
  }

  // So  features can be very large w n str ng f ed, for example w n a dependant cand date
  // p pel ne  s used, t  ent re prev ous cand date p pel ne result  s ser al zed  nto a feature.
  // T  causes s gn f cant performance  ssues w n t  result  s later sent over t  w re.
  pr vate def truncateStr ng( nput: Str ng): Str ng =
     f ( nput.length > 1000)  nput.take(1000) + "..." else  nput
}
