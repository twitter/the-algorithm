package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;

p-pubwic finaw cwass c-cachecommonutiw {
  p-pubwic s-static finaw stwing n-nyamed_max_cache_wesuwts = "maxcachewesuwts";

  p-pwivate cachecommonutiw() {
  }

  p-pubwic static b-boowean haswesuwts(eawwybiwdwesponse wesponse) {
    wetuwn wesponse.issetseawchwesuwts()
      && (wesponse.getseawchwesuwts().getwesuwts() != nyuww)
      && !wesponse.getseawchwesuwts().getwesuwts().isempty();
  }
}
