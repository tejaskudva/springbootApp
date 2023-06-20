package com.tejas.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Service;

import com.tejas.model.Alien;
import com.tejas.model.AlienMarksBean;
import com.tejas.model.Marks;

@Service
public class AlienService {
	
	public List<AlienMarksBean> getAllDetails(List<Alien> alienList, List<Marks> marksList){
		
		List<AlienMarksBean> alienMarksList = new ArrayList<AlienMarksBean>();
		
		ListIterator<Alien> a = alienList.listIterator();
		ListIterator<Marks> m = marksList.listIterator();
		
		while(a.hasNext() && m.hasNext()) {
			AlienMarksBean alienMarksObj = new AlienMarksBean();
			
			alienMarksObj.setAlienId(a.next().getAid());
			a.previous();
			alienMarksObj.setAlienName(a.next().getAname());
			a.previous();
			alienMarksObj.setAlienTechnology(a.next().getTech());
			alienMarksObj.setMarks(m.next());
			
			alienMarksList.add(alienMarksObj);
		}
		
		return alienMarksList;	
	}

}
