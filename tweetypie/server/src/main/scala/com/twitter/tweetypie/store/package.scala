package com.tw ter.t etyp e

 mport com.fasterxml.jackson.core.JsonGenerator
 mport com.tw ter.t etyp e.thr ftscala.Cac dT et
 mport com.tw ter.context.Tw terContext

package object store {
  type JsonGen = JsonGenerator => Un 

  // Br ng T etyp e perm ted Tw terContext  nto scope
  val Tw terContext: Tw terContext =
    com.tw ter.context.Tw terContext(com.tw ter.t etyp e.Tw terContextPerm )

  def cac dT etFromUnhydratedT et(t et: T et): Cac dT et =
    Cac dT et(t et = t et)
}
