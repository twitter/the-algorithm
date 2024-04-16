package com.tw ter.search.earlyb rd. ndex;

 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsData;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsFactory;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rdRealt   ndexExtens onsData;

publ c class T etSearch ndexExtens onsFactory extends Earlyb rd ndexExtens onsFactory {
  @Overr de
  publ c Earlyb rdRealt   ndexExtens onsData newRealt   ndexExtens onsData() {
    return new T etSearchRealt   ndexExtens onsData();
  }

  @Overr de
  publ c Earlyb rd ndexExtens onsData newLucene ndexExtens onsData() {
    return new T etSearchLucene ndexExtens onsData();
  }
}
