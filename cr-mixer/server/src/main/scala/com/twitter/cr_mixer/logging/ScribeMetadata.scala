package com.ExTwitter.cr_mixer.logging

import com.ExTwitter.cr_mixer.model.AdsCandidateGeneratorQuery
import com.ExTwitter.cr_mixer.model.CrCandidateGeneratorQuery
import com.ExTwitter.cr_mixer.model.RelatedTweetCandidateGeneratorQuery
import com.ExTwitter.cr_mixer.model.UtegTweetCandidateGeneratorQuery
import com.ExTwitter.cr_mixer.thriftscala.Product
import com.ExTwitter.product_mixer.core.thriftscala.ClientContext
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.simclusters_v2.thriftscala.InternalId

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
