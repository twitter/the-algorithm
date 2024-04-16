package com.tw ter.t etyp e

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.thr ftscala.F eldByPath
 mport org.apac .thr ft.protocol.TF eld
 mport com.tw ter.context.Tw terContext

package object hydrator {
  type T etDataValueHydrator = ValueHydrator[T etData, T etQuery.Opt ons]
  type T etDataEd Hydrator = Ed Hydrator[T etData, T etQuery.Opt ons]

  def f eldByPath(f elds: TF eld*): F eldByPath = F eldByPath(f elds.map(_. d))

  val Tw terContext: Tw terContext =
    com.tw ter.context.Tw terContext(com.tw ter.t etyp e.Tw terContextPerm )
}
