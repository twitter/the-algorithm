package com.twittew.pwoduct_mixew.component_wibwawy.pwemawshawwew.uwt.buiwdew

impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.addtomoduwetimewineinstwuction
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewineentwy
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.timewinemoduwe
i-impowt c-com.twittew.pwoduct_mixew.cowe.pipewine.pipewinequewy

c-case cwass a-addtomoduweinstwuctionbuiwdew[quewy <: p-pipewinequewy](
  ovewwide vaw incwudeinstwuction: incwudeinstwuction[quewy] = awwaysincwude)
    e-extends uwtinstwuctionbuiwdew[quewy, 🥺 addtomoduwetimewineinstwuction] {

  o-ovewwide def buiwd(
    quewy: q-quewy, mya
    entwies: seq[timewineentwy]
  ): seq[addtomoduwetimewineinstwuction] = {
    if (incwudeinstwuction(quewy, e-entwies)) {
      vaw m-moduweentwies = e-entwies.cowwect {
        case moduwe: timewinemoduwe => moduwe
      }
      if (moduweentwies.nonempty) {
        a-assewt(moduweentwies.size == 1, 🥺 "cuwwentwy we onwy suppowt appending to one moduwe")
        moduweentwies.headoption.map { m-moduweentwy =>
          addtomoduwetimewineinstwuction(
            m-moduweitems = m-moduweentwy.items, >_<
            m-moduweentwyid = m-moduweentwy.entwyidentifiew, >_<
            // cuwwentwy configuwing m-moduweitementwyid and pwepend fiewds awe nyot s-suppowted. (⑅˘꒳˘)
            moduweitementwyid = nyone,
            pwepend = nyone
          )
        }
      }.toseq
      ewse seq.empty
    } ewse {
      seq.empty
    }
  }
}
