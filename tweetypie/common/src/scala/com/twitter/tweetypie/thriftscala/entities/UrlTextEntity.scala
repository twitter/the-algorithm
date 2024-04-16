package com.tw ter.t etyp e.thr ftscala.ent  es

 mport com.tw ter.t etyp e.thr ftscala.UrlEnt y
 mport com.tw ter.t etyp e.t ettext.TextEnt y

object UrlTextEnt y extends TextEnt y[UrlEnt y] {
  overr de def from ndex(ent y: UrlEnt y): Short = ent y.from ndex
  overr de def to ndex(ent y: UrlEnt y): Short = ent y.to ndex
  overr de def move(ent y: UrlEnt y, from ndex: Short, to ndex: Short): UrlEnt y =
    ent y.copy(from ndex = from ndex, to ndex = to ndex)
}
