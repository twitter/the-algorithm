package com.tw ter.v s b l y.bu lder

 mport com.tw ter.datatools.ent yserv ce.ent  es.thr ftscala.Fleet nterst  al
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.Dec der.NullDec der
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.logp pel ne.cl ent.EventPubl s rManager
 mport com.tw ter.logp pel ne.cl ent.ser al zers.EventLogMsgThr ftStructSer al zer
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel
 mport com.tw ter.v s b l y.bu lder.Verd ctLogger.Fa lureCounterNa 
 mport com.tw ter.v s b l y.bu lder.Verd ctLogger.SuccessCounterNa 
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.logg ng.thr ftscala.Act onS ce
 mport com.tw ter.v s b l y.logg ng.thr ftscala.Ent y d
 mport com.tw ter.v s b l y.logg ng.thr ftscala.Ent y dType
 mport com.tw ter.v s b l y.logg ng.thr ftscala.Ent y dValue
 mport com.tw ter.v s b l y.logg ng.thr ftscala. althAct onType
 mport com.tw ter.v s b l y.logg ng.thr ftscala.M s nfoPol cyCategory
 mport com.tw ter.v s b l y.logg ng.thr ftscala.VFL bType
 mport com.tw ter.v s b l y.logg ng.thr ftscala.VFVerd ctLogEntry
 mport com.tw ter.v s b l y.models.Content d
 mport com.tw ter.v s b l y.rules._

object Verd ctLogger {

  pr vate val BaseStatsNa space = "vf_verd ct_logger"
  pr vate val Fa lureCounterNa  = "fa lures"
  pr vate val SuccessCounterNa  = "successes"
  val LogCategoryNa : Str ng = "v s b l y_f lter ng_verd cts"

  val Empty: Verd ctLogger = new Verd ctLogger(NullStatsRece ver, NullDec der, None)

  def apply(
    statsRece ver: StatsRece ver,
    dec der: Dec der
  ): Verd ctLogger = {
    val eventPubl s r: EventPubl s r[VFVerd ctLogEntry] =
      EventPubl s rManager
        .newScr bePubl s rBu lder(
          LogCategoryNa ,
          EventLogMsgThr ftStructSer al zer.getNewSer al zer[VFVerd ctLogEntry]()).bu ld()
    new Verd ctLogger(statsRece ver.scope(BaseStatsNa space), dec der, So (eventPubl s r))
  }
}

class Verd ctLogger(
  statsRece ver: StatsRece ver,
  dec der: Dec der,
  publ s rOpt: Opt on[EventPubl s r[VFVerd ctLogEntry]]) {

  def log(
    verd ctLogEntry: VFVerd ctLogEntry,
    publ s r: EventPubl s r[VFVerd ctLogEntry]
  ): Un  = {
    publ s r
      .publ sh(verd ctLogEntry)
      .onSuccess(_ => statsRece ver.counter(SuccessCounterNa ). ncr())
      .onFa lure { e =>
        statsRece ver.counter(Fa lureCounterNa ). ncr()
        statsRece ver.scope(Fa lureCounterNa ).counter(e.getClass.getNa ). ncr()
      }
  }

  pr vate def toEnt y d(content d: Content d): Opt on[Ent y d] = {
    content d match {
      case Content d.T et d( d) => So (Ent y d(Ent y dType.T et d, Ent y dValue.Ent y d( d)))
      case Content d.User d( d) => So (Ent y d(Ent y dType.User d, Ent y dValue.Ent y d( d)))
      case Content d.QuotedT etRelat onsh p(outerT et d, _) =>
        So (Ent y d(Ent y dType.T et d, Ent y dValue.Ent y d(outerT et d)))
      case Content d.Not f cat on d(So ( d)) =>
        So (Ent y d(Ent y dType.Not f cat on d, Ent y dValue.Ent y d( d)))
      case Content d.Dm d( d) => So (Ent y d(Ent y dType.Dm d, Ent y dValue.Ent y d( d)))
      case Content d.BlenderT et d( d) =>
        So (Ent y d(Ent y dType.T et d, Ent y dValue.Ent y d( d)))
      case Content d.SpacePlusUser d(_) =>
    }
  }

  pr vate def getLogEntryData(
    act ngRule: Opt on[Rule],
    secondaryAct ngRules: Seq[Rule],
    verd ct: Act on,
    secondaryVerd cts: Seq[Act on],
    resolvedFeatureMap: Map[Feature[_], Any]
  ): (Seq[Str ng], Seq[Act onS ce], Seq[ althAct onType], Opt on[Fleet nterst  al]) = {
    act ngRule
      .f lter {
        case dec deredRule: DoesLogVerd ctDec dered =>
          dec der. sAva lable(dec deredRule.verd ctLogDec derKey.toStr ng)
        case rule: DoesLogVerd ct => true
        case _ => false
      }
      .map { pr maryRule =>
        val secondaryRulesAndVerd cts = secondaryAct ngRules z p secondaryVerd cts
        var act ngRules: Seq[Rule] = Seq(pr maryRule)
        var act ngRuleNa s: Seq[Str ng] = Seq(pr maryRule.na )
        var act onS ces: Seq[Act onS ce] = Seq()
        var  althAct onTypes: Seq[ althAct onType] = Seq(verd ct.to althAct onTypeThr ft.get)

        val m s nfoPol cyCategory: Opt on[Fleet nterst  al] = {
          verd ct match {
            case soft ntervent on: Soft ntervent on =>
              soft ntervent on.fleet nterst  al
            case t et nterst  al: T et nterst  al =>
              t et nterst  al.soft ntervent on.flatMap(_.fleet nterst  al)
            case _ => None
          }
        }

        secondaryRulesAndVerd cts.foreach(ruleAndVerd ct => {
           f (ruleAndVerd ct._1. s nstanceOf[DoesLogVerd ct]) {
            act ngRules = act ngRules :+ ruleAndVerd ct._1
            act ngRuleNa s = act ngRuleNa s :+ ruleAndVerd ct._1.na 
             althAct onTypes =  althAct onTypes :+ ruleAndVerd ct._2.to althAct onTypeThr ft.get
          }
        })

        act ngRules.foreach(rule => {
          rule.act onS ceBu lder
            .flatMap(_.bu ld(resolvedFeatureMap, verd ct))
            .map(act onS ce => {
              act onS ces = act onS ces :+ act onS ce
            })
        })
        (act ngRuleNa s, act onS ces,  althAct onTypes, m s nfoPol cyCategory)
      }
      .getOrElse((Seq.empty[Str ng], Seq.empty[Act onS ce], Seq.empty[ althAct onType], None))
  }

  def scr beVerd ct(
    v s b l yResult: V s b l yResult,
    safetyLevel: SafetyLevel,
    vfL bType: VFL bType,
    v e r d: Opt on[Long] = None
  ): Un  = {
    publ s rOpt.foreach { publ s r =>
      toEnt y d(v s b l yResult.content d).foreach { ent y d =>
        v s b l yResult.verd ct.to althAct onTypeThr ft.foreach {  althAct onType =>
          val (act on ngRules, act onS ces,  althAct onTypes, m s nfoPol cyCategory) =
            getLogEntryData(
              act ngRule = v s b l yResult.act ngRule,
              secondaryAct ngRules = v s b l yResult.secondaryAct ngRules,
              verd ct = v s b l yResult.verd ct,
              secondaryVerd cts = v s b l yResult.secondaryVerd cts,
              resolvedFeatureMap = v s b l yResult.resolvedFeatureMap
            )

           f (act on ngRules.nonEmpty) {
            log(
              VFVerd ctLogEntry(
                ent y d = ent y d,
                v e r d = v e r d,
                t  stampMsec = System.currentT  M ll s(),
                vfL bType = vfL bType,
                 althAct onType =  althAct onType,
                safetyLevel = safetyLevel,
                act on ngRules = act on ngRules,
                act onS ces = act onS ces,
                 althAct onTypes =  althAct onTypes,
                m s nfoPol cyCategory =
                  fleet nterst  alToM s nfoPol cyCategory(m s nfoPol cyCategory)
              ),
              publ s r
            )
          }
        }
      }
    }
  }

  def fleet nterst  alToM s nfoPol cyCategory(
    fleet nterst  alOpt on: Opt on[Fleet nterst  al]
  ): Opt on[M s nfoPol cyCategory] = {
    fleet nterst  alOpt on.map {
      case Fleet nterst  al.Gener c =>
        M s nfoPol cyCategory.Gener c
      case Fleet nterst  al.Samm =>
        M s nfoPol cyCategory.Samm
      case Fleet nterst  al.C v c ntegr y =>
        M s nfoPol cyCategory.C v c ntegr y
      case _ => M s nfoPol cyCategory.Unknown
    }
  }

}
