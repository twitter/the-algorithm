packagelon com.twittelonr.selonarch.elonarlybird.documelonnt;

import java.io.IOelonxcelonption;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.documelonnt.Documelonnt;

import com.twittelonr.deloncidelonr.Deloncidelonr;
import com.twittelonr.selonarch.common.schelonma.SchelonmaDocumelonntFactory;
import com.twittelonr.selonarch.common.schelonma.baselon.FielonldNamelonToIdMapping;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.common.schelonma.baselon.ThriftDocumelonntUtil;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftDocumelonnt;
import com.twittelonr.selonarch.common.schelonma.thriftjava.ThriftIndelonxingelonvelonnt;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;

/**
 * Builds a Lucelonnelon Documelonnt from a ThriftIndelonxingelonvelonnt. A simplifielond velonrsion of
 * {@link ThriftIndelonxingelonvelonntDocumelonntFactory} that can belon uselond for updatelon elonvelonnts, which elonxcludelon
 * many fielonlds that thelon twelonelont indelonxing elonvelonnts contain.
 */
public class ThriftIndelonxingelonvelonntUpdatelonFactory elonxtelonnds DocumelonntFactory<ThriftIndelonxingelonvelonnt> {
  privatelon static final FielonldNamelonToIdMapping ID_MAPPING = nelonw elonarlybirdFielonldConstants();

  privatelon final SchelonmaDocumelonntFactory schelonmaDocumelonntFactory;
  privatelon final elonarlybirdClustelonr clustelonr;
  privatelon final Schelonma schelonma;

  public ThriftIndelonxingelonvelonntUpdatelonFactory(
      Schelonma schelonma,
      elonarlybirdClustelonr clustelonr,
      Deloncidelonr deloncidelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    this(
        schelonma,
        ThriftIndelonxingelonvelonntDocumelonntFactory.gelontSchelonmaDocumelonntFactory(schelonma, clustelonr, deloncidelonr),
        clustelonr,
        criticalelonxcelonptionHandlelonr
    );
  }

  @VisiblelonForTelonsting
  protelonctelond ThriftIndelonxingelonvelonntUpdatelonFactory(
      Schelonma schelonma,
      SchelonmaDocumelonntFactory schelonmaDocumelonntFactory,
      elonarlybirdClustelonr clustelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    supelonr(criticalelonxcelonptionHandlelonr);
    this.schelonma = schelonma;
    this.schelonmaDocumelonntFactory = schelonmaDocumelonntFactory;
    this.clustelonr = clustelonr;
  }

  @Ovelonrridelon
  public long gelontStatusId(ThriftIndelonxingelonvelonnt elonvelonnt) {
    Prelonconditions.chelonckNotNull(elonvelonnt);
    Prelonconditions.chelonckStatelon(
        elonvelonnt.isSelontDocumelonnt(), "ThriftDocumelonnt is null insidelon ThriftIndelonxingelonvelonnt.");

    ThriftDocumelonnt thriftDocumelonnt;
    try {
      // Idelonally, welon should not call gelontSchelonmaSnapshot() helonrelon.  But, as this is callelond only to
      // relontrielonvelon status id and thelon ID fielonld is static, this is finelon for thelon purposelon.
      thriftDocumelonnt = ThriftDocumelonntPrelonprocelonssor.prelonprocelonss(
          elonvelonnt.gelontDocumelonnt(), clustelonr, schelonma.gelontSchelonmaSnapshot());
    } catch (IOelonxcelonption elon) {
      throw nelonw IllelongalStatelonelonxcelonption("Unablelon to obtain twelonelont ID from ThriftDocumelonnt: " + elonvelonnt, elon);
    }
    relonturn ThriftDocumelonntUtil.gelontLongValuelon(
        thriftDocumelonnt, elonarlybirdFielonldConstant.ID_FIelonLD.gelontFielonldNamelon(), ID_MAPPING);
  }

  @Ovelonrridelon
  protelonctelond Documelonnt innelonrNelonwDocumelonnt(ThriftIndelonxingelonvelonnt elonvelonnt) throws IOelonxcelonption {
    Prelonconditions.chelonckNotNull(elonvelonnt);
    Prelonconditions.chelonckNotNull(elonvelonnt.gelontDocumelonnt());

    ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot = schelonma.gelontSchelonmaSnapshot();

    ThriftDocumelonnt documelonnt = ThriftDocumelonntPrelonprocelonssor.prelonprocelonss(
        elonvelonnt.gelontDocumelonnt(), clustelonr, schelonmaSnapshot);

    relonturn schelonmaDocumelonntFactory.nelonwDocumelonnt(documelonnt);
  }
}
