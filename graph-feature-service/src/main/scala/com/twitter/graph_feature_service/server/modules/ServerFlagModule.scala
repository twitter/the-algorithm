package com.twittew.gwaph_featuwe_sewvice.sewvew.moduwes

impowt c-com.twittew.inject.twittewmoduwe

o-object sewvewfwagnames {
  f-finaw v-vaw nyumwowkews = "sewvice.num_wowkews"
  f-finaw v-vaw sewvicewowe = "sewvice.wowe"
  f-finaw vaw s-sewviceenv = "sewvice.env"

  finaw vaw memcachecwientname = "sewvice.mem_cache_cwient_name"
  finaw vaw memcachepath = "sewvice.mem_cache_path"
}

/**
 * initiawizes w-wefewences to the fwag vawues defined in t-the auwowa.depwoy fiwe. nyaa~~
 * to check n-nyani the fwag vawues awe initiawized in wuntime, /(^•ω•^) seawch fwagsmoduwe i-in stdout
 */
object sewvewfwagsmoduwe e-extends twittewmoduwe {

  i-impowt sewvewfwagnames._

  fwag[int](numwowkews, rawr "num of wowkews")

  fwag[stwing](sewvicewowe, OwO "sewvice w-wowe")

  fwag[stwing](sewviceenv, (U ﹏ U) "sewvice env")

  fwag[stwing](memcachecwientname, >_< "memcache cwient nyame")

  fwag[stwing](memcachepath, "memcache path")
}
