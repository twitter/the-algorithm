package com.tw ter.representat on_manager.conf g

 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on

/*
 * T   s RMS cl ent conf g class.
 *   only support sett ng up  n  mory cac  params for now, but   expect to enable ot r
 * custom sat ons  n t  near future e.g. request t  out
 *
 * --------------------------------------------
 * PLEASE NOTE:
 * Hav ng  n- mory cac   s not necessar ly a free performance w n, anyone cons der ng   should
 *  nvest gate rat r than bl ndly enabl ng  
 * */
class Cl entConf g( n mCac ParamsOverr des: Map[
  (Embedd ngType, ModelVers on),
   n moryCac Params
] = Map.empty) {
  //  n  mory cac  conf g per embedd ng
  val  n mCac Params = Default n moryCac Conf g.cac ParamsMap ++  n mCac ParamsOverr des
  val  n moryCac Conf g = new  n moryCac Conf g( n mCac Params)
}

object DefaultCl entConf g extends Cl entConf g
