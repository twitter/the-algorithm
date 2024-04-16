package com.tw ter.un f ed_user_act ons.kafka

 mport com.tw ter.app.Flaggable
 mport org.apac .kafka.common.record.Compress onType

case class Compress onTypeFlag(compress onType: Compress onType)

object Compress onTypeFlag {

  def fromStr ng(s: Str ng): Compress onType = s.toLo rCase match {
    case "lz4" => Compress onType.LZ4
    case "snappy" => Compress onType.SNAPPY
    case "gz p" => Compress onType.GZ P
    case "zstd" => Compress onType.ZSTD
    case _ => Compress onType.NONE
  }

   mpl c  val flaggable: Flaggable[Compress onTypeFlag] =
    Flaggable.mandatory(s => Compress onTypeFlag(fromStr ng(s)))
}
