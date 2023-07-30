package com.X.representationscorer.columns

import com.X.contentrecommender.thriftscala.ScoringResponse
import com.X.representationscorer.scorestore.ScoreStore
import com.X.simclusters_v2.thriftscala.ScoreId
import com.X.stitch
import com.X.stitch.Stitch
import com.X.strato.config.ContactInfo
import com.X.strato.config.Policy
import com.X.strato.catalog.OpMetadata
import com.X.strato.data.Conv
import com.X.strato.data.Lifecycle
import com.X.strato.data.Description.PlainText
import com.X.strato.fed._
import com.X.strato.thrift.ScroogeConv
import javax.inject.Inject

class ScoreColumn @Inject() (scoreStore: ScoreStore)
    extends StratoFed.Column("recommendations/representation_scorer/score")
    with StratoFed.Fetch.Stitch {

  override val policy: Policy = Common.rsxReadPolicy

  override type Key = ScoreId
  override type View = Unit
  override type Value = ScoringResponse

  override val keyConv: Conv[Key] = ScroogeConv.fromStruct[ScoreId]
  override val viewConv: Conv[View] = Conv.ofType
  override val valueConv: Conv[Value] = ScroogeConv.fromStruct[ScoringResponse]

  override val contactInfo: ContactInfo = Info.contactInfo

  override val metadata: OpMetadata = OpMetadata(
    lifecycle = Some(Lifecycle.Production),
    description = Some(PlainText(
      "The Uniform Scoring Endpoint in Representation Scorer for the Content-Recommender." +
        " TDD: http://go/representation-scorer-tdd Guideline: http://go/uniform-scoring-guideline"))
  )

  override def fetch(key: Key, view: View): Stitch[Result[Value]] =
    scoreStore
      .uniformScoringStoreStitch(key)
      .map(score => found(ScoringResponse(Some(score))))
      .handle {
        case stitch.NotFound => missing
      }
}
