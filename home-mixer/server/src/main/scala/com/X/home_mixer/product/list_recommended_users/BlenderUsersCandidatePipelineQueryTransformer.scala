package com.X.home_mixer.product.list_recommended_users

import com.X.home_mixer.product.list_recommended_users.model.ListRecommendedUsersQuery
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.search.adaptive.adaptive_results.thriftscala.ResultType
import com.X.search.blender.adaptive_search.thriftscala.AdaptiveSearchRequest
import com.X.search.blender.thriftscala.ThriftBlenderRequest
import com.X.search.blender.thriftscala.ThriftBlenderTweetypieOptions
import com.X.search.blender.thriftscala.ThriftBlenderWorkflowID
import com.X.search.common.constants.thriftscala.ThriftQuerySource
import com.X.spam.rtf.thriftscala.SafetyLevel

object BlenderUsersCandidatePipelineQueryTransformer
    extends CandidatePipelineQueryTransformer[ListRecommendedUsersQuery, ThriftBlenderRequest] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("BlenderUsers")

  /**
   * This is a user-defined descriptor used by Blender to track the source of traffic, and it
   * is different from a client id, which is set during Finagle client construction.
   */
  private val ClientAppName = "timelinemixer.list_recommended_users"

  override def transform(query: ListRecommendedUsersQuery): ThriftBlenderRequest = {

    ThriftBlenderRequest(
      workflowID = Some(ThriftBlenderWorkflowID.AdaptiveSearch),
      userID = Some(query.getRequiredUserId), // perspectival
      uiLang = query.clientContext.languageCode, // perspectival
      clientAppName = Some(ClientAppName),
      adaptiveSearchRequest = Some(
        AdaptiveSearchRequest(
          rawQuery = query.listName,
          numResults = 40,
          getPromotedContent = false,
          resultFilter = Some(ResultType.User),
        )
      ),
      querySource = Some(ThriftQuerySource.TypedQuery),
      getCorrections = true,
      tweetypieOptions = Some(
        ThriftBlenderTweetypieOptions(
          safetyLevel = Some(SafetyLevel.Recommendations)
        )
      )
    )
  }
}
