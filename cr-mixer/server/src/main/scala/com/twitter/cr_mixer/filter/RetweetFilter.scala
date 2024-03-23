package com.ExTwitter.cr_mixer.filter

import com.ExTwitter.cr_mixer.model.CandidateGeneratorQuery
import com.ExTwitter.cr_mixer.model.InitialCandidate
import com.ExTwitter.cr_mixer.param.UtegTweetGlobalParams
import com.ExTwitter.util.Future

import javax.inject.Inject
import javax.inject.Singleton

/***
 * Filters candidates that are retweets
 */
@Singleton
case class RetweetFilter @Inject() () extends FilterBase {
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
