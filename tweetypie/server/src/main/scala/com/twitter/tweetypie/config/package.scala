package com.tw ter.t etyp e

 mport com.tw ter.context.thr ftscala.V e r

package object conf g {
  // Br ng T etyp e perm ted Tw terContext  nto scope
  pr vate[conf g] val Tw terContext =
    com.tw ter.context.Tw terContext(com.tw ter.t etyp e.Tw terContextPerm )

  def getApp d: Opt on[App d] = Tw terContext().getOrElse(V e r()).cl entAppl cat on d
}
