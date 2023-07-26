package com.twittew.fowwow_wecommendations.moduwes

impowt com.twittew.fowwow_wecommendations.pwoducts.pwodpwoductwegistwy
i-impowt c-com.twittew.fowwow_wecommendations.pwoducts.common.pwoductwegistwy
i-impowt com.twittew.inject.twittewmoduwe
i-impowt j-javax.inject.singweton

o-object p-pwoductwegistwymoduwe e-extends twittewmoduwe {
  ovewwide pwotected def configuwe(): unit = {
    b-bind[pwoductwegistwy].to[pwodpwoductwegistwy].in[singweton]
  }
}
