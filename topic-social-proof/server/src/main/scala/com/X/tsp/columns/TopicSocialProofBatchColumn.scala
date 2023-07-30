package com.X.tsp.columns

import com.X.stitch.SeqGroup
import com.X.stitch.Stitch
import com.X.strato.catalog.Fetch
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
import com.X.tsp.thriftscala.TopicSocialProofOptions
import com.X.tsp.service.TopicSocialProofService
import com.X.tsp.thriftscala.TopicWithScore
import com.X.util.Future
import com.X.util.Try
import javax.inject.Inject

class TopicSocialProofBatchColumn @Inject() (
  topicSocialProofService: TopicSocialProofService)
    extends StratoFed.Column(TopicSocialProofBatchColumn.Path)
    with StratoFed.Fetch.Stitch {

  override val policy: Policy =
    ReadWritePolicy(
      readPolicy = AllowAll,
      writePolicy = AllowKeyAuthenticatedXUserId
    )

  override type Key = Long
  override type View = TopicSocialProofOptions
  override type Value = Seq[TopicWithScore]

  override val keyConv: Conv[Key] = Conv.ofType
  override val viewConv: Conv[View] = ScroogeConv.fromStruct[TopicSocialProofOptions]
  override val valueConv: Conv[Value] = Conv.seq(ScroogeConv.fromStruct[TopicWithScore])
  override val metadata: OpMetadata =
    OpMetadata(
      lifecycle = Some(Production),
      Some(PlainText("Topic Social Proof Batched Federated Column")))

  case class TspsGroup(view: View) extends SeqGroup[Long, Fetch.Result[Value]] {
    override protected def run(keys: Seq[Long]): Future[Seq[Try[Result[Seq[TopicWithScore]]]]] = {
      val request = TopicSocialProofRequest(
        userId = view.userId,
        tweetIds = keys.toSet,
        displayLocation = view.displayLocation,
        topicListingSetting = view.topicListingSetting,
        context = view.context,
        bypassModes = view.bypassModes,
        tags = view.tags
      )

      val response = topicSocialProofService
        .topicSocialProofHandlerStoreStitch(request)
        .map(_.socialProofs)
      Stitch
        .run(response).map(r =>
          keys.map(key => {
            Try {
              val v = r.get(key)
              if (v.nonEmpty && v.get.nonEmpty) {
                found(v.get)
              } else {
                missing
              }
            }
          }))
    }
  }

  override def fetch(key: Key, view: View): Stitch[Result[Value]] = {
    Stitch.call(key, TspsGroup(view))
  }
}

object TopicSocialProofBatchColumn {
  val Path = "topic-signals/tsp/topic-social-proof-batched"
}
