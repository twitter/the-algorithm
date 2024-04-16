package com.tw ter.cr_m xer.param

 mport com.tw ter.convers ons.Durat onOps.r chDurat onFrom nt
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

object Consu rBasedWalsParams {

  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "consu r_based_wals_enable_s ce",
        default = false
      )

  object ModelNa Param
      extends FSParam[Str ng](
        na  = "consu r_based_wals_model_na ",
        default = "model_0"
      )

  object W lyNsNa Param
      extends FSParam[Str ng](
        na  = "consu r_based_wals_w ly_ns_na ",
        default = ""
      )

  object Model nputNa Param
      extends FSParam[Str ng](
        na  = "consu r_based_wals_model_ nput_na ",
        default = "examples"
      )

  object ModelOutputNa Param
      extends FSParam[Str ng](
        na  = "consu r_based_wals_model_output_na ",
        default = "all_t et_ ds"
      )

  object ModelS gnatureNa Param
      extends FSParam[Str ng](
        na  = "consu r_based_wals_model_s gnature_na ",
        default = "serv ng_default"
      )

  object MaxT etS gnalAgeH sParam
      extends FSBoundedParam[Durat on](
        na  = "consu r_based_wals_max_t et_s gnal_age_h s",
        default = 72.h s,
        m n = 1.h s,
        max = 720.h s
      )
      w h HasDurat onConvers on {

    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromH s
  }

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceParam,
    ModelNa Param,
    Model nputNa Param,
    ModelOutputNa Param,
    ModelS gnatureNa Param,
    MaxT etS gnalAgeH sParam,
    W lyNsNa Param,
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam,
    )
    val str ngOverr des = FeatureSw chOverr deUt l.getStr ngFSOverr des(
      ModelNa Param,
      Model nputNa Param,
      ModelOutputNa Param,
      ModelS gnatureNa Param,
      W lyNsNa Param
    )

    val boundedDurat onFSOverr des =
      FeatureSw chOverr deUt l.getBoundedDurat onFSOverr des(MaxT etS gnalAgeH sParam)

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(str ngOverr des: _*)
      .set(boundedDurat onFSOverr des: _*)
      .bu ld()
  }
}
