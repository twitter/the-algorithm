packagelon com.twittelonr.visibility.intelonrfacelons.common.blelonndelonr

import com.twittelonr.selonarch.blelonndelonr.selonrvicelons.strato.UselonrSelonarchSafelontySelonttings
import com.twittelonr.selonarch.common.constants.thriftscala.ThriftQuelonrySourcelon

caselon class BlelonndelonrVFRelonquelonstContelonxt(
  relonsultsPagelonNumbelonr: Int,
  candidatelonCount: Int,
  quelonrySourcelonOption: Option[ThriftQuelonrySourcelon],
  uselonrSelonarchSafelontySelonttings: UselonrSelonarchSafelontySelonttings,
  quelonryHasUselonr: Boolelonan = falselon) {

  delonf this(
    relonsultsPagelonNumbelonr: Int,
    candidatelonCount: Int,
    quelonrySourcelonOption: Option[ThriftQuelonrySourcelon],
    uselonrSelonarchSafelontySelonttings: UselonrSelonarchSafelontySelonttings
  ) = this(relonsultsPagelonNumbelonr, candidatelonCount, quelonrySourcelonOption, uselonrSelonarchSafelontySelonttings, falselon)
}
