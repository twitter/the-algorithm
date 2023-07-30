package com.X.tsp.columns

import com.X.stitch
import com.X.stitch.Stitch
import com.X.strato.catalog.OpMetadata
import com.X.strato.config._
import com.X.strato.config.AllowAll
import com.X.strato.config.ContactInfo
import com.X.strato.config.Policy
import com.X.strato.data.Conv
import com.X.strato.data.Description.PlainText
import com.X.strato.data.Lifecycle.Production
import com.X.strato.fed.StratoFed
import com.X.strato.thrift.ScroogeConv
import com.X.tsp.thriftscala.TopicSocialProofRequest
import com.X.tsp.thriftscala.TopicSocialProofResponse
import com.X.tsp.service.TopicSocialProofService
import javax.inject.Inject

class TopicSocialProofColumn @Inject() (
  topicSocialProofService: TopicSocialProofService)
    extends StratoFed.Column(TopicSocialProofColumn.Path)
    with StratoFed.Fetch.Stitch {

  override type Key = TopicSocialProofRequest
  override type View = Unit
  override type Value = TopicSocialProofResponse

  override val keyConv: Conv[Key] = ScroogeConv.fromStruct[TopicSocialProofRequest]
  override val viewConv: Conv[View] = Conv.ofType
  override val valueConv: Conv[Value] = ScroogeConv.fromStruct[TopicSocialProofResponse]
  override val metadata: OpMetadata =
    OpMetadata(lifecycle = Some(Production), Some(PlainText("Topic Social Proof Federated Column")))

  override def fetch(key: Key, view: View): Stitch[Result[Value]] = {
    topicSocialProofService
      .topicSocialProofHandlerStoreStitch(key)
      .map { result => found(result) }
      .handle {
        case stitch.NotFound => missing
      }
  }
}

object TopicSocialProofColumn {
  val Path = "topic-signals/tsp/topic-social-proof"
}
