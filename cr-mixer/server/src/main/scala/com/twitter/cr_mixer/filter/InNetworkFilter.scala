package com.ExTwitter.cr_mixer.filter

import com.ExTwitter.cr_mixer.model.CandidateGeneratorQuery
import com.ExTwitter.cr_mixer.model.InitialCandidate
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.UtegTweetCandidateGeneratorQuery
import com.ExTwitter.cr_mixer.param.UtegTweetGlobalParams
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.util.StatsUtil
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Future
import com.ExTwitter.wtf.candidate.thriftscala.CandidateSeq

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/***
 * Filters in-network tweets
 */
@Singleton
case class InNetworkFilter @Inject() (
  @Named(ModuleNames.RealGraphInStore) realGraphStoreMh: ReadableStore[UserId, CandidateSeq],
  globalStats: StatsReceiver)
    extends FilterBase {
  override val name: String = this.getClass.getCanonicalName
  import InNetworkFilter._

  override type ConfigType = FilterConfig
  private val stats: StatsReceiver = globalStats.scope(this.getClass.getCanonicalName)
  private val filterCandidatesStats = stats.scope("filter_candidates")

  override def filter(
    candidates: Seq[Seq[InitialCandidate]],
    filterConfig: FilterConfig,
  ): Future[Seq[Seq[InitialCandidate]]] = {
    StatsUtil.trackItemsStats(filterCandidatesStats) {
      filterCandidates(candidates, filterConfig)
    }
  }

  private def filterCandidates(
    candidates: Seq[Seq[InitialCandidate]],
    filterConfig: FilterConfig,
  ): Future[Seq[Seq[InitialCandidate]]] = {

    if (!filterConfig.enableInNetworkFilter) {
      Future.value(candidates)
    } else {
      filterConfig.userIdOpt match {
        case Some(userId) =>
          realGraphStoreMh
            .get(userId).map(_.map(_.candidates.map(_.userId)).getOrElse(Seq.empty).toSet).map {
              realGraphInNetworkAuthorsSet =>
                candidates.map(_.filterNot { candidate =>
                  realGraphInNetworkAuthorsSet.contains(candidate.tweetInfo.authorId)
                })
            }
        case None => Future.value(candidates)
      }
    }
  }

  override def requestToConfig[CGQueryType <: CandidateGeneratorQuery](
    request: CGQueryType
  ): FilterConfig = {
    request match {
      case UtegTweetCandidateGeneratorQuery(userId, _, _, _, _, params, _) =>
        FilterConfig(Some(userId), params(UtegTweetGlobalParams.EnableInNetworkFilterParam))
      case _ => FilterConfig(None, false)
    }
  }
}

object InNetworkFilter {
  case class FilterConfig(
    userIdOpt: Option[UserId],
    enableInNetworkFilter: Boolean)
}
