package com.twitter.tweetypie.storage

object Response {
  case class TweetResponse(
    tweetId: Long,
    overallResponse: TweetResponseCode,
    additionalFieldResponses: Option[Map[Short, FieldResponse]] = None)

  sealed trait TweetResponseCode

  object TweetResponseCode {
    object Success extends TweetResponseCode
    object Partial extends TweetResponseCode
    object Failure extends TweetResponseCode
    object OverCapacity extends TweetResponseCode
    object Deleted extends TweetResponseCode
  }

  case class FieldResponse(code: FieldResponseCode, message: Option[String] = None)

  sealed trait FieldResponseCode

  object FieldResponseCode {
    object Success extends FieldResponseCode
    object InvalidRequest extends FieldResponseCode
    object ValueNotFound extends FieldResponseCode
    object Timeout extends FieldResponseCode
    object Error extends FieldResponseCode
  }
}
