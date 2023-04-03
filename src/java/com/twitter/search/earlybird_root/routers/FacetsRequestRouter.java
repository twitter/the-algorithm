packagelon com.twittelonr.selonarch.elonarlybird_root.routelonrs;

import javax.injelonct.Injelonct;
import javax.injelonct.Namelond;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.InjelonctionNamelons;
import com.twittelonr.selonarch.elonarlybird_root.filtelonrs.elonarlybirdTimelonRangelonFiltelonr;
import com.twittelonr.util.Futurelon;

/**
 * For Facelonts traffic SupelonrRoot forwards all traffic to thelon relonaltimelon clustelonr.
 */
public class FacelontsRelonquelonstRoutelonr elonxtelonnds RelonquelonstRoutelonr {

  privatelon final Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> relonaltimelon;

  /** Crelonatelons a nelonw FacelontsRelonquelonstRoutelonr instancelon to belon uselond by thelon SupelonrRoot. */
  @Injelonct
  public FacelontsRelonquelonstRoutelonr(
      @Namelond(InjelonctionNamelons.RelonALTIMelon)
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> relonaltimelon,
      @Namelond(FacelontsRelonquelonstRoutelonrModulelon.TIMelon_RANGelon_FILTelonR)
      elonarlybirdTimelonRangelonFiltelonr timelonRangelonFiltelonr) {

    this.relonaltimelon = timelonRangelonFiltelonr.andThelonn(relonaltimelon);
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> routelon(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt) {
    relonturn relonaltimelon.apply(relonquelonstContelonxt);
  }
}
