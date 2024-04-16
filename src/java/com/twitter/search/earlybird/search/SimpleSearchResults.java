package com.tw ter.search.earlyb rd.search;

 mport java.ut l.L st;

publ c class S mpleSearchResults extends SearchResults nfo {
  protected H [] h s;
  protected  nt numH s;

  publ c S mpleSearchResults( nt s ze) {
    t .h s = new H [s ze];
    t .numH s = 0;
  }

  publ c S mpleSearchResults(L st<H > h s) {
    t .h s = new H [h s.s ze()];
    t .numH s = h s.s ze();
    h s.toArray(t .h s);
  }

  publ c H [] h s() {
    return h s;
  }

  publ c  nt numH s() {
    return numH s;
  }

  publ c vo d setNumH s( nt numH s) {
    t .numH s = numH s;
  }

  publ c H  getH ( nt h  ndex) {
    return h s[h  ndex];
  }
}
