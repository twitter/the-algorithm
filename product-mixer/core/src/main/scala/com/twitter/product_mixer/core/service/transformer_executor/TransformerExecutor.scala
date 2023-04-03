packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.transformelonr_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.transformelonr.Transformelonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.stitch.Arrow

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class Transformelonrelonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr) elonxtelonnds elonxeloncutor {
  delonf arrow[In, Out](
    transformelonr: Transformelonr[In, Out],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[In, Out] = {
    wrapComponelonntWithelonxeloncutorBookkelonelonping(
      contelonxt,
      transformelonr.idelonntifielonr
    )(Arrow.map(transformelonr.transform))
  }
}
