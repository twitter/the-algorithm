package com.X.representation_manager.columns.tweet

import com.X.representation_manager.columns.ColumnConfigBase
import com.X.representation_manager.store.TweetSimClustersEmbeddingStore
import com.X.representation_manager.thriftscala.SimClustersEmbeddingView
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.simclusters_v2.thriftscala.SimClustersEmbedding
import com.X.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.X.stitch
import com.X.stitch.Stitch
import com.X.stitch.storehaus.StitchOfReadableStore
import com.X.strato.catalog.OpMetadata
import com.X.strato.config.AnyOf
import com.X.strato.config.ContactInfo
import com.X.strato.config.FromColumns
import com.X.strato.config.Policy
import com.X.strato.config.Prefix
import com.X.strato.data.Conv
import com.X.strato.data.Description.PlainText
import com.X.strato.data.Lifecycle
import com.X.strato.fed._
import com.X.strato.thrift.ScroogeConv
import javax.inject.Inject

class TweetSimClustersEmbeddingCol @Inject() (embeddingStore: TweetSimClustersEmbeddingStore)
    extends StratoFed.Column("recommendations/representation_manager/simClustersEmbedding.Tweet")
    with StratoFed.Fetch.Stitch {

  private val storeStitch: SimClustersEmbeddingId => Stitch[SimClustersEmbedding] =
    StitchOfReadableStore(embeddingStore.tweetSimClustersEmbeddingStore.mapValues(_.toThrift))

  val colPermissions: Seq[com.X.strato.config.Policy] =
    ColumnConfigBase.recosPermissions ++ ColumnConfigBase.externalPermissions :+ FromColumns(
      Set(
        Prefix("ml/featureStore/simClusters"),
      ))

  override val policy: Policy = AnyOf({
    colPermissions
  })

  override type Key = Long // TweetId
  override type View = SimClustersEmbeddingView
  override type Value = SimClustersEmbedding

  override val keyConv: Conv[Key] = Conv.long
  override val viewConv: Conv[View] = ScroogeConv.fromStruct[SimClustersEmbeddingView]
  override val valueConv: Conv[Value] = ScroogeConv.fromStruct[SimClustersEmbedding]

  override val contactInfo: ContactInfo = ColumnConfigBase.contactInfo

  override val metadata: OpMetadata = OpMetadata(
    lifecycle = Some(Lifecycle.Production),
    description = Some(
      PlainText("The Tweet SimClusters Embedding Endpoint in Representation Management Service." +
        " TDD: http://go/rms-tdd"))
  )

  override def fetch(key: Key, view: View): Stitch[Result[Value]] = {
    val embeddingId = SimClustersEmbeddingId(
      view.embeddingType,
      view.modelVersion,
      InternalId.TweetId(key)
    )

    storeStitch(embeddingId)
      .map(embedding => found(embedding))
      .handle {
        case stitch.NotFound => missing
      }
  }

}
