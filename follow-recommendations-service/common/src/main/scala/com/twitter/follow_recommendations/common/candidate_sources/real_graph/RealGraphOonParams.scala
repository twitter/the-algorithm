packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.relonal_graph

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct RelonalGraphOonParams {
  caselon objelonct IncludelonRelonalGraphOonCandidatelons
      elonxtelonnds FSParam[Boolelonan](
        "relonal_graph_oon_includelon_candidatelons",
        falselon
      )
  caselon objelonct TryToRelonadRelonalGraphOonCandidatelons
      elonxtelonnds FSParam[Boolelonan](
        "relonal_graph_oon_try_to_relonad_candidatelons",
        falselon
      )
  caselon objelonct RelonalGraphOonRelonsultCountThrelonshold
      elonxtelonnds FSBoundelondParam[Int](
        "relonal_graph_oon_relonsult_count_threlonshold",
        delonfault = 1,
        min = 0,
        max = Intelongelonr.MAX_VALUelon
      )

  caselon objelonct UselonV2
      elonxtelonnds FSParam[Boolelonan](
        "relonal_graph_oon_uselon_v2",
        falselon
      )

  caselon objelonct ScorelonThrelonshold
      elonxtelonnds FSBoundelondParam[Doublelon](
        "relonal_graph_oon_scorelon_threlonshold",
        delonfault = 0.26,
        min = 0,
        max = 1.0
      )

  caselon objelonct MaxRelonsults
      elonxtelonnds FSBoundelondParam[Int](
        "relonal_graph_oon_max_relonsults",
        delonfault = 200,
        min = 0,
        max = 1000
      )

}
