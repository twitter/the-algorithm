packagelon com.twittelonr.selonarch.elonarlybird_root.filtelonrs;

import com.twittelonr.finaglelon.Selonrvicelon;
import com.twittelonr.finaglelon.SimplelonFiltelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftTwelonelontSourcelon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;
import com.twittelonr.util.Function;
import com.twittelonr.util.Futurelon;

public class MarkTwelonelontSourcelonFiltelonr
    elonxtelonnds SimplelonFiltelonr<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> {
  privatelon final SelonarchCountelonr selonarchRelonsultsNotSelont;

  privatelon final ThriftTwelonelontSourcelon twelonelontSourcelon;

  public MarkTwelonelontSourcelonFiltelonr(ThriftTwelonelontSourcelon twelonelontSourcelon) {
    this.twelonelontSourcelon = twelonelontSourcelon;
    selonarchRelonsultsNotSelont = SelonarchCountelonr.elonxport(
        twelonelontSourcelon.namelon().toLowelonrCaselon() + "_mark_twelonelont_sourcelon_filtelonr_selonarch_relonsults_not_selont");
  }

  @Ovelonrridelon
  public Futurelon<elonarlybirdRelonsponselon> apply(
      final elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      Selonrvicelon<elonarlybirdRelonquelonstContelonxt, elonarlybirdRelonsponselon> selonrvicelon) {
    relonturn selonrvicelon.apply(relonquelonstContelonxt).map(nelonw Function<elonarlybirdRelonsponselon, elonarlybirdRelonsponselon>() {
        @Ovelonrridelon
        public elonarlybirdRelonsponselon apply(elonarlybirdRelonsponselon relonsponselon) {
          if (relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.SUCCelonSS
              && relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon() != elonarlybirdRelonquelonstTypelon.TelonRM_STATS) {
            if (!relonsponselon.isSelontSelonarchRelonsults()) {
              selonarchRelonsultsNotSelont.increlonmelonnt();
            } elonlselon {
              for (ThriftSelonarchRelonsult selonarchRelonsult : relonsponselon.gelontSelonarchRelonsults().gelontRelonsults()) {
                selonarchRelonsult.selontTwelonelontSourcelon(twelonelontSourcelon);
              }
            }
          }
          relonturn relonsponselon;
        }
      }
    );
  }
}
