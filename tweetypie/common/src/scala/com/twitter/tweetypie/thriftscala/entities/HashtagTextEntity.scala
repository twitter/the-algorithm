package com.tw ter.t etyp e.thr ftscala.ent  es

 mport com.tw ter.t etyp e.thr ftscala.HashtagEnt y
 mport com.tw ter.t etyp e.t ettext.TextEnt y

object HashtagTextEnt y extends TextEnt y[HashtagEnt y] {
  overr de def from ndex(ent y: HashtagEnt y): Short = ent y.from ndex
  overr de def to ndex(ent y: HashtagEnt y): Short = ent y.to ndex
  overr de def move(ent y: HashtagEnt y, from ndex: Short, to ndex: Short): HashtagEnt y =
    ent y.copy(from ndex = from ndex, to ndex = to ndex)
}
