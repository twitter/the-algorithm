namespace :spam do

  desc "Print list of potential spammers"
  task(:print_possible => :environment) do
    SpamReport.run
  end
end
