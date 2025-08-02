package com.twitter.tweetypie.storage

object Response {
  case class TweetResponse(
    tweetId: Long,
    overallResponse: TweetResponseCode,
    additionalFieldResponses: Option[Map[Short, FieldResponse]] = None)

  sealed trait TweetResponseCode

  object TweetResponseCode {
    object Success etwittertends TweetResponseCode
    object Partial etwittertends TweetResponseCode
    object Failure etwittertends TweetResponseCode
    object OverCapacity etwittertends TweetResponseCode
    object Deleted etwittertends TweetResponseCode
  }

  case class FieldResponse(code: FieldResponseCode, message: Option[String] = None)

  sealed trait FieldResponseCode

  object FieldResponseCode {
    object Success etwittertends FieldResponseCode
    object InvalidRequest etwittertends FieldResponseCode
    object ValueNotFound etwittertends FieldResponseCode
    object Timeout etwittertends FieldResponseCode
    object Error etwittertends FieldResponseCode
  }
}
