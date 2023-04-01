require "faker"

FactoryBot.define do
  factory :favorite_tag do
    tag_id { FactoryBot.create(:canonical_freeform).id }
    user_id { FactoryBot.create(:user).id }
  end
end
