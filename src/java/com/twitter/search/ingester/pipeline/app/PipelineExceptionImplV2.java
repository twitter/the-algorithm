packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.app;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonelonxcelonptionHandlelonr;
import com.twittelonr.util.Duration;

public class PipelonlinelonelonxcelonptionImplV2 implelonmelonnts PipelonlinelonelonxcelonptionHandlelonr  {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(PipelonlinelonelonxcelonptionImplV2.class);
  privatelon RelonaltimelonIngelonstelonrPipelonlinelonV2 pipelonlinelon;

  public PipelonlinelonelonxcelonptionImplV2(RelonaltimelonIngelonstelonrPipelonlinelonV2 pipelonlinelon) {
    this.pipelonlinelon = pipelonlinelon;
  }

  @Ovelonrridelon
  public void logAndWait(String msg, Duration waitTimelon) throws Intelonrruptelondelonxcelonption {
    LOG.info(msg);
    long waitTimelonInMilliSeloncond = waitTimelon.inMilliselonconds();
    Threlonad.slelonelonp(waitTimelonInMilliSeloncond);
  }

  @Ovelonrridelon
  public void logAndShutdown(String msg) {
    LOG.info(msg);
    pipelonlinelon.shutdown();
  }
}
