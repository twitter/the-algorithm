using UnityEngine;
using System.Collections;
using System.Collections.Generic;


/// <summary>
/// nodes should be in the order start, end, control1, control2
/// </summary>
public class GoSplineCubicBezierSolver : AbstractGoSplineSolver
{
	public GoSplineCubicBezierSolver( List<Vector3> nodes )
	{
		_nodes = nodes;
	}
	

	#region AbstractGoSplineSolver
	
	public override void closePath()
	{
		
	}
	
	
	public override Vector3 getPoint( float t )
	{
		float d = 1f - t;
		return d * d * d * _nodes[0] + 3f * d * d * t * _nodes[1] + 3f * d * t * t * _nodes[2] + t * t * t * _nodes[3];
	}

	
	public override void drawGizmos()
	{
		// draw the control points
		var originalColor = Gizmos.color;
		Gizmos.color = Color.red;
		
		Gizmos.DrawLine( _nodes[0], _nodes[1] );
		Gizmos.DrawLine( _nodes[2], _nodes[3] );
		
		Gizmos.color = originalColor;
	}
	
	#endregion

}
