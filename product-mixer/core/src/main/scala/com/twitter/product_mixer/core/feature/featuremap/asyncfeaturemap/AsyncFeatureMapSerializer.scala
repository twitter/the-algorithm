package com.tw ter.product_m xer.core.feature.featuremap.asyncfeaturemap

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.fasterxml.jackson.datab nd.JsonSer al zer
 mport com.fasterxml.jackson.datab nd.Ser al zerProv der

/**
 * S nce an [[AsyncFeatureMap]]  s typ cally  ncomplete, and by t  t    's ser al zed, all t  [[com.tw ter.product_m xer.core.feature.Feature]]s
 *   w ll typ cally be completed and part of t  Query or Cand date's  nd v dual [[com.tw ter.product_m xer.core.feature.Feature]]s
 *    nstead opt to prov de a summary of t  Features wh ch would be hydrated us ng [[AsyncFeatureMap.features]]
 *
 * T   nd cates wh ch [[com.tw ter.product_m xer.core.feature.Feature]]s w ll be ready at wh ch Steps
 * and wh ch [[com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.FeatureHydrator]]
 * are respons ble for those [[com.tw ter.product_m xer.core.feature.Feature]]
 *
 * @note changes to ser al zat on log c can have ser ous performance  mpl cat ons g ven how hot t 
 *       ser al zat on path  s. Cons der benchmark ng changes w h [[com.tw ter.product_m xer.core.benchmark.AsyncQueryFeatureMapSer al zat onBenchmark]]
 */
pr vate[asyncfeaturemap] class AsyncFeatureMapSer al zer() extends JsonSer al zer[AsyncFeatureMap] {
  overr de def ser al ze(
    asyncFeatureMap: AsyncFeatureMap,
    gen: JsonGenerator,
    ser al zers: Ser al zerProv der
  ): Un  = {
    gen.wr eStartObject()

    asyncFeatureMap.features.foreach {
      case (step dent f er, featureHydrators) =>
        gen.wr eObjectF eldStart(step dent f er.toStr ng)

        featureHydrators.foreach {
          case (hydrator dent f er, featuresFromHydrator) =>
            gen.wr eArrayF eldStart(hydrator dent f er.toStr ng)

            featuresFromHydrator.foreach(feature => gen.wr eStr ng(feature.toStr ng))

            gen.wr eEndArray()
        }

        gen.wr eEndObject()
    }

    gen.wr eEndObject()
  }
}
