namespace :deploy do

  desc "Get servername"
  task(:get_servername) do
    @server ||= %x{hostname -s}.chomp
  end

  desc "clear subscriptions on stage"
  task(:clear_subscriptions => [:get_servername, :environment]) do
    if @server == "stage"
      Subscription.delete_all
    else
      puts "Don't clear subscriptions except on stage!!!"
    end 
  end
end
