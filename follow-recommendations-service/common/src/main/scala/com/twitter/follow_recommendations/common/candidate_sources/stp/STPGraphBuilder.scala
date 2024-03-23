package com.ExTwitter.follow_recommendations.common.candidate_sources.stp

import com.ExTwitter.finagle.stats.Stat
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.models.HasRecentFollowedUserIds
import com.ExTwitter.follow_recommendations.common.models.STPGraph
import com.ExTwitter.product_mixer.core.model.marshalling.request.HasClientContext
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.HasParams
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class STPGraphBuilder @Inject() (
  stpFirstDegreeFetcher: STPFirstDegreeFetcher,
  stpSecondDegreeFetcher: STPSecondDegreeFetcher,
  statsReceiver: StatsReceiver) {
  private val stats: StatsReceiver = statsReceiver.scope(this.getClass.getSimpleName)
  private val firstDegreeStat: Stat = stats.stat("first_degree_edges")
  private val secondDegreeStat: Stat = stats.stat("second_degree_edges")
  def apply(
    target: HasClientContext with HasParams with HasRecentFollowedUserIds
  ): Stitch[STPGraph] = stpFirstDegreeFetcher
    .getFirstDegreeEdges(target).flatMap { firstDegreeEdges =>
      firstDegreeStat.add(firstDegreeEdges.size)
      stpSecondDegreeFetcher
        .getSecondDegreeEdges(target, firstDegreeEdges).map { secondDegreeEdges =>
          secondDegreeStat.add(firstDegreeEdges.size)
          STPGraph(firstDegreeEdges.toList, secondDegreeEdges.toList)
        }
    }
}
