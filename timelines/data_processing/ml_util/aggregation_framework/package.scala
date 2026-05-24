package com.twitter.timelines.data_processing.ml_util

import com.twitter.ml.api.DataRecord

package object aggregation_framework {
  object AggregateType extends Enumeration {
    type AggregateType = Value
    val User, UserAuthor, UserEngager, UserMention, UserRequestHour, UserRequestDow,
      UserOriginalAuthor, UserList, UserTopic, UserInferredTopic, UserMediaUnderstandingAnnotation =
      Value
  }

  type AggregateUserEntityKey = (Long, AggregateType.Value, Option[Long])

  case class MergedRecordsDescriptor(
    userId: Long,
    keyedRecords: Map[AggregateType.Value, Option[KeyedRecord]],
    keyedRecordMaps: Map[AggregateType.Value, Option[KeyedRecordMap]])
}
