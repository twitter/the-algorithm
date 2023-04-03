packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.ppmi_localelon_follow

import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

class PPMILocalelonFollowSourcelonParams {}
objelonct PPMILocalelonFollowSourcelonParams {
  caselon objelonct LocalelonToelonxcludelonFromReloncommelonndation
      elonxtelonnds FSParam[Selonq[String]](
        "ppmilocalelon_follow_sourcelon_localelons_to_elonxcludelon_from_reloncommelonndation",
        delonfault = Selonq.elonmpty)

  caselon objelonct CandidatelonSourcelonelonnablelond
      elonxtelonnds FSParam[Boolelonan]("ppmilocalelon_follow_sourcelon_elonnablelond", truelon)

  caselon objelonct CandidatelonSourcelonWelonight
      elonxtelonnds FSBoundelondParam[Doublelon](
        "ppmilocalelon_follow_sourcelon_candidatelon_sourcelon_welonight",
        delonfault = 1,
        min = 0.001,
        max = 2000)
}
