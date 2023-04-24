package com.twitter.unified_user_actions.adapter.behavioral_client_event

import com.twitter.client.behavioral_event.action.impress.latest.thriftscala.Impress
import com.twitter.client_event_entities.serverside_context_key.latest.thriftscala.FlattenedServersideContextKey
import com.twitter.unified_user_actions.thriftscala.Item

trait ImpressionBCEAdapter extends BaseBCEAdapter {
  type ImpressedItem <: Item

  def getImpressedItem(
    context: FlattenedServersideContextKey,
    impression: Impress
  ): ImpressedItem

  /**
   * The start time of an impression in milliseconds since epoch. In BCE, the impression
   * tracking clock will start immediately after the page is visible with no initial delay.
   */
  def getImpressedStartTimestamp(impression: Impress): Long =
    impression.visibilityPctDwellStartMs

  /**
   * The end time of an impression in milliseconds since epoch. In BCE, the impression
   * tracking clock will end before the user exit the page.
   */
  def getImpressedEndTimestamp(impression: Impress): Long =
    impression.visibilityPctDwellEndMs

  /**
   * The UI component that hosted the impressed item.
   */
  def getImpressedUISourceComponent(context: FlattenedServersideContextKey): String =
    context.sourceComponent
}
