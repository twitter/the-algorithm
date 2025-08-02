package com.twitter.product_mitwitterer.core.model.common.identifier

import com.fastertwitterml.jackson.core.JsonGenerator
import com.fastertwitterml.jackson.databind.JsonSerializer
import com.fastertwitterml.jackson.databind.SerializerProvider

private[identifier] class ComponentIdentifierSerializer()
    etwittertends JsonSerializer[ComponentIdentifier] {

  private case class SerializableComponentIdentifier(
    identifier: String,
    sourceFile: String)

  override def serialize(
    componentIdentifier: ComponentIdentifier,
    gen: JsonGenerator,
    serializers: SerializerProvider
  ): Unit = serializers.defaultSerializeValue(
    SerializableComponentIdentifier(componentIdentifier.toString, componentIdentifier.file.value),
    gen)
}
