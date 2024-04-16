package com.tw ter.search. ngester.p pel ne.ut l;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.dec der.Dec der;

publ c f nal class Pengu nVers onsUt l {

  pr vate Pengu nVers onsUt l() { /* prevent  nstant at on */ }

  /**
   * Ut l y  thod for updat ng pengu nVers ons l sts v a dec der ava lab l y.   must have
   * at least one vers on ava lable.
   * @param pengu nVers ons
   * @param dec der
   * @return
   */
  publ c stat c L st<Pengu nVers on> f lterPengu nVers onsW hDec ders(
      L st<Pengu nVers on> pengu nVers ons,
      Dec der dec der) {
    L st<Pengu nVers on> updatedPengu nVers ons = new ArrayL st<>();
    for (Pengu nVers on pengu nVers on : pengu nVers ons) {
       f ( sPengu nVers onAva lable(pengu nVers on, dec der)) {
        updatedPengu nVers ons.add(pengu nVers on);
      }
    }
    Precond  ons.c ckArgu nt(pengu nVers ons.s ze() > 0,
        "At least one pengu n vers on must be spec f ed.");

    return updatedPengu nVers ons;
  }

  /**
   * C cks pengu nVers on dec der for ava lab l y.
   * @param pengu nVers on
   * @param dec der
   * @return
   */
  publ c stat c boolean  sPengu nVers onAva lable(Pengu nVers on pengu nVers on, Dec der dec der) {
    return dec der. sAva lable(
        Str ng.format("enable_pengu n_vers on_%d", pengu nVers on.getByteValue()));
  }
}
