package com.tw ter.t etyp e.core

 mport com.tw ter.servo.ut l.Except onCategor zer
 mport com.tw ter.spam.rtf.thr ftscala.F lteredReason
 mport scala.ut l.control.NoStackTrace

sealed tra  F lteredState

object F lteredState {

  /**
   * T  t et ex sts and t  f ltered state was due to bus ness rules
   * (e.g. safety label f lter ng, or protected accounts). Note that
   * Suppress and Unava lable can both have a F lteredReason.
   */
  sealed tra  HasF lteredReason extends F lteredState {
    def f lteredReason: F lteredReason
  }

  /**
   * T  only F lteredState that  s not an except on.    nd cates that
   * t  t et should be returned along w h a suppress reason. T   s
   * so t  s known as "soft f lter ng". Only used by VF.
   */
  case class Suppress(f lteredReason: F lteredReason) extends F lteredState w h HasF lteredReason

  /**
   * F lteredStates that cause t  t et to be unava lable are modeled
   * as an [[Except on]]. (Suppressed f ltered states cannot be used as
   * except ons because t y should not prevent t  t et from be ng
   * returned.) T   s so t  s known as "hard f lter ng".
   */
  sealed abstract class Unava lable extends Except on w h F lteredState w h NoStackTrace

  object Unava lable {
    // Used for T ets that should be dropped because of VF rules
    case class Drop(f lteredReason: F lteredReason) extends Unava lable w h HasF lteredReason

    // Used for T ets that should be dropped and replaced w h t  r prev ew because of VF rules
    case class Prev ew(f lteredReason: F lteredReason) extends Unava lable w h HasF lteredReason

    // Used for T ets that should be dropped because of T etyp e bus ness log c
    case object DropUnspec f ed extends Unava lable w h HasF lteredReason {
      val f lteredReason: F lteredReason = F lteredReason.Unspec f edReason(true)
    }

    // Represents a Deleted t et (NotFound  s represented w h st ch.NotFound)
    case object T etDeleted extends Unava lable

    // Represents a Deleted t et that v olated Tw ter Rules (see go/bounced-t et)
    case object BounceDeleted extends Unava lable

    // Represents both Deleted and NotFound s ce t ets
    case class S ceT etNotFound(deleted: Boolean) extends Unava lable

    // Used by t  [[ReportedT etF lter]] to s gnal that a T et has a "reported" perspect ve from TLS
    case object Reported extends Unava lable w h HasF lteredReason {
      val f lteredReason: F lteredReason = F lteredReason.ReportedT et(true)
    }

    // T  follow ng objects are used by t  [[UserRepos ory]] to s gnal problems w h t  T et author
    object Author {
      case object NotFound extends Unava lable

      case object Deact vated extends Unava lable w h HasF lteredReason {
        val f lteredReason: F lteredReason = F lteredReason.Author sDeact vated(true)
      }

      case object Offboarded extends Unava lable w h HasF lteredReason {
        val f lteredReason: F lteredReason = F lteredReason.AuthorAccount s nact ve(true)
      }

      case object Suspended extends Unava lable w h HasF lteredReason {
        val f lteredReason: F lteredReason = F lteredReason.Author sSuspended(true)
      }

      case object Protected extends Unava lable w h HasF lteredReason {
        val f lteredReason: F lteredReason = F lteredReason.Author sProtected(true)
      }

      case object Unsafe extends Unava lable w h HasF lteredReason {
        val f lteredReason: F lteredReason = F lteredReason.Author sUnsafe(true)
      }
    }
  }

  /**
   * Creates a new Except onCategor zer wh ch returns an empty category for any
   * Unava lable value, and forwards to `underly ng` for anyth ng else.
   */
  def  gnor ngCategor zer(underly ng: Except onCategor zer): Except onCategor zer =
    Except onCategor zer {
      case _: Unava lable => Set.empty
      case t => underly ng(t)
    }
}
