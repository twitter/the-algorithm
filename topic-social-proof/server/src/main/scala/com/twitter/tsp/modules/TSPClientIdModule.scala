package com.twittew.tsp.moduwes

impowt com.googwe.inject.pwovides
i-impowt com.twittew.finagwe.thwift.cwientid
i-impowt c-com.twittew.inject.twittewmoduwe
i-impowt javax.inject.singweton

o-object tspcwientidmoduwe e-extends t-twittewmoduwe {
  p-pwivate vaw cwientidfwag = fwag("thwift.cwientid", >_< "topic-sociaw-pwoof.pwod", :3 "thwift cwient id")

  @pwovides
  @singweton
  d-def pwovidescwientid: cwientid = cwientid(cwientidfwag())
}
