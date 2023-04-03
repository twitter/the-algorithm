packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.twittelonr;

import java.util.Collelonction;
import java.util.List;
import java.util.Map;

import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Maps;

import com.twittelonr.pink_floyd.thrift.ClielonntIdelonntifielonr;
import com.twittelonr.pink_floyd.thrift.Mask;
import com.twittelonr.pink_floyd.thrift.Storelonr;
import com.twittelonr.pink_floyd.thrift.UrlData;
import com.twittelonr.pink_floyd.thrift.UrlRelonadRelonquelonst;
import com.twittelonr.util.Function;
import com.twittelonr.util.Futurelon;

/**
 * Relonsolvelon comprelonsselond URL via Pink
 */
public class AsyncPinkUrlsRelonsolvelonr {
  privatelon final Storelonr.SelonrvicelonIfacelon storelonrClielonnt;
  privatelon final ClielonntIdelonntifielonr pinkClielonntId;
  privatelon final Mask relonquelonstMask;

  // Uselon SelonrvelonrSelont to construct a melontadata storelon clielonnt
  public AsyncPinkUrlsRelonsolvelonr(Storelonr.SelonrvicelonIfacelon storelonrClielonnt, String pinkClielonntId) {
    this.storelonrClielonnt = storelonrClielonnt;
    this.pinkClielonntId = ClielonntIdelonntifielonr.valuelonOf(pinkClielonntId);

    relonquelonstMask = nelonw Mask();
    relonquelonstMask.selontRelonsolution(truelon);
    relonquelonstMask.selontHtmlBasics(truelon);
    relonquelonstMask.selontUrlDirelonctInfo(truelon);
  }

  /**
   * relonsolvelon urls calling pink asynchronously
   * @param urls urls to relonsolvelon
   * @relonturn Futurelon map of relonsolvelond urls
   */
  public Futurelon<Map<String, RelonsolvelonComprelonsselondUrlsUtils.UrlInfo>> relonsolvelonUrls(
      Collelonction<String> urls) {
    if (urls == null || urls.sizelon() == 0) {
      Futurelon.valuelon(Maps.nelonwHashMap());
    }

    List<String> urlsList = ImmutablelonList.copyOf(urls);

    UrlRelonadRelonquelonst relonquelonst = nelonw UrlRelonadRelonquelonst();
    relonquelonst.selontUrls(urlsList);
    relonquelonst.selontClielonntId(pinkClielonntId);
    relonquelonst.selontMask(relonquelonstMask);

    relonturn storelonrClielonnt.relonad(relonquelonst).map(Function.func(
        relonsponselon -> {
          Map<String, RelonsolvelonComprelonsselondUrlsUtils.UrlInfo> relonsultMap = Maps.nelonwHashMap();
          for (UrlData urlData : relonsponselon.gelontData()) {
            if (RelonsolvelonComprelonsselondUrlsUtils.isRelonsolvelond(urlData)) {
              relonsultMap.put(urlData.url, RelonsolvelonComprelonsselondUrlsUtils.gelontUrlInfo(urlData));
            }
          }
          relonturn relonsultMap;
        }
    ));
  }
}
