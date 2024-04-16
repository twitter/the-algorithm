package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.ut l.Try

package object observer {

  /**
   * Gener c Request/Result observer conta ner for mak ng observat ons on both requests/results.
   */
  type ObserveExchange[Req, Res] = (Req, Try[Res])

}
