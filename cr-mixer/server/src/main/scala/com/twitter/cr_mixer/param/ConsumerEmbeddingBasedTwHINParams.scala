package com.twittew.cw_mixew.pawam

impowt com.twittew.cw_mixew.modew.modewconfig
i-impowt com.twittew.timewines.configapi.baseconfig
i-impowt com.twittew.timewines.configapi.baseconfigbuiwdew
i-impowt c-com.twittew.timewines.configapi.fsname
i-impowt c-com.twittew.timewines.configapi.fspawam
i-impowt c-com.twittew.timewines.configapi.pawam

impowt com.twittew.timewines.configapi.featuweswitchovewwideutiw

object consumewembeddingbasedtwhinpawams {
  object modewidpawam
      e-extends fspawam[stwing](
        nyame = "consumew_embedding_based_twhin_modew_id", rawr x3
        defauwt = m-modewconfig.consumewbasedtwhinweguwawupdateaww20221024, nyaa~~
      ) // nyote: t-this defauwt vawue does nyot match with modewids yet. /(^•ω•^) this fs is a-a pwacehowdew

  vaw awwpawams: s-seq[pawam[_] with f-fsname] = seq(
    modewidpawam
  )

  wazy vaw config: baseconfig = {
    vaw s-stwingfsovewwides =
      featuweswitchovewwideutiw.getstwingfsovewwides(
        modewidpawam
      )

    baseconfigbuiwdew()
      .set(stwingfsovewwides: _*)
      .buiwd()
  }
}
