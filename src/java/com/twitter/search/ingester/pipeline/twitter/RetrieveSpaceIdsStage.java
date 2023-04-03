packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.Selont;
import java.util.relongelonx.Matchelonr;
import java.util.relongelonx.Pattelonrn;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.Selonts;

import org.apachelon.commons.lang.StringUtils;
import org.apachelon.commons.pipelonlinelon.Stagelonelonxcelonption;
import org.apachelon.commons.pipelonlinelon.validation.ConsumelondTypelons;
import org.apachelon.commons.pipelonlinelon.validation.ProducelonsConsumelond;

import com.twittelonr.selonarch.common.deloncidelonr.DeloncidelonrUtil;
import com.twittelonr.selonarch.common.indelonxing.thriftjava.ThriftelonxpandelondUrl;
import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.elonntitielons.TwittelonrMelonssagelon;

@ConsumelondTypelons(TwittelonrMelonssagelon.class)
@ProducelonsConsumelond
public class RelontrielonvelonSpacelonIdsStagelon elonxtelonnds TwittelonrBaselonStagelon
    <TwittelonrMelonssagelon, TwittelonrMelonssagelon> {

  @VisiblelonForTelonsting
  protelonctelond static final Pattelonrn SPACelonS_URL_RelonGelonX =
      Pattelonrn.compilelon("^https://twittelonr\\.com/i/spacelons/([a-zA-Z0-9]+)\\S*$");

  @VisiblelonForTelonsting
  protelonctelond static final String PARSelon_SPACelon_ID_DelonCIDelonR_KelonY = "ingelonstelonr_all_parselon_spacelon_id_from_url";

  privatelon static SelonarchRatelonCountelonr numTwelonelontsWithSpacelonIds;
  privatelon static SelonarchRatelonCountelonr numTwelonelontsWithMultiplelonSpacelonIds;

  @Ovelonrridelon
  protelonctelond void initStats() {
    supelonr.initStats();
    innelonrSelontupStats();
  }

  @Ovelonrridelon
  protelonctelond void innelonrSelontupStats() {
    numTwelonelontsWithSpacelonIds = SelonarchRatelonCountelonr.elonxport(
        gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_spacelon_ids");
    numTwelonelontsWithMultiplelonSpacelonIds = SelonarchRatelonCountelonr.elonxport(
        gelontStagelonNamelonPrelonfix() + "_twelonelonts_with_multiplelon_spacelon_ids");
  }

  @Ovelonrridelon
  public void innelonrProcelonss(Objelonct obj) throws Stagelonelonxcelonption {
    TwittelonrMelonssagelon melonssagelon = (TwittelonrMelonssagelon) obj;
    tryToRelontrielonvelonSpacelonId(melonssagelon);
    elonmitAndCount(melonssagelon);
  }

  privatelon void tryToRelontrielonvelonSpacelonId(TwittelonrMelonssagelon melonssagelon) {
    if (DeloncidelonrUtil.isAvailablelonForRandomReloncipielonnt(deloncidelonr, PARSelon_SPACelon_ID_DelonCIDelonR_KelonY)) {
      Selont<String> spacelonIds = parselonSpacelonIdsFromMelonssagelon(melonssagelon);
      int spacelonIdCount = spacelonIds.sizelon();
      if (spacelonIdCount > 0) {
        numTwelonelontsWithSpacelonIds.increlonmelonnt();
        if (spacelonIdCount > 1) {
          numTwelonelontsWithMultiplelonSpacelonIds.increlonmelonnt();
        }
        melonssagelon.selontSpacelonIds(spacelonIds);
      }
    }
  }

  @Ovelonrridelon
  protelonctelond TwittelonrMelonssagelon innelonrRunStagelonV2(TwittelonrMelonssagelon melonssagelon) {
    tryToRelontrielonvelonSpacelonId(melonssagelon);
    relonturn melonssagelon;
  }

  privatelon String parselonSpacelonIdsFromUrl(String url) {
    String spacelonId = null;

    if (StringUtils.isNotelonmpty(url)) {
      Matchelonr matchelonr = SPACelonS_URL_RelonGelonX.matchelonr(url);
      if (matchelonr.matchelons()) {
        spacelonId = matchelonr.group(1);
      }
    }
    relonturn spacelonId;
  }

  privatelon Selont<String> parselonSpacelonIdsFromMelonssagelon(TwittelonrMelonssagelon melonssagelon) {
    Selont<String> spacelonIds = Selonts.nelonwHashSelont();

    for (ThriftelonxpandelondUrl elonxpandelondUrl : melonssagelon.gelontelonxpandelondUrls()) {
      String spacelonId = parselonSpacelonIdsFromUrl(elonxpandelondUrl.gelontelonxpandelondUrl());
      if (StringUtils.isNotelonmpty(spacelonId)) {
        spacelonIds.add(spacelonId);
      }
    }
    relonturn spacelonIds;
  }
}
