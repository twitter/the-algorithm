package com.tw ter.cr_m xer.param

 mport com.tw ter.cr_m xer.model.ModelConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Custom zedRetr evalBasedCand dateGenerat onParams {

  // Offl ne S mClusters  nterested n params
  object EnableOffl ne nterested nParam
      extends FSParam[Boolean](
        na  = "custom zed_retr eval_based_cand date_generat on_enable_offl ne_ nterested n",
        default = false
      )

  // Offl ne S mClusters FTR-based  nterested n
  object EnableOffl neFTR nterested nParam
      extends FSParam[Boolean](
        na  = "custom zed_retr eval_based_cand date_generat on_enable_ftr_offl ne_ nterested n",
        default = false
      )

  // TwH n Collab F lter Cluster params
  object EnableTwh nCollabF lterClusterParam
      extends FSParam[Boolean](
        na  = "custom zed_retr eval_based_cand date_generat on_enable_twh n_collab_f lter_cluster",
        default = false
      )

  // TwH n Mult  Cluster params
  object EnableTwh nMult ClusterParam
      extends FSParam[Boolean](
        na  = "custom zed_retr eval_based_cand date_generat on_enable_twh n_mult _cluster",
        default = false
      )

  object EnableRet etBasedD ffus onParam
      extends FSParam[Boolean](
        na  = "custom zed_retr eval_based_cand date_generat on_enable_ret et_based_d ffus on",
        default = false
      )
  object Custom zedRetr evalBasedRet etD ffus onS ce
      extends FSParam[Str ng](
        na  =
          "custom zed_retr eval_based_cand date_generat on_offl ne_ret et_based_d ffus on_model_ d",
        default = ModelConf g.Ret etBasedD ffus on
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableOffl ne nterested nParam,
    EnableOffl neFTR nterested nParam,
    EnableTwh nCollabF lterClusterParam,
    EnableTwh nMult ClusterParam,
    EnableRet etBasedD ffus onParam,
    Custom zedRetr evalBasedRet etD ffus onS ce
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableOffl ne nterested nParam,
      EnableOffl neFTR nterested nParam,
      EnableTwh nCollabF lterClusterParam,
      EnableTwh nMult ClusterParam,
      EnableRet etBasedD ffus onParam
    )

    val str ngFSOverr des =
      FeatureSw chOverr deUt l.getStr ngFSOverr des(
        Custom zedRetr evalBasedRet etD ffus onS ce
      )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(str ngFSOverr des: _*)
      .bu ld()
  }
}
