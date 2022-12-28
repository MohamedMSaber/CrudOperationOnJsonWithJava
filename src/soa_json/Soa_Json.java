/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soa_json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.json.simple.parser.JSONParser;
//import org.js


public class Soa_Json {

    public static void main(String[] args) {
        while(true){
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Choose 1 to add new records");
                System.out.println("Choose 2 to Read ");
                System.out.println("Choose 3 to Search");
                System.out.println("Choose 4 to delete");
                System.out.println("Choose 5 to Exit");
                System.out.println("Enter your choose");
                String choose = in.nextLine();
                // open or create file
                File file = new File("src\\soa_Json\\output.json");
                if(choose.equals("1")){
                    add_create(in,file);
                }
                else if(choose.equals("2")){
                    System.out.println(Read(file));                           
                }
                else if(choose.equals("3")){
                    search(in,file);               
                }else if(choose.equals("4")){
                    Delete(in,file);
                }
                else if(choose.equals("5")){
                    System.exit(0);                            
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void add_create( Scanner in,File file) throws SAXException, IOException, ParseException, org.json.simple.parser.ParseException {
                JSONArray users = null;
                if(file.exists()){
                    users = Read(file);
                }else{
                    users = new JSONArray() ;
                }
                FileWriter f=new FileWriter(file);
                String BlName , city , FoundationYear;
                System.out.println("Enter BlName: ");
                BlName = in.nextLine();
                System.out.println("Enter city: ");
                city = in.nextLine();
                System.out.println("Enter FoundationYear: ");
                FoundationYear = in.nextLine();
                Map<String, String> map = new LinkedHashMap<String, String>();
                map.put("BlName" , BlName);
                map.put("city" , city);
                map.put("FoundationYear" , FoundationYear);
                users.add(map);
                try {
                    f.write(users.toJSONString());
                    f.close();
                 } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                 }
    }
    public static JSONArray Read(File file) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
                    JSONParser jsonParser = new  JSONParser();
                    FileReader reader = new FileReader(file);
                    Object obj = jsonParser.parse(reader, new ContainerFactory(){
                        @Override
                        public Map createObjectContainer() {
                            return new LinkedHashMap();
                        }

                        @Override
                        public List creatArrayContainer() {
                            return null;
                        }
                    });
                    return (JSONArray) obj;
    }
    public static void search(Scanner in,File file) throws FileNotFoundException, IOException, ParseException, org.json.simple.parser.ParseException {
                System.out.println("choose 1 to search by city");
                System.out.println("choose 2 to search by FoundationYear");
                String ch = in.nextLine();
                System.out.println("Enter Value to search By it");
                String SearchingValue = in.nextLine();
                JSONArray users = Read(file);
                ArrayList <LinkedHashMap<String, String>> Building = new ArrayList <LinkedHashMap<String, String>>();
                for(int i=0;i<users.size();i++){
                    LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) users.get(i);
                    if(ch.equals("1")){
                        if((map.get("city")).equals(SearchingValue)){
                            Building.add(map);
                        }
                    }else if(ch.equals("2")){
                        if((map.get("FoundationYear")).equals(SearchingValue)){
                            Building.add(map);
                        }
                    }
                }
                if((Building.size()) == 0){
                        System.out.println("Not Exist any Buildings");
                }
                for(LinkedHashMap<String, String> build : Building){
                    System.out.println(build);
                }
    }
    public static Map<String, String> Convertor(String value){
        value = value.substring(1, value.length()-1);           //remove curly brackets
        String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
        Map<String,String> map = new LinkedHashMap<>();               
        for(String pair : keyValuePairs)                        //iterate over the pairs
        {
            String[] entry = pair.split("=");                   //split the pairs to get key and value 
            map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
        }
        return map;
    }
    public static void Delete(Scanner in,File file) throws IOException, FileNotFoundException, ParseException, org.json.simple.parser.ParseException {
        //        remove object with index
        //        jsArray.remove(3);
                System.out.println("Enter Number of json object that you need to delete");
                String SearchingValue = in.nextLine();
                int index = Integer.parseInt(SearchingValue) - 1;
                JSONArray jsArray = Read(file);
                if(index >= (jsArray.size())){
                    System.out.println("This object not exists");
                }else{
                    jsArray.remove(index);
                    FileWriter f=new FileWriter(file);
                     try {
                        f.write(jsArray.toJSONString());
                        f.close();
                     } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                     }
                    System.out.println("Deleted successfully");
                }
    }
}