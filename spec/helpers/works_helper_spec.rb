require 'spec_helper'

describe WorksHelper do
  describe '#all_coauthor_skins' do
    before do
      @users = Array.new(5) { FactoryBot.create(:user) }
      @work = create(:work, authors: @users.flat_map(&:pseuds))
    end

    context 'no public work skins or private work skins' do
      it 'returns an empty array' do
        expect(helper.all_coauthor_skins).to be_empty
      end
    end

    context 'public work skins exist' do
      before do
        create(:work_skin, :public, title: "Z Public Skin")
        create(:work_skin, :public, title: "B Public Skin")
      end

      context 'no private work skins' do
        it 'returns public work skins, ordered by title' do
          expect(helper.all_coauthor_skins.pluck(:title)).to eq(['B Public Skin', 'Z Public Skin'])
        end
      end

      context 'private work skins exist' do
        before do
          create(:work_skin, :private, title: "A Private Skin", author: @users[3])
          create(:work_skin, :private, title: "M Private Skin", author: @users[0])
          create(:work_skin, :private, title: "Unowned Private Skin")
        end

        it 'returns public work skins and skins belonging to allpseuds, ordered by title' do
          expect(helper.all_coauthor_skins.pluck(:title)).to eq(['A Private Skin',
                                                                 'B Public Skin',
                                                                 'M Private Skin',
                                                                 'Z Public Skin'])
        end

        it 'does not return unassociated private work skins' do
          expect(helper.all_coauthor_skins.pluck(:title)).not_to include(['Unowned Private Skin'])
        end
      end
    end
  end

  describe '#sorted_languages' do
    it 'returns all languages sorted alphabetically' do
      # only english language exists
      expect(Language.count).to eq(1)
      english = Language.find_by(short: 'en', sortable_name: '')

      # create 3 new languages
      german = Language.create(name: 'Deutsch', short: 'de', sortable_name: '')
      finnish = Language.create(name: 'Suomi', short: 'fi', sortable_name: 'su')
      indonesian = Language.create(name: 'Bahasa Indonesia', short: 'id', sortable_name: 'ba')

      # sort them
      expect(sorted_languages).to eq([indonesian, german, english, finnish])
    end
  end
end
