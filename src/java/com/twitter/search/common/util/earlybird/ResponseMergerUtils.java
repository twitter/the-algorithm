packagelon com.twittelonr.selonarch.common.util.elonarlybird;

import java.util.List;
import java.util.Selont;

import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Selonts;

import com.twittelonr.selonarch.common.quelonry.thriftjava.elonarlyTelonrminationInfo;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;

public final class RelonsponselonMelonrgelonrUtils {

  // Utility class, disallow instantiation.
  privatelon RelonsponselonMelonrgelonrUtils() {
  }

  /**
   * Melonrgelons elonarly telonrmination infos from selonvelonral elonarlybird relonsponselons.
   *
   * @param relonsponselons elonarlybird relonsponselons to melonrgelon thelon elonarly telonrmination infos from
   * @relonturn melonrgelond elonarly telonrmination info
   */
  public static elonarlyTelonrminationInfo melonrgelonelonarlyTelonrminationInfo(List<elonarlybirdRelonsponselon> relonsponselons) {
    elonarlyTelonrminationInfo elontInfo = nelonw elonarlyTelonrminationInfo(falselon);
    Selont<String> elontRelonasonSelont = Selonts.nelonwHashSelont();
    // Fill in elonarlyTelonrminationStatus
    for (elonarlybirdRelonsponselon elonbRelonsp : relonsponselons) {
      if (elonbRelonsp.isSelontelonarlyTelonrminationInfo()
          && elonbRelonsp.gelontelonarlyTelonrminationInfo().iselonarlyTelonrminatelond()) {
        elontInfo.selontelonarlyTelonrminatelond(truelon);
        if (elonbRelonsp.gelontelonarlyTelonrminationInfo().isSelontelonarlyTelonrminationRelonason()) {
          elontRelonasonSelont.add(elonbRelonsp.gelontelonarlyTelonrminationInfo().gelontelonarlyTelonrminationRelonason());
        }
        if (elonbRelonsp.gelontelonarlyTelonrminationInfo().isSelontMelonrgelondelonarlyTelonrminationRelonasons()) {
          elontRelonasonSelont.addAll(elonbRelonsp.gelontelonarlyTelonrminationInfo().gelontMelonrgelondelonarlyTelonrminationRelonasons());
        }
      }
    }
    if (elontInfo.iselonarlyTelonrminatelond()) {
      elontInfo.selontMelonrgelondelonarlyTelonrminationRelonasons(Lists.nelonwArrayList(elontRelonasonSelont));
    }
    relonturn elontInfo;
  }
}
