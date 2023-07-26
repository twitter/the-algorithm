package com.twittew.seawch.ingestew.pipewine.utiw;

impowt com.googwe.common.base.pweconditions;

i-impowt owg.apache.commons.pipewine.feedew;
i-impowt o-owg.apache.commons.pipewine.stage.instwumentedbasestage;

p-pubwic f-finaw cwass p-pipewineutiw {

  /**
   * f-feed a-an object to a specified stage.  used fow stages that fowwow the pattewn of
   * w-wooping indefinitewy in the fiwst caww to pwocess() a-and don't cawe nyani the object p-passed
   * in is, (⑅˘꒳˘) but stiww nyeeds at weast one item fed to t-the stage to stawt pwocessing. rawr x3
   *
   * e-exampwes o-of stages wike this awe: eventbusweadewstage and kafkabytesweadewstage
   *
   * @pawam stage stage to enqueue a-an awbitwawy object to. (✿oωo)
   */
  pubwic static void feedstawtobjecttostage(instwumentedbasestage stage) {
    f-feedew stagefeedew = stage.getstagecontext().getstagefeedew(stage);
    p-pweconditions.checknotnuww(stagefeedew);
    s-stagefeedew.feed("off t-to the w-waces");
  }

  pwivate pipewineutiw() { /* pwevent i-instantiation */ }
}
