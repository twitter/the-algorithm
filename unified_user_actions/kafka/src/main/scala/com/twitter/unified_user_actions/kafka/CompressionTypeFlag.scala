package com.twitter.unified_user_actions.kafka

import com.twitter.app.Flaggable
import org.apache.kafka.common.record.CompressionType

case class CompressionTypeFlag(compressionType: CompressionType)

object CompressionTypeFlag {

  def fromString(s: String): CompressionType = s.toLowerCase match {
    case "lz4" => CompressionType.LZ4
    case "snappy" => CompressionType.SNAPPY
    case "gzip" => CompressionType.GZIP
    case "zstd" => CompressionType.ZSTD
    case _ => CompressionType.NONE
  }

  implicit val flaggable: Flaggable[CompressionTypeFlag] =
    Flaggable.mandatory(s => CompressionTypeFlag(fromString(s)))
}
