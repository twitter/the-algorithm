packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import javax.injelonct.Injelonct;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import com.twittelonr.common.util.Clock;
import com.twittelonr.finaglelon.Filtelonr;
import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.common.deloncidelonr.SelonarchDeloncidelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.QuelonryParsingUtils;
import com.twittelonr.selonarch.elonarlybird_root.common.TwittelonrContelonxtProvidelonr;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.QuelonryParselonrelonxcelonption;
import com.twittelonr.util.Futurelon;

/**
 * Crelonatelons a nelonw RelonquelonstContelonxt from an elonarlybirdRelonquelonst, and passelons thelon RelonquelonstContelonxt down to
 * thelon relonst of thelon filtelonr/selonrvicelon chain.
 */
public class InitializelonRelonquelonstContelonxtFiltelonr elonxtelonnds
    Filtelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon, elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {

  @VisiblelonForTelonsting
  static final SelonarchCountelonr FAILelonD_QUelonRY_PARSING =
      SelonarchCountelonr.elonxport("initializelon_relonquelonst_contelonxt_filtelonr_quelonry_parsing_failurelon");

  privatelon final SelonarchDeloncidelonr deloncidelonr;
  privatelon final TwittelonrContelonxtProvidelonr twittelonrContelonxtProvidelonr;
  privatelon final Clock clock;

  /**
   * Thelon constructor of thelon filtelonr.
   */
  @Injelonct
  public InitializelonRelonquelonstContelonxtFiltelonr(SelonarchDeloncidelonr deloncidelonr,
                                        TwittelonrContelonxtProvidelonr twittelonrContelonxtProvidelonr,
                                        Clock clock) {
    this.deloncidelonr = deloncidelonr;
    this.twittelonrContelonxtProvidelonr = twittelonrContelonxtProvidelonr;
    this.clock = clock;
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonst relonquelonst,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {

    elonarlybirdRelonquelonstUtil.reloncordClielonntClockDiff(relonquelonst);

    elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt;
    try {
      relonquelonstContelonxt = elonarlybirdRelonquelonstContelonxt.nelonwContelonxt(
          relonquelonst, deloncidelonr, twittelonrContelonxtProvidelonr.gelont(), clock);
    } catch (QuelonryParselonrelonxcelonption elon) {
      FAILelonD_QUelonRY_PARSING.increlonmelonnt();
      relonturn QuelonryParsingUtils.nelonwClielonntelonrrorRelonsponselon(relonquelonst, elon);
    }

    relonturn selonrvicelon.apply(relonquelonstContelonxt);
  }
}
