package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.compat.LegacySeqGroup
 mport com.tw ter.t etyp e.backends.Esc rb rd
 mport com.tw ter.t etyp e.thr ftscala.Esc rb rdEnt yAnnotat ons

object Esc rb rdAnnotat onRepos ory {
  type Type = T et => St ch[Opt on[Esc rb rdEnt yAnnotat ons]]

  def apply(annotate: Esc rb rd.Annotate): Type =
    // use a `SeqGroup` to group t  future-calls toget r, even though t y can be
    // executed  ndependently,  n order to  lp keep hydrat on bet en d fferent t ets
    //  n sync, to  mprove batch ng  n hydrators wh ch occur later  n t  p pel ne.
    t et =>
      St ch
        .call(t et, LegacySeqGroup(annotate.l ftSeq))
        .map { annotat ons =>
           f (annotat ons. sEmpty) None
          else So (Esc rb rdEnt yAnnotat ons(annotat ons))
        }
}
