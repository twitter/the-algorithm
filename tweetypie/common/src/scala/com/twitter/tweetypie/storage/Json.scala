package com.twitter.tweetypie.storage

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object Json {
  val TimestampKey = "timestamp"
  val SoftDeleteTimestampKey = "softdelete_timestamp"

  private val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def encode(m: Map[String, Any]): Array[Byte] = mapper.writeValueAsBytes(m)

  def decode(arr: Array[Byte]): Map[String, Any] =
    mapper.readValue[Map[String, Any]](arr, classOf[Map[String, Any]])
}
