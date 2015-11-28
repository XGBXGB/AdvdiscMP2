package model;
import java.util.HashMap;

public class FilterFactory {
	private HashMap<String, Filter> filterList = new HashMap<String, Filter>();
	private static FilterFactory instance = null;
	
	public static FilterFactory getInstance(){
        if(instance == null)
            instance = new FilterFactory();
        return instance;
    }
	
	public void registerFilter(String filterName, Filter filter)
	{
		filterList.put(filterName, filter);
	}
    
	public Filter createFilter(String filterName)
	{
		return ((Filter)filterList.get(filterName)).createFilter();
	}
	
	public Filter getFilter(String filterName)
	{
		return filterList.get(filterName);
	}
}
