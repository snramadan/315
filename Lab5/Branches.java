/* Student: Saba Ramadan
 * Assignment: Lab 5
 * Section: 01
 */

import java.lang.Math;

public class Branches
{
   private final int x1 = 0; 
   private final int y1 = 1;   
   private final int x2 = 2;
   private final int y2 = 3;
   private int val;
   private int mask;
   private int temp;
   private int total;
   private int[] arr;

   public Branches() 
   {
      mask = 3;
      arr = new int[4];
      temp = 0;
      total = 0;
   }

   public Branches(int size)
   {
      int i;
      mask = (int)Math.pow(2.0, (double)(size)) - 1;
      arr = new int [(int)Math.pow(2.0, (double)size)];
      temp = 0;
      total = 0;
      
      for (i = 0; i < arr.length; i++)
      {
        arr[i] = 0;
      }
   }

   private boolean pull(int x) 
   {
      return arr[x] >= 2;
   }

   public void prints()
   {
      if (total == 0) 
      {
        System.out.println("There have been no predictions");
      } 
      else 
      {
        float accuracy = (float)temp/total * 100;
        System.out.printf("\naccuracy %.2f%% (%d ", accuracy, temp);
        System.out.printf("correct predictions, %d predictions)\n\n", total);
      }
   }

   public void changePreds(boolean taken)
   {
      temp += (taken == pull(val)) ? 1 : 0;
      total++;
      int changes = -1;
      int old = 0;
      int hold = 0;

      if (taken) 
      {
        changes = 1;
      }
      arr[val] += changes;

      hold = arr[val];
      if (hold < 0)
      {
        arr[val] = 0;
      } 
      else if (hold > 3) 
      {
        arr[val] = 3;
      }

      old = val;
      val <<= 1;

      if (taken)
      {
        val += 1;
      }

      val &= mask;
   }
}
