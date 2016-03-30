package employee.persistence;

import employee.logic.Empleado;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Manuel 
 * @date 2016
 */
public class FlatFile {
  private File file =null;
  private FileReader fr = null;
  private BufferedReader br = null;
  private FileWriter fw = null;
  private PrintWriter pw = null; 

    public FlatFile() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileReader getFr() {
        return fr;
    }

    public void setFr(FileReader fr) {
        this.fr = fr;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public FileWriter getFw() {
        return fw;
    }

    public void setFw(FileWriter fw) {
        this.fw = fw;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }
 
    public List<Empleado> readFile(String filename,String separator){
       List<Empleado> employeeList = new ArrayList<>();
    try {
          this.setFile(new File(filename));
          this.setFr(new FileReader(this.getFile()));
          this.setBr(new BufferedReader(this.getFr()));
          String record;
          SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
          while((record = this.getBr().readLine())!=null){
             String fields[] = record.split(separator);
             Empleado employee = new Empleado();
             employee.setId(Long.parseLong(fields[0]));
             employee.setNombre(fields[1]);
             employee.setApellido(fields[2]);
             employee.setFoto(fields[3]);
             employee.setGenero(Integer.parseInt(fields[4]));
             employee.setFechaNacimiento(dateFormat.parse(fields[5]));
             employee.setFechaIngreso(dateFormat.parse(fields[6]));
             employee.setSalario(Integer.parseInt(fields[7]));
             employeeList.add(employee);
          }
          this.getBr().close();
          this.getFr().close();
      } catch (FileNotFoundException fnfex){
         
      }
       catch (IOException | ParseException  ex) {
          Logger.getLogger(FlatFile.class.getName()).log(Level.SEVERE, null, ex);
      }
       return employeeList;
    }
    
    public void writeFile(List<Empleado> employeeList,String filename, String separator){
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
          this.setFw(new FileWriter(filename)); //El true sirve para adicionar información 
          this.setPw(new PrintWriter(this.getFw()));
          
          for (Empleado employee : employeeList) //Con esto se recorre la lista
          {
            this.getPw().print(employee.getId() + separator);
            this.getPw().print(employee.getNombre() + separator);
            this.getPw().print(employee.getApellido()+ separator);
            this.getPw().print(employee.getFoto()+ separator);
            this.getPw().print(employee.getGenero() + separator);
            this.getPw().print(dateFormat.format(employee.getFechaNacimiento())+ separator);
            this.getPw().print(dateFormat.format(employee.getFechaIngreso())+ separator);
            this.getPw().println(employee.getSalario() + separator);
          }
          this.getPw().close();
          this.getFw().close();
      } catch (IOException ex) {
          Logger.getLogger(FlatFile.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  
}
