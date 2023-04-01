require 'faker'

FactoryBot.define do
  factory :pseud do
    name { Faker::Lorem.characters(number: 8) }
    user
  end
end
