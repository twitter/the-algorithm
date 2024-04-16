package com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.fasterxml.jackson.datab nd.JsonSer al zer
 mport com.fasterxml.jackson.datab nd.Ser al zerProv der
 mport com.tw ter.product_m xer.core.serv ce.query_feature_hydrator_executor.QueryFeatureHydratorExecutor.Async nd v dualFeatureHydratorResult

/** A [[JsonSer al zer]] that sk ps t  `St ch` values */
pr vate[query_feature_hydrator_executor] class Async nd v dualFeatureHydratorResultSer al zer()
    extends JsonSer al zer[Async nd v dualFeatureHydratorResult] {

  overr de def ser al ze(
    async nd v dualFeatureHydratorResult: Async nd v dualFeatureHydratorResult,
    gen: JsonGenerator,
    ser al zers: Ser al zerProv der
  ): Un  =
    ser al zers.defaultSer al zeValue(
      //  mpl c ly calls `toStr ng` on t   dent f er because t y are keys  n t  Map
      Map(
        async nd v dualFeatureHydratorResult.hydrateBefore ->
          async nd v dualFeatureHydratorResult.features.map(_.toStr ng)),
      gen
    )
}
