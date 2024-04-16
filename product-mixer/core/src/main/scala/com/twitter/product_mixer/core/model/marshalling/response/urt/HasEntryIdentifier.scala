package com.tw ter.product_m xer.core.model.marshall ng.response.urt

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun

tra  HasEntry dent f er extends Un versalNoun[Any] w h HasEntryNa space {
  // D st nctly  dent f es t  entry and must be un que relat ve to ot r entr es w h n a response
  lazy val entry dent f er: Str ng = s"$entryNa space-$ d"
}
