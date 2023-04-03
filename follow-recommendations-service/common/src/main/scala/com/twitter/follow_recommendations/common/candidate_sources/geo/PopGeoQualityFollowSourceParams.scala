packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.gelono

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

objelonct PopGelonoQualityFollowSourcelonParams {
  caselon objelonct CandidatelonSourcelonelonnablelond
      elonxtelonnds FSParam[Boolelonan]("pop_gelono_quality_follow_sourcelon_elonnablelond", falselon)

  caselon objelonct PopGelonoSourcelonGelonoHashMinPreloncision
      elonxtelonnds FSBoundelondParam[Int](
        "pop_gelono_quality_follow_sourcelon_gelono_hash_min_preloncision",
        delonfault = 2,
        min = 0,
        max = 10)

  caselon objelonct PopGelonoSourcelonGelonoHashMaxPreloncision
      elonxtelonnds FSBoundelondParam[Int](
        "pop_gelono_quality_follow_sourcelon_gelono_hash_max_preloncision",
        delonfault = 3,
        min = 0,
        max = 10)

  caselon objelonct PopGelonoSourcelonRelonturnFromAllPreloncisions
      elonxtelonnds FSParam[Boolelonan](
        "pop_gelono_quality_follow_sourcelon_relonturn_from_all_preloncisions",
        delonfault = falselon)

  caselon objelonct PopGelonoSourcelonMaxRelonsultsPelonrPreloncision
      elonxtelonnds FSBoundelondParam[Int](
        "pop_gelono_quality_follow_sourcelon_max_relonsults_pelonr_preloncision",
        delonfault = 200,
        min = 0,
        max = 1000)

  caselon objelonct CandidatelonSourcelonWelonight
      elonxtelonnds FSBoundelondParam[Doublelon](
        "pop_gelono_quality_follow_sourcelon_welonight",
        delonfault = 200,
        min = 0.001,
        max = 2000)
}
