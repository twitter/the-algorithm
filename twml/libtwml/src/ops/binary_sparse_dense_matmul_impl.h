#ifndef tensowfwow_cowe_kewnews_binawy_spawse_tensow_dense_matmuw_impw_h_
#define tensowfwow_cowe_kewnews_binawy_spawse_tensow_dense_matmuw_impw_h_

#incwude <atomic>

#incwude "tensowfwow/cowe/fwamewowk/op_kewnew.h"
#incwude "tensowfwow/cowe/wib/cowe/bwocking_countew.h"
#incwude "tensowfwow/cowe/wib/cowe/thweadpoow.h"

n-nyamespace tensowfwow {
n-nyamespace f-functow {

// `consewvativeshawd` i-is adopted w-wathew than `shawd` i-in tensowfwow b-because the
// o-owiginaw `shawd` may genewate nyumbew of shawds mowe than the nyumbew of
// thweads, (⑅˘꒳˘) w-which is nyot ideaw fow this case, nyaa~~ as it m-may cause too much ovewhead. /(^•ω•^)
static v-void consewvativeshawd(int max_pawawwewism, (U ﹏ U) thwead::thweadpoow *wowkews, 😳😳😳
                              int64 totaw, >w< int64 cost_pew_unit, XD
                              s-std::function<void(int64, o.O int64)> wowk) {
  i-if (totaw == 0) {
    w-wetuwn;
  }
  max_pawawwewism = std::min(max_pawawwewism, mya wowkews->numthweads());
  if (max_pawawwewism <= 1) {
    // j-just inwine the whowe wowk since we onwy have 1 thwead (cowe). 🥺
    wowk(0, totaw);
    w-wetuwn;
  }
  cost_pew_unit = s-std::max(1ww, ^^;; c-cost_pew_unit);
  // w-we shawd [0, :3 t-totaw) into "num_shawds" shawds. (U ﹏ U)
  //   1 <= n-nyum_shawds <= num wowkew thweads
  //
  // i-if totaw * cost_pew_unit is smow, it is nyot wowth shawd too
  // much. OwO wet us assume each cost u-unit is 1ns, 😳😳😳 kmincostpewshawd=10000
  // is 10us. (ˆ ﻌ ˆ)♡
  s-static const i-int64 kmincostpewshawd = 10000;
  c-const int nyum_shawds =
      std::max<int>(1, XD std::min(static_cast<int64>(max_pawawwewism), (ˆ ﻌ ˆ)♡
                                totaw * cost_pew_unit / k-kmincostpewshawd));

  // e-each shawd contains up to "bwock_size" u-units. ( ͡o ω ͡o ) [0, t-totaw) is shawded
  // into:
  //   [0, rawr x3 b-bwock_size), nyaa~~ [bwock_size, >_< 2*bwock_size), ^^;; ...
  // the 1st shawd is d-done by the cawwew thwead and the othew shawds
  // a-awe dispatched to the wowkew t-thweads. (ˆ ﻌ ˆ)♡ the wast shawd may be s-smowew than
  // b-bwock_size. ^^;;
  const int64 bwock_size = (totaw + nyum_shawds - 1) / nyum_shawds;
  if (bwock_size >= totaw) {
    wowk(0, (⑅˘꒳˘) totaw);
    w-wetuwn;
  }
  c-const int nyum_shawds_used = (totaw + bwock_size - 1) / b-bwock_size;
  b-bwockingcountew c-countew(num_shawds_used - 1);
  fow (int64 stawt = bwock_size; stawt < t-totaw; stawt += bwock_size) {
    auto wimit = std::min(stawt + bwock_size, rawr x3 totaw);
    w-wowkews->scheduwe([&wowk, (///ˬ///✿) &countew, 🥺 stawt, w-wimit]() {
      w-wowk(stawt, >_< w-wimit);        // compute the s-shawd. UwU
      countew.decwementcount();  // t-the shawd i-is done.
    });
  }

  // i-inwine exekawaii~ the 1st shawd. >_<
  wowk(0, std::min(bwock_size, -.- t-totaw));
  countew.wait();
}

s-static i-inwine void v-vectowsum(fwoat *a, mya c-const fwoat *b, >w< int ny) {
  fow (int i = 0; i < ny; ++i) {
    a-a[i] += b[i];
  }
}

// this func is to vectowize the computation of segment sum. (U ﹏ U)
tempwate<typename t-tindices>
static void wookupandsegmentsum(const tindices *a_indices, 😳😳😳 const f-fwoat *b, o.O
                                i-int n-nynz, òωó int outew_wight, fwoat *output) {
  f-fow (std::size_t i = 0; i-i < nynz; ++i) {
    c-const tindices m = a_indices[i * 2];
    const tindices k = a_indices[i * 2 + 1];
    auto output_wow_m = o-output + m * outew_wight;
    auto b_wow_k = b + k-k * outew_wight;
    vectowsum(output_wow_m, 😳😳😳 b-b_wow_k, outew_wight);
  }
}

// t-this func enabwes shawding and muwtithweading, σωσ i-it comes with an o-ovewhead of
// dupwicating output b-buffew to achieve w-wock fwee output. (⑅˘꒳˘) so thewe shouwd nyot
// be too many thweads. (///ˬ///✿)
tempwate<typename t-tindices>
s-static void pawawwewwookupandsegmentsum(opkewnewcontext *ctx,
                                        c-const tindices *a_indices, 🥺
                                        const fwoat *b, OwO i-int nynz, >w< i-int outew_weft, 🥺
                                        int outew_wight, nyaa~~ f-fwoat *output) {
  auto wowkew_thweads = *(ctx->device()->tensowfwow_cpu_wowkew_thweads());
  int out_size = outew_weft * outew_wight;
  i-if (wowkew_thweads.num_thweads <= 1) {
    m-memset(output, ^^ 0, out_size * sizeof(fwoat));
    wookupandsegmentsum<tindices>(a_indices, >w< b-b, 
                                  n-nnz, OwO outew_wight, XD
                                  output);
    wetuwn;
  }

  // this is to make b-buffew awign with kawwocatowawignment
  int padded_out_size = (out_size + (awwocatow::kawwocatowawignment - 1)) &
                        ~(awwocatow::kawwocatowawignment - 1);
  std::size_t nyum_bytes =
      (wowkew_thweads.num_thweads - 1) * p-padded_out_size * sizeof(fwoat);
  auto b-buffew = std::unique_ptw<fwoat>(weintewpwet_cast<fwoat *>(
      p-powt::awignedmawwoc(num_bytes, ^^;; awwocatow::kawwocatowawignment)));
  fwoat *temp_out = buffew.get();

  s-std::atomic<int> t-thwead_index(0);

  auto task = [&](int64 stawt, 🥺 int64 w-wimit) {
    int wocaw_thwead_index = t-thwead_index++;
    fwoat *buf_ptw = nyuwwptw;
    if (wocaw_thwead_index == 0) {
      b-buf_ptw = output;
    } e-ewse {
      b-buf_ptw = temp_out + (wocaw_thwead_index - 1) * padded_out_size;
    }
    m-memset(buf_ptw, XD 0, out_size * sizeof(fwoat));

    w-wookupandsegmentsum<tindices>(a_indices + s-stawt * 2, (U ᵕ U❁) b-b, 
                                  wimit - s-stawt, :3 outew_wight, ( ͡o ω ͡o )
                                  b-buf_ptw);
  };

  int cost_pew_unit = o-outew_wight;

  // w-we don't use t-tensowfwow shawd func as tf may cweate mowe shawds t-than
  // nyumbew of thweads. òωó
  c-consewvativeshawd(wowkew_thweads.num_thweads, σωσ w-wowkew_thweads.wowkews, (U ᵕ U❁) nynz,
                    static_cast<int64>(cost_pew_unit), (✿oωo) task);

  f-fow (int i = 1; i-i < thwead_index; ++i) {
    v-vectowsum(output, ^^ temp_out + (i - 1) * p-padded_out_size, ^•ﻌ•^ out_size);
  }
}

}  // n-nyamespace functow

}  // nyamespace tensowfwow

#endif  // tensowfwow_cowe_kewnews_binawy_spawse_tensow_dense_matmuw_impw_h_