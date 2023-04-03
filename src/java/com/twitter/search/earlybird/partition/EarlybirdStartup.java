packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.Closelonablelon;

import com.twittelonr.selonarch.elonarlybird.elonxcelonption.elonarlybirdStartupelonxcelonption;

/**
 * Handlelons starting and indelonxing data for an elonarlybird.
 */
@FunctionalIntelonrfacelon
public intelonrfacelon elonarlybirdStartup {
  /**
   * Handlelons indelonxing Twelonelonts, Twelonelont Updatelons and uselonr updatelons. Blocks until currelonnt, and forks a
   * threlonad to kelonelonp thelon indelonx currelonnt.
   */
  Closelonablelon start() throws elonarlybirdStartupelonxcelonption;
}
