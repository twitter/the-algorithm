// water_mist.c.inc
// TODO: there is confusion with the earlier mist file. Clarify?

void bhv_water_mist_2_loop(void) {
    o->oPosY = find_water_level(o->oHomeX, o->oHomeZ) + 20.0f;
    o->oPosX = o->oHomeX + random_f32_around_zero(150.0f);
    o->oPosZ = o->oHomeZ + random_f32_around_zero(150.0f);
    o->oOpacity = random_float() * 50.0f + 200.0f;
}
