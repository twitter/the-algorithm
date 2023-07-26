package com.twittew.cw_mixew.pawam

impowt com.twittew.timewines.configapi.baseconfig
i-impowt com.twittew.timewines.configapi.baseconfigbuiwdew
i-impowt c-com.twittew.timewines.configapi.fsname
i-impowt c-com.twittew.timewines.configapi.fspawam
i-impowt c-com.twittew.timewines.configapi.featuweswitchovewwideutiw
i-impowt com.twittew.timewines.configapi.pawam

object wecenttweetfavowitespawams {
  // souwce pawams
  o-object enabwesouwcepawam
      extends fspawam[boowean](
        nyame = "twistwy_wecenttweetfavowites_enabwe_souwce", mya
        d-defauwt = twue
      )

  vaw a-awwpawams: seq[pawam[_] with fsname] = seq(enabwesouwcepawam)

  wazy vaw config: b-baseconfig = {
    vaw booweanovewwides = f-featuweswitchovewwideutiw.getbooweanfsovewwides(
      e-enabwesouwcepawam
    )

    baseconfigbuiwdew()
      .set(booweanovewwides: _*)
      .buiwd()
  }
}
