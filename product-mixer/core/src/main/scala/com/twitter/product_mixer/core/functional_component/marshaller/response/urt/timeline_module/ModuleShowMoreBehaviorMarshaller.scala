packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.timelonlinelon_modulelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonShowMorelonBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.timelonlinelon_modulelon.ModulelonShowMorelonBelonhaviorRelonvelonalByCount
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ModulelonShowMorelonBelonhaviorMarshallelonr @Injelonct() (
  modulelonShowMorelonBelonhaviorRelonvelonalByCountMarshallelonr: ModulelonShowMorelonBelonhaviorRelonvelonalByCountMarshallelonr) {

  delonf apply(
    modulelonShowMorelonBelonhavior: ModulelonShowMorelonBelonhavior
  ): urt.ModulelonShowMorelonBelonhavior = modulelonShowMorelonBelonhavior match {
    caselon modulelonShowMorelonBelonhaviorRelonvelonalByCount: ModulelonShowMorelonBelonhaviorRelonvelonalByCount =>
      modulelonShowMorelonBelonhaviorRelonvelonalByCountMarshallelonr(modulelonShowMorelonBelonhaviorRelonvelonalByCount)
  }
}
