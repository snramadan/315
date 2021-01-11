/* Author: Saba Ramadan
 * Lab: 6
 */

import java.lang.*;
import java.util.*;
import java.io.*;

public class Cache 
{
   private int cache;
   private int length;
   private int entries;
   private int rows;
   private int t;
   private int ind;
   private int ctr;
   private int holds;
   private int hits;
   private int ents;
   private Entries [] arr;
   private LinkedList<Integer> [] temp;


   @SuppressWarnings("unchecked")
   public Cache(int cache, int length, int entries, int holds) 
   {
      this.cache = cache;
      this.length = length;
      this.entries = entries;
      this.holds = holds;
      this.hits = 0;
      this.ents = 0;
      getBits();
      temp = new LinkedList[rows];
      arr = new Entries[entries];
      int i;
      
      for (i = 0; i < entries; i++) 
      {
         arr[i] = new Entries(rows);
      }
      
      for (i = 0; i < rows; i++) 
      {
         temp[i] = new LinkedList<Integer>();
      }
   }

   public void getCache(int addr) 
   {
      boolean bool = true;
      int i;
      int x = ((addr >> ctr) & ((1 << ind) - 1)) % rows;
      int y = (addr >> (ctr + ind)) & ((1 << t) - 1);

      for (i = 0; i < entries; i++) 
      {
         if (arr[i].getEntries(x) == y) 
         {
            hits++;
            bool = false;
         } 
         else if (arr[i].getEntries(x) == -1) 
         {
            bool = false;
            arr[i].setEntries(x, y);
         }
         if (bool == false) 
         {
            while (temp[x].removeFirstOccurrence(i));
            temp[x].offer(i);
            break;
         }
      }

      if (bool) 
      {
         int empty = temp[x].poll();
         arr[empty].setEntries(x, y);
         temp[x].offer(empty);
      }

      ents++;
   }

   private void getBits() 
   {
      rows = length / (holds * entries * 4);
      ind = (int)(Math.log(rows)/Math.log(2));
      ctr = 2 + (int)(Math.log(holds)/Math.log(2));
      t = (32 - ind) - ctr;

   }

   public void print() 
   {
      double average = ((1.0 * this.hits) / this.ents) * 100;
      System.out.printf("Cache #%d\n", this.cache);
      System.out.printf("Cache size: %dB\tAssociativity: %d\tBlock size: %d\n", this.length, this.entries, this.holds);
      System.out.printf("Hits: %d\tHit Rate: %.2f%%\n", this.hits, average);
      System.out.printf("---------------------------\n");
   }
}
