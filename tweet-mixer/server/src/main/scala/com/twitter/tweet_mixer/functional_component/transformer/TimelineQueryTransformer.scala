package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelineservice.{thriftscala => tlsthrift}
import com.twitter.tweet_mixer.feature.AuthorIdFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.MediaRelatedCreatorMaxCandidates

case class TimelineQueryTransformer(
  timelineType: tlsthrift.TimelineType,
  signalFn: PipelineQuery => Seq[Long],
  disableUrtHealthTreatments: Option[Boolean])
    extends CandidatePipelineQueryTransformer[
      PipelineQuery,
      tlsthrift.TimelineQuery
    ] {

  override def transform(
    query: PipelineQuery
  ): tlsthrift.TimelineQuery = {
    val timelineQueryOptions: tlsthrift.TimelineQueryOptions = tlsthrift.TimelineQueryOptions(
      contextualUserId = query.getOptionalUserId,
      deviceContext = Some(toDeviceContext(query.clientContext)),
      contextualUserContext =
        Some(tlsthrift.ContextualUserContext(roles = query.clientContext.userRoles)),
      disableUrtHealthTreatments = disableUrtHealthTreatments
    )

    val authorId = query.features.get.getOrElse(AuthorIdFeature, None).getOrElse(-1L)

    tlsthrift.TimelineQuery(
      timelineType = timelineType,
      authorId,
      maxCount = query.params(MediaRelatedCreatorMaxCandidates).toShort,
      options = Some(timelineQueryOptions)
    )
  }

  private def toDeviceContext(clientContext: ClientContext): tlsthrift.DeviceContext = {
    tlsthrift.DeviceContext(
      countryCode = clientContext.countryCode,
      languageCode = clientContext.languageCode,
      clientAppId = clientContext.appId,
      ipAddress = clientContext.ipAddress,
      guestId = clientContext.guestId,
      deviceId = clientContext.deviceId,
      userAgent = clientContext.userAgent
    )
  }
}
