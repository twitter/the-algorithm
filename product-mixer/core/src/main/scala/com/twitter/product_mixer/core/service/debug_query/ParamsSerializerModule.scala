package com.twitter.product_mixer.core.service.debug_query

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.twitter.timelines.configapi.Params
import com.fasterxml.jackson.databind.module.SimpleModule
import com.twitter.timelines.configapi.Config

object ParamsSerializerModule extends SimpleModule {
  addSerializer(ParamsConfigSerializer)
  addSerializer(ParamsStdSerializer)
}

object ParamsStdSerializer extends StdSerializer[Params](classOf[Params]) {
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

object ParamsConfigSerializer extends StdSerializer[Config](classOf[Config]) {
  override def serialize(
    value: Config,
    gen: JsonGenerator,
    provider: SerializerProvider
  ): Unit = {
    gen.writeString(value.simpleName)
  }
}
