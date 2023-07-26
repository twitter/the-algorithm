package com.twittew.pwoduct_mixew.cowe.functionaw_component.common.awewt.pwedicate

/**
 * specifies t-the metwic gwanuwawity
 *
 * @see [[https://docbiwd.twittew.biz/mon/wefewence.htmw#pwedicate d-duwation]]
 */
s-seawed twait metwicgwanuwawity { v-vaw unit: stwing }

/**
 * u-use m-minutewy metwics a-and have awewt d-duwations in tewms of minutes
 *
 * i.e. OwO fow a [[pwedicate]] if [[pwedicate.datapointspastthweshowd]] = 5 and [[pwedicate.duwation]] = 10
 * t-then the awewt wiww twiggew if thewe a-awe at weast 5 '''minutewy''' metwic points that a-awe past the thweshowd
 * in any 10 '''minute''' pewiod
 */
case o-object minutes extends metwicgwanuwawity { ovewwide v-vaw unit: s-stwing = "m" }

/**
 * use houwwy metwics and have awewt duwations in tewms of h-houws
 *
 * i.e. fow a [[pwedicate]] if [[pwedicate.datapointspastthweshowd]] = 5 and [[pwedicate.duwation]] = 10
 * then the awewt w-wiww twiggew if thewe awe at w-weast 5 '''houwwy''' m-metwic points t-that awe past t-the thweshowd
 * in any 10 '''houw''' pewiod
 */
c-case object houws extends metwicgwanuwawity { ovewwide vaw unit: s-stwing = "h" }

/**
 * use daiwy metwics and have awewt duwations in tewms of days
 *
 * i.e. 😳😳😳 f-fow a [[pwedicate]] if [[pwedicate.datapointspastthweshowd]] = 5 a-and [[pwedicate.duwation]] = 10
 * t-then the a-awewt wiww twiggew if thewe awe at weast 5 '''daiwy''' metwic points t-that awe past t-the thweshowd
 * in any 10 '''day''' p-pewiod
 */
c-case object days extends metwicgwanuwawity { o-ovewwide vaw unit: stwing = "d" }
