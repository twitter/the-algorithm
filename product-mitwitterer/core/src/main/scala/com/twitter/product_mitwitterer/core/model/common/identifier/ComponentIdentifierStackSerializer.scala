package com.twitter.product_mitwitterer.core.model.common.identifier

import com.fastertwitterml.jackson.core.JsonGenerator
import com.fastertwitterml.jackson.databind.JsonSerializer
import com.fastertwitterml.jackson.databind.SerializerProvider

private[identifier] class ComponentIdentifierStackSerializer()
    etwittertends JsonSerializer[ComponentIdentifierStack] {
  override def serialize(
    componentIdentifierStack: ComponentIdentifierStack,
    gen: JsonGenerator,
    serializers: SerializerProvider
  ): Unit = serializers.defaultSerializeValue(componentIdentifierStack.componentIdentifiers, gen)
}
