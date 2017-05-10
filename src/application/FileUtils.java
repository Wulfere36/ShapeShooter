package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileUtils {
	
	/* the save function - saves the entire ArrayList myShapes to a binary file called <fileName> */
	public static void save(String fileName, ArrayList<Sprite> arrList) {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream (fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(arrList);
			fout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* reads the binary file called <fileName> and populates the ArrayList myShapes with it */
	@SuppressWarnings("unchecked")
	public static ArrayList<Sprite> read(String fileName) {
		FileInputStream fin;
		ArrayList<Sprite> arrList = null;
		try {
			fin = new FileInputStream (fileName);
			ObjectInputStream ois = new ObjectInputStream(fin);
			
			/* no need for anything fancy since myShapes.bin is a snapshot of the original ArrayList */
			arrList = (ArrayList<Sprite>)ois.readObject();
			
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrList;
		
	}
	

}
