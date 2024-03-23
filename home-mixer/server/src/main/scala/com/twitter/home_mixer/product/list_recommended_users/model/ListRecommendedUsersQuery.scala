package com.ExTwitter.home_mixer.product.list_recommended_users.model

import com.ExTwitter.home_mixer.model.request.HasListId
import com.ExTwitter.home_mixer.model.request.ListRecommendedUsersProduct
import com.ExTwitter.product_mixer.component_library.model.cursor.UrtUnorderedExcludeIdsCursor
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.model.marshalling.request._
import com.ExTwitter.product_mixer.core.pipeline.HasPipelineCursor
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.timelines.configapi.Params

case class ListRecommendedUsersQuery(
  override val listId: Long,
  override val params: Params,
  override val clientContext: ClientContext,
  override val pipelineCursor: Option[UrtUnorderedExcludeIdsCursor],
  override val requestedMaxResults: Option[Int],
  override val debugOptions: Option[DebugOptions],
  override val features: Option[FeatureMap],
  selectedUserIds: Option[Seq[Long]],
  excludedUserIds: Option[Seq[Long]],
  listName: Option[String])
    extends PipelineQuery
    with HasPipelineCursor[UrtUnorderedExcludeIdsCursor]
    with HasListId {

  override val product: Product = ListRecommendedUsersProduct

  override def withFeatureMap(features: FeatureMap): ListRecommendedUsersQuery =
    copy(features = Some(features))
}
