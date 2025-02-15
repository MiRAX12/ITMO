package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

public class EmployeeXMLTest {

    @Test
    public void serializationTest() {

        // Create an object of POJO class
        Employee employee = new Employee();

        employee.setFirstName("Vibha");
        employee.setLastName("Singh");
        employee.setAge(35);
        employee.setSalary(135000);
        employee.setDesignation("Manager");
        employee.setContactNumber("+919999988822");
        employee.setEmailId("abc@test.com");
        employee.setMaritalStatus("married");
        employee.setGender("female");

        // Converting a Java class object to XML
        XmlMapper xmlMapper = new XmlMapper();

        try {
            String employeeXml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee);
            System.out.println(employeeXml);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}