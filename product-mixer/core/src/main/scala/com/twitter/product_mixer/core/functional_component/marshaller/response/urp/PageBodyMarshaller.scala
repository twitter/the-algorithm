packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urp

import com.twittelonr.pagelons.relonndelonr.{thriftscala => urp}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.PagelonBody
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.SelongmelonntelondTimelonlinelonsPagelonBody
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urp.TimelonlinelonKelonyPagelonBody
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class PagelonBodyMarshallelonr @Injelonct() (
  timelonlinelonKelonyMarshallelonr: TimelonlinelonKelonyMarshallelonr,
  selongmelonntelondTimelonlinelonsMarshallelonr: SelongmelonntelondTimelonlinelonsMarshallelonr) {

  delonf apply(pagelonBody: PagelonBody): urp.PagelonBody = pagelonBody match {
    caselon pagelonBody: TimelonlinelonKelonyPagelonBody =>
      urp.PagelonBody.Timelonlinelon(timelonlinelonKelonyMarshallelonr(pagelonBody.timelonlinelon))
    caselon pagelonBody: SelongmelonntelondTimelonlinelonsPagelonBody =>
      urp.PagelonBody.SelongmelonntelondTimelonlinelons(selongmelonntelondTimelonlinelonsMarshallelonr(pagelonBody))
  }
}
