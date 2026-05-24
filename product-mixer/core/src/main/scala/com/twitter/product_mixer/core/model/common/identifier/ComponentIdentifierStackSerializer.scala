package com.twitter.product_mixer.core.model.common.identifier

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

private[identifier] class ComponentIdentifierStackSerializer()
    extends JsonSerializer[ComponentIdentifierStack] {
  override def serialize(
    componentIdentifierStack: ComponentIdentifierStack,
    gen: JsonGenerator,
    serializers: SerializerProvider
  ): Unit = serializers.defaultSerializeValue(componentIdentifierStack.componentIdentifiers, gen)
}
