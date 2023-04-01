require 'spec_helper'

describe TagSetNominationsController do
  include LoginMacros
  include RedirectExpectationHelper

  let(:tag_set_nomination) { FactoryBot.create(:tag_set_nomination) }
  let(:owned_tag_set) { tag_set_nomination.owned_tag_set }

  let(:tag_nominator_pseud) { tag_set_nomination.pseud }
  let(:tag_nominator) { tag_nominator_pseud.user }
  let(:mod_pseud) {
    FactoryBot.create(:pseud).tap do |pseud|
      owned_tag_set.add_moderator(pseud)
      owned_tag_set.save!
    end
  }
  let(:moderator) { mod_pseud.user }

  let(:random_user) { FactoryBot.create(:user) }

  describe 'GET index' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        get :index, params: { user_id: moderator.login, tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'user is logged in' do
      before do
        fake_login_known_user(user)
      end

      context 'user_id param is truthy' do
        let(:user) { tag_nominator }

        context 'user_id does not match logged in user' do
          it 'redirects and returns an error message' do
            get :index, params: { user_id: 'invalid', tag_set_id: owned_tag_set.id }
            it_redirects_to_with_error(tag_sets_path, 'You can only view your own nominations, sorry.')
          end
        end

        context 'user_id matches logged in user' do
          before do
            get :index, params: { user_id: tag_nominator.login, tag_set_id: owned_tag_set.id }
          end

          it 'renders the index template' do
            expect(response).to render_template('index')
          end

          it 'returns expected tag set nominations' do
            expect(assigns(:tag_set_nominations)).to eq([tag_set_nomination])
          end
        end
      end

      context 'user_id param is falsey' do
        context 'tag set exists' do
          context 'logged in user is moderator' do
            let(:user) { moderator.reload }

            it 'renders the index template' do
              get :index, params: { tag_set_id: owned_tag_set.id }
              expect(response).to render_template('index')
            end

            context 'no unreviewed tag_nominations' do
              it 'returns a flash notice about no unreviewed nominations' do
                get :index, params: { tag_set_id: owned_tag_set.id }
                expect(flash[:notice]).to eq('No nominations to review!')
              end
            end

            context 'unreviewed tag_nominations exist' do
              it 'does not return a flash notice about no unreviewed nominations' do
                FandomNomination.create(tag_set_nomination: tag_set_nomination, tagname: 'New Fandom')
                get :index, params: { tag_set_id: owned_tag_set.id }
                expect(flash[:notice]).not_to eq('No nominations to review!')
              end

              context 'tag set freeform_nomination_limit is > 0' do
                before do
                  owned_tag_set.update_column(:freeform_nomination_limit, 1)
                end

                context 'unreviewed freeform_nominations' do
                  context 'unreviewed freeform nominations <= 30' do
                    before do
                      add_unreviewed_freeform_nominations(30)
                      get :index, params: { tag_set_id: owned_tag_set.id }
                    end

                    it 'returns all freeform nominations in order' do
                      expect(assigns(:nominations_count)[:freeform]).to eq(30)
                      expect(assigns(:nominations)[:freeform].count).to eq(30)
                      expect(assigns(:nominations)[:freeform].first.tagname).to eq('New Freeform 0')
                    end

                    it 'does not return a flash notice about too many nominations' do
                      expect(flash[:notice]).not_to eq("There are too many nominations to show at once, so here's a " \
                                                         "randomized selection! Additional nominations will appear " \
                                                         "after you approve or reject some.")
                    end
                  end

                  context 'unreviewed freeform nominations > 30' do
                    before do
                      add_unreviewed_freeform_nominations(31)
                      get :index, params: { tag_set_id: owned_tag_set.id }
                    end

                    it 'returns 30 freeform nominations' do
                      expect(assigns(:nominations_count)[:freeform]).to eq(31)
                      expect(assigns(:nominations)[:freeform].count).to eq(30)
                    end

                    it 'returns a flash notice about too many nominations' do
                      expect(flash[:notice]).to eq("There are too many nominations to show at once, so here's a " \
                                                     "randomized selection! Additional nominations will appear " \
                                                     "after you approve or reject some.")
                    end
                  end

                  def add_unreviewed_freeform_nominations(num)
                    num.times do |i|
                      FreeformNomination.create(tag_set_nomination: tag_set_nomination, tagname: "New Freeform #{i}")
                    end
                  end
                end

                context 'reviewed freeform_nominations' do
                  it 'does not return freeform nominations' do
                    freeform_nom = FreeformNomination.create(tag_set_nomination: tag_set_nomination,
                                                             tagname: 'New Freeform')
                    freeform_nom.update_column(:approved, true)
                    get :index, params: { tag_set_id: owned_tag_set.id }

                    expect(assigns(:nominations_count)[:freeform]).to eq(0)
                    expect(assigns(:nominations)[:freeform]).to be_empty
                  end
                end
              end

              context 'tag set freeform_nomination_limit is 0' do
                it 'does not return freeform nominations' do
                  owned_tag_set.update_column(:freeform_nomination_limit, 0)
                  FreeformNomination.create(tag_set_nomination: tag_set_nomination, tagname: 'New Freeform')

                  get :index, params: { tag_set_id: owned_tag_set.id }
                  expect(assigns(:nominations)[:freeform]).to be_nil
                end
              end

              context 'tag set fandom_nomination_limit is > 0' do
                before do
                  owned_tag_set.update_column(:fandom_nomination_limit, 1)
                  owned_tag_set.update_column(:character_nomination_limit, 1)
                  owned_tag_set.update_column(:relationship_nomination_limit, 1)

                  add_fandom_nominations(fandom_nom_num, fandom_nom_status)
                  CharacterNomination.create(tag_set_nomination: tag_set_nomination,
                                             fandom_nomination: FandomNomination.last, tagname: 'Unreviewed Character')
                  RelationshipNomination.create(tag_set_nomination: tag_set_nomination,
                                                fandom_nomination: FandomNomination.last, tagname: 'Unreviewed Relationship')
                end

                context 'unreviewed fandom nominations' do
                  let(:fandom_nom_status) { :unreviewed }

                  before do
                    get :index, params: { tag_set_id: owned_tag_set.id }
                  end

                  context 'unreviewed fandom nominations <= 30' do
                    let(:fandom_nom_num) { 30 }

                    it 'returns all fandom nominations in order' do
                      expect(assigns(:nominations_count)[:fandom]).to eq(30)
                      expect(assigns(:nominations)[:fandom].count).to eq(30)
                      expect(assigns(:nominations)[:fandom].first.tagname).to eq('New Fandom 0')
                    end

                    it 'does not return associated character and relationship nominations' do
                      expect(assigns(:nominations)[:cast]).to be_empty
                    end

                    it 'does not return a flash notice about too many nominations' do
                      expect(flash[:notice]).not_to eq("There are too many nominations to show at once, so here's a " \
                                                         "randomized selection! Additional nominations will appear " \
                                                         "after you approve or reject some.")
                    end
                  end

                  context 'unreviewed fandom nominations > 30' do
                    let(:fandom_nom_num) { 31 }

                    it 'returns 30 fandom nominations' do
                      expect(assigns(:nominations_count)[:fandom]).to eq(31)
                      expect(assigns(:nominations)[:fandom].count).to eq(30)
                    end

                    it 'returns a flash notice about too many nominations' do
                      expect(flash[:notice]).to eq("There are too many nominations to show at once, so here's a " \
                                                     "randomized selection! Additional nominations will appear " \
                                                     "after you approve or reject some.")
                    end
                  end
                end

                context 'rejected fandom nominations' do
                  let(:fandom_nom_num) { 1 }
                  let(:fandom_nom_status) { :rejected }

                  before do
                    get :index, params: { tag_set_id: owned_tag_set.id }
                  end

                  it 'does not return fandom nominations' do
                    expect(assigns(:nominations)[:fandom]).to be_empty
                  end

                  it 'does not return associated character and relationship nominations' do
                    expect(assigns(:nominations)[:cast]).to be_empty
                  end
                end

                context 'approved fandom nominations' do
                  let(:fandom_nom_num) { 1 }
                  let(:fandom_nom_status) { :approved }

                  it 'does not return fandom nominations' do
                    get :index, params: { tag_set_id: owned_tag_set.id }
                    expect(assigns(:nominations)[:fandom]).to be_empty
                  end

                  it 'does not return associated reviewed character and relationship nominations' do
                    approved_character_nom = CharacterNomination.create(tag_set_nomination: tag_set_nomination,
                                                                        fandom_nomination: FandomNomination.last,
                                                                        tagname: 'Approved Character')
                    approved_character_nom.update_column(:approved, true)
                    approved_relationship_nom = RelationshipNomination.create(tag_set_nomination: tag_set_nomination,
                                                                              fandom_nomination: FandomNomination.last,
                                                                              tagname: 'Approved Relationship')
                    approved_relationship_nom.update_column(:approved, true)
                    get :index, params: { tag_set_id: owned_tag_set.id }

                    expect(assigns(:nominations)[:cast]).not_to include(approved_character_nom)
                    expect(assigns(:nominations)[:cast]).not_to include(approved_relationship_nom)
                  end

                  context 'character_ and relationship_nomination_limit are > 0' do
                    it 'returns associated unreviewed character and relationship nominations' do
                      expect(owned_tag_set.character_nomination_limit).to be > 0
                      expect(owned_tag_set.relationship_nomination_limit).to be > 0

                      get :index, params: { tag_set_id: owned_tag_set.id }
                      expect(assigns(:nominations)[:cast].map(&:tagname)).to eq(['Unreviewed Character',
                                                                                 'Unreviewed Relationship'])
                    end
                  end

                  context 'either character_ or relationship_nomination_limit is > 0' do
                    it 'returns associated unreviewed character and relationship nominations' do
                      owned_tag_set.update_column(:character_nomination_limit, 0)
                      expect(owned_tag_set.relationship_nomination_limit).to be > 0

                      get :index, params: { tag_set_id: owned_tag_set.id }
                      expect(assigns(:nominations)[:cast].map(&:tagname)).to eq(['Unreviewed Character',
                                                                                 'Unreviewed Relationship'])
                    end
                  end

                  context 'character_ and relationship_nomination_limit are 0' do
                    it 'does not return associated character and relationship nominations' do
                      owned_tag_set.update_column(:character_nomination_limit, 0)
                      owned_tag_set.update_column(:relationship_nomination_limit, 0)

                      get :index, params: { tag_set_id: owned_tag_set.id }
                      expect(assigns(:nominations)[:cast]).to be_nil
                    end
                  end
                end

                def add_fandom_nominations(num, status)
                  num.times do |i|
                    fandom_nom = FandomNomination.create(tag_set_nomination: tag_set_nomination, tagname: "New Fandom #{i}")
                    fandom_nom.update_column(status, true) if status != :unreviewed
                  end
                end
              end

              context 'tag set fandom_nomination_limit is 0' do
                let(:fandom_nom) { FandomNomination.create(tag_set_nomination: tag_set_nomination, tagname: 'New Fandom') }

                before do
                  owned_tag_set.update_column(:fandom_nomination_limit, 0)
                end

                context 'character_ and relationship_nomination_limit are > 0' do
                  before do
                    owned_tag_set.update_column(:character_nomination_limit, 1)
                    owned_tag_set.update_column(:relationship_nomination_limit, 1)
                  end

                  context 'unreviewed character and relationship nominations <= 30' do
                    before do
                      add_unreviewed_character_nominations(30)
                      add_unreviewed_relationship_nominations(30)
                      get :index, params: { tag_set_id: owned_tag_set.id }
                    end

                    it 'returns all ordered character and relationship nominations' do
                      expect(assigns(:nominations_count)[:character]).to eq(30)
                      expect(assigns(:nominations)[:character].count).to eq(30)
                      expect(assigns(:nominations)[:character].first.tagname).to eq('New Character 0')
                      expect(assigns(:nominations_count)[:relationship]).to eq(30)
                      expect(assigns(:nominations)[:relationship].count).to eq(30)
                      expect(assigns(:nominations)[:relationship].first.tagname).to eq('New Relationship 0')
                    end

                    it 'does not return a flash notice about too many nominations' do
                      expect(flash[:notice]).not_to eq("There are too many nominations to show at once, so here's a " \
                                                         "randomized selection! Additional nominations will appear " \
                                                         "after you approve or reject some.")
                    end
                  end

                  context 'unreviewed character or relationship nominations > 30' do
                    before do
                      add_unreviewed_character_nominations(1)
                      add_unreviewed_relationship_nominations(31)
                      get :index, params: { tag_set_id: owned_tag_set.id }
                    end

                    it 'returns 30 character and relationship nominations' do
                      expect(assigns(:nominations_count)[:character]).to eq(1)
                      expect(assigns(:nominations)[:character].count).to eq(1)
                      expect(assigns(:nominations_count)[:relationship]).to eq(31)
                      expect(assigns(:nominations)[:relationship].count).to eq(30)
                    end

                    it 'returns a flash notice about too many nominations' do
                      expect(flash[:notice]).to eq("There are too many nominations to show at once, so here's a " \
                                                     "randomized selection! Additional nominations will appear " \
                                                     "after you approve or reject some.")
                    end
                  end
                end

                context 'character_nomination_limit is 0' do
                  it 'does not return character nominations' do
                    add_unreviewed_character_nominations(1)
                    owned_tag_set.update_column(:character_nomination_limit, 0)
                    get :index, params: { tag_set_id: owned_tag_set.id }
                    expect(assigns(:nominations)[:character]).to be_nil
                  end
                end

                context 'relationship_nomination_limit is 0' do
                  it 'does not return relationship nominations' do
                    add_unreviewed_relationship_nominations(1)
                    owned_tag_set.update_column(:relationship_nomination_limit, 0)
                    get :index, params: { tag_set_id: owned_tag_set.id }
                    expect(assigns(:nominations)[:relationship]).to be_nil
                  end
                end

                def add_unreviewed_character_nominations(num)
                  num.times do |i|
                    CharacterNomination.create(tag_set_nomination: tag_set_nomination, fandom_nomination: fandom_nom,
                                               tagname: "New Character #{i}")
                  end
                end

                def add_unreviewed_relationship_nominations(num)
                  num.times do |i|
                    RelationshipNomination.create(tag_set_nomination: tag_set_nomination, fandom_nomination: fandom_nom,
                                                  tagname: "New Relationship #{i}")
                  end
                end
              end
            end
          end

          context 'logged in user is not moderator' do
            let(:user) { random_user }

            it 'redirects and returns an error message' do
              get :index, params: { tag_set_id: owned_tag_set.id }
              it_redirects_to_with_error(tag_sets_path, "You can't see those nominations, sorry.")
            end
          end
        end

        context 'no tag set' do
          let(:user) { random_user }

          it 'redirects and returns an error message' do
            get :index, params: { tag_set_id: 0 }
            it_redirects_to_with_error(tag_sets_path, 'What nominations did you want to work with?')
          end
        end
      end
    end
  end

  describe 'GET show' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        get :show, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'user is logged in' do
      context 'invalid params' do
        before do
          fake_login_known_user(tag_nominator)
        end

        context 'no tag set' do
          it 'redirects and returns an error message' do
            get :show, params: { id: tag_set_nomination.id, tag_set_id: 0 }
            it_redirects_to_with_error(tag_sets_path, 'What tag set did you want to nominate for?')
          end
        end

        context 'no tag set nomination' do
          it 'redirects and returns an error message' do
            get :show, params: { id: 0, tag_set_id: owned_tag_set.id }
            it_redirects_to_with_error(tag_set_path(owned_tag_set), 'Which nominations did you want to work with?')
          end
        end
      end

      context 'valid params' do
        context 'user is not associated with nomination' do
          before do
            fake_login_known_user(random_user)
          end

          it 'redirects and returns an error message' do
            get :show, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
            it_redirects_to_with_error(tag_set_path(owned_tag_set),
                                       'You can only see your own nominations or nominations for a set you moderate.')
          end
        end

        context 'user is author of nomination' do
          before do
            fake_login_known_user(tag_nominator.reload)
          end

          it 'renders the show template' do
            get :show, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
            expect(response).to render_template('show')
          end
        end

        context 'user is moderator of tag set' do
          before do
            fake_login_known_user(moderator.reload)
          end

          it 'renders the show template' do
            get :show, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
            expect(response).to render_template('show')
          end

          context "when the nomination is from a different tag set" do
            let(:owned_tag_set) { create(:owned_tag_set) }

            it "redirects and returns an error message" do
              get :show, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
              it_redirects_to_with_error(tag_set_path(owned_tag_set),
                                         "Which nominations did you want to work with?")
            end
          end
        end
      end
    end
  end

  describe 'GET new' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        get :new, params: { tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'user is logged in' do
      context 'invalid params' do
        context 'no tag set' do
          it 'redirects and returns an error message' do
            fake_login_known_user(random_user)
            get :new, params: { tag_set_id: 0 }
            it_redirects_to_with_error(tag_sets_path, 'What tag set did you want to nominate for?')
          end
        end
      end

      context 'valid params' do
        context 'user already has nominated tags for tag set' do
          before do
            fake_login_known_user(tag_nominator)
            get :new, params: { tag_set_id: owned_tag_set.id }
          end

          it 'redirects to edit page' do
            it_redirects_to(edit_tag_set_nomination_path(owned_tag_set, tag_set_nomination))
          end

          it 'does not build a new tag set nomination' do
            expect(assigns(:tag_set_nomination).new_record?).to be_falsey
            expect(assigns(:tag_set_nomination)).to eq(tag_set_nomination)
          end
        end

        context 'user has not yet nominated tags for tag set' do
          before do
            fake_login_known_user(random_user)
          end

          it 'renders the new template' do
            get :new, params: { tag_set_id: owned_tag_set.id }
            expect(response).to render_template('new')
          end

          it 'builds a new tag set nomination' do
            get :new, params: { tag_set_id: owned_tag_set.id }
            expect(assigns(:tag_set_nomination).new_record?).to be_truthy
            expect(assigns(:tag_set_nomination).pseud).to eq(random_user.default_pseud)
            expect(assigns(:tag_set_nomination).owned_tag_set).to eq(owned_tag_set)
          end

          it 'builds new freeform nominations until freeform_nomination_limit' do
            owned_tag_set.update_column(:freeform_nomination_limit, 3)
            get :new, params: { tag_set_id: owned_tag_set.id }
            expect(assigns(:tag_set_nomination).freeform_nominations.size).to eq(3)
          end

          context 'fandom_nomination_limit is > 0' do
            before do
              owned_tag_set.update_column(:fandom_nomination_limit, 2)
              owned_tag_set.update_column(:character_nomination_limit, 3)
              owned_tag_set.update_column(:relationship_nomination_limit, 1)
              get :new, params: { tag_set_id: owned_tag_set.id }
            end

            it 'builds new fandom nominations until fandom_nomination_limit' do
              expect(assigns(:tag_set_nomination).fandom_nominations.size).to eq(2)
            end

            it 'builds new character nominations for each fandom nomination' do
              assigns(:tag_set_nomination).fandom_nominations.each do |fandom_nom|
                expect(fandom_nom.character_nominations.size).to eq(3)
              end
            end

            it 'builds new relationship nominations for each fandom nomination' do
              assigns(:tag_set_nomination).fandom_nominations.each do |fandom_nom|
                expect(fandom_nom.relationship_nominations.size).to eq(1)
              end
            end
          end

          context 'fandom_nomination_limit is 0' do
            before do
              owned_tag_set.update_column(:fandom_nomination_limit, 0)
              owned_tag_set.update_column(:character_nomination_limit, 2)
              owned_tag_set.update_column(:relationship_nomination_limit, 3)
              get :new, params: { tag_set_id: owned_tag_set.id }
            end

            it 'does not build new fandom nominations' do
              expect(assigns(:tag_set_nomination).fandom_nominations.size).to eq(0)
            end

            it 'builds new character nominations until character_nomination_limit' do
              expect(assigns(:tag_set_nomination).character_nominations.size).to eq(2)
            end

            it 'builds new relationship nominations until relationship_nomination_limit' do
              expect(assigns(:tag_set_nomination).relationship_nominations.size).to eq(3)
            end
          end
        end
      end
    end
  end

  describe 'GET edit' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        get :edit, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'user is logged in' do
      context 'invalid params' do
        before do
          fake_login_known_user(tag_nominator)
        end

        context 'no tag set' do
          it 'redirects and returns an error message' do
            get :edit, params: { id: tag_set_nomination.id, tag_set_id: 0 }
            it_redirects_to_with_error(tag_sets_path, 'What tag set did you want to nominate for?')
          end
        end

        context 'no tag set nomination' do
          it 'redirects and returns an error message' do
            get :edit, params: { id: 0, tag_set_id: owned_tag_set.id }
            it_redirects_to_with_error(tag_set_path(owned_tag_set), 'Which nominations did you want to work with?')
          end
        end
      end

      context 'valid params' do
        let(:fandom_nom) { FandomNomination.create(tag_set_nomination: tag_set_nomination, tagname: 'New Fandom') }

        context 'user is not associated with nomination' do
          before do
            fake_login_known_user(random_user)
          end

          it 'redirects and returns an error message' do
            get :edit, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
            it_redirects_to_with_error(tag_set_path(owned_tag_set),
                                       'You can only see your own nominations or nominations for a set you moderate.')
          end
        end

        context 'user is author of nomination' do
          before do
            fake_login_known_user(tag_nominator.reload)
            owned_tag_set.update_column(:fandom_nomination_limit, 1)
            owned_tag_set.update_column(:character_nomination_limit, 3)
            owned_tag_set.update_column(:relationship_nomination_limit, 2)
            owned_tag_set.update_column(:freeform_nomination_limit, 4)
          end

          it 'renders the edit template' do
            get :edit, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
            expect(response).to render_template('edit')
          end

          context 'number of tag nominations matches limits specified on tag set' do
            before do
              add_character_nominations(owned_tag_set.character_nomination_limit)
              add_relationship_nominations(owned_tag_set.relationship_nomination_limit)
              add_freeform_nominations(owned_tag_set.freeform_nomination_limit)

              get :edit, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
            end

            it 'returns only existing associated tag nominations' do
              expect(assigns(:tag_set_nomination).fandom_nominations.map(&:tagname)).to eq(['New Fandom'])
              expect(assigns(:tag_set_nomination).fandom_nominations[0].character_nominations.map(&:tagname)).
                to eq(['New Character 0', 'New Character 1', 'New Character 2'])
              expect(assigns(:tag_set_nomination).fandom_nominations[0].relationship_nominations.map(&:tagname)).
                to eq(['New Relationship 0', 'New Relationship 1'])
              expect(assigns(:tag_set_nomination).freeform_nominations.map(&:tagname)).
                to eq(['New Freeform 0', 'New Freeform 1', 'New Freeform 2', 'New Freeform 3'])
            end
          end

          context 'fewer tag nominations than limit specified on tag set' do
            before do
              add_character_nominations(1)
              add_relationship_nominations(1)
              add_freeform_nominations(1)

              get :edit, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
            end

            it 'returns existing associated tag nominations' do
              expect(assigns(:tag_set_nomination).fandom_nominations.map(&:tagname)).to eq(['New Fandom'])
              expect(assigns(:tag_set_nomination).fandom_nominations[0].character_nominations.map(&:tagname)).
                to include('New Character 0')
              expect(assigns(:tag_set_nomination).fandom_nominations[0].relationship_nominations.map(&:tagname)).
                to include('New Relationship 0')
              expect(assigns(:tag_set_nomination).freeform_nominations.map(&:tagname)).to include('New Freeform 0')
            end

            it 'builds new tag nominations until _nomination_limit is reached' do
              expect(assigns(:tag_set_nomination).fandom_nominations.size).to eq(1)
              expect(assigns(:tag_set_nomination).fandom_nominations[0].character_nominations.size).to eq(3)
              expect(assigns(:tag_set_nomination).fandom_nominations[0].relationship_nominations.size).to eq(2)
              expect(assigns(:tag_set_nomination).freeform_nominations.size).to eq(4)
            end
          end
        end

        context 'user is moderator of tag set' do
          before do
            fake_login_known_user(moderator.reload)
            owned_tag_set.update_column(:fandom_nomination_limit, 1)
            add_character_nominations(1)
            add_relationship_nominations(1)
            add_freeform_nominations(1)

            get :edit, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
          end

          it 'renders the edit template' do
            expect(response).to render_template('edit')
          end

          it 'returns associated tag nominations' do
            expect(assigns(:tag_set_nomination).fandom_nominations.map(&:tagname)).to eq(['New Fandom'])
            expect(assigns(:tag_set_nomination).fandom_nominations[0].character_nominations.map(&:tagname)).
              to eq(['New Character 0'])
            expect(assigns(:tag_set_nomination).fandom_nominations[0].relationship_nominations.map(&:tagname)).
              to eq(['New Relationship 0'])
            expect(assigns(:tag_set_nomination).freeform_nominations.map(&:tagname)).to eq(['New Freeform 0'])
          end

          context "when the nomination is from a different tag set" do
            let(:owned_tag_set) { create(:owned_tag_set) }

            it "redirects and returns an error message" do
              it_redirects_to_with_error(tag_set_path(owned_tag_set),
                                         "Which nominations did you want to work with?")
            end
          end
        end

        def add_character_nominations(num)
          num.times do |i|
            CharacterNomination.create(tag_set_nomination: tag_set_nomination, fandom_nomination: fandom_nom,
                                       tagname: "New Character #{i}")
          end
        end

        def add_relationship_nominations(num)
          num.times do |i|
            RelationshipNomination.create(tag_set_nomination: tag_set_nomination, fandom_nomination: fandom_nom,
                                          tagname: "New Relationship #{i}")
          end
        end

        def add_freeform_nominations(num)
          num.times do |i|
            FreeformNomination.create(tag_set_nomination: tag_set_nomination, tagname: "New Freeform #{i}")
          end
        end
      end
    end
  end

  describe 'POST create' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        post :create, params: { tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'user is logged in' do
      context 'invalid params' do
        before do
          fake_login_known_user(random_user)
        end

        context 'no tag set' do
          it 'redirects and returns an error message' do
            post :create, params: { tag_set_id: 0, tag_set_nomination: { pseud_id: 0 } }
            it_redirects_to_with_error(tag_sets_path, 'What tag set did you want to nominate for?')
          end
        end

        context 'pseud_id param does not match user' do
          it 'redirects and returns an error message' do
            post :create, params: { tag_set_id: owned_tag_set.id, tag_set_nomination: { pseud_id: tag_nominator.default_pseud.id } }
            it_redirects_to_with_error(root_path, "You can't nominate tags with that pseud.")
          end
        end
      end

      context 'valid params' do
        before do
          fake_login_known_user(random_user)
        end

        context "success when fandom_nomination_limit is 0" do
          let(:owned_tag_set) do
            create(:owned_tag_set,
                   fandom_nomination_limit: 0,
                   character_nomination_limit: 1,
                   relationship_nomination_limit: 1)
          end

          let(:new_nomination) { owned_tag_set.tag_set_nominations.first }

          before do
            post :create, params: {
                 tag_set_id: owned_tag_set.id,
                 tag_set_nomination: {
                   pseud_id: random_user.default_pseud.id,
                   character_nominations_attributes: {
                     "0": {
                       tagname: "Character A",
                       parent_tagname: "Fandom A"
                     }
                   },
                   relationship_nominations_attributes: {
                     "0": {
                       tagname: "Characters B & C",
                       parent_tagname: "Fandom B"
                     }
                   }
                 }
                }

            owned_tag_set.reload
          end

          it "creates the new tag set nomination" do
            expect(owned_tag_set.tag_set_nominations.count).to eq 1
            expect(new_nomination).not_to eq nil
            expect(new_nomination.pseud).to eq random_user.default_pseud
            expect(new_nomination.owned_tag_set).to eq owned_tag_set
          end

          it "creates the character and relationship nominations" do
            expect(owned_tag_set.character_nominations.count).to eq 1
            expect(new_nomination.character_nominations.count).to eq 1
            character_nom = new_nomination.character_nominations.first
            expect(character_nom.tagname).to eq "Character A"
            expect(character_nom.parent_tagname).to eq "Fandom A"

            expect(owned_tag_set.relationship_nominations.count).to eq 1
            expect(new_nomination.relationship_nominations.count).to eq 1
            relationship_nom = new_nomination.relationship_nominations.first
            expect(relationship_nom.tagname).to eq "Characters B & C"
            expect(relationship_nom.parent_tagname).to eq "Fandom B"
          end

          it "does not create a fandom nomination" do
            expect(owned_tag_set.fandom_nominations.count).to eq 0
            expect(new_nomination.fandom_nominations.count).to eq 0
          end

          it "redirects and returns a success message" do
            it_redirects_to_with_notice(
              tag_set_nomination_path(owned_tag_set, new_nomination),
              "Your nominations were successfully submitted."
            )
          end
        end

        context "success when fandom_nomination_limit > 0" do
          before do
            owned_tag_set.update_column(:character_nomination_limit, 1)
            post :create, params: {
                 tag_set_id: owned_tag_set.id,
                 tag_set_nomination: { pseud_id: random_user.default_pseud.id,
                                       fandom_nominations_attributes: {
                                         '0': { tagname: 'New Fandom',
                                                character_nominations_attributes: {
                                                  '0': { tagname: 'New Character',
                                                         from_fandom_nomination: true }
                                                } }
                                       } }
            }
          end

          it 'creates a new tag set nomination' do
            new_tag_set_nomination = TagSetNomination.last
            expect(assigns(:tag_set_nomination)).to eq(new_tag_set_nomination)
            expect(new_tag_set_nomination.pseud).to eq(random_user.default_pseud)
            expect(new_tag_set_nomination.owned_tag_set).to eq(owned_tag_set)
          end

          it 'creates associated tag nominations' do
            new_fandom_nomination = FandomNomination.last
            new_character_nomination = CharacterNomination.last
            expect(assigns(:tag_set_nomination).fandom_nominations).to eq([new_fandom_nomination])
            expect(new_fandom_nomination.tagname).to eq('New Fandom')
            expect(new_fandom_nomination.character_nominations).to eq([new_character_nomination])
            expect(new_character_nomination.tagname).to eq('New Character')
          end

          it 'redirects and returns a success message' do
            it_redirects_to_with_notice(tag_set_nomination_path(owned_tag_set, TagSetNomination.last),
                                        'Your nominations were successfully submitted.')
          end
        end

        context 'tag set nomination save fails' do
          before do
            owned_tag_set.update_column(:nominated, false)
          end

          it 'renders the new template' do
            post :create, params: { tag_set_id: owned_tag_set.id, tag_set_nomination: { pseud_id: random_user.default_pseud.id } }
            expect(response).to render_template('new')
          end

          it 'builds a new tag set nomination' do
            expect { post :create, params: { tag_set_id: owned_tag_set.id, tag_set_nomination: { pseud_id: random_user.default_pseud.id } } }
              .not_to change { owned_tag_set.tag_set_nominations.count }
            expect(assigns(:tag_set_nomination).new_record?).to be_truthy
            expect(assigns(:tag_set_nomination).pseud).to eq(random_user.default_pseud)
            expect(assigns(:tag_set_nomination).owned_tag_set).to eq(owned_tag_set)
          end

          it 'builds new tag nominations until limits' do
            owned_tag_set.update_column(:fandom_nomination_limit, 1)
            owned_tag_set.update_column(:character_nomination_limit, 2)
            owned_tag_set.update_column(:relationship_nomination_limit, 3)
            owned_tag_set.update_column(:freeform_nomination_limit, 1)
            post :create, params: { tag_set_id: owned_tag_set.id, tag_set_nomination: { pseud_id: random_user.default_pseud.id } }

            expect(assigns(:tag_set_nomination).fandom_nominations.size).to eq(1)
            expect(assigns(:tag_set_nomination).fandom_nominations[0].character_nominations.size).to eq(2)
            expect(assigns(:tag_set_nomination).fandom_nominations[0].relationship_nominations.size).to eq(3)
            expect(assigns(:tag_set_nomination).freeform_nominations.size).to eq(1)
          end
        end
      end
    end
  end

  describe 'PUT update' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        put :update, params: { tag_set_id: owned_tag_set.id, id: tag_set_nomination.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'user is logged in' do
      context 'invalid params' do
        before do
          fake_login_known_user(tag_nominator)
        end

        context 'no tag set' do
          it 'redirects and returns an error message' do
            put :update, params: { id: tag_set_nomination.id, tag_set_id: 0, tag_set_nomination: { pseud_id: 0 } }
            it_redirects_to_with_error(tag_sets_path, 'What tag set did you want to nominate for?')
          end
        end

        context 'pseud_id param does not match user' do
          it 'redirects and returns an error message' do
            put :update, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id,
                tag_set_nomination: { pseud_id: random_user.default_pseud.id } }
            it_redirects_to_with_error(root_path, "You can't nominate tags with that pseud.")
          end
        end

        context 'no tag set nomination' do
          it 'redirects and returns an error message' do
            put :update, params: { id: 0, tag_set_id: owned_tag_set.id, tag_set_nomination: { pseud_id: nil } }
            it_redirects_to_with_error(tag_set_path(owned_tag_set), 'Which nominations did you want to work with?')
          end
        end
      end

      context 'valid params' do
        let(:fandom_nom) { FandomNomination.create(tag_set_nomination: tag_set_nomination, tagname: 'New Fandom') }

        context 'user is not associated with nomination' do
          before do
            fake_login_known_user(random_user)
          end

          it 'redirects and returns an error message' do
            put :update, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id, tag_set_nomination: { pseud_id: nil } }
            it_redirects_to_with_error(tag_set_path(owned_tag_set),
                                       'You can only see your own nominations or nominations for a set you moderate.')
          end
        end

        context 'user is author of nomination' do
          before do
            fake_login_known_user(tag_nominator.reload)
          end

          context "success when fandom_nomination_limit is 0" do
            let(:owned_tag_set) do
              create(:owned_tag_set,
                     fandom_nomination_limit: 0,
                     character_nomination_limit: 1,
                     relationship_nomination_limit: 1)
            end

            let(:tag_set_nom) do
              TagSetNomination.create(owned_tag_set: owned_tag_set,
                                      pseud: random_user.default_pseud)
            end

            let(:character_nom) do
              CharacterNomination.create(tag_set_nomination: tag_set_nom,
                                         tagname: "Character A",
                                         parent_tagname: "Fandom A")
            end

            let(:relationship_nom) do
              RelationshipNomination.create(tag_set_nomination: tag_set_nom,
                                            tagname: "Characters B & C",
                                            parent_tagname: "Fandom B")
            end

            before do
              fake_login_known_user(tag_set_nom.pseud.user)

              post :update, params: {
                   tag_set_id: owned_tag_set.id,
                   id: tag_set_nom.id,
                   tag_set_nomination: {
                     pseud_id: tag_set_nom.pseud.id,
                     character_nominations_attributes: {
                       "0": {
                         id: character_nom.id,
                         tagname: "Character D",
                         parent_tagname: "Fandom C"
                       }
                     },
                     relationship_nominations_attributes: {
                       "0": {
                         id: relationship_nom.id,
                         tagname: "Characters E & F",
                         parent_tagname: "Fandom D"
                       }
                     }
                   }
                 }

              owned_tag_set.reload
              tag_set_nom.reload
              character_nom.reload
              relationship_nom.reload
            end

            it "does not create new nominations" do
              expect(owned_tag_set.tag_set_nominations.count).to eq 1
              expect(owned_tag_set.fandom_nominations.count).to eq 0
              expect(owned_tag_set.character_nominations.count).to eq 1
              expect(owned_tag_set.relationship_nominations.count).to eq 1

              expect(tag_set_nom.fandom_nominations.count).to eq 0
              expect(tag_set_nom.character_nominations.count).to eq 1
              expect(tag_set_nom.relationship_nominations.count).to eq 1
            end

            it "modifies the character and relationship nominations" do
              expect(character_nom.tagname).to eq "Character D"
              expect(character_nom.parent_tagname).to eq "Fandom C"

              expect(relationship_nom.tagname).to eq "Characters E & F"
              expect(relationship_nom.parent_tagname).to eq "Fandom D"
            end

            it "redirects and returns a success message" do
              it_redirects_to_with_notice(
                tag_set_nomination_path(owned_tag_set, tag_set_nom),
                "Your nominations were successfully updated."
              )
            end
          end

          context "success when fandom_nomination_limit > 0" do
            let!(:character_nom) { CharacterNomination.create(tag_set_nomination: tag_set_nomination,
                                                              fandom_nomination: fandom_nom, tagname: 'New Character') }

            before do
              owned_tag_set.update_column(:character_nomination_limit, 1)
              put :update, params: {
                  tag_set_id: owned_tag_set.id,
                  id: tag_set_nomination.id,
                  tag_set_nomination: { pseud_id: tag_nominator_pseud.id,
                                        fandom_nominations_attributes: {
                                          '0': { tagname: 'Renamed Fandom',
                                                 id: fandom_nom.id,
                                                 character_nominations_attributes: {
                                                   '0': { tagname: 'Renamed Character',
                                                          id: character_nom.id,
                                                          from_fandom_nomination: true }
                                                 } }
                                        } }
                  }
            end

            it 'updates the tag set nomination and associated tag nominations' do
              expect(fandom_nom.reload.tagname).to eq('Renamed Fandom')
              expect(character_nom.reload.tagname).to eq('Renamed Character')
            end

            it 'redirects and returns a success message' do
              it_redirects_to_with_notice(tag_set_nomination_path(owned_tag_set, tag_set_nomination),
                                          'Your nominations were successfully updated.')
            end
          end

          context 'tag set nomination save fails' do
            before do
              owned_tag_set.update_column(:nominated, false)
              owned_tag_set.update_column(:fandom_nomination_limit, 1)
              owned_tag_set.update_column(:character_nomination_limit, 2)
              owned_tag_set.update_column(:relationship_nomination_limit, 3)
              owned_tag_set.update_column(:freeform_nomination_limit, 1)

              put :update, params: {
                  tag_set_id: owned_tag_set.id,
                  id: tag_set_nomination.id,
                  tag_set_nomination: { pseud_id: tag_nominator_pseud.id }
                }
            end

            it 'builds new tag nominations until limits' do
              expect(assigns(:tag_set_nomination).fandom_nominations.size).to eq(1)
              expect(assigns(:tag_set_nomination).fandom_nominations[0].character_nominations.size).to eq(2)
              expect(assigns(:tag_set_nomination).fandom_nominations[0].relationship_nominations.size).to eq(3)
              expect(assigns(:tag_set_nomination).freeform_nominations.size).to eq(1)
            end

            it 'renders the edit template' do
              expect(response).to render_template('edit')
            end
          end
        end

        context 'user is moderator of tag set' do
          let!(:character_nom) { CharacterNomination.create(tag_set_nomination: tag_set_nomination,
                                                            fandom_nomination: fandom_nom, tagname: 'New Character') }

          before do
            fake_login_known_user(moderator.reload)
            owned_tag_set.update_column(:character_nomination_limit, 1)
            put :update, params: {
                tag_set_id: owned_tag_set.id,
                id: tag_set_nomination.id,
                tag_set_nomination: { pseud_id: mod_pseud.id,
                                      fandom_nominations_attributes: {
                                        '0': { tagname: 'Renamed Fandom',
                                               id: fandom_nom.id,
                                               character_nominations_attributes: {
                                                 '0': { tagname: 'Renamed Character',
                                                        id: character_nom.id,
                                                        from_fandom_nomination: true }
                                               } }
                                      } }
            }
          end

          it 'updates the tag set nomination and associated tag nominations' do
            expect(fandom_nom.reload.tagname).to eq('Renamed Fandom')
            expect(character_nom.reload.tagname).to eq('Renamed Character')
          end

          it 'redirects and returns a success message' do
            it_redirects_to_with_notice(tag_set_nomination_path(owned_tag_set, tag_set_nomination),
                                        'Your nominations were successfully updated.')
          end

          context "when the nomination is from a different tag set" do
            let(:owned_tag_set) { create(:owned_tag_set) }

            it "redirects and returns an error message" do
              it_redirects_to_with_error(tag_set_path(owned_tag_set),
                                         "Which nominations did you want to work with?")
            end

            it "doesn't update the nominations" do
              expect(fandom_nom.reload.tagname).not_to eq("Renamed Fandom")
              expect(character_nom.reload.tagname).not_to eq("Renamed Character")
            end
          end
        end
      end
    end
  end

  describe 'DELETE destroy' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'user is logged in' do
      context 'invalid params' do
        before do
          fake_login_known_user(tag_nominator)
        end

        context 'no tag set' do
          it 'redirects and returns an error message' do
            delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: 0 }
            it_redirects_to_with_error(tag_sets_path, 'What tag set did you want to nominate for?')
          end
        end

        context 'no tag set nomination' do
          it 'redirects and returns an error message' do
            delete :destroy, params: { id: 0, tag_set_id: owned_tag_set.id }
            it_redirects_to_with_error(tag_set_path(owned_tag_set), 'Which nominations did you want to work with?')
          end
        end
      end

      context 'valid params' do
        context 'user is not moderator of tag set' do
          before do
            fake_login_known_user(tag_nominator.reload)
          end

          context 'at least one tag nomination associated with tag_set_nomination is reviewed' do
            let(:fandom_nomination) do
              FandomNomination.create(tag_set_nomination: tag_set_nomination,
                                      tagname: "New Fandom")
            end

            before do
              fandom_nomination.update_column(:rejected, true)
            end

            it 'does not delete the tag set nomination' do
              delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
              expect(tag_set_nomination.reload).to eq(tag_set_nomination)
            end

            it 'redirects and returns an error message' do
              delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
              it_redirects_to_with_error(tag_set_nomination_path(owned_tag_set, tag_set_nomination),
                                         'You cannot delete nominations after some of them have been reviewed, sorry!')
            end
          end

          context 'all tag nominations associated with tag_set_nomination are unreviewed' do
            it 'deletes the tag set nomination' do
              expect { delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id } }.
                to change { TagSetNomination.count }.by(-1)
            end

            it 'redirects and returns a success message' do
              delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
              it_redirects_to_with_notice(tag_set_path(owned_tag_set), 'Your nominations were deleted.')
            end
          end
        end

        context 'user is moderator of tag set' do
          before do
            fake_login_known_user(moderator.reload)
          end

          it 'deletes the tag set nomination' do
            expect { delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id } }.
              to change { TagSetNomination.count }.by(-1)
          end

          it 'redirects and returns a success message' do
            delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
            it_redirects_to_with_notice(tag_set_path(owned_tag_set), 'Your nominations were deleted.')
          end

          context "when the nomination is from a different tag set" do
            let(:owned_tag_set) { create(:owned_tag_set) }

            it "redirects and returns an error message" do
              delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
              it_redirects_to_with_error(tag_set_path(owned_tag_set),
                                         "Which nominations did you want to work with?")
            end

            it "doesn't delete the nomination" do
              delete :destroy, params: { id: tag_set_nomination.id, tag_set_id: owned_tag_set.id }
              expect { tag_set_nomination.reload }.not_to raise_exception
            end
          end
        end
      end
    end
  end

  describe 'GET confirm_destroy_multiple' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        get :confirm_destroy_multiple, params: { tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'user is logged in' do
      before do
        fake_login_known_user(random_user)
      end

      context 'invalid params' do
        context 'no tag set' do
          it 'redirects and returns an error message' do
            get :confirm_destroy_multiple, params: { tag_set_id: 0 }
            it_redirects_to_with_error(tag_sets_path, 'What tag set did you want to nominate for?')
          end
        end
      end

      context 'valid params' do
        it 'renders the confirm_destroy_multiple template' do
          get :confirm_destroy_multiple, params: { tag_set_id: owned_tag_set.id }
          expect(response).to render_template('confirm_destroy_multiple')
        end
      end
    end
  end

  describe 'DELETE destroy_multiple' do
    before do
      allow(OwnedTagSet).to receive(:find_by_id).with(owned_tag_set.id.to_s) { owned_tag_set }
      allow(owned_tag_set).to receive(:clear_nominations!)
    end

    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        delete :destroy_multiple, params: { tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'logged in user is owner of tag set' do
      before do
        fake_login_known_user(owned_tag_set.owners.first.user)
        delete :destroy_multiple, params: { tag_set_id: owned_tag_set.id }
      end

      it 'deletes all associated tag nominations' do
        expect(owned_tag_set).to have_received(:clear_nominations!)
      end

      it 'redirects and returns a success message' do
        it_redirects_to_with_notice(tag_set_path(owned_tag_set), 'All nominations for this Tag Set have been cleared.')
      end
    end

    context 'logged in user is not owner of tag set' do
      before do
        fake_login_known_user(moderator)
        delete :destroy_multiple, params: { tag_set_id: owned_tag_set.id }
      end

      it 'redirects and returns an error message' do
        it_redirects_to_with_error(tag_set_path(owned_tag_set), "You don't have permission to do that.")
      end

      it 'does not delete associated tag nominations' do
        expect(owned_tag_set).not_to have_received(:clear_nominations!)
      end
    end
  end

  describe 'PUT update_multiple' do
    context 'user is not logged in' do
      it 'redirects and returns an error message' do
        put :update_multiple, params: { tag_set_id: owned_tag_set.id }
        it_redirects_to_with_error(new_user_session_path, "Sorry, you don't have permission to access the page you " \
          "were trying to reach. Please log in.")
      end
    end

    context 'logged in user is moderator of tag set' do
      let(:fandom_nom) { FandomNomination.create(tag_set_nomination: tag_set_nomination, tagname: 'New Fandom') }
      let!(:character_nom1) { CharacterNomination.create(tag_set_nomination: tag_set_nomination,
                                                         fandom_nomination: fandom_nom,
                                                         tagname: 'New Character 1') }
      let!(:character_nom2) { CharacterNomination.create(tag_set_nomination: tag_set_nomination,
                                                         fandom_nomination: fandom_nom,
                                                         tagname: 'New Character 2') }
      let!(:relationship_nom) { RelationshipNomination.create(tag_set_nomination: tag_set_nomination,
                                                              fandom_nomination: fandom_nom,
                                                              tagname: 'New Relationship') }
      let!(:freeform_nom) { FreeformNomination.create(tag_set_nomination: tag_set_nomination, tagname: 'New Freeform') }
      let(:base_params) { { 'character_change_New Character 1': '',
                            'character_change_New Character 2': '',
                            'relationship_change_New Relationship': '',
                            'fandom_change_New Fandom': '',
                            'freeform_change_New Freeform': '' } }

      before do
        fake_login_known_user(moderator.reload)
      end

      context 'not all tag nominations have an associated _approve, _reject, _change, or _synonym param value' do
        it 'redirects and returns a flash message' do
          put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params)
          it_redirects_to_simple(tag_set_nominations_path(owned_tag_set))
          expect(flash[:notice]).to include('Still some nominations left to review!')
        end
      end

      context 'all tag nominations have an associated _approve, _reject, _change, or _synonym param value' do
        it 'redirects and returns a flash message' do
          put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
            merge('character_approve_New Character 1': 1,
                  'character_reject_New Character 2': 1,
                  'relationship_approve_New Relationship': 1,
                  'fandom_approve_New Fandom': 1,
                  'freeform_reject_New Freeform': 1)
          it_redirects_to_simple(tag_set_path(owned_tag_set))
          expect(flash[:notice]).to include('All nominations reviewed, yay!')
        end
      end

      context 'tag nomination _reject param has a value' do
        before do
          put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
            merge('relationship_reject_New Relationship': 1)
        end

        it 'updates tag_nomination.rejected to true' do
          expect(relationship_nom.reload.rejected).to be_truthy
        end

        it 'returns a success message' do
          expect(flash[:notice]).to include('Successfully rejected: New Relationship')
        end
      end

      context 'tag nomination _approve param has a value' do
        context 'approving the tag nomination is successful' do
          before do
            put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
              merge('fandom_approve_New Fandom': 1, 'character_approve_New Character 2': 1)
          end

          it 'updates tag_nomination.approved to true' do
            expect(fandom_nom.reload.approved).to be_truthy
            expect(character_nom2.reload.approved).to be_truthy
          end

          it 'returns a success message' do
            expect(flash[:notice]).to include('Successfully added to set: New Fandom')
            expect(flash[:notice]).to include('Successfully added to set: New Character 2')
          end
        end

        context 'approving the tag nomination fails' do
          before do
            allow(OwnedTagSet).to receive(:find_by_id).with(owned_tag_set.id.to_s) { owned_tag_set }
            allow(owned_tag_set).to receive(:add_tagnames).with('fandom', ['New Fandom']) { false }
            put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
              merge('fandom_approve_New Fandom': 1, 'character_approve_New Character 2': 1)
          end

          it 'renders the index template and returns an error message' do
            expect(flash[:error]).to eq('Oh no! We ran into a problem partway through saving your updates -- please ' \
                                          'check over your tag set closely!')
            expect(response).to render_template('index')
          end
        end

        context 'other tag nominations exist in tag set that go by a synonym of the approved nom' do
          before do
            character_nom1.update_column(:synonym, 'New Character 2')
          end

          context 'name change is successful' do
            it 'calls TagNomination.change_tagname! with old and new tagnames' do
              allow(TagNomination).to receive(:change_tagname!)
              put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
                merge('character_approve_New Character 2': 1)
              expect(TagNomination).to have_received(:change_tagname!).with(owned_tag_set, 'New Character 1', 'New Character 2')
            end
          end

          context 'name change fails' do
            it 'renders the index template and returns an error message' do
              allow(TagNomination).to receive(:change_tagname!) { false }
              put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
                merge('character_approve_New Character 2': 1)
              expect(flash[:error]).to eq('Oh no! We ran into a problem partway through saving your updates, ' \
                                            'changing New Character 1 to New Character 2 -- please check over ' \
                                            'your tag set closely!')
              expect(response).to render_template('index')
            end
          end
        end
      end

      context 'tag nomination _approve and _reject params have a value' do
        before do
          put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
            merge('relationship_approve_New Relationship': 1, 'relationship_reject_New Relationship': 2)
        end

        it 'renders the index template' do
          expect(response).to render_template('index')
        end

        it 'returns expected errors' do
          expect(assigns(:errors)).to include('You have both approved and rejected the following relationship tags: ' \
                                                'New Relationship')
        end
      end

      context 'tag nomination _change param is not empty' do
        context '_change param = current tag nomination tagname' do
          it 'does not update the tag nomination' do
            put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
              merge('freeform_change_New Freeform': 'New Freeform')
            expect(freeform_nom.reload.approved).to be_falsey
          end
        end

        context '_change param is different from current tag nomination tagname' do
          context 'new name is invalid' do
            before do
              put :update_multiple, params: { tag_set_id: owned_tag_set.id }.
                merge(base_params).merge('freeform_change_New Freeform': 'N*w Fr**f*rm')
            end

            it 'renders the index template' do
              expect(response).to render_template('index')
            end

            it 'returns expected errors' do
              expect(assigns(:errors).first).to start_with('Invalid name change for New Freeform to N*w Fr**f*rm: ')
            end
          end

          context 'no tag nomination associated with name' do
            before do
              put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
                merge('freeform_change_A Freeform Masquerade': 'Different Freeform')
            end

            it 'renders the index template' do
              expect(response).to render_template('index')
            end

            it 'returns expected errors' do
              expect(assigns(:errors)).to include("Couldn't find a freeform nomination for A Freeform Masquerade")
            end
          end

          context 'new name is valid' do
            context 'name change is successful' do
              before do
                allow(TagNomination).to receive(:change_tagname!).and_call_original
                put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
                  merge('freeform_change_New Freeform': 'Different Freeform')
              end

              it 'calls TagNomination.change_tagname! with _change param value' do
                expect(TagNomination).to have_received(:change_tagname!).with(owned_tag_set, 'New Freeform',
                                                                              'Different Freeform')
              end

              it 'updates tag_nomination.approved to true' do
                expect(freeform_nom.reload.approved).to be_truthy
              end

              it 'returns a success message' do
                expect(flash[:notice]).to include('Successfully added to set: Different Freeform')
              end
            end

            context 'name change is not successful' do
              before do
                allow(TagNomination).to receive(:change_tagname!) { false }
                put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
                  merge('freeform_change_New Freeform': 'Different Freeform')
              end

              it 'renders the index template' do
                expect(response).to render_template('index')
              end

              it 'returns an error message' do
                expect(flash[:error]).to eq('Oh no! We ran into a problem partway through saving your updates, ' \
                                              'changing New Freeform to Different Freeform -- please check over ' \
                                              'your tag set closely!')
              end
            end
          end
        end
      end

      context 'tag nomination _synonym param is not empty' do
        context '_synonym param = current tag nomination tagname' do
          it 'does not update the tag nomination' do
            put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
              merge('freeform_synonym_New Freeform': 'New Freeform')
            expect(freeform_nom.reload.approved).to be_falsey
          end
        end

        context '_synonym param is different from current tag nomination tagname' do
          context 'new name is invalid' do
            before do
              put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
                merge('freeform_synonym_New Freeform': 'N*w Fr**f*rm')
            end

            it 'renders the index template' do
              expect(response).to render_template('index')
            end

            it 'returns expected errors' do
              expect(assigns(:errors).first).to start_with('Invalid name change for New Freeform to N*w Fr**f*rm: ')
            end
          end

          context 'no tag nomination associated with name' do
            before do
              put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
                merge('freeform_synonym_A Freeform Masquerade': 'Different Freeform')
            end

            it 'renders the index template' do
              expect(response).to render_template('index')
            end

            it 'returns expected errors' do
              expect(assigns(:errors)).to include("Couldn't find a freeform nomination for A Freeform Masquerade")
            end
          end

          context 'new name is valid' do
            before do
              freeform_nom.update_column(:synonym, 'Different Freeform')
              put :update_multiple, params: { tag_set_id: owned_tag_set.id }.merge(base_params).
                merge('freeform_synonym_New Freeform': 'Different Freeform')
            end

            it 'updates tag_nomination.approved to true' do
              expect(freeform_nom.reload.approved).to be_truthy
            end

            it 'returns a success message' do
              expect(flash[:notice]).to include('Successfully added to set: Different Freeform')
            end
          end
        end
      end
    end

    context 'logged in user is not moderator of tag set' do
      before do
        fake_login_known_user(tag_nominator)
        put :update_multiple, params: { tag_set_id: owned_tag_set.id }
      end

      it 'redirects and returns an error message' do
        it_redirects_to_with_error(tag_set_path(owned_tag_set), "You don't have permission to do that.")
      end
    end
  end
end
