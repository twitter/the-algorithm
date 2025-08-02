package com.twitter.cr_mitwitterer.logging

import com.twitter.cr_mitwitterer.model.AdsCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.CrCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.RelatedTweetCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.UtegTweetCandidateGeneratorQuery
import com.twitter.cr_mitwitterer.thriftscala.Product
import com.twitter.product_mitwitterer.core.thriftscala.ClientContetwittert
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
  clientContetwittert: ClientContetwittert,
  product: Product)

object RelatedTweetScribeMetadata {
  def from(query: RelatedTweetCandidateGeneratorQuery): RelatedTweetScribeMetadata = {
    RelatedTweetScribeMetadata(
      query.requestUUID,
      query.internalId,
      query.clientContetwittert,
      query.product)
  }
}
