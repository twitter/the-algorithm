package com.tw ter.search. ngester.model;

 mport com.tw ter.ut l.Prom se;

publ c class Prom seConta ner<T, U> {
  pr vate f nal Prom se<T> prom se;
  pr vate f nal U obj;

  publ c Prom seConta ner(Prom se<T> prom se, U obj) {
    t .prom se = prom se;
    t .obj = obj;
  }

  publ c Prom se<T> getProm se() {
    return prom se;
  }

  publ c U getObj() {
    return obj;
  }
}
