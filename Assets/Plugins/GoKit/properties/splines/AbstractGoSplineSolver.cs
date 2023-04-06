using UnityEngine;
using System.Collections;
using System.Collections.Generic;


public abstract class AbstractGoSplineSolver
{
	protected List<Vector3> _nodes;
	public List<Vector3> nodes { get { return _nodes; } }
	protected float _pathLength;
	
	// how many subdivisions should we divide each segment into? higher values take longer to build and lookup but
	// result in closer to actual constant velocity
	protected int totalSubdivisionsPerNodeForLookupTable = 5;
	protected Dictionary<float, float> _segmentTimeForDistance; // holds data in the form [time:distance] as a lookup table
	
	
	
	// the default implementation breaks the spline down into segments and approximates distance by adding up
	// the length of each segment
	public virtual void buildPath()
	{
		var totalSudivisions = _nodes.Count * totalSubdivisionsPerNodeForLookupTable;
		_pathLength = 0;
		float timePerSlice = 1f / totalSudivisions;
		
		// we dont care about the first node for distances because they are always t:0 and len:0
		_segmentTimeForDistance = new Dictionary<float, float>( totalSudivisions );
		
		
		var lastPoint = getPoint( 0 );
		
		// skip the first node and wrap 1 extra node
        for( var i = 1; i < totalSudivisions + 1; i++ )
        {
			// what is the current time along the path?
            float currentTime = timePerSlice * i;

            var currentPoint = getPoint( currentTime );
            _pathLength += Vector3.Distance( currentPoint, lastPoint );
            lastPoint = currentPoint;

            _segmentTimeForDistance.Add( currentTime, _pathLength );
        }
	}
	
	
	public abstract void closePath();
	
	
	// gets the raw point not taking into account constant speed. used for drawing gizmos
	public abstract Vector3 getPoint( float t );
	
	
	// gets the point taking in to account constant speed. the default implementation approximates the length of the spline
	// by walking it and calculating the distance between each node
	public virtual Vector3 getPointOnPath( float t )
	{
		// we know exactly how far along the path we want to be from the passed in t
		var targetDistance = _pathLength * t;
		
		// store the previous and next nodes in our lookup table
		var previousNodeTime = 0f;
		var previousNodeLength = 0f;
		var nextNodeTime = 0f;
		var nextNodeLength = 0f;
		
		// loop through all the values in our lookup table and find the two nodes our targetDistance falls between
		foreach( var item in _segmentTimeForDistance )
		{
			// have we passed our targetDistance yet?
		    if( item.Value >= targetDistance )
		    {
		        nextNodeTime = item.Key;
		        nextNodeLength = item.Value;
				
		        if( previousNodeTime > 0 )
		            previousNodeLength = _segmentTimeForDistance[previousNodeTime];

		        break;
		    }
		    previousNodeTime = item.Key;
		}
		
		// translate the values from the lookup table estimating the arc length between our known nodes from the lookup table
		var segmentTime = nextNodeTime - previousNodeTime;
		var segmentLength = nextNodeLength - previousNodeLength;
		var distanceIntoSegment = targetDistance - previousNodeLength;
		
		t = previousNodeTime + ( distanceIntoSegment / segmentLength ) * segmentTime;
		
		return getPoint( t );
	}
	
	
	public void reverseNodes()
	{
		_nodes.Reverse();
	}
	
	
	public virtual void drawGizmos()
	{}

}
