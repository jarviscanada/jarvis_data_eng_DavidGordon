package ca.jrvs.apps.practice;

import ca.jrvs.apps.practice.dto.Company;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Company company = new Company();
        ObjectMapper mapper = new ObjectMapper();

        try {
            company = mapper.readValue(Paths.get("C:\\Users\\akali\\source\\jarvis_data_eng_DavidGordon\\core_java\\jdbc\\src\\main\\java\\ca\\jrvs\\apps\\practice\\Quote.json").toFile(), Company.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println(company);
    }
}
