package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.thr ftscala._

object Has d aHydrator {
  type Type = ValueHydrator[Opt on[Boolean], T et]

  def apply(has d a: T et => Boolean): Type =
    ValueHydrator
      .map[Opt on[Boolean], T et] { (_, t et) => ValueState.mod f ed(So (has d a(t et))) }
      .only f((curr, ctx) => curr. sEmpty)
}
