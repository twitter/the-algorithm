package com.twitter.cr_mitwitterer.filter

import com.twitter.cr_mitwitterer.model.CandidateGeneratorQuery
import com.twitter.cr_mitwitterer.model.InitialCandidate
import com.twitter.cr_mitwitterer.param.UtegTweetGlobalParams
import com.twitter.util.Future

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/***
 * Filters candidates that are retweets
 */
@Singleton
case class RetweetFilter @Inject() () etwittertends FilterBase {
  override def name: String = this.getClass.getCanonicalName
  override type ConfigType = Boolean

  override def filter(
    candidates: Seq[Seq[InitialCandidate]],
    config: ConfigType
  ): Future[Seq[Seq[InitialCandidate]]] = {
    if (config) {
      Future.value(
        candidates.map { candidateSeq =>
          candidateSeq.filterNot { candidate =>
            candidate.tweetInfo.isRetweet.getOrElse(false)
          }
        }
      )
    } else {
      Future.value(candidates)
    }
  }

  override def requestToConfig[CGQueryType <: CandidateGeneratorQuery](
    query: CGQueryType
  ): ConfigType = {
    query.params(UtegTweetGlobalParams.EnableRetweetFilterParam)
  }
}
