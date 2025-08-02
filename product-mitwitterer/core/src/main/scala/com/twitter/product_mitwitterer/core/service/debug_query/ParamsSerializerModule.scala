package com.twitter.product_mitwitterer.core.service.debug_query

import com.fastertwitterml.jackson.core.JsonGenerator
import com.fastertwitterml.jackson.databind.SerializerProvider
import com.fastertwitterml.jackson.databind.ser.std.StdSerializer
import com.twitter.timelines.configapi.Params
import com.fastertwitterml.jackson.databind.module.SimpleModule
import com.twitter.timelines.configapi.Config

object ParamsSerializerModule etwittertends SimpleModule {
  addSerializer(ParamsConfigSerializer)
  addSerializer(ParamsStdSerializer)
}

object ParamsStdSerializer etwittertends StdSerializer[Params](classOf[Params]) {
  override def serialize(
    value: Params,
    gen: JsonGenerator,
    provider: SerializerProvider
  ): Unit = {
    gen.writeStartObject()
    gen.writeObjectField("applied_params", value.allAppliedValues)
    gen.writeEndObject()
  }
}

object ParamsConfigSerializer etwittertends StdSerializer[Config](classOf[Config]) {
  override def serialize(
    value: Config,
    gen: JsonGenerator,
    provider: SerializerProvider
  ): Unit = {
    gen.writeString(value.simpleName)
  }
}
