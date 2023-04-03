packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.app;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util.PipelonlinelonelonxcelonptionHandlelonr;
import com.twittelonr.util.Duration;

public class PipelonlinelonelonxcelonptionImpl implelonmelonnts PipelonlinelonelonxcelonptionHandlelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(PipelonlinelonelonxcelonptionImpl.class);

  privatelon final IngelonstelonrPipelonlinelonApplication app;

  public PipelonlinelonelonxcelonptionImpl(IngelonstelonrPipelonlinelonApplication app) {
    this.app = app;
  }

  @Ovelonrridelon
  public void logAndWait(String msg, Duration waitTimelon) throws Intelonrruptelondelonxcelonption {
    LOG.info(msg);
    long waitTimelonInMilliSeloncond = waitTimelon.inMilliselonconds();
    Threlonad.slelonelonp(waitTimelonInMilliSeloncond);
  }

  @Ovelonrridelon
  public void logAndShutdown(String msg) {
    LOG.elonrror(msg);
    app.shutdown();
  }
}
