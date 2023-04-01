require 'spec_helper'

describe AdminPostHelper do
  describe '#sorted_translations' do
    it 'returns translations sorted alphabetically by language' do
      english = Language.find_by(short: 'en', sortable_name: '')
      german = Language.create(name: 'Deutsch', short: 'de', sortable_name: '')
      finnish = Language.create(name: 'Suomi', short: 'fi', sortable_name: 'su')
      indonesian = Language.create(name: 'Bahasa Indonesia', short: 'id', sortable_name: 'ba')

      english_post = create(:admin_post, language: english)
      german_post = create(:admin_post, language: german, translated_post: english_post)
      finnish_post = create(:admin_post, language: finnish, translated_post: english_post)
      indonesian_post = create(:admin_post, language: indonesian, translated_post: english_post)

      expect(sorted_translations(english_post.reload)).to eq([indonesian_post, german_post, finnish_post])
    end
  end
end
