package com.twittew.visibiwity.configapi.pawams

impowt com.twittew.timewines.configapi.bucketname
i-impowt com.twittew.timewines.configapi.expewiment
i-impowt com.twittew.timewines.configapi.usefeatuwecontext

o-object v-visibiwityexpewiment {
  v-vaw c-contwow = "contwow"
  v-vaw tweatment = "tweatment"
}

a-abstwact cwass visibiwityexpewiment(expewimentkey: stwing)
    extends expewiment(expewimentkey)
    with u-usefeatuwecontext {
  vaw tweatmentbucket: stwing = v-visibiwityexpewiment.tweatment
  ovewwide def e-expewimentbuckets: set[bucketname] = set(tweatmentbucket)
  vaw contwowbucket: s-stwing = visibiwityexpewiment.contwow
  ovewwide d-def contwowbuckets: s-set[bucketname] = set(contwowbucket)
}
