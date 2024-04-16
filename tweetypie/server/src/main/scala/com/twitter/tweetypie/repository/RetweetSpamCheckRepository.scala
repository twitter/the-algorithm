package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.serv ce.gen.scarecrow.{thr ftscala => scarecrow}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.backends.Scarecrow

object Ret etSpamC ckRepos ory {
  type Type = scarecrow.Ret et => St ch[scarecrow.T eredAct on]

  def apply(c ckRet et: Scarecrow.C ckRet et): Type =
    ret et => St ch.callFuture(c ckRet et(ret et))
}
