packagelon com.twittelonr.cr_mixelonr.modulelon.corelon

import com.twittelonr.injelonct.TwittelonrModulelon

objelonct CrMixelonrFlagNamelon {
  val SelonRVICelon_FLAG = "cr_mixelonr.flag"
  val DarkTrafficFiltelonrDeloncidelonrKelony = "thrift.dark.traffic.filtelonr.deloncidelonr_kelony"
}

objelonct CrMixelonrFlagModulelon elonxtelonnds TwittelonrModulelon {
  import CrMixelonrFlagNamelon._

  flag[Boolelonan](namelon = SelonRVICelon_FLAG, delonfault = falselon, helonlp = "This is a CR Mixelonr flag")

  flag[String](
    namelon = DarkTrafficFiltelonrDeloncidelonrKelony,
    delonfault = "dark_traffic_filtelonr",
    helonlp = "Dark traffic filtelonr deloncidelonr kelony"
  )
}
