package com.X.home_mixer.functional_component.feature_hydrator

import com.X.finagle.tracing.Annotation.BinaryAnnotation
import com.X.finagle.tracing.ForwardAnnotation
import com.X.home_mixer.model.HomeFeatures._
import com.X.home_mixer.model.request.DeviceContext.RequestContext
import com.X.home_mixer.model.request.HasDeviceContext
import com.X.joinkey.context.RequestJoinKeyContext
import com.X.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.model.marshalling.response.urt.operation.BottomCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.X.product_mixer.core.pipeline.HasPipelineCursor
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.pipeline.pipeline_failure.BadRequest
import com.X.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.X.search.common.util.lang.ThriftLanguageUtil
import com.X.snowflake.id.SnowflakeId
import com.X.stitch.Stitch
import com.X.timelines.prediction.adapters.request_context.RequestContextAdapter.dowFromTimestamp
import com.X.timelines.prediction.adapters.request_context.RequestContextAdapter.hourFromTimestamp
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestQueryFeatureHydrator[
  Query <: PipelineQuery with HasPipelineCursor[UrtOrderedCursor] with HasDeviceContext] @Inject() (
) extends QueryFeatureHydrator[Query] {

  override val features: Set[Feature[_, _]] = Set(
    AccountAgeFeature,
    ClientIdFeature,
    DeviceLanguageFeature,
    GetInitialFeature,
    GetMiddleFeature,
    GetNewerFeature,
    GetOlderFeature,
    GuestIdFeature,
    HasDarkRequestFeature,
    IsForegroundRequestFeature,
    IsLaunchRequestFeature,
    PollingFeature,
    PullToRefreshFeature,
    RequestJoinIdFeature,
    ServedRequestIdFeature,
    TimestampFeature,
    TimestampGMTDowFeature,
    TimestampGMTHourFeature,
    ViewerIdFeature
  )

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Request")

  private val DarkRequestAnnotation = "clnt/has_dark_request"

  // Convert Language code to ISO 639-3 format
  private def getLanguageISOFormatByCode(languageCode: String): String =
    ThriftLanguageUtil.getLanguageCodeOf(ThriftLanguageUtil.getThriftLanguageOf(languageCode))

  private def getRequestJoinId(servedRequestId: Long): Option[Long] =
    Some(RequestJoinKeyContext.current.flatMap(_.requestJoinId).getOrElse(servedRequestId))

  private def hasDarkRequest: Option[Boolean] = ForwardAnnotation.current
    .getOrElse(Seq[BinaryAnnotation]())
    .find(_.key == DarkRequestAnnotation)
    .map(_.value.asInstanceOf[Boolean])

  override def hydrate(query: Query): Stitch[FeatureMap] = {
    val requestContext = query.deviceContext.flatMap(_.requestContextValue)
    val servedRequestId = UUID.randomUUID.getMostSignificantBits
    val timestamp = query.queryTime.inMilliseconds

    val featureMap = FeatureMapBuilder()
      .add(AccountAgeFeature, query.getOptionalUserId.flatMap(SnowflakeId.timeFromIdOpt))
      .add(ClientIdFeature, query.clientContext.appId)
      .add(DeviceLanguageFeature, query.getLanguageCode.map(getLanguageISOFormatByCode))
      .add(
        GetInitialFeature,
        query.pipelineCursor.forall(cursor => cursor.id.isEmpty && cursor.gapBoundaryId.isEmpty))
      .add(
        GetMiddleFeature,
        query.pipelineCursor.exists(cursor =>
          cursor.id.isDefined && cursor.gapBoundaryId.isDefined &&
            cursor.cursorType.contains(GapCursor)))
      .add(
        GetNewerFeature,
        query.pipelineCursor.exists(cursor =>
          cursor.id.isDefined && cursor.gapBoundaryId.isEmpty &&
            cursor.cursorType.contains(TopCursor)))
      .add(
        GetOlderFeature,
        query.pipelineCursor.exists(cursor =>
          cursor.id.isDefined && cursor.gapBoundaryId.isEmpty &&
            cursor.cursorType.contains(BottomCursor)))
      .add(GuestIdFeature, query.clientContext.guestId)
      .add(IsForegroundRequestFeature, requestContext.contains(RequestContext.Foreground))
      .add(IsLaunchRequestFeature, requestContext.contains(RequestContext.Launch))
      .add(PollingFeature, query.deviceContext.exists(_.isPolling.contains(true)))
      .add(PullToRefreshFeature, requestContext.contains(RequestContext.PullToRefresh))
      .add(ServedRequestIdFeature, Some(servedRequestId))
      .add(RequestJoinIdFeature, getRequestJoinId(servedRequestId))
      .add(TimestampFeature, timestamp)
      .add(TimestampGMTDowFeature, dowFromTimestamp(timestamp))
      .add(TimestampGMTHourFeature, hourFromTimestamp(timestamp))
      .add(HasDarkRequestFeature, hasDarkRequest)
      .add(
        ViewerIdFeature,
        query.getOptionalUserId
          .orElse(query.getGuestId).getOrElse(
            throw PipelineFailure(BadRequest, "Missing viewer id")))
      .build()

    Stitch.value(featureMap)
  }
}
