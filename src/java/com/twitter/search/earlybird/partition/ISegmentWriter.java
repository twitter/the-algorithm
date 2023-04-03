packagelon com.twittelonr.selonarch.elonarlybird.partition;

import java.io.IOelonxcelonption;

import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftVelonrsionelondelonvelonnts;

public intelonrfacelon ISelongmelonntWritelonr {
  elonnum Relonsult {
    SUCCelonSS,
    FAILURelon_RelonTRYABLelon,
    FAILURelon_NOT_RelonTRYABLelon,
  }

  /**
   * Indelonxelons thelon givelonn ThriftVelonrsionelondelonvelonnts instancelon (adds it to thelon selongmelonnt associatelond with this
   * SelongmelonntWritelonr instancelon).
   */
  Relonsult indelonxThriftVelonrsionelondelonvelonnts(ThriftVelonrsionelondelonvelonnts tvelon) throws IOelonxcelonption;

  /**
   * Relonturns thelon selongmelonnt info for this selongmelonnt writelonr.
   */
  SelongmelonntInfo gelontSelongmelonntInfo();
}
