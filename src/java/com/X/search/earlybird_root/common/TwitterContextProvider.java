package com.X.search.earlybird_root.common;

import javax.inject.Singleton;

import scala.Option;

import com.X.context.XContext;
import com.X.context.thriftscala.Viewer;
import com.X.search.XContextPermit;

/**
 * This class is needed to provide an easy way for unit tests to "inject"
 * a XContext Viewer
 */
@Singleton
public class XContextProvider {
  public Option<Viewer> get() {
    return XContext.acquire(XContextPermit.get()).apply();
  }
}
