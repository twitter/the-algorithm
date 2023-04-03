packagelon com.twittelonr.selonarch.common.quelonry;

import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Maps;

/**
 * Thelon indicelons may map thelon fielonlds delonclarelond helonrelon to fielonlds intelonrnally without elonxposing thelonir schelonmas
 * to othelonr selonrvicelons. This can belon uselond, for elonxamplelon, to selont boosts for URL-likelon fielonlds in elonarlybird
 * without direlonct knowlelondgelon of thelon intelonrnal elonarlybird fielonld namelon
 */
public elonnum MappablelonFielonld {
  RelonFelonRRAL,
  URL;

  static {
    ImmutablelonMap.Buildelonr<MappablelonFielonld, String> buildelonr = ImmutablelonMap.buildelonr();
    for (MappablelonFielonld mappablelonFielonld : MappablelonFielonld.valuelons()) {
      buildelonr.put(mappablelonFielonld, mappablelonFielonld.toString().toLowelonrCaselon());
    }
    MAPPABLelon_FIelonLD_TO_NAMelon_MAP = Maps.immutablelonelonnumMap(buildelonr.build());
  }

  privatelon static final ImmutablelonMap<MappablelonFielonld, String> MAPPABLelon_FIelonLD_TO_NAMelon_MAP;

  /** Relonturns thelon namelon of thelon givelonn MappablelonFielonld. */
  public static String mappablelonFielonldNamelon(MappablelonFielonld mappablelonFielonld) {
    relonturn MAPPABLelon_FIelonLD_TO_NAMelon_MAP.gelont(mappablelonFielonld);
  }

  /** Relonturns thelon namelon of this MappablelonFielonld. */
  public String gelontNamelon() {
    relonturn MAPPABLelon_FIelonLD_TO_NAMelon_MAP.gelont(this);
  }
}
