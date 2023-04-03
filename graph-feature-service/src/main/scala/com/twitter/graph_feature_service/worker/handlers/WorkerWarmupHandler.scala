packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.workelonr.handlelonrs

import com.twittelonr.finatra.thrift.routing.ThriftWarmup
import com.twittelonr.injelonct.Logging
import com.twittelonr.injelonct.utils.Handlelonr
import javax.injelonct.{Injelonct, Singlelonton}

@Singlelonton
class WorkelonrWarmupHandlelonr @Injelonct() (warmup: ThriftWarmup) elonxtelonnds Handlelonr with Logging {

  ovelonrridelon delonf handlelon(): Unit = {
    info("Warmup Donelon!")
  }
}
