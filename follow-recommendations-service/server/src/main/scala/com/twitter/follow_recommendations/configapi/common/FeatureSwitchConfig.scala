package com.tw ter.follow_recom ndat ons.conf gap .common

 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l.Def nedFeatureNa 
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l.ValueFeatureNa 
 mport com.tw ter.t  l nes.conf gap .BoundedParam
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Opt onalOverr de
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

tra  FeatureSw chConf g {
  def booleanFSParams: Seq[Param[Boolean] w h FSNa ] = N l

  def  ntFSParams: Seq[FSBoundedParam[ nt]] = N l

  def longFSParams: Seq[FSBoundedParam[Long]] = N l

  def doubleFSParams: Seq[FSBoundedParam[Double]] = N l

  def durat onFSParams: Seq[FSBoundedParam[Durat on] w h HasDurat onConvers on] = N l

  def opt onalDoubleFSParams: Seq[
    (BoundedParam[Opt on[Double]], Def nedFeatureNa , ValueFeatureNa )
  ] = N l

  def str ngSeqFSParams: Seq[Param[Seq[Str ng]] w h FSNa ] = N l

  /**
   * Apply overr des  n l st w n t  g ven FS Key  s enabled.
   * T  overr de type does NOT work w h exper  nts. Params  re w ll be evaluated for every
   * request  MMED ATELY, not upon param.apply.  f   would l ke to use an exper  nt pls use
   * t  pr m  ve type or ENUM overr des.
   */
  def gatedOverr desMap: Map[Str ng, Seq[Opt onalOverr de[_]]] = Map.empty
}

object FeatureSw chConf g {
  def  rge(conf gs: Seq[FeatureSw chConf g]): FeatureSw chConf g = new FeatureSw chConf g {
    overr de def booleanFSParams: Seq[Param[Boolean] w h FSNa ] =
      conf gs.flatMap(_.booleanFSParams)
    overr de def  ntFSParams: Seq[FSBoundedParam[ nt]] =
      conf gs.flatMap(_. ntFSParams)
    overr de def longFSParams: Seq[FSBoundedParam[Long]] =
      conf gs.flatMap(_.longFSParams)
    overr de def durat onFSParams: Seq[FSBoundedParam[Durat on] w h HasDurat onConvers on] =
      conf gs.flatMap(_.durat onFSParams)
    overr de def gatedOverr desMap: Map[Str ng, Seq[Opt onalOverr de[_]]] =
      conf gs.flatMap(_.gatedOverr desMap).toMap
    overr de def doubleFSParams: Seq[FSBoundedParam[Double]] =
      conf gs.flatMap(_.doubleFSParams)
    overr de def opt onalDoubleFSParams: Seq[
      (BoundedParam[Opt on[Double]], Def nedFeatureNa , ValueFeatureNa )
    ] =
      conf gs.flatMap(_.opt onalDoubleFSParams)
    overr de def str ngSeqFSParams: Seq[Param[Seq[Str ng]] w h FSNa ] =
      conf gs.flatMap(_.str ngSeqFSParams)
  }
}
