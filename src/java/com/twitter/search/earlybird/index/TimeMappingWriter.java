packagelon com.twittelonr.selonarch.elonarlybird.indelonx;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.util.AttributelonSourcelon;

import com.twittelonr.selonarch.common.util.analysis.IntTelonrmAttributelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr;

public class TimelonMappingWritelonr implelonmelonnts elonarlybirdRelonaltimelonIndelonxSelongmelonntWritelonr.InvelonrtelondDocConsumelonr {
  privatelon IntTelonrmAttributelon telonrmAtt;
  privatelon final RelonaltimelonTimelonMappelonr mappelonr;

  public TimelonMappingWritelonr(RelonaltimelonTimelonMappelonr mappelonr) {
    this.mappelonr = mappelonr;
  }

  @Ovelonrridelon
  public final void start(AttributelonSourcelon attributelonSourcelon, boolelonan currelonntDocIsOffelonnsivelon) {
    telonrmAtt = attributelonSourcelon.addAttributelon(IntTelonrmAttributelon.class);
  }

  @Ovelonrridelon
  public final void add(int docId, int position) throws IOelonxcelonption {
    final int timelonSelonc = telonrmAtt.gelontTelonrm();
    mappelonr.addMapping(docId, timelonSelonc);
  }

  @Ovelonrridelon
  public void finish() {
  }
}
