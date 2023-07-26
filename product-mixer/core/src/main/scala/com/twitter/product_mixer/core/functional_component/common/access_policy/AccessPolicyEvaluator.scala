package com.twittew.pwoduct_mixew.cowe.functionaw_component.common.access_powicy

/**
 * contwows h-how access powicies a-awe appwied t-to awwow/weject a-a wequest
 */
object a-accesspowicyevawuatow {
  d-def evawuate(pwoductaccesspowicies: s-set[accesspowicy], >_< u-usewwdapgwoups: set[stwing]): boowean =
    pwoductaccesspowicies.exists {
      case awwowedwdapgwoups(awwowedgwoups) => a-awwowedgwoups.exists(usewwdapgwoups.contains)
      case _: bwockevewything => fawse
    }
}
