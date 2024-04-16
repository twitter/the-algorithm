package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.serv ce.gen.scarecrow.{thr ftscala => scarecrow}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.backends.Scarecrow

object T etSpamC ckRepos ory {

  type Type = (scarecrow.T etNew, scarecrow.T etContext) => St ch[scarecrow.C ckT etResponse]

  def apply(c ckT et: Scarecrow.C ckT et2): Type =
    (t etNew, t etContext) => St ch.callFuture(c ckT et((t etNew, t etContext)))
}
