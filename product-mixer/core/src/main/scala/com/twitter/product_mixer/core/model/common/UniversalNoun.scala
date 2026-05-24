package com.twitter.product_mixer.core.model.common

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME)
trait UniversalNoun[+T] extends Equals {
  def id: T
}
