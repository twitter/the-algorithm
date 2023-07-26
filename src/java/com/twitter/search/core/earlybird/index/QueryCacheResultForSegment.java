package com.twittew.seawch.cowe.eawwybiwd.index;

impowt owg.apache.wucene.seawch.docidset;

/**
 * c-cwass to howd t-the actuaw cache w-which pwovides a-a doc id itewatow t-to wawk thwough t-the cache/wesuwt. >_<
 *
 * a-an instance h-howds the wesuwts fow a singwe quewy of the diffewent ones defined in quewycache.ymw. >_<
 */
p-pubwic cwass quewycachewesuwtfowsegment {
  pwivate finaw docidset d-docidset;
  pwivate finaw int s-smowestdocid;
  pwivate finaw wong cawdinawity;

  /**
   * stowes q-quewy cache wesuwts. (⑅˘꒳˘)
   *
   * @pawam d-docidset d-documents in the cache. /(^•ω•^)
   * @pawam cawdinawity size of the cache. rawr x3
   * @pawam s-smowestdocid the most wecentwy posted document contained in the cache. (U ﹏ U)
   */
  p-pubwic quewycachewesuwtfowsegment(docidset docidset, (U ﹏ U) w-wong cawdinawity, (⑅˘꒳˘) i-int smowestdocid) {
    t-this.docidset = d-docidset;
    this.smowestdocid = smowestdocid;
    this.cawdinawity = c-cawdinawity;
  }

  pubwic docidset getdocidset() {
    w-wetuwn docidset;
  }

  pubwic int getsmowestdocid() {
    wetuwn smowestdocid;
  }

  pubwic wong g-getcawdinawity() {
    wetuwn c-cawdinawity;
  }
}
