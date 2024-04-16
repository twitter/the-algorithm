package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedExclude dsCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * Bu lds [[UrtUnorderedExclude dsCursor]]  n t  Bottom pos  on w n   want to also exclude  ds
 * of  ems  ns de a module. T  reason   cannot use [[UnorderedExclude dsBottomCursorBu lder]]  n
 * such case  s that t  exclude dsSelector of [[UnorderedExclude dsBottomCursorBu lder]]  s do ng a
 * one to one mapp ng bet en entr es and excluded  ds, but  n case of hav ng a module, a module
 * entry can result  n exclud ng a sequence of entr es.
 *
 * @param excluded dsMaxLengthParam T  max mum length of t  cursor
 * @param exclude dsSelector Spec f es t  entry  ds to populate on t  `excluded ds` f eld
 * @param ser al zer Converts t  cursor to an encoded str ng
 */
case class UnorderedExclude dsSeqBottomCursorBu lder(
  overr de val excluded dsMaxLengthParam: Param[ nt],
  exclude dsSelector: Part alFunct on[Un versalNoun[_], Seq[Long]],
  overr de val ser al zer: P pel neCursorSer al zer[UrtUnorderedExclude dsCursor] =
    UrtCursorSer al zer)
    extends BaseUnorderedExclude dsBottomCursorBu lder {

  overr de def excludeEntr esCollector(entr es: Seq[T  l neEntry]): Seq[Long] =
    entr es.collect(exclude dsSelector).flatten
}
