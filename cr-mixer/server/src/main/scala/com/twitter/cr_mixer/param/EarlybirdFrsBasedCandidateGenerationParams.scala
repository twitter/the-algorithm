package com.tw ter.cr_m xer.param

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.model.Earlyb rdS m lar yEng neType
 mport com.tw ter.cr_m xer.model.Earlyb rdS m lar yEng neType_ModelBased
 mport com.tw ter.cr_m xer.model.Earlyb rdS m lar yEng neType_RecencyBased
 mport com.tw ter.cr_m xer.model.Earlyb rdS m lar yEng neType_TensorflowBased
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

object Earlyb rdFrsBasedCand dateGenerat onParams {
  object Cand dateGenerat onEarlyb rdS m lar yEng neType extends Enu rat on {
    protected case class S m lar yEng neType(rank ngMode: Earlyb rdS m lar yEng neType)
        extends super.Val
     mport scala.language. mpl c Convers ons
     mpl c  def valueToEarlyb rdRank ngMode(x: Value): S m lar yEng neType =
      x.as nstanceOf[S m lar yEng neType]

    val Earlyb rdRank ngMode_RecencyBased: S m lar yEng neType = S m lar yEng neType(
      Earlyb rdS m lar yEng neType_RecencyBased)
    val Earlyb rdRank ngMode_ModelBased: S m lar yEng neType = S m lar yEng neType(
      Earlyb rdS m lar yEng neType_ModelBased)
    val Earlyb rdRank ngMode_TensorflowBased: S m lar yEng neType = S m lar yEng neType(
      Earlyb rdS m lar yEng neType_TensorflowBased)
  }

  object FrsBasedCand dateGenerat onEarlyb rdS m lar yEng neTypeParam
      extends FSEnumParam[Cand dateGenerat onEarlyb rdS m lar yEng neType.type](
        na  = "frs_based_cand date_generat on_earlyb rd_rank ng_mode_ d",
        default =
          Cand dateGenerat onEarlyb rdS m lar yEng neType.Earlyb rdRank ngMode_RecencyBased,
        enum = Cand dateGenerat onEarlyb rdS m lar yEng neType
      )

  object FrsBasedCand dateGenerat onRecencyBasedEarlyb rdMaxT etsPerUser
      extends FSBoundedParam[ nt](
        na  = "frs_based_cand date_generat on_earlyb rd_max_t ets_per_user",
        default = 100,
        m n = 0,
        /**
         * Note max should be equal to Earlyb rdRecencyBasedCand dateStoreModule.DefaultMaxNumT etPerUser.
         * Wh ch  s t  s ze of t   mcac d result l st.
         */
        max = 100
      )

  object FrsBasedCand dateGenerat onEarlyb rdMaxT etAge
      extends FSBoundedParam[Durat on](
        na  = "frs_based_cand date_generat on_earlyb rd_max_t et_age_h s",
        default = 24.h s,
        m n = 12.h s,
        /**
         * Note max could be related to Earlyb rdRecencyBasedCand dateStoreModule.DefaultMaxNumT etPerUser.
         * Wh ch  s t  s ze of t   mcac d result l st for recency based earlyb rd cand date s ce.
         * E.g.  f max = 720.h s,   may want to  ncrease t  DefaultMaxNumT etPerUser.
         */
        max = 96.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromH s
  }

  object FrsBasedCand dateGenerat onEarlyb rdF lterOutRet etsAndRepl es
      extends FSParam[Boolean](
        na  = "frs_based_cand date_generat on_earlyb rd_f lter_out_ret ets_and_repl es",
        default = true
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    FrsBasedCand dateGenerat onEarlyb rdS m lar yEng neTypeParam,
    FrsBasedCand dateGenerat onRecencyBasedEarlyb rdMaxT etsPerUser,
    FrsBasedCand dateGenerat onEarlyb rdMaxT etAge,
    FrsBasedCand dateGenerat onEarlyb rdF lterOutRet etsAndRepl es,
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      FrsBasedCand dateGenerat onEarlyb rdF lterOutRet etsAndRepl es,
    )

    val doubleOverr des = FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des()

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      FrsBasedCand dateGenerat onRecencyBasedEarlyb rdMaxT etsPerUser
    )

    val durat onFSOverr des =
      FeatureSw chOverr deUt l.getDurat onFSOverr des(
        FrsBasedCand dateGenerat onEarlyb rdMaxT etAge
      )

    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
      FrsBasedCand dateGenerat onEarlyb rdS m lar yEng neTypeParam,
    )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .set( ntOverr des: _*)
      .set(enumOverr des: _*)
      .set(durat onFSOverr des: _*)
      .bu ld()
  }
}
