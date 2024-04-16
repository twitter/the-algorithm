package com.tw ter.follow_recom ndat ons.common.transforms.mod fy_soc al_proof

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.follow_recom ndat ons.common.base.GatedTransform
 mport com.tw ter.follow_recom ndat ons.common.cl ents.graph_feature_serv ce.GraphFeatureServ ceCl ent
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derKey
 mport com.tw ter.graph_feature_serv ce.thr ftscala.EdgeType
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.logg ng.Logg ng
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

object Mod fySoc alProof {
  val GfsLagDurat on: Durat on = 14.days
  val Gfs ntersect on ds:  nt = 3
  val Sgs ntersect on ds:  nt = 10
  val LeftEdgeTypes: Set[EdgeType] = Set(EdgeType.Follow ng)
  val R ghtEdgeTypes: Set[EdgeType] = Set(EdgeType.Follo dBy)

  /**
   * G ven t   ntersect on  D's for a part cular cand date, update t  cand date's soc al proof
   * @param cand date          cand date object
   * @param followProof        follow proof to be added ( ncludes  d's and count)
   * @param stats              stats for track ng
   * @return                   updated cand date object
   */
  def add ntersect on dsToCand date(
    cand date: Cand dateUser,
    followProof: FollowProof,
    stats: StatsRece ver
  ): Cand dateUser = {
    // create updated set of soc al proof
    val updatedFollo dByOpt = cand date.follo dBy match {
      case So (ex st ngFollo dBy) => So ((followProof.follo dBy ++ ex st ngFollo dBy).d st nct)
      case None  f followProof.follo dBy.nonEmpty => So (followProof.follo dBy.d st nct)
      case _ => None
    }

    val updatedFollowProof = updatedFollo dByOpt.map { updatedFollo dBy =>
      val updatedCount = followProof.num ds.max(updatedFollo dBy.s ze)
      // track stats
      val numSoc alProofAdded = updatedFollo dBy.s ze - cand date.follo dBy.s ze
      addCand datesW hSoc alContextCountStat(stats, numSoc alProofAdded)
      FollowProof(updatedFollo dBy, updatedCount)
    }

    cand date.setFollowProof(updatedFollowProof)
  }

  pr vate def addCand datesW hSoc alContextCountStat(
    statsRece ver: StatsRece ver,
    count:  nt
  ): Un  = {
     f (count > 3) {
      statsRece ver.counter("4_and_more"). ncr()
    } else {
      statsRece ver.counter(count.toStr ng). ncr()
    }
  }
}

/**
 * T  class makes a request to gfs/sgs for hydrat ng add  onal soc al proof on each of t 
 * prov ded cand dates.
 */
@S ngleton
class Mod fySoc alProof @ nject() (
  gfsCl ent: GraphFeatureServ ceCl ent,
  soc alGraphCl ent: Soc alGraphCl ent,
  statsRece ver: StatsRece ver,
  dec der: Dec der = Dec der.True)
    extends Logg ng {
   mport Mod fySoc alProof._

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val addedStats = stats.scope("num_soc al_proof_added_per_cand date")
  pr vate val gfsStats = stats.scope("graph_feature_serv ce")
  pr vate val sgsStats = stats.scope("soc al_graph_serv ce")
  pr vate val prev ousProofEmptyCounter = stats.counter("prev ous_proof_empty")
  pr vate val emptyFollowProofCounter = stats.counter("empty_follo d_proof")

  /**
   * For each cand date prov ded,   get t   ntersect on ds bet en t  user and t  cand date,
   * append ng t  un que results to t  soc al proof (follo dBy f eld)  f not already prev ously
   * seen   query GFS for all users, except for cases spec f ed v a t  mustCallSgs f eld or for
   * very new users, who would not have any data  n GFS, due to t  lag durat on of t  serv ce's
   * process ng. t   s determ ned by GfsLagDurat on
   * @param user d  d of t  target user whom   prov de recom ndat ons for
   * @param cand dates l st of cand dates
   * @param  ntersect on dsNum  f prov ded, determ nes t  max mum number of accounts   want to be hydrated for soc al proof
   * @param mustCallSgs Determ nes  f   should query SGS regardless of user age or not.
   * @return l st of cand dates updated w h add  onal soc al proof
   */
  def hydrateSoc alProof(
    user d: Long,
    cand dates: Seq[Cand dateUser],
     ntersect on dsNum: Opt on[ nt] = None,
    mustCallSgs: Boolean = false,
    callSgsCac dColumn: Boolean = false,
    gfsLagDurat on: Durat on = GfsLagDurat on,
    gfs ntersect on ds:  nt = Gfs ntersect on ds,
    sgs ntersect on ds:  nt = Sgs ntersect on ds,
  ): St ch[Seq[Cand dateUser]] = {
    addCand datesW hSoc alContextCountStat(
      stats.scope("soc al_context_count_before_hydrat on"),
      cand dates.count(_.follo dBy. sDef ned)
    )
    val cand date ds = cand dates.map(_. d)
    val userAgeOpt = Snowflake d.t  From dOpt(user d).map(T  .now - _)

    // t  dec der gate  s used to determ ne what % of requests  s allo d to call
    // Graph Feature Serv ce. t   s useful for ramp ng down requests to Graph Feature Serv ce
    // w n necessary
    val dec derKey: Str ng = Dec derKey.EnableGraphFeatureServ ceRequests.toStr ng
    val enableGfsRequests: Boolean = dec der. sAva lable(dec derKey, So (RandomRec p ent))

    //  f new query sgs
    val (cand dateTo ntersect on dsMapFut,  sGfs) =
       f (!enableGfsRequests || mustCallSgs || userAgeOpt.ex sts(_ < gfsLagDurat on)) {
        (
           f (callSgsCac dColumn)
            soc alGraphCl ent.get ntersect onsFromCac dColumn(
              user d,
              cand date ds,
               ntersect on dsNum.getOrElse(sgs ntersect on ds)
            )
          else
            soc alGraphCl ent.get ntersect ons(
              user d,
              cand date ds,
               ntersect on dsNum.getOrElse(sgs ntersect on ds)),
          false)
      } else {
        (
          gfsCl ent.get ntersect ons(
            user d,
            cand date ds,
             ntersect on dsNum.getOrElse(gfs ntersect on ds)),
          true)
      }
    val f nalCand dates = cand dateTo ntersect on dsMapFut
      .map { cand dateTo ntersect on dsMap =>
        {
          prev ousProofEmptyCounter. ncr(cand dates.count(_.follo dBy.ex sts(_. sEmpty)))
          cand dates.map { cand date =>
            add ntersect on dsToCand date(
              cand date,
              cand dateTo ntersect on dsMap.getOrElse(cand date. d, FollowProof(Seq.empty, 0)),
              addedStats)
          }
        }
      }
      .w h n(250.m ll seconds)(DefaultT  r)
      .rescue {
        case e: Except on =>
          error(e.get ssage)
           f ( sGfs) {
            gfsStats.scope("rescued").counter(e.getClass.getS mpleNa ). ncr()
          } else {
            sgsStats.scope("rescued").counter(e.getClass.getS mpleNa ). ncr()
          }
          St ch.value(cand dates)
      }

    f nalCand dates.onSuccess { cand datesSeq =>
      emptyFollowProofCounter. ncr(cand datesSeq.count(_.follo dBy.ex sts(_. sEmpty)))
      addCand datesW hSoc alContextCountStat(
        stats.scope("soc al_context_count_after_hydrat on"),
        cand datesSeq.count(_.follo dBy. sDef ned)
      )
    }
  }
}

/**
 * T  transform uses Mod fySoc alProof (wh ch makes a request to gfs/sgs) for hydrat ng add  onal
 * soc al proof on each of t  prov ded cand dates.
 */
@S ngleton
class Mod fySoc alProofTransform @ nject() (mod fySoc alProof: Mod fySoc alProof)
    extends GatedTransform[HasCl entContext w h HasParams, Cand dateUser]
    w h Logg ng {

  overr de def transform(
    target: HasCl entContext w h HasParams,
    cand dates: Seq[Cand dateUser]
  ): St ch[Seq[Cand dateUser]] =
    target.getOpt onalUser d
      .map(mod fySoc alProof.hydrateSoc alProof(_, cand dates)).getOrElse(St ch.value(cand dates))
}
