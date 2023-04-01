class CollectionParticipantsController < ApplicationController
  before_action :users_only
  before_action :load_collection
  before_action :load_participant, only: [:update, :destroy]
  before_action :allowed_to_promote, only: [:update]
  before_action :allowed_to_destroy, only: [:destroy]
  before_action :has_other_owners, only: [:update, :destroy]
  before_action :collection_maintainers_only, only: [:index, :add, :update]

  cache_sweeper :collection_sweeper

  def owners_required
    flash[:error] = t('collection_participants.owners_required', default: "You can't remove the only owner!")
    redirect_to collection_participants_path(@collection)
    false
  end

  def load_collection
    @collection = Collection.find_by!(name: params[:collection_id])
  end

  def load_participant
    @participant = @collection.collection_participants.find(params[:id])
  end

  def allowed_to_promote
    @new_role = collection_participant_params[:participant_role]
    @participant.user_allowed_to_promote?(current_user, @new_role) || not_allowed(@collection)
  end

  def allowed_to_destroy
    @participant.user_allowed_to_destroy?(current_user) || not_allowed(@collection)
  end

  def has_other_owners
    !@participant.is_owner? || (@collection.owners != [@participant.pseud]) || owners_required
  end

  ## ACTIONS

  def join
    unless @collection
      flash[:error] = t('no_collection', default: "Which collection did you want to join?")
      redirect_to(request.env["HTTP_REFERER"] || root_path) and return
    end
    participants = CollectionParticipant.in_collection(@collection).for_user(current_user) unless current_user.nil?
    if participants.empty?
      @participant = CollectionParticipant.new(
        collection: @collection,
        pseud: current_user.default_pseud,
        participant_role: CollectionParticipant::NONE
      )
      @participant.save
      flash[:notice] = t('applied_to_join_collection', default: "You have applied to join %{collection}.", collection: @collection.title)
    else
      participants.each do |participant|
        if participant.is_invited?
          participant.approve_membership!
          flash[:notice] = t('collection_participants.accepted_invite', default: "You are now a member of %{collection}.", collection: @collection.title)
          redirect_to(request.env["HTTP_REFERER"] || root_path) and return
        end
      end

      flash[:notice] = t('collection_participants.no_invitation', default: "You have already joined (or applied to) this collection.")
    end

    redirect_to(request.env["HTTP_REFERER"] || root_path)
  end

  def index
    @collection_participants = @collection.collection_participants.reject {|p| p.pseud.nil?}.sort_by {|participant| participant.pseud.name.downcase }
  end

  def update
    if @participant.update(collection_participant_params)
      flash[:notice] = t('collection_participants.update_success', default: "Updated %{participant}.", participant: @participant.pseud.name)
    else
      flash[:error] = t('collection_participants.update_failure', default: "Couldn't update %{participant}.", participant: @participant.pseud.name)
    end
    redirect_to collection_participants_path(@collection)
  end

  def destroy
    @participant.destroy
    flash[:notice] = t('collection_participants.destroy', default: "Removed %{participant} from collection.", participant: @participant.pseud.name)
    redirect_to(request.env["HTTP_REFERER"] || root_path)
  end

  def add
    @participants_added = []
    @participants_invited = []
    pseud_results = Pseud.parse_bylines(params[:participants_to_invite], assume_matching_login: true)
    pseud_results[:pseuds].each do |pseud|
      if @collection.participants.include?(pseud)
        participant = CollectionParticipant.where(collection_id: @collection.id, pseud_id: pseud.id).first
        if participant && participant.is_none?
          @participants_added << participant if participant.approve_membership!
        end
      else
        participant = CollectionParticipant.new(collection: @collection, pseud: pseud, participant_role: CollectionParticipant::MEMBER)
        @participants_invited << participant if participant.save
      end
    end

    if @participants_invited.empty? && @participants_added.empty?
      if pseud_results[:banned_pseuds].present?
        flash[:error] =
            ts("%{name} is currently banned and cannot participate in challenges.",
               name: pseud_results[:banned_pseuds].to_sentence
        )
      else
        flash[:error] = ts("We couldn't find anyone new by that name to add.")
      end
    else
      flash[:notice] = ""
    end

    unless @participants_invited.empty?
      @participants_invited = @participants_invited.sort_by {|participant| participant.pseud.name.downcase }
      flash[:notice] += ts("New members invited: ") +  @participants_invited.collect(&:pseud).collect(&:byline).join(', ')
    end

    unless @participants_added.empty?
      @participants_added = @participants_added.sort_by {|participant| participant.pseud.name.downcase }
      flash[:notice] += ts("Members added: ") +  @participants_added.collect(&:pseud).collect(&:byline).join(', ')
    end

    redirect_to collection_participants_path(@collection)
  end

  private

  def collection_participant_params
    params.require(:collection_participant).permit(:participant_role)
  end
end
