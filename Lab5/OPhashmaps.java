/* Student: Saba Ramadan
 * Assignment: Lab 4
 * Section: 01
 *
 * Description: Hashmap that looks for operands, opcodes, and registers.
 */


import java.util.*;
import java.lang.*;
import java.io.*;

public class OPhashmaps 
{
    private HashMap<String, String> registers;
    private HashMap<String, Integer> addr;
    private HashMap<String, String> opcodes;
    private HashMap<String, String> functions;

    public OPhashmaps(HashMap<String, Integer> addr)
    {
        registers = regMap();
        opcodes = opMap();
        functions = functionMap();
        this.addr = addr;
    }

    public boolean checkInstr(String str)
    {
        return opcodes.containsKey(str);
    }

    public String create(ArrayList<String> ops, int l) 
    {
        if (functions.containsKey(ops.get(0))) 
        {     
            return parse1(ops, ops.get(0));    
        } 
        else if (ops.size() >= 3) 
        {                   
            return parse2(ops, ops.get(0), l);
        } 
        else 
        {
            return parse3(ops, ops.get(0));
        }
    }

    private String extendBit(String str, int i) 
    {
        int size = str.length();
        String str2 = "0";

        while (size < i) 
        {
            str = str2 + str;
            size++;
        }
        if (size > i + 1) 
        {
            str = str.substring(16);
        }
        return str;
    }

    private String parse1(ArrayList<String> operands, String str) 
    {
        StringBuffer strbuff = new StringBuffer();
        String shift = " ";
        strbuff.append(opcodes.get(str) + " ");

        if (str.equals("and") || str.equals("or") || str.equals("add") || str.equals("sub") || str.equals("slt")) 
        {
            strbuff.append(registers.get(operands.get(2)) + " ");

            strbuff.append(registers.get(operands.get(3)) + " ");

            strbuff.append(registers.get(operands.get(1)) + " ");

            strbuff.append("00000 ");
        } 
        else if (str.equals("sll")) 
        {    
            shift = extendBit(Integer.toBinaryString(Integer.parseInt(operands.get(3))), 5);

            strbuff.append("00000 ");

            strbuff.append(registers.get(operands.get(2)) + " ");

            strbuff.append(registers.get(operands.get(1)) + " ");

            strbuff.append(shift + " ");
        } 
        else 
        {
            strbuff.append(registers.get(operands.get(1)) + " ");
            
            strbuff.append("000000000000000 ");
        }

        strbuff.append(functions.get(str));
        return strbuff.toString();
    }

    private String parse2(ArrayList<String> operands, String str, int l)
    {
        StringBuffer strbuff = new StringBuffer();
        String bit;
        int temp = 0;
        int x, y;
        strbuff.append(opcodes.get(str) + " ");

        if (operands.size() == 3) 
        { 
            y = operands.get(2).indexOf("(");
            x = Integer.parseInt(operands.get(2).substring(0, y));
            bit = extendBit(Integer.toBinaryString(x), 16);
            strbuff.append(registers.get(operands.get(2).substring(y + 1, operands.get(2).length() - 1)) + " ");
            strbuff.append(registers.get(operands.get(1)) + " ");
            strbuff.append(bit);
        } 

        else if (addr.containsKey(operands.get(3))) 
        {
            bit = extendBit(Integer.toBinaryString((addr.get(operands.get(3)) - 1) - l), 16);
            strbuff.append(registers.get(operands.get(1)) + " ");
            strbuff.append(registers.get(operands.get(2)) + " ");
            strbuff.append(bit);
        } 

        else 
        {                                          
            strbuff.append(registers.get(operands.get(2)) + " ");
            strbuff.append(registers.get(operands.get(1)) + " ");
            bit = extendBit(Integer.toBinaryString(Integer.parseInt(operands.get(3))), 16);
            strbuff.append(bit);
        }

        return strbuff.toString();
    }

    private String parse3(ArrayList<String> operands, String str)
    {
        StringBuffer strbuff = new StringBuffer();
        String bit = extendBit(Integer.toBinaryString(addr.get(operands.get(1))), 26);
        strbuff.append(opcodes.get(str) + " ");
        strbuff.append(bit);
        return strbuff.toString();
    }

    private HashMap<String, String> functionMap() 
    {
        HashMap<String, String> functions = new HashMap<String, String>();
        
        functions.put("sll", "000000");
        functions.put("sub", "100010");
        functions.put("slt", "101010");
        functions.put("jr", "001000");
        functions.put("and", "100100");
        functions.put("or", "100101");
        functions.put("add", "100000");

        return functions;
    }

    private HashMap<String, String> opMap()
    {
        HashMap<String, String> opcodes = new HashMap<String, String>();
        
        opcodes.put("sw", "101011");
        opcodes.put("j", "000010");
        opcodes.put("jr", "000000");
        opcodes.put("jal", "000011");
        opcodes.put("and", "000000");
        opcodes.put("or", "000000");
        opcodes.put("add", "000000");
        opcodes.put("addi", "001000");
        opcodes.put("sll", "000000");
        opcodes.put("sub", "000000");
        opcodes.put("slt", "000000");
        opcodes.put("beq", "000100");
        opcodes.put("bne", "000101");
        opcodes.put("lw", "100011");

        return opcodes;
    }

    private HashMap<String, String> regMap() 
    {
        HashMap<String, String> registers = new HashMap<String, String>();

        registers.put("$0", "00000");
        registers.put("$zero", "00000");
        registers.put("$v0", "00010");
        registers.put("$v1", "00011");
        registers.put("$a0", "00100");
        registers.put("$a1", "00101");
        registers.put("$a2", "00110");
        registers.put("$a3", "00111");
        registers.put("$t0", "01000");
        registers.put("$t1", "01001");
        registers.put("$t2", "01010");
        registers.put("$t3", "01011");
        registers.put("$t4", "01100");
        registers.put("$t5", "01101");
        registers.put("$t6", "01110");
        registers.put("$t7", "01111");
        registers.put("$t8", "11000");
        registers.put("$t9", "11001");
        registers.put("$s0", "10000");
        registers.put("$s1", "10001");
        registers.put("$s2", "10010");
        registers.put("$s3", "10011");
        registers.put("$s4", "10100");
        registers.put("$s5", "10101");
        registers.put("$s6", "10110");
        registers.put("$s7", "10111");
        registers.put("$sp", "11101");
        registers.put("$ra", "11111");

        return registers;
    }
}
