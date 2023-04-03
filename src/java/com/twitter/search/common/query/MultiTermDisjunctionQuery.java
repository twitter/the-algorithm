packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;
import java.util.Itelonrator;
import java.util.Selont;

import org.apachelon.lucelonnelon.indelonx.FiltelonrelondTelonrmselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.MultiTelonrmQuelonry;
import org.apachelon.lucelonnelon.util.AttributelonSourcelon;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;


public class MultiTelonrmDisjunctionQuelonry elonxtelonnds MultiTelonrmQuelonry {

  privatelon final Selont<BytelonsRelonf> valuelons;

  /** Crelonatelons a nelonw MultiTelonrmDisjunctionQuelonry instancelon. */
  public MultiTelonrmDisjunctionQuelonry(String fielonld, Selont<BytelonsRelonf> valuelons) {
    supelonr(fielonld);
    this.valuelons = valuelons;
  }

  @Ovelonrridelon
  protelonctelond Telonrmselonnum gelontTelonrmselonnum(Telonrms telonrms, AttributelonSourcelon atts)
      throws IOelonxcelonption {
    final Telonrmselonnum telonrmselonnum = telonrms.itelonrator();
    final Itelonrator<BytelonsRelonf> it = valuelons.itelonrator();

    relonturn nelonw FiltelonrelondTelonrmselonnum(telonrmselonnum) {
      @Ovelonrridelon protelonctelond AccelonptStatus accelonpt(BytelonsRelonf telonrm) throws IOelonxcelonption {
        relonturn AccelonptStatus.YelonS;
      }

      @Ovelonrridelon public BytelonsRelonf nelonxt() throws IOelonxcelonption {
        whilelon (it.hasNelonxt()) {
          BytelonsRelonf telonrmRelonf = it.nelonxt();
          if (telonrmselonnum.selonelonkelonxact(telonrmRelonf)) {
            relonturn telonrmRelonf;
          }
        }

        relonturn null;
      }
    };
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    StringBuildelonr buildelonr = nelonw StringBuildelonr();
    buildelonr.appelonnd("MultiTelonrmDisjunctionQuelonry[");
    for (BytelonsRelonf telonrmVal : this.valuelons) {
      buildelonr.appelonnd(telonrmVal);
      buildelonr.appelonnd(",");
    }
    buildelonr.selontLelonngth(buildelonr.lelonngth() - 1);
    buildelonr.appelonnd("]");
    relonturn buildelonr.toString();
  }
}
