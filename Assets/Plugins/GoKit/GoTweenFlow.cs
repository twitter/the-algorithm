using UnityEngine;
using System.Collections;


/// <summary>
/// TweenFlows are used for creating a chain of Tweens via the append/prepend methods. You can also get timeline
/// like control by inserting Tweens and setting them to start at a specific time. Note that TweenFlows do not
/// honor the delays set within regular Tweens. Use the append/prependDelay method to add any required delays
/// </summary>
public class GoTweenFlow : AbstractGoTweenCollection
{
	public GoTweenFlow() : this( new GoTweenCollectionConfig() ) {}

	public GoTweenFlow( GoTweenCollectionConfig config ) : base( config ) {}


	#region internal Flow management
	
	/// <summary>
	/// the item being added already has a start time so no extra parameter is needed
	/// </summary>
	private void insert( TweenFlowItem item )
	{
		// early out for invalid items
		if( item.tween != null && !item.tween.isValid() )
			return;
		
		if( float.IsInfinity( item.duration ) )
		{
			Debug.LogError( "adding a Tween with infinite iterations to a TweenFlow is not permitted" );
			return;
		}
		
		if( item.tween != null )
        {
            if (item.tween.isReversed != isReversed)
            {
                Debug.LogError( "adding a Tween that doesn't match the isReversed property of the TweenFlow is not permitted." );
                return;
            }

            // ensure the tween isnt already live
            Go.removeTween(item.tween);

            // ensure that the item is marked to play.
            item.tween.play();
        }

		// add the item then sort based on startTimes
		_tweenFlows.Add( item );
		_tweenFlows.Sort( ( x, y ) =>
		{
			return x.startTime.CompareTo( y.startTime );
		} );
		
		duration = Mathf.Max( item.startTime + item.duration, duration );

        if (iterations < 0)
            totalDuration = float.PositiveInfinity;
        else
            totalDuration = duration * iterations;
	}
	
	#endregion
	
	
	#region Flow management
	
	/// <summary>
	/// inserts a Tween and sets it to start at the given startTime
	/// </summary>
	public GoTweenFlow insert( float startTime, AbstractGoTween tween )
	{
		var item = new TweenFlowItem( startTime, tween );
		insert( item );
		
		return this;
	}
	
	#endregion
	

}
