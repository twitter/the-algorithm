package com.tw ter.t  l neranker

 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l nes.conf gap 

package object core {
  type FutureDependencyTransfor r[-U, +V] = conf gap .FutureDependencyTransfor r[RecapQuery, U, V]
  object FutureDependencyTransfor r
      extends conf gap .FutureDependencyTransfor rFunct ons[RecapQuery]

  type DependencyTransfor r[-U, +V] = conf gap .DependencyTransfor r[RecapQuery, U, V]
  object DependencyTransfor r extends conf gap .DependencyTransfor rFunct ons[RecapQuery]
}
