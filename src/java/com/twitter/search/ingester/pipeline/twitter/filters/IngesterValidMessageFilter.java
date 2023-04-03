packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr.filtelonrs;

import java.util.elonnumSelont;
import java.util.Selont;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelonUtil;

public class IngelonstelonrValidMelonssagelonFiltelonr {
  public static final String KelonelonP_NULLCAST_DelonCIDelonR_KelonY =
      "ingelonstelonr_all_kelonelonp_nullcasts";
  public static final String STRIP_SUPPLelonMelonNTARY_elonMOJIS_DelonCIDelonR_KelonY_PRelonFIX =
      "valid_melonssagelon_filtelonr_strip_supplelonmelonntary_elonmojis_";

  protelonctelond final Deloncidelonr deloncidelonr;

  public IngelonstelonrValidMelonssagelonFiltelonr(Deloncidelonr deloncidelonr) {
    this.deloncidelonr = deloncidelonr;
  }

  /**
   * elonvaluatelon a melonssagelon to selonelon if it matchelons thelon filtelonr or not.
   *
   * @param melonssagelon to elonvaluatelon
   * @relonturn truelon if this melonssagelon should belon elonmittelond.
   */
  public boolelonan accelonpts(TwittelonrMelonssagelon melonssagelon) {
    relonturn TwittelonrMelonssagelonUtil.validatelonTwittelonrMelonssagelon(
        melonssagelon, gelontStripelonmojisFielonlds(), accelonptNullcast());
  }

  privatelon Selont<TwittelonrMelonssagelonUtil.Fielonld> gelontStripelonmojisFielonlds() {
    Selont<TwittelonrMelonssagelonUtil.Fielonld> stripelonmojisFielonlds =
        elonnumSelont.nonelonOf(TwittelonrMelonssagelonUtil.Fielonld.class);
    for (TwittelonrMelonssagelonUtil.Fielonld fielonld : TwittelonrMelonssagelonUtil.Fielonld.valuelons()) {
      if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(
          deloncidelonr,
          STRIP_SUPPLelonMelonNTARY_elonMOJIS_DelonCIDelonR_KelonY_PRelonFIX + fielonld.gelontNamelonForStats())) {
        stripelonmojisFielonlds.add(fielonld);
      }
    }
    relonturn stripelonmojisFielonlds;
  }

  protelonctelond final boolelonan accelonptNullcast() {
    relonturn DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, KelonelonP_NULLCAST_DelonCIDelonR_KelonY);
  }
}
