package assemblerpass1;

public class assembler_pass1 {
	/********************MAIN .JAVA*****************************************
package assemblerpass1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Main {
    int lc = 0;
    int libtab_ptr = 0, pooltab_ptr = 0;
    int symIndex = 0, litIndex = 0;
    LinkedHashMap<String, TableRow> SYMTAB;
    ArrayList<TableRow> LITTAB;
    ArrayList<Integer> POOLTAB;
    private BufferedReader br;

    public Main() {
        SYMTAB = new LinkedHashMap<>();
        LITTAB = new ArrayList<>();
        POOLTAB = new ArrayList<>();
        lc = 0;
        POOLTAB.add(0);
    }

    public static void main(String[] args) {
        Main one = new Main();
        try {
            one.parseFile();
        } catch (Exception e) {
            System.out.println("Error: " + e);// TODO: handle exception
        }
    }

    public void parseFile() throws Exception {
        String prev = "";
        String line, code;
        br = new BufferedReader(new FileReader("C:\\Users\\SURAJ\\OneDrive\\Desktop\\eclippse\\OSCODES\\src\\assemblerpass1\\input.asm"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("IC.txt"));
        INSTtable lookup = new INSTtable();

        while ((line = br.readLine()) != null) {
            String parts[] = line.split("\\s+");
            if (!parts[0].isEmpty()) // processing of label
            {
                if (SYMTAB.containsKey(parts[0]))
                    SYMTAB.put(parts[0], new TableRow(parts[0], lc, SYMTAB.get(parts[0]).getIndex()));
                else
                    SYMTAB.put(parts[0], new TableRow(parts[0], lc, ++symIndex));
            }

            if (parts[1].equals("LTORG")) {
                int ptr = POOLTAB.get(pooltab_ptr);
                for (int j = ptr; j < libtab_ptr; j++) {
                    lc++;
                    LITTAB.set(j, new TableRow(LITTAB.get(j).getSymbol(), lc));
                    code = "(DL,01)\t(C," + LITTAB.get(j).symbol + ")";
                    bw.write(code + "\n");
                }
                pooltab_ptr++;
                POOLTAB.add(libtab_ptr);
            }

            if (parts[1].equals("START")) {
                lc = expr(parts[2]);
                code = "(AD,01)\t(C," + lc + ")";
                bw.write(code + "\n");
                prev = "START";
            } else if (parts[1].equals("ORIGIN")) {
                lc = expr(parts[2]);
                String splits[] = parts[2].split("\\+"); // Same for – SYMBOL // Add code
                code = "(AD,03)\t(S," + SYMTAB.get(splits[0]).getIndex() + ")+"
                        + Integer.parseInt(splits[1]);
                bw.write(code + "\n");
            }

            // Now for EQU
            if (parts[1].equals("EQU")) {
                int loc = expr(parts[2]);
                // below If conditions are optional as no IC is generated for them
                if (parts[2].contains("+")) {
                    String splits[] = parts[2].split("\\+");
                    code = "(AD,04)\t(S," + SYMTAB.get(splits[0]).getIndex() + ")+"
                            + Integer.parseInt(splits[1]);
                } else if (parts[2].contains("-")) {
                    String splits[] = parts[2].split("\\-");
                    code = "(AD,04)\t(S," + SYMTAB.get(splits[0]).getIndex() + ")-" + Integer.parseInt(splits[1]);
                } else {
                	code = "(AD,04)\t(C," + Integer.parseInt(parts[2]) + ")";
                }
                bw.write(code + "\n");
                if (SYMTAB.containsKey(parts[0]))
                    SYMTAB.put(parts[0], new TableRow(parts[0], loc, SYMTAB.get(parts[0]).getIndex()));
                else
                    SYMTAB.put(parts[0], new TableRow(parts[0], loc, ++symIndex));
            }

            if (parts[1].equals("DC")) {
                lc++;
                int constant = Integer.parseInt(parts[2].replace("'", ""));
                code = "(DL,01)\t(C," + constant + ")";
                bw.write(code + "\n");
            } else if (parts[1].equals("DS")) {
                int size = Integer.parseInt(parts[2].replace("'", ""));
                code = "(DL,02)\t(C," + size + ")";
                bw.write(code + "\n");
                /* if (prev.equals("START")) { lc = lc + size - 1;//
                 * System.out.println(“here”); } else  lc = lc + size;
                prev = "";
            }

            if (lookup.getType(parts[1]).equals("IS")) {
                code = "(IS,0" + lookup.getCode(parts[1]) + ")\t";
                int j = 2;
                String code2 = "";
                while (j < parts.length) {
                    parts[j] = parts[j].replace(",", "");
                    if (lookup.getType(parts[j]).equals("RG")) {
                        code2 += lookup.getCode(parts[j]) + "\t";
                    } else {
                        if (parts[j].contains("=")) {
                            parts[j] = parts[j].replace("=", "").replace("'", "");
                            LITTAB.add(new TableRow(parts[j], -1, ++litIndex));
                            libtab_ptr++;
                            code2 += "(L," + (litIndex) + ")";
                        } else if (SYMTAB.containsKey(parts[j])) {
                            int ind = SYMTAB.get(parts[j]).getIndex();
                            code2 += "(S,0" + ind + ")";
                        } else {
                            SYMTAB.put(parts[j], new TableRow(parts[j], -1, ++symIndex));
                            int ind = SYMTAB.get(parts[j]).getIndex();
                            code2 += "(S,0" + ind + ")";
                        }
                    }
                    j++;
                }
                lc++;
                code = code + code2;
                bw.write(code + "\n");
            }

            if (parts[1].equals("END")) {
                int ptr = POOLTAB.get(pooltab_ptr);
                for (int j = ptr; j < libtab_ptr; j++) {
                    lc++;
                    LITTAB.set(j, new TableRow(LITTAB.get(j).getSymbol(), lc));
                    code = "(DL,01)\t(C," + LITTAB.get(j).symbol + ")";
                    bw.write(code + "\n");
                }
                pooltab_ptr++;
                POOLTAB.add(libtab_ptr);
                code = "(AD,02)";
                bw.write(code + "\n");
            }
        }

        bw.close();
        printSYMTAB();
        // Printing Literal table
        PrintLITTAB();
        printPOOLTAB();
    }

    void PrintLITTAB() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("LITTAB.txt"));
        System.out.println("\nLiteral Table\n");
        // Processing LITTAB
        for (int i = 0; i < LITTAB.size(); i++) {
            TableRow row = LITTAB.get(i);
            System.out.println(i + "\t" + row.getSymbol() + "\t" + row.getAddress());
            bw.write((i + 1) + "\t" + row.getSymbol() + "\t" + row.getAddress() + "\n");
        }
        bw.close();
    }

    void printPOOLTAB() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("POOLTAB.txt"));
        System.out.println("\\Npooltab");
        System.out.println("Index\t#first");
        for (int i = 0; i < POOLTAB.size(); i++) {
            System.out.println(i + "\t" + POOLTAB.get(i));
            bw.write((i + 1) + "\t" + POOLTAB.get(i) + "\n");
        }
        bw.close();
    }

    void printSYMTAB() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("SYMTAB.txt"));
        // Printing Symbol Table
        java.util.Iterator<String> iterator = SYMTAB.keySet().iterator();
        System.out.println("SYMBOL TABLE");
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            TableRow value = SYMTAB.get(key);
            System.out.println(value.getIndex() + "\t" + value.getSymbol() + "\t" + value.getAddress());
            bw.write(value.getIndex() + "\t" + value.getSymbol() + "\t" + value.getAddress() + "\n");
        }
        bw.close();
    }

    public int expr(String str) {
        int temp = 0;
        if (str.contains("+")) {
            String splits[] = str.split("\\+");
            temp = SYMTAB.get(splits[0]).getAddress() + Integer.parseInt(splits[1]);
        } else if (str.contains("-")) {
            String splits[] = str.split("\\-");
            temp = SYMTAB.get(splits[0]).getAddress() - (Integer.parseInt(splits[1]));
        } else {
            temp = Integer.parseInt(str);
        }
        return temp;
    }
}

}*/
	
	
	
	
	
	
	
	
	
/****************INSTable.JAVA*******************************
	package assemblerpass1;

import java.util.HashMap;

public class INSTtable {
    HashMap<String, Integer> AD, RG, IS, CC, DL;

    public INSTtable() {
        AD = new HashMap<>();
        CC = new HashMap<>();
        IS = new HashMap<>();
        RG = new HashMap<>();
        DL = new HashMap<String, Integer>();
        DL.put("DC", 01);
        DL.put("DS", 02);
        IS.put("STOP", 0);
        IS.put("ADD", 1);
        IS.put("SUB", 2);
        IS.put("MULT", 3);
        IS.put("MOVER", 4);
        IS.put("MOVEM", 5);
        IS.put("COMP", 6);
        IS.put("BC", 7);
        IS.put("DIV", 8);
        IS.put("READ", 9);
        IS.put("PRINT", 10);
        CC.put("LT", 1);
        CC.put("LE", 2);
        CC.put("EQ", 3);
        CC.put("GT", 4);
        CC.put("GE", 5);
        CC.put("ANY", 6);
        AD.put("START", 1);
        AD.put("END", 2);
        AD.put("ORIGIN", 3);
        AD.put("EQU", 4);
        AD.put("LTORG", 5);
        RG.put("AREG", 1);
        RG.put("BREG", 2);
        RG.put("CREG", 3);
        RG.put("DREG", 4);
    }

    public String getType(String s) {
        s = s.toUpperCase();
        if (AD.containsKey(s))
            return "AD";
        else if (IS.containsKey(s))
            return "IS";
        else if (CC.containsKey(s))
            return "CC";
        else if (DL.containsKey(s))
            return "DL";
        else if (RG.containsKey(s))
            return "RG";
        return "";
    }

    public int getCode(String s) {
        s = s.toUpperCase();
        if (AD.containsKey(s))
            return AD.get(s);
        else if (IS.containsKey(s))
            return IS.get(s);
        else if (CC.containsKey(s))
            return CC.get(s);
        else if (DL.containsKey(s))
            return DL.get(s);
        else if (RG.containsKey(s))
            return RG.get(s);
        return -1;
    }
}

	 
*/
	
	
	
	
	
/****************TableRow.JAVA*******************************
	 package assemblerpass1;

public class TableRow {
    String symbol;
    int address, index;

    public String getSymbol() {
        return symbol;
    }

    public TableRow(String symbol, int address) {
        super();
        this.symbol = symbol;
        this.address = address;
        index = 0;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public TableRow(String symbol, int address, int index) {
        super();
        this.symbol = symbol;
        this.address = address;
        this.index = index;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

*/

}


package assemblerpass2;

public class assembler_pass2 {
/*************************MAIN.JAVA***********************************
 package assemblerpass2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
public class Main {
ArrayList<TableRow> SYMTAB,LITTAB;
public Main()
{
SYMTAB=new ArrayList<>();
LITTAB=new ArrayList<>();
}
public static void main(String[] args) {
Main pass2=new Main();
try {
pass2.generateCode("IC.txt");
} catch (Exception e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
public void readtables()
{
BufferedReader br;
String line;
try
{
br=new BufferedReader(new FileReader("SYMTAB.txt"));
while((line=br.readLine())!=null)
{
String parts[]=line.split("\\s+");
SYMTAB.add(new TableRow(parts[1],
Integer.parseInt(parts[2]),Integer.parseInt(parts[0]) ));
}
br.close();
br=new BufferedReader(new FileReader("LITTAB.txt"));
while((line=br.readLine())!=null)
{
String parts[]=line.split("\\s+");
LITTAB.add(new TableRow(parts[1],
Integer.parseInt(parts[2]),Integer.parseInt(parts[0])));
}
br.close();
}
catch (Exception e) {
System.out.println(e.getMessage());
}
}
public void generateCode(String filename) throws Exception
{
readtables();
BufferedReader br=new BufferedReader(new FileReader(filename));
BufferedWriter bw=new BufferedWriter(new FileWriter("PASS2.txt"));
String line,code;
while((line=br.readLine())!=null)
{
String parts[]=line.split("\\s+");
if(parts[0].contains("AD")||parts[0].contains("DL,02"))
{
bw.write("\n");
continue;
}
else if(parts.length==2)
{
if(parts[0].contains("DL")) //DC INSTR
{
parts[0]=parts[0].replaceAll("[^0-9]", "");
if(Integer.parseInt(parts[0])==1)
{
int constant=Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
code="00\t0\t"+String.format("%03d", constant)+"\n";
bw.write(code);
}
}
else if(parts[0].contains("IS"))
{
int opcode=Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
if(opcode==10)
{
if(parts[1].contains("S"))
{
int
symIndex=Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
code=String.format("%02d",
opcode)+"\t0\t"+String.format("%03d", SYMTAB.get(symIndex-1).getAddress())+"\n";
bw.write(code);
}
else if(parts[1].contains("L"))
{
int
symIndex=Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
code=String.format("%02d",
opcode)+"\t0\t"+String.format("%03d", LITTAB.get(symIndex-1).getAddress())+"\n";
bw.write(code);
}
}
}
}
else if(parts.length==1 && parts[0].contains("IS"))
{
int opcode=Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
code=String.format("%02d", opcode)+"\t0\t"+String.format("%03d",
0)+"\n";
bw.write(code);
}
else if(parts[0].contains("IS") && parts.length==3) //All OTHER IS INSTR
{
int opcode= Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
int regcode=Integer.parseInt(parts[1]);
if(parts[2].contains("S"))
{
int symIndex=Integer.parseInt(parts[2].replaceAll("[^0-9]", ""));
code=String.format("%02d",
opcode)+"\t"+regcode+"\t"+String.format("%03d", SYMTAB.get(symIndex-1).getAddress())+"\n";
bw.write(code);
}
else if(parts[2].contains("L"))
{
int symIndex=Integer.parseInt(parts[2].replaceAll("[^0-9]", ""));
code=String.format("%02d",
opcode)+"\t"+regcode+"\t"+String.format("%03d", LITTAB.get(symIndex-1).getAddress())+"\n";
bw.write(code);
}
}
}
System.out.println("Program Executed !!!");
bw.close();
br.close();
}
}

 */
	
	
	
	
	/*************************TableRow.JAVA***********************************
	 package assemblerpass2;

public class TableRow {
String symbol;
int address;
int index;
public TableRow(String symbol, int address) {
super();
this.symbol = symbol;
this.address = address;
}
public TableRow(String symbol, int address,int index) {
super();
this.symbol = symbol;
this.address = address;
this.index=index;
}
public int getIndex() {
return index;
}
public void setIndex(int index) {
this.index = index;
}
public String getSymbol() {
return symbol;
}
public void setSymbol(String symbol) {
this.symbol = symbol;
}
public int getAddress() {
return address;
}
public void setAddress(int address) {
this.address = address;
}
}

	 */
}

