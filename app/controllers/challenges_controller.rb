class ChallengesController < ApplicationController
  # This is the PARENT controller for all the challenge controller classes
  #
  # Here's how challenges work:
  #
  # For each TYPE of challenge, you use the challenge generator:
  # script/generate challenge WhatEver -- eg, script/generate challenge
  # Flashfic, script/generate challenge BigBang, etc
  # This creates:
  # - a model called Challenge::WhatEver -- eg, Challenge::Flashfic,
  # Challenge::BigBang, etc which actually implements a challenge
  # - a controller Challenge::WhatEverController --
  # Challenge::FlashficController which goes in
  # controllers/challenge/flashfic_controller.rb
  # - a views subfolder called views/challenge/whatever --
  # eg, views/challenge/flashfic/, views/challenge/bigbang/
  #
  # Example:
  # script/generate challenge Flashfic creates:
  # - controllers/challenge/flashfics_controller.rb
  # - models/challenge/flashfic.rb
  # - views/challenges/flashfics/
  # - db/migrate/create_challenge_flashfics to create challenge_flashfics table
  #
  # Setting up an instance:
  # - create the collection SGA_Flashfic
  # - mark it as a challenge collection
  # - choose "Regular Single Prompt (flashfic-style)" as the challenge type
  #
  # This feeds us into challenges/new and creates a new empty Challenge object
  # with collection -> SGA_Flashfic
  # and implementation -> Challenge::Flashfic.new
  # We are then redirected to Challenge::FlashficController new ->
  # views/challenges/flashfic/new.html.erb
  #
  # These can have lots of options specific to flashfics, including eg how often
  # prompts are posted, if they should automatically
  # be closed, whether we take user suggestions for prompts, etc.
  #

  before_action :load_collection

  def no_collection
    flash[:error] = t('challenge.no_collection',
                      default: 'What collection did you want to work with?')
    redirect_to(request.env['HTTP_REFERER'] || root_path)
    false
  end

  def no_challenge
    flash[:error] = t('challenges.no_challenge',
                      default: 'What challenge did you want to work on?')
    redirect_to collection_path(@collection) rescue redirect_to '/'
    false
  end

  def load_collection
    @collection ||= Collection.find_by(name: params[:collection_id]) if
        params[:collection_id]
    no_collection && return unless @collection
  end

  def load_challenge
    @challenge = @collection.challenge
    no_challenge and return unless @challenge
  end

end
