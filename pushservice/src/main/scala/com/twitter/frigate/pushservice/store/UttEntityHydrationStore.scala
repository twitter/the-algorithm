package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter.esc rb rd.ut l.uttcl ent.Cac dUttCl entV2
 mport com.tw ter.esc rb rd.ut l.uttcl ent. nval dUttEnt yExcept on
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.st ch.St ch
 mport com.tw ter.top cl st ng.Top cL st ngV e rContext
 mport com.tw ter.top cl st ng.utt.Local zedEnt y
 mport com.tw ter.top cl st ng.utt.Local zedEnt yFactory
 mport com.tw ter.ut l.Future

/**
 *
 * @param v e rContext: [[Top cL st ngV e rContext]] for f lter ng top c
 * @param semant cCoreEnt y ds: l st of semant c core ent  es to hydrate
 */
case class UttEnt yHydrat onQuery(
  v e rContext: Top cL st ngV e rContext,
  semant cCoreEnt y ds: Seq[Long])

/**
 *
 * @param cac dUttCl entV2
 * @param statsRece ver
 */
class UttEnt yHydrat onStore(
  cac dUttCl entV2: Cac dUttCl entV2,
  statsRece ver: StatsRece ver,
  log: Logger) {

  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val uttEnt yNotFound = stats.counter(" nval d_utt_ent y")
  pr vate val dev ceLanguageM smatch = stats.counter("language_m smatch")

  /**
   * Semant cCore recom nds sett ng language and country code to None to fetch all local zed top c
   * na s and apply f lter ng for locales on   end
   *
   *   use [[Local zedEnt yFactory]] from [[Top cl st ng]] l brary to f lter out top c na  based
   * on user locale
   *
   * So (Local zedEnt y) - Local zedUttEnt y found
   * None - Local zedUttEnt y not found
   */
  def getLocal zedTop cEnt  es(
    query: UttEnt yHydrat onQuery
  ): Future[Seq[Opt on[Local zedEnt y]]] = St ch.run {
    St ch.collect {
      query.semant cCoreEnt y ds.map { semant cCoreEnt y d =>
        val uttEnt y = cac dUttCl entV2.cac dGetUttEnt y(
          language = None,
          country = None,
          vers on = None,
          ent y d = semant cCoreEnt y d)

        uttEnt y
          .map { uttEnt y tadata =>
            val local zedEnt y = Local zedEnt yFactory.getLocal zedEnt y(
              uttEnt y tadata,
              query.v e rContext,
              enable nternat onalTop cs = true,
              enableTop cDescr pt on = true)
            // update counter
            local zedEnt y.foreach { ent y =>
               f (!ent y.na Matc sDev ceLanguage) dev ceLanguageM smatch. ncr()
            }

            local zedEnt y
          }.handle {
            case e:  nval dUttEnt yExcept on =>
              log.error(e.get ssage)
              uttEnt yNotFound. ncr()
              None
          }
      }
    }
  }
}
