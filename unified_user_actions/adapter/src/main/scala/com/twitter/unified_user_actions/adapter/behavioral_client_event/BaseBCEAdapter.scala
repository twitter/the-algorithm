package com.twitter.unified_user_actions.adapter.behavioral_client_event

import com.twitter.client_event_entities.serverside_context_key.latest.thriftscala.FlattenedServersideContextKey
import com.twitter.storage.behavioral_event.thriftscala.EventLogContext
import com.twitter.storage.behavioral_event.thriftscala.FlattenedEventLog
import com.twitter.unified_user_actions.adapter.common.AdapterUtils
import com.twitter.unified_user_actions.thriftscala.ActionType
import com.twitter.unified_user_actions.thriftscala.BreadcrumbTweet
import com.twitter.unified_user_actions.thriftscala.ClientEventNamespace
import com.twitter.unified_user_actions.thriftscala.EventMetadata
import com.twitter.unified_user_actions.thriftscala.Item
import com.twitter.unified_user_actions.thriftscala.ProductSurface
import com.twitter.unified_user_actions.thriftscala.ProductSurfaceInfo
import com.twitter.unified_user_actions.thriftscala.SourceLineage
import com.twitter.unified_user_actions.thriftscala.UnifiedUserAction
import com.twitter.unified_user_actions.thriftscala.UserIdentifier

case class ProductSurfaceRelated(
  productSurface: Option[ProductSurface],
  productSurfaceInfo: Option[ProductSurfaceInfo])

trait BaseBCEAdapter {
  def toUUA(e: FlattenedEventLog): Seq[UnifiedUserAction]

  protected def getUserIdentifier(c: EventLogContext): UserIdentifier =
    UserIdentifier(
      userId = c.userId,
      guestIdMarketing = c.guestIdMarketing
    )

  protected def getEventMetadata(e: FlattenedEventLog): EventMetadata =
    EventMetadata(
      sourceLineage = SourceLineage.BehavioralClientEvents,
      sourceTimestampMs =
        e.context.driftAdjustedEventCreatedAtMs.getOrElse(e.context.eventCreatedAtMs),
      receivedTimestampMs = AdapterUtils.currentTimestampMs,
      // Client UI language or from Gizmoduck which is what user set in Twitter App.
      // Please see more at https://sourcegraph.twitter.biz/git.twitter.biz/source/-/blob/finatra-internal/international/src/main/scala/com/twitter/finatra/international/LanguageIdentifier.scala
      // The format should be ISO 639-1.
      language = e.context.languageCode.map(AdapterUtils.normalizeLanguageCode),
      // Country code could be IP address (geoduck) or User registration country (gizmoduck) and the former takes precedence.
      // We don’t know exactly which one is applied, unfortunately,
      // see https://sourcegraph.twitter.biz/git.twitter.biz/source/-/blob/finatra-internal/international/src/main/scala/com/twitter/finatra/international/CountryIdentifier.scala
      // The format should be ISO_3166-1_alpha-2.
      countryCode = e.context.countryCode.map(AdapterUtils.normalizeCountryCode),
      clientAppId = e.context.clientApplicationId,
      clientVersion = e.context.clientVersion,
      clientPlatform = e.context.clientPlatform,
      viewHierarchy = e.v1ViewTypeHierarchy,
      clientEventNamespace = Some(
        ClientEventNamespace(
          page = e.page,
          section = e.section,
          element = e.element,
          action = e.actionName,
          subsection = e.subsection
        )),
      breadcrumbViews = e.v1BreadcrumbViewTypeHierarchy,
      breadcrumbTweets = e.v1BreadcrumbTweetIds.map { breadcrumbs =>
        breadcrumbs.map { breadcrumb =>
          BreadcrumbTweet(
            tweetId = breadcrumb.serversideContextId.toLong,
            sourceComponent = breadcrumb.sourceComponent)
        }
      }
    )

  protected def getBreadcrumbTweetIds(
    breadcrumbTweetIds: Option[Seq[FlattenedServersideContextKey]]
  ): Seq[BreadcrumbTweet] =
    breadcrumbTweetIds
      .getOrElse(Nil).map(breadcrumb => {
        BreadcrumbTweet(
          tweetId = breadcrumb.serversideContextId.toLong,
          sourceComponent = breadcrumb.sourceComponent)
      })

  protected def getBreadcrumbViews(breadcrumbView: Option[Seq[String]]): Seq[String] =
    breadcrumbView.getOrElse(Nil)

  protected def getUnifiedUserAction(
    event: FlattenedEventLog,
    actionType: ActionType,
    item: Item,
    productSurface: Option[ProductSurface] = None,
    productSurfaceInfo: Option[ProductSurfaceInfo] = None
  ): UnifiedUserAction =
    UnifiedUserAction(
      userIdentifier = getUserIdentifier(event.context),
      actionType = actionType,
      item = item,
      eventMetadata = getEventMetadata(event),
      productSurface = productSurface,
      productSurfaceInfo = productSurfaceInfo
    )
}
