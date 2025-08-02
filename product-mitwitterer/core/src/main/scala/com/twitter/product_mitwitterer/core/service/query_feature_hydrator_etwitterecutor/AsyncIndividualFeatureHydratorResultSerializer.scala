package com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor

import com.fastertwitterml.jackson.core.JsonGenerator
import com.fastertwitterml.jackson.databind.JsonSerializer
import com.fastertwitterml.jackson.databind.SerializerProvider
import com.twitter.product_mitwitterer.core.service.query_feature_hydrator_etwitterecutor.QueryFeatureHydratorEtwitterecutor.AsyncIndividualFeatureHydratorResult

/** A [[JsonSerializer]] that skips the `Stitch` values */
private[query_feature_hydrator_etwitterecutor] class AsyncIndividualFeatureHydratorResultSerializer()
    etwittertends JsonSerializer[AsyncIndividualFeatureHydratorResult] {

  override def serialize(
    asyncIndividualFeatureHydratorResult: AsyncIndividualFeatureHydratorResult,
    gen: JsonGenerator,
    serializers: SerializerProvider
  ): Unit =
    serializers.defaultSerializeValue(
      // implicitly calls `toString` on the identifier because they are keys in the Map
      Map(
        asyncIndividualFeatureHydratorResult.hydrateBefore ->
          asyncIndividualFeatureHydratorResult.features.map(_.toString)),
      gen
    )
}
