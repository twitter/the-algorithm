package com.twitter.home_mitwitterer.product.list_recommended_users.model

import com.twitter.home_mitwitterer.model.request.HasListId
import com.twitter.home_mitwitterer.model.request.ListRecommendedUsersProduct
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtUnorderedEtwittercludeIdsCursor
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.request._
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Params

case class ListRecommendedUsersQuery(
  override val listId: Long,
  override val params: Params,
  override val clientContetwittert: ClientContetwittert,
  override val pipelineCursor: Option[UrtUnorderedEtwittercludeIdsCursor],
  override val requestedMatwitterResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  selectedUserIds: Option[Seq[Long]],
  etwittercludedUserIds: Option[Seq[Long]],
  listName: Option[String])
    etwittertends PipelineQuery
    with HasPipelineCursor[UrtUnorderedEtwittercludeIdsCursor]
    with HasListId {

  override val product: Product = ListRecommendedUsersProduct

  override def withFeatureMap(features: FeatureMap): ListRecommendedUsersQuery =
    copy(features = Some(features))
}
