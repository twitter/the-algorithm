package com.X.frigate.pushservice.predicate

import com.X.abdecider.GuestRecipient
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.common.predicate.{FatiguePredicate => CommonFatiguePredicate}
import com.X.hermit.predicate.NamedPredicate
import com.X.conversions.DurationOps._
import com.X.frigate.common.util.Experiments.LoggedOutRecsHoldback
import com.X.hermit.predicate.Predicate

object LoggedOutTargetPredicates {

  def targetFatiguePredicate[T <: Target](
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[T] = {
    val name = "logged_out_target_min_duration_since_push"
    CommonFatiguePredicate
      .magicRecsPushTargetFatiguePredicate(
        minInterval = 24.hours,
        maxInInterval = 1
      ).withStats(statsReceiver.scope(name))
      .withName(name)
  }

  def loggedOutRecsHoldbackPredicate[T <: Target](
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[T] = {
    val name = "logged_out_recs_holdback"
    val guestIdNotFoundCounter = statsReceiver.scope("logged_out").counter("guest_id_not_found")
    val controlBucketCounter = statsReceiver.scope("logged_out").counter("holdback_control")
    val allowTrafficCounter = statsReceiver.scope("logged_out").counter("allow_traffic")
    Predicate.from { target: T =>
      val guestId = target.targetGuestId match {
        case Some(guest) => guest
        case _ =>
          guestIdNotFoundCounter.incr()
          throw new IllegalStateException("guest_id_not_found")
      }
      target.abDecider
        .bucket(LoggedOutRecsHoldback.exptName, GuestRecipient(guestId)).map(_.name) match {
        case Some(LoggedOutRecsHoldback.control) =>
          controlBucketCounter.incr()
          false
        case _ =>
          allowTrafficCounter.incr()
          true
      }
    }
  }
}
