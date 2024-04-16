package com.tw ter.search.earlyb rd.ut l;

 mport scala.So ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.dec der.Dec der$;
 mport com.tw ter.dec der.RandomRec p ent$;
 mport com.tw ter.dec der.Rec p ent;
 mport com.tw ter.dec der.dec s onmaker.MutableDec s onMaker;
 mport com.tw ter.search.common.dec der.Dec derUt l;
 mport com.tw ter.search.common.dec der.SearchDec derFactory;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;

/**
 * A S ngleton to let any code  n Earlyb rd have t  ab l y to be guarded by a dec der key.
 *
 * Earlyb rdDec der  s a th n wrapper around t  Tw ter Dec der l brary to prov de global access to a s ngle
 * dec der conf gurat on. T  way any code anyw re can eas ly be guarded by a Dec der key. T   n  al zer requ res
 * Earlyb rdConf g to be  n  al zed already. Defaults to a NullDec der, wh ch causes all requests for keys to return
 * false.
 */
publ c f nal class Earlyb rdDec der {
  publ c stat c f nal org.slf4j.Logger LOG =
      org.slf4j.LoggerFactory.getLogger(Earlyb rdDec der.class);
  publ c stat c f nal Str ng DEC DER_CONF G = "./conf g/earlyb rd-dec der.yml";

  pr vate stat c volat le Dec der earlyb rdDec der = Dec der$.MODULE$.NullDec der();
  pr vate stat c volat le MutableDec s onMaker mutableDec s onMaker;

  pr vate Earlyb rdDec der() { }

  /**
   *  n  al zes t  global dec der accessor. Requ res Earlyb rdConf g to be  n  al zed.
   *
   * @return t  new dec der  nterface.
   */
  publ c stat c Dec der  n  al ze() {
    return  n  al ze(DEC DER_CONF G);
  }

  /**
   *  n  al zes t  global dec der accessor. Requ res Earlyb rdConf g to be  n  al zed.
   *
   * @param conf gPath path to t  base dec der conf g f le.
   * @return t  new dec der  nterface.
   */
  @V s bleForTest ng publ c stat c Dec der  n  al ze(Str ng conf gPath) {
    synchron zed (Earlyb rdDec der.class) {
      Precond  ons.c ckState(earlyb rdDec der == Dec der$.MODULE$.NullDec der(),
                               "Earlyb rdDec der can be  n  al zed only once.");

      mutableDec s onMaker = new MutableDec s onMaker();

       f (Earlyb rdProperty.USE_DEC DER_OVERLAY.get(false)) {
        Str ng category = Earlyb rdProperty.DEC DER_OVERLAY_CONF G.get();
        earlyb rdDec der =
            SearchDec derFactory.createDec derW houtRefreshBaseW hOverlay(
                conf gPath, category, mutableDec s onMaker);
        LOG. nfo("Earlyb rdDec der set to use t  dec der overlay " + category);
      } else {
        earlyb rdDec der =
            SearchDec derFactory.createDec derW hRefreshBaseW houtOverlay(
                conf gPath, mutableDec s onMaker);
        LOG. nfo("Earlyb rdDec der set to only use t  base conf g");
      }
      return earlyb rdDec der;
    }
  }

  /**
   * C ck  f feature  s ava lable based on randomness
   *
   * @param feature t  feature na  to test
   * @return true  f t  feature  s ava lable, false ot rw se
   */
  publ c stat c boolean  sFeatureAva lable(Str ng feature) {
    return  sFeatureAva lable(feature, RandomRec p ent$.MODULE$);
  }

  /**
   * C ck  f t  feature  s ava lable based on t  user
   *
   * T  rec p ent'd  d  s has d and used as t  value to compare w h t  dec der percentage. T refore, t  sa  user
   * w ll always get t  sa  result for a g ven percentage, and h g r percentages should always be a superset of t 
   * lo r percentage users.
   *
   * RandomRec p ent can be used to get a random value for every call.
   *
   * @param feature t  feature na  to test
   * @param rec p ent t  rec p ent to base a dec s on on
   * @return true  f t  feature  s ava lable, false ot rw se
   */
  publ c stat c boolean  sFeatureAva lable(Str ng feature, Rec p ent rec p ent) {
     f (earlyb rdDec der == Dec der$.MODULE$.NullDec der()) {
      LOG.warn("Earlyb rdDec der  s un n  al zed but requested feature " + feature);
    }

    return earlyb rdDec der. sAva lable(feature, So .apply(rec p ent));
  }

  /**
   * Get t  raw dec der value for a g ven feature.
   *
   * @param feature t  feature na 
   * @return t   nteger value of t  dec der
   */
  publ c stat c  nt getAva lab l y(Str ng feature) {
    return Dec derUt l.getAva lab l y(earlyb rdDec der, feature);
  }

  publ c stat c Dec der getDec der() {
    c ck n  al zed();
    return earlyb rdDec der;
  }

  publ c stat c MutableDec s onMaker getMutableDec s onMaker() {
    c ck n  al zed();
    return mutableDec s onMaker;
  }

  pr vate stat c vo d c ck n  al zed() {
    Precond  ons.c ckState(earlyb rdDec der != Dec der$.MODULE$.NullDec der(),
        "Earlyb rdDec der  s not  n  al zed.");
  }
}
