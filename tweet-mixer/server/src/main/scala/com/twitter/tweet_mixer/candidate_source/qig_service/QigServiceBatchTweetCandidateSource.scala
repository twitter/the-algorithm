package com.twitter.tweet_mixer.candidate_source.qig_service

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.search.query_interaction_graph.service.{thriftscala => t}
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Calls QIG Service's getBatchCandidates endpoint.
 *
 * Since Candidate Sources need to return a flattened list of candidates, we wrap the TweetCandidate
 * into a case class containing the relative rank of the Tweet and the rank of the request in the
 * batch.
 */
@Singleton
class QigServiceBatchTweetCandidateSource @Inject() (
  qigService: t.QigService.MethodPerEndpoint)
    extends CandidateSource[Seq[t.QigRequest], QigTweetCandidate] {

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "QigServiceBatchTweets")

  override def apply(requests: Seq[t.QigRequest]): Stitch[Seq[QigTweetCandidate]] = {
    Stitch
      .callFuture(qigService.getBatchCandidates(requests))
      .map { responses =>
        if (responses.size == requests.size) {
          responses.zip(requests).map {
            case (response, request) =>
              response.tweetCandidates match {
                case Some(tweetCandidates) =>
                  tweetCandidates.map { tweetCandidate =>
                    QigTweetCandidate(tweetCandidate, request.query.getOrElse(""))
                  }
                case _ => Seq.empty
              }
          }
        } else {
          throw PipelineFailure(
            IllegalStateFailure,
            s"QigService Batch endpoint returned ${responses.size} results but was expecting ${requests.size}"
          )
        }
      }
      // round robbin flatten
      .map { resultLists =>
        resultLists
          .flatMap(list => list.zipWithIndex)
          .sortBy(_._2)
          .map(_._1)
      }
  }
}
