package com.tw ter.product_m xer.component_l brary.model.cand date.trends_events

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.event.EventSummaryD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.trend.GroupedTrend
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageVar ant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.D sclosureType

/**
 * An [[Un f edTrendEventCand date]] represents a p ece of Event or Trend content.
 * T  Event and Trend cand date are represented by d fferent types of keys that Event has a Long
 * event d wh le Trend has a Str ng trendNa .
 */
sealed tra  Un f edTrendEventCand date[+T] extends Un versalNoun[T]

f nal class Un f edEventCand date pr vate (
  overr de val  d: Long)
    extends Un f edTrendEventCand date[Long] {

  overr de def canEqual(that: Any): Boolean = t . s nstanceOf[Un f edEventCand date]

  overr de def equals(that: Any): Boolean = {
    that match {
      case cand date: Un f edEventCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode)
              && ( d == cand date. d))
        )
      case _ => false
    }
  }

  overr de val hashCode:  nt =  d.##
}

object Un f edEventCand date {
  def apply( d: Long): Un f edEventCand date = new Un f edEventCand date( d)
}

/**
 * Text descr pt on of an Event. Usually t   s extracted from curated Event  tadata
 */
object EventT leFeature extends Feature[Un f edEventCand date, Str ng]

/**
 * D splay type of an Event. T  w ll be used for cl ent to d fferent ate  f t  Event w ll be
 * d splayed as a normal cell, a  ro, etc.
 */
object EventD splayType extends Feature[Un f edEventCand date, EventSummaryD splayType]

/**
 * URL that servces as t  land ng page of an Event
 */
object EventUrl extends Feature[Un f edEventCand date, Url]

/**
 * Use to render an Event cell's ed or al  mage
 */
object Event mage extends Feature[Un f edEventCand date, Opt on[ mageVar ant]]

/**
 * Local zed t   str ng l ke "L VE" or "Last N ght" that  s used to render t  Event cell
 */
object EventT  Str ng extends Feature[Un f edEventCand date, Opt on[Str ng]]

f nal class Un f edTrendCand date pr vate (
  overr de val  d: Str ng)
    extends Un f edTrendEventCand date[Str ng] {

  overr de def canEqual(that: Any): Boolean = t . s nstanceOf[Un f edTrendCand date]

  overr de def equals(that: Any): Boolean = {
    that match {
      case cand date: Un f edTrendCand date =>
        (
          (t  eq cand date)
            || ((hashCode == cand date.hashCode)
              && ( d == cand date. d))
        )
      case _ => false
    }
  }

  overr de val hashCode:  nt =  d.##
}

object Un f edTrendCand date {
  def apply( d: Str ng): Un f edTrendCand date = new Un f edTrendCand date( d)
}

object TrendNormal zedTrendNa  extends Feature[Un f edTrendCand date, Str ng]

object TrendTrendNa  extends Feature[Un f edTrendCand date, Str ng]

object TrendUrl extends Feature[Un f edTrendCand date, Url]

object TrendDescr pt on extends Feature[Un f edTrendCand date, Opt on[Str ng]]

object TrendT etCount extends Feature[Un f edTrendCand date, Opt on[ nt]]

object TrendDoma nContext extends Feature[Un f edTrendCand date, Opt on[Str ng]]

object TrendGroupedTrends extends Feature[Un f edTrendCand date, Opt on[Seq[GroupedTrend]]]

object PromotedTrendNa Feature extends Feature[Un f edTrendCand date, Opt on[Str ng]]

object PromotedTrendDescr pt onFeature extends Feature[Un f edTrendCand date, Opt on[Str ng]]

object PromotedTrendAdvert serNa Feature extends Feature[Un f edTrendCand date, Opt on[Str ng]]

object PromotedTrend dFeature extends Feature[Un f edTrendCand date, Opt on[Long]]

object PromotedTrendD sclosureTypeFeature
    extends Feature[Un f edTrendCand date, Opt on[D sclosureType]]

object PromotedTrend mpress on dFeature extends Feature[Un f edTrendCand date, Opt on[Str ng]]
