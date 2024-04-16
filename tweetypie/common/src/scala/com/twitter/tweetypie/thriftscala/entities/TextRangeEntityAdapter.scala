package com.tw ter.t etyp e.thr ftscala.ent  es

 mport com.tw ter.t etyp e.thr ftscala.TextRange
 mport com.tw ter.t etyp e.t ettext.TextEnt y

object TextRangeEnt yAdapter extends TextEnt y[TextRange] {
  overr de def from ndex(ent y: TextRange): Short = ent y.from ndex.toShort
  overr de def to ndex(ent y: TextRange): Short = ent y.to ndex.toShort
  overr de def move(ent y: TextRange, from ndex: Short, to ndex: Short): TextRange =
    ent y.copy(from ndex = from ndex, to ndex = to ndex)
}
