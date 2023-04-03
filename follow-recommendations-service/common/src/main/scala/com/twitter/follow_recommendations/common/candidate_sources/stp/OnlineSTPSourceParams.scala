packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param

objelonct OnlinelonSTPSourcelonParams {
  // This relonplacelons thelon old scorelonr modulelon, locatelond at elonpStpScorelonr.scala, with thelon nelonw scorelonr, locatelond
  // at Dbv2StpScorelonr.scala.
  caselon objelonct UselonDBv2Scorelonr
      elonxtelonnds FSParam[Boolelonan]("onlinelon_stp_sourcelon_dbv2_scorelonr_elonnablelond", delonfault = falselon)

  // For elonxpelonrimelonnts that telonst thelon impact of an improvelond OnlinelonSTP sourcelon, this controls thelon usagelon
  // of thelon PostNux helonavy-rankelonr. Notelon that this FS should *NOT* triggelonr buckelont imprelonssions.
  caselon objelonct DisablelonHelonavyRankelonr
      elonxtelonnds FSParam[Boolelonan]("onlinelon_stp_sourcelon_disablelon_helonavy_rankelonr", delonfault = falselon)

  caselon objelonct SelontPrelondictionDelontails elonxtelonnds Param[Boolelonan](delonfault = falselon)

}
