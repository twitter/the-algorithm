package com.tw ter.search.earlyb rd_root;

 mport javax. nject. nject;
 mport javax. nject.Na d;

 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.root.SearchRootModule;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;

/**
 * Determ nes  f a root should send requests to certa n part  ons based on  f t y have been turned
 * off by dec der.
 */
publ c class Part  onAccessController {
  pr vate f nal Str ng clusterNa ;
  pr vate f nal SearchDec der dec der;

  @ nject
  publ c Part  onAccessController(
      @Na d(SearchRootModule.NAMED_SEARCH_ROOT_NAME) Str ng clusterNa ,
      @Na d(SearchRootModule.NAMED_PART T ON_DEC DER) SearchDec der part  onDec der) {
    t .clusterNa  = clusterNa ;
    t .dec der = part  onDec der;
  }

  /**
   * Should root send requests to a g ven part  on
   * Des gned to be used to qu ckly stop h t ng a part  on of t re are problems w h  .
   */
  publ c boolean canAccessPart  on(
      Str ng t erNa ,  nt part  onNum, Str ng cl ent d, Earlyb rdRequestType requestType) {

    Str ng part  onDec derNa  =
        Str ng.format("cluster_%s_sk p_t er_%s_part  on_%s", clusterNa , t erNa , part  onNum);
     f (dec der. sAva lable(part  onDec derNa )) {
      SearchCounter.export(part  onDec derNa ). ncre nt();
      return false;
    }

    Str ng cl entDec derNa  = Str ng.format("cluster_%s_sk p_t er_%s_part  on_%s_cl ent_ d_%s",
        clusterNa , t erNa , part  onNum, cl ent d);
     f (dec der. sAva lable(cl entDec derNa )) {
      SearchCounter.export(cl entDec derNa ). ncre nt();
      return false;
    }

    Str ng requestTypeDec derNa  = Str ng.format(
        "cluster_%s_sk p_t er_%s_part  on_%s_request_type_%s",
        clusterNa , t erNa , part  onNum, requestType.getNormal zedNa ());
     f (dec der. sAva lable(requestTypeDec derNa )) {
      SearchCounter.export(requestTypeDec derNa ). ncre nt();
      return false;
    }

    Str ng cl entRequestTypeDec derNa  = Str ng.format(
        "cluster_%s_sk p_t er_%s_part  on_%s_cl ent_ d_%s_request_type_%s",
        clusterNa , t erNa , part  onNum, cl ent d, requestType.getNormal zedNa ());
     f (dec der. sAva lable(cl entRequestTypeDec derNa )) {
      SearchCounter.export(cl entRequestTypeDec derNa ). ncre nt();
      return false;
    }

    return true;
  }

  publ c Str ng getClusterNa () {
    return clusterNa ;
  }
}
