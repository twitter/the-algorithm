package com.twitter.product_mitwitterer.core.model.common

import com.fastertwitterml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME)
trait UniversalNoun[+T] etwittertends Equals {
  def id: T
}
