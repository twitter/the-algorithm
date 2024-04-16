package com.tw ter.product_m xer.core.model.common

 mport com.fasterxml.jackson.annotat on.JsonType nfo

@JsonType nfo( nclude = JsonType nfo.As.PROPERTY, use = JsonType nfo. d.NAME)
tra  Un versalNoun[+T] extends Equals {
  def  d: T
}
