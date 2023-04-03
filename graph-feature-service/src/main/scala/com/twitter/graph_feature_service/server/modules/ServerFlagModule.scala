packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.modulelons

import com.twittelonr.injelonct.TwittelonrModulelon

objelonct SelonrvelonrFlagNamelons {
  final val NumWorkelonrs = "selonrvicelon.num_workelonrs"
  final val SelonrvicelonRolelon = "selonrvicelon.rolelon"
  final val Selonrvicelonelonnv = "selonrvicelon.elonnv"

  final val MelonmCachelonClielonntNamelon = "selonrvicelon.melonm_cachelon_clielonnt_namelon"
  final val MelonmCachelonPath = "selonrvicelon.melonm_cachelon_path"
}

/**
 * Initializelons relonfelonrelonncelons to thelon flag valuelons delonfinelond in thelon aurora.delonploy filelon.
 * To chelonck what thelon flag valuelons arelon initializelond in runtimelon, selonarch FlagsModulelon in stdout
 */
objelonct SelonrvelonrFlagsModulelon elonxtelonnds TwittelonrModulelon {

  import SelonrvelonrFlagNamelons._

  flag[Int](NumWorkelonrs, "Num of workelonrs")

  flag[String](SelonrvicelonRolelon, "Selonrvicelon Rolelon")

  flag[String](Selonrvicelonelonnv, "Selonrvicelon elonnv")

  flag[String](MelonmCachelonClielonntNamelon, "MelonmCachelon Clielonnt Namelon")

  flag[String](MelonmCachelonPath, "MelonmCachelon Path")
}
