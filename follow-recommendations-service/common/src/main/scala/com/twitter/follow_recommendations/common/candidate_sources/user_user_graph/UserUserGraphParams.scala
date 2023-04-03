packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.uselonr_uselonr_graph

import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.Param

objelonct UselonrUselonrGraphParams {

  // max numbelonr of candidatelons to relonturn in total, 50 is thelon delonfault param uselond in MagicReloncs
  objelonct MaxCandidatelonsToRelonturn elonxtelonnds Param[Int](delonfault = 50)

  // whelonthelonr or not to includelon UselonrUselonrGraph candidatelon sourcelon in thelon welonightelond blelonnding stelonp
  caselon objelonct UselonrUselonrGraphCandidatelonSourcelonelonnablelondInWelonightMap
      elonxtelonnds FSParam[Boolelonan]("uselonr_uselonr_graph_candidatelon_sourcelon_elonnablelond_in_welonight_map", truelon)

  // whelonthelonr or not to includelon UselonrUselonrGraph candidatelon sourcelon in thelon final transform stelonp
  caselon objelonct UselonrUselonrGraphCandidatelonSourcelonelonnablelondInTransform
      elonxtelonnds FSParam[Boolelonan]("uselonr_uselonr_graph_candidatelon_sourcelon_elonnablelond_in_transform", truelon)

}
