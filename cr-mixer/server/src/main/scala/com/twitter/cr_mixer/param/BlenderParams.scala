package com.tw ter.cr_m xer.param

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object BlenderParams {
  object Blend ngAlgor hmEnum extends Enu rat on {
    val RoundRob n: Value = Value
    val S ceTypeBackF ll: Value = Value
    val S ceS gnalSort ng: Value = Value
  }
  object ContentBasedSort ngAlgor hmEnum extends Enu rat on {
    val Favor eCount: Value = Value
    val S ceS gnalRecency: Value = Value
    val RandomSort ng: Value = Value
    val S m lar yToS gnalSort ng: Value = Value
    val Cand dateRecency: Value = Value
  }

  object Blend ngAlgor hmParam
      extends FSEnumParam[Blend ngAlgor hmEnum.type](
        na  = "blend ng_algor hm_ d",
        default = Blend ngAlgor hmEnum.RoundRob n,
        enum = Blend ngAlgor hmEnum
      )

  object Rank ng nterleave  ghtShr nkageParam
      extends FSBoundedParam[Double](
        na  = "blend ng_enable_ml_rank ng_ nterleave_  ghts_shr nkage",
        default = 1.0,
        m n = 0.0,
        max = 1.0
      )

  object Rank ng nterleaveMax  ghtAdjust nts
      extends FSBoundedParam[ nt](
        na  = "blend ng_ nterleave_max_  ghted_adjust nts",
        default = 3000,
        m n = 0,
        max = 9999
      )

  object S gnalTypeSort ngAlgor hmParam
      extends FSEnumParam[ContentBasedSort ngAlgor hmEnum.type](
        na  = "blend ng_algor hm_ nner_s gnal_sort ng_ d",
        default = ContentBasedSort ngAlgor hmEnum.S ceS gnalRecency,
        enum = ContentBasedSort ngAlgor hmEnum
      )

  object ContentBlenderTypeSort ngAlgor hmParam
      extends FSEnumParam[ContentBasedSort ngAlgor hmEnum.type](
        na  = "blend ng_algor hm_content_blender_sort ng_ d",
        default = ContentBasedSort ngAlgor hmEnum.Favor eCount,
        enum = ContentBasedSort ngAlgor hmEnum
      )

  //UserAff n  es Algo Param: w t r to d str buted t  s ce type   ghts
  object EnableD str butedS ceType  ghtsParam
      extends FSParam[Boolean](
        na  = "blend ng_algor hm_enable_d str buted_s ce_type_  ghts",
        default = false
      )

  object BlendGroup ng thodEnum extends Enu rat on {
    val S ceKeyDefault: Value = Value("S ceKey")
    val S ceTypeS m lar yEng ne: Value = Value("S ceTypeS m lar yEng ne")
    val Author d: Value = Value("Author d")
  }

  object BlendGroup ng thodParam
      extends FSEnumParam[BlendGroup ng thodEnum.type](
        na  = "blend ng_group ng_ thod_ d",
        default = BlendGroup ng thodEnum.S ceKeyDefault,
        enum = BlendGroup ng thodEnum
      )

  object RecencyBasedRandomSampl ngHalfL fe nDays
      extends FSBoundedParam[ nt](
        na  = "blend ng_ nterleave_random_sampl ng_recency_based_half_l fe_ n_days",
        default = 7,
        m n = 1,
        max = 28
      )

  object RecencyBasedRandomSampl ngDefault  ght
      extends FSBoundedParam[Double](
        na  = "blend ng_ nterleave_random_sampl ng_recency_based_default_  ght",
        default = 1.0,
        m n = 0.1,
        max = 2.0
      )

  object S ceTypeBackF llEnableV deoBackF ll
      extends FSParam[Boolean](
        na  = "blend ng_enable_v deo_backf ll",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    Blend ngAlgor hmParam,
    Rank ng nterleave  ghtShr nkageParam,
    Rank ng nterleaveMax  ghtAdjust nts,
    EnableD str butedS ceType  ghtsParam,
    BlendGroup ng thodParam,
    RecencyBasedRandomSampl ngHalfL fe nDays,
    RecencyBasedRandomSampl ngDefault  ght,
    S ceTypeBackF llEnableV deoBackF ll,
    S gnalTypeSort ngAlgor hmParam,
    ContentBlenderTypeSort ngAlgor hmParam,
  )

  lazy val conf g: BaseConf g = {
    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
      Blend ngAlgor hmParam,
      BlendGroup ng thodParam,
      S gnalTypeSort ngAlgor hmParam,
      ContentBlenderTypeSort ngAlgor hmParam
    )

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableD str butedS ceType  ghtsParam,
      S ceTypeBackF llEnableV deoBackF ll
    )

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      Rank ng nterleaveMax  ghtAdjust nts,
      RecencyBasedRandomSampl ngHalfL fe nDays
    )

    val doubleOverr des = FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(
      Rank ng nterleave  ghtShr nkageParam,
      RecencyBasedRandomSampl ngDefault  ght
    )

    BaseConf gBu lder()
      .set(enumOverr des: _*)
      .set(booleanOverr des: _*)
      .set( ntOverr des: _*)
      .set(doubleOverr des: _*)
      .bu ld()
  }
}
