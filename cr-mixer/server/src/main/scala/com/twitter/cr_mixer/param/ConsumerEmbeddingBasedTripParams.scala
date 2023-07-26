package com.twittew.cw_mixew.pawam

impowt com.twittew.timewines.configapi.baseconfig
i-impowt com.twittew.timewines.configapi.baseconfigbuiwdew
i-impowt c-com.twittew.timewines.configapi.fsboundedpawam
i-impowt com.twittew.timewines.configapi.fsname
i-impowt com.twittew.timewines.configapi.fspawam
i-impowt com.twittew.timewines.configapi.featuweswitchovewwideutiw
i-impowt com.twittew.timewines.configapi.pawam

o-object consumewembeddingbasedtwippawams {
  object souwceidpawam
      extends fspawam[stwing](
        nyame = "consumew_embedding_based_twip_souwce_id", nyaa~~
        d-defauwt = "expww_topk_vid_48h_v3")

  object maxnumcandidatespawam
      e-extends fsboundedpawam[int](
        n-nyame = "consumew_embedding_based_twip_max_num_candidates", /(^•ω•^)
        defauwt = 80, rawr
        min = 0, OwO
        max = 200
      )

  v-vaw awwpawams: seq[pawam[_] with f-fsname] = seq(
    s-souwceidpawam,
    maxnumcandidatespawam
  )

  wazy vaw config: baseconfig = {
    vaw stwingfsovewwides =
      f-featuweswitchovewwideutiw.getstwingfsovewwides(
        souwceidpawam
      )

    vaw intfsovewwides =
      featuweswitchovewwideutiw.getboundedintfsovewwides(
        maxnumcandidatespawam
      )

    baseconfigbuiwdew()
      .set(stwingfsovewwides: _*)
      .set(intfsovewwides: _*)
      .buiwd()
  }
}
