package com.tw ter.product_m xer.core.funct onal_component.conf gap .reg stry

 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l.Def nedFeatureNa 
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l.EnumParamW hFeatureNa 
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l.EnumSeqParamW hFeatureNa 
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l.ValueFeatureNa 
 mport com.tw ter.t  l nes.conf gap .dec der.HasDec der
 mport com.tw ter.t  l nes.conf gap .Bounded
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Opt onalOverr de
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

/** ParamConf g  s used to conf gure overr des for [[Param]]s of var ous types */
tra  ParamConf g {

  def booleanDec derOverr des: Seq[Param[Boolean] w h HasDec der] = Seq.empty

  def booleanFSOverr des: Seq[Param[Boolean] w h FSNa ] = Seq.empty

  def opt onalBooleanOverr des: Seq[
    (Param[Opt on[Boolean]], Def nedFeatureNa , ValueFeatureNa )
  ] = Seq.empty

  def enumFSOverr des: Seq[EnumParamW hFeatureNa [_ <: Enu rat on]] = Seq.empty

  def enumSeqFSOverr des: Seq[EnumSeqParamW hFeatureNa [_ <: Enu rat on]] = Seq.empty

  /**
   * Support for non-Durat on suppl ed FS overr des (e.g. `t  FromStr ngFSOverr des`,
   * `t  FromNumberFSOverr des`, `getBoundedOpt onalDurat onFromM ll sOverr des`)  s not prov ded
   * as Durat on  s preferred
   */
  def boundedDurat onFSOverr des: Seq[
    Param[Durat on] w h Bounded[Durat on] w h FSNa  w h HasDurat onConvers on
  ] = Seq.empty

  /** Support for unbounded nu r c FS overr des  s not prov ded as bounded  s preferred */
  def bounded ntFSOverr des: Seq[Param[ nt] w h Bounded[ nt] w h FSNa ] = Seq.empty

  def boundedOpt onal ntOverr des: Seq[
    (Param[Opt on[ nt]] w h Bounded[Opt on[ nt]], Def nedFeatureNa , ValueFeatureNa )
  ] = Seq.empty

  def  ntSeqFSOverr des: Seq[Param[Seq[ nt]] w h FSNa ] = Seq.empty

  def boundedLongFSOverr des: Seq[Param[Long] w h Bounded[Long] w h FSNa ] = Seq.empty

  def boundedOpt onalLongOverr des: Seq[
    (Param[Opt on[Long]] w h Bounded[Opt on[Long]], Def nedFeatureNa , ValueFeatureNa )
  ] = Seq.empty

  def longSeqFSOverr des: Seq[Param[Seq[Long]] w h FSNa ] = Seq.empty

  def longSetFSOverr des: Seq[Param[Set[Long]] w h FSNa ] = Seq.empty

  def boundedDoubleFSOverr des: Seq[Param[Double] w h Bounded[Double] w h FSNa ] = Seq.empty

  def boundedOpt onalDoubleOverr des: Seq[
    (Param[Opt on[Double]] w h Bounded[Opt on[Double]], Def nedFeatureNa , ValueFeatureNa )
  ] = Seq.empty

  def doubleSeqFSOverr des: Seq[Param[Seq[Double]] w h FSNa ] = Seq.empty

  def str ngFSOverr des: Seq[Param[Str ng] w h FSNa ] = Seq.empty

  def str ngSeqFSOverr des: Seq[Param[Seq[Str ng]] w h FSNa ] = Seq.empty

  def opt onalStr ngOverr des: Seq[(Param[Opt on[Str ng]], Def nedFeatureNa , ValueFeatureNa )] =
    Seq.empty

  def gatedOverr des: Map[Str ng, Seq[Opt onalOverr de[_]]] = Map.empty
}
