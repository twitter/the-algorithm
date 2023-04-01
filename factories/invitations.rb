require "faker"

FactoryBot.define do
  factory :invite_request do
    sequence :email do |n|
      Faker::Internet.email(name: "#{Faker::Name.first_name}_#{n}")
    end
  end

  factory :invitation do
    invitee_email { "default@email.com" }
  end
end
