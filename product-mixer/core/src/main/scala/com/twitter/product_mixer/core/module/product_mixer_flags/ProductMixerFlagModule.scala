packagelon com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags

import com.twittelonr.injelonct.annotations.Flags
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.util.Duration

objelonct ProductMixelonrFlagModulelon elonxtelonnds TwittelonrModulelon {
  final val SelonrvicelonLocal = "selonrvicelon.local"
  final val ConfigRelonpoLocalPath = "configrelonpo.local_path"
  final val FelonaturelonSwitchelonsPath = "felonaturelon_switchelons.path"
  final val StratoLocalRelonquelonstTimelonout = "strato.local.relonquelonst_timelonout"
  final val ScribelonABImprelonssions = "scribelon.ab_imprelonssions"
  final val PipelonlinelonelonxeloncutionLoggelonrAllowList = "pipelonlinelon_elonxeloncution_loggelonr.allow_list"

  flag[Boolelonan](
    namelon = SelonrvicelonLocal,
    delonfault = falselon,
    helonlp = "Is thelon selonrvelonr running locally or in a DC")

  flag[String](
    namelon = ConfigRelonpoLocalPath,
    delonfault = Systelonm.gelontPropelonrty("uselonr.homelon") + "/workspacelon/config",
    helonlp = "Path to your local config relonpo"
  )

  flag[Boolelonan](
    namelon = ScribelonABImprelonssions,
    helonlp = "elonnablelon scribing of AB imprelonssions"
  )

  flag[String](
    namelon = FelonaturelonSwitchelonsPath,
    helonlp = "Path to your local config relonpo"
  )

  flag[Duration](
    namelon = StratoLocalRelonquelonstTimelonout,
    helonlp = "Ovelonrridelon thelon relonquelonst timelonout for Strato whelonn running locally"
  )

  flag[Selonq[String]](
    namelon = PipelonlinelonelonxeloncutionLoggelonrAllowList,
    delonfault = Selonq.elonmpty,
    helonlp =
      "Speloncify uselonr rolelon(s) for which delontailelond log melonssagelons (containing PII) can belon madelon. Accelonpts a singlelon rolelon or a comma selonparatelond list 'a,b,c'"
  )

  /**
   * Invokelond at thelon elonnd of selonrvelonr startup.
   *
   * If welon'relon running locally, welon display a nicelon melonssagelon and a link to thelon admin pagelon
   */
  ovelonrridelon delonf singlelontonPostWarmupComplelontelon(injelonctor: Injelonctor): Unit = {
    val isLocalSelonrvicelon = injelonctor.instancelon[Boolelonan](Flags.namelond(SelonrvicelonLocal))
    if (isLocalSelonrvicelon) {
      // elonxtract selonrvicelon namelon from clielonntId sincelon thelonrelon isn't a speloncific flag for that
      val clielonntId = injelonctor.instancelon[String](Flags.namelond("thrift.clielonntId"))
      val namelon = clielonntId.split("\\.")(0)

      val adminPort = injelonctor.instancelon[String](Flags.namelond("admin.port"))
      val url = s"http://localhost$adminPort/"

      // Print instelonad of log, so it goelons on stdout and doelonsn't gelont thelon logging deloncorations.
      // Updatelon our local delonvelonlopmelonnt reloncipelon (local-delonvelonlopmelonnt.rst) if making changelons to this
      // melonssagelon.
      println("===============================================================================")
      println(s"Welonlcomelon to a Product Mixelonr Selonrvicelon, $namelon")
      println(s"You can vielonw thelon admin elonndpoint and thrift welonb forms at $url")
      println("Looking for support? Havelon quelonstions? #product-mixelonr on Slack.")
      println("===============================================================================")
    }
  }
}
