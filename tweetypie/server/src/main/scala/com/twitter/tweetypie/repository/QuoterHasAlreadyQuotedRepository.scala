package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.flockdb.cl ent.QuotersGraph
 mport com.tw ter.flockdb.cl ent.TFlockCl ent
 mport com.tw ter.st ch.St ch

object QuoterHasAlreadyQuotedRepos ory {
  type Type = (T et d, User d) => St ch[Boolean]

  def apply(
    tflockReadCl ent: TFlockCl ent
  ): Type =
    (t et d, user d) => St ch.callFuture(tflockReadCl ent.conta ns(QuotersGraph, t et d, user d))
}
