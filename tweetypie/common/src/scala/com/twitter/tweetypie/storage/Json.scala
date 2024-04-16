package com.tw ter.t etyp e.storage

 mport com.fasterxml.jackson.datab nd.ObjectMapper
 mport com.fasterxml.jackson.module.scala.DefaultScalaModule

object Json {
  val T  stampKey = "t  stamp"
  val SoftDeleteT  stampKey = "softdelete_t  stamp"

  pr vate val mapper = new ObjectMapper
  mapper.reg sterModule(DefaultScalaModule)

  def encode(m: Map[Str ng, Any]): Array[Byte] = mapper.wr eValueAsBytes(m)

  def decode(arr: Array[Byte]): Map[Str ng, Any] =
    mapper.readValue[Map[Str ng, Any]](arr, classOf[Map[Str ng, Any]])
}
