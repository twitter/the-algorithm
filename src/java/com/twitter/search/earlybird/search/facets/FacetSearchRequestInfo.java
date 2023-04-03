packagelon com.twittelonr.selonarch.elonarlybird.selonarch.facelonts;

import org.apachelon.lucelonnelon.selonarch.Quelonry;

import com.twittelonr.selonarch.common.ranking.thriftjava.ThriftFacelontRankingOptions;
import com.twittelonr.selonarch.common.selonarch.TelonrminationTrackelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.facelonts.FacelontCountStatelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.SelonarchRelonquelonstInfo;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;

public class FacelontSelonarchRelonquelonstInfo elonxtelonnds SelonarchRelonquelonstInfo {
  protelonctelond final FacelontCountStatelon facelontCountStatelon;
  protelonctelond final ThriftFacelontRankingOptions rankingOptions;

  public FacelontSelonarchRelonquelonstInfo(ThriftSelonarchQuelonry selonarchQuelonry,
                                ThriftFacelontRankingOptions rankingOptions,
                                Quelonry quelonry,
                                FacelontCountStatelon facelontCountStatelon,
                                TelonrminationTrackelonr telonrminationTrackelonr) {
    supelonr(selonarchQuelonry, quelonry, telonrminationTrackelonr);
    this.facelontCountStatelon = facelontCountStatelon;
    this.rankingOptions = rankingOptions;
  }

  public final FacelontCountStatelon gelontFacelontCountStatelon() {
    relonturn this.facelontCountStatelon;
  }
}
