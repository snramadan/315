/* Student: Saba Ramadan
 * Assignment: Lab 2
 * Section: 01
 *
 * Description: parses through assembly and script files.
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Emulator 
{
   public void first(Scanner scan, ArrayList<String> instr, HashMap<String, Integer> addr) 
   {
      String s;
      boolean found = false;
      boolean exists = true;
      int last;

      while (scan.hasNextLine()) 
      {
         s = scan.nextLine();
         s = s.trim();
         if (s.length() == 0 || s.startsWith("#")) 
         {    
            continue;                                   
         }

         if (s.contains(":"))
         {    
            addr.put(s.substring(0, s.indexOf(":")), instr.size());
            found = true;
         }

         last = clearLabel(s);
         if (found) 
         {          
            found = false;
            if (last >= 0)
            {  
               s = s.substring(last, s.length()).trim(); 
               exists = true;
            }
            else
               exists = false;
         }

         if (s.contains("#"))  
         {                 
            s = s.substring(0, s.indexOf("#")); 
         }

         if (exists)
         {
            instr.add(s);
         }

         found = false;
         exists = true;
      }
   }

   public ArrayList<String> second(ArrayList<String> instr, HashMap<String, Integer> label_addr) 
   {
      ArrayList<String> icodes = new ArrayList<String>();
      OPhashmaps opHash = new OPhashmaps(label_addr);
      int inc = 0;   

      for (String str : instr) 
      {
         ArrayList<String> ops;
         String oper;

         str = str.replace('\t', ' ');
         str = str.replace(',', ' ');
         str = rpFirst(str, "$", " $");

         ops = clearArr(str.split(" "));
         if (opHash.checkInstr(ops.get(0))) 
         {
            icodes.add(opHash.create(ops, inc));
         } 
         else 
         {
            icodes.add("invalid instruction: " + ops.get(0) + "\n");
            return icodes;
         }
         inc++; 
      }
      return icodes;
   }

   private ArrayList<String> clearArr(String [] arr) 
   {
      ArrayList<String> Arr = new ArrayList<String>();
      for (String str : arr) 
      {
         if (str.length() > 0) 
         {
            Arr.add(str.trim());
         }
      }
      return Arr;
   }

   private String rpFirst(String instr, String goal, String rp) 
   {
      int i;
      
      if (!instr.contains(goal)) 
      {
         return instr;
      } 
      else 
      {
         i = instr.indexOf(goal);
         return instr.substring(0, i) + rp + instr.substring(i + 1);
      }
   }

   private int clearLabel(String str) 
   {
      int index;
      if (str.indexOf(":") == str.length() - 1) 
      {
         return -1;
      }
      index = str.indexOf(":");
      if (index > 0) 
      {
         for (char c : str.substring(index + 1).toCharArray()) 
         {
            if (Character.isWhitespace(c)) 
            {
               continue;
            } 
            else if (c == '#') 
            {
               return -1;
            } 
            else 
            {
               return str.indexOf(":") + 1;
            }
         }
      }
      return -1;
   }
}
