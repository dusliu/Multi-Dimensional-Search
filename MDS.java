/** Starter code for P3

 *  @Dustin Liu
 */
import java.util.*;

// zxl170011

public class MDS 
{
    // Add fields of MDS here
	int size;
	TreeMap <Integer, Item> tM; // Store id and Item
	HashMap <Integer, Item> hM; // to hold id and Item for holding key and value that contain a specified description pattern 
	
	// Class. Each Item
	public static class Item
	{
		private
			
		// Data fields, description uses list
			int ID;
			int price;
		// original test case required set HashSet<Integer> description = new HashSet<>();
			List<Integer> description = new ArrayList<>();
		// An item contains an id, price, and description
		public Item(int i, int p, List <Integer> l)
		{
			this.ID = i;
			this.price = p;
			if(!description.isEmpty())
				description.clear();
			for(int e:l)
				description.add(e);
		}
		
		// delete a description
		void clear()
		{
			ID = 0;
			price = 0;
			//description = new HashSet<>();
			description = new ArrayList<>();
		}
		
		// getter and setter methods
		void setPrice(int p)
		{
			this.price = p;
		}
		
		void setDescription(List<Integer>l)
		{
			if(!description.isEmpty())
				description.clear();
			for(int i : l)
			{
				description.add(i);
			}
		}
		
		//original test case was HashSet<Integer> 
		List<Integer> getDescription()
		{
			return this.description;
		}
		
		int getPrice()
		{
			return this.price;
		}
	}
	
    // Constructor
    public MDS() 
    {
    	// initialize the maps and initialize the size
    	tM = new TreeMap<>();
    	hM = new HashMap<>();
    	size = 0;
    }

    // id is key, item is value
    public int insert (int id, int price, java.util.List<Integer> list) 
    {
    	// if the id does not exist
    	if(!(tM.containsKey(id)))
    	{
    		// create new item and put into the map, increment the size and return 1
        	tM.put(id, new Item(id, price, list));
        	size++;
    		return 1;
    	}
    	
    	// if id already exists, update price and description
    	else  
    	{
    		tM.get(id).setPrice(price);
    		tM.get(id).setDescription(list);
    	}
    	return 0;
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public int find(int id) 
    {
    	if(tM.containsKey(id))
    	{
    		// return price of item if it exists in the tree map
    		return tM.get(id).getPrice();
    	}
    	// else return 0
	return 0;
    }
    
    // delete the element and add sum of the numbers in description
    public int delete(int id) 
    {
    	int sum = 0;
    	if(!tM.containsKey(id)) // if item does not exist in map
    		return 0;
    	
    	// List to hold all elements in description
    	List<Integer> originalList = new ArrayList<>();
    	
    	originalList.addAll(tM.get(id).getDescription());    	
    	
    	// go through the list
    	for(int i = 0; i < originalList.size(); i++)
    	{
    		// add 
    		sum += originalList.get(i);
    	}
    	// remove element from map
    	tM.remove(id);
    	
    	// decrement size
    	size--;
    
    	// if the sum is greater than 0, return the sum
    	if(sum!= 0)
    		return sum;
    	return 0;
    }    	
   
    public int findMinPrice(int n) 
    {
    	// clear any elements from hash map from previous operations 
    	if(!hM.isEmpty())
    		hM.clear();
    	
    	int minPrice = 0;	
    	
    	for(Map.Entry<Integer, Item> m: tM.entrySet())
		{// Get all information from treeMap and put into hashMap that contains desired pattern 
			if(m.getValue().getDescription().contains(n))
				hM.put(m.getKey(), tM.get(m.getKey()));
		}
		
    	// find minPrice
    	for(Map.Entry<Integer, Item> node : hM.entrySet())
    	{
    		if(minPrice == 0) 
    		{
    			// give minPrice
    			minPrice = node.getValue().getPrice();
    		}
    		
    		// update
    		else if(node.getValue().getPrice() < minPrice)
    			minPrice = node.getValue().getPrice();
    	}
    	// return minPrice
    	if(minPrice != 0)
    		return minPrice;
    	return 0;
    }

    // findMaxPrice will retrieve the highest price of key that contains the value n in description
   public int findMaxPrice(int n) 
    {
	    // clear any elements inside hashMap
	   	if(!hM.isEmpty())
   		{
   			hM.clear();
   		}
	   	
	   int maxPrice = 0;
   	   for(Map.Entry<Integer, Item> m: tM.entrySet())
	   {// put values that contain n in description into the hashMap
		 if(m.getValue().getDescription().contains(n))
			hM.put(m.getKey(), tM.get(m.getKey()));
		}
		
   	for(Map.Entry<Integer, Item> node : hM.entrySet())
   	{
   		// Compare prices
   		if(node.getValue().getPrice() > maxPrice)
   			maxPrice = node.getValue().getPrice();
   	}
   	
   	// return max price
   	if(maxPrice!= 0)
   		return maxPrice;
   	return 0;
    }

   // find the number of items between the range low and high that contain n in the description 
    public int findPriceRange(int n, int low, int high) 
    {
    	// throw in description number, get price range 
    	// if (contains description && .getPrice() >= low && <= high)
    	  
    	   int num_containing_n = 0;
    	   for(Map.Entry<Integer, Item> m: tM.entrySet())
    	   {// go through the treeMap
    		 if(m.getValue().getDescription().contains(n) && (m.getValue().getPrice() >= low && m.getValue().getPrice() <= high)) 
    		 {
    			 // increment if present
    			num_containing_n++;
    		 }
    		}
    	   // return the number of elements containing n in the description between low and high price
    	   if(num_containing_n != 0)
    		   return num_containing_n;
    	return 0;
    }

    /*
      g. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    
    public int removeNames(int id, java.util.List<Integer> list) 
    {
    	if(list == null || list.isEmpty())
    		return 0;
    	
    	int sum = 0;
    	
    	// create a list to contain original elements of description
    	List <Integer> original = new ArrayList<>();
    	original.addAll(tM.get(id).getDescription());
    	
    	// create another list to store elements common in the user's list and original description
    	List <Integer> common = new ArrayList<>();
    	Item it = tM.get(id);
    	for(int i : list)
    	{
    		if(it.getDescription().contains(i) && !(common.contains(i)))
    		{
    			common.add(i);
    		}
    	}
    	
    	// add all elements in common
    	for(int j : common)
    	{
    		sum += j;
    	}
    	
    	// remove from original 
    	original.removeAll(common);
    	if(original.size()!= 0)
    		tM.get(id).setDescription(original);
    	else
    		tM.remove(id);
     	
    	if(sum != 0)
    		return sum;
    	return 0;
    }
    
    // BONUS priceHike, assume dollars only
    int priceHike(int l, int h, int r)
    {
    	int sumOfIncrease = 0;
    	// between l and h
    	// Iterate through the treeMap
    	for(Map.Entry<Integer, Item> m : tM.entrySet())
    	{
    		if(m.getKey() <= h && m.getKey() >= l)
    		{
    			int price = m.getValue().getPrice();
    			int newPrice = (int) (price * (1 + (r/100.0))); 
    			m.getValue().setPrice(newPrice);
    			sumOfIncrease += newPrice;
    		}
    	}
    	return sumOfIncrease;
    }
}
