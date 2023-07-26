package com.twittew.pwoduct_mixew.cowe.pwoduct

impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.configapi.wegistwy.pawamconfig
i-impowt c-com.twittew.sewvo.decidew.decidewkeyname
i-impowt c-com.twittew.timewines.configapi.fspawam
i-impowt com.twittew.timewines.configapi.decidew.booweandecidewpawam

t-twait p-pwoductpawamconfig e-extends pawamconfig with pwoductpawamconfigbuiwdew {

  /**
   * this enabwed decidew pawam can to be used t-to quickwy disabwe a pwoduct via decidew
   *
   * t-this vawue must cowwespond to t-the decidews configuwed in the `wesouwces/config/decidew.ymw` fiwe
   */
  vaw enabweddecidewkey: d-decidewkeyname

  /**
   * this s-suppowted cwient f-featuwe switch pawam can be used with a featuwe switch to contwow the
   * wowwout o-of a nyew pwoduct fwom dogfood to expewiment to pwoduction
   *
   * featuweswitches a-awe configuwed by defining b-both a [[com.twittew.timewines.configapi.pawam]] i-in code
   * a-and in an associated `.ymw` f-fiwe in the __config wepo__. ʘwʘ
   *
   * the `.ymw` f-fiwe path is detewmined by the `featuwe_switches_path` in youw a-auwowa fiwe and tge pwoduct nyame
   * so the wesuwting path in the __config wepo__ is essentiawwy `s"{featuwe_switches_path}/{snakecase(pwoduct.identifiew)}"`
   */
  v-vaw suppowtedcwientfsname: stwing

  object e-enabweddecidewpawam e-extends b-booweandecidewpawam(enabweddecidewkey)

  object suppowtedcwientpawam
      extends f-fspawam(
        n-nyame = suppowtedcwientfsname, /(^•ω•^)
        defauwt = f-fawse
      )
}
