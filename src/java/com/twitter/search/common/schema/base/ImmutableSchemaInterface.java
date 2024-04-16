package com.tw ter.search.common.sc ma.base;

 mport javax.annotat on.concurrent. mmutable;
 mport javax.annotat on.concurrent.ThreadSafe;

/**
 * T   nterface carr es t  sa  s gnature as Sc ma w h t  only d fference that t  sc ma
 *  s  mmutable.  T  should be used by short sess ons and t  class would guarantee t  sc ma
 * would not change for t  sess on.  A typ cal usage  s l ke a search query sess on.
 */
@ mmutable
@ThreadSafe
publ c  nterface  mmutableSc ma nterface extends Sc ma {
}
