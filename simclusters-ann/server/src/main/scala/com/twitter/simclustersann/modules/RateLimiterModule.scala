packagelon com.twittelonr.simclustelonrsann.modulelons

import com.googlelon.common.util.concurrelonnt.RatelonLimitelonr
import com.googlelon.injelonct.Providelons
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.simclustelonrsann.common.FlagNamelons.RatelonLimitelonrQPS
import javax.injelonct.Singlelonton

objelonct RatelonLimitelonrModulelon elonxtelonnds TwittelonrModulelon {
  flag[Int](
    namelon = RatelonLimitelonrQPS,
    delonfault = 1000,
    helonlp = "Thelon QPS allowelond by thelon ratelon limitelonr."
  )

  @Singlelonton
  @Providelons
  delonf providelonsRatelonLimitelonr(
    @Flag(RatelonLimitelonrQPS) ratelonLimitelonrQps: Int
  ): RatelonLimitelonr =
    RatelonLimitelonr.crelonatelon(ratelonLimitelonrQps)
}
