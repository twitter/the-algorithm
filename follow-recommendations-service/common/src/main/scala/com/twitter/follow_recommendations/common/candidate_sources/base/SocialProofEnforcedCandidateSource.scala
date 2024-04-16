package com.tw ter.follow_recom ndat ons.common.cand date_s ces.base

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.transforms.mod fy_soc al_proof.Mod fySoc alProof
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.Durat on

abstract class Soc alProofEnforcedCand dateS ce(
  cand dateS ce: Cand dateS ce[HasCl entContext w h HasParams, Cand dateUser],
  mod fySoc alProof: Mod fySoc alProof,
  m nNumSoc alProofsRequ red:  nt,
  overr de val  dent f er: Cand dateS ce dent f er,
  baseStatsRece ver: StatsRece ver)
    extends Cand dateS ce[HasCl entContext w h HasParams, Cand dateUser] {

  val statsRece ver = baseStatsRece ver.scope( dent f er.na )

  overr de def apply(target: HasCl entContext w h HasParams): St ch[Seq[Cand dateUser]] = {
    val mustCallSgs: Boolean = target.params(Soc alProofEnforcedCand dateS ceParams.MustCallSgs)
    val callSgsCac dColumn: Boolean =
      target.params(Soc alProofEnforcedCand dateS ceParams.CallSgsCac dColumn)
    val Query ntersect on dsNum:  nt =
      target.params(Soc alProofEnforcedCand dateS ceParams.Query ntersect on dsNum)
    val MaxNumCand datesToAnnotate:  nt =
      target.params(Soc alProofEnforcedCand dateS ceParams.MaxNumCand datesToAnnotate)
    val gfs ntersect on dsNum:  nt =
      target.params(Soc alProofEnforcedCand dateS ceParams.Gfs ntersect on dsNum)
    val sgs ntersect on dsNum:  nt =
      target.params(Soc alProofEnforcedCand dateS ceParams.Sgs ntersect on dsNum)
    val gfsLagDurat on: Durat on =
      target.params(Soc alProofEnforcedCand dateS ceParams.GfsLagDurat on nDays)

    cand dateS ce(target)
      .flatMap { cand dates =>
        val cand datesW houtEnoughSoc alProof = cand dates
          .collect {
            case cand date  f !cand date.follo dBy.ex sts(_.s ze >= m nNumSoc alProofsRequ red) =>
              cand date
          }
        statsRece ver
          .stat("cand dates_w h_no_soc al_proofs").add(cand datesW houtEnoughSoc alProof.s ze)
        val cand datesToAnnotate =
          cand datesW houtEnoughSoc alProof.take(MaxNumCand datesToAnnotate)
        statsRece ver.stat("cand dates_to_annotate").add(cand datesToAnnotate.s ze)

        val annotatedCand datesMapSt ch = target.getOpt onalUser d
          .map { user d =>
            mod fySoc alProof
              .hydrateSoc alProof(
                user d,
                cand datesToAnnotate,
                So (Query ntersect on dsNum),
                mustCallSgs,
                callSgsCac dColumn,
                gfsLagDurat on = gfsLagDurat on,
                gfs ntersect on ds = gfs ntersect on dsNum,
                sgs ntersect on ds = sgs ntersect on dsNum
              ).map { annotatedCand dates =>
                annotatedCand dates
                  .map(annotatedCand date => (annotatedCand date. d, annotatedCand date)).toMap
              }
          }.getOrElse(St ch.value(Map.empty[Long, Cand dateUser]))

        annotatedCand datesMapSt ch.map { annotatedCand datesMap =>
          cand dates
            .flatMap { cand date =>
               f (cand date.follo dBy.ex sts(_.s ze >= m nNumSoc alProofsRequ red)) {
                So (cand date)
              } else {
                annotatedCand datesMap.get(cand date. d).collect {
                  case annotatedCand date
                       f annotatedCand date.follo dBy.ex sts(
                        _.s ze >= m nNumSoc alProofsRequ red) =>
                    annotatedCand date
                }
              }
            }.map(_.w hCand dateS ce( dent f er))
        }
      }
  }
}
