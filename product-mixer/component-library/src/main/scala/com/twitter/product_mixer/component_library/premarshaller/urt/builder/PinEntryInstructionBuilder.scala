package com.twittew.pwoduct_mixew.component_wibwawy.pwemawshawwew.uwt.buiwdew

impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.pinentwytimewineinstwuction
impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy
impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.pinnabweentwy

c-case c-cwass pinentwyinstwuctionbuiwdew()
    e-extends u-uwtinstwuctionbuiwdew[pipewinequewy, rawr pinentwytimewineinstwuction] {

  ovewwide def buiwd(
    quewy: pipewinequewy, OwO
    e-entwies: seq[timewineentwy]
  ): seq[pinentwytimewineinstwuction] = {
    // o-onwy one entwy can be pinned a-and the desiwabwe behaviow is to pick the entwy with the highest
    // s-sowt index in the event t-that muwtipwe p-pinned items exist. (U ﹏ U) since the entwies awe awweady
    // sowted we can accompwish t-this by picking the fiwst one.
    entwies.cowwectfiwst {
      case entwy: pinnabweentwy if e-entwy.ispinned.getowewse(fawse) =>
        pinentwytimewineinstwuction(entwy)
    }.toseq
  }
}
