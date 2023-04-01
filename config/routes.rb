Otwarchive::Application.routes.draw do

  devise_scope :admin do
    get "admin/logout" => "admin/sessions#confirm_logout"

    # Rails emulates some HTTP methods over POST, so password resets (PUT /admin/password)
    # look the same as password reset requests (POST /admin/password).
    #
    # To rate limit them differently at nginx, we set up an alias for
    # the first request type.
    put "admin/password/reset" => "admin/passwords#update"
  end

  devise_for :admin,
             module: "admin",
             only: [:sessions, :passwords],
             controllers: {
               sessions: "admin/sessions",
               passwords: "admin/passwords"
             },
             path_names: {
               sign_in: "login",
               sign_out: "logout"
             }

  devise_scope :user do
    get "signup(/:invitation_token)" => "users/registrations#new", as: "signup"
    get "users/logout" => "users/sessions#confirm_logout"

    # Rails emulate some HTTP methods over POST, so password resets (PUT /users/password)
    # look the same as password reset requests (POST /users/password).
    #
    # To rate limit them differently at nginx, we set up an alias for
    # the first request type.
    put "users/password/reset" => "users/passwords#update"
  end

  devise_for :users,
             module: "users",
             controllers: {
               sessions: "users/sessions",
               registrations: "users/registrations",
               passwords: "users/passwords"
             },
             path_names: {
               sign_in: "login",
               sign_out: "logout"
             }

  #### ERRORS ####

  get '/403', to: 'errors#403'
  get '/404', to: 'errors#404'
  get '/422', to: 'errors#422'
  get '/500', to: 'errors#500'
  get '/auth_error', to: 'errors#auth_error'

  #### DOWNLOADS ####

  get 'downloads/:id/:download_title.:format' => 'downloads#show', as: 'download'

  #### OPEN DOORS ####
  namespace :opendoors do
    resources :tools, only: [:index] do
      collection do
        post :url_update
      end
    end
    resources :external_authors do
      member do
        post :forward
      end
    end
  end

  #### INVITATIONS ####

  resources :invitations
  resources :user_invite_requests
  resources :invite_requests, only: [:index, :create, :destroy] do
    collection do
      get :manage
      get :status
    end
  end

  get 'claim/:invitation_token' => 'external_authors#claim', as: 'claim'
  post 'complete_claim/:invitation_token' => 'external_authors#complete_claim', as: 'complete_claim'

  #### TAGS ####

  resources :media do
    resources :fandoms
  end
  resources :fandoms do
    collection do
      get :unassigned
    end
    get :show
  end
  resources :tag_wranglings do
    collection do
      post :wrangle
    end
  end
  resources :tag_wranglers
  resources :unsorted_tags do
    collection do
      post :mass_update
    end
  end
  resources :tags do
    member do
      get :feed
      post :mass_update
      get :remove_association
      get :wrangle
    end
    collection do
      get :show_hidden
      get :search
    end
    resources :works
    resources :bookmarks
    resources :comments
    resource :troubleshooting, controller: :troubleshooting, only: [:show, :update]
  end

  resources :tag_sets, controller: 'owned_tag_sets' do
    resources :nominations, controller: 'tag_set_nominations' do
      collection do
        put :update_multiple
        delete :destroy_multiple
        get :confirm_destroy_multiple
      end
      member do
        get :confirm_delete
      end
    end
    resources :associations, controller: 'tag_set_associations', only: [:index] do
      collection do
        put :update_multiple
      end
    end
    member do
      get :batch_load
      put :do_batch_load
      get :confirm_delete
    end
    collection do
      get :show_options
    end
  end
  resources :tag_nominations, only: [:update]

  resources :tag_wrangling_requests, only: [:index] do
    collection do
      patch :update_multiple
    end
  end

  #### ADMIN ####
  resources :admin_posts do
    resources :comments
  end

  namespace :admin do
    resources :activities, only: [:index, :show]
    resources :banners do
      member do
        get :confirm_delete
      end
    end
    resources :blacklisted_emails, only: [:index, :create, :destroy]
    resources :settings
    resources :skins do
      collection do
        get :index_rejected
        get :index_approved
      end
    end
    resources :spam, only: [:index] do
      collection do
        post :bulk_update
      end
    end
    resources :user_creations, only: [:destroy] do
      member do
        put :hide
        put :set_spam
      end
    end
    resources :users, controller: "admin_users", only: [:index, :show] do
      member do
        get :confirm_delete_user_creations
        post :destroy_user_creations
        post :activate
        post :send_activation
        get :check_user
      end
      collection do
        get :bulk_search
        post :bulk_search
        post :update
        post :update_status
        post :update_next_of_kin
      end
    end
    resources :invitations, controller: 'admin_invitations' do
      collection do
        post :invite_from_queue
        post :grant_invites_to_users
        get :find
      end
    end
    resources :api
  end
  resources :admins, only: [:index]

  post '/admin/api/new', to: 'admin/api#create'

  #### USERS ####

  resources :people, only: [:index] do
    collection do
      get :search
    end
  end

  # When adding new nested resources, please keep them in alphabetical order
  resources :users, except: [:new, :create] do
    member do
      get :change_email
      post :changed_email
      get :change_password
      post :changed_password
      get :change_username
      post :changed_username
      post :end_first_login
      post :end_banner
      post :end_tos_prompt
    end
    resources :assignments, controller: "challenge_assignments", only: [:index]
    resources :claims, controller: "challenge_claims", only: [:index]
    resources :bookmarks
    resources :collection_items, only: [:index, :update] do
      collection do
        patch :update_multiple
      end
    end
    resources :collections, only: [:index]
    resources :comments do
      member do
        put :approve
        put :reject
      end
    end
    resource :creatorships, controller: "creatorships", only: [:show, :update]
    resources :external_authors do
      resources :external_author_names
    end
    resources :favorite_tags, only: [:create, :destroy]
    resources :gifts, only: [:index]
    resource :inbox, controller: "inbox" do
      member do
        get :reply
        post :delete
      end
    end
    resources :invitations do
      collection do
        post :invite_friend
      end
      collection do
        get :manage
      end
    end
    resources :nominations, controller: "tag_set_nominations", only: [:index]
    resources :preferences, only: [:index, :update]
    resource :profile, only: [:show], controller: "profile"
    resources :pseuds do
      resources :works
      resources :series
      resources :bookmarks
    end
    resources :readings do
      collection do
        post :clear
      end
    end
    resources :related_works
    resources :series do
      member do
        get :manage
      end
      resources :serial_works
    end
    resources :signups, controller: "challenge_signups", only: [:index]
    resources :skins, only: [:index]
    resources :stats, only: [:index]
    resources :subscriptions, only: [:index, :create, :destroy]
    resources :tag_sets, controller: "owned_tag_sets", only: [:index]
    resources :works do
      collection do
        get :drafts
        get :collected
        get :show_multiple
        post :edit_multiple
        patch :update_multiple
        post :delete_multiple
      end
    end
    namespace :blocked do
      resources :users, only: [:index, :create, :destroy] do
        collection do
          get :confirm_block
        end
        member do
          get :confirm_unblock
        end
      end
    end
    namespace :muted do
      resources :users, only: [:index, :create, :destroy] do
        collection do
          get :confirm_mute
        end
        member do
          get :confirm_unmute
        end
      end
    end
  end

  #### WORKS ####

  resources :works do
    collection do
      post :import
      get :search
    end
    member do
      get :preview
      post :post
      put :post_draft
      get :navigate
      get :edit_tags
      get :preview_tags
      patch :update_tags
      get :mark_for_later
      get :mark_as_read
      get :confirm_delete
      get :share
    end
    resources :bookmarks
    resources :chapters do
      collection do
        get :manage
        post :update_positions
      end
      member do
        get :preview
        post :post
        get :confirm_delete
      end
      resources :comments
    end
    resources :collections
    resources :collection_items
    resources :comments do
      member do
        put :approve
        put :reject
      end
      collection do
        get :unreviewed
        put :review_all
      end
    end
    resource :hit_count, controller: :hit_count, only: [:create]
    resources :kudos, only: [:index]
    resources :links, controller: "work_links", only: [:index]
    resource :troubleshooting, controller: :troubleshooting, only: [:show, :update]
  end

  resources :chapters do
    member do
      get :preview
      post :post
    end
    resources :comments
  end

  resources :external_works do
    collection do
      get :fetch
    end
    resources :bookmarks
    resources :related_works
  end

  resources :related_works
  resources :serial_works
  resources :series do
    member do
      get :confirm_delete
      get :manage
      post :update_positions
    end
    resources :bookmarks
  end

  #### COLLECTIONS ####

  resources :gifts, only: [:index] do
    member do
      post :toggle_rejected
    end
  end

  resources :collections do
    collection do
      get :list_challenges
      get :list_ge_challenges
      get :list_pm_challenges
    end
    member do
      get :confirm_delete
    end
    resource :profile, controller: "collection_profile"
    resources :collections
    resources :works
    resources :gifts
    resources :bookmarks
    resources :media
    resources :fandoms
    resources :people
    resources :prompts
    resources :tags do
      resources :works
    end
    resources :participants, controller: "collection_participants", only: [:index, :update, :destroy] do
      collection do
        get :add
        get :join
      end
    end
    resources :items, controller: "collection_items" do
      collection do
        patch :update_multiple
      end
    end
    resources :signups, controller: "challenge_signups" do
      collection do
        get :summary
      end
      member do
        get :confirm_delete
      end
    end
    resources :assignments, controller: "challenge_assignments", only: [:index, :show] do
      collection do
        get :confirm_purge
        get :generate
        put :set
        post :purge
        get :send_out
        put :update_multiple
        get :default_all
      end
      member do
        get :default
      end
    end
    resources :claims, controller: "challenge_claims" do
      collection do
        patch :set
        get :purge
      end
    end
    resources :potential_matches do
      collection do
        get :generate
        get :cancel_generate
        get :regenerate_for_signup
      end
    end
    resources :requests, controller: "challenge_requests"
    # challenge types
    resource :gift_exchange, controller: "challenge/gift_exchange" do
      member do
        get :confirm_delete
      end
    end
    resource :prompt_meme, controller: "challenge/prompt_meme" do
      member do
        get :confirm_delete
      end
    end
  end

  #### I18N ####

  # should stay below the main works mapping
  resources :languages do
    resources :works
    resources :admin_posts
  end
  resources :locales, except: :destroy

  #### API ####

  namespace :api do
    namespace :v2 do
      resources :bookmarks, only: [:create], defaults: { format: :json }
      resources :works, only: [:create], defaults: { format: :json }
      post 'bookmarks/search', to: 'bookmarks#search'
      post 'works/search', to: 'works#search'
    end
  end

  #### MISC ####

  resources :comments do
    member do
      put :approve
      put :freeze
      put :hide
      put :reject
      put :review
      put :unfreeze
      put :unhide
    end
    collection do
      get :hide_comments
      get :show_comments
      get :add_comment_reply
      get :cancel_comment_reply
      get :cancel_comment_edit
      get :delete_comment
      get :cancel_comment_delete
    end
    resources :comments
  end
  resources :bookmarks do
    collection do
      get :search
    end
    member do
      get :confirm_delete
      get :share
    end
    resources :collection_items
  end

  resources :kudos, only: [:create]

  resources :skins do
    member do
      get :preview
      get :set
      get :confirm_delete
    end
    collection do
      get :unset
    end
  end
  resources :known_issues
  resources :archive_faqs, path: "faq" do
    member do
      get :confirm_delete
    end
    collection do
      get :manage
      post :update_positions
    end
    resources :questions do
      collection do
        get :manage
        post :update_positions
      end
    end
  end
  resources :wrangling_guidelines do
    member do
      get :confirm_delete
    end
    collection do
      get :manage
      post :update_positions
    end
  end

  resource :redirect, controller: "redirect", only: [:show] do
    member do
      get :do_redirect
    end
  end

  resources :abuse_reports, only: [:new, :create]
  resources :external_authors do
    resources :external_author_names
  end
  resources :orphans, only: [:index, :new, :create]

  get 'search' => 'works#search'
  post 'support' => 'feedbacks#create', as: 'feedbacks'
  get 'support' => 'feedbacks#new', as: 'new_feedback_report'
  get 'tos' => 'home#tos'
  get 'tos_faq' => 'home#tos_faq'
  get 'unicorn_test' => 'home#unicorn_test'
  get 'dmca' => 'home#dmca'
  get 'diversity' => 'home#diversity'
  get 'site_map' => 'home#site_map'
  get 'site_pages' => 'home#site_pages'
  get 'first_login_help' => 'home#first_login_help'
  get 'token_dispenser' => 'home#token_dispenser'
  get 'delete_confirmation' => 'users#delete_confirmation'
  get 'activate/:id' => 'users#activate', as: 'activate'
  get 'devmode' => 'devmode#index'
  get 'donate' => 'home#donate'
  get 'lost_cookie' => 'home#lost_cookie'
  get 'about' => 'home#about'
  get 'menu/browse' => 'menu#browse'
  get 'menu/fandoms' => 'menu#fandoms'
  get 'menu/search' => 'menu#search'
  get 'menu/about' => 'menu#about'

  # The priority is based upon order of creation:
  # first created -> highest priority.
  root to: "home#index"

  # See how all your routes lay out with "rake routes"

  # These are whitelisted routes that are proven to be used throughout the
  # application, which previously relied on a deprecated catch-all route definition
  # (`get ':controller(/:action(/:id(.:format)))'`) to work.
  #
  # They are generally not RESTful and in some cases are *almost* duplicates of
  # existing routes defined above, but due to how extensively they are used
  # throughout the application must exist until forms, controllers, and tests
  # can be refactored to not rely on their existence.
  #
  # Note written on August 1, 2017 during upgrade to Rails 5.1.
  get '/bookmarks/fetch_recent/:id' => 'bookmarks#fetch_recent', as: :fetch_recent_bookmarks
  get '/bookmarks/hide_recent/:id' => 'bookmarks#hide_recent', as: :hide_recent_bookmarks

  get '/invite_requests/show' => 'invite_requests#show', as: :show_invite_request
  get '/user_invite_requests/update' => 'user_invite_requests#update'

  patch '/admin/skins/update' => 'admin_skins#update', as: :update_admin_skin

  get "/admin/admin_users/troubleshoot/:id" => "admin/admin_users#troubleshoot", as: :troubleshoot_admin_user

  # TODO: rewrite the autocomplete controller to deal with the fact that
  # there are 21 different actions going on in there
  %w[
    pseud
    tag
    fandom
    character
    relationship
    freeform
    character_in_fandom
    relationship_in_fandom
    tags_in_sets
    associated_tags
    noncanonical_tag
    collection_fullname
    open_collection_names
    collection_parent_name
    external_work
    potential_offers
    potential_requests
    owned_tag_sets
    site_skins
    admin_posts
    admin_post_tags
  ].each do |action|
    get "/autocomplete/#{action}" => "autocomplete##{action}"
  end

  get '/challenges/no_collection' => 'challenges#no_collection'
  get '/challenges/no_challenge' => 'challenges#no_challenge'

  get '/works/clean_work_search_params' => 'works#clean_work_search_params'
  get '/works/collected' => 'works#collected'
  get '/works/drafts' => 'works#drafts'

  post '/works/edit_multiple/:id' => 'works#edit_multiple'
  post '/works/confirm_delete_multiple/:id' => 'works#confirm_delete_multiple'
  post '/works/delete_multiple/:id' => 'works#delete_multiple'
  put '/works/update_multiple' => 'works#update_multiple'
end
