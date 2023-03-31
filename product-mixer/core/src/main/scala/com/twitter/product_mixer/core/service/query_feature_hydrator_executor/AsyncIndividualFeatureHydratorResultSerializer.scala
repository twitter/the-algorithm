package com.twitter.product_mixer.core.service.query_feature_hydrator_executor

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.twitter.product_mixer.core.service.query_feature_hydrator_executor.QueryFeatureHydratorExecutor.AsyncIndividualFeatureHydratorResult

/** A [[JsonSerializer]] that skips the `Stitch` values */
private[query_feature_hydrator_executor] class AsyncIndividualFeatureHydratorResultSerializer()
    extends JsonSerializer[AsyncIndividualFeatureHydratorResult] {

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
