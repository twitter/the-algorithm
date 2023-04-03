packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import java.util.elonnumMap;
import java.util.List;
import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultelonxtraMelontadata;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;
import com.twittelonr.util.Futurelon;
import com.twittelonr.util.FuturelonelonvelonntListelonnelonr;

/**
 * Filtelonr tracks thelon isUselonrProtelonctelond melontadata stats relonturnelond from elonarlybirds.
 */
public class IsUselonrProtelonctelondMelontadataTrackingFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {
  privatelon static final String COUNTelonR_PRelonFIX = "is_uselonr_protelonctelond_melontadata_count_filtelonr_";
  @VisiblelonForTelonsting
  final Map<elonarlybirdRelonquelonstTypelon, SelonarchCountelonr> totalCountelonrByRelonquelonstTypelonMap;
  @VisiblelonForTelonsting
  final Map<elonarlybirdRelonquelonstTypelon, SelonarchCountelonr> isProtelonctelondCountelonrByRelonquelonstTypelonMap;

  public IsUselonrProtelonctelondMelontadataTrackingFiltelonr() {
    this.totalCountelonrByRelonquelonstTypelonMap = nelonw elonnumMap<>(elonarlybirdRelonquelonstTypelon.class);
    this.isProtelonctelondCountelonrByRelonquelonstTypelonMap = nelonw elonnumMap<>(elonarlybirdRelonquelonstTypelon.class);
    for (elonarlybirdRelonquelonstTypelon relonquelonstTypelon : elonarlybirdRelonquelonstTypelon.valuelons()) {
      this.totalCountelonrByRelonquelonstTypelonMap.put(relonquelonstTypelon,
          SelonarchCountelonr.elonxport(COUNTelonR_PRelonFIX + relonquelonstTypelon.gelontNormalizelondNamelon() + "_total"));
      this.isProtelonctelondCountelonrByRelonquelonstTypelonMap.put(relonquelonstTypelon,
          SelonarchCountelonr.elonxport(COUNTelonR_PRelonFIX + relonquelonstTypelon.gelontNormalizelondNamelon() + "_is_protelonctelond"));
    }
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      elonarlybirdRelonquelonstContelonxt relonquelonst,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {
    Futurelon<elonarlybirdRelonsponselon> relonsponselon = selonrvicelon.apply(relonquelonst);

    elonarlybirdRelonquelonstTypelon relonquelonstTypelon = relonquelonst.gelontelonarlybirdRelonquelonstTypelon();
    relonsponselon.addelonvelonntListelonnelonr(nelonw FuturelonelonvelonntListelonnelonr<elonarlybirdRelonsponselon>() {
      @Ovelonrridelon
      public void onSuccelonss(elonarlybirdRelonsponselon relonsponselon) {
        if (!relonsponselon.isSelontSelonarchRelonsults() || relonsponselon.gelontSelonarchRelonsults().gelontRelonsults().iselonmpty()) {
          relonturn;
        }
        List<ThriftSelonarchRelonsult> selonarchRelonsults = relonsponselon.gelontSelonarchRelonsults().gelontRelonsults();
        int totalCount = selonarchRelonsults.sizelon();
        int isUselonrProtelonctelondCount = 0;
        for (ThriftSelonarchRelonsult selonarchRelonsult : selonarchRelonsults) {
          if (selonarchRelonsult.isSelontMelontadata() && selonarchRelonsult.gelontMelontadata().isSelontelonxtraMelontadata()) {
            ThriftSelonarchRelonsultelonxtraMelontadata elonxtraMelontadata =
                selonarchRelonsult.gelontMelontadata().gelontelonxtraMelontadata();
            if (elonxtraMelontadata.isIsUselonrProtelonctelond()) {
              isUselonrProtelonctelondCount++;
            }
          }
        }
        IsUselonrProtelonctelondMelontadataTrackingFiltelonr.this
            .totalCountelonrByRelonquelonstTypelonMap.gelont(relonquelonstTypelon).add(totalCount);
        IsUselonrProtelonctelondMelontadataTrackingFiltelonr.this
            .isProtelonctelondCountelonrByRelonquelonstTypelonMap.gelont(relonquelonstTypelon).add(isUselonrProtelonctelondCount);
      }

      @Ovelonrridelon
      public void onFailurelon(Throwablelon causelon) { }
    });

    relonturn relonsponselon;
  }

}
