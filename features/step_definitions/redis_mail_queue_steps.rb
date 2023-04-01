When /^subscription notifications are sent$/ do
  RedisMailQueue.deliver_subscriptions
end

When /^the subscription queue is cleared$/ do
  RedisMailQueue.clear_queue("subscription")
end

When /^kudos are sent$/ do
  RedisMailQueue.deliver_kudos
end

When /^the kudos queue is cleared$/ do
  RedisMailQueue.clear_queue("kudos")
end
