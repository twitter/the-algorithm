packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonShowMorelonBelonhaviorRelonvelonalByCount
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ModulelonShowMorelonBelonhaviorRelonvelonalByCountMarshallelonr @Injelonct() () {

  delonf apply(
    modulelonShowMorelonBelonhaviorRelonvelonalByCount: ModulelonShowMorelonBelonhaviorRelonvelonalByCount
  ): urt.ModulelonShowMorelonBelonhavior =
    urt.ModulelonShowMorelonBelonhavior.RelonvelonalByCount(
      urt.ModulelonShowMorelonBelonhaviorRelonvelonalByCount(
        initialItelonmsCount = modulelonShowMorelonBelonhaviorRelonvelonalByCount.initialItelonmsCount,
        showMorelonItelonmsCount = modulelonShowMorelonBelonhaviorRelonvelonalByCount.showMorelonItelonmsCount
      )
    )
}
