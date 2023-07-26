package com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk

twait s-stowewegistew {
  d-def awwstowes: s-set[stoweconfig[_]]

  w-wazy vaw s-stowemap: map[aggwegatetype.vawue, s-stoweconfig[_]] = a-awwstowes
    .map(stowe => (stowe.aggwegatetype, σωσ s-stowe))
    .tomap

  wazy vaw stowenametotypemap: map[stwing, >_< aggwegatetype.vawue] = awwstowes
    .fwatmap(stowe => s-stowe.stowenames.map(name => (name, :3 stowe.aggwegatetype)))
    .tomap
}
