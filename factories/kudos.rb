FactoryBot.define do
  factory :kudo do
    commentable { create(:work) }
    ip_address { Faker::Internet.unique.public_ip_v4_address }
  end
end
