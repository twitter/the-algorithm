package com.twitter.tweetypie
package store

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.twitter.finagle.tracing.Trace
import com.twitter.tweetypie.additionalfields.AdditionalFields
import com.twitter.tweetypie.client_id.ClientIdHelper
import com.twitter.tweetypie.media.Media.ownMedia

trait LogLensStore
    extends TweetStoreBase[LogLensStore]
    with InsertTweet.Store
    with DeleteTweet.Store
    with UndeleteTweet.Store
    with SetAdditionalFields.Store
    with DeleteAdditionalFields.Store
    with ScrubGeo.Store
    with Takedown.Store
    with UpdatePossiblySensitiveTweet.Store {
  def wrap(w: TweetStore.Wrap): LogLensStore =
    new TweetStoreWrapper(w, this)
      with LogLensStore
      with InsertTweet.StoreWrapper
      with DeleteTweet.StoreWrapper
      with UndeleteTweet.StoreWrapper
      with SetAdditionalFields.StoreWrapper
      with DeleteAdditionalFields.StoreWrapper
      with ScrubGeo.StoreWrapper
      with Takedown.StoreWrapper
      with UpdatePossiblySensitiveTweet.StoreWrapper
}

object LogLensStore {
  def apply(
    tweetCreationsLogger: Logger,
    tweetDeletionsLogger: Logger,
    tweetUndeletionsLogger: Logger,
    tweetUpdatesLogger: Logger,
    clientIdHelper: ClientIdHelper,
  ): LogLensStore =
    new LogLensStore {
      private[this] val mapper = new ObjectMapper().registerModule(DefaultScalaModule)

      private def logMessage(logger: Logger, data: (String, Any)*): Future[Unit] =
        Future {
          val allData = data ++ defaultData
          val msg = mapper.writeValueAsString(Map(allData: _*))
          logger.info(msg)
        }

      // Note: Longs are logged as strings to avoid JSON 53-bit numeric truncation
      private def defaultData: Seq[(String, Any)] = {
        val viewer = TwitterContext()
        Seq(
          "client_id" -> getOpt(clientIdHelper.effectiveClientId),
          "service_id" -> getOpt(clientIdHelper.effectiveServiceIdentifier),
          "trace_id" -> Trace.id.traceId.toString,
          "audit_ip" -> getOpt(viewer.flatMap(_.auditIp)),
          "application_id" -> getOpt(viewer.flatMap(_.clientApplicationId).map(_.toString)),
          "user_agent" -> getOpt(viewer.flatMap(_.userAgent)),
          "authenticated_user_id" -> getOpt(viewer.flatMap(_.authenticatedUserId).map(_.toString))
        )
      }

      private def getOpt[A](opt: Option[A]): Any =
        opt.getOrElse(null)

      override val insertTweet: FutureEffect[InsertTweet.Event] =
        FutureEffect[InsertTweet.Event] { event =>
          logMessage(
            tweetCreationsLogger,
            "type" -> "create_tweet",
            "tweet_id" -> event.tweet.id.toString,
            "user_id" -> event.user.id.toString,
            "source_tweet_id" -> getOpt(event.sourceTweet.map(_.id.toString)),
            "source_user_id" -> getOpt(event.sourceUser.map(_.id.toString)),
            "directed_at_user_id" -> getOpt(getDirectedAtUser(event.tweet).map(_.userId.toString)),
            "reply_to_tweet_id" -> getOpt(
              getReply(event.tweet).flatMap(_.inReplyToStatusId).map(_.toString)),
            "reply_to_user_id" -> getOpt(getReply(event.tweet).map(_.inReplyToUserId.toString)),
            "media_ids" -> ownMedia(event.tweet).map(_.mediaId.toString)
          )
        }

      override val deleteTweet: FutureEffect[DeleteTweet.Event] =
        FutureEffect[DeleteTweet.Event] { event =>
          logMessage(
            tweetDeletionsLogger,
            "type" -> "delete_tweet",
            "tweet_id" -> event.tweet.id.toString,
            "user_id" -> getOpt(event.user.map(_.id.toString)),
            "source_tweet_id" -> getOpt(getShare(event.tweet).map(_.sourceStatusId.toString)),
            "by_user_id" -> getOpt(event.byUserId.map(_.toString)),
            "passthrough_audit_ip" -> getOpt(event.auditPassthrough.flatMap(_.host)),
            "media_ids" -> ownMedia(event.tweet).map(_.mediaId.toString),
            "cascaded_from_tweet_id" -> getOpt(event.cascadedFromTweetId.map(_.toString))
          )
        }

      override val undeleteTweet: FutureEffect[UndeleteTweet.Event] =
        FutureEffect[UndeleteTweet.Event] { event =>
          logMessage(
            tweetUndeletionsLogger,
            "type" -> "undelete_tweet",
            "tweet_id" -> event.tweet.id.toString,
            "user_id" -> event.user.id.toString,
            "source_tweet_id" -> getOpt(getShare(event.tweet).map(_.sourceStatusId.toString)),
            "media_ids" -> ownMedia(event.tweet).map(_.mediaId.toString)
          )
        }

      override val setAdditionalFields: FutureEffect[SetAdditionalFields.Event] =
        FutureEffect[SetAdditionalFields.Event] { event =>
          logMessage(
            tweetUpdatesLogger,
            "type" -> "set_additional_fields",
            "tweet_id" -> event.additionalFields.id.toString,
            "field_ids" -> AdditionalFields.nonEmptyAdditionalFieldIds(event.additionalFields)
          )
        }

      override val deleteAdditionalFields: FutureEffect[DeleteAdditionalFields.Event] =
        FutureEffect[DeleteAdditionalFields.Event] { event =>
          logMessage(
            tweetUpdatesLogger,
            "type" -> "delete_additional_fields",
            "tweet_id" -> event.tweetId.toString,
            "field_ids" -> event.fieldIds
          )
        }

      override val scrubGeo: FutureEffect[ScrubGeo.Event] =
        FutureEffect[ScrubGeo.Event] { event =>
          Future.join(
            event.tweetIds.map { tweetId =>
              logMessage(
                tweetUpdatesLogger,
                "type" -> "scrub_geo",
                "tweet_id" -> tweetId.toString,
                "user_id" -> event.userId.toString
              )
            }
          )
        }

      override val takedown: FutureEffect[Takedown.Event] =
        FutureEffect[Takedown.Event] { event =>
          logMessage(
            tweetUpdatesLogger,
            "type" -> "takedown",
            "tweet_id" -> event.tweet.id.toString,
            "user_id" -> getUserId(event.tweet).toString,
            "reasons" -> event.takedownReasons
          )
        }

      override val updatePossiblySensitiveTweet: FutureEffect[UpdatePossiblySensitiveTweet.Event] =
        FutureEffect[UpdatePossiblySensitiveTweet.Event] { event =>
          logMessage(
            tweetUpdatesLogger,
            "type" -> "update_possibly_sensitive_tweet",
            "tweet_id" -> event.tweet.id.toString,
            "nsfw_admin" -> TweetLenses.nsfwAdmin(event.tweet),
            "nsfw_user" -> TweetLenses.nsfwUser(event.tweet)
          )
        }
    }
}
