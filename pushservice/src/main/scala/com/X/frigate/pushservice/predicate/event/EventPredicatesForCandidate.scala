package com.X.frigate.pushservice.predicate.event

import com.X.conversions.DurationOps._
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.EventCandidate
import com.X.frigate.common.base.TargetInfo
import com.X.frigate.common.base.TargetUser
import com.X.frigate.common.candidate.FrigateHistory
import com.X.frigate.common.history.RecItems
import com.X.frigate.magic_events.thriftscala.Locale
import com.X.frigate.pushservice.model.MagicFanoutEventHydratedCandidate
import com.X.frigate.pushservice.model.MagicFanoutEventPushCandidate
import com.X.frigate.pushservice.model.MagicFanoutNewsEventPushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.predicate.magic_fanout.MagicFanoutPredicatesUtil._
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.util.Future

object EventPredicatesForCandidate {
  def hasTitle(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[MagicFanoutEventHydratedCandidate] = {
    val name = "event_title_available"
    val scopedStatsReceiver = statsReceiver.scope(s"predicate_$name")
    Predicate
      .fromAsync { candidate: MagicFanoutEventHydratedCandidate =>
        candidate.eventTitleFut.map(_.nonEmpty)
      }
      .withStats(scopedStatsReceiver)
      .withName(name)
  }

  def isNotDuplicateWithEventId(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[MagicFanoutEventHydratedCandidate] = {
    val name = "duplicate_event_id"
    Predicate
      .fromAsync { candidate: MagicFanoutEventHydratedCandidate =>
        val useRelaxedFatigueLengthFut: Future[Boolean] =
          candidate match {
            case mfNewsEvent: MagicFanoutNewsEventPushCandidate =>
              mfNewsEvent.isHighPriorityEvent
            case _ => Future.value(false)
          }
        Future.join(candidate.target.history, useRelaxedFatigueLengthFut).map {
          case (history, useRelaxedFatigueLength) =>
            val filteredNotifications = if (useRelaxedFatigueLength) {
              val relaxedFatigueInterval =
                candidate.target
                  .params(
                    PushFeatureSwitchParams.MagicFanoutRelaxedEventIdFatigueIntervalInHours).hours
              history.notificationMap.filterKeys { time =>
                time.untilNow <= relaxedFatigueInterval
              }.values
            } else history.notificationMap.values
            !RecItems(filteredNotifications.toSeq).events.exists(_.eventId == candidate.eventId)
        }
      }
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }

  def isNotDuplicateWithEventIdForCandidate[
    T <: TargetUser with FrigateHistory,
    Cand <: EventCandidate with TargetInfo[T]
  ](
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[Cand] = {
    val name = "is_not_duplicate_event"
    Predicate
      .fromAsync { candidate: Cand =>
        candidate.target.pushRecItems.map {
          !_.events.map(_.eventId).contains(candidate.eventId)
        }
      }
      .withStats(statsReceiver.scope(name))
      .withName(name)
  }

  def accountCountryPredicateWithAllowlist(
    implicit stats: StatsReceiver
  ): NamedPredicate[MagicFanoutEventPushCandidate] = {
    val name = "account_country_predicate_with_allowlist"
    val scopedStats = stats.scope(name)

    val skipPredicate = Predicate
      .from { candidate: MagicFanoutEventPushCandidate =>
        candidate.target.params(PushFeatureSwitchParams.MagicFanoutSkipAccountCountryPredicate)
      }
      .withStats(stats.scope("skip_account_country_predicate_mf"))
      .withName("skip_account_country_predicate_mf")

    val excludeEventFromAccountCountryPredicateFiltering = Predicate
      .from { candidate: MagicFanoutEventPushCandidate =>
        val eventId = candidate.eventId
        val target = candidate.target
        target
          .params(PushFeatureSwitchParams.MagicFanoutEventAllowlistToSkipAccountCountryPredicate)
          .exists(eventId.equals)
      }
      .withStats(stats.scope("exclude_event_from_account_country_predicate_filtering"))
      .withName("exclude_event_from_account_country_predicate_filtering")

    skipPredicate
      .or(excludeEventFromAccountCountryPredicateFiltering)
      .or(accountCountryPredicate)
      .withStats(scopedStats)
      .withName(name)
  }

  /**
   * Check if user's country is targeted
   * @param stats
   */
  def accountCountryPredicate(
    implicit stats: StatsReceiver
  ): NamedPredicate[MagicFanoutEventPushCandidate] = {
    val name = "account_country_predicate"
    val scopedStatsReceiver = stats.scope(s"predicate_$name")
    val internationalLocalePassedCounter =
      scopedStatsReceiver.counter("international_locale_passed")
    val internationalLocaleFilteredCounter =
      scopedStatsReceiver.counter("international_locale_filtered")
    Predicate
      .fromAsync { candidate: MagicFanoutEventPushCandidate =>
        candidate.target.countryCode.map {
          case Some(countryCode) =>
            val denyListedCountryCodes: Seq[String] =
              if (candidate.commonRecType == CommonRecommendationType.MagicFanoutNewsEvent) {
                candidate.target
                  .params(PushFeatureSwitchParams.MagicFanoutDenyListedCountries)
              } else if (candidate.commonRecType == CommonRecommendationType.MagicFanoutSportsEvent) {
                candidate.target
                  .params(PushFeatureSwitchParams.MagicFanoutSportsEventDenyListedCountries)
              } else Seq()
            val eventCountries =
              candidate.newsForYouMetadata
                .flatMap(_.locales).getOrElse(Seq.empty[Locale]).flatMap(_.country)
            if (isInCountryList(countryCode, eventCountries)
              && !isInCountryList(countryCode, denyListedCountryCodes)) {
              internationalLocalePassedCounter.incr()
              true
            } else {
              internationalLocaleFilteredCounter.incr()
              false
            }
          case _ => false
        }
      }
      .withStats(scopedStatsReceiver)
      .withName(name)
  }
}
