use anyhow::{anyhow, Result};
use arrayvec::ArrayVec;
use  ertools:: ertools;
use log::{error,  nfo};
use std::fmt::{Debug, D splay};
use std::str ng::Str ng;
use std::sync::Arc;
use std::t  ::Durat on;
use tok o::process::Command;
use tok o::sync::mpsc::error::TryRecvError;
use tok o::sync::mpsc::{Rece ver, Sender};
use tok o::sync::{mpsc, oneshot};
use tok o::t  ::{ nstant, sleep};
use warp::F lter;

use crate::batch::BatchPred ctor;
use crate::bootstrap::Tensor nput;
use crate::{MAX_NUM_MODELS, MAX_VERS ONS_PER_MODEL, META_ NFO,  tr cs, ModelFactory, Pred ct ssage, Pred ctResult, TensorReturnEnum, ut ls};

use crate::cl _args::{ARGS, MODEL_SPECS};
use crate::cores::val dator::val dat or::cl _val dator;
use crate:: tr cs::MPSC_CHANNEL_S ZE;
use serde_json::{self, Value};

pub tra  Model: Send + Sync + D splay + Debug + 'stat c {
    fn warmup(&self) -> Result<()>;
    //TODO: refactor t  to return vec<vec<TensorScores>>,  .e.
    //  have t  underly ng runt    mpl to spl  t  response to each cl ent.
    //  w ll el m nate so   neff c ent  mory copy  n onnx_model.rs as  ll as s mpl fy code
    fn do_pred ct(
        &self,
         nput_tensors: Vec<Vec<Tensor nput>>,
        total_len: u64,
    ) -> (Vec<TensorReturnEnum>, Vec<Vec<us ze>>);
    fn model_ dx(&self) -> us ze;
    fn vers on(&self) ->  64;
}

#[der ve(Debug)]
pub struct Pred ctServ ce<T: Model> {
    tx: Sender<Pred ct ssage<T>>,
}
 mpl<T: Model> Pred ctServ ce<T> {
    pub async fn  n (model_factory: ModelFactory<T>) -> Self {
        cl _val dator::val date_ps_model_args();
        let (tx, rx) = mpsc::channel(32_000);
        tok o::spawn(Pred ctServ ce::tf_queue_manager(rx));
        tok o::spawn(Pred ctServ ce::model_watc r_latest(
            model_factory,
            tx.clone(),
        ));
        let  tr cs_route = warp::path!(" tr cs").and_t n( tr cs:: tr cs_handler);
        let  tr c_server = warp::serve( tr cs_route).run(([0, 0, 0, 0], ARGS.pro t us_port));
        tok o::spawn( tr c_server);
        Pred ctServ ce { tx }
    }
    #[ nl ne(always)]
    pub async fn pred ct(
        &self,
         dx: us ze,
        vers on: Opt on< 64>,
        val: Vec<Tensor nput>,
        ts:  nstant,
    ) -> Result<Pred ctResult> {
        let (tx, rx) = oneshot::channel();
         f let Err(e) = self
            .tx
            .clone()
            .send(Pred ct ssage::Pred ct( dx, vers on, val, tx, ts))
            .awa 
        {
            error!("mpsc send error:{}", e);
            Err(anyhow!(e))
        } else {
            MPSC_CHANNEL_S ZE. nc();
            rx.awa .map_err(anyhow::Error::msg)
        }
    }

    async fn load_latest_model_from_model_d r(
        model_factory: ModelFactory<T>,
        model_conf g: &Value,
        tx: Sender<Pred ct ssage<T>>,
         dx: us ze,
        max_vers on: Str ng,
        latest_vers on: &mut Str ng,
    ) {
        match model_factory( dx, max_vers on.clone(), model_conf g) {
            Ok(tf_model) => tx
                .send(Pred ct ssage::UpsertModel(tf_model))
                .awa 
                .map_or_else(
                    |e| error!("send UpsertModel error: {}", e),
                    |_| *latest_vers on = max_vers on,
                ),
            Err(e) => {
                error!("sk p load ng model due to fa lure: {:?}", e);
            }
        }
    }

    async fn scan_load_latest_model_from_model_d r(
        model_factory: ModelFactory<T>,
        model_conf g: &Value,
        tx: Sender<Pred ct ssage<T>>,
        model_ dx: us ze,
        cur_vers on: &mut Str ng,
    ) -> Result<()> {
        let model_d r = &ARGS.model_d r[model_ dx];
        let next_vers on = ut ls::get_conf g_or_else(model_conf g, "vers on", || {
             nfo!("no vers on found,  nce use max vers on");
            std::fs::read_d r(model_d r)
                .map_err(|e| format!("read d r error:{}", e))
                .and_t n(|paths| {
                    paths
                        . nto_ er()
                        .flat_map(|p| {
                            p.map_err(|e| error!("d r entry error: {}", e))
                                .and_t n(|d r| {
                                    d r.f le_na ()
                                        . nto_str ng()
                                        .map_err(|e| error!("osstr ng error: {:?}", e))
                                })
                                .ok()
                        })
                        .f lter(|f| !f.to_lo rcase().conta ns(&META_ NFO.to_lo rcase()))
                        .max()
                        .ok_or_else(|| "no d r found  nce no max".to_owned())
                })
                .unwrap_or_else(|e| {
                    error!(
                        "can't get t  max vers on  nce return cur_vers on, error  s: {}",
                        e
                    );
                    cur_vers on.to_str ng()
                })
        });
        //as long as next vers on doesn't match cur vers on ma nta ned   reload
         f next_vers on.ne(cur_vers on) {
             nfo!("reload t  vers on: {}->{}", cur_vers on, next_vers on);
            Pred ctServ ce::load_latest_model_from_model_d r(
                model_factory,
                model_conf g,
                tx,
                model_ dx,
                next_vers on,
                cur_vers on,
            )
            .awa ;
        }
        Ok(())
    }

    async fn model_watc r_latest(model_factory: ModelFactory<T>, tx: Sender<Pred ct ssage<T>>) {
        async fn call_external_modelsync(cl : &str, cur_vers ons: &Vec<Str ng>) -> Result<()> {
            let mut args = cl .spl _wh espace();

            let mut cmd = Command::new(args.next().ok_or(anyhow!("model sync cl  empty"))?);
            let extr_args = MODEL_SPECS
                . er()
                .z p(cur_vers ons)
                .flat_map(|(spec, vers on)| vec!["--model-spec", spec, "--cur-vers on", vers on])
                .collect_vec();
             nfo!("run model sync: {} w h extra args: {:?}", cl , extr_args);
            let output = cmd.args(args).args(extr_args).output().awa ?;
             nfo!("model sync stdout:{}", Str ng::from_utf8(output.stdout)?);
             nfo!("model sync stderr:{}", Str ng::from_utf8(output.stderr)?);
             f output.status.success() {
                Ok(())
            } else {
                Err(anyhow!(
                    "model sync fa led w h status: {:?}!",
                    output.status
                ))
            }
        }
        let  ta_d r = ut ls::get_ ta_d r();
        let  ta_f le = format!("{}{}",  ta_d r, META_ NFO);
        // n  al ze t  latest vers on array
        let mut cur_vers ons = vec!["".to_owned(); MODEL_SPECS.len()];
        loop {
             nfo!("***poll ng for models***"); //n ce del m nter
             f let So (ref cl ) = ARGS.modelsync_cl  {
                 f let Err(e) = call_external_modelsync(cl , &cur_vers ons).awa  {
                    error!("model sync cl  runn ng error:{}", e)
                }
            }
            let conf g = ut ls::read_conf g(& ta_f le).unwrap_or_else(|e| {
                 nfo!("conf g f le {} not found due to: {}",  ta_f le, e);
                Value::Null
            });
             nfo!("conf g:{}", conf g);
            for ( dx, cur_vers on)  n cur_vers ons. er_mut().enu rate() {
                let model_d r = &ARGS.model_d r[ dx];
                Pred ctServ ce::scan_load_latest_model_from_model_d r(
                    model_factory,
                    &conf g[&MODEL_SPECS[ dx]],
                    tx.clone(),
                     dx,
                    cur_vers on,
                )
                .awa 
                .map_or_else(
                    |e| error!("scanned {}, error {:?}", model_d r, e),
                    |_|  nfo!("scanned {}, latest_vers on: {}", model_d r, cur_vers on),
                );
            }
            sleep(Durat on::from_secs(ARGS.model_c ck_ nterval_secs)).awa ;
        }
    }
    async fn tf_queue_manager(mut rx: Rece ver<Pred ct ssage<T>>) {
        // Start rece v ng  ssages
         nfo!("sett ng up queue manager");
        let max_batch_s ze = ARGS
            .max_batch_s ze
            . er()
            .map(|b| b.parse().unwrap())
            .collect::<Vec<us ze>>();
        let batch_t  _out_m ll s = ARGS
            .batch_t  _out_m ll s
            . er()
            .map(|b| b.parse().unwrap())
            .collect::<Vec<u64>>();
        let no_msg_wa _m ll s = *batch_t  _out_m ll s. er().m n().unwrap();
        let mut all_model_pred ctors: ArrayVec::<ArrayVec<BatchPred ctor<T>, MAX_VERS ONS_PER_MODEL>, MAX_NUM_MODELS> =
            (0 ..MAX_NUM_MODELS).map( |_| ArrayVec::<BatchPred ctor<T>, MAX_VERS ONS_PER_MODEL>::new()).collect();
        loop {
            let msg = rx.try_recv();
            let no_more_msg = match msg {
                Ok(Pred ct ssage::Pred ct(model_spec_at, vers on, val, resp, ts)) => {
                     f let So (model_pred ctors) = all_model_pred ctors.get_mut(model_spec_at) {
                         f model_pred ctors. s_empty() {
                            resp.send(Pred ctResult::ModelNotReady(model_spec_at))
                                .unwrap_or_else(|e| error!("cannot send back model not ready error: {:?}", e));
                        }
                        else {
                            match vers on {
                                None => model_pred ctors[0].push(val, resp, ts),
                                So (t _vers on) => match model_pred ctors
                                    . er_mut()
                                    .f nd(|x| x.model.vers on() == t _vers on)
                                {
                                    None => resp
                                        .send(Pred ctResult::ModelVers onNotFound(
                                            model_spec_at,
                                            t _vers on,
                                        ))
                                        .unwrap_or_else(|e| {
                                            error!("cannot send back vers on error: {:?}", e)
                                        }),
                                    So (pred ctor) => pred ctor.push(val, resp, ts),
                                },
                            }
                        }
                    } else {
                        resp.send(Pred ctResult::ModelNotFound(model_spec_at))
                            .unwrap_or_else(|e| error!("cannot send back model not found error: {:?}", e))
                    }
                    MPSC_CHANNEL_S ZE.dec();
                    false
                }
                Ok(Pred ct ssage::UpsertModel(tf_model)) => {
                    let  dx = tf_model.model_ dx();
                    let pred ctor = BatchPred ctor {
                        model: Arc::new(tf_model),
                         nput_tensors: Vec::w h_capac y(max_batch_s ze[ dx]),
                        callbacks: Vec::w h_capac y(max_batch_s ze[ dx]),
                        cur_batch_s ze: 0,
                        max_batch_s ze: max_batch_s ze[ dx],
                        batch_t  _out_m ll s: batch_t  _out_m ll s[ dx],
                        // n  al ze to be current t  
                        queue_reset_ts:  nstant::now(),
                        queue_earl est_rq_ts:  nstant::now(),
                    };
                    assert!( dx < all_model_pred ctors.len());
                     tr cs::NEW_MODEL_SNAPSHOT
                        .w h_label_values(&[&MODEL_SPECS[ dx]])
                        . nc();

                    //  can do t  s nce t  vector  s small
                    let pred ctors = &mut all_model_pred ctors[ dx];
                     f pred ctors.len() == 0 {
                         nfo!("now   serve new model: {}", pred ctor.model);
                    }
                    else {
                         nfo!("now   serve updated model: {}", pred ctor.model);
                    }
                     f pred ctors.len() == ARGS.vers ons_per_model {
                        pred ctors.remove(pred ctors.len() - 1);
                    }
                    pred ctors. nsert(0, pred ctor);
                    false
                }
                Err(TryRecvError::Empty) => true,
                Err(TryRecvError::D sconnected) => true,
            };
            for pred ctor  n all_model_pred ctors. er_mut().flatten() {
                // f pred ctor batch queue not empty and t  s out or no more msg  n t  queue, flush
                 f (!pred ctor. nput_tensors. s_empty() && (pred ctor.durat on_past(pred ctor.batch_t  _out_m ll s) || no_more_msg))
                    // f batch queue reac s l m , flush
                    || pred ctor.cur_batch_s ze >= pred ctor.max_batch_s ze
                {
                    pred ctor.batch_pred ct();
                }
            }
             f no_more_msg {
                sleep(Durat on::from_m ll s(no_msg_wa _m ll s)).awa ;
            }
        }
    }
    #[ nl ne(always)]
    pub fn get_model_ ndex(model_spec: &str) -> Opt on<us ze> {
        MODEL_SPECS. er().pos  on(|m| m == model_spec)
    }
}
