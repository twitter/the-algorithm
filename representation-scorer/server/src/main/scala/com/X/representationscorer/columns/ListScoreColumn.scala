package com.X.representationscorer.columns

import com.X.representationscorer.thriftscala.ListScoreId
import com.X.representationscorer.thriftscala.ListScoreResponse
import com.X.representationscorer.scorestore.ScoreStore
import com.X.representationscorer.thriftscala.ScoreResult
import com.X.simclusters_v2.common.SimClustersEmbeddingId.LongInternalId
import com.X.simclusters_v2.common.SimClustersEmbeddingId.LongSimClustersEmbeddingId
import com.X.simclusters_v2.thriftscala.Score
import com.X.simclusters_v2.thriftscala.ScoreId
import com.X.simclusters_v2.thriftscala.ScoreInternalId
import com.X.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.X.simclusters_v2.thriftscala.SimClustersEmbeddingPairScoreId
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
import com.X.util.Future
import com.X.util.Return
import com.X.util.Throw
import javax.inject.Inject

class ListScoreColumn @Inject() (scoreStore: ScoreStore)
    extends StratoFed.Column("recommendations/representation_scorer/listScore")
    with StratoFed.Fetch.Stitch {

  override val policy: Policy = Common.rsxReadPolicy

  override type Key = ListScoreId
  override type View = Unit
  override type Value = ListScoreResponse

  override val keyConv: Conv[Key] = ScroogeConv.fromStruct[ListScoreId]
  override val viewConv: Conv[View] = Conv.ofType
  override val valueConv: Conv[Value] = ScroogeConv.fromStruct[ListScoreResponse]

  override val contactInfo: ContactInfo = Info.contactInfo

  override val metadata: OpMetadata = OpMetadata(
    lifecycle = Some(Lifecycle.Production),
    description = Some(
      PlainText(
        "Scoring for multiple candidate entities against a single target entity"
      ))
  )

  override def fetch(key: Key, view: View): Stitch[Result[Value]] = {

    val target = SimClustersEmbeddingId(
      embeddingType = key.targetEmbeddingType,
      modelVersion = key.modelVersion,
      internalId = key.targetId
    )
    val scoreIds = key.candidateIds.map { candidateId =>
      val candidate = SimClustersEmbeddingId(
        embeddingType = key.candidateEmbeddingType,
        modelVersion = key.modelVersion,
        internalId = candidateId
      )
      ScoreId(
        algorithm = key.algorithm,
        internalId = ScoreInternalId.SimClustersEmbeddingPairScoreId(
          SimClustersEmbeddingPairScoreId(target, candidate)
        )
      )
    }

    Stitch
      .callFuture {
        val (keys: Iterable[ScoreId], vals: Iterable[Future[Option[Score]]]) =
          scoreStore.uniformScoringStore.multiGet(scoreIds.toSet).unzip
        val results: Future[Iterable[Option[Score]]] = Future.collectToTry(vals.toSeq) map {
          tryOptVals =>
            tryOptVals map {
              case Return(Some(v)) => Some(v)
              case Return(None) => None
              case Throw(_) => None
            }
        }
        val scoreMap: Future[Map[Long, Double]] = results.map { scores =>
          keys
            .zip(scores).collect {
              case (
                    ScoreId(
                      _,
                      ScoreInternalId.SimClustersEmbeddingPairScoreId(
                        SimClustersEmbeddingPairScoreId(
                          _,
                          LongSimClustersEmbeddingId(candidateId)))),
                    Some(score)) =>
                (candidateId, score.score)
            }.toMap
        }
        scoreMap
      }
      .map { (scores: Map[Long, Double]) =>
        val orderedScores = key.candidateIds.collect {
          case LongInternalId(id) => ScoreResult(scores.get(id))
          case _ =>
            // This will return None scores for candidates which don't have Long ids, but that's fine:
            // at the moment we're only scoring for Tweets
            ScoreResult(None)
        }
        found(ListScoreResponse(orderedScores))
      }
      .handle {
        case stitch.NotFound => missing
      }
  }
}
