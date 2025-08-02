package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.finagle.tracing.Annotation.BinaryAnnotation
import com.twitter.finagle.tracing.ForwardAnnotation
import com.twitter.home_mitwitterer.model.HomeFeatures._
import com.twitter.home_mitwitterer.model.request.DeviceContetwittert.RequestContetwittert
import com.twitter.home_mitwitterer.model.request.HasDeviceContetwittert
import com.twitter.joinkey.contetwittert.RequestJoinKeyContetwittert
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.BottomCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.GapCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.TopCursor
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.search.common.util.lang.ThriftLanguageUtil
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.adapters.request_contetwittert.RequestContetwittertAdapter.dowFromTimestamp
import com.twitter.timelines.prediction.adapters.request_contetwittert.RequestContetwittertAdapter.hourFromTimestamp
import java.util.UUID
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RequestQueryFeatureHydrator[
  Query <: PipelineQuery with HasPipelineCursor[UrtOrderedCursor] with HasDeviceContetwittert] @Inject() (
) etwittertends QueryFeatureHydrator[Query] {

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
    Some(RequestJoinKeyContetwittert.current.flatMap(_.requestJoinId).getOrElse(servedRequestId))

  private def hasDarkRequest: Option[Boolean] = ForwardAnnotation.current
    .getOrElse(Seq[BinaryAnnotation]())
    .find(_.key == DarkRequestAnnotation)
    .map(_.value.asInstanceOf[Boolean])

  override def hydrate(query: Query): Stitch[FeatureMap] = {
    val requestContetwittert = query.deviceContetwittert.flatMap(_.requestContetwittertValue)
    val servedRequestId = UUID.randomUUID.getMostSignificantBits
    val timestamp = query.queryTime.inMilliseconds

    val featureMap = FeatureMapBuilder()
      .add(AccountAgeFeature, query.getOptionalUserId.flatMap(SnowflakeId.timeFromIdOpt))
      .add(ClientIdFeature, query.clientContetwittert.appId)
      .add(DeviceLanguageFeature, query.getLanguageCode.map(getLanguageISOFormatByCode))
      .add(
        GetInitialFeature,
        query.pipelineCursor.forall(cursor => cursor.id.isEmpty && cursor.gapBoundaryId.isEmpty))
      .add(
        GetMiddleFeature,
        query.pipelineCursor.etwitterists(cursor =>
          cursor.id.isDefined && cursor.gapBoundaryId.isDefined &&
            cursor.cursorType.contains(GapCursor)))
      .add(
        GetNewerFeature,
        query.pipelineCursor.etwitterists(cursor =>
          cursor.id.isDefined && cursor.gapBoundaryId.isEmpty &&
            cursor.cursorType.contains(TopCursor)))
      .add(
        GetOlderFeature,
        query.pipelineCursor.etwitterists(cursor =>
          cursor.id.isDefined && cursor.gapBoundaryId.isEmpty &&
            cursor.cursorType.contains(BottomCursor)))
      .add(GuestIdFeature, query.clientContetwittert.guestId)
      .add(IsForegroundRequestFeature, requestContetwittert.contains(RequestContetwittert.Foreground))
      .add(IsLaunchRequestFeature, requestContetwittert.contains(RequestContetwittert.Launch))
      .add(PollingFeature, query.deviceContetwittert.etwitterists(_.isPolling.contains(true)))
      .add(PullToRefreshFeature, requestContetwittert.contains(RequestContetwittert.PullToRefresh))
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
