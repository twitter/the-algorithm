#ifndef _THREAD_6_H
#define _THREAD_6_H

#ifdef VERSION_SH

extern s32 gRumblePakTimer;

extern void init_rumble_pak_scheduler_queue(void);
extern void block_until_rumble_pak_free(void);
extern void release_rumble_pak_control(void);
extern void queue_rumble_data(s16 a0, s16 a1);
extern void func_sh_8024C89C(s16 a0);
extern u8 is_rumble_finished_and_queue_empty(void);
extern void reset_rumble_timers(void);
extern void reset_rumble_timers_2(s32 a0);
extern void func_sh_8024CA04(void);
extern void cancel_rumble(void);
extern void create_thread_6(void);
extern void rumble_thread_update_vi(void);

#endif

#endif
