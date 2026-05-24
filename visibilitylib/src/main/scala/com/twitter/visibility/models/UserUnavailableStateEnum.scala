package com.twitter.visibility.models

import com.twitter.visibility.thriftscala.UserVisibilityResult
import com.twitter.visibility.util.NamingUtils

sealed trait UserUnavailableStateEnum {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}
object UserUnavailableStateEnum {
  case object Deleted extends UserUnavailableStateEnum
  case object BounceDeleted extends UserUnavailableStateEnum
  case object Deactivated extends UserUnavailableStateEnum
  case object Offboarded extends UserUnavailableStateEnum
  case object Erased extends UserUnavailableStateEnum
  case object Suspended extends UserUnavailableStateEnum
  case object Protected extends UserUnavailableStateEnum
  case object AuthorBlocksViewer extends UserUnavailableStateEnum
  case object ViewerBlocksAuthor extends UserUnavailableStateEnum
  case object ViewerMutesAuthor extends UserUnavailableStateEnum
  case class Filtered(result: UserVisibilityResult) extends UserUnavailableStateEnum
  case object Unavailable extends UserUnavailableStateEnum
}
