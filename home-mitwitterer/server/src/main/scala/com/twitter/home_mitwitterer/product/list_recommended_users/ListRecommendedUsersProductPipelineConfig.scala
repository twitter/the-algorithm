package com.twitter.home_mitwitterer.product.list_recommended_users

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.marshaller.timelines.RecommendedUsersCursorUnmarshaller
import com.twitter.home_mitwitterer.model.request.HomeMitwittererRequest
import com.twitter.home_mitwitterer.model.request.ListRecommendedUsersProduct
import com.twitter.home_mitwitterer.model.request.ListRecommendedUsersProductContetwittert
import com.twitter.home_mitwitterer.product.list_recommended_users.model.ListRecommendedUsersQuery
import com.twitter.home_mitwitterer.product.list_recommended_users.param.ListRecommendedUsersParam.ServerMatwitterResultsParam
import com.twitter.home_mitwitterer.product.list_recommended_users.param.ListRecommendedUsersParamConfig
import com.twitter.home_mitwitterer.service.HomeMitwittererAccessPolicy.DefaultHomeMitwittererAccessPolicy
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig.DefaultNotificationGroup
import com.twitter.product_mitwitterer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mitwitterer.core.functional_component.common.alert.predicate.TriggerIfBelow
import com.twitter.product_mitwitterer.core.functional_component.common.alert.predicate.TriggerIfLatencyAbove
import com.twitter.product_mitwitterer.core.functional_component.common.alert.Alert
import com.twitter.product_mitwitterer.core.functional_component.common.alert.LatencyAlert
import com.twitter.product_mitwitterer.core.functional_component.common.alert.P99
import com.twitter.product_mitwitterer.core.functional_component.common.alert.SuccessRateAlert
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ProductPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request
import com.twitter.product_mitwitterer.core.pipeline.PipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineConfig
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.timelines.configapi.Params
import com.twitter.timelines.render.{thriftscala => urt}
import com.twitter.timelines.util.RequestCursorSerializer
import com.twitter.util.Try

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ListRecommendedUsersProductPipelineConfig @Inject() (
  listRecommendedUsersMitwittererPipelineConfig: ListRecommendedUsersMitwittererPipelineConfig,
  listRecommendedUsersParamConfig: ListRecommendedUsersParamConfig)
    etwittertends ProductPipelineConfig[
      HomeMitwittererRequest,
      ListRecommendedUsersQuery,
      urt.TimelineResponse
    ] {

  override val identifier: ProductPipelineIdentifier =
    ProductPipelineIdentifier("ListRecommendedUsers")
  override val product: request.Product = ListRecommendedUsersProduct
  override val paramConfig: ProductParamConfig = listRecommendedUsersParamConfig

  override def pipelineQueryTransformer(
    request: HomeMitwittererRequest,
    params: Params
  ): ListRecommendedUsersQuery = {
    val contetwittert = request.productContetwittert match {
      case Some(contetwittert: ListRecommendedUsersProductContetwittert) => contetwittert
      case _ => throw PipelineFailure(BadRequest, "ListRecommendedUsersProductContetwittert not found")
    }

    val debugOptions = request.debugParams.flatMap(_.debugOptions)

    val pipelineCursor = request.serializedRequestCursor.flatMap { cursor =>
      Try(UrtCursorSerializer.deserializeUnorderedEtwittercludeIdsCursor(cursor))
        .getOrElse(RecommendedUsersCursorUnmarshaller(RequestCursorSerializer.deserialize(cursor)))
    }

    ListRecommendedUsersQuery(
      listId = contetwittert.listId,
      params = params,
      clientContetwittert = request.clientContetwittert,
      features = None,
      pipelineCursor = pipelineCursor,
      requestedMatwitterResults = Some(params(ServerMatwitterResultsParam)),
      debugOptions = debugOptions,
      selectedUserIds = contetwittert.selectedUserIds,
      etwittercludedUserIds = contetwittert.etwittercludedUserIds,
      listName = contetwittert.listName
    )
  }

  override def pipelines: Seq[PipelineConfig] = Seq(listRecommendedUsersMitwittererPipelineConfig)

  override def pipelineSelector(query: ListRecommendedUsersQuery): ComponentIdentifier =
    listRecommendedUsersMitwittererPipelineConfig.identifier

  override val alerts: Seq[Alert] = Seq(
    SuccessRateAlert(
      notificationGroup = DefaultNotificationGroup,
      warnPredicate = TriggerIfBelow(99.9, 20, 30),
      criticalPredicate = TriggerIfBelow(99.9, 30, 30),
    ),
    LatencyAlert(
      notificationGroup = DefaultNotificationGroup,
      percentile = P99,
      warnPredicate = TriggerIfLatencyAbove(1000.millis, 15, 30),
      criticalPredicate = TriggerIfLatencyAbove(1500.millis, 15, 30)
    )
  )

  override val debugAccessPolicies: Set[AccessPolicy] = DefaultHomeMitwittererAccessPolicy
}
