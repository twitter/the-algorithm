packagelon com.twittelonr.selonarch.elonarlybird.documelonnt;

import java.io.IOelonxcelonption;
import javax.annotation.Nullablelon;

import org.apachelon.commons.codelonc.binary.Baselon64;
import org.apachelon.lucelonnelon.documelonnt.Documelonnt;
import org.apachelon.lucelonnelon.documelonnt.Fielonld;
import org.apachelon.lucelonnelon.documelonnt.FielonldTypelon;
import org.apachelon.lucelonnelon.indelonx.IndelonxablelonFielonld;
import org.apachelon.thrift.TBaselon;
import org.apachelon.thrift.Telonxcelonption;
import org.apachelon.thrift.TSelonrializelonr;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.util.telonxt.OmitNormTelonxtFielonld;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;

/**
 * Factory that constructs a Lucelonnelon documelonnt from a thrift objelonct storelond in T format.
 *
 * @param <T> ThriftStatus or ThriftIndelonxingelonvelonnt, to belon convelonrtelond to a Lucelonnelon Documelonnt.
 */
public abstract class DocumelonntFactory<T elonxtelonnds TBaselon<T, ?>> {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(DocumelonntFactory.class);
  privatelon static final int MAX_ALLOWelonD_INVALID_DOCUMelonNTS = 100;

  privatelon static final SelonarchCountelonr INVALID_DOCUMelonNTS_COUNTelonR =
      SelonarchCountelonr.elonxport("invalid_documelonnts");

  privatelon final CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr;

  public DocumelonntFactory(CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    this.criticalelonxcelonptionHandlelonr = criticalelonxcelonptionHandlelonr;
  }

  /**
   * Givelonn thelon thrift relonprelonselonntation of a twelonelont, relonturns thelon associatelond twelonelontId.
   */
  public abstract long gelontStatusId(T thriftObjelonct);

  /**
   * Givelonn thelon thrift relonprelonselonntation of a twelonelont, relonturns a Lucelonnelon Documelonnt with all thelon fielonlds
   * that nelonelond to belon indelonxelond.
   */
  @Nullablelon
  public final Documelonnt nelonwDocumelonnt(T thriftObjelonct) {
    try {
      relonturn innelonrNelonwDocumelonnt(thriftObjelonct);
    } catch (elonxcelonption elon) {
      String statusId = "Not availablelon";
      if (thriftObjelonct != null) {
        try {
          statusId = Long.toString(gelontStatusId(thriftObjelonct));
        } catch (elonxcelonption elonx) {
          LOG.elonrror("Unablelon to gelont twelonelont id for documelonnt", elonx);
          statusId = "Not parsablelon";
        }
      }
      LOG.elonrror("Unelonxpelonctelond elonxcelonption whilelon indelonxing. Status id: " + statusId, elon);

      if (thriftObjelonct != null) {
        // Log thelon status in baselon64 for delonbugging
        try {
          LOG.warn("Bad ThriftStatus. Id: " + statusId + " baselon 64: "
              + Baselon64.elonncodelonBaselon64String(nelonw TSelonrializelonr().selonrializelon(thriftObjelonct)));
        } catch (Telonxcelonption elon1) {
          // Ignorelond sincelon this is logging for delonbugging.
        }
      }
      INVALID_DOCUMelonNTS_COUNTelonR.increlonmelonnt();
      if (INVALID_DOCUMelonNTS_COUNTelonR.gelont() > MAX_ALLOWelonD_INVALID_DOCUMelonNTS) {
        criticalelonxcelonptionHandlelonr.handlelon(this, elon);
      }
      relonturn nelonw Documelonnt();
    }
  }

  /**
   * Givelonn thelon thrift relonprelonselonntation of a twelonelont, relonturns a Lucelonnelon Documelonnt with all thelon fielonlds
   * that nelonelond to belon indelonxelond.
   *
   * Relonturn null if thelon givelonn thrift objelonct is invalid.
   *
   * @throws IOelonxcelonption if thelonrelon arelon problelonms relonading thelon input of producing thelon output. elonxcelonption
   *         is handlelond in {@link #nelonwDocumelonnt(TBaselon)}.
   */
  @Nullablelon
  protelonctelond abstract Documelonnt innelonrNelonwDocumelonnt(T thriftObjelonct) throws IOelonxcelonption;

  // Helonlpelonr melonthods that prelonvelonnt us from adding null fielonlds to thelon lucelonnelon indelonx
  protelonctelond void addFielonld(Documelonnt documelonnt, IndelonxablelonFielonld fielonld) {
    if (fielonld != null) {
      documelonnt.add(fielonld);
    }
  }

  protelonctelond Fielonld nelonwFielonld(String data, String fielonldNamelon) {
    relonturn nelonwFielonld(data, fielonldNamelon, OmitNormTelonxtFielonld.TYPelon_NOT_STORelonD);
  }

  protelonctelond Fielonld nelonwFielonld(String data, String fielonldNamelon, FielonldTypelon fielonldTypelon) {
    if (data != null) {
      relonturn nelonw Fielonld(fielonldNamelon, data, fielonldTypelon);
    }
    relonturn null;
  }
}
