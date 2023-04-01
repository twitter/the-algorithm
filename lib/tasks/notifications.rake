namespace :notifications do
  
  desc "Send next set of kudos notifications"
  task(:deliver_kudos => :environment) do
    RedisMailQueue.deliver_kudos
  end
  
  desc "Send next set of subscription notifications"
  task(:deliver_subscriptions => :environment) do
    RedisMailQueue.deliver_subscriptions
  end
    
end