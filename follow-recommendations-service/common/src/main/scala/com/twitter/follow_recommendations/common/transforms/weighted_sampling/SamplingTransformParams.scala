package com.tw ter.follow_recom ndat ons.common.transforms.  ghted_sampl ng

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object Sampl ngTransformParams {

  case object TopKF xed //  nd cates how many of t  f srt K who-to-follow recom ndat ons are reserved for t  cand dates w h largest K Cand dateUser.score w re t se cand dates are sorted  n decreas ng order of score
      extends FSBoundedParam[ nt](
        na  = "post_nux_ml_flow_  ghted_sampl ng_top_k_f xed",
        default = 0,
        m n = 0,
        max = 100)

  case object Mult pl cat veFactor // Cand dateUser.score gets transfor d to mult pl cat veFactor*Cand dateUser.score before sampl ng from t  Plackett-Luce d str but on
      extends FSBoundedParam[Double](
        na  = "post_nux_ml_flow_  ghted_sampl ng_mult pl cat ve_factor",
        default = 1.0,
        m n = -1000.0,
        max = 1000.0)

  case object Scr beRank ng nfo nSampl ngTransform
      extends FSParam[Boolean]("sampl ng_transform_scr be_rank ng_ nfo", false)

}
