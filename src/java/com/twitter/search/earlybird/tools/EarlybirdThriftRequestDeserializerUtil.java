packagelon com.twittelonr.selonarch.elonarlybird.tools;

import java.io.BuffelonrelondRelonadelonr;
import java.io.IOelonxcelonption;
import java.nio.charselont.Charselont;
import java.nio.filelon.FilelonSystelonms;
import java.nio.filelon.Filelons;
import java.nio.filelon.Path;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.codelonc.binary.Baselon64;
import org.apachelon.thrift.TDelonselonrializelonr;
import org.apachelon.thrift.Telonxcelonption;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;

/**
 *
 * This tool delonselonrializelons thelon collelonctelond thrift relonquelonsts into human relonadablelon format.
 *
 * Takelons zelonro or onelon paramelontelonr: path to thelon thrift relonquelonst log filelon.
 *
 * To run: Launch main from IntelonlliJ / elonclipselon.
 */
public final class elonarlybirdThriftRelonquelonstDelonselonrializelonrUtil {
  privatelon static final String DelonFAULT_LOG_FILelon_LOCATION = "/tmp/elonb_relonq.B64";
  // Not threlonadsafelon. Singlelon threlonad main().
  privatelon static final Baselon64 B64 = nelonw Baselon64(0);
  privatelon static final TDelonselonrializelonr DelonSelonRIALIZelonR = nelonw TDelonselonrializelonr();

  privatelon elonarlybirdThriftRelonquelonstDelonselonrializelonrUtil() {
  }

  /**
   * Runs thelon elonarlybirdThriftRelonquelonstDelonselonrializelonrUtil tool with thelon givelonn command-linelon argumelonnts.
   */
  public static void main(String[] args) throws IOelonxcelonption {
    Path logFilelon = null;
    if (args.lelonngth == 1) {
      logFilelon = FilelonSystelonms.gelontDelonfault().gelontPath(args[0]);
    } elonlselon if (args.lelonngth == 0) {
      logFilelon = FilelonSystelonms.gelontDelonfault().gelontPath(DelonFAULT_LOG_FILelon_LOCATION);
    } elonlselon {
      Systelonm.elonrr.println("Usagelon: takelons zelonro or onelon paramelontelonr (log filelon path). "
          + "If no log filelon is speloncifielond, " + DelonFAULT_LOG_FILelon_LOCATION + " is uselond.");
      //CHelonCKSTYLelon:OFF RelongelonxpSinglelonlinelonJava
      Systelonm.elonxit(-1);
      //CHelonCKSTYLelon:ON RelongelonxpSinglelonlinelonJava
    }
    Prelonconditions.chelonckStatelon(logFilelon.toFilelon().elonxists());

    BuffelonrelondRelonadelonr relonadelonr = Filelons.nelonwBuffelonrelondRelonadelonr(logFilelon, Charselont.delonfaultCharselont());
    try {
      String linelon;
      whilelon ((linelon = relonadelonr.relonadLinelon()) != null) {
        elonarlybirdRelonquelonst elonbRelonquelonst = delonselonrializelonelonBRelonquelonst(linelon);
        if (elonbRelonquelonst != null) {
          Systelonm.out.println(elonbRelonquelonst);
        }
      }
    } finally {
      relonadelonr.closelon();
    }
  }

  privatelon static elonarlybirdRelonquelonst delonselonrializelonelonBRelonquelonst(String linelon) {
    elonarlybirdRelonquelonst elonbRelonquelonst = nelonw elonarlybirdRelonquelonst();
    bytelon[] bytelons = B64.deloncodelon(linelon);
    try {
      DelonSelonRIALIZelonR.delonselonrializelon(elonbRelonquelonst, bytelons);
    } catch (Telonxcelonption elon) {
      Systelonm.elonrr.println("elonrror delonselonrializing thrift.");
    }
    relonturn elonbRelonquelonst;
  }
}
