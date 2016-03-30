package employee.persistence;

import employee.logic.Empleado;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Docente
 */
public class XMLFile {
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;

    /**
     * Default constructor
     */
    public XMLFile() {
    }

    /**
     * Get Document Builder Factory
     * @return Document Builder Factory
     */
    public DocumentBuilderFactory getDocFactory() {
        return docFactory;
    }

    /**
     * Set Document Builder Factory
     * @param docFactory Document Builder Factory
     */
    public void setDocFactory(DocumentBuilderFactory docFactory) {
        this.docFactory = docFactory;
    }

    /**
     * Get Document Builder
     * @return Document Builder
     */
    public DocumentBuilder getDocBuilder() {
        return docBuilder;
    }

    /**
     * Set Document Builder
     * @param docBuilder Document Builder
     */
    public void setDocBuilder(DocumentBuilder docBuilder) {
        this.docBuilder = docBuilder;
    }

    /**
     * Read a xml file of employees and create a employee list
     * @param filename XML filename
     * @return Employee list
     */
    public List<Empleado> readFile(String filename) {
        List<Empleado> employeeList = new ArrayList<>();
        
        try {
            this.setDocFactory(DocumentBuilderFactory.newInstance());
            this.setDocBuilder(this.getDocFactory().newDocumentBuilder());
            Document doc = this.getDocBuilder().newDocument();
            
            Element rootElement = doc.getDocumentElement();
            NodeList xmlList = rootElement.getElementsByTagName("Employee");
            
            if (xmlList != null && xmlList.getLength() > 0) {
                for (int i = 0; i < xmlList.getLength(); i++) {
                    Element xmlElement = (Element)xmlList.item(i);
                    Empleado employee = getEmployee(xmlElement);
                    employeeList.add(employee);
                }
            }
        } catch (ParserConfigurationException | NullPointerException ex) {
            Logger.getLogger(XMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employeeList;
    }

    /**
     * Write file (XML format)
     * @param filename XML filename
     * @param employeeList Employee list to save
     */
    public void writeFile(String filename, List<Empleado> employeeList) {
        try {
            this.setDocFactory(DocumentBuilderFactory.newInstance());
            this.setDocBuilder(this.getDocFactory().newDocumentBuilder());
            
            Document doc = this.getDocBuilder().newDocument();
            Element rootElement = doc.createElement("Employees");
            doc.appendChild(rootElement);
            
            for (Empleado employee : employeeList) {
                rootElement.appendChild(getEmployee(doc, employee));
            }
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(XMLFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Build employee xml record
     * @param doc Document
     * @param employee Employee 
     * @return Employee element node
     */
    private Node getEmployee(Document doc, Empleado employee) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Element xmlEmployee = doc.createElement("Employee");
        
        xmlEmployee.setAttribute("id", "" + employee.getId());
        
        xmlEmployee.appendChild(getEmployeeElements(
                doc, xmlEmployee, "firstname", employee.getNombre()));
        xmlEmployee.appendChild(getEmployeeElements(
                doc, xmlEmployee, "lastname", employee.getApellido()));
        xmlEmployee.appendChild(getEmployeeElements(
                doc, xmlEmployee, "photo", employee.getFoto()));
        xmlEmployee.appendChild(getEmployeeElements(
                doc, xmlEmployee, "gender", "" + employee.getGenero()));
        xmlEmployee.appendChild(getEmployeeElements(
                doc, xmlEmployee, "bornDate", dateFormat.format(employee.getFechaNacimiento())));
        xmlEmployee.appendChild(getEmployeeElements(
                doc, xmlEmployee, "hiredDate", dateFormat.format(employee.getFechaIngreso())));
        xmlEmployee.appendChild(getEmployeeElements(
                doc, xmlEmployee, "salary", "" + employee.getSalario()));

        return xmlEmployee;
    }

    /**
     * Build employee xml field
     * @param doc Document
     * @param xmlEmployee Employee xml element
     * @param tagName Tag name
     * @param tagValue Tag value
     * @return Employee file node
     */
    private Node getEmployeeElements(Document doc, Element xmlEmployee, String tagName, String tagValue) {
        Element node = doc.createElement(tagName);
        node.appendChild(doc.createTextNode(tagValue));
        return node;
    }

    private Empleado getEmployee(Element xmlElement) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Empleado employee = new Empleado();
        
        try {
            employee.setId(Long.parseLong(xmlElement.getAttribute("id")));
            employee.setNombre(getTagValue(xmlElement, "firstname"));
            employee.setApellido(getTagValue(xmlElement, "lastname"));
            employee.setFoto(getTagValue(xmlElement, "photo"));
            employee.setGenero(Integer.parseInt(getTagValue(xmlElement, "gender")));
            employee.setFechaNacimiento(dateFormat.parse(getTagValue(xmlElement, "bornDate")));
            employee.setFechaIngreso(dateFormat.parse(getTagValue(xmlElement, "hiredDate")));
            employee.setSalario(Integer.parseInt(getTagValue(xmlElement, "salary")));
        } catch (ParseException ex) {
            Logger.getLogger(XMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employee;
    }

    private String getTagValue(Element xmlElement, String tagname) {
        String txtValue = null;
        NodeList nl = xmlElement.getElementsByTagName(tagname);
        if (nl != null && nl.getLength() > 0) {
            Element element = (Element)nl.item(0);
            txtValue = element.getFirstChild().getNodeValue();
        }
        return txtValue;
    }
}
