
package com.stackroute.datamunger;

import java.util.Arrays;
import java.util.Scanner;

public class DataMunger {

	public static void main(String[] args) {
		System.out.println("Welcome to Data Munging Proejct");
		
		// read the query from the user into queryString variable
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the query to perform actions");
		String queryString = input.nextLine();
		
		// call the parseQuery method and pass the queryString variable as a parameter
		DataMunger dm= new DataMunger();
		dm.parseQuery(queryString);
		
		//System.out.println(dm.getConditions(queryString));
		System.out.println(Arrays.toString(dm.getConditions(queryString)));
	}
	

	public void parseQuery(String queryString) {
		//call the methods
		getSplitStrings(queryString);
		getFile(queryString);
		getBaseQuery(queryString);
		getConditionsPartQuery(queryString);
		getConditions(queryString);
		getLogicalOperators(queryString);
		getFields(queryString);
		getOrderByFields(queryString);
		getGroupByFields(queryString);
		getAggregateFunctions(queryString);
	}
	
	// parse the queryString into words and display
	public String[] getSplitStrings(String queryString) {
		String [] splitwords = queryString.split("\\s");
		return splitwords;
	}

	// get and display the filename
	public String getFile(String queryString) {
		
		String filename = queryString.split("from")[1].trim().split(" ")[0];
		
		return filename;
	}
	
	// getting the baseQuery and display
	public String getBaseQuery(String queryString) {
		
		if(queryString.contains("where"))
		{
			String basequery = queryString.split("where")[0];
			return basequery;
		}
		else if(queryString.contains("order by"))
		{
			String basequery1 = queryString.split("order by")[0];
			return basequery1;
		}
		else if(queryString.contains("group by"))
		{
			String basequery2 = queryString.split("group by")[0];
			return basequery2;
		}
		
		return queryString;

	}
	
	// get and display the where conditions part(if where condition exists)
	public String getConditionsPartQuery(String queryString) {
		
		if(queryString.contains("where"))
		{
			String cpq = queryString.split("where")[1].toLowerCase();
			if(cpq.contains("group by"))
			{
				String cpq1 = cpq.split("group by")[0];
				return cpq1;
			}else if(cpq.contains("order by"))
			{
				String cpq3 = cpq.split("order by")[0];
				return cpq3;
			}else
			
				return cpq;
		}else
	
		return null;

	}
	
	/* parse the where conditions and display the propertyName, propertyValue and
	 conditionalOperator for each conditions*/
	public String[] getConditions(String queryString) {
		
		String[] finalArray=null;
		if(queryString.contains("where"))
		{
			String cd1 =  queryString.split("where")[1].trim().toLowerCase();
			if(cd1.contains("group by") || cd1.contains("order by"))
			{
				String cd2 = cd1.split("order by | group by")[0].trim();
				if(cd2.contains("and") || cd2.contains("or")|| cd2.contains("not"))
				{
					finalArray = cd2.split("and | or | not");
					String [] finalArray1=new String[finalArray.length];
					int count=0;
					for(String a :finalArray)
					{
						finalArray1[count]=a.trim();
						count++;
					}
					return finalArray1;
				}
			}
			else
			{
								
				finalArray = cd1.split("and | or | not");
				String [] finalArray1=new String[finalArray.length];
				int count=0;
				for(String a :finalArray)
				{
					finalArray1[count]=a.trim();
					count++;
				}
				return finalArray1;
					
			}
			
		}
		
		return finalArray; 
		
		
	}
	
	// get the logical operators(applicable only if multiple conditions exist)
	public String[] getLogicalOperators(String queryString) {
			
		if(queryString.contains("where"))
		{
		
			String [] logicalOperator1 = queryString.split("where")[1].trim().split(" ");
			String [] logicalOperator = new String[logicalOperator1.length];
			int count=0;
			for(int i =0; i<logicalOperator1.length; i++)
			{
			if(logicalOperator1[i].equals("and") || logicalOperator1[i].equals("or") || logicalOperator1[i].equals("not"))
			{
			logicalOperator[count] = logicalOperator1[i];
			count++;
			}
			}
			String [] logicalOperator2 = new String[count];
			for(int i=0;i<count;i++){
			logicalOperator2[i]=logicalOperator[i];
			}
			return logicalOperator2;
		}
		else
			return null;
		
	}
	
	/*get the fields from the select clause*/
	public String[] getFields(String queryString) {
		
		String fields1 = queryString.split("from")[0];
		String [] fields = fields1.split("select")[1].trim().split(",");
		return fields;
		
	}
	// get order by fields if order by clause exists
	public String[] getOrderByFields(String queryString) {
		
		String [] orderby = null;
		if(queryString.contains("order by"))
		{
		orderby = queryString.split("order by")[1].trim().split("\\s");
		}
		return orderby;
		
	}
	
	// get group by fields if group by clause exists
	public String[] getGroupByFields(String queryString) {
		
		String [] groupby = null;
		if(queryString.contains("group by"))
		{
		groupby = queryString.split("group by")[1].trim().split("\\s");
		}
		return groupby;
	}
	
	// parse and display aggregate functions(if applicable)
	public String[] getAggregateFunctions(String queryString) {
		
		String aggregate1 = queryString.split("from")[0];
		String [] aggregate2 = aggregate1.split("select")[1].trim().split(",");
		String [] aggregate = new String[aggregate2.length];
		int i,j=0;
		for(i =0; i<aggregate2.length; i++)
		{
		if(aggregate2[i].contains("min") || aggregate2[i].contains("max") || aggregate2[i].contains("sum") || aggregate2[i].contains("count") || aggregate2[i].contains("avg"))
		{
		aggregate[j] = aggregate2[i];
		j++;
		}
		}
		String [] aggregate3 = new String[j];
		for(i=0;i<j;i++)
		{
		aggregate3[i]=aggregate[i];
		}
		return aggregate3;
		}
		
}