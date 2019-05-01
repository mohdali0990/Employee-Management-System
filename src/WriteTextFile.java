// This class writes to text file. 
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;


public class WriteTextFile {
	
	private FileOutputStream fw = null;
	private BufferedWriter bw = null;

	public void delete(List<EmployeeRecord> list) throws IOException{
		try {		 
			fw = new FileOutputStream(Application.path);
			bw = new BufferedWriter(new OutputStreamWriter(fw));
			
			EmployeeRecord record;
			Iterator<EmployeeRecord> it = list.iterator();
			while(it.hasNext()){
				record = it.next();
				String content = Integer.toString(record.getID())+","+record.getFirstName()+" "+record.getLastName()+","
						+record.getYearStarted()+","+record.getSalary()+","+record.getTeamName();
				bw.write(content+'\n');
			}		
		}//end of try; 
		catch (IOException e) {
		e.printStackTrace();
	}//end of catch
		finally{
			bw.close();
			fw.close();
			}	
	
}//end of delete
	public void add(int id, String name, int year, int salary, String team) throws IOException{

		try {		 
			fw = new FileOutputStream(Application.path, true);
		    bw = new BufferedWriter(new OutputStreamWriter(fw));
	
				String content = Integer.toString(id)+","+ name+","+year+","+salary+","+team;
				bw.write(content+'\n');
							
			
		} catch (IOException e) {
				e.printStackTrace();
	}//end of catch
		finally{
			bw.close();
			fw.close();
		}
}
	
}
