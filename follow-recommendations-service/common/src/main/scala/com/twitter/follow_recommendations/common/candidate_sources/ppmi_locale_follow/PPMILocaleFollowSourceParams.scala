package com.twitter.follow_recommendations.common.candidate_sources.ppmi_locale_follow

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

class PPMILocaleFollowSourceParams {}
object PPMILocaleFollowSourceParams {
  case object LocaleToEtwittercludeFromRecommendation
      etwittertends FSParam[Seq[String]](
        "ppmilocale_follow_source_locales_to_etwitterclude_from_recommendation",
        default = Seq.empty)

  case object CandidateSourceEnabled
      etwittertends FSParam[Boolean]("ppmilocale_follow_source_enabled", true)

  case object CandidateSourceWeight
      etwittertends FSBoundedParam[Double](
        "ppmilocale_follow_source_candidate_source_weight",
        default = 1,
        min = 0.001,
        matwitter = 2000)
}
