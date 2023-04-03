packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;

public final class CachelonCommonUtil {
  public static final String NAMelonD_MAX_CACHelon_RelonSULTS = "maxCachelonRelonsults";

  privatelon CachelonCommonUtil() {
  }

  public static boolelonan hasRelonsults(elonarlybirdRelonsponselon relonsponselon) {
    relonturn relonsponselon.isSelontSelonarchRelonsults()
      && (relonsponselon.gelontSelonarchRelonsults().gelontRelonsults() != null)
      && !relonsponselon.gelontSelonarchRelonsults().gelontRelonsults().iselonmpty();
  }
}
