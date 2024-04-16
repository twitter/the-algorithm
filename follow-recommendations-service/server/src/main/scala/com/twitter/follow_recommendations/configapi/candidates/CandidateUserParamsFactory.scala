package com.tw ter.follow_recom ndat ons.conf gap .cand dates

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.conf gap .params.GlobalParams
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Cand dateParamsFactory  s pr mar ly used for "producer s de" exper  nts, don't use   on consu r s de exper  nts
 */
@S ngleton
class Cand dateUserParamsFactory[T <: HasParams w h HasD splayLocat on] @ nject() (
  conf g: Conf g,
  cand dateContextFactory: Cand dateUserContextFactory,
  statsRece ver: StatsRece ver) {
  pr vate val stats = new  mo z ngStatsRece ver(statsRece ver.scope("conf gap _cand date_params"))
  def apply(cand dateContext: Cand dateUser, request: T): Cand dateUser = {
     f (cand dateContext.params == Params. nval d) {
       f (request.params(GlobalParams.EnableCand dateParamHydrat ons)) {
        cand dateContext.copy(params =
          conf g(cand dateContextFactory(cand dateContext, request.d splayLocat on), stats))
      } else {
        cand dateContext.copy(params = Params.Empty)
      }
    } else {
      cand dateContext
    }
  }
}
