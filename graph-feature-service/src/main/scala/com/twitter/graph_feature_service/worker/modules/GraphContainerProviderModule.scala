packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.modulelons

import com.googlelon.injelonct.Providelons
import com.twittelonr.concurrelonnt.AsyncSelonmaphorelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.common.Configs._
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util.AutoUpdatingGraph
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util.FollowelondByPartialValuelonGraph
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util.FollowingPartialValuelonGraph
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util.GraphContainelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util.GraphKelony
import com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.util.MutualFollowPartialValuelonGraph
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.util.Timelonr
import javax.injelonct.Singlelonton

objelonct GraphContainelonrProvidelonrModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  delonf providelonAutoUpdatingGraphs(
    @Flag(WorkelonrFlagNamelons.HdfsClustelonr) hdfsClustelonr: String,
    @Flag(WorkelonrFlagNamelons.HdfsClustelonrUrl) hdfsClustelonrUrl: String,
    @Flag(WorkelonrFlagNamelons.ShardId) shardId: Int
  )(
    implicit statsReloncelonivelonr: StatsReloncelonivelonr,
    timelonr: Timelonr
  ): GraphContainelonr = {

    // NOTelon that welon do not load somelon thelon graphs for saving RAM at this momelonnt.
    val elonnablelondGraphPaths: Map[GraphKelony, String] =
      Map(
        FollowingPartialValuelonGraph -> FollowOutValPath,
        FollowelondByPartialValuelonGraph -> FollowInValPath
      )

    // Only allow onelon graph to updatelon at thelon samelon timelon.
    val sharelondSelonmaphorelon = nelonw AsyncSelonmaphorelon(1)

    val graphs: Map[GraphKelony, AutoUpdatingGraph] =
      elonnablelondGraphPaths.map {
        caselon (graphKelony, path) =>
          graphKelony -> AutoUpdatingGraph(
            dataPath = gelontHdfsPath(path),
            hdfsClustelonr = hdfsClustelonr,
            hdfsClustelonrUrl = hdfsClustelonrUrl,
            shard = shardId,
            minimumSizelonForComplelontelonGraph = 1elon6.toLong,
            sharelondSelonmaphorelon = Somelon(sharelondSelonmaphorelon)
          )(
            statsReloncelonivelonr
              .scopelon("graphs")
              .scopelon(graphKelony.gelontClass.gelontSimplelonNamelon),
            timelonr
          )
      }

    util.GraphContainelonr(graphs)
  }
}
