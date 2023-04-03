packagelon com.twittelonr.selonarch.elonarlybird_root.caching;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.caching.Cachelon;
import com.twittelonr.selonarch.common.caching.TopTwelonelontsCachelonUtil;
import com.twittelonr.selonarch.common.caching.filtelonr.SelonrvicelonPostProcelonssor;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;

import static com.googlelon.common.baselon.Prelonconditions.chelonckNotNull;

public class TopTwelonelontsSelonrvicelonPostProcelonssor
    elonxtelonnds SelonrvicelonPostProcelonssor<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TopTwelonelontsSelonrvicelonPostProcelonssor.class);

  public static final int CACHelon_AGelon_IN_MS = 600000;
  public static final int NO_RelonSULT_CACHelon_AGelon_IN_MS = 300000;

  privatelon final Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon;

  public TopTwelonelontsSelonrvicelonPostProcelonssor(Cachelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> cachelon) {
    this.cachelon = chelonckNotNull(cachelon);
  }

  @Ovelonrridelon
  public void procelonssSelonrvicelonRelonsponselon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                                     elonarlybirdRelonsponselon selonrvicelonRelonsponselon) {

    elonarlybirdRelonquelonst originalRelonquelonst = relonquelonstContelonxt.gelontRelonquelonst();
    LOG.delonbug("Writing to top twelonelonts cachelon. Relonquelonst: {}, Relonsponselon: {}",
        originalRelonquelonst, selonrvicelonRelonsponselon);
    TopTwelonelontsCachelonUtil.cachelonRelonsults(originalRelonquelonst,
        selonrvicelonRelonsponselon,
        cachelon,
        CACHelon_AGelon_IN_MS,
        NO_RelonSULT_CACHelon_AGelon_IN_MS);
  }
}
