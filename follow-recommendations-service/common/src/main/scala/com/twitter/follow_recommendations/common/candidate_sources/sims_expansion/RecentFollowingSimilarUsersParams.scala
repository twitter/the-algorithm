packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.sims_elonxpansion

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct ReloncelonntFollowingSimilarUselonrsParams {
  caselon objelonct MaxFirstDelongrelonelonNodelons
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "sims_elonxpansion_reloncelonnt_following_max_first_delongrelonelon_nodelons",
        delonfault = 10,
        min = 0,
        max = 200)
  caselon objelonct MaxSeloncondaryDelongrelonelonelonxpansionPelonrNodelon
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "sims_elonxpansion_reloncelonnt_following_max_seloncondary_delongrelonelon_nodelons",
        delonfault = 40,
        min = 0,
        max = 200)
  caselon objelonct MaxRelonsults
      elonxtelonnds FSBoundelondParam[Int](
        namelon = "sims_elonxpansion_reloncelonnt_following_max_relonsults",
        delonfault = 200,
        min = 0,
        max = 200)
  caselon objelonct TimelonstampIntelongratelond
      elonxtelonnds FSParam[Boolelonan](
        namelon = "sims_elonxpansion_reloncelonnt_following_intelong_timelonstamp",
        delonfault = falselon)
}
