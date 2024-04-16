package com.tw ter.t etyp e.thr ftscala.ent  es

 mport com.tw ter.t etyp e.thr ftscala. nt onEnt y
 mport com.tw ter.t etyp e.t ettext.TextEnt y

object  nt onTextEnt y extends TextEnt y[ nt onEnt y] {
  overr de def from ndex(ent y:  nt onEnt y): Short = ent y.from ndex
  overr de def to ndex(ent y:  nt onEnt y): Short = ent y.to ndex
  overr de def move(ent y:  nt onEnt y, from ndex: Short, to ndex: Short):  nt onEnt y =
    ent y.copy(from ndex = from ndex, to ndex = to ndex)
}
