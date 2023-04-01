require 'faker'

FactoryBot.define do

  factory :subscription do
    subscribable_type { "Series" }
    subscribable_id { FactoryBot.create(:series).id }
    user
  end
end
