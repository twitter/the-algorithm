package com.X.representationscorer.columns

import com.X.representationscorer.common.TweetId
import com.X.representationscorer.common.UserId
import com.X.representationscorer.thriftscala.SimClustersRecentEngagementSimilarities
import com.X.representationscorer.twistlyfeatures.Scorer
import com.X.stitch
import com.X.stitch.Stitch
import com.X.strato.catalog.OpMetadata
import com.X.strato.config.ContactInfo
import com.X.strato.config.Policy
import com.X.strato.data.Conv
import com.X.strato.data.Description.PlainText
import com.X.strato.data.Lifecycle
import com.X.strato.fed._
import com.X.strato.thrift.ScroogeConv
import javax.inject.Inject

class SimClustersRecentEngagementSimilarityUserTweetEdgeColumn @Inject() (scorer: Scorer)
    extends StratoFed.Column(
      "recommendations/representation_scorer/simClustersRecentEngagementSimilarity.UserTweetEdge")
    with StratoFed.Fetch.Stitch {

  override val policy: Policy = Common.rsxReadPolicy

  override type Key = (UserId, TweetId)
  override type View = Unit
  override type Value = SimClustersRecentEngagementSimilarities

  override val keyConv: Conv[Key] = Conv.ofType[(Long, Long)]
  override val viewConv: Conv[View] = Conv.ofType
  override val valueConv: Conv[Value] =
    ScroogeConv.fromStruct[SimClustersRecentEngagementSimilarities]

  override val contactInfo: ContactInfo = Info.contactInfo

  override val metadata: OpMetadata = OpMetadata(
    lifecycle = Some(Lifecycle.Production),
    description = Some(
      PlainText(
        "User-Tweet scores based on the user's recent engagements"
      ))
  )

  override def fetch(key: Key, view: View): Stitch[Result[Value]] =
    scorer
      .get(key._1, key._2)
      .map(found(_))
      .handle {
        case stitch.NotFound => missing
      }
}
