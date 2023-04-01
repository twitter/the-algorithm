package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

trait HasClientEventInfo {
  def clientEventInfo: Option[ClientEventInfo]
}

/**
 * Information used to build Client Events
 * @see [[http://go/client-events]]
 */
case class ClientEventInfo(
  component: Option[String],
  element: Option[String],
  details: Option[ClientEventDetails],
  action: Option[String],
  entityToken: Option[String])

/**
 * Additional client events fields
 *
 * @note if a field from [[http://go/client_app.thrift]] is needed but is not here
 *       contact the `#product-mixer` team to have it added.
 */
case class ClientEventDetails(
  conversationDetails: Option[ConversationDetails],
  timelinesDetails: Option[TimelinesDetails],
  articleDetails: Option[ArticleDetails],
  liveEventDetails: Option[LiveEventDetails],
  commerceDetails: Option[CommerceDetails])
