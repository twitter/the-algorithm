package com.tw ter.t etyp e.thr ftscala.ent  es

 mport com.tw ter.t etyp e.thr ftscala.CashtagEnt y
 mport com.tw ter.t etyp e.t ettext.TextEnt y

object CashtagTextEnt y extends TextEnt y[CashtagEnt y] {
  overr de def from ndex(ent y: CashtagEnt y): Short = ent y.from ndex
  overr de def to ndex(ent y: CashtagEnt y): Short = ent y.to ndex
  overr de def move(ent y: CashtagEnt y, from ndex: Short, to ndex: Short): CashtagEnt y =
    ent y.copy(from ndex = from ndex, to ndex = to ndex)
}
