package com.twitter.cr_mixer.logging

import com.twitter.cr_mixer.model.AdsCandidateGeneratorQuery
import com.twitter.cr_mixer.model.CrCandidateGeneratorQuery
import com.twitter.cr_mixer.model.RelatedTweetCandidateGeneratorQuery
import com.twitter.cr_mixer.model.UtegTweetCandidateGeneratorQuery
import com.twitter.cr_mixer.thriftscala.Product
import com.twitter.product_mixer.core.thriftscala.ClientContext
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.thriftscala.InternalId

case class ScribeMetadata(
  requestUUID: Long,
  userId: UserId,
  product: Product)

object ScribeMetadata {
  def from(query: CrCandidateGeneratorQuery): ScribeMetadata = {
    ScribeMetadata(query.requestUUID, query.userId, query.product)
  }

  def from(query: UtegTweetCandidateGeneratorQuery): ScribeMetadata = {
    ScribeMetadata(query.requestUUID, query.userId, query.product)
  }

  def from(query: AdsCandidateGeneratorQuery): ScribeMetadata = {
    ScribeMetadata(query.requestUUID, query.userId, query.product)
  }
}

case class RelatedTweetScribeMetadata(
  requestUUID: Long,
  internalId: InternalId,
  clientContext: ClientContext,
  product: Product)

object RelatedTweetScribeMetadata {
  def from(query: RelatedTweetCandidateGeneratorQuery): RelatedTweetScribeMetadata = {
    RelatedTweetScribeMetadata(
      query.requestUUID,
      query.internalId,
      query.clientContext,
      query.product)
  }
}
