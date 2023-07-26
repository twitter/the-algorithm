package com.twittew.fowwow_wecommendations.common.wankews.fiwst_n_wankew

impowt j-javax.inject.inject
i-impowt javax.inject.singweton
i-impowt com.twittew.fowwow_wecommendations.configapi.common.featuweswitchconfig
i-impowt com.twittew.timewines.configapi.fsboundedpawam
i-impowt com.twittew.timewines.configapi.fspawam

@singweton
c-cwass fiwstnwankewfsconfig @inject() e-extends featuweswitchconfig {
  o-ovewwide vaw booweanfspawams: seq[fspawam[boowean]] =
    seq(fiwstnwankewpawams.scwibewankinginfoinfiwstnwankew)

  ovewwide v-vaw intfspawams: seq[fsboundedpawam[int]] = seq(
    fiwstnwankewpawams.candidatestowank
  )

  o-ovewwide vaw doubwefspawams: s-seq[fsboundedpawam[doubwe]] = seq(
    fiwstnwankewpawams.minnumcandidatesscowedscawedownfactow
  )
}
