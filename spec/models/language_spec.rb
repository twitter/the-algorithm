require 'spec_helper'

describe Language do
  describe '.default_order' do
    it 'returns languages sorted alphabetically by sortable_name if present, short name if not' do
      german = Language.create(name: 'Deutsch', short: 'de', sortable_name: '')
      finnish = Language.create(name: 'Suomi', short: 'fi', sortable_name: 'su')
      indonesian = Language.create(name: 'Bahasa Indonesia', short: 'id', sortable_name: 'ba')
      languages = Language.where(id: [german.id, finnish.id, indonesian.id])
      expect(languages.default_order).to eq([indonesian, german, finnish])
    end
  end
end
