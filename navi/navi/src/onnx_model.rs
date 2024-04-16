#[cfg(feature = "onnx")]
pub mod onnx {
    use crate::TensorReturnEnum;
    use crate::bootstrap::{Tensor nput, Tensor nputEnum};
    use crate::cl _args::{
        Args, ARGS,  NPUTS, MODEL_SPECS, OUTPUTS,
    };
    use crate:: tr cs::{self, CONVERTER_T ME_COLLECTOR};
    use crate::pred ct_serv ce::Model;
    use crate::{MAX_NUM_ NPUTS, MAX_NUM_OUTPUTS, META_ NFO, ut ls};
    use anyhow::Result;
    use arrayvec::ArrayVec;
    use dr_transform::converter::{BatchPred ct onRequestToTorchTensorConverter, Converter};
    use  ertools:: ertools;
    use log::{debug,  nfo};
    use dr_transform::ort::env ron nt::Env ron nt;
    use dr_transform::ort::sess on::Sess on;
    use dr_transform::ort::tensor:: nputTensor;
    use dr_transform::ort::{Execut onProv der, GraphOpt m zat onLevel, Sess onBu lder};
    use dr_transform::ort::Logg ngLevel;
    use serde_json::Value;
    use std::fmt::{Debug, D splay};
    use std::sync::Arc;
    use std::{fmt, fs};
    use tok o::t  :: nstant;
    lazy_stat c! {
        pub stat c ref ENV RONMENT: Arc<Env ron nt> = Arc::new(
            Env ron nt::bu lder()
                .w h_na ("onnx ho ")
                .w h_log_level(Logg ngLevel::Error)
                .w h_global_thread_pool(ARGS.onnx_global_thread_pool_opt ons.clone())
                .bu ld()
                .unwrap()
        );
    }
    #[der ve(Debug)]
    pub struct OnnxModel {
        pub sess on: Sess on,
        pub model_ dx: us ze,
        pub vers on:  64,
        pub export_d r: Str ng,
        pub output_f lters: ArrayVec<us ze, MAX_NUM_OUTPUTS>,
        pub  nput_converter: Box<dyn Converter>,
    }
     mpl D splay for OnnxModel {
        fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
            wr e!(
                f,
                " dx: {}, onnx model_na :{}, vers on:{}, output_f lters:{:?}, converter:{:}",
                self.model_ dx,
                MODEL_SPECS[self.model_ dx],
                self.vers on,
                self.output_f lters,
                self. nput_converter
            )
        }
    }
     mpl Drop for OnnxModel {
        fn drop(&mut self) {
             f ARGS.prof l ng != None {
                self.sess on.end_prof l ng().map_or_else(
                    |e| {
                         nfo!("end prof l ng w h so  error:{:?}", e);
                    },
                    |f| {
                         nfo!("prof l ng ended w h f le:{}", f);
                    },
                );
            }
        }
    }
     mpl OnnxModel {
        fn get_output_f lters(sess on: &Sess on,  dx: us ze) -> ArrayVec<us ze, MAX_NUM_OUTPUTS> {
            OUTPUTS[ dx]
                . er()
                .map(|output| sess on.outputs. er().pos  on(|o| o.na  == *output))
                .flatten()
                .collect::<ArrayVec<us ze, MAX_NUM_OUTPUTS>>()
        }
        #[cfg(target_os = "l nux")]
        fn ep_cho ces() -> Vec<Execut onProv der> {
            match ARGS.onnx_gpu_ep.as_ref().map(|e| e.as_str()) {
                So ("onednn") => vec![Self::ep_w h_opt ons(Execut onProv der::onednn())],
                So ("tensorrt") => vec![Self::ep_w h_opt ons(Execut onProv der::tensorrt())],
                So ("cuda") => vec![Self::ep_w h_opt ons(Execut onProv der::cuda())],
                _ => vec![Self::ep_w h_opt ons(Execut onProv der::cpu())],
            }
        }
        fn ep_w h_opt ons(mut ep: Execut onProv der) -> Execut onProv der {
            for (ref k, ref v)  n ARGS.onnx_ep_opt ons.clone() {
                ep = ep.w h(k, v);
                 nfo!("sett ng opt on:{} -> {} and now ep  s:{:?}", k, v, ep);
            }
            ep
        }
        #[cfg(target_os = "macos")]
        fn ep_cho ces() -> Vec<Execut onProv der> {
            vec![Self::ep_w h_opt ons(Execut onProv der::cpu())]
        }
        pub fn new( dx: us ze, vers on: Str ng, model_conf g: &Value) -> Result<OnnxModel> {
            let export_d r = format!("{}/{}/model.onnx", ARGS.model_d r[ dx], vers on);
            let  ta_ nfo = format!("{}/{}/{}", ARGS.model_d r[ dx], vers on, META_ NFO);
            let mut bu lder = Sess onBu lder::new(&ENV RONMENT)?
                .w h_opt m zat on_level(GraphOpt m zat onLevel::Level3)?
                .w h_parallel_execut on(ARGS.onnx_use_parallel_mode == "true")?;
             f ARGS.onnx_global_thread_pool_opt ons. s_empty() {
                bu lder = bu lder
                    .w h_ nter_threads(
                        ut ls::get_conf g_or(
                            model_conf g,
                            " nter_op_parallel sm",
                            &ARGS. nter_op_parallel sm[ dx],
                        )
                            .parse()?,
                    )?
                    .w h_ ntra_threads(
                        ut ls::get_conf g_or(
                            model_conf g,
                            " ntra_op_parallel sm",
                            &ARGS. ntra_op_parallel sm[ dx],
                        )
                            .parse()?,
                    )?;
            }
            else {
                bu lder = bu lder.w h_d sable_per_sess on_threads()?;
            }
            bu lder = bu lder
                .w h_ mory_pattern(ARGS.onnx_use_ mory_pattern == "true")?
                .w h_execut on_prov ders(&OnnxModel::ep_cho ces())?;
            match &ARGS.prof l ng {
                So (p) => {
                    debug!("Enable prof l ng, wr  ng to {}", *p);
                    bu lder = bu lder.w h_prof l ng(p)?
                }
                _ => {}
            }
            let sess on = bu lder.w h_model_from_f le(&export_d r)?;

             nfo!(
                " nputs: {:?}, outputs: {:?}",
                sess on. nputs. er().format(","),
                sess on.outputs. er().format(",")
            );

            fs::read_to_str ng(& ta_ nfo)
                .ok()
                .map(| nfo|  nfo!(" ta  nfo:{}",  nfo));
            let output_f lters = OnnxModel::get_output_f lters(&sess on,  dx);
            let mut report ng_feature_ ds: Vec<( 64, &str)> = vec![];

            let  nput_spec_cell = & NPUTS[ dx];
             f  nput_spec_cell.get(). s_none() {
                let  nput_spec = sess on
                    . nputs
                    . er()
                    .map(| nput|  nput.na .clone())
                    .collect::<ArrayVec<Str ng, MAX_NUM_ NPUTS>>();
                 nput_spec_cell.set( nput_spec.clone()).map_or_else(
                    |_|  nfo!("unable to set t   nput_spec for model {}",  dx),
                    |_|  nfo!("auto detect and set t   nputs: {:?}",  nput_spec),
                );
            }
            ARGS.onnx_report_d screte_feature_ ds
                . er()
                .for_each(| ds| {
                     ds.spl (",")
                        .f lter(|s| !s. s_empty())
                        .map(|s| s.parse::< 64>().unwrap())
                        .for_each(| d| report ng_feature_ ds.push(( d, "d screte")))
                });
            ARGS.onnx_report_cont nuous_feature_ ds
                . er()
                .for_each(| ds| {
                     ds.spl (",")
                        .f lter(|s| !s. s_empty())
                        .map(|s| s.parse::< 64>().unwrap())
                        .for_each(| d| report ng_feature_ ds.push(( d, "cont nuous")))
                });

            let onnx_model = OnnxModel {
                sess on,
                model_ dx:  dx,
                vers on: Args::vers on_str_to_epoch(&vers on)?,
                export_d r,
                output_f lters,
                 nput_converter: Box::new(BatchPred ct onRequestToTorchTensorConverter::new(
                    &ARGS.model_d r[ dx],
                    &vers on,
                    report ng_feature_ ds,
                    So ( tr cs::reg ster_dynam c_ tr cs),
                )?),
            };
            onnx_model.warmup()?;
            Ok(onnx_model)
        }
    }
    ///Currently   only assu  t   nput as just one str ng tensor.
    ///T  str ng tensor w ll be be converted to t  actual raw tensors.
    /// T  converter   are us ng  s very spec f c to ho .
    ///   reads a BatchDataRecord thr ft and decode   to a batch of raw  nput tensors.
    /// Nav  w ll t n do server s de batch ng and feed   to ONNX runt  
     mpl Model for OnnxModel {
        //TODO:  mple nt a gener c onl ne warmup for all runt  s
        fn warmup(&self) -> Result<()> {
            Ok(())
        }

        #[ nl ne(always)]
        fn do_pred ct(
            &self,
             nput_tensors: Vec<Vec<Tensor nput>>,
            _: u64,
        ) -> (Vec<TensorReturnEnum>, Vec<Vec<us ze>>) {
            let batc d_tensors = Tensor nputEnum:: rge_batch( nput_tensors);
            let ( nputs, batch_ends): (Vec<Vec< nputTensor>>, Vec<Vec<us ze>>) = batc d_tensors
                . nto_ er()
                .map(|batc d_tensor| {
                    match batc d_tensor.tensor_data {
                        Tensor nputEnum::Str ng(t)  f ARGS.onnx_use_converter. s_so () => {
                            let start =  nstant::now();
                            let ( nputs, batch_ends) = self. nput_converter.convert(t);
                            //  nfo!("batch_ends:{:?}", batch_ends);
                            CONVERTER_T ME_COLLECTOR
                                .w h_label_values(&[&MODEL_SPECS[self.model_ dx()]])
                                .observe(
                                    start.elapsed().as_m cros() as f64
                                        / (*batch_ends.last().unwrap() as f64),
                                );
                            ( nputs, batch_ends)
                        }
                        _ => un mple nted!(),
                    }
                })
                .unz p();
            // nvar ant   only support one  nput as str ng. w ll relax later
            assert_eq!( nputs.len(), 1);
            let output_tensors = self
                .sess on
                .run( nputs. nto_ er().flatten().collect::<Vec<_>>())
                .unwrap();
            self.output_f lters
                . er()
                .map(|& dx| {
                    let mut s ze = 1us ze;
                    let output = output_tensors[ dx].try_extract::<f32>().unwrap();
                    for &d m  n self.sess on.outputs[ dx].d  ns ons. er().flatten() {
                        s ze *= d m as us ze;
                    }
                    let tensor_ends = batch_ends[0]
                        . er()
                        .map(|&batch| batch * s ze)
                        .collect::<Vec<_>>();

                    (
                        //only works for batch major
                        //TODO: to_vec() obv ously wasteful, espec ally for large batc s(GPU) . W ll refactor to
                        //break up output and return Vec<Vec<TensorScore>>  re
                        TensorReturnEnum::FloatTensorReturn(Box::new(output.v ew().as_sl ce().unwrap().to_vec(),
                        )),
                        tensor_ends,
                    )
                })
                .unz p()
        }
        #[ nl ne(always)]
        fn model_ dx(&self) -> us ze {
            self.model_ dx
        }
        #[ nl ne(always)]
        fn vers on(&self) ->  64 {
            self.vers on
        }
    }
}
