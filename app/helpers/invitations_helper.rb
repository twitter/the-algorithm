module InvitationsHelper

  def creator_link(invitation)
    if invitation.creator.is_a?(User)
      link_to(invitation.creator.login, invitation.creator)
    elsif invitation.creator.is_a?(Admin)
      invitation.creator.login
    else
      "queue"
    end
  end

  def invitee_link(invitation)
    if invitation.invitee && invitation.invitee.is_a?(User)
      link_to(invitation.invitee.login, invitation.invitee)
    end
  end
end
