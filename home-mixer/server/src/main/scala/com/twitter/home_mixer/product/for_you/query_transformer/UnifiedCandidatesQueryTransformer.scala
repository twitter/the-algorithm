package com.twitter.home_mixer.product.for_you.query_transformer

import com.twitter.events.recos.{thriftscala => t}
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

case class UnifiedCandidatesQueryTransformer(
  maxResultsParam: Param[Int],
  candidatePipelineIdentifier: CandidatePipelineIdentifier)
    extends CandidatePipelineQueryTransformer[
      PipelineQuery,
      t.GetUnfiedCandidatesRequest
    ] {
  override val identifier: TransformerIdentifier = TransformerIdentifier("UnifiedCandidates")

  override def transform(
    query: PipelineQuery
  ): t.GetUnfiedCandidatesRequest = {

    t.GetUnfiedCandidatesRequest(
      displayLocation = t.DisplayLocation.Guide,
      clientId = query.clientContext.appId,
      userId = query.getOptionalUserId,
      languageCode = query.getLanguageCode,
      countryCode = query.getCountryCode,
      maxResults = Some(query.params(maxResultsParam)),
      userAgent = query.clientContext.userAgent,
      isObjectiveTrendsRequest = Some(true)
    )
  }
}
