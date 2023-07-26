package com.twittew.pwoduct_mixew.component_wibwawy.pwemawshawwew.uwt.buiwdew

impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.addentwiestimewineinstwuction
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy

c-case cwass addentwiesinstwuctionbuiwdew[quewy <: p-pipewinequewy](
  o-ovewwide vaw i-incwudeinstwuction: i-incwudeinstwuction[quewy] = awwaysincwude)
    extends uwtinstwuctionbuiwdew[quewy, -.- addentwiestimewineinstwuction] {

  ovewwide d-def buiwd(
    quewy: quewy, ^^;;
    entwies: s-seq[timewineentwy]
  ): seq[addentwiestimewineinstwuction] = {
    i-if (entwies.nonempty && incwudeinstwuction(quewy, >_< entwies))
      seq(addentwiestimewineinstwuction(entwies))
    e-ewse seq.empty
  }
}
