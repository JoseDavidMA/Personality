package employee.logic;

import java.util.Date;

/**
 *
 * @author VM
 */
public class Empleado {
    private long id;        /** Employee id */
    private String nombre; /**Nombre del empleado **/
    private String apellido; /**Apellido del empleado **/
    private int genero; /**Genero del empleado **/
    private String foto; /**Ruta donde se encuentra la fotografia **/
    private int salario; /**Salario base del empleado **/
    private Date fechaNacimiento; /**Fecha de nacimiento del empleado **/
    private Date fechaIngreso; /**Fecha de ingreso empleado **/
    private final long MILS_DIAS = (1000*60*60*24);
    
    /**
     * Constructor por defecto
     */
    public Empleado() {
        this.id = 0;
        this.nombre = " ";
        this.apellido = " ";
        this.genero = 0;
        this.foto = " ";
        this.salario = 0;
        this.fechaNacimiento = new Date();
        this.fechaIngreso = new Date();
    }

    /**
     * Constructor with parameters
     * @param id Employee identification
     * @param nombre Nombre del empleado 
     * @param apellido Apellido del empleado
     * @param genero Genero del empleado
     * @param foto Ruta donde se encuentra la fotografia
     * @param salario Salario base del empleado 
     * @param fechaNacimiento Fecha de nacimiento del empleado
     * @param fechaIngreso Fecha de ingreso empleado
     */
    public Empleado(long id, String nombre, String apellido, char genero, String foto, int salario, Date fechaNacimiento, Date fechaIngreso) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.foto = foto;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaIngreso = fechaIngreso;
       
    }

    /**
     * Get Employee id
     * @return Employee id
     */
    public long getId() {
        return id;
    }

    /**
     * Set Employee id
     * @param id Employee id
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * obtiene los datos del empleado
     * @return Nombre del empleado
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Cambia los nombres del empleado
     * @param nombre Nombre del empleado 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * obtiene los apellidos del empleado
     * @return apellido del empleado
     */
    public String getApellido() {
        return apellido;
    }
    /**
     * Cambia los apellidos del empleado
     * @param apellido Apellido del empleado
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    /**
     * obtiene el genero del empleado
     * @return genero del empleado
     */
    public int getGenero() {
        return genero;
    }
    /**
     * Cambia el genero del empleado
     * @param genero Genero del empleado
     */
    public void setGenero(int genero) {
        this.genero = genero;
    }
    /**
     * obtiene la foto del empleado
     * @return foto del empleado
     */
    public String getFoto() {
        return foto;
    }
    /**
     * Cambia la foto del empleado
     * @param foto Ruta donde se encuentra la fotografia
      */
    public void setFoto(String foto) {
        this.foto = foto;
    }
    /**
     * obtiene el salario del empleado
     * @return salario del empleado
     */
    public int getSalario() {
        return salario;
    }
    /**
     * Cambia el salario del empleado
     * @param salario Salario base del empleado 
     */
    public void setSalario(int salario) {
        this.salario = salario;
    }
    /**
     * obtiene la fecha del nacimiento del empleado
     * @return fecha del nacimiento del empleado
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    /**
     * Cambia la fecha de nacimiento del empleado
     * @param fechaNacimiento Fecha de nacimiento del empleado
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    /**
     * obtiene la fecha de ingreso del empleado
     * @return fecha del empleado
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    /**
     * Cambia la fecha de ingreso del empleado
     * @param fechaIngreso  Fecha de ingreso empleado
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    /**
     * Años de antiguedad
     * @return Años de antigueda
     */
    public long getAntiguedad(){
      Date fechaActual=new Date();
      long antiguedad = (fechaActual.getTime()-this.getFechaIngreso().getTime());
      return antiguedad/MILS_DIAS;
    }
    
    /**
     * Edad del empleado
     * @return edad 
     */
    public long getEdadEmpleado(){
      Date fechaActual=new Date();
      long edad= (fechaActual.getTime()-this.getFechaNacimiento().getTime());
      return edad/MILS_DIAS; 
    }
    
    /**
     * Calcula las prestaciones del salario base
     * @return prestaciones 
     */
    public float getPrestaciones(){
        float prestaciones=(this.getAntiguedad()/365)*(this.getSalario()/12);
        return prestaciones;
    }
    
}