require "faker"

FactoryBot.define do
  factory :known_issue do
    title { Faker::Lorem.sentence }
    content { Faker::Lorem.paragraph }
  end
end
