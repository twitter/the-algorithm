local algo = twitter.algo

while (algo:active()) do
    for _, user in pairs(algo.users) do
        local is_a_rightwing_dumbass = user:is_a_rightwing_dumbass()
        local is_elon = user:is_elon()

        if (is_elon) then
            user:shadow_ban()
        elseif (is_a_rightwing_dumbass) then
            user:shadowed_shadow_very_shadow_ban()
        else
            -- WIN!
            user:boost(20000)
        end
    end
end