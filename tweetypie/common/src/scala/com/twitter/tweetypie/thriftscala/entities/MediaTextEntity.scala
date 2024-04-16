package com.tw ter.t etyp e.thr ftscala.ent  es

 mport com.tw ter.t etyp e.thr ftscala. d aEnt y
 mport com.tw ter.t etyp e.t ettext.TextEnt y

object  d aTextEnt y extends TextEnt y[ d aEnt y] {
  overr de def from ndex(ent y:  d aEnt y): Short = ent y.from ndex
  overr de def to ndex(ent y:  d aEnt y): Short = ent y.to ndex
  overr de def move(ent y:  d aEnt y, from ndex: Short, to ndex: Short):  d aEnt y =
    ent y.copy(from ndex = from ndex, to ndex = to ndex)
}
