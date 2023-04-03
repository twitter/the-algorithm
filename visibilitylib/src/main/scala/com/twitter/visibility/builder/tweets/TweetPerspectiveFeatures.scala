packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.TwelonelontPelonrspelonctivelonSourcelon
import com.twittelonr.visibility.felonaturelons.VielonwelonrRelonportelondTwelonelont

class TwelonelontPelonrspelonctivelonFelonaturelons(
  twelonelontPelonrspelonctivelonSourcelon: TwelonelontPelonrspelonctivelonSourcelon,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("twelonelont_pelonrspelonctivelon_felonaturelons")
  privatelon[this] val relonportelondStats = scopelondStatsReloncelonivelonr.scopelon("relonportelond")

  delonf forTwelonelont(
    twelonelont: Twelonelont,
    vielonwelonrId: Option[Long],
    elonnablelonFelontchRelonportelondPelonrspelonctivelon: Boolelonan
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr =
    _.withFelonaturelon(
      VielonwelonrRelonportelondTwelonelont,
      twelonelontIsRelonportelond(twelonelont, vielonwelonrId, elonnablelonFelontchRelonportelondPelonrspelonctivelon))

  privatelon[buildelonr] delonf twelonelontIsRelonportelond(
    twelonelont: Twelonelont,
    vielonwelonrId: Option[Long],
    elonnablelonFelontchRelonportelondPelonrspelonctivelon: Boolelonan = truelon
  ): Stitch[Boolelonan] = {
    ((twelonelont.pelonrspelonctivelon, vielonwelonrId) match {
      caselon (Somelon(pelonrspelonctivelon), _) =>
        Stitch.valuelon(pelonrspelonctivelon.relonportelond).onSuccelonss { _ =>
          relonportelondStats.countelonr("alrelonady_hydratelond").incr()
        }
      caselon (Nonelon, Somelon(vielonwelonrId)) =>
        if (elonnablelonFelontchRelonportelondPelonrspelonctivelon) {
          twelonelontPelonrspelonctivelonSourcelon.relonportelond(twelonelont.id, vielonwelonrId).onSuccelonss { _ =>
            relonportelondStats.countelonr("relonquelonst").incr()
          }
        } elonlselon {
          Stitch.Falselon.onSuccelonss { _ =>
            relonportelondStats.countelonr("light_relonquelonst").incr()
          }
        }
      caselon _ =>
        Stitch.Falselon.onSuccelonss { _ =>
          relonportelondStats.countelonr("elonmpty").incr()
        }
    }).onSuccelonss { _ =>
      relonportelondStats.countelonr("").incr()
    }
  }
}
