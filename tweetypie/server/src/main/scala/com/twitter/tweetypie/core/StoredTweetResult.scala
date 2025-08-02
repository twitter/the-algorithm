package com.twitter.tweetypie.core

sealed trait StoredTweetResult {
  def canHydrate: Boolean
}

object StoredTweetResult {
  sealed trait Error
  object Error {
    case object Corrupt etwittertends Error
    case object ScrubbedFieldsPresent etwittertends Error
    case object FieldsMissingOrInvalid etwittertends Error
    case object ShouldBeHardDeleted etwittertends Error
  }

  case class Present(errors: Seq[Error], canHydrate: Boolean) etwittertends StoredTweetResult

  case class HardDeleted(softDeletedAtMsec: Long, hardDeletedAtMsec: Long)
      etwittertends StoredTweetResult {
    override def canHydrate: Boolean = false
  }

  case class SoftDeleted(softDeletedAtMsec: Long, errors: Seq[Error], canHydrate: Boolean)
      etwittertends StoredTweetResult

  case class BounceDeleted(deletedAtMsec: Long, errors: Seq[Error], canHydrate: Boolean)
      etwittertends StoredTweetResult

  case class Undeleted(undeletedAtMsec: Long, errors: Seq[Error], canHydrate: Boolean)
      etwittertends StoredTweetResult

  case class ForceAdded(addedAtMsec: Long, errors: Seq[Error], canHydrate: Boolean)
      etwittertends StoredTweetResult

  case class Failed(errors: Seq[Error]) etwittertends StoredTweetResult {
    override def canHydrate: Boolean = false
  }

  object NotFound etwittertends StoredTweetResult {
    override def canHydrate: Boolean = false
  }
}
