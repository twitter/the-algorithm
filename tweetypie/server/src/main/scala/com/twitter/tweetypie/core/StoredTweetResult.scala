package com.tw ter.t etyp e.core

sealed tra  StoredT etResult {
  def canHydrate: Boolean
}

object StoredT etResult {
  sealed tra  Error
  object Error {
    case object Corrupt extends Error
    case object ScrubbedF eldsPresent extends Error
    case object F eldsM ss ngOr nval d extends Error
    case object ShouldBeHardDeleted extends Error
  }

  case class Present(errors: Seq[Error], canHydrate: Boolean) extends StoredT etResult

  case class HardDeleted(softDeletedAtMsec: Long, hardDeletedAtMsec: Long)
      extends StoredT etResult {
    overr de def canHydrate: Boolean = false
  }

  case class SoftDeleted(softDeletedAtMsec: Long, errors: Seq[Error], canHydrate: Boolean)
      extends StoredT etResult

  case class BounceDeleted(deletedAtMsec: Long, errors: Seq[Error], canHydrate: Boolean)
      extends StoredT etResult

  case class Undeleted(undeletedAtMsec: Long, errors: Seq[Error], canHydrate: Boolean)
      extends StoredT etResult

  case class ForceAdded(addedAtMsec: Long, errors: Seq[Error], canHydrate: Boolean)
      extends StoredT etResult

  case class Fa led(errors: Seq[Error]) extends StoredT etResult {
    overr de def canHydrate: Boolean = false
  }

  object NotFound extends StoredT etResult {
    overr de def canHydrate: Boolean = false
  }
}
