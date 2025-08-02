package com.twitter.product_mitwitterer.component_library.model.candidate.trends_events

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.event.EventSummaryDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.trend.GroupedTrend
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.DisclosureType

/**
 * An [[UnifiedTrendEventCandidate]] represents a piece of Event or Trend content.
 * The Event and Trend candidate are represented by different types of keys that Event has a Long
 * eventId while Trend has a String trendName.
 */
sealed trait UnifiedTrendEventCandidate[+T] etwittertends UniversalNoun[T]

final class UnifiedEventCandidate private (
  override val id: Long)
    etwittertends UnifiedTrendEventCandidate[Long] {

  override def canEqual(that: Any): Boolean = this.isInstanceOf[UnifiedEventCandidate]

  override def equals(that: Any): Boolean = {
    that match {
      case candidate: UnifiedEventCandidate =>
        (
          (this eq candidate)
            || ((hashCode == candidate.hashCode)
              && (id == candidate.id))
        )
      case _ => false
    }
  }

  override val hashCode: Int = id.##
}

object UnifiedEventCandidate {
  def apply(id: Long): UnifiedEventCandidate = new UnifiedEventCandidate(id)
}

/**
 * Tetwittert description of an Event. Usually this is etwittertracted from curated Event metadata
 */
object EventTitleFeature etwittertends Feature[UnifiedEventCandidate, String]

/**
 * Display type of an Event. This will be used for client to differentiate if this Event will be
 * displayed as a normal cell, a hero, etc.
 */
object EventDisplayType etwittertends Feature[UnifiedEventCandidate, EventSummaryDisplayType]

/**
 * URL that servces as the landing page of an Event
 */
object EventUrl etwittertends Feature[UnifiedEventCandidate, Url]

/**
 * Use to render an Event cell's editorial image
 */
object EventImage etwittertends Feature[UnifiedEventCandidate, Option[ImageVariant]]

/**
 * Localized time string like "LIVE" or "Last Night" that is used to render the Event cell
 */
object EventTimeString etwittertends Feature[UnifiedEventCandidate, Option[String]]

final class UnifiedTrendCandidate private (
  override val id: String)
    etwittertends UnifiedTrendEventCandidate[String] {

  override def canEqual(that: Any): Boolean = this.isInstanceOf[UnifiedTrendCandidate]

  override def equals(that: Any): Boolean = {
    that match {
      case candidate: UnifiedTrendCandidate =>
        (
          (this eq candidate)
            || ((hashCode == candidate.hashCode)
              && (id == candidate.id))
        )
      case _ => false
    }
  }

  override val hashCode: Int = id.##
}

object UnifiedTrendCandidate {
  def apply(id: String): UnifiedTrendCandidate = new UnifiedTrendCandidate(id)
}

object TrendNormalizedTrendName etwittertends Feature[UnifiedTrendCandidate, String]

object TrendTrendName etwittertends Feature[UnifiedTrendCandidate, String]

object TrendUrl etwittertends Feature[UnifiedTrendCandidate, Url]

object TrendDescription etwittertends Feature[UnifiedTrendCandidate, Option[String]]

object TrendTweetCount etwittertends Feature[UnifiedTrendCandidate, Option[Int]]

object TrendDomainContetwittert etwittertends Feature[UnifiedTrendCandidate, Option[String]]

object TrendGroupedTrends etwittertends Feature[UnifiedTrendCandidate, Option[Seq[GroupedTrend]]]

object PromotedTrendNameFeature etwittertends Feature[UnifiedTrendCandidate, Option[String]]

object PromotedTrendDescriptionFeature etwittertends Feature[UnifiedTrendCandidate, Option[String]]

object PromotedTrendAdvertiserNameFeature etwittertends Feature[UnifiedTrendCandidate, Option[String]]

object PromotedTrendIdFeature etwittertends Feature[UnifiedTrendCandidate, Option[Long]]

object PromotedTrendDisclosureTypeFeature
    etwittertends Feature[UnifiedTrendCandidate, Option[DisclosureType]]

object PromotedTrendImpressionIdFeature etwittertends Feature[UnifiedTrendCandidate, Option[String]]
