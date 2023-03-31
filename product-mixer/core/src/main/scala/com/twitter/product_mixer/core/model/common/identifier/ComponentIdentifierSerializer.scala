package com.twitter.product_mixer.core.model.common.identifier

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

private[identifier] class ComponentIdentifierSerializer()
    extends JsonSerializer[ComponentIdentifier] {

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
