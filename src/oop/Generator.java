package oop;

import marker.CircleMark;
import marker.CircleTag;
import marker.enums.CardinalDirection;
import marker.enums.DiscreteSize;
import marker.enums.Polarity;
import misc.Debug;

public abstract class Generator {
	private CircleTag circleTag;
	private CircleMark mark = null;

	private int
	d=0, o=0, s=0,
	direction=0, orbit=0, size=0,
	markerId=0, idDifference=0, leastIdDifference=Integer.MAX_VALUE;
	private boolean hasDifference;
	
	public Generator(CircleTag circleTag) {
		this.circleTag = circleTag;
		
	}
	public void generateCircleTag(int id) {
		clearCircleMarks();
		
		Debug.print("ID: " + id);
		
		int sizeLimit = 7, attempt, attempts=1;
		while(sizeLimit > 0) {
			
			for(attempt=0; attempt<attempts; attempt++) {
				addHollows(id, sizeLimit);
				addSolids(id, sizeLimit);
			}

			if(markerId != id) {
				clearCircleMarks();
				sizeLimit--;
				attempts++;
			}
			else {
				break;
			}
		}
		
		onIdGenerated();
		Debug.print("ID Generated: " + circleTag.getMarkerId());
	}
	public void clearCircleMarks() {
		circleTag.clearCircleMarks();
		markerId = 0;
	}
	public void addHollows(int targetId, int sizeLimit) {
		Debug.print("\taddHollows();");
		
		addCircleMarks(targetId, sizeLimit, Polarity.HOLLOW);
	}
	public void addSolids(int targetId, int sizeLimit) {
		Debug.print("\taddSolids();");
		
		addCircleMarks(targetId, sizeLimit, Polarity.SOLID);
	}
	public void addCircleMarks(int targetId, int sizeLimit, Polarity polarity) {
		leastIdDifference = Integer.MAX_VALUE;
		
		while(markerId != targetId) {
			mark = new CircleMark(1, CardinalDirection.NW, DiscreteSize.A, polarity);
			circleTag.addCircleMark(mark);

			for(d=7; d>=0; d--) {
				mark.setDirection(CardinalDirection.values()[d]);
				
				for(o=5; o>0; o--) {
					mark.setOrbit(o);
					
					for(s=0; s<sizeLimit; s++) {
						mark.setDiscreteSize(DiscreteSize.values()[s]);

						if(circleTag.isMarkerValid()) {
							markerId = circleTag.getMarkerId();
							
							setPartialCircleMarkPosition(markerId, targetId, polarity);
						}
					}
				}
			}
			
			if(!setFinalCircleMarkPosition()) {
				Debug.print("\t\tinfinity");
				break;
			}
			
			markerId = circleTag.getMarkerId();
		}
		
		if(!circleTag.isMarkerValid()) circleTag.removeCircleMark(mark);
	}
	protected void setPartialCircleMarkPosition(int markerId, int targetId, Polarity polarity) {
		idDifference = idDifference(markerId, targetId);
		
		if(idDifference < leastIdDifference) {
			leastIdDifference = idDifference;
			
			direction = d;
			orbit = o;
			size = s;
			
			hasDifference = true;
		}
	}
	protected boolean setFinalCircleMarkPosition() {
		if(hasDifference) {
			mark.setDirection(CardinalDirection.values()[direction]);
			mark.setOrbit(orbit);
			mark.setDiscreteSize(DiscreteSize.values()[size]);
			
			Debug.print("\t\t"+mark.toString());
			
			hasDifference = false;
			return true;
		}
		else {
			circleTag.removeCircleMark(mark);
			return false;
		}
	}
	
	public abstract void onIdGenerated();
	
	private int idDifference(int id1, int id2) {
		return Math.abs(id1 - id2);
	}
}
