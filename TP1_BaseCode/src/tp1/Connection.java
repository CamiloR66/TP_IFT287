package tp1;

import java.util.ArrayList;
import java.util.List;

public class Connection
{
	int id;
    List<to> listeTo=new ArrayList<to>();
    
    public Connection(int id) {
		this.id = id;
	}

	public void addTo(to to) {
		listeTo.add(to);	
		
	}
	
}
