using UnityEngine;
using System.Collections;


public class GoTweenChain : AbstractGoTweenCollection
{
	public GoTweenChain() : this(new GoTweenCollectionConfig()) {}
	public GoTweenChain(GoTweenCollectionConfig config) : base(config) {}

	#region internal Chain management
	
	private void append( TweenFlowItem item )
	{
		// early out for invalid items
		if( item.tween != null && !item.tween.isValid() )
			return;
		
		if( float.IsInfinity( item.duration ) )
		{
			Debug.LogError( "adding a Tween with infinite iterations to a TweenChain is not permitted" );
			return;
		}

        if ( item.tween != null )
        {
            if ( item.tween.isReversed != isReversed )
            {
                Debug.LogError( "adding a Tween that doesn't match the isReversed property of the TweenChain is not permitted." );
                return;
            }

            // ensure the tween isnt already live
            Go.removeTween(item.tween);

            // ensure that the item is marked to play.
            item.tween.play();
        }
		
		_tweenFlows.Add( item );
		
		// update the duration and total duration
		duration += item.duration;
		
		if( iterations < 0 )
			totalDuration = float.PositiveInfinity;
		else
            totalDuration = duration * iterations;
	}
	
	
	private void prepend( TweenFlowItem item )
	{
		// early out for invalid items
		if( item.tween != null && !item.tween.isValid() )
			return;
		
		if( float.IsInfinity( item.duration ) )
		{
			Debug.LogError( "adding a Tween with infinite iterations to a TweenChain is not permitted" );
			return;
		}

        if ( item.tween != null )
        {
            if ( item.tween.isReversed != isReversed )
            {
                Debug.LogError( "adding a Tween that doesn't match the isReversed property of the TweenChain is not permitted." );
                return;
            }

            // ensure the tween isnt already live
            Go.removeTween( item.tween );

            // ensure that the item is marked to play.
            item.tween.play();
        }
		
		// fix all the start times on our previous chains
		foreach( var flowItem in _tweenFlows )
			flowItem.startTime += item.duration;

        _tweenFlows.Insert( 0, item );
		
		// update the duration and total duration
		duration += item.duration;

        if ( iterations < 0 )
            totalDuration = float.PositiveInfinity;
        else
            totalDuration = duration * iterations;
    }
	
	#endregion
	
	
	#region Chain management
	
	/// <summary>
	/// appends a Tween at the end of the current flow
	/// </summary>
	public GoTweenChain append( AbstractGoTween tween )
	{
		var item = new TweenFlowItem( duration, tween );
		append( item );
		
		return this;
	}
	
	
	/// <summary>
	/// appends a delay to the end of the current flow
	/// </summary>
	public GoTweenChain appendDelay( float delay )
	{
		var item = new TweenFlowItem( duration, delay );
		append( item );
		
		return this;
	}
	
	
	/// <summary>
	/// adds a Tween to the front of the flow
	/// </summary>
	public GoTweenChain prepend( AbstractGoTween tween )
	{
		var item = new TweenFlowItem( 0, tween );
		prepend( item );
		
		return this;
	}
	
	
	/// <summary>
	/// adds a delay to the front of the flow
	/// </summary>
	public GoTweenChain prependDelay( float delay )
	{
		var item = new TweenFlowItem( 0, delay );
		prepend( item );
		
		return this;
	}

	#endregion

}
