package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.product.follow ng.model.Ho M xerExternalStr ngs
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Ch ldFeedbackAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.R chFeedbackBehav orReportT et
 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class ReportT etCh ldFeedbackAct onBu lder @ nject() (
  @ProductScoped str ngCenter: Str ngCenter,
  externalStr ngs: Ho M xerExternalStr ngs) {

  def apply(
    cand date: T etCand date
  ): Opt on[Ch ldFeedbackAct on] = {
    So (
      Ch ldFeedbackAct on(
        feedbackType = R chBehav or,
        prompt = So (str ngCenter.prepare(externalStr ngs.reportT etStr ng)),
        conf rmat on = None,
        feedbackUrl = None,
        hasUndoAct on = So (true),
        conf rmat onD splayType = None,
        cl entEvent nfo = None,
         con = So ( con.Flag),
        r chBehav or = So (R chFeedbackBehav orReportT et(cand date. d)),
        subprompt = None
      )
    )
  }
}
