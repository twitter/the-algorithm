package com.tw ter.representat on_manager.conf g

 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.ut l.Durat on

/*
 * --------------------------------------------
 * PLEASE NOTE:
 * Hav ng  n- mory cac   s not necessar ly a free performance w n, anyone cons der ng   should
 *  nvest gate rat r than bl ndly enabl ng  
 * --------------------------------------------
 * */

sealed tra   n moryCac Params

/*
 * T  holds params that  s requ red to set up a  n- m cac  for a s ngle embedd ng store
 */
case class Enabled n moryCac Params(
  ttl: Durat on,
  maxKeys:  nt,
  cac Na : Str ng)
  extends  n moryCac Params
object D sabled n moryCac Params extends  n moryCac Params

/*
 * T   s t  class for t   n- mory cac  conf g. Cl ent could pass  n t  r own cac ParamsMap to
 * create a new  n moryCac Conf g  nstead of us ng t  Default n moryCac Conf g object below
 * */
class  n moryCac Conf g(
  cac ParamsMap: Map[
    (Embedd ngType, ModelVers on),
     n moryCac Params
  ] = Map.empty) {

  def getCac Setup(
    embedd ngType: Embedd ngType,
    modelVers on: ModelVers on
  ):  n moryCac Params = {
    // W n requested embedd ng type doesn't ex st,   return D sabled n moryCac Params
    cac ParamsMap.getOrElse((embedd ngType, modelVers on), D sabled n moryCac Params)
  }
}

/*
 * Default conf g for t   n- mory cac 
 * Cl ents can d rectly  mport and use t  one  f t y don't want to set up a custom sed conf g
 * */
object Default n moryCac Conf g extends  n moryCac Conf g {
  // set default to no  n- mory cach ng
  val cac ParamsMap = Map.empty
}
