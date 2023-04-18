package com.twitter.representationscorer.columns

import com.twitter.representationscorer.common.TweetId
import com.twitter.representationscorer.common.UserId
import com.twitter.representationscorer.thriftscala.RecentEngagementSimilaritiesResponse
import com.twitter.representationscorer.twistlyfeatures.Scorer
import com.twitter.stitch
import com.twitter.stitch.Stitch
import com.twitter.strato.catalog.OpMetadata
import com.twitter.strato.config.ContactInfo
import com.twitter.strato.config.Policy
import com.twitter.strato.data.Conv
import com.twitter.strato.data.Description.PlainText
import com.twitter.strato.data.Lifecycle
import com.twitter.strato.fed._
import com.twitter.strato.thrift.ScroogeConv
import javax.inject.Inject

class SimClustersRecentEngagementSimilarityColumn @Inject() (scorer: Scorer)
    extends StratoFed.Column(
      "recommendations/representation_scorer/simClustersRecentEngagementSimilarity")
    with StratoFed.Fetch.Stitch {

  override val policy: Policy = Common.rsxReadPolicy

  override type Key = (UserId, Seq[TweetId])
  override type View = Unit
  override type Value = RecentEngagementSimilaritiesResponse

  override val keyConv: Conv[Key] = Conv.ofType[(Long, Seq[Long])]
  override val viewConv: Conv[View] = Conv.ofType
  override val valueConv: Conv[Value] =
    ScroogeConv.fromStruct[RecentEngagementSimilaritiesResponse]

  override val contactInfo: ContactInfo = Info.contactInfo

  override val metadata: OpMetadata = OpMetadata(
    lifecycle = Some(Lifecycle.Production),
    description = Some(
      PlainText(
        "User-Tweet scores based on the user's recent engagements for multiple tweets."
      ))
  )

  override def fetch(key: Key, view: View): Stitch[Result[Value]] =
    scorer
      .get(key._1, key._2)
      .map(results => found(RecentEngagementSimilaritiesResponse(results)))
      .handle {
        case stitch.NotFound => missing
      }
}
