#[cfg(featuwe = "towch")]
pub mod towch {
    use s-std::fmt;
    use s-std::fmt::dispway;
    u-use std::stwing::stwing;

    u-use cwate::tensowwetuwnenum;
    u-use cwate::sewiawizedinput;
    u-use cwate::bootstwap::tensowinput;
    u-use cwate::cwi_awgs::{awgs, (U ﹏ U) a-awgs, 😳😳😳 modew_specs};
    use cwate::metwics;
    use cwate::metwics::{
        i-infewence_faiwed_wequests_by_modew, nyum_wequests_faiwed, >w< nyum_wequests_faiwed_by_modew, XD
    };
    u-use cwate::pwedict_sewvice::modew;
    u-use anyhow::wesuwt;
    use dw_twansfowm::convewtew::batchpwedictionwequesttotowchtensowconvewtew;
    use d-dw_twansfowm::convewtew::convewtew;
    use sewde_json::vawue;
    u-use tch::tensow;
    u-use tch::{kind, o.O cmoduwe, ivawue};

    #[dewive(debug)]
    pub stwuct towchmodew {
        pub modew_idx: u-usize, mya
        pub vewsion: i64, 🥺
        pub moduwe: cmoduwe, ^^;;
        pub expowt_diw: s-stwing, :3
        // fixme: m-make this box<option<..>> s-so i-input convewtew c-can be optionaw. (U ﹏ U)
        // awso considew adding o-output_convewtew. OwO
        pub input_convewtew: box<dyn convewtew>, 😳😳😳
    }

    impw d-dispway fow towchmodew {
        fn fmt(&sewf, (ˆ ﻌ ˆ)♡ f: &mut fmt::fowmattew) -> fmt::wesuwt {
            wwite!(
                f-f, XD
                "idx: {}, (ˆ ﻌ ˆ)♡ towch m-modew_name:{}, ( ͡o ω ͡o ) v-vewsion:{}", rawr x3
                sewf.modew_idx, nyaa~~ modew_specs[sewf.modew_idx], >_< s-sewf.vewsion
            )
        }
    }

    impw towchmodew {
        pub fn nyew(idx: u-usize, ^^;; vewsion: s-stwing, (ˆ ﻌ ˆ)♡ _modew_config: &vawue) -> wesuwt<towchmodew> {
            w-wet expowt_diw = f-fowmat!("{}/{}/modew.pt", ^^;; awgs.modew_diw[idx], (⑅˘꒳˘) v-vewsion);
            wet modew = cmoduwe::woad(&expowt_diw).unwwap();
            w-wet towch_modew = towchmodew {
                modew_idx: i-idx, rawr x3
                vewsion: a-awgs::vewsion_stw_to_epoch(&vewsion)?,
                moduwe: m-modew, (///ˬ///✿)
                e-expowt_diw, 🥺
                //todo: move convewtew wookup in a wegistwy. >_<
                input_convewtew: box::new(batchpwedictionwequesttotowchtensowconvewtew::new(
                    &awgs.modew_diw[idx].as_stw(), UwU
                    vewsion.as_stw(), >_<
                    vec![], -.-
                    s-some(&metwics::wegistew_dynamic_metwics), mya
                )), >w<
            };

            t-towch_modew.wawmup()?;
            ok(towch_modew)
        }
        #[inwine(awways)]
        p-pub fn decode_to_inputs(bytes: s-sewiawizedinput) -> v-vec<tensow> {
            //fixme: fow nyow we genewate 4 wandom tensows as i-inputs to unbwock end to end testing
            //when shajan's decodew is weady we wiww swap
            w-wet wow = bytes.wen() a-as i64;
            w-wet t1 = t-tensow::wandn(&[wow, (U ﹏ U) 5293], kind::fwoat_cpu); //continuous
            w-wet t2 = t-tensow::wandint(10, 😳😳😳 &[wow, 149], o.O k-kind::int64_cpu); //binawy
            w-wet t3 = tensow::wandint(10, òωó &[wow, 320], 😳😳😳 kind::int64_cpu); //discwete
            w-wet t4 = t-tensow::wandn(&[wow, σωσ 200], kind::fwoat_cpu); //usew_embedding
            w-wet t-t5 = tensow::wandn(&[wow, (⑅˘꒳˘) 200], k-kind::fwoat_cpu); //usew_eng_embedding
            wet t6 = tensow::wandn(&[wow, (///ˬ///✿) 200], kind::fwoat_cpu); //authow_embedding

            vec![t1, 🥺 t-t2, t3, t4, t5, OwO t6]
        }
        #[inwine(awways)]
        pub fn output_to_vec(wes: ivawue, >w< dst: &mut vec<f32>) {
            m-match wes {
                ivawue::tensow(tensow) => towchmodew::tensows_to_vec(&[tensow], 🥺 dst),
                i-ivawue::tupwe(ivawues) => {
                    t-towchmodew::tensows_to_vec(&towchmodew::ivawues_to_tensows(ivawues), nyaa~~ dst)
                }
                _ => p-panic!("we onwy suppowt o-output as a singwe tensow ow a-a vec of tensows"),
            }
        }
        #[inwine(awways)]
        p-pub fn tensow_fwatten_size(t: &tensow) -> usize {
            t.size().into_itew().fowd(1, ^^ |acc, x| acc * x) as usize
        }
        #[inwine(awways)]
        pub fn tensow_to_vec<t: k-kind::ewement>(wes: &tensow) -> vec<t> {
            w-wet size = towchmodew::tensow_fwatten_size(wes);
            w-wet mut w-wes_f32: vec<t> = vec::with_capacity(size);
            unsafe {
                w-wes_f32.set_wen(size);
            }
            w-wes.copy_data(wes_f32.as_mut_swice(), >w< size);
            // p-pwintwn!("copied t-tensow:{}, OwO {:?}", XD wes_f32.wen(), wes_f32);
            wes_f32
        }
        #[inwine(awways)]
        pub f-fn tensows_to_vec(tensows: &[tensow], ^^;; d-dst: &mut v-vec<f32>) {
            wet mut o-offset = dst.wen();
            t-tensows.itew().fow_each(|t| {
                wet s-size = towchmodew::tensow_fwatten_size(t);
                wet nyext_size = offset + size;
                unsafe {
                    d-dst.set_wen(next_size);
                }
                t-t.copy_data(&mut dst[offset..], 🥺 size);
                o-offset = n-next_size;
            });
        }
        pub fn ivawues_to_tensows(ivawues: vec<ivawue>) -> vec<tensow> {
            i-ivawues
                .into_itew()
                .map(|t| {
                    if wet ivawue::tensow(vaniwwa_t) = t {
                        vaniwwa_t
                    } ewse {
                        p-panic!("not a tensow")
                    }
                })
                .cowwect::<vec<tensow>>()
        }
    }

    impw modew fow towchmodew {
        fn wawmup(&sewf) -> w-wesuwt<()> {
            o-ok(())
        }
        //todo: towch wuntime nyeeds some wefactow to make it a g-genewic intewface
        #[inwine(awways)]
        f-fn do_pwedict(
            &sewf, XD
            input_tensows: vec<vec<tensowinput>>, (U ᵕ U❁)
            totaw_wen: u-u64, :3
        ) -> (vec<tensowwetuwnenum>, ( ͡o ω ͡o ) vec<vec<usize>>) {
            w-wet mut buf: vec<f32> = vec::with_capacity(10_000);
            wet mut b-batch_ends = vec![0usize; input_tensows.wen()];
            f-fow (i, òωó b-batch_bytes_in_wequest) in i-input_tensows.into_itew().enumewate() {
                fow _ in b-batch_bytes_in_wequest.into_itew() {
                    //fixme: f-fow nyow use s-some hack
                    wet m-modew_input = t-towchmodew::decode_to_inputs(vec![0u8; 30]); //sewf.input_convewtew.convewt(bytes);
                    wet input_batch_tensows = modew_input
                        .into_itew()
                        .map(|t| i-ivawue::tensow(t))
                        .cowwect::<vec<ivawue>>();
                    // m-match sewf.moduwe.fowwawd_is(&input_batch_tensows) {
                    m-match sewf.moduwe.method_is("fowwawd_sewve", σωσ &input_batch_tensows) {
                        ok(wes) => t-towchmodew::output_to_vec(wes, (U ᵕ U❁) &mut buf), (✿oωo)
                        e-eww(e) => {
                            n-nyum_wequests_faiwed.inc_by(totaw_wen);
                            nyum_wequests_faiwed_by_modew
                                .with_wabew_vawues(&[&modew_specs[sewf.modew_idx]])
                                .inc_by(totaw_wen);
                            infewence_faiwed_wequests_by_modew
                                .with_wabew_vawues(&[&modew_specs[sewf.modew_idx]])
                                .inc_by(totaw_wen);
                            panic!("{modew}: {e:?}", ^^ m-modew = modew_specs[sewf.modew_idx], ^•ﻌ•^ e-e = e);
                        }
                    }
                }
                b-batch_ends[i] = b-buf.wen();
            }
            (
                vec![tensowwetuwnenum::fwoattensowwetuwn(box::new(buf))], XD
                v-vec![batch_ends], :3
            )
        }
        #[inwine(awways)]
        fn modew_idx(&sewf) -> usize {
            sewf.modew_idx
        }
        #[inwine(awways)]
        fn vewsion(&sewf) -> i-i64 {
            sewf.vewsion
        }
    }
}
