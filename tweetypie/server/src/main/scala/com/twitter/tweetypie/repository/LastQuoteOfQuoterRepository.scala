package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.flockdb.cl ent.QuoteT ets ndexGraph
 mport com.tw ter.flockdb.cl ent.TFlockCl ent
 mport com.tw ter.flockdb.cl ent.UserT  l neGraph
 mport com.tw ter.st ch.St ch

object LastQuoteOfQuoterRepos ory {
  type Type = (T et d, User d) => St ch[Boolean]

  def apply(
    tflockReadCl ent: TFlockCl ent
  ): Type =
    (t et d, user d) => {
      // Select t  t ets authored by user d quot ng t et d.
      // By  ntersect ng t  t et quotes w h t  user's t ets.
      val quotesFromQuot ngUser = QuoteT ets ndexGraph
        .from(t et d)
        . ntersect(UserT  l neGraph.from(user d))

      St ch.callFuture(tflockReadCl ent.selectAll(quotesFromQuot ngUser).map(_.s ze <= 1))
    }
}
