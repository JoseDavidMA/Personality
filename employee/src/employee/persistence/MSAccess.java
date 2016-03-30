/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.persistence;

import employee.logic.Empleado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MS Acess database
 * @author Estudiante
 */
public class MSAccess {
    private Connection connection; //DataBase connection
    private Statement statement;   //SQL statement
    private ResultSet resultSet;   //Result set 
    
    private final String dbFilename = "E:\\5to Semestre\\employee\\employee\\src\\employee\\persistence\\EmployeeDB.accdb";
    /**
     * Default constructor
     */
    public MSAccess(){
        if (this.connect()) {            
        }
    }
    
    /**
     * get databese connection
     * @return connection
     */
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * set database connection
     * @param connection set connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * get Statement
     * @return statement
     */
    public Statement getStatement() {
        return statement;
    }
    
    /**
     * Set statement
     * @param statement  
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
    /**
     * 
     * @return 
     */
    public ResultSet getResultSet() {
        return resultSet;
    }
    
    /**
     * 
     * @param resultSet 
     */
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
    
    /**
     * Try to connect to DB
     * @return True = connected, false = not connected
     */
    public boolean connect(){
        boolean connected = false;
        
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            this.setConnection(DriverManager.getConnection("jdbc:ucanaccess://" + this.dbFilename));
            connected = true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MSAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connected;
    }
    
    /**
     * Select employees from DB
     * @return Employee list
     */
    public List<Empleado> selectEmployee(){
        List<Empleado> employeeList = new ArrayList<>();
        
        try {
            this.setStatement(this.getConnection().createStatement());
            String query = "SELECT * FROM employee";
            this.getStatement().execute(query);
            this.setResultSet(this.getStatement().getResultSet());
            
            if(this.getResultSet() != null) {
                while(this.resultSet.next()) {
                    Empleado employee = new Empleado();
                    
                    employee.setId(this.getResultSet().getLong("id"));
                    employee.setNombre(this.getResultSet().getString("firstname"));
                    employee.setApellido(this.getResultSet().getString("lastname"));
                    employee.setFoto(this.getResultSet().getString("photo"));
                    employee.setGenero(this.getResultSet().getInt("gender"));
                    employee.setFechaNacimiento(this.getResultSet().getDate("bornDate"));
                    employee.setFechaIngreso(this.getResultSet().getDate("hiredDate"));
                    employee.setSalario(this.getResultSet().getInt("salary"));
                    
                    employeeList.add(employee);
                }
            }
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(MSAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return employeeList;
    }
    
    /**
     * Query data
     * @param query Query
     * @return True= successful operation
     */
    public boolean execute(String query){
        boolean result = false;
        try {
            this.setStatement(this.getConnection().createStatement());
            this.getStatement().execute(query);
            this.setResultSet(this.getStatement().getResultSet());
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(MSAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }
    
}
