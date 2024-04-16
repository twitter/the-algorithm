package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;
 mport javax. nject.S ngleton;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.dec der.SearchDec der;

/**
 * Controls fract ons of requests that are sent out to each t er.
 */
@S ngleton
publ c class Earlyb rdT erThrottleDec ders {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Earlyb rdT erThrottleDec ders.class);
  pr vate stat c f nal Str ng T ER_THROTTLE_DEC DER_KEY_FORMAT =
      "percentage_to_h _cluster_%s_t er_%s";
  pr vate f nal SearchDec der dec der;

  /**
   * Construct a dec der us ng t  s ngleton dec der object  njected by Gu ce for t 
   * spec f ed t er.
   * See {@l nk com.tw ter.search.common.root.SearchRootModule#prov deDec der()}
   */
  @ nject
  publ c Earlyb rdT erThrottleDec ders(SearchDec der dec der) {
    t .dec der = dec der;
  }

  /**
   * Return t  throttle dec der key for t  spec f ed t er.
   */
  publ c Str ng getT erThrottleDec derKey(Str ng clusterNa , Str ng t erNa ) {
    Str ng dec derKey = Str ng.format(T ER_THROTTLE_DEC DER_KEY_FORMAT, clusterNa , t erNa );
     f (!dec der.getDec der().feature(dec derKey).ex sts()) {
      LOG.warn("Dec der key {} not found. W ll always return unava lable.", dec derKey);
    }
    return dec derKey;
  }

  /**
   * C ck w t r a request should be sent to t  spec f ed t er.
   */
  publ c Boolean shouldSendRequestToT er(f nal Str ng t erDarkReadDec derKey) {
    return dec der. sAva lable(t erDarkReadDec derKey);
  }
}
