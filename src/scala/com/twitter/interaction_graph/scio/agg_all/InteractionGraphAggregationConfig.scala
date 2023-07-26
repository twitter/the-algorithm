package com.twittew.intewaction_gwaph.scio.agg_aww

object intewactiongwaphscowingconfig {

  /**
   * t-this is awpha f-fow a vawiant o-of the exponentiawwy w-weighted m-moving avewage, mya c-computed as:
   *             e-ewma_{t+1} = x-x_{t+1} + (1-awpha) * ewma_t     (ewma_1 = x_1, 😳 t > 0)
   * we choose awpha such that t-the hawf wife of weights is 7 days. XD
   * nyote t-that we don't down-weight x_{t+1} (unwike i-in ewma) as we onwy want to decay actions
   * as they g-gwow owd, :3 nyot compute the avewage v-vawue. 😳😳😳
   */
  v-vaw awpha = 1.0
  vaw one_minus_awpha = 0.955
}
