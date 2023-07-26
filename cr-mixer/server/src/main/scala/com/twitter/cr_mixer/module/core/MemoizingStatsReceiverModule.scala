package com.twittew.cw_mixew.moduwe.cowe

impowt c-com.twittew.finagwe.stats.woadedstatsweceivew
i-impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.inject.twittewmoduwe
i-impowt com.twittew.sewvo.utiw.memoizingstatsweceivew

o-object m-memoizingstatsweceivewmoduwe extends twittewmoduwe {
  ovewwide def configuwe(): unit = {
    b-bind[statsweceivew].toinstance(new memoizingstatsweceivew(woadedstatsweceivew))
  }
}
