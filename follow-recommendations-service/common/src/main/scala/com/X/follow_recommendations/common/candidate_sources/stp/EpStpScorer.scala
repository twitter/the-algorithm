package com.X.follow_recommendations.common.candidate_sources.stp

import com.X.bijection.scrooge.BinaryScalaCodec
import com.X.bijection.thrift.BinaryThriftCodec
import com.X.relevance.ep_model.scorer.EPScorer
import com.X.relevance.ep_model.scorer.ScorerUtil
import com.X.relevance.ep_model.thrift
import com.X.relevance.ep_model.thriftscala.EPScoringOptions
import com.X.relevance.ep_model.thriftscala.EPScoringRequest
import com.X.relevance.ep_model.thriftscala.EPScoringResponse
import com.X.relevance.ep_model.thriftscala.Record
import com.X.stitch.Stitch
import com.X.util.Future
import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._
import scala.util.Success

case class ScoredResponse(score: Double, featuresBreakdown: Option[String] = None)

/**
 * STP ML ranker trained using prehistoric ML framework
 */
@Singleton
class EpStpScorer @Inject() (epScorer: EPScorer) {
  private def getScore(responses: List[EPScoringResponse]): Option[ScoredResponse] =
    responses.headOption
      .flatMap { response =>
        response.scores.flatMap {
          _.headOption.map(score => ScoredResponse(ScorerUtil.normalize(score)))
        }
      }

  def getScoredResponse(
    record: Record,
    details: Boolean = false
  ): Stitch[Option[ScoredResponse]] = {
    val scoringOptions = EPScoringOptions(
      addFeaturesBreakDown = details,
      addTransformerIntermediateRecords = details
    )
    val request = EPScoringRequest(auxFeatures = Some(Seq(record)), options = Some(scoringOptions))

    Stitch.callFuture(
      BinaryThriftCodec[thrift.EPScoringRequest]
        .invert(BinaryScalaCodec(EPScoringRequest).apply(request))
        .map { thriftRequest: thrift.EPScoringRequest =>
          val responsesF = epScorer
            .score(List(thriftRequest).asJava)
            .map(
              _.asScala.toList
                .map(response =>
                  BinaryScalaCodec(EPScoringResponse)
                    .invert(BinaryThriftCodec[thrift.EPScoringResponse].apply(response)))
                .collect { case Success(response) => response }
            )
          responsesF.map(getScore)
        }
        .getOrElse(Future(None)))
  }
}

object EpStpScorer {
  val WithFeaturesBreakDown = false
}
