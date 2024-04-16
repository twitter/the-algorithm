package com.tw ter.graph_feature_serv ce.server.modules

 mport com.tw ter. nject.Tw terModule

object ServerFlagNa s {
  f nal val NumWorkers = "serv ce.num_workers"
  f nal val Serv ceRole = "serv ce.role"
  f nal val Serv ceEnv = "serv ce.env"

  f nal val  mCac Cl entNa  = "serv ce. m_cac _cl ent_na "
  f nal val  mCac Path = "serv ce. m_cac _path"
}

/**
 *  n  al zes references to t  flag values def ned  n t  aurora.deploy f le.
 * To c ck what t  flag values are  n  al zed  n runt  , search FlagsModule  n stdout
 */
object ServerFlagsModule extends Tw terModule {

   mport ServerFlagNa s._

  flag[ nt](NumWorkers, "Num of workers")

  flag[Str ng](Serv ceRole, "Serv ce Role")

  flag[Str ng](Serv ceEnv, "Serv ce Env")

  flag[Str ng]( mCac Cl entNa , " mCac  Cl ent Na ")

  flag[Str ng]( mCac Path, " mCac  Path")
}
