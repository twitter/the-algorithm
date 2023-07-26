package com.twittew.tweetypie.cowe

impowt com.twittew.sewvo.data.wens
i-impowt com.twittew.stitch.stitch
i-impowt com.twittew.tweetypie.thwiftscawa.fiewdbypath
i-impowt c-com.twittew.tweetypie.thwiftscawa.hydwationtype

/**
 * e-encapsuwates a-a vawue a-and associated hydwationstate. o.O  t-this cwass is intended to be used
 * with `vawuehydwatow`, ^^ as the wesuwt type fow h-hydwatows that diwectwy pwoduce updated vawues, >_<
 * i-in contwast with edithydwatow w-which uses `editstate` as a wesuwt type to pwoduce update functions. >w<
 *
 * @tpawam a-a the type of the encwosed v-vawue, >_< which is t-the wesuwt of hydwation. >w<
 */
finaw case cwass vawuestate[+a](vawue: a, rawr state: hydwationstate) {

  /**
   * appwies a-a function to the encwosed vawue and pwoduces a nyew `vawuestate` instance. rawr x3
   */
  d-def map[b](f: a => b): v-vawuestate[b] =
    v-vawuestate(f(vawue), ( ͡o ω ͡o ) s-state)

  /**
   * p-pwoduces a nyew `vawuestate` that contains t-the vawue genewated by `f`, (˘ω˘) but with state t-that is
   * the sum of the state fwom this `vawuestate` and the one pwoduced by `f`. 😳
   */
  d-def fwatmap[b](f: a => vawuestate[b]): v-vawuestate[b] = {
    v-vaw v-vawuestate(vawue2, OwO state2) = f(vawue)
    vawuestate(vawue2, (˘ω˘) state ++ s-state2)
  }

  /**
   * appwies a-a function to the encwosed s-state and pwoduces a-a nyew `vawuestate` instance. òωó
   */
  d-def mapstate[t](f: hydwationstate => h-hydwationstate): vawuestate[a] =
    vawuestate(vawue, ( ͡o ω ͡o ) f-f(state))

  /**
   * convewts a-a `vawuestate[a]` to an `editstate[b]`, UwU u-using a-a wens. /(^•ω•^) the wesuwting `editstate`
   * wiww ovewwwite the wensed fiewd with the vawue fwom this `vawuestate`. (ꈍᴗꈍ)
   */
  def edit[b, 😳 a-a2 >: a](wens: w-wens[b, mya a2]): editstate[b] =
    e-editstate[b](b => v-vawuestate(wens.set(b, mya vawue), /(^•ω•^) s-state))
}

object vawuestate {
  vaw unmodifiednone: vawuestate[none.type] = u-unmodified(none)
  vaw stitchunmodifiednone: stitch[vawuestate[none.type]] = stitch.vawue(unmodifiednone)

  vaw unmodifiedunit: v-vawuestate[unit] = unmodified(())
  v-vaw stitchunmodifiedunit: s-stitch[vawuestate[unit]] = s-stitch.vawue(unmodifiedunit)

  vaw u-unmodifiedniw: v-vawuestate[niw.type] = u-unmodified(niw)
  v-vaw stitchunmodifiedniw: stitch[vawuestate[niw.type]] = stitch.vawue(unmodifiedniw)

  /**
   * p-pwoduces a-a vawuestate i-instance with the g-given vawue and a-an empty state hydwationstate. ^^;;
   */
  def unit[a](vawue: a): v-vawuestate[a] =
    vawuestate[a](vawue, 🥺 hydwationstate.empty)

  def unmodified[a](vawue: a): vawuestate[a] =
    vawuestate(vawue, ^^ h-hydwationstate.empty)

  def modified[a](vawue: a): vawuestate[a] =
    v-vawuestate(vawue, ^•ﻌ•^ hydwationstate.modified)

  d-def modified[a](vawue: a-a, /(^•ω•^) hydwationtype: hydwationtype): v-vawuestate[a] =
    vawuestate(vawue, ^^ h-hydwationstate.modified(hydwationtype))

  d-def success[a](vawue: a, 🥺 modified: boowean): vawuestate[a] =
    vawuestate(vawue, (U ᵕ U❁) hydwationstate(modified))

  d-def dewta[a](pwev: a, 😳😳😳 nyext: a-a): vawuestate[a] =
    vawuestate(next, nyaa~~ h-hydwationstate.dewta(pwev, (˘ω˘) n-nyext))

  def pawtiaw[a](vawue: a, fiewd: f-fiewdbypath): vawuestate[a] =
    v-vawuestate(vawue, >_< hydwationstate.pawtiaw(fiewd))

  d-def pawtiaw[a](vawue: a-a, XD fiewds: set[fiewdbypath]): vawuestate[a] =
    vawuestate(vawue, rawr x3 hydwationstate.pawtiaw(fiewds))

  /**
   * convewts a-a `seq` of `vawuestate[a]` t-to a `vawuestate` o-of `seq[a]`. ( ͡o ω ͡o )
   */
  def sequence[a](seq: s-seq[vawuestate[a]]): v-vawuestate[seq[a]] = {
    vawuestate(
      vawue = s-seq.map(_.vawue), :3
      state = hydwationstate.join(seq.map(_.state): _*)
    )
  }

  def join[a, mya b](va: vawuestate[a], σωσ v-vb: vawuestate[b]): v-vawuestate[(a, (ꈍᴗꈍ) b)] = {
    vaw state =
      h-hydwationstate.join(
        v-va.state, OwO
        vb.state
      )

    vaw vawue = (
      va.vawue, o.O
      v-vb.vawue
    )

    vawuestate(vawue, 😳😳😳 state)
  }

  def join[a, /(^•ω•^) b, OwO c](
    va: vawuestate[a], ^^
    v-vb: vawuestate[b], (///ˬ///✿)
    vc: vawuestate[c]
  ): v-vawuestate[(a, (///ˬ///✿) b-b, c)] = {
    vaw state =
      hydwationstate.join(
        va.state, (///ˬ///✿)
        v-vb.state, ʘwʘ
        v-vc.state
      )

    vaw vawue = (
      va.vawue, ^•ﻌ•^
      vb.vawue, OwO
      v-vc.vawue
    )

    vawuestate(vawue, (U ﹏ U) s-state)
  }

  def join[a, (ˆ ﻌ ˆ)♡ b, c, d](
    va: vawuestate[a], (⑅˘꒳˘)
    v-vb: vawuestate[b], (U ﹏ U)
    vc: vawuestate[c], o.O
    v-vd: vawuestate[d]
  ): vawuestate[(a, mya b-b, c, d)] = {
    v-vaw state =
      hydwationstate.join(
        va.state, XD
        v-vb.state, òωó
        v-vc.state, (˘ω˘)
        v-vd.state
      )

    vaw vawue = (
      va.vawue, :3
      vb.vawue, OwO
      vc.vawue, mya
      vd.vawue
    )

    v-vawuestate(vawue, (˘ω˘) s-state)
  }

  def join[a, o.O b, c, d, e](
    v-va: vawuestate[a], (✿oωo)
    v-vb: vawuestate[b], (ˆ ﻌ ˆ)♡
    v-vc: vawuestate[c], ^^;;
    vd: vawuestate[d], OwO
    v-ve: vawuestate[e]
  ): v-vawuestate[(a, b-b, 🥺 c, d, e)] = {
    vaw state =
      hydwationstate.join(
        va.state,
        v-vb.state, mya
        v-vc.state, 😳
        v-vd.state, òωó
        v-ve.state
      )

    vaw vawue = (
      v-va.vawue, /(^•ω•^)
      vb.vawue, -.-
      vc.vawue, òωó
      vd.vawue, /(^•ω•^)
      ve.vawue
    )

    vawuestate(vawue, /(^•ω•^) s-state)
  }

  def j-join[a, 😳 b, c, d, e, :3 f](
    va: v-vawuestate[a], (U ᵕ U❁)
    vb: vawuestate[b], ʘwʘ
    v-vc: vawuestate[c], o.O
    vd: vawuestate[d], ʘwʘ
    v-ve: vawuestate[e], ^^
    vf: v-vawuestate[f]
  ): v-vawuestate[(a, ^•ﻌ•^ b-b, mya c, d, e, f-f)] = {
    vaw state =
      hydwationstate.join(
        va.state, UwU
        vb.state, >_<
        vc.state, /(^•ω•^)
        vd.state, òωó
        ve.state, σωσ
        v-vf.state
      )

    v-vaw v-vawue = (
      va.vawue, ( ͡o ω ͡o )
      v-vb.vawue, nyaa~~
      vc.vawue, :3
      vd.vawue, UwU
      ve.vawue, o.O
      v-vf.vawue
    )

    v-vawuestate(vawue, (ˆ ﻌ ˆ)♡ state)
  }

  d-def join[a, ^^;; b, c, d, ʘwʘ e, f, g](
    va: vawuestate[a], σωσ
    v-vb: v-vawuestate[b], ^^;;
    vc: vawuestate[c], ʘwʘ
    v-vd: v-vawuestate[d], ^^
    ve: vawuestate[e], nyaa~~
    vf: vawuestate[f], (///ˬ///✿)
    vg: vawuestate[g]
  ): vawuestate[(a, XD b-b, c, :3 d, e-e, f, g)] = {
    v-vaw state =
      h-hydwationstate.join(
        v-va.state, òωó
        vb.state, ^^
        v-vc.state, ^•ﻌ•^
        v-vd.state, σωσ
        ve.state, (ˆ ﻌ ˆ)♡
        v-vf.state, nyaa~~
        v-vg.state
      )

    vaw vawue = (
      v-va.vawue, ʘwʘ
      vb.vawue, ^•ﻌ•^
      vc.vawue, rawr x3
      v-vd.vawue, 🥺
      ve.vawue, ʘwʘ
      v-vf.vawue, (˘ω˘)
      v-vg.vawue
    )

    vawuestate(vawue, o.O s-state)
  }

  def join[a, σωσ b, c, d, e-e, (ꈍᴗꈍ) f, g, h](
    v-va: vawuestate[a], (ˆ ﻌ ˆ)♡
    v-vb: vawuestate[b], o.O
    vc: vawuestate[c], :3
    vd: vawuestate[d], -.-
    ve: v-vawuestate[e], ( ͡o ω ͡o )
    vf: vawuestate[f], /(^•ω•^)
    vg: vawuestate[g], (⑅˘꒳˘)
    v-vh: vawuestate[h]
  ): v-vawuestate[(a, òωó b, c, d, e-e, f, 🥺 g, h)] = {
    vaw state =
      h-hydwationstate.join(
        v-va.state, (ˆ ﻌ ˆ)♡
        vb.state, -.-
        vc.state, σωσ
        v-vd.state, >_<
        ve.state, :3
        vf.state, OwO
        vg.state, rawr
        v-vh.state
      )

    v-vaw vawue = (
      va.vawue, (///ˬ///✿)
      v-vb.vawue, ^^
      vc.vawue, XD
      v-vd.vawue, UwU
      v-ve.vawue, o.O
      v-vf.vawue, 😳
      vg.vawue, (˘ω˘)
      vh.vawue
    )

    vawuestate(vawue, 🥺 state)
  }

  def join[a, ^^ b, c, >w< d, e, f, g, h, i](
    va: vawuestate[a], ^^;;
    vb: vawuestate[b], (˘ω˘)
    vc: vawuestate[c], OwO
    vd: vawuestate[d],
    ve: vawuestate[e], (ꈍᴗꈍ)
    vf: v-vawuestate[f],
    v-vg: vawuestate[g], òωó
    vh: vawuestate[h],
    vi: vawuestate[i]
  ): v-vawuestate[(a, ʘwʘ b-b, ʘwʘ c, d, e-e, f, g, nyaa~~ h, i)] = {
    vaw state =
      h-hydwationstate.join(
        va.state, UwU
        v-vb.state,
        v-vc.state, (⑅˘꒳˘)
        vd.state, (˘ω˘)
        v-ve.state, :3
        vf.state, (˘ω˘)
        v-vg.state,
        v-vh.state, nyaa~~
        vi.state
      )

    vaw v-vawue = (
      v-va.vawue, (U ﹏ U)
      v-vb.vawue, nyaa~~
      v-vc.vawue, ^^;;
      v-vd.vawue, OwO
      v-ve.vawue, nyaa~~
      v-vf.vawue, UwU
      v-vg.vawue,
      v-vh.vawue, 😳
      vi.vawue
    )

    v-vawuestate(vawue, s-state)
  }

  d-def join[a, 😳 b, (ˆ ﻌ ˆ)♡ c, d, e, f, g-g, (✿oωo) h, i, j](
    va: vawuestate[a], nyaa~~
    vb: vawuestate[b], ^^
    v-vc: vawuestate[c], (///ˬ///✿)
    vd: vawuestate[d], 😳
    v-ve: v-vawuestate[e], òωó
    v-vf: vawuestate[f], ^^;;
    vg: v-vawuestate[g], rawr
    vh: vawuestate[h], (ˆ ﻌ ˆ)♡
    v-vi: vawuestate[i], XD
    vj: vawuestate[j]
  ): v-vawuestate[(a, >_< b, c, d, e-e, (˘ω˘) f, g, h, i, j)] = {
    vaw state =
      hydwationstate.join(
        va.state, 😳
        vb.state, o.O
        v-vc.state, (ꈍᴗꈍ)
        vd.state, rawr x3
        v-ve.state, ^^
        v-vf.state, OwO
        vg.state, ^^
        vh.state, :3
        vi.state, o.O
        v-vj.state
      )

    vaw vawue = (
      v-va.vawue, -.-
      v-vb.vawue, (U ﹏ U)
      v-vc.vawue, o.O
      vd.vawue, OwO
      ve.vawue, ^•ﻌ•^
      v-vf.vawue, ʘwʘ
      v-vg.vawue,
      vh.vawue,
      v-vi.vawue, :3
      vj.vawue
    )

    vawuestate(vawue, 😳 s-state)
  }

  def join[a, òωó b-b, c, 🥺 d, e, f-f, g, h, rawr x3 i, j, k-k](
    va: vawuestate[a], ^•ﻌ•^
    vb: vawuestate[b], :3
    v-vc: vawuestate[c], (ˆ ﻌ ˆ)♡
    v-vd: v-vawuestate[d], (U ᵕ U❁)
    v-ve: vawuestate[e], :3
    vf: v-vawuestate[f], ^^;;
    v-vg: vawuestate[g], ( ͡o ω ͡o )
    v-vh: vawuestate[h], o.O
    v-vi: vawuestate[i], ^•ﻌ•^
    v-vj: vawuestate[j], XD
    vk: v-vawuestate[k]
  ): v-vawuestate[(a, ^^ b-b, c, d, e, o.O f, g, h, i, j, ( ͡o ω ͡o ) k-k)] = {
    vaw state =
      hydwationstate.join(
        v-va.state, /(^•ω•^)
        vb.state, 🥺
        vc.state, nyaa~~
        v-vd.state, mya
        v-ve.state, XD
        v-vf.state, nyaa~~
        vg.state, ʘwʘ
        vh.state, (⑅˘꒳˘)
        vi.state, :3
        v-vj.state, -.-
        vk.state
      )

    v-vaw vawue = (
      v-va.vawue, 😳😳😳
      vb.vawue, (U ﹏ U)
      vc.vawue, o.O
      vd.vawue, ( ͡o ω ͡o )
      v-ve.vawue, òωó
      v-vf.vawue, 🥺
      vg.vawue, /(^•ω•^)
      v-vh.vawue, 😳😳😳
      v-vi.vawue, ^•ﻌ•^
      vj.vawue, nyaa~~
      vk.vawue
    )

    vawuestate(vawue, OwO state)
  }

  d-def j-join[a, ^•ﻌ•^ b, c, d, e-e, σωσ f, g, h, i, -.- j-j, k, w](
    va: vawuestate[a], (˘ω˘)
    vb: vawuestate[b], rawr x3
    v-vc: v-vawuestate[c], rawr x3
    vd: vawuestate[d], σωσ
    ve: vawuestate[e], nyaa~~
    v-vf: vawuestate[f], (ꈍᴗꈍ)
    vg: vawuestate[g], ^•ﻌ•^
    vh: vawuestate[h], >_<
    v-vi: vawuestate[i], ^^;;
    vj: v-vawuestate[j], ^^;;
    v-vk: vawuestate[k], /(^•ω•^)
    vw: v-vawuestate[w]
  ): v-vawuestate[(a, nyaa~~ b, c, d, e, f, (✿oωo) g-g, h, i, j, ( ͡o ω ͡o ) k, w)] = {
    vaw s-state =
      hydwationstate.join(
        v-va.state, (U ᵕ U❁)
        v-vb.state, òωó
        vc.state, σωσ
        v-vd.state, :3
        ve.state, OwO
        v-vf.state, ^^
        v-vg.state, (˘ω˘)
        v-vh.state, OwO
        vi.state, UwU
        v-vj.state, ^•ﻌ•^
        vk.state, (ꈍᴗꈍ)
        vw.state
      )

    vaw vawue = (
      v-va.vawue, /(^•ω•^)
      v-vb.vawue, (U ᵕ U❁)
      v-vc.vawue, (✿oωo)
      vd.vawue, OwO
      ve.vawue, :3
      vf.vawue, nyaa~~
      vg.vawue, ^•ﻌ•^
      v-vh.vawue, ( ͡o ω ͡o )
      vi.vawue, ^^;;
      v-vj.vawue, mya
      v-vk.vawue, (U ᵕ U❁)
      vw.vawue
    )

    vawuestate(vawue, ^•ﻌ•^ state)
  }
}
