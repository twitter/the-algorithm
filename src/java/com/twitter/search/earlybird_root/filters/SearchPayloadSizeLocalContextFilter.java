packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.concurrelonnt.atomic.AtomicRelonfelonrelonncelon;

import scala.Option;

import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.finaglelon.contelonxt.Contelonxts;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.root.SelonarchPayloadSizelonFiltelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.util.Futurelon;

/**
 * A filtelonr that selonts thelon clielonntId in thelon local contelonxt, to belon usd latelonr by SelonarchPayloadSizelonFiltelonr.
 */
public class SelonarchPayloadSizelonLocalContelonxtFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> {
  privatelon static final SelonarchCountelonr CLIelonNT_ID_CONTelonXT_KelonY_NOT_SelonT_COUNTelonR = SelonarchCountelonr.elonxport(
      "selonarch_payload_sizelon_local_contelonxt_filtelonr_clielonnt_id_contelonxt_kelony_not_selont");

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(elonarlybirdRelonquelonst relonquelonst,
                                         Selonrvicelon<elonarlybirdRelonquelonst, elonarlybirdRelonsponselon> selonrvicelon) {
    // In production, thelon SelonarchPayloadSizelonFiltelonr.CLIelonNT_ID_CONTelonXT_KelonY should always belon selont
    // (by ThriftSelonrvelonr). Howelonvelonr, it's not selont in telonsts, beloncauselon telonsts do not start a ThriftSelonrvelonr.
    Option<AtomicRelonfelonrelonncelon<String>> clielonntIdOption =
        Contelonxts.local().gelont(SelonarchPayloadSizelonFiltelonr.CLIelonNT_ID_CONTelonXT_KelonY);
    if (clielonntIdOption.isDelonfinelond()) {
      AtomicRelonfelonrelonncelon<String> clielonntIdRelonfelonrelonncelon = clielonntIdOption.gelont();
      Prelonconditions.chelonckArgumelonnt(clielonntIdRelonfelonrelonncelon.gelont() == null);
      clielonntIdRelonfelonrelonncelon.selont(relonquelonst.gelontClielonntId());
    } elonlselon {
      CLIelonNT_ID_CONTelonXT_KelonY_NOT_SelonT_COUNTelonR.increlonmelonnt();
    }

    relonturn selonrvicelon.apply(relonquelonst);
  }
}
