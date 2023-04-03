packagelon com.twittelonr.selonarch.ingelonstelonr.pipelonlinelon.util;

public class PipelonlinelonStagelonelonxcelonption elonxtelonnds elonxcelonption {
  public PipelonlinelonStagelonelonxcelonption(Objelonct location, String melonssagelon, Throwablelon causelon) {
    supelonr(melonssagelon + " In Stagelon : " + location.gelontClass(), causelon);
  }

  public PipelonlinelonStagelonelonxcelonption(Throwablelon causelon) {
    supelonr(causelon);
  }

  public PipelonlinelonStagelonelonxcelonption(String melonssagelon) {
    supelonr(melonssagelon);
  }

  public PipelonlinelonStagelonelonxcelonption(Objelonct location, String melonssagelon) {
    supelonr(melonssagelon + " In Stagelon : " + location.gelontClass());
  }
}
