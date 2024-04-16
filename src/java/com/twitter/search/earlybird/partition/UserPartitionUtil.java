package com.tw ter.search.earlyb rd.part  on;

 mport com.google.common.base.Pred cate;

 mport com.tw ter.search.common.ut l.hash.Earlyb rdPart  on ngFunct on;
 mport com.tw ter.search.common.ut l.hash.GeneralEarlyb rdPart  on ngFunct on;

publ c f nal class UserPart  onUt l {
  pr vate UserPart  onUt l() {
  }

  /**
   * F lter out t  users that are not present  n t  part  on.
   */
  publ c stat c Pred cate<Long> f lterUsersByPart  onPred cate(f nal Part  onConf g conf g) {
    return new Pred cate<Long>() {

      pr vate f nal  nt part  on D = conf g.get ndex ngHashPart  on D();
      pr vate f nal  nt numPart  ons = conf g.getNumPart  ons();
      pr vate f nal Earlyb rdPart  on ngFunct on part  oner =
          new GeneralEarlyb rdPart  on ngFunct on();

      @Overr de
      publ c boolean apply(Long user d) {
        // See SEARCH-6675
        // R ght now  f t  part  on ng log c changes  n Arch vePart  on ng t  log c
        // needs to be updated too.
        return part  oner.getPart  on(user d, numPart  ons) == part  on D;
      }
    };
  }
}
