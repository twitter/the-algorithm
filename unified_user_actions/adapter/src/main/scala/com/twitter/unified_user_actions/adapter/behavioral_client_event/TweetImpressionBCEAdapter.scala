package com.twitter.unified_user_actions.adapter.behavioral_client_event

import com.twitter.client.behavioral_event.action.impress.latest.thriftscala.Impress
import com.twitter.client_event_entities.serverside_context_key.latest.thriftscala.FlattenedServersideContextKey
import com.twitter.storage.behavioral_event.thriftscala.FlattenedEventLog
import com.twitter.unified_user_actions.thriftscala.ActionType
import com.twitter.unified_user_actions.thriftscala.ClientTweetV2Impression
import com.twitter.unified_user_actions.thriftscala.Item
import com.twitter.unified_user_actions.thriftscala.ProductSurface
import com.twitter.unified_user_actions.thriftscala.TweetActionInfo
import com.twitter.unified_user_actions.thriftscala.TweetInfo
import com.twitter.unified_user_actions.thriftscala.UnifiedUserAction

object TweetImpressionBCEAdapter {
  val TweetDetails = new TweetImpressionBCEAdapter(ActionType.ClientTweetV2Impression)
  val FullscreenVideo = new TweetImpressionBCEAdapter(
    ActionType.ClientTweetVideoFullscreenV2Impression)
  val FullscreenImage = new TweetImpressionBCEAdapter(
    ActionType.ClientTweetImageFullscreenV2Impression)
}

class TweetImpressionBCEAdapter(actionType: ActionType) extends ImpressionBCEAdapter {
  override type ImpressedItem = Item.TweetInfo

  override def toUUA(e: FlattenedEventLog): Seq[UnifiedUserAction] =
    (actionType, e.v2Impress, e.v1TweetIds, e.v1BreadcrumbTweetIds) match {
      case (ActionType.ClientTweetV2Impression, Some(v2Impress), Some(v1TweetIds), _) =>
        toUUAEvents(e, v2Impress, v1TweetIds)
      case (
            ActionType.ClientTweetVideoFullscreenV2Impression,
            Some(v2Impress),
            _,
            Some(v1BreadcrumbTweetIds)) =>
        toUUAEvents(e, v2Impress, v1BreadcrumbTweetIds)
      case (
            ActionType.ClientTweetImageFullscreenV2Impression,
            Some(v2Impress),
            _,
            Some(v1BreadcrumbTweetIds)) =>
        toUUAEvents(e, v2Impress, v1BreadcrumbTweetIds)
      case _ => Nil
    }

  private def toUUAEvents(
    e: FlattenedEventLog,
    v2Impress: Impress,
    v1TweetIds: Seq[FlattenedServersideContextKey]
  ): Seq[UnifiedUserAction] =
    v1TweetIds.map { tweet =>
      getUnifiedUserAction(
        event = e,
        actionType = actionType,
        item = getImpressedItem(tweet, v2Impress),
        productSurface = getProductSurfaceRelated.productSurface,
        productSurfaceInfo = getProductSurfaceRelated.productSurfaceInfo
      )
    }

  override def getImpressedItem(
    context: FlattenedServersideContextKey,
    impression: Impress
  ): ImpressedItem =
    Item.TweetInfo(
      TweetInfo(
        actionTweetId = context.serversideContextId.toLong,
        tweetActionInfo = Some(
          TweetActionInfo.ClientTweetV2Impression(
            ClientTweetV2Impression(
              impressStartTimestampMs = getImpressedStartTimestamp(impression),
              impressEndTimestampMs = getImpressedEndTimestamp(impression),
              sourceComponent = getImpressedUISourceComponent(context)
            )
          ))
      ))

  private def getProductSurfaceRelated: ProductSurfaceRelated =
    actionType match {
      case ActionType.ClientTweetV2Impression =>
        ProductSurfaceRelated(
          productSurface = Some(ProductSurface.TweetDetailsPage),
          productSurfaceInfo = None)
      case _ => ProductSurfaceRelated(productSurface = None, productSurfaceInfo = None)
    }
}
