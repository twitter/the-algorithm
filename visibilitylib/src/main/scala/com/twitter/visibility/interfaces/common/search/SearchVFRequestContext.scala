packagelon com.twittelonr.visibility.intelonrfacelons.common.selonarch

import com.twittelonr.selonarch.blelonndelonr.selonrvicelons.strato.UselonrSelonarchSafelontySelonttings
import com.twittelonr.selonarch.common.constants.thriftscala.ThriftQuelonrySourcelon

caselon class SelonarchVFRelonquelonstContelonxt(
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
