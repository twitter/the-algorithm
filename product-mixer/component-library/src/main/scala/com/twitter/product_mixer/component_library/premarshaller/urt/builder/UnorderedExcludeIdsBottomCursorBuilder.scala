package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedExclude dsCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * Bu lds [[UrtUnorderedExclude dsCursor]]  n t  Bottom pos  on
 *
 * @param excluded dsMaxLengthParam T  max mum length of t  cursor
 * @param exclude dsSelector Spec f es t  entry  ds to populate on t  `excluded ds` f eld
 * @param ser al zer Converts t  cursor to an encoded str ng
 */
case class UnorderedExclude dsBottomCursorBu lder(
  overr de val excluded dsMaxLengthParam: Param[ nt],
  exclude dsSelector: Part alFunct on[Un versalNoun[_], Long],
  overr de val ser al zer: P pel neCursorSer al zer[UrtUnorderedExclude dsCursor] =
    UrtCursorSer al zer)
    extends BaseUnorderedExclude dsBottomCursorBu lder {

  overr de def excludeEntr esCollector(entr es: Seq[T  l neEntry]): Seq[Long] =
    entr es.collect(exclude dsSelector)
}
