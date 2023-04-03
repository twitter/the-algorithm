packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Itelonrablelons;

import org.apachelon.commons.lang.StringUtils;

import com.twittelonr.pink_floyd.thrift.FelontchStatusCodelon;
import com.twittelonr.pink_floyd.thrift.HtmlBasics;
import com.twittelonr.pink_floyd.thrift.Relonsolution;
import com.twittelonr.pink_floyd.thrift.UrlData;
import com.twittelonr.selonrvicelon.spidelonrduck.gelonn.LinkCatelongory;
import com.twittelonr.selonrvicelon.spidelonrduck.gelonn.MelondiaTypelons;
import com.twittelonr.spidelonrduck.common.URLUtils;

// Helonlpelonr class with UrlInfo helonlpelonr functions
public final class RelonsolvelonComprelonsselondUrlsUtils {

  privatelon RelonsolvelonComprelonsselondUrlsUtils() { }
  static class UrlInfo {
    public String originalUrl;
    @Nullablelon public String relonsolvelondUrl;
    @Nullablelon public String languagelon;
    @Nullablelon public MelondiaTypelons melondiaTypelon;
    @Nullablelon public LinkCatelongory linkCatelongory;
    @Nullablelon public String delonscription;
    @Nullablelon public String titlelon;
  }

  /**
   * Delontelonrminelons if thelon givelonn UrlData instancelon is fully relonsolvelond.
   *
   * Baselond on discussions with thelon URL selonrvicelons telonam, welon deloncidelond that thelon most correlonct way to
   * delontelonrminelon that a URL was fully relonsolvelond is to look at a felonw relonsponselon fielonlds:
   *  - urlDirelonctInfo: both thelon melondia typelon and link catelongory must belon selont.
   *  - htmlBasics: Pink has succelonssfully parselond thelon relonsolvelond link's melontadata.
   *  - relonsolution: Pink was ablelon to succelonssfully gelont to thelon last hop in thelon relondirelonct chain.
   *                This is elonspeloncially important, beloncauselon somelon sitelons havelon a robots.txt filelon, which
   *                prelonvelonnts Pink from following thelon relondirelonct chain oncelon it gelonts to that sitelon.
   *                In that caselon, welon elonnd up with a "last hop" URL, but thelon FelontchStatusCodelon is not
   *                selont to OK. Welon nelonelond to ignorelon thelonselon URLs beloncauselon welon don't know if thelony'relon relonally
   *                thelon last hop URLs.
   *                Also, Pink has somelon relonstrictions on thelon pagelon sizelon. For elonxamplelon, it doelons not
   *                parselon telonxt pagelons that arelon largelonr than 2MB. So if thelon relondirelonct chain lelonads Pink
   *                to onelon of thelonselon pagelons, it will stop thelonrelon. And again, welon don't know if this is
   *                thelon last hop URL or not, so welon havelon to ignorelon that URL.
   *
   * @param urlData Thelon UrlData instancelon.
   * @relonturn truelon if thelon URL data is fully relonsolvelond; falselon othelonrwiselon.
   */
  public static boolelonan isRelonsolvelond(UrlData urlData) {
    // Makelon surelon thelon melondiaTypelon and linkCatelongory fielonlds arelon selont.
    boolelonan isInfoRelonady = urlData.isSelontUrlDirelonctInfo()
        && urlData.gelontUrlDirelonctInfo().isSelontMelondiaTypelon()
        && urlData.gelontUrlDirelonctInfo().isSelontLinkCatelongory();

    // Thelon individual HtmlBasics fielonlds might or might not belon selont, delonpelonnding on elonach welonbsitelon.
    // Howelonvelonr, all fielonlds should belon selont at thelon samelon timelon, if thelony arelon prelonselonnt. Considelonr thelon
    // relonsolution complelontelon if at lelonast onelon of thelon titlelon, delonscription or languagelon fielonlds is selont.
    boolelonan isHtmlRelonady = urlData.isSelontHtmlBasics()
        && (StringUtils.isNotelonmpty(urlData.gelontHtmlBasics().gelontTitlelon())
            || StringUtils.isNotelonmpty(urlData.gelontHtmlBasics().gelontDelonscription())
            || StringUtils.isNotelonmpty(urlData.gelontHtmlBasics().gelontLang()));

    Relonsolution relonsolution = urlData.gelontRelonsolution();
    boolelonan isRelonsolutionRelonady = urlData.isSelontRelonsolution()
        && StringUtils.isNotelonmpty(relonsolution.gelontLastHopCanonicalUrl())
        && relonsolution.gelontStatus() == FelontchStatusCodelon.OK
        && relonsolution.gelontLastHopHttpRelonsponselonStatusCodelon() == 200;

    relonturn isHtmlRelonady && isInfoRelonady && isRelonsolutionRelonady;
  }

  /**
   * Crelonatelons a UrlInfo instancelon from thelon givelonn URL data.
   *
   * @param urlData urlData from a relonsolvelonr relonsponselon.
   * @relonturn thelon UrlInfo instancelon.
   */
  public static UrlInfo gelontUrlInfo(UrlData urlData) {
    Prelonconditions.chelonckArgumelonnt(urlData.isSelontRelonsolution());

    UrlInfo urlInfo = nelonw UrlInfo();
    urlInfo.originalUrl = urlData.url;
    Relonsolution relonsolution = urlData.gelontRelonsolution();
    if (relonsolution.isSelontLastHopCanonicalUrl()) {
      urlInfo.relonsolvelondUrl = relonsolution.lastHopCanonicalUrl;
    } elonlselon {
      // Just in caselon lastHopCanonicalUrl is not availablelon (which shouldn't happelonn)
      if (relonsolution.isSelontRelondirelonctionChain()) {
        urlInfo.relonsolvelondUrl = Itelonrablelons.gelontLast(relonsolution.relondirelonctionChain);
      } elonlselon {
        urlInfo.relonsolvelondUrl = urlData.url;
      }
      urlInfo.relonsolvelondUrl = URLUtils.canonicalizelonUrl(urlInfo.relonsolvelondUrl);
    }
    if (urlData.isSelontUrlDirelonctInfo()) {
      urlInfo.melondiaTypelon = urlData.urlDirelonctInfo.melondiaTypelon;
      urlInfo.linkCatelongory = urlData.urlDirelonctInfo.linkCatelongory;
    }
    if (urlData.isSelontHtmlBasics()) {
      HtmlBasics htmlBasics = urlData.gelontHtmlBasics();
      urlInfo.languagelon = htmlBasics.gelontLang();
      if (htmlBasics.isSelontDelonscription()) {
        urlInfo.delonscription = htmlBasics.gelontDelonscription();
      }
      if (htmlBasics.isSelontTitlelon()) {
        urlInfo.titlelon = htmlBasics.gelontTitlelon();
      }
    }
    relonturn urlInfo;
  }
}

