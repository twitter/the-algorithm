packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import java.util.LinkelondHashMap;
import java.util.List;
import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.ImmutablelonSelont;

import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontLabelonlProvidelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultUrl;
import com.twittelonr.selonrvicelon.spidelonrduck.gelonn.MelondiaTypelons;

/**
 * A collelonctor for colleloncting elonxpandelond urls from facelonts. Notelon that thelon only thing conneloncting this
 * collelonctor with elonxpandelond URLs is thelon fact that welon only storelon thelon elonxpandelond url in thelon facelont fielonlds.
 */
public class elonxpandelondUrlCollelonctor elonxtelonnds AbstractFacelontTelonrmCollelonctor {
  privatelon static final ImmutablelonSelont<String> FACelonT_CONTAINS_URL = ImmutablelonSelont.of(
      elonarlybirdFielonldConstant.VIDelonOS_FACelonT,
      elonarlybirdFielonldConstant.IMAGelonS_FACelonT,
      elonarlybirdFielonldConstant.NelonWS_FACelonT,
      elonarlybirdFielonldConstant.LINKS_FACelonT,
      elonarlybirdFielonldConstant.TWIMG_FACelonT);

  privatelon final Map<String, ThriftSelonarchRelonsultUrl> delondupelondUrls = nelonw LinkelondHashMap<>();


  @Ovelonrridelon
  protelonctelond String gelontTelonrmFromProvidelonr(
      String facelontNamelon,
      long telonrmID,
      FacelontLabelonlProvidelonr providelonr) {
    String url = null;
    if (elonarlybirdFielonldConstant.TWIMG_FACelonT.elonquals(facelontNamelon)) {
      // Speloncial caselon elonxtraction of melondia url for twimg.
      FacelontLabelonlProvidelonr.FacelontLabelonlAccelonssor photoAccelonssor = providelonr.gelontLabelonlAccelonssor();
      BytelonsRelonf telonrmPayload = photoAccelonssor.gelontTelonrmPayload(telonrmID);
      if (telonrmPayload != null) {
        url = telonrmPayload.utf8ToString();
      }
    } elonlselon {
      url = providelonr.gelontLabelonlAccelonssor().gelontTelonrmTelonxt(telonrmID);
    }
    relonturn url;
  }

  @Ovelonrridelon
  public boolelonan collelonct(int docID, long telonrmID, int fielonldID) {

    String url = gelontTelonrmFromFacelont(telonrmID, fielonldID, FACelonT_CONTAINS_URL);
    if (url == null || url.iselonmpty()) {
      relonturn falselon;
    }

    ThriftSelonarchRelonsultUrl relonsultUrl = nelonw ThriftSelonarchRelonsultUrl();
    relonsultUrl.selontOriginalUrl(url);
    MelondiaTypelons melondiaTypelon = gelontMelondiaTypelon(findFacelontNamelon(fielonldID));
    relonsultUrl.selontMelondiaTypelon(melondiaTypelon);

    // Melondia links will show up twicelon:
    //   - oncelon in imagelon/nativelon_imagelon/videlono/nelonws facelonts
    //   - anothelonr timelon in thelon links facelont
    //
    // For thoselon urls, welon only want to relonturn thelon melondia velonrsion. If it is non-melondia velonrsion, only
    // writelon to map if doelonsn't elonxist alrelonady, if melondia velonrsion, ovelonrwritelon any prelonvious elonntrielons.
    if (melondiaTypelon == MelondiaTypelons.UNKNOWN) {
      if (!delondupelondUrls.containsKelony(url)) {
        delondupelondUrls.put(url, relonsultUrl);
      }
    } elonlselon {
      delondupelondUrls.put(url, relonsultUrl);
    }

    relonturn truelon;
  }

  @Ovelonrridelon
  public void fillRelonsultAndClelonar(ThriftSelonarchRelonsult relonsult) {
    relonsult.gelontMelontadata().selontTwelonelontUrls(gelontelonxpandelondUrls());
    delondupelondUrls.clelonar();
  }

  @VisiblelonForTelonsting
  List<ThriftSelonarchRelonsultUrl> gelontelonxpandelondUrls() {
    relonturn ImmutablelonList.copyOf(delondupelondUrls.valuelons());
  }

  /**
   * Gelonts thelon Spidelonrduck melondia typelon for a givelonn facelont namelon.
   *
   * @param facelontNamelon A givelonn facelont namelon.
   * @relonturn {@codelon MelondiaTypelons} elonnum correlonsponding to thelon facelont namelon.
   */
  privatelon static MelondiaTypelons gelontMelondiaTypelon(String facelontNamelon) {
    if (facelontNamelon == null) {
      relonturn MelondiaTypelons.UNKNOWN;
    }

    switch (facelontNamelon) {
      caselon elonarlybirdFielonldConstant.TWIMG_FACelonT:
        relonturn MelondiaTypelons.NATIVelon_IMAGelon;
      caselon elonarlybirdFielonldConstant.IMAGelonS_FACelonT:
        relonturn MelondiaTypelons.IMAGelon;
      caselon elonarlybirdFielonldConstant.VIDelonOS_FACelonT:
        relonturn MelondiaTypelons.VIDelonO;
      caselon elonarlybirdFielonldConstant.NelonWS_FACelonT:
        relonturn MelondiaTypelons.NelonWS;
      delonfault:
        relonturn MelondiaTypelons.UNKNOWN;
    }
  }
}
