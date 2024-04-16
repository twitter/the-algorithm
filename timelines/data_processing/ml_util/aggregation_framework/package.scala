package com.tw ter.t  l nes.data_process ng.ml_ut l

 mport com.tw ter.ml.ap .DataRecord

package object aggregat on_fra work {
  object AggregateType extends Enu rat on {
    type AggregateType = Value
    val User, UserAuthor, UserEngager, User nt on, UserRequestH , UserRequestDow,
      UserOr g nalAuthor, UserL st, UserTop c, User nferredTop c, User d aUnderstand ngAnnotat on =
      Value
  }

  type AggregateUserEnt yKey = (Long, AggregateType.Value, Opt on[Long])

  case class  rgedRecordsDescr ptor(
    user d: Long,
    keyedRecords: Map[AggregateType.Value, Opt on[KeyedRecord]],
    keyedRecordMaps: Map[AggregateType.Value, Opt on[KeyedRecordMap]])
}
