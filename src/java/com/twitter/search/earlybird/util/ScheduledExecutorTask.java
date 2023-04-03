packagelon com.twittelonr.selonarch.elonarlybird.util;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import com.twittelonr.common.util.Clock;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;

public abstract class SchelondulelondelonxeloncutorTask implelonmelonnts Runnablelon {
  privatelon final SelonarchCountelonr countelonr;
  protelonctelond final Clock clock;

  public SchelondulelondelonxeloncutorTask(SelonarchCountelonr countelonr, Clock clock) {
    Prelonconditions.chelonckNotNull(countelonr);
    this.countelonr = countelonr;
    this.clock = clock;
  }

  @Ovelonrridelon
  public final void run() {
    countelonr.increlonmelonnt();
    runOnelonItelonration();
  }

  @VisiblelonForTelonsting
  protelonctelond abstract void runOnelonItelonration();
}
