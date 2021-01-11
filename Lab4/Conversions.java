/* Student: Saba Ramadan
 * Assignment: Lab 4
 * Section: 01
 */

public class Conversions
{
   public static boolean checkR(String str)
   {
      return (str.equals("000000"));
   }

   public static boolean checkJ(String str)
   {
      return str.equals("000010") || str.equals("000011");
   }

   public static String[] parseR(String[] icodes) 
   {
      String[] arr = new String[4];
      int last= icodes.length - 1;
      arr[0] = icodes[1];

      if (icodes.length > 4) 
      {
        arr[1] = icodes[2];
        arr[2] = icodes[3];
      }

      arr[3] = Conversions.getR(icodes[last]);
      return arr;
   }

   public static String[] parseJ(String [] icodes) 
   {
      String[] arr = new String[4];
      int num;

      if (icodes[0].equals("000010")) 
      {
        arr[3] = "j";
      } 
      else 
      {
        arr[3] = "jal";
      }

      num = ~~Integer.parseInt(icodes[1], 2);
      arr[0] = "" + num;
      
      return arr;
   }

   public static String[] parseI(String [] icodes) 
   {
      String [] arr = new String[4];
      String OP = icodes[0];
      int last = icodes.length - 1;
      arr[0] = icodes[1];
      arr[1] = icodes[2];
      arr[2] = "" + (int) Long.parseLong(extend(icodes[last]), 2);

      if (OP.equals("001000")) 
      {
        arr[3] = "addi";
      } 
      else if (OP.equals("000100")) 
      {
        arr[3] = "beq";
      } 
      else if (OP.equals("000101")) 
      {
        arr[3] = "bne";
      } 
      else if (OP.equals("100011")) 
      {
        arr[3] = "lw";
      } 
      else 
      {
        arr[3] = "sw";
      }

      return arr;
   }

   private static String extend(String bit) 
   {
      int size = bit.length();
      int total = 32;
      String msb = bit.substring(0, 1);

      while (size != total) 
      {
         bit = msb + bit;
         ++size;
      }
      return bit;
   }

   private static String getR(String code) 
   {
      if (code.equals("100100")) 
      {
         return "and";
      } 
      else if (code.equals("100101")) 
      {
         return "or";
      } 
      else if (code.equals("100000")) 
      {
         return "add";
      } 
      else if (code.equals("000000")) 
      {
         return "sll";
      } 
      else if (code.equals("100010")) 
      {
         return "sub";
      } 
      else if (code.equals("101010")) 
      {
         return "slt";
      } 
      else 
      {
         return "jr";
      }
   }
}
