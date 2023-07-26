package com.twittew.fowwow_wecommendations.common.modews

twait haspweviouswecommendationscontext {

  d-def pweviouswywecommendedusewids: s-set[wong]

  d-def pweviouswyfowwowedusewids: s-set[wong]

  d-def skippedfowwows: s-set[wong] = {
    p-pweviouswywecommendedusewids.diff(pweviouswyfowwowedusewids)
  }
}
