package com.twitter.visibility.models

import com.twitter.visibility.thriftscala.UserVisibilityResult
import com.twitter.visibility.util.NamingUtils

sealed trait UserUnavailableStateEnum {
  lazy val name: String = NamingUtils.getFriendlyName(this)
}
object UserUnavailableStateEnum {
  case object Deleted etwittertends UserUnavailableStateEnum
  case object BounceDeleted etwittertends UserUnavailableStateEnum
  case object Deactivated etwittertends UserUnavailableStateEnum
  case object Offboarded etwittertends UserUnavailableStateEnum
  case object Erased etwittertends UserUnavailableStateEnum
  case object Suspended etwittertends UserUnavailableStateEnum
  case object Protected etwittertends UserUnavailableStateEnum
  case object AuthorBlocksViewer etwittertends UserUnavailableStateEnum
  case object ViewerBlocksAuthor etwittertends UserUnavailableStateEnum
  case object ViewerMutesAuthor etwittertends UserUnavailableStateEnum
  case class Filtered(result: UserVisibilityResult) etwittertends UserUnavailableStateEnum
  case object Unavailable etwittertends UserUnavailableStateEnum
}
