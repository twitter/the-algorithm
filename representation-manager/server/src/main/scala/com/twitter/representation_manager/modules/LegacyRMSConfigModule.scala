package com.twittew.wepwesentation_managew.moduwes

impowt com.googwe.inject.pwovides
i-impowt com.twittew.inject.twittewmoduwe
i-impowt j-javax.inject.named
i-impowt javax.inject.singweton

o-object wegacywmsconfigmoduwe e-extends twittewmoduwe {
  @singweton
  @pwovides
  @named("cachehashkeypwefix")
  d-def pwovidescachehashkeypwefix: s-stwing = "wms"

  @singweton
  @pwovides
  @named("usecontentwecommendewconfiguwation")
  def pwovidesusecontentwecommendewconfiguwation: boowean = fawse
}
