package com.tw ter.product_m xer.component_l brary.exper  nts. tr cs

 mport scala.collect on. mmutable.L stSet

/**
 *
 * @param  d opt onal  tr c group  d.  f  d  s None, t   ans t  group
 *            s be ng newly created and t   d  s not prov s oned by go/ddg. Ot rw se, t   tr c
 *           group  s present  n DDG and has a correspond ng  d.
 * @param na   tr c group na 
 * @param descr pt on  tr c group descr pt on
 * @param  tr cs set of  tr cs that belong to t   tr c group
 */
case class  tr cGroup(
   d: Opt on[Long],
  na : Str ng,
  descr pt on: Str ng,
   tr cs: L stSet[ tr c]) {

  /*
   * Returns a CSV representat on of t   tr c group that can be  mported v a DDG's bulk  mport tool
   * T  bulk  mport tool consu s CSV data w h t  follow ng columns:
   * 1. group na 
   * 2. group descr pt on
   * 3.  tr c na 
   * 4.  tr c descr pt on
   * 5.  tr c pattern
   * 6. group  d -- nu r c  d
   * 7. (opt onal)  tr c type -- `NAMED_PATTERN`, `STRA NER`, or `LAMBDA`.
   */
  def toCsv: Str ng = {
    val  tr cCsvL nes: L stSet[Str ng] = for {
       tr c <-  tr cs
      def n  on <-  tr c.def n  on.toCsvF eld
    } y eld {
      Seq(
        na ,
        descr pt on,
         tr c.na ,
         tr c.na ,
        // wrap  n s ngle quotes so that DDG bulk  mport tool correctly parses
        s""""$def n  on"""",
         d.map(_.toStr ng).getOrElse(""),
         tr c.def n  on. tr cDef n  onType
      ).mkStr ng(",")
    }
    pr ntln(s"Generated  tr cs  n CSV count: ${ tr cCsvL nes.s ze}")
     tr cCsvL nes.mkStr ng("\n")
  }

  // Un que  tr c na s based on globally un que  tr c na 
  def un que tr cNa s: Set[Str ng] =
     tr cs.groupBy(_.na ).keys.toSet
}
