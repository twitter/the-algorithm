package com.tw ter.t etyp e.ut l

 mport com.tw ter.t etyp e.thr ftscala.Trans entCreateContext
 mport com.tw ter.t etyp e.thr ftscala.T etCreateContextKey
 mport com.tw ter.t etyp e.thr ftscala.T etCreateContextKey.Per scopeCreator d
 mport com.tw ter.t etyp e.thr ftscala.T etCreateContextKey.Per scope sL ve

object Trans entContextUt l {

  def toAdd  onalContext(context: Trans entCreateContext): Map[T etCreateContextKey, Str ng] =
    Seq
      .concat(
        context.per scope sL ve.map(Per scope sL ve -> _.toStr ng), // "true" or "false"
        context.per scopeCreator d.map(Per scopeCreator d -> _.toStr ng) // user d
      )
      .toMap
}
